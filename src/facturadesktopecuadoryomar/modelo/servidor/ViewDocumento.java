package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.TypeConverter;

@Entity
@Table(name = "view_documento", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "ViewDocumento.findAll", query = "SELECT v FROM ViewDocumento v"), 
  @NamedQuery(name = "ViewDocumento.findEnvioCorreo", query = "SELECT d FROM ViewDocumento d WHERE ((d.estadoCorreoDocu='PENDIENTE' or d.estadoCorreoDocu is null) AND d.estadoDocu='AUTORIZADO' AND d.fechaAutorizacionDocu IS NOT NULL AND d.numeroAutorizacionDocu IS NOT NULL AND (d.firmadoDocu=1 or d.firmadoDocu=TRUE) AND (d.correoDocu IS  NOT NULL AND TRIM(d.correoDocu)!=''))"),
  @NamedQuery(name = "ViewDocumento.findEnvioCorreoPorEmpresa", query = "SELECT d FROM ViewDocumento d WHERE ((d.estadoCorreoDocu='PENDIENTE' or d.estadoCorreoDocu is null) AND d.estadoDocu='AUTORIZADO' AND d.fechaAutorizacionDocu IS NOT NULL) AND d.codigoInterno=:empresa AND d.correoDocu is not null and d.correoDocu!='null'"), 
  @NamedQuery(name = "ViewDocumento.findByCodigoDocu", query = "SELECT v FROM ViewDocumento v WHERE v.codigoDocu = :codigoDocu"), 
  @NamedQuery(name = "ViewDocumento.findByNumeroDocu", query = "SELECT v FROM ViewDocumento v WHERE v.numeroDocu = :numeroDocu"), 
  @NamedQuery(name = "ViewDocumento.findByInformacionDocu", query = "SELECT v FROM ViewDocumento v WHERE v.informacionDocu = :informacionDocu"), 
  @NamedQuery(name = "ViewDocumento.findByClaveAccesoDocu", query = "SELECT v FROM ViewDocumento v WHERE v.claveAccesoDocu = :claveAccesoDocu"), 
  @NamedQuery(name = "ViewDocumento.findByNumeroAutorizacionDocu", query = "SELECT v FROM ViewDocumento v WHERE v.numeroAutorizacionDocu = :numeroAutorizacionDocu"), 
  @NamedQuery(name = "ViewDocumento.findByFechaAutorizacionDocu", query = "SELECT v FROM ViewDocumento v WHERE v.fechaAutorizacionDocu = :fechaAutorizacionDocu"), 
  @NamedQuery(name = "ViewDocumento.findByGuiaRemisionDocu", query = "SELECT v FROM ViewDocumento v WHERE v.guiaRemisionDocu = :guiaRemisionDocu"), 
  @NamedQuery(name = "ViewDocumento.findByMensajeDocu", query = "SELECT v FROM ViewDocumento v WHERE v.mensajeDocu = :mensajeDocu"), 
  @NamedQuery(name = "ViewDocumento.findByDocSustentoDocu", query = "SELECT v FROM ViewDocumento v WHERE v.docSustentoDocu = :docSustentoDocu"), 
  @NamedQuery(name = "ViewDocumento.findByObservacionDocu", query = "SELECT v FROM ViewDocumento v WHERE v.observacionDocu = :observacionDocu"), 
  @NamedQuery(name = "ViewDocumento.findByEstadoDocu", query = "SELECT v FROM ViewDocumento v WHERE v.estadoDocu = :estadoDocu"), 
  @NamedQuery(name = "ViewDocumento.findByFechaEmisionDocu", query = "SELECT v FROM ViewDocumento v WHERE v.fechaEmisionDocu = :fechaEmisionDocu"), 
  @NamedQuery(name = "ViewDocumento.findByNombreCompletoUsua", query = "SELECT v FROM ViewDocumento v WHERE v.nombreCompletoUsua = :nombreCompletoUsua"), 
  @NamedQuery(name = "ViewDocumento.findByIdentificacionUsua", query = "SELECT v FROM ViewDocumento v WHERE v.identificacionUsua = :identificacionUsua"), 
  @NamedQuery(name = "ViewDocumento.findByNombreTico", query = "SELECT v FROM ViewDocumento v WHERE v.nombreTico = :nombreTico"), 
  @NamedQuery(name = "ViewDocumento.findByCodigoTico", query = "SELECT v FROM ViewDocumento v WHERE v.codigoTico = :codigoTico"), 
  @NamedQuery(name = "ViewDocumento.findByIdentificadorTico", query = "SELECT v FROM ViewDocumento v WHERE v.identificadorTico = :identificadorTico"), 
  @NamedQuery(name = "ViewDocumento.findByCorreoUsua", query = "SELECT v FROM ViewDocumento v WHERE v.correoUsua = :correoUsua")
})
public class ViewDocumento implements Serializable {
  @Id
  @Column(name = "codigo_docu")
  private Long codigoDocu;
  @Column(name = "numero_docu")
  private String numeroDocu;
  @Column(name = "informacion_docu")
  private String informacionDocu;
  @Column(name = "xml_firm_docu")
  private String xmlFirmDocu;
  @Column(name = "clave_acceso_docu")
  private String claveAccesoDocu;
  @Column(name = "numero_autorizacion_docu")
  private String numeroAutorizacionDocu;
  @Column(name = "fecha_autorizacion_docu")
  @Temporal(TemporalType.DATE)
  private Date fechaAutorizacionDocu;
  @Column(name = "guia_remision_docu")
  private String guiaRemisionDocu;
  @Column(name = "mensaje_docu")
  private String mensajeDocu;
  @Column(name = "doc_sustento_docu")
  private String docSustentoDocu;
  @Column(name = "observacion_docu")
  private String observacionDocu;
  @Column(name = "estado_docu")
  private String estadoDocu;
  @Column(name = "fecha_emision_docu")
  @Temporal(TemporalType.DATE)
  private Date fechaEmisionDocu;
  @Column(name = "nombre_completo_usua")
  private String nombreCompletoUsua;
  @Column(name = "identificacion_usua")
  private String identificacionUsua;
  @Column(name = "nombre_tico")
  private String nombreTico;
  @Column(name = "codigo_tico")
  private Integer codigoTico;
  @Column(name = "codigo_empr")
  private Integer codigoEmpr;
  @Column(name = "identificador_tico")
  private String identificadorTico;
  @Column(name = "correo_usua")
  private String correoUsua;
  @Column(name = "cuenta_cliente_docu")
  private String cuentaClienteDocu;
  @Column(name = "estado_correo_docu")
  private String estadoCorreoDocu;
  @Column(name = "url_ride_docu")
  private String urlRideDocu;
  @Column(name = "path_ride_docu")
  private String pathRideDocu;
  @Column(name = "correo_docu")
  private String correoDocu;
  @Column(name = "fecha_envio_correo_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEnvioCorreoDocu;
  @Column(name = "firmado_docu")
  @TypeConverter(name = "", dataType = Integer.class)
  private Boolean firmadoDocu;
  @Column(name = "iva_actual")
  private Float ivaActual;
  @Column(name = "tipo_doc_cliente")
  private String tipoDocCliente;
  @Column(name = "descripcion_doc")
  private String descripcionDoc;
  @Column(name = "codigo_interno")
  private String codigoInterno;
  @Column(name = "nombre_comercial_empr")
  private String nombreComercialEmpr;
  
  public Long getCodigoDocu() {
    return this.codigoDocu;
  }
  
  public void setCodigoDocu(Long codigoDocu) {
    this.codigoDocu = codigoDocu;
  }
  
  public String getNumeroDocu() {
    return this.numeroDocu;
  }
  
  public void setNumeroDocu(String numeroDocu) {
    this.numeroDocu = numeroDocu;
  }
  
  public String getInformacionDocu() {
    return this.informacionDocu;
  }
  
  public void setInformacionDocu(String informacionDocu) {
    this.informacionDocu = informacionDocu;
  }
  
  public String getXmlFirmDocu() {
    return this.xmlFirmDocu;
  }
  
  public void setXmlFirmDocu(String xmlDocu) {
    this.xmlFirmDocu = xmlDocu;
  }
  
  public String getClaveAccesoDocu() {
    return this.claveAccesoDocu;
  }
  
  public void setClaveAccesoDocu(String claveAccesoDocu) {
    this.claveAccesoDocu = claveAccesoDocu;
  }
  
  public String getNumeroAutorizacionDocu() {
    return this.numeroAutorizacionDocu;
  }
  
  public void setNumeroAutorizacionDocu(String numeroAutorizacionDocu) {
    this.numeroAutorizacionDocu = numeroAutorizacionDocu;
  }
  
  public Date getFechaAutorizacionDocu() {
    return this.fechaAutorizacionDocu;
  }
  
  public void setFechaAutorizacionDocu(Date fechaAutorizacionDocu) {
    this.fechaAutorizacionDocu = fechaAutorizacionDocu;
  }
  
  public String getGuiaRemisionDocu() {
    return this.guiaRemisionDocu;
  }
  
  public void setGuiaRemisionDocu(String guiaRemisionDocu) {
    this.guiaRemisionDocu = guiaRemisionDocu;
  }
  
  public String getMensajeDocu() {
    return this.mensajeDocu;
  }
  
  public void setMensajeDocu(String mensajeDocu) {
    this.mensajeDocu = mensajeDocu;
  }
  
  public String getDocSustentoDocu() {
    return this.docSustentoDocu;
  }
  
  public void setDocSustentoDocu(String docSustentoDocu) {
    this.docSustentoDocu = docSustentoDocu;
  }
  
  public String getObservacionDocu() {
    return this.observacionDocu;
  }
  
  public void setObservacionDocu(String observacionDocu) {
    this.observacionDocu = observacionDocu;
  }
  
  public String getEstadoDocu() {
    return this.estadoDocu;
  }
  
  public void setEstadoDocu(String estadoDocu) {
    this.estadoDocu = estadoDocu;
  }
  
  public Date getFechaEmisionDocu() {
    return this.fechaEmisionDocu;
  }
  
  public void setFechaEmisionDocu(Date fechaEmisionDocu) {
    this.fechaEmisionDocu = fechaEmisionDocu;
  }
  
  public String getNombreCompletoUsua() {
    return this.nombreCompletoUsua;
  }
  
  public void setNombreCompletoUsua(String nombreCompletoUsua) {
    this.nombreCompletoUsua = nombreCompletoUsua;
  }
  
  public String getIdentificacionUsua() {
    return this.identificacionUsua;
  }
  
  public void setIdentificacionUsua(String identificacionUsua) {
    this.identificacionUsua = identificacionUsua;
  }
  
  public String getNombreTico() {
    return this.nombreTico;
  }
  
  public void setNombreTico(String nombreTico) {
    this.nombreTico = nombreTico;
  }
  
  public Integer getCodigoTico() {
    return this.codigoTico;
  }
  
  public void setCodigoTico(Integer codigoTico) {
    this.codigoTico = codigoTico;
  }
  
  public String getIdentificadorTico() {
    return this.identificadorTico;
  }
  
  public void setIdentificadorTico(String identificadorTico) {
    this.identificadorTico = identificadorTico;
  }
  
  public String getCorreoUsua() {
    return this.correoUsua;
  }
  
  public void setCorreoUsua(String correoUsua) {
    this.correoUsua = correoUsua;
  }
  
  public String getCuentaClienteDocu() {
    return this.cuentaClienteDocu;
  }
  
  public void setCuentaClienteDocu(String cuentaClienteDocu) {
    this.cuentaClienteDocu = cuentaClienteDocu;
  }
  
  public String getEstadoCorreoDocu() {
    return this.estadoCorreoDocu;
  }
  
  public void setEstadoCorreoDocu(String estadoCorreoDocu) {
    this.estadoCorreoDocu = estadoCorreoDocu;
  }
  
  public String getUrlRideDocu() {
    return this.urlRideDocu;
  }
  
  public void setUrlRideDocu(String urlRideDocu) {
    this.urlRideDocu = urlRideDocu;
  }
  
  public String getPathRideDocu() {
    return this.pathRideDocu;
  }
  
  public void setPathRideDocu(String pathRideDocu) {
    this.pathRideDocu = pathRideDocu;
  }
  
  public String getCorreoDocu() {
    return this.correoDocu;
  }
  
  public void setCorreoDocu(String correoDocu) {
    this.correoDocu = correoDocu;
  }
  
  public Date getFechaEnvioCorreoDocu() {
    return this.fechaEnvioCorreoDocu;
  }
  
  public void setFechaEnvioCorreoDocu(Date fechaEnvioCorreoDocu) {
    this.fechaEnvioCorreoDocu = fechaEnvioCorreoDocu;
  }
  
  public Boolean getFirmadoDocu() {
    return this.firmadoDocu;
  }
  
  public void setFirmadoDocu(Boolean firmadoDocu) {
    this.firmadoDocu = firmadoDocu;
  }
  
  public Integer getCodigoEmpr() {
    return this.codigoEmpr;
  }
  
  public void setCodigoEmpr(Integer codigoEmpr) {
    this.codigoEmpr = codigoEmpr;
  }
  
  public Float getIvaActual() {
    return this.ivaActual;
  }
  
  public void setIvaActual(Float ivaActual) {
    this.ivaActual = ivaActual;
  }
  
  public String getTipoDocCliente() {
    return this.tipoDocCliente;
  }
  
  public void setTipoDocCliente(String tipoDocCliente) {
    this.tipoDocCliente = tipoDocCliente;
  }
  
  public String getDescripcionDoc() {
    return this.descripcionDoc;
  }
  
  public void setDescripcionDoc(String descripcionDoc) {
    this.descripcionDoc = descripcionDoc;
  }
  
  public String getCodigoInterno() {
    return this.codigoInterno;
  }
  
  public void setCodigoInterno(String codigoInterno) {
    this.codigoInterno = codigoInterno;
  }
  
  public String getNombreComercialEmpr() {
    return this.nombreComercialEmpr;
  }
  
  public void setNombreComercialEmpr(String nombreComercialEmpr) {
    this.nombreComercialEmpr = nombreComercialEmpr;
  }
}