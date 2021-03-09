/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturadesktopecuadoryomar.hilos;

import com.ecuasis.coresri.excepciones.NodeNotFound;
import com.ecuasis.coresri.modelo.sri.envio.Mensaje;
import com.ecuasis.coresri.modelo.sri.envio.RespuestaRecepcionComprobante;
import com.ecuasis.coresri.modelo.sri.recepcion.Autorizacion;
import com.ecuasis.coresri.modelo.sri.recepcion.RespuestaAutorizacionComprobante;
import com.ecuasis.coresri.servicios.ServiciosSRI;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.logica.MovimientoFacade;
import facturadesktopecuadoryomar.logica.NotificacionFacade;
import facturadesktopecuadoryomar.logica.TipoDocumentoFacade;
import facturadesktopecuadoryomar.logica.UsuarioFacade;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.MovimientoDocumento;
import facturadesktopecuadoryomar.modelo.servidor.Notificacion;
import facturadesktopecuadoryomar.utilidades.StateEntity;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Usuario
 */
public class ProcesoSRI {

    private DocumentoFacade documentoLogica = new DocumentoFacade();
    private List<Documento> listaDocumentos;
    private ServiciosSRI sri;
    private MovimientoFacade movFacade = new MovimientoFacade();
    UsuarioFacade usuarioFacade = new UsuarioFacade();
    NotificacionFacade notificacionFacade = new NotificacionFacade();

    public ProcesoSRI(ServiciosSRI sri) {
        this.sri = sri;
    }

    public void enviarDocumentosSRI() {
        Calendar c = new GregorianCalendar(2017, 6, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        this.listaDocumentos = documentoLogica.listaDocumentosPendientesEnvioPorFechaEmision(1000, c.getTime());
        for (Documento x : listaDocumentos) {
            try {
                System.out.println("PREPARANDO DOCUMENTO PARA ENVIAR SRI:" + x.getNumeroDocu());
                RespuestaRecepcionComprobante recepcion = sri.enviarDocumento(x.getXmlFirmDocu());
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

    public void recibirDocumentosSRI() {
        this.listaDocumentos = documentoLogica.listaDocumentosEnviados(100);
        System.out.println("EMPEZANDO CON EL PORCESO DE RECEPCION DE DOCUMENTOS SRI");
        for (Documento x : listaDocumentos) {
            try {
                System.out.println("VALIDANDO DOCUMENTO: " + x.getNumeroDocu());
                RespuestaAutorizacionComprobante autorizacion = sri.comprobarAutorizacionSimple(x.getClaveAccesoDocu());
                String error = "";
                if (autorizacion.getAutorizaciones() != null) {
                    if (autorizacion.getAutorizaciones().getAutorizacion() != null) {
                        Autorizacion auth = autorizacion.getAutorizaciones().getAutorizacion().get(0);
                        if (auth != null) {
                            if (auth.getMensajes() != null) {
                                if (auth.getMensajes().getMensaje() != null) {
                                    if (auth.getMensajes().getMensaje().size() > 0) {
                                        com.ecuasis.coresri.modelo.sri.recepcion.Mensaje msg = auth.getMensajes().getMensaje().get(0);
                                        error += "Tipo:" + msg.getTipo() + ", Identificador:" + msg.getIdentificador() + ", Mensaje:" + msg.getMensaje() + ", Informacion Adicional:";
                                    }
                                }
                            }

                            if (auth.getEstado().equals("NO AUTORIZADO") || auth.getEstado().equals("RECHAZADO")) {
                                x.setEstadoDocu("RECHAZADO");
                                System.out.println("se ha rechazado el documento: " + x.getNumeroDocu());
                            } else {
                                if (auth.getEstado().equals("AUTORIZADO")) {
                                    x.setNumeroAutorizacionDocu(auth.getNumeroAutorizacion());
                                    x.setFechaAutorizacionDocu(auth.getFechaAutorizacion());
                                    x.setXmlFirmDocu(auth.getComprobante());
                                }
                                x.setEstadoDocu(auth.getEstado());
                                System.out.println("se ha autorizado el documento: " + x.getNumeroDocu());
                            }
                            x.setObservacionDocu(error);
                            this.documentoLogica.modificar(x);
                            System.out.println("se ha modificado autorizacion: " + x.getNumeroDocu());
                        }
                    }
                } else {
                    if (x.getEstadoDocu().equals("EN PROCESO")) {
                        x.setMensajeDocu(null);
                        x.setObservacionDocu(null);
                        x.setEstadoDocu("AUTORIZADO");
                        this.documentoLogica.modificar(x);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NodeNotFound ex) {
                Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.listaDocumentos = new LinkedList<>();
    }

    public void enviarDocumentosSRI(Empresa emp) {
        Calendar c = new GregorianCalendar(2017, 6, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        this.listaDocumentos = documentoLogica.listaDocumentosPendientesEnvio(5000, emp, c.getTime());
        for (Documento x : listaDocumentos) {
            boolean permite = this.permiteProcesar(emp, x.getCodigoTido(), x.getDescripcionDoc());
            if (permite) {
                MovimientoDocumento mov = this.getMovimiento(x, "ENVIO_SRI", "");
                try {
                    System.out.println("PREPARANDO DOCUMENTO PARA ENVIAR SRI:" + x.getNumeroDocu());
                    RespuestaRecepcionComprobante recepcion = sri.enviarDocumento(x.getXmlFirmDocu());
                    if (recepcion.getEstado().equals("RECIBIDA")) {
                        //x.setEstadoDocu(StateEntity.ENVIADO.name());
                        x.setMensajeDocu("ENVIADOSRI");
                        mov.setObservacion("RECIBIDA POR EL SRI");
                        System.out.println("ENVIADO AL SRI: " + x.getNumeroDocu());
                    } else {
                        String error = "";
                        for (Mensaje msg : recepcion.getComprobantes().getComprobante().get(0).getMensajes().getMensaje()) {
                            error += "Estado:" + recepcion.getEstado() + "Tipo:" + msg.getTipo() + ", Identificador:" + msg.getIdentificador() + ", Mensaje:" + msg.getMensaje() + ", Informacion Adicional:" + msg.getInformacionAdicional();
                        }
                        x.setEstadoDocu(StateEntity.DEVUELTA.name());
                        x.setObservacionDocu(error);
                        mov.setObservacion(error);
                        System.out.println("ERROR DEL DOCUMENTO ENVIADO AL SRI: " + x.getNumeroDocu());

                        Notificacion noti = new Notificacion("Devuelta por el SRI", "Se ha devuelto el documento " + x.getNumeroDocu(), "");
                        noti.setCodigoSafpro(x.getCuentaClienteDocu());
                        noti.setTipoSafpro(x.getTipoDocuCliente());
                        noti.setNumeroDocumento(x.getNumeroDocu());
                        noti.setEstado("ACTIVO");
                        noti.setClaveAcceso(x.getClaveAccesoDocu());
                        notificacionFacade.guardarNotificacion(noti, usuarioFacade.getUsuariosNotificacion());
                    }
                    x.setFechaEnvioDocu(new Date());
                    this.documentoLogica.modificar(x);
                    movFacade.guardar(mov);
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
        }
        this.listaDocumentos = new LinkedList<>();

    }

    public void recibirDocumentosSRI(Empresa emp) {
        this.listaDocumentos = documentoLogica.listaDocumentosEnviados(5000, emp);
        this.listaDocumentos.addAll(documentoLogica.listaDocumentosClaveAccesoRegistrada(5000, emp));
        System.out.println("EMPEZANDO CON EL PORCESO DE RECEPCION DE DOCUMENTOS SRI");
        for (Documento x : listaDocumentos) {
            boolean permite = this.permiteProcesar(emp, x.getCodigoTido(), x.getDescripcionDoc());
            if (permite) {
                try {
                    MovimientoDocumento mov = this.getMovimiento(x, "ENVIO_SRI", "");
                    System.out.println("VALIDANDO DOCUMENTO: " + x.getNumeroDocu());
                    RespuestaAutorizacionComprobante autorizacion = sri.comprobarAutorizacionSimple(x.getClaveAccesoDocu());
                    String error = "";
                    if (autorizacion.getAutorizaciones() != null) {
                        if (autorizacion.getAutorizaciones().getAutorizacion() != null) {
                            Autorizacion auth = autorizacion.getAutorizaciones().getAutorizacion().get(0);
                            if (auth != null) {
                                if (auth.getMensajes() != null) {
                                    if (auth.getMensajes().getMensaje() != null) {
                                        if (auth.getMensajes().getMensaje().size() > 0) {
                                            com.ecuasis.coresri.modelo.sri.recepcion.Mensaje msg = auth.getMensajes().getMensaje().get(0);
                                            //error += "Tipo:" + msg.getTipo() + ", Identificador:" + msg.getIdentificador() + ", Mensaje:" + msg.getMensaje() + ", Informacion Adicional:"+msg.getInformacionAdicional();
                                            error += "Tipo:" + msg.getTipo() + ", Identificador:" + msg.getIdentificador() + ", Mensaje:" + msg.getMensaje();
                                        }
                                    }
                                }

                                if (auth.getEstado().equals("NO AUTORIZADO") || auth.getEstado().equals("RECHAZADO")) {
                                    x.setEstadoDocu("RECHAZADO");
                                    mov.setObservacion("Documento Rechazado por: " + error);
                                    System.out.println("se ha rechazado el documento: " + x.getNumeroDocu());

                                    Notificacion noti = new Notificacion("Rechazado por el SRI", "Se ha rechazado el documento " + x.getNumeroDocu(), "");
                                    noti.setCodigoSafpro(x.getCuentaClienteDocu());
                                    noti.setTipoSafpro(x.getTipoDocuCliente());
                                    noti.setNumeroDocumento(x.getNumeroDocu());
                                    noti.setEstado("ACTIVO");
                                    noti.setClaveAcceso(x.getClaveAccesoDocu());
                                    notificacionFacade.guardarNotificacion(noti, usuarioFacade.getUsuariosNotificacion());
                                } else {
                                    if (auth.getEstado().equals("AUTORIZADO")) {
                                        x.setNumeroAutorizacionDocu(auth.getNumeroAutorizacion());
                                        x.setFechaAutorizacionDocu(auth.getFechaAutorizacion());
                                        x.setXmlFirmDocu(auth.getComprobante());
                                        mov.setObservacion("Documento Autorizado");
                                    }
                                    x.setEstadoDocu(auth.getEstado());
                                    System.out.println("se ha autorizado el documento: " + x.getNumeroDocu());
                                }
                                x.setObservacionDocu(error);
                                this.documentoLogica.modificar(x);
                                movFacade.guardar(mov);
                                System.out.println("se ha modificado autorizacion: " + x.getNumeroDocu());
                            }
                        }
                    } else {
                        if (x.getEstadoDocu().equals("EN PROCESO")) {
                            x.setMensajeDocu(null);
                            x.setObservacionDocu(null);
                            x.setEstadoDocu("AUTORIZADO");
                            this.documentoLogica.modificar(x);
                            mov.setObservacion("Documento se encontro en proceso por lo cual se ha modificado para reenviarse nuevamente.");
                            movFacade.guardar(mov);
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JAXBException ex) {
                    Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NodeNotFound ex) {
                    Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(RecepcionDocumento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        this.listaDocumentos = new LinkedList<>();
    }

    public MovimientoDocumento getMovimiento(Documento doc, String tipo, String observacion) {
        MovimientoDocumento mov = new MovimientoDocumento();
        mov.setClaveAccesoDocu(doc.getClaveAccesoDocu());
        mov.setFecha(new Date());
        mov.setTipo(tipo);
        mov.setUsuario("sistema");
        mov.setObservacion(observacion);
        return mov;
    }

    private boolean permiteProcesar(Empresa emp, Integer tipoDocumento, String descripcion) {
        boolean procesado = false;
        switch (tipoDocumento) {
            case 1:
                if (descripcion.trim().equals("NO")) {
                    procesado = Objects.equals(emp.getEstadoServicioFactura(), Boolean.TRUE);
                } else {
                    if (descripcion.trim().equals("EX")) {
                        procesado = Objects.equals(emp.getEstadoServicioExportacion(), Boolean.TRUE);
                    } else {
                        if (descripcion.trim().equals("RE")) {
                            procesado = Objects.equals(emp.getEstadoServicioReembolso(), Boolean.TRUE);
                        }
                    }
                }
                break;
            case 2:
                procesado = Objects.equals(emp.getEstadoServicioNotaCredito(), Boolean.TRUE);
                break;
            case 3:
                procesado = Objects.equals(emp.getEstadoServicioNotaDebito(), Boolean.TRUE);
                break;
            case 4:
                procesado = Objects.equals(emp.getEstadoServicioGuia(), Boolean.TRUE);
                break;
            case 5:
                procesado = Objects.equals(emp.getEstadoServicioRetencion(), Boolean.TRUE);
                break;
            case 6:
                procesado = true;
                break;
        }
        return procesado;
    }

}
