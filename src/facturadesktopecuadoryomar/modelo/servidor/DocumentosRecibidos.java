package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "documentos_recibidos")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "DocumentosRecibidos.findAll", query = "SELECT d FROM DocumentosRecibidos d"), 
  @NamedQuery(name = "DocumentosRecibidos.findByCodigoDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.codigoDocu = :codigoDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByClaveAccesoDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.claveAccesoDocu = :claveAccesoDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByDocSustentoDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.docSustentoDocu = :docSustentoDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByEstadoDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.estadoDocu = :estadoDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByFechaAutorizacionDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.fechaAutorizacionDocu = :fechaAutorizacionDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByFechaEmisionDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.fechaEmisionDocu = :fechaEmisionDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByGuiaRemisionDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.guiaRemisionDocu = :guiaRemisionDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByMensajeDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.mensajeDocu = :mensajeDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByNumeroAutorizacionDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.numeroAutorizacionDocu = :numeroAutorizacionDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByNumeroDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.numeroDocu = :numeroDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByObservacionDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.observacionDocu = :observacionDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByPathRideDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.pathRideDocu = :pathRideDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByUrlRideDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.urlRideDocu = :urlRideDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByCodigoEmpr", query = "SELECT d FROM DocumentosRecibidos d WHERE d.codigoEmpr = :codigoEmpr"), 
  @NamedQuery(name = "DocumentosRecibidos.findByEstadoClienteDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.estadoClienteDocu = :estadoClienteDocu AND d.tipoComprobante='07'"), 
  @NamedQuery(name = "DocumentosRecibidos.findByFechaEnvioDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.fechaEnvioDocu = :fechaEnvioDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByFechaLlegadaDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.fechaLlegadaDocu = :fechaLlegadaDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByRazonsocialClienteDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.razonsocialClienteDocu = :razonsocialClienteDocu"), 
  @NamedQuery(name = "DocumentosRecibidos.findByRucClienteDocu", query = "SELECT d FROM DocumentosRecibidos d WHERE d.rucClienteDocu = :rucClienteDocu")})
public class DocumentosRecibidos
  implements Serializable
{
  @Id
  @Basic(optional = false)
  @Column(name = "codigo_docu")
  private Long codigoDocu;
  @Column(name = "clave_acceso_docu")
  private String claveAccesoDocu;
  @Column(name = "tipo_comprobante")
  private String tipoComprobante;
  @Column(name = "doc_sustento_docu")
  private String docSustentoDocu;
  @Column(name = "estado_docu")
  private String estadoDocu;
  @Column(name = "fecha_autorizacion_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAutorizacionDocu;
  @Column(name = "fecha_emision_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEmisionDocu;
  @Column(name = "guia_remision_docu")
  private String guiaRemisionDocu;
  @Column(name = "mensaje_docu")
  private String mensajeDocu;
  @Column(name = "numero_autorizacion_docu")
  private String numeroAutorizacionDocu;
  @Column(name = "numero_docu")
  private String numeroDocu;
  @Column(name = "observacion_docu")
  private String observacionDocu;
  @Column(name = "path_ride_docu")
  private String pathRideDocu;
  @Column(name = "url_ride_docu")
  private String urlRideDocu;
  @Column(name = "xml_firm_docu")
  private String xmlFirmDocu;
  @Column(name = "codigo_empr")
  private Integer codigoEmpr;
  @Column(name = "estado_cliente_docu")
  private String estadoClienteDocu;
  @Column(name = "fecha_envio_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEnvioDocu;
  @Column(name = "fecha_llegada_docu")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaLlegadaDocu;
  @Column(name = "razonsocial_cliente_docu")
  private String razonsocialClienteDocu;
  @Column(name = "ruc_cliente_docu")
  private String rucClienteDocu;
  
  public DocumentosRecibidos() {}
  
  public DocumentosRecibidos(Long codigoDocu) {
    this.codigoDocu = codigoDocu;
  }
  
  public Long getCodigoDocu() {
    return this.codigoDocu;
  }
  
  public void setCodigoDocu(Long codigoDocu) {
    this.codigoDocu = codigoDocu;
  }
  
  public String getClaveAccesoDocu() {
    return this.claveAccesoDocu;
  }
  
  public void setClaveAccesoDocu(String claveAccesoDocu) {
    this.claveAccesoDocu = claveAccesoDocu;
  }
  
  public String getDocSustentoDocu() {
    return this.docSustentoDocu;
  }
  
  public void setDocSustentoDocu(String docSustentoDocu) {
    this.docSustentoDocu = docSustentoDocu;
  }
  
  public String getEstadoDocu() {
    return this.estadoDocu;
  }
  
  public void setEstadoDocu(String estadoDocu) {
    this.estadoDocu = estadoDocu;
  }
  
  public Date getFechaAutorizacionDocu() {
    return this.fechaAutorizacionDocu;
  }
  
  public void setFechaAutorizacionDocu(Date fechaAutorizacionDocu) {
    this.fechaAutorizacionDocu = fechaAutorizacionDocu;
  }
  
  public Date getFechaEmisionDocu() {
    return this.fechaEmisionDocu;
  }
  
  public void setFechaEmisionDocu(Date fechaEmisionDocu) {
    this.fechaEmisionDocu = fechaEmisionDocu;
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
  
  public String getNumeroAutorizacionDocu() {
    return this.numeroAutorizacionDocu;
  }
  
  public void setNumeroAutorizacionDocu(String numeroAutorizacionDocu) {
    this.numeroAutorizacionDocu = numeroAutorizacionDocu;
  }
  
  public String getNumeroDocu() {
    return this.numeroDocu;
  }
  
  public void setNumeroDocu(String numeroDocu) {
    this.numeroDocu = numeroDocu;
  }
  
  public String getObservacionDocu() {
    return this.observacionDocu;
  }
  
  public void setObservacionDocu(String observacionDocu) {
    this.observacionDocu = observacionDocu;
  }
  
  public String getPathRideDocu() {
    return this.pathRideDocu;
  }
  
  public void setPathRideDocu(String pathRideDocu) {
    this.pathRideDocu = pathRideDocu;
  }
  
  public String getUrlRideDocu() {
    return this.urlRideDocu;
  }
  
  public void setUrlRideDocu(String urlRideDocu) {
    this.urlRideDocu = urlRideDocu;
  }
  
  public String getXmlFirmDocu() {
    return this.xmlFirmDocu;
  }
  
  public void setXmlFirmDocu(String xmlFirmDocu) {
    this.xmlFirmDocu = xmlFirmDocu;
  }
  
  public Integer getCodigoEmpr() {
    return this.codigoEmpr;
  }
  
  public void setCodigoEmpr(Integer codigoEmpr) {
    this.codigoEmpr = codigoEmpr;
  }
  
  public String getEstadoClienteDocu() {
    return this.estadoClienteDocu;
  }
  
  public void setEstadoClienteDocu(String estadoClienteDocu) {
    this.estadoClienteDocu = estadoClienteDocu;
  }
  
  public Date getFechaEnvioDocu() {
    return this.fechaEnvioDocu;
  }
  
  public void setFechaEnvioDocu(Date fechaEnvioDocu) {
    this.fechaEnvioDocu = fechaEnvioDocu;
  }
  
  public Date getFechaLlegadaDocu() {
    return this.fechaLlegadaDocu;
  }
  
  public void setFechaLlegadaDocu(Date fechaLlegadaDocu) {
    this.fechaLlegadaDocu = fechaLlegadaDocu;
  }
  
  public String getRazonsocialClienteDocu() {
    return this.razonsocialClienteDocu;
  }
  
  public void setRazonsocialClienteDocu(String razonsocialClienteDocu) {
    this.razonsocialClienteDocu = razonsocialClienteDocu;
  }
  
  public String getRucClienteDocu() {
    return this.rucClienteDocu;
  }
  
  public void setRucClienteDocu(String rucClienteDocu) {
    this.rucClienteDocu = rucClienteDocu;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoDocu != null) ? this.codigoDocu.hashCode() : 0;
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof DocumentosRecibidos)) {
      return false;
    }
    DocumentosRecibidos other = (DocumentosRecibidos)object;
    if ((this.codigoDocu == null && other.codigoDocu != null) || (this.codigoDocu != null && !this.codigoDocu.equals(other.codigoDocu))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "DocumentosRecibidos{codigoDocu=" + this.codigoDocu + ", claveAccesoDocu=" + this.claveAccesoDocu + ", tipoComprobante=" + this.tipoComprobante + ", docSustentoDocu=" + this.docSustentoDocu + ", estadoDocu=" + this.estadoDocu + ", fechaAutorizacionDocu=" + this.fechaAutorizacionDocu + ", fechaEmisionDocu=" + this.fechaEmisionDocu + ", guiaRemisionDocu=" + this.guiaRemisionDocu + ", mensajeDocu=" + this.mensajeDocu + ", numeroAutorizacionDocu=" + this.numeroAutorizacionDocu + ", numeroDocu=" + this.numeroDocu + ", observacionDocu=" + this.observacionDocu + ", pathRideDocu=" + this.pathRideDocu + ", urlRideDocu=" + this.urlRideDocu + ", xmlFirmDocu=" + this.xmlFirmDocu + ", codigoEmpr=" + this.codigoEmpr + ", estadoClienteDocu=" + this.estadoClienteDocu + ", fechaEnvioDocu=" + this.fechaEnvioDocu + ", fechaLlegadaDocu=" + this.fechaLlegadaDocu + ", razonsocialClienteDocu=" + this.razonsocialClienteDocu + ", rucClienteDocu=" + this.rucClienteDocu + '}';
  }
  
  public String getTipoComprobante() {
    return this.tipoComprobante;
  }
  
  public void setTipoComprobante(String tipoComprobante) {
    this.tipoComprobante = tipoComprobante;
  }
}