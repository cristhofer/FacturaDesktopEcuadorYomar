package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.TypeConverter;

@Entity
@Table(name = "documento", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"), 
  @NamedQuery(name = "Documento.findPendienteFirma", query = "SELECT d FROM Documento d WHERE ((d.firmadoDocu=0 or d.firmadoDocu is null) AND d.estadoDocu='AUTORIZADO')"), 
  @NamedQuery(name = "Documento.findPendienteFirmaPorEmpresa", query = "SELECT d FROM Documento d WHERE ((d.firmadoDocu=0 or d.firmadoDocu is null) AND d.estadoDocu='AUTORIZADO' AND d.codigoEmpr.codigoInterno=:empresa)"), 
  @NamedQuery(name = "Documento.findPendienteEnvio", query = "SELECT d FROM Documento d WHERE (d.firmadoDocu=1 or d.firmadoDocu=true)AND d.mensajeDocu IS NULL AND d.fechaAutorizacionDocu IS NULL AND d.claveAccesoDocu IS NOT NULL AND d.fechaEmisionDocu >= :fechaEmisionIni AND (d.estadoDocu='AUTORIZADO' or (d.estadoDocu='DEVUELTA' AND (d.observacionDocu LIKE '%Identificador:70%' OR d.observacionDocu LIKE '%Identificador:50%'))) AND d.codigoEmpr.codigoInterno=:empresa"), 
  @NamedQuery(name = "Documento.findEnviados", query = "SELECT d FROM Documento d WHERE ((d.estadoDocu='AUTORIZADO' OR d.estadoDocu='EN PROCESO')) AND d.numeroAutorizacionDocu IS NULL AND d.codigoEmpr.codigoInterno=:empresa AND d.mensajeDocu='ENVIADOSRI'"), 
  @NamedQuery(name = "Documento.findClaveAccesoRegistrada", query = "SELECT d FROM Documento d WHERE (d.observacionDocu LIKE '%Identificador:43%' or d.observacionDocu LIKE '%Identificador:70%' or d.observacionDocu LIKE '%Identificador:50%' ) AND d.codigoEmpr.codigoInterno=:empresa"), 
  @NamedQuery(name = "Documento.findGenerados", query = "SELECT d FROM Documento d WHERE d.estadoDocu=:estado"), 
  @NamedQuery(name = "Documento.findActualizarDMZ", query = "SELECT d FROM Documento d WHERE (d.estadoDMZ='P' OR d.estadoDMZ is Null) AND d.fechaAutorizacionDocu IS NOT NULL AND d.numeroAutorizacionDocu IS NOT NULL AND d.estadoDocu='AUTORIZADO'"), 
  @NamedQuery(name = "Documento.findActualizarDMZPendiente", query = "SELECT d FROM Documento d WHERE (d.estadoDMZ='P' OR d.estadoDMZ is Null) AND d.fechaAutorizacionDocu IS NOT NULL AND d.numeroAutorizacionDocu IS NOT NULL AND d.estadoDocu='ANULADO'"), 
  @NamedQuery(name = "Documento.findActualizarDMZAutorizado", query = "SELECT d FROM Documento d WHERE d.estadoDMZ='E'"), 
  @NamedQuery(name = "Documento.findActualizarDocumentos", query = "SELECT d FROM Documento d WHERE d.estadoClienteDocu='P' AND d.estadoDocu='AUTORIZADO'"), 
  @NamedQuery(name = "Documento.findActualizarDocumentosAutCliente", query = "SELECT d FROM Documento d WHERE ((d.estadoClienteDocu='P' AND d.estadoDocu='AUTORIZADO' AND d.fechaAutorizacionDocu IS NOT NULL AND d.numeroAutorizacionDocu IS NOT NULL) OR (d.estadoDocu='RECHAZADO' AND d.estadoClienteDocu='P'))"), 
  @NamedQuery(name = "Documento.findDocumento", query = "SELECT d FROM Documento d WHERE d.numeroDocu=:numero AND d.codigoTido=:codigoTipoDoc AND d.codigoEmpr.codigoInterno=:empresa"), 
  @NamedQuery(name = "Documento.findByCodigoDocu", query = "SELECT d FROM Documento d WHERE d.codigoDocu = :codigoDocu"), 
  @NamedQuery(name = "Documento.findByNumeroDocu", query = "SELECT d FROM Documento d WHERE d.numeroDocu = :numeroDocu"), 
  @NamedQuery(name = "Documento.findByInformacionDocu", query = "SELECT d FROM Documento d WHERE d.informacionDocu = :informacionDocu"), 
  @NamedQuery(name = "Documento.findByClaveAccesoDocu", query = "SELECT d FROM Documento d WHERE d.claveAccesoDocu = :claveAccesoDocu"), 
  @NamedQuery(name = "Documento.findByNumeroAutorizacionDocu", query = "SELECT d FROM Documento d WHERE d.numeroAutorizacionDocu = :numeroAutorizacionDocu"), 
  @NamedQuery(name = "Documento.findByCodigoTido", query = "SELECT d FROM Documento d WHERE d.codigoTido = :codigoTido"), 
  @NamedQuery(name = "Documento.findByFechaAutorizacionDocu", query = "SELECT d FROM Documento d WHERE d.fechaAutorizacionDocu = :fechaAutorizacionDocu"), 
  @NamedQuery(name = "Documento.findByGuiaRemisionDocu", query = "SELECT d FROM Documento d WHERE d.guiaRemisionDocu = :guiaRemisionDocu"), 
  @NamedQuery(name = "Documento.findByDocSustentoDocu", query = "SELECT d FROM Documento d WHERE d.docSustentoDocu = :docSustentoDocu"), 
  @NamedQuery(name = "Documento.findByMensajeDocu", query = "SELECT d FROM Documento d WHERE d.mensajeDocu = :mensajeDocu"), 
  @NamedQuery(name = "Documento.findByObservacionDocu", query = "SELECT d FROM Documento d WHERE d.observacionDocu = :observacionDocu"), 
  @NamedQuery(name = "Documento.findByCodigoClien", query = "SELECT d FROM Documento d WHERE d.codigoClien = :codigoClien"), 
  @NamedQuery(name = "Documento.findByEstadoDocu", query = "SELECT d FROM Documento d WHERE d.estadoDocu = :estadoDocu"), 
  @NamedQuery(name = "Documento.findByFechaEmisionDocu", query = "SELECT d FROM Documento d WHERE d.fechaEmisionDocu = :fechaEmisionDocu"), 
  @NamedQuery(name = "Documento.findByXmlAuthDocu", query = "SELECT d FROM Documento d WHERE d.xmlFirmDocu = :xmlAuthDocu"), 
  @NamedQuery(name = "Documento.findByModoEnvioDocu", query = "SELECT d FROM Documento d WHERE d.modoEnvioDocu = :modoEnvioDocu")})
public class Documento
  implements Serializable
{
  @Column(name = "codigo_clien")
  private Long codigoClien;
  @Column(name = "modo_envio_docu")
  private Integer modoEnvioDocu;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_docu")
  private Long codigoDocu;
  @Column(name = "numero_docu")
  private String numeroDocu;
  @Column(name = "informacion_docu")
  private String informacionDocu;
  @Column(name = "clave_acceso_docu")
  private String claveAccesoDocu;
  @Column(name = "numero_autorizacion_docu")
  private String numeroAutorizacionDocu;
  @Column(name = "codigo_tido")
  private Integer codigoTido;
  @Column(name = "fecha_autorizacion_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAutorizacionDocu;
  @Column(name = "guia_remision_docu")
  private String guiaRemisionDocu;
  @Column(name = "doc_sustento_docu")
  private String docSustentoDocu;
  @Column(name = "mensaje_docu")
  private String mensajeDocu;
  @Column(name = "observacion_docu")
  private String observacionDocu;
  @Column(name = "estado_docu")
  private String estadoDocu;
  @Column(name = "estado_dmz")
  private String estadoDMZ;
  @Column(name = "fecha_emision_docu")
  @Temporal(TemporalType.DATE)
  private Date fechaEmisionDocu;
  @Column(name = "xml_firm_docu")
  private String xmlFirmDocu;
  @Column(name = "cuenta_cliente_docu")
  private String cuentaClienteDocu;
  @Column(name = "estado_cliente_docu")
  private String estadoClienteDocu;
  @Column(name = "estado_correo_docu")
  private String estadoCorreoDocu;
  @Column(name = "fecha_llegada_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaLlegadaDocu;
  @Column(name = "firmado_docu")
  @TypeConverter(name = "", dataType = Integer.class)
  private Boolean firmadoDocu;
  @Column(name = "fecha_envio_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEnvioDocu;
  @Column(name = "url_ride_docu")
  private String urlRideDocu;
  @Column(name = "path_ride_docu")
  private String pathRideDocu;
  @Column(name = "correo_docu")
  private String correoDocu;
  @Column(name = "fecha_envio_correo_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEnvioCorreoDocu;
  @Column(name = "tipo_doc_cliente")
  private String tipoDocuCliente;
  @Column(name = "descripcion_doc")
  private String descripcionDoc;
  @Column(name = "iva_actual")
  private Float ivaActual;
  @JoinColumn(name = "codigo_empr", referencedColumnName = "codigo_empr")
  @ManyToOne(optional = false)
  private Empresa codigoEmpr;
  
  public Documento() {}
  
  public Documento(Long codigoDocu) {
    this.codigoDocu = codigoDocu;
  }
  
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
  
  public Integer getCodigoTido() {
    return this.codigoTido;
  }
  
  public void setCodigoTido(Integer codigoTido) {
    this.codigoTido = codigoTido;
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
  
  public String getDocSustentoDocu() {
    return this.docSustentoDocu;
  }
  
  public void setDocSustentoDocu(String docSustentoDocu) {
    this.docSustentoDocu = docSustentoDocu;
  }
  
  public String getMensajeDocu() {
    return this.mensajeDocu;
  }
  
  public void setMensajeDocu(String mensajeDocu) {
    this.mensajeDocu = mensajeDocu;
  }
  
  public String getObservacionDocu() {
    return this.observacionDocu;
  }
  
  public void setObservacionDocu(String observacionDocu) {
    this.observacionDocu = observacionDocu;
  }
  
  public Long getCodigoClien() {
    return this.codigoClien;
  }
  
  public void setCodigoClien(Long codigoClien) {
    this.codigoClien = codigoClien;
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
  
  public String getXmlFirmDocu() {
    return this.xmlFirmDocu;
  }
  
  public void setXmlFirmDocu(String xmlFirmDocu) {
    this.xmlFirmDocu = xmlFirmDocu;
  }
  
  public int getModoEnvioDocu() {
    return this.modoEnvioDocu.intValue();
  }
  
  public void setModoEnvioDocu(int modoEnvioDocu) {
    this.modoEnvioDocu = Integer.valueOf(modoEnvioDocu);
  }

  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoDocu != null) ? this.codigoDocu.hashCode() : 0;
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Documento)) {
      return false;
    }
    Documento other = (Documento)object;
    if ((this.codigoDocu == null && other.codigoDocu != null) || (this.codigoDocu != null && !this.codigoDocu.equals(other.codigoDocu))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Documento[ codigoDocu=" + this.codigoDocu + " ]";
  }
  
  public Empresa getCodigoEmpr() {
    return this.codigoEmpr;
  }
  
  public void setCodigoEmpr(Empresa codigoEmpr) {
    this.codigoEmpr = codigoEmpr;
  }
  
  public String getCuentaClienteDocu() {
    return this.cuentaClienteDocu;
  }
  
  public void setCuentaClienteDocu(String cuentaClienteDocu) {
    this.cuentaClienteDocu = cuentaClienteDocu;
  }
  
  public String getEstadoClienteDocu() {
    return this.estadoClienteDocu;
  }
  
  public void setEstadoClienteDocu(String estadoClienteDocu) {
    this.estadoClienteDocu = estadoClienteDocu;
  }
  
  public String getEstadoCorreoDocu() {
    return this.estadoCorreoDocu;
  }
  
  public void setEstadoCorreoDocu(String estadoCorreoDocu) {
    this.estadoCorreoDocu = estadoCorreoDocu;
  }
  
  public Date getFechaLlegadaDocu() {
    return this.fechaLlegadaDocu;
  }
  
  public void setFechaLlegadaDocu(Date fechaLlegadaDocu) {
    this.fechaLlegadaDocu = fechaLlegadaDocu;
  }
  
  public Boolean getFirmadoDocu() {
    return this.firmadoDocu;
  }
  
  public void setFirmadoDocu(Boolean firmadoDocu) {
    this.firmadoDocu = firmadoDocu;
  }
  
  public Date getFechaEnvioDocu() {
    return this.fechaEnvioDocu;
  }
  
  public void setFechaEnvioDocu(Date fechaEnvioDocu) {
    this.fechaEnvioDocu = fechaEnvioDocu;
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
  
  public String getTipoDocuCliente() {
    return this.tipoDocuCliente;
  }
  
  public void setTipoDocuCliente(String tipoDocuCliente) {
    this.tipoDocuCliente = tipoDocuCliente;
  }
  
  public Float getIvaActual() {
    return this.ivaActual;
  }
  
  public void setIvaActual(Float ivaActual) {
    this.ivaActual = ivaActual;
  }
  
  public String getEstadoDMZ() {
    return this.estadoDMZ;
  }
  
  public void setEstadoDMZ(String estadoDMZ) {
    this.estadoDMZ = estadoDMZ;
  }
  
  public String getDescripcionDoc() {
    return this.descripcionDoc;
  }
  
  public void setDescripcionDoc(String descripcionDoc) {
    this.descripcionDoc = descripcionDoc;
  }
  
  public void setModoEnvioDocu(Integer modoEnvioDocu) {
    this.modoEnvioDocu = modoEnvioDocu;
  }
}