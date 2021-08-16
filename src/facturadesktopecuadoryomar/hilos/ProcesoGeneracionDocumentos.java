package facturadesktopecuadoryomar.hilos;

import com.ecuasis.coresri.excepciones.ClaveAccesoException;
import com.ecuasis.coresri.firma.XAdESBESSignature;
import com.ecuasis.coresri.util.UtilSRI;
import facturadesktopecuadoryomar.logica.ConfiguracionFacade;
import facturadesktopecuadoryomar.logica.DocumentoClienteFacade;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.logica.EmpresaFacade;
import facturadesktopecuadoryomar.logica.EnvioCorreoFacade;
import facturadesktopecuadoryomar.logica.MovimientoFacade;
import facturadesktopecuadoryomar.logica.NotificacionFacade;
import facturadesktopecuadoryomar.logica.TipoDocumentoFacade;
import facturadesktopecuadoryomar.logica.UsuarioFacade;
import facturadesktopecuadoryomar.logica.XmlSriAbstratc;
import facturadesktopecuadoryomar.logica.XmlSriFactura;
import facturadesktopecuadoryomar.logica.XmlSriFacturaExportacion;
import facturadesktopecuadoryomar.logica.XmlSriFacturaReembolso;
import facturadesktopecuadoryomar.logica.XmlSriGuiaRemision;
//import facturadesktopecuadoryomar.logica.XmlSriLiquidacionCompra;
import facturadesktopecuadoryomar.logica.XmlSriNotaCredito;
import facturadesktopecuadoryomar.logica.XmlSriNotaDebito;
import facturadesktopecuadoryomar.logica.XmlSriRetencion;
import facturadesktopecuadoryomar.modelo.cliente.Carga;
import facturadesktopecuadoryomar.modelo.servidor.Configuracion;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.MovimientoDocumento;
import facturadesktopecuadoryomar.modelo.servidor.Notificacion;
import facturadesktopecuadoryomar.modelo.servidor.ViewDocumento;
import facturadesktopecuadoryomar.utilidades.UtilFiles;
import oracle.fe.logica.FacturacionIntermediaFacade;
import oracle.fe.modelo.FacturacionIntermedia;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ProcesoGeneracionDocumentos {
  XmlSriAbstratc xmlFacade = null;
  DocumentoFacade documentoLogica = new DocumentoFacade();
  DocumentoClienteFacade datos = new DocumentoClienteFacade();
  List<Carga> listaCargas;
  private List<Documento> listaDocumentos;
  private List<ViewDocumento> listaDocumentosCorreo;
  private ConfiguracionFacade configuracionFacade = new ConfiguracionFacade();
  private EmpresaFacade empresaFacade = new EmpresaFacade();
  private Configuracion config = null;
  private Empresa empresa = null;
  XAdESBESSignature firmar = new XAdESBESSignature();  
  TipoDocumentoFacade tipoDocFacade = new TipoDocumentoFacade();
  FacturacionIntermediaFacade facIntLogica = new FacturacionIntermediaFacade();
  MovimientoFacade movFacade = new MovimientoFacade();
  UsuarioFacade usuarioFacade = new UsuarioFacade();
  NotificacionFacade notificacionFacade = new NotificacionFacade();

  public ProcesoGeneracionDocumentos() {
  }
 
  public void generarDocumentosCliente(Empresa emp) {
    this.empresa = emp;
    this.config = emp.getIdConfiguracion();
    for (
        FacturacionIntermedia x : this.facIntLogica.getPendientesByEmpresa(
            5000, emp.getCodigoInterno()
        )
    ) {
      boolean permite = permiteProcesar(
              emp, 
              this.tipoDocFacade.buscarPorCodigo(x.getTipoDoc())
                                .getCodigoTico(), 
              x.getDescripcionDoc()
      );
      System.out.println("DOCUMENTO A PROCESAR : ".concat(x.toString()));
      if (permite) {
        System.out.println(
            "DOCUMENTO SI PERMITE PROCESAR : ".concat(x.getNumeroDocumento())
        );
        Documento felec = new Documento();
        MovimientoDocumento mov = getMovimiento(felec, "CREACION", "");
        int error = 0;
        try {
          switch (x.getTipoDoc().trim()) {
            case "01":
              if (x.getDescripcionDoc().trim().equals("NO")) {
                this.xmlFacade = new XmlSriFactura(x);
              } else {
                if (x.getDescripcionDoc().trim().equals("EX")) {
                  this.xmlFacade = new XmlSriFacturaExportacion(x); break;
                } else {
                  if (x.getDescripcionDoc().trim().equals("RE")) {
                    this.xmlFacade = new XmlSriFacturaReembolso(x);
                  }
                }
              }
              break;

            
            case "04":this.xmlFacade = new XmlSriNotaCredito(x); break;
            case "05":this.xmlFacade = new XmlSriNotaDebito(x); break;
            case "06":this.xmlFacade = new XmlSriGuiaRemision(x); break;
            case "07":this.xmlFacade = new XmlSriRetencion(x); break;
            /*case "03":
              if (x.getDescripcionDoc().trim().equals("NO")) {
                xmlFacade = new XmlSriLiquidacionCompra(x);
              } else {
                if (x.getDescripcionDoc().trim().equals("RE")) {
                  xmlFacade = new XmlSriLiquidacionCompraReembolso(x);
                }
              }
              break;*/
          } 
        
        } catch (Exception ex) {
          felec.setNumeroDocu(x.getNumeroDocumento());
          felec.setTipoDocuCliente(x.getTipoDoc());
          felec.setFechaLlegadaDocu(new Date());
          felec.setEstadoClienteDocu("P");
          felec.setEstadoDocu("DEVUELTA");
          felec.setEstadoDMZ("P");
          felec.setCuentaClienteDocu(x.getIdDocumento());
          felec.setTipoDocuCliente(x.getCodCliente());
          felec.setDescripcionDoc(x.getDescripcionDoc());
          felec.setObservacionDocu(ex.getMessage());
          felec.setInformacionDocu(x.getTramaDoc());
          felec.setFechaEmisionDocu(x.getFechaEmision());
          felec.setCodigoTido(
            this.tipoDocFacade.buscarPorCodigo(x.getTipoDoc()).getCodigoTico()
          );
          mov.setObservacion(
            "ESTADO: ".concat(felec.getEstadoDocu()).concat(", Observación: ") 
            .concat(ex.getMessage())
          );
          
          Notificacion noti = new Notificacion(
            "Devuelta por el Sistema", "Se ha devuelto el documento " 
            .concat(felec.getNumeroDocu()), ""
          );
          noti.setCodigoSafpro(felec.getCuentaClienteDocu());
          noti.setTipoSafpro(felec.getTipoDocuCliente());
          noti.setNumeroDocumento(felec.getNumeroDocu());
          noti.setEstado("ACTIVO");
          noti.setClaveAcceso(felec.getClaveAccesoDocu());
          this.notificacionFacade.guardarNotificacion(
            noti, this.usuarioFacade.getUsuariosNotificacion()
          );
          
          error = 1;
          Logger.getLogger(
            NuevoDocumento.class.getName()).log(Level.SEVERE, null, ex
          );
        } 
        if (error == 0) {
          try {
            felec = this.xmlFacade.getDocumento();
            felec.setTipoDocuCliente(x.getCodCliente());
            felec.setDescripcionDoc(x.getDescripcionDoc());
          } catch (
                ClaveAccesoException|SQLException|facturadesktopecuadoryomar
                                                    .excepciones
                                                    .FormatoCargaException ex
            ) {
            felec = new Documento();
            felec.setTipoDocuCliente(x.getCodCliente());
            felec.setNumeroDocu(x.getNumeroDocumento());
            felec.setFechaLlegadaDocu(new Date());
            felec.setEstadoClienteDocu("P");
            felec.setEstadoDocu("DEVUELTA");
            felec.setEstadoDMZ("P");
            felec.setDescripcionDoc(x.getDescripcionDoc());
            felec.setInformacionDocu(x.getTramaDoc());
            felec.setFechaEmisionDocu(x.getFechaEmision());
            felec.setCodigoEmpr(
                this.empresaFacade.buscarEmpresaPorReferencia(x.getCompania())
            );
            felec.setCodigoTido(
                this.tipoDocFacade.buscarPorCodigo(x.getTipoDoc())
                                  .getCodigoTico()
            );
            felec.setObservacionDocu(ex.getMessage());
            mov.setObservacion(
                "ESTADO: ".concat(felec.getEstadoDocu())
                .concat(", Observación: ").concat(ex.getMessage())
            );
            Logger.getLogger(
                NuevoDocumento.class.getName()).log(Level.SEVERE, null, ex
            );
            felec.setCuentaClienteDocu(x.getIdDocumento());
            
            Notificacion noti = new Notificacion(
                "Devuelta por el Sistema", "Se ha devuelto el documento " 
                .concat(felec.getNumeroDocu()), ""
            );
            noti.setCodigoSafpro(felec.getCuentaClienteDocu());
            noti.setTipoSafpro(felec.getTipoDocuCliente());
            noti.setNumeroDocumento(felec.getNumeroDocu());
            noti.setEstado("ACTIVO");
            noti.setClaveAcceso(felec.getClaveAccesoDocu());
            this.notificacionFacade.guardarNotificacion(
                noti, this.usuarioFacade.getUsuariosNotificacion()
            );
          } 
        }
        try {
          felec.setFechaEmisionDocu(x.getFechaEmision());
          felec.setEstadoClienteDocu("P");
          felec.setEstadoDMZ("P");
          if (this.documentoLogica == null) {
            this.documentoLogica = new DocumentoFacade();
          }
          if (felec == null) {
            System.out.println("FELEC ESTA NULO");
          } else {
            if (felec.getEstadoDocu().equals("AUTORIZADO")) {
              mov.setObservacion(
                "ESTADO: ".concat(felec.getEstadoDocu()) 
                .concat(", Observación: Se ha creado sin ningún inconveniente")
              );
            }
            this.documentoLogica.guardar(felec, emp);
            mov.setClaveAccesoDocu(felec.getClaveAccesoDocu());
            this.movFacade.guardar(mov);
          } 
          x.setEstado("E");
          this.facIntLogica.modificar(x);
        } catch (SQLException ex) {
          Logger.getLogger(
            NuevoDocumento.class.getName()).log(Level.SEVERE, null, ex
          );
        }
      } else {
        System.out.println(
            "DOCUMENTO NO PERMITE PROCESAR : ".concat(x.getNumeroDocumento())
        );
      }
    } 
  }
  
  public void firmarDocumentos(Empresa emp) {
    this.empresa = emp;
    this.config = emp.getIdConfiguracion();
    for (
        Documento x : this.documentoLogica
                          .listaDocumentosPendientesFirma(1000, emp)
    ) {
      boolean permite = permiteProcesar(
        emp, x.getCodigoTido(), x.getDescripcionDoc()
      );
      
      if (permite) {
        MovimientoDocumento mov = getMovimiento(x, "FIRMADO", "");
        System.out.println(
            "EL DOCUMENTO A FIRMAR ES :".concat(x.getClaveAccesoDocu())
        );
        
        if (x.getXmlFirmDocu() != null && x.getFirmadoDocu() == false) {
          String tmp = this.config.getUrlTemporalConf();
          File archivoXML = new File(
            tmp.concat(File.separator).concat(x.getClaveAccesoDocu())
            .concat(".xml")
          );
          
          if (!archivoXML.exists()) {
            try {
              archivoXML.createNewFile();
            }
            catch (IOException ex) {
              Logger.getLogger(FirmarDocumento.class
                  .getName()).log(Level.SEVERE, null, ex);
            } 
          } else {
            try {
              archivoXML.delete();
              archivoXML.createNewFile();
            } catch (IOException ex) {
              Logger.getLogger(FirmarDocumento.class
                  .getName()).log(Level.SEVERE, null, ex);
            } 
          } 
          Document xmlFirmado = null;
          try {
            UtilFiles.EcribirFichero(archivoXML, x.getXmlFirmDocu());
            Thread.sleep(1000l);
            xmlFirmado = this.firmar.firmar(
                archivoXML.getAbsolutePath(), 
                this.config.getUrlFirmasConf().concat(File.separator) 
                    .concat(this.empresa.getFirmaEmpr()), 
                this.empresa.getClaveFirmaEmpr(), 
                tmp, 
                x.getClaveAccesoDocu().concat("firmado.XML")
            );
            Thread.sleep(1000l);
          } catch (IOException ex) {
            mov.setObservacion(
                "No se ha encontrado la FIRMA".concat(ex.getMessage())
            );
          }
          catch (TransformerException ex) {
            mov.setObservacion(
                "No se ha podido embeber la frma en el documento la FIRMA: " 
                .concat(ex.getMessage())
            );
          
          }
          catch (InterruptedException ex) {
            Logger.getLogger(FirmarDocumento.class
                .getName()).log(Level.SEVERE, null, ex);
          }
          catch (SAXException ex) {
            Logger.getLogger(FirmarDocumento.class
                .getName()).log(Level.SEVERE, null, ex);
          }
          catch (ParserConfigurationException ex) {
            Logger.getLogger(FirmarDocumento.class
                .getName()).log(Level.SEVERE, null, ex);
            mov.setObservacion(
                "Error al convertir información : ".concat(ex.getMessage())
            );
          }
          catch (CertificateException ex) {
            mov.setObservacion(
                "Error en el certficado: ".concat(ex.getMessage())
            );
            Logger.getLogger(FirmarDocumento.class
                .getName()).log(Level.SEVERE, null, ex);
          } 
          try {
            x.setXmlFirmDocu(UtilSRI.convertDocumentToString(xmlFirmado));
          }
          catch (TransformerException ex) {
            mov.setObservacion(
                "Error al tratar de cnvertir en string xml firmado: " 
                .concat(ex.getMessage())
            );
            Logger.getLogger(FirmarDocumento.class.getName())
                  .log(Level.SEVERE, null, ex);
          } 
          try {
            x.setFirmadoDocu(true);
            this.documentoLogica.modificar(x);
            System.out.println("Se ha modificado la firma en el documento");
          }
          catch (SQLException ex) {
            mov.setObservacion(
                "Error a modificar documento en SQL: ".concat(ex.getMessage())
            );
            Logger.getLogger(FirmarDocumento.class.getName())
                  .log(Level.SEVERE, null, ex);
          } 
          try {
            this.movFacade.guardar(mov);
          } catch (SQLException ex) {
            System.out.println("ERROR al guardar movimiento");
            Logger.getLogger(ProcesoGeneracionDocumentos.class.getName())
                  .log(Level.SEVERE, null, ex);
          } 
          archivoXML.delete();
        } 
      } 
    } 
    this.listaDocumentos = new LinkedList<>();
  }
  
  public void enviarDocumentosCliente() {
    this.listaDocumentos = this.documentoLogica.listaDocumentosPendientes(100);
    
    if (this.listaDocumentos.size() > 0) {
      for (Documento x : this.listaDocumentos) {
        MovimientoDocumento mov = getMovimiento(x, "ACT_CLIENTE", "");
        System.err.println(
            "DOCUMENTO A PROCESAR--: ".concat(x.getCodigoDocu().toString())
        );
        try {
          int error = 0;
          this.datos.actualizarCliente(x, 1);
          if (error != -1) {
            x.setEstadoClienteDocu("E");
          } else {
            x.setEstadoClienteDocu("X");
            mov.setObservacion(
                "Ha sucedido un inconveniente al tratar de actualizar en el "
                .concat("cliente.")
            );
          } 
          this.documentoLogica.modificar(x);
          this.movFacade.guardar(mov);
        } catch (SQLException ex) {
          Logger.getLogger(ActualizarCliente.class
              .getName()).log(Level.SEVERE, (String)null, ex);
        } 
      } 
    }
    this.listaDocumentos = new LinkedList<>();
  }
  
  public void enviarDocumentosAutorizadosCliente() {
    this.listaDocumentos = this.documentoLogica
                           .listaDocumentosAutorizadosPendientesCliente(100);
    
    if (this.listaDocumentos.size() > 0) {
      for (Documento x : this.listaDocumentos) {
        MovimientoDocumento mov = getMovimiento(x, "ACT_CLIENTE_AUT", "");
        System.err.println(
            "DOCUMENTO A PROCESAR A ACTUALIZAR AUTORIZADO  --: " 
            .concat(x.getCodigoDocu().toString())
        );
        
        try {
          int error = 0;
          switch (x.getEstadoDocu()) {
            case "RECHAZADO": this.datos.actualizarCliente(x, 4); break;
            case "AUTORIZADO": this.datos.actualizarCliente(x, 2); break;
          }
          
          if (error != -1) {
            x.setEstadoClienteDocu("A");
          } else {
            x.setEstadoClienteDocu("X");
            mov.setObservacion(
                "Ha sucedido un inconveniente al tratar de actualizar en el "
                .concat("cliente el doc. autorizado")
            );
          }
          
          this.documentoLogica.modificar(x);
          this.movFacade.guardar(mov);
        } catch (SQLException ex) {
          Logger.getLogger(ActualizarCliente.class.getName())
                .log(Level.SEVERE, null, ex);
        } 
      } 
    }
    this.listaDocumentos = new LinkedList<>();
  }
  
  public void enviarCorreos(Empresa emp) {
    this.empresa = emp;
    this.config = emp.getIdConfiguracion();
    this.listaDocumentosCorreo = this.documentoLogica
                                     .listaDocumentosEnvioCorreo(50, emp);
    for (ViewDocumento x : this.listaDocumentosCorreo) {
      boolean permite = permiteProcesar(
        emp, x.getCodigoTico(), x.getDescripcionDoc()
      );
      
      if (permite) {
        Documento doc = this.documentoLogica.buscarPorCodigo(x.getCodigoDocu());
        MovimientoDocumento mov = getMovimiento(doc, "CORREO", "");
        System.out.println(
            "*********************INICIO DE ENVIO DE CORREOS A:" 
            .concat(x.getCorreoDocu()).concat(" ************************")
        );
        
        if (x.getCorreoDocu() != null) {
          if (x.getCorreoDocu().trim().length() > 5) {
            try {
              if (doc.getFirmadoDocu() == true) {
                EnvioCorreoFacade envioFacade = new EnvioCorreoFacade(
                    this.config, this.empresa
                );
                envioFacade.enviarCorreo(x);
                
                if (x.getCorreoDocu() != null) {
                  doc.setEstadoCorreoDocu("ENVIADO");
                  doc.setFechaEnvioCorreoDocu(new Date());
                  mov.setObservacion("Correo enviado.");
                } else {
                  doc.setEstadoCorreoDocu("NO_ENVIADO");
                  mov.setObservacion("El correo no se ha podido enviar.");
                } 
                this.documentoLogica.modificar(doc);
              }
            
            } catch (Exception ex) {
              doc.setEstadoCorreoDocu("NO_ENVIADO");
              mov.setObservacion(
                "El correo no se ha podido enviar.".concat(ex.getMessage())
              );
              
              try {
                this.documentoLogica.modificar(doc);
              } catch (SQLException ex1) {
                Logger.getLogger(ProcesoGeneracionDocumentos.class.getName())
                      .log(Level.SEVERE, null, ex1);
              }
              Logger.getLogger(EnviarCorreo.class.getName())
                    .log(Level.SEVERE, null, ex);
              mov.setObservacion("Error: ".concat(ex.getMessage()));
            }
            
            try {
              Thread.sleep(5000l);
            }
            catch (InterruptedException ex) {
              Logger.getLogger(EnvioCorreoDocumentos.class.getName())
                    .log(Level.SEVERE, null, ex);
            }
          } else {
            mov.setObservacion("No tiene correos disponibles para enviar.");
            System.err.println("NO HAY CORREO: ".concat(x.getCorreoUsua()));
          } 
        } else {
          mov.setObservacion("No tiene correos disponibles para enviar.");
        } 
        try {
          this.movFacade.guardar(mov);
        } catch (SQLException ex) {
          Logger.getLogger(ProcesoGeneracionDocumentos.class.getName())
                .log(Level.SEVERE, null, ex);
        } 
        System.out.println("*********************************************");
      } 
    } 
    this.listaDocumentosCorreo = new LinkedList<>();
  }
  
  public MovimientoDocumento getMovimiento(
    Documento doc, String tipo, String observacion
  ) {
    MovimientoDocumento mov = new MovimientoDocumento();
    mov.setClaveAccesoDocu(doc.getClaveAccesoDocu());
    mov.setFecha(new Date());
    mov.setTipo(tipo);
    mov.setUsuario("sistema");
    mov.setObservacion(observacion);
    return mov;
  }
  
  private boolean permiteProcesar(
    Empresa emp, Integer tipoDocumento, String descripcion
  ) {
    boolean procesado = false;
    switch (tipoDocumento) {
      case 1:
        if (descripcion.trim().equals("NO")) {
          procesado = Objects.equals(
            emp.getEstadoServicioFactura(), Boolean.TRUE
          );
        } else {
          if (descripcion.trim().equals("EX")) {
            procesado = Objects.equals(
                emp.getEstadoServicioExportacion(), Boolean.TRUE
            );
          } else {
            if (descripcion.trim().equals("RE")) {
              procesado = Objects.equals(
                emp.getEstadoServicioReembolso(), Boolean.TRUE
              );
            }
          }
        }
        break;
      case 2:
        procesado = Objects.equals(
            emp.getEstadoServicioNotaCredito(), Boolean.TRUE
        );
        break;
      case 3:
        procesado = Objects.equals(
            emp.getEstadoServicioNotaDebito(), Boolean.TRUE
        );
        break;
      case 4:
        procesado = Objects.equals(emp.getEstadoServicioGuia(), Boolean.TRUE);
        break;
      case 5:
        procesado = Objects.equals(
            emp.getEstadoServicioRetencion(), Boolean.TRUE
        );
        break;
      case 6:
        procesado = true;
        break;
    } 
    return procesado;
  }
}