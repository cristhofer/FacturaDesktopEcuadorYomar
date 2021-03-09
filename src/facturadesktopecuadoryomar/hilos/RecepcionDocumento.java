package facturadesktopecuadoryomar.hilos;

import com.ecuasis.coresri.excepciones.NodeNotFound;
import com.ecuasis.coresri.modelo.sri.recepcion.Autorizacion;
import com.ecuasis.coresri.modelo.sri.recepcion.Mensaje;
import com.ecuasis.coresri.modelo.sri.recepcion.RespuestaAutorizacionComprobante;
import com.ecuasis.coresri.servicios.ServiciosSRI;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class RecepcionDocumento extends Thread {
  private DocumentoFacade documentoLogica = new DocumentoFacade();
  private List<Documento> listaDocumentos;
  private ServiciosSRI sri;
  
  public RecepcionDocumento(List<Documento> listaDocumentos, ThreadGroup group, String name, ServiciosSRI sri) {
    super(group, name);
    this.listaDocumentos = listaDocumentos;
    this.sri = sri;
  }
  
  @Override
  public void run() {
    for (Documento x : this.listaDocumentos) {
      try {
        RespuestaAutorizacionComprobante autorizacion = this.sri.comprobarAutorizacionSimple(x.getClaveAccesoDocu());
        String error = "";
        if (autorizacion.getAutorizaciones() != null) {
          if (autorizacion.getAutorizaciones().getAutorizacion() != null) {
            Autorizacion auth = autorizacion.getAutorizaciones().getAutorizacion().get(0);
            if (auth != null) {
              if (auth.getMensajes() != null) {
                if (auth.getMensajes().getMensaje() != null) {
                  if (auth.getMensajes().getMensaje().size() > 0) {
                    Mensaje msg = auth.getMensajes().getMensaje().get(0);
                    error = error + "Tipo:" + msg.getTipo() + ", Identificador:" + msg.getIdentificador() + ", Mensaje:" + msg.getMensaje() + ", Informacion Adicional:";
                  } 
                }
              }
              
              x.setNumeroAutorizacionDocu(auth.getNumeroAutorizacion());
              x.setFechaAutorizacionDocu(auth.getFechaAutorizacion());
              x.setXmlFirmDocu(auth.getComprobante());
              if (auth.getEstado().equals("NO AUTORIZADO") || auth.getEstado().equals("RECHAZADO")) {
                x.setEstadoDocu("RECHAZADO");
              } else {
                x.setEstadoDocu(auth.getEstado());
              } 
              x.setObservacionDocu(error);
              this.documentoLogica.modificar(x);
            }
          }
        }
      }
      catch (IOException ex) {
        Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JAXBException ex) {
        Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
        Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
      } catch (NodeNotFound ex) {
        Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
      } catch (SAXException ex) {
        Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ParserConfigurationException ex) {
        Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
      } 
    } 
    this.listaDocumentos = new LinkedList<>();
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.documentoLogica = null;
    this.listaDocumentos = null;
    this.sri = null;
  }
}