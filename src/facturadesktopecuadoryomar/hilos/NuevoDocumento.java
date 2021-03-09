package facturadesktopecuadoryomar.hilos;

import com.ecuasis.coresri.excepciones.ClaveAccesoException;
import facturadesktopecuadoryomar.excepciones.FormatoCargaException;
import facturadesktopecuadoryomar.logica.DocumentoClienteFacade;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.logica.TipoDocumentoFacade;
import facturadesktopecuadoryomar.logica.XmlSriAbstratc;
import facturadesktopecuadoryomar.modelo.cliente.Carga;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NuevoDocumento extends Thread {
  XmlSriAbstratc xmlFacade = null;
  DocumentoFacade documentoLogica = new DocumentoFacade();
  DocumentoClienteFacade datos = new DocumentoClienteFacade();  
  List<Carga> listaCargas;
  TipoDocumentoFacade tipoDocFacade = new TipoDocumentoFacade();
  
  public NuevoDocumento(List<Carga> listaCargas, ThreadGroup group, String name) {
    super(group, name);
    this.listaCargas = listaCargas;
  }
  
  @Override
  public void run() {
    for (Carga x : listaCargas) {
            Documento felec = new Documento();
            System.out.println("SE ESTA PROCESANDO: " + x.getNumeroDocumento());
            int error = 0;
            try {
//                switch (x.getTipoDocumento()) {
//                    case "01":
//                        xmlFacade = new XmlSriFacturaExportacion(x);
//                        break;
//                    case "04":
//                        xmlFacade = new XmlSriNotaCredito(x);
//                        break;
//                    case "05":
//                        xmlFacade = new XmlSriNotaDebito(x);
//                        break;
//                    case "06":
//                        xmlFacade = new XmlSriGuiaRemision(x);
//                        break;
//                    case "07":
//                        xmlFacade = new XmlSriRetencion(x);
//                        break;
//                }
            } catch (Exception ex) {
                felec.setNumeroDocu(x.getNumeroDocumento());
                felec.setTipoDocuCliente(x.getTipo());
                felec.setFechaLlegadaDocu(new Date());
                felec.setEstadoClienteDocu("P");
                felec.setEstadoDocu("DEVUELTA");
                 felec.setEstadoDMZ("P");
                felec.setObservacionDocu(ex.getMessage());
                felec.setInformacionDocu(x.getInformacion());
                felec.setFechaEmisionDocu(x.getFechaEmision());
                if (!ex.getMessage().contains("FC-03")) {
                    felec.setCodigoTido(this.tipoDocFacade.buscarPorCodigo(x.getTipoDocumento()).getCodigoTico());
                }
                error = 1;
                Logger.getLogger(NuevoDocumento.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (error == 0) {
                try {
                    felec = xmlFacade.getDocumento();
                    felec.setTipoDocuCliente(x.getTipo());
                } catch (ClaveAccesoException | SQLException | FormatoCargaException ex) {
                    felec = new Documento();
                    felec.setTipoDocuCliente(x.getTipo());
                    felec.setNumeroDocu(x.getNumeroDocumento());
                    felec.setFechaLlegadaDocu(new Date());
                    felec.setEstadoClienteDocu("P");
                    felec.setEstadoDocu("DEVUELTA");
                     felec.setEstadoDMZ("P");
                    felec.setInformacionDocu(x.getInformacion());
                    felec.setFechaEmisionDocu(x.getFechaEmision());
                    felec.setCodigoTido(this.tipoDocFacade.buscarPorCodigo(x.getTipoDocumento()).getCodigoTico());
                    felec.setObservacionDocu(ex.getMessage());
                    Logger.getLogger(NuevoDocumento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                felec.setFechaEmisionDocu(x.getFechaEmision());
                felec.setEstadoClienteDocu("P");
                felec.setEstadoDMZ("P");
                documentoLogica.guardar(felec);
                
                datos.actualizarCargaEnviada(x);
            } catch (SQLException ex) {
                Logger.getLogger(NuevoDocumento.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.xmlFacade = null;
    this.documentoLogica = null;
    this.datos = null;
    this.listaCargas = null;
    this.tipoDocFacade = null;
  }
}