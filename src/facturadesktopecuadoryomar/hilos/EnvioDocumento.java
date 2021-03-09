package facturadesktopecuadoryomar.hilos;

import com.ecuasis.coresri.excepciones.NodeNotFound;
import com.ecuasis.coresri.modelo.sri.envio.Comprobante;
import com.ecuasis.coresri.modelo.sri.envio.Mensaje;
import com.ecuasis.coresri.modelo.sri.envio.RespuestaRecepcionComprobante;
import com.ecuasis.coresri.servicios.ServiciosSRI;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.utilidades.StateEntity;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class EnvioDocumento extends Thread {
  private DocumentoFacade documentoLogica = new DocumentoFacade();
  private List<Documento> listaDocumentos;
  private ServiciosSRI sri;
  
  public EnvioDocumento(List<Documento> listaDocumentos, ThreadGroup group, String name, ServiciosSRI sri) {
    super(group, name);
    this.listaDocumentos = listaDocumentos;
    this.sri = sri;
  }
  
  @Override
  public void run() {
    for (Documento x : this.listaDocumentos) {
      try {
        System.out.println("PREPARANDO DOCUMENTO PARA ENVIAR SRI:" + x.getNumeroDocu());
        RespuestaRecepcionComprobante recepcion = this.sri.enviarDocumento(x.getXmlFirmDocu());
        if (recepcion.getEstado().equals("RECIBIDA")) {
          
          x.setMensajeDocu("ENVIADOSRI");
          System.out.println("ENVIADO AL SRI: " + x.getNumeroDocu());
        } else {
          String error = "";
          for (Mensaje msg : recepcion.getComprobantes().getComprobante().get(0).getMensajes().getMensaje()) {
            error += "Estado:" + recepcion.getEstado() + "Tipo:" + msg.getTipo() + ", Identificador:" + msg.getIdentificador() + ", Mensaje:" + msg.getMensaje() + ", Informacion Adicional:" + msg.getInformacionAdicional();
          }
          x.setEstadoDocu(StateEntity.DEVUELTA.name());
          x.setObservacionDocu(error);
          System.out.println("ERROR DEL DOCUMENTO ENVIADO AL SRI: " + x.getNumeroDocu());
        } 
        x.setFechaEnvioDocu(new Date());
        this.documentoLogica.modificar(x);
      } catch (IOException ex) {
        System.err.println("El Servicio del SRI no esta operativo: " + ex.getMessage());
      } catch (JAXBException ex) {
        System.err.println("El xml servido por el sri no esta bien:" + ex.getMessage());
      } catch (SQLException ex) {
        System.err.println("No no hemos podido conectar a la base de datos: " + ex.getMessage());
      } catch (NodeNotFound ex) {
        System.err.println("Nodo no existe en envio al sri: " + ex.getMessage());
      } catch (SAXException ex) {
        System.err.println("Error de XML existe en envio al sri: " + ex.getMessage());
      } catch (ParserConfigurationException ex) {
        System.err.println("Error de conversion al sri: " + ex.getMessage());
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