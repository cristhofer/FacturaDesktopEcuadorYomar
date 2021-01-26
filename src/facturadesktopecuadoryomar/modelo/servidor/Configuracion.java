package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "configuracion", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c"), 
  @NamedQuery(name = "Configuracion.findByCodigoConf", query = "SELECT c FROM Configuracion c WHERE c.codigoConf = :codigoConf"), 
  @NamedQuery(name = "Configuracion.findByModoConf", query = "SELECT c FROM Configuracion c WHERE c.modoConf = :modoConf"), 
  @NamedQuery(name = "Configuracion.findByCorreoEnvioConf", query = "SELECT c FROM Configuracion c WHERE c.correoEnvioConf = :correoEnvioConf"), 
  @NamedQuery(name = "Configuracion.findByCorreoHostConf", query = "SELECT c FROM Configuracion c WHERE c.correoHostConf = :correoHostConf"), 
  @NamedQuery(name = "Configuracion.findByCorreoUserConf", query = "SELECT c FROM Configuracion c WHERE c.correoUserConf = :correoUserConf"), 
  @NamedQuery(name = "Configuracion.findByCorreoPassConf", query = "SELECT c FROM Configuracion c WHERE c.correoPassConf = :correoPassConf"), 
  @NamedQuery(name = "Configuracion.findByCorreoSslConf", query = "SELECT c FROM Configuracion c WHERE c.correoSslConf = :correoSslConf"), 
  @NamedQuery(name = "Configuracion.findByCorreoTlsConf", query = "SELECT c FROM Configuracion c WHERE c.correoTlsConf = :correoTlsConf"), 
  @NamedQuery(name = "Configuracion.findByUrlFirmasConf", query = "SELECT c FROM Configuracion c WHERE c.urlFirmasConf = :urlFirmasConf"), 
  @NamedQuery(name = "Configuracion.findByCorreoPortConf", query = "SELECT c FROM Configuracion c WHERE c.correoPortConf = :correoPortConf"), 
  @NamedQuery(name = "Configuracion.findByDocFacturaConf", query = "SELECT c FROM Configuracion c WHERE c.docFacturaConf = :docFacturaConf"), 
  @NamedQuery(name = "Configuracion.findByDocFacturaPathConf", query = "SELECT c FROM Configuracion c WHERE c.docFacturaPathConf = :docFacturaPathConf"), 
  @NamedQuery(name = "Configuracion.findByDocRetencionConf", query = "SELECT c FROM Configuracion c WHERE c.docRetencionConf = :docRetencionConf"), 
  @NamedQuery(name = "Configuracion.findByDocRetencionPathConf", query = "SELECT c FROM Configuracion c WHERE c.docRetencionPathConf = :docRetencionPathConf"),
  @NamedQuery(name = "Configuracion.findByDocNotacreditoConf", query = "SELECT c FROM Configuracion c WHERE c.docNotacreditoConf = :docNotacreditoConf"), 
  @NamedQuery(name = "Configuracion.findByDocNotacreditoPathConf", query = "SELECT c FROM Configuracion c WHERE c.docNotacreditoPathConf = :docNotacreditoPathConf"), 
  @NamedQuery(name = "Configuracion.findByDocNotadebitoConf", query = "SELECT c FROM Configuracion c WHERE c.docNotadebitoConf = :docNotadebitoConf"), 
  @NamedQuery(name = "Configuracion.findByDocNotadebitoPathConf", query = "SELECT c FROM Configuracion c WHERE c.docNotadebitoPathConf = :docNotadebitoPathConf"), 
  @NamedQuery(name = "Configuracion.findByDocGuiaConf", query = "SELECT c FROM Configuracion c WHERE c.docGuiaConf = :docGuiaConf"), 
  @NamedQuery(name = "Configuracion.findByDocGuiaPathConf", query = "SELECT c FROM Configuracion c WHERE c.docGuiaPathConf = :docGuiaPathConf"), 
  @NamedQuery(name = "Configuracion.findByMensajeConf", query = "SELECT c FROM Configuracion c WHERE c.mensajeConf = :mensajeConf"), 
  @NamedQuery(name = "Configuracion.findByUrlTemporalConf", query = "SELECT c FROM Configuracion c WHERE c.urlTemporalConf = :urlTemporalConf")})
public class Configuracion implements Serializable {
  @OneToMany(mappedBy = "idConfiguracion")
  private List<Empresa> empresaList;
  @Column(name = "host_ws_prueba")
  private String hostWsPrueba;
  @Column(name = "host_ws_produccion")
  private String hostWsProduccion;
  @Column(name = "iva_conf")
  private Double ivaConf;
  @Column(name = "url_ws_envio_offline")
  private String urlWsEnvioOffline;
  @Column(name = "url_ws_recepcion_offline")
  private String urlWsRecepcionOffline;
  @Column(name = "url_ws_envio_offline_prueba")
  private String urlWsEnvioOfflinePrueba;
  @Column(name = "url_ws_recepcion_offline_prueba")
  private String urlWsRecepcionOfflinePrueba;
  @Column(name = "url_ws_envio_online")
  private String urlWsEnvioOnline;
  @Column(name = "url_ws_recepcion_online")
  private String urlWsRecepcionOnline;
  @Column(name = "url_ws_envio_online_prueba")
  private String urlWsEnvioOnlinePrueba;
  @Column(name = "url_ws_recepcion_online_prueba")
  private String urlWsRecepcionOnlinePrueba;
  @Column(name = "user_sri")
  private String userSri;
  @Column(name = "password_sri")
  private String passwordSri;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_conf")
  private Integer codigoConf;
  @Column(name = "modo_conf")
  private String modoConf;
  @Column(name = "correo_envio_conf")
  private String correoEnvioConf;
  @Column(name = "correo_host_conf")
  private String correoHostConf;
  @Column(name = "correo_user_conf")
  private String correoUserConf;
  @Column(name = "correo_pass_conf")
  private String correoPassConf;
  @Column(name = "correo_ssl_conf")
  private Boolean correoSslConf;
  @Column(name = "correo_tls_conf")
  private Boolean correoTlsConf;
  @Column(name = "url_firmas_conf")
  private String urlFirmasConf;
  @Column(name = "correo_port_conf")
  private String correoPortConf;
  @Column(name = "doc_factura_conf")
  private String docFacturaConf;
  @Column(name = "doc_factura_path_conf")
  private String docFacturaPathConf;
  @Column(name = "doc_retencion_conf")
  private String docRetencionConf;
  @Column(name = "doc_retencion_path_conf")
  private String docRetencionPathConf;
  @Column(name = "doc_notacredito_conf")
  private String docNotacreditoConf;
  @Column(name = "doc_notacredito_path_conf")
  private String docNotacreditoPathConf;
  @Column(name = "doc_notadebito_conf")
  private String docNotadebitoConf;
  @Column(name = "doc_notadebito_path_conf")
  private String docNotadebitoPathConf;
  @Column(name = "doc_guia_conf")
  private String docGuiaConf;
  @Column(name = "doc_guia_path_conf")
  private String docGuiaPathConf;
  @Column(name = "mensaje_conf")
  private String mensajeConf;
  @Column(name = "url_temporal_conf")
  private String urlTemporalConf;
  @Column(name = "ambiente_conf")
  private String ambienteConf;
  @Column(name = "codigo_numerico_conf")
  private String codigoNumericoConf;
  @Column(name = "tipo_emision_conf")
  private String tipoEmisionConf;
  @Column(name = "ruta_plantilla_conf")
  private String rutaPlantillaConf;
  @Column(name = "ruta_reporte_conf")
  private String rutaReporteConf;
  @Column(name = "ruta_documentos")
  private String rutaDocumentos;
  
  public Configuracion() {}
  
  public Configuracion(Integer codigoConf) {
    this.codigoConf = codigoConf;
  }
  
  public Integer getCodigoConf() {
    return this.codigoConf;
  }
  
  public void setCodigoConf(Integer codigoConf) {
    this.codigoConf = codigoConf;
  }
  
  public String getModoConf() {
    return this.modoConf;
  }
  
  public void setModoConf(String modoConf) {
    this.modoConf = modoConf;
  }
  
  public String getCorreoEnvioConf() {
    return this.correoEnvioConf;
  }
  
  public void setCorreoEnvioConf(String correoEnvioConf) {
    this.correoEnvioConf = correoEnvioConf;
  }
  
  public String getCorreoHostConf() {
    return this.correoHostConf;
  }
  
  public void setCorreoHostConf(String correoHostConf) {
    this.correoHostConf = correoHostConf;
  }
  
  public String getCorreoUserConf() {
    return this.correoUserConf;
  }
  
  public void setCorreoUserConf(String correoUserConf) {
    this.correoUserConf = correoUserConf;
  }
  
  public String getCorreoPassConf() {
    return this.correoPassConf;
  }
  
  public void setCorreoPassConf(String correoPassConf) {
    this.correoPassConf = correoPassConf;
  }
  
  public Boolean getCorreoSslConf() {
    return this.correoSslConf;
  }
  
  public void setCorreoSslConf(Boolean correoSslConf) {
    this.correoSslConf = correoSslConf;
  }
  
  public Boolean getCorreoTlsConf() {
    return this.correoTlsConf;
  }
  
  public void setCorreoTlsConf(Boolean correoTlsConf) {
    this.correoTlsConf = correoTlsConf;
  }
  
  public String getUrlFirmasConf() {
    return this.urlFirmasConf;
  }
  
  public void setUrlFirmasConf(String urlFirmasConf) {
    this.urlFirmasConf = urlFirmasConf;
  }
  
  public String getCorreoPortConf() {
    return this.correoPortConf;
  }
  
  public void setCorreoPortConf(String correoPortConf) {
    this.correoPortConf = correoPortConf;
  }
  
  public String getDocFacturaConf() {
    return this.docFacturaConf;
  }
  
  public void setDocFacturaConf(String docFacturaConf) {
    this.docFacturaConf = docFacturaConf;
  }
  
  public String getDocFacturaPathConf() {
    return this.docFacturaPathConf;
  }
  
  public void setDocFacturaPathConf(String docFacturaPathConf) {
    this.docFacturaPathConf = docFacturaPathConf;
  }
  
  public String getDocRetencionConf() {
    return this.docRetencionConf;
  }
  
  public void setDocRetencionConf(String docRetencionConf) {
    this.docRetencionConf = docRetencionConf;
  }
  
  public String getDocRetencionPathConf() {
    return this.docRetencionPathConf;
  }
  
  public void setDocRetencionPathConf(String docRetencionPathConf) {
    this.docRetencionPathConf = docRetencionPathConf;
  }
  
  public String getDocNotacreditoConf() {
    return this.docNotacreditoConf;
  }
  
  public void setDocNotacreditoConf(String docNotacreditoConf) {
    this.docNotacreditoConf = docNotacreditoConf;
  }
  
  public String getDocNotacreditoPathConf() {
    return this.docNotacreditoPathConf;
  }
  
  public void setDocNotacreditoPathConf(String docNotacreditoPathConf) {
    this.docNotacreditoPathConf = docNotacreditoPathConf;
  }
  
  public String getDocNotadebitoConf() {
    return this.docNotadebitoConf;
  }
  
  public void setDocNotadebitoConf(String docNotadebitoConf) {
    this.docNotadebitoConf = docNotadebitoConf;
  }
  
  public String getDocNotadebitoPathConf() {
    return this.docNotadebitoPathConf;
  }
  
  public void setDocNotadebitoPathConf(String docNotadebitoPathConf) {
    this.docNotadebitoPathConf = docNotadebitoPathConf;
  }
  
  public String getDocGuiaConf() {
    return this.docGuiaConf;
  }
  
  public void setDocGuiaConf(String docGuiaConf) {
    this.docGuiaConf = docGuiaConf;
  }
  
  public String getDocGuiaPathConf() {
    return this.docGuiaPathConf;
  }
  
  public void setDocGuiaPathConf(String docGuiaPathConf) {
    this.docGuiaPathConf = docGuiaPathConf;
  }
  
  public String getMensajeConf() {
    return this.mensajeConf;
  }
  
  public void setMensajeConf(String mensajeConf) {
    this.mensajeConf = mensajeConf;
  }
  
  public String getUrlTemporalConf() {
    return this.urlTemporalConf;
  }
  
  public void setUrlTemporalConf(String urlTemporalConf) {
    this.urlTemporalConf = urlTemporalConf;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoConf != null) ? this.codigoConf.hashCode() : 0;
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Configuracion)) {
      return false;
    }
    Configuracion other = (Configuracion)object;
    if ((this.codigoConf == null && other.codigoConf != null) || (this.codigoConf != null && !this.codigoConf.equals(other.codigoConf))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Configuracion[ codigoConf=" + this.codigoConf + " ]";
  }
  
  public String getAmbienteConf() {
    return this.ambienteConf;
  }
  
  public void setAmbienteConf(String ambienteConf) {
    this.ambienteConf = ambienteConf;
  }

  public String getCodigoNumericoConf() {
    return this.codigoNumericoConf;
  }
  
  public void setCodigoNumericoConf(String codigoNumericoConf) {
    this.codigoNumericoConf = codigoNumericoConf;
  }
  
  public String getTipoEmisionConf() {
    return this.tipoEmisionConf;
  }
  
  public void setTipoEmisionConf(String tipoEmisionConf) {
    this.tipoEmisionConf = tipoEmisionConf;
  }
  
  public String getRutaPlantillaConf() {
    return this.rutaPlantillaConf;
  }
  
  public void setRutaPlantillaConf(String rutaPlantillaConf) {
    this.rutaPlantillaConf = rutaPlantillaConf;
  }
  
  public String getRutaReporteConf() {
    return this.rutaReporteConf;
  }

  public void setRutaReporteConf(String rutaReporteConf) {
    this.rutaReporteConf = rutaReporteConf;
  }
  
  public Double getIvaConf() {
    return this.ivaConf;
  }
  
  public void setIvaConf(Double ivaConf) {
    this.ivaConf = ivaConf;
  }
  
  public String getUrlWsEnvioOffline() {
    return this.urlWsEnvioOffline;
  }
  
  public void setUrlWsEnvioOffline(String urlWsEnvioOffline) {
    this.urlWsEnvioOffline = urlWsEnvioOffline;
  }
  
  public String getUrlWsRecepcionOffline() {
    return this.urlWsRecepcionOffline;
  }
  
  public void setUrlWsRecepcionOffline(String urlWsRecepcionOffline) {
    this.urlWsRecepcionOffline = urlWsRecepcionOffline;
  }
  
  public String getUrlWsEnvioOfflinePrueba() {
    return this.urlWsEnvioOfflinePrueba;
  }
  
  public void setUrlWsEnvioOfflinePrueba(String urlWsEnvioOfflinePrueba) {
    this.urlWsEnvioOfflinePrueba = urlWsEnvioOfflinePrueba;
  }
  
  public String getUrlWsRecepcionOfflinePrueba() {
    return this.urlWsRecepcionOfflinePrueba;
  }
  
  public void setUrlWsRecepcionOfflinePrueba(String urlWsRecepcionOfflinePrueba) {
    this.urlWsRecepcionOfflinePrueba = urlWsRecepcionOfflinePrueba;
  }
  
  public String getUrlWsEnvioOnline() {
    return this.urlWsEnvioOnline;
  }
  
  public void setUrlWsEnvioOnline(String urlWsEnvioOnline) {
    this.urlWsEnvioOnline = urlWsEnvioOnline;
  }
  
  public String getUrlWsRecepcionOnline() {
    return this.urlWsRecepcionOnline;
  }
  
  public void setUrlWsRecepcionOnline(String urlWsRecepcionOnline) {
    this.urlWsRecepcionOnline = urlWsRecepcionOnline;
  }
  
  public String getUrlWsEnvioOnlinePrueba() {
    return this.urlWsEnvioOnlinePrueba;
  }
  
  public void setUrlWsEnvioOnlinePrueba(String urlWsEnvioOnlinePrueba) {
    this.urlWsEnvioOnlinePrueba = urlWsEnvioOnlinePrueba;
  }
  
  public String getUrlWsRecepcionOnlinePrueba() {
    return this.urlWsRecepcionOnlinePrueba;
  }
  
  public void setUrlWsRecepcionOnlinePrueba(String urlWsRecepcionOnlinePrueba) {
    this.urlWsRecepcionOnlinePrueba = urlWsRecepcionOnlinePrueba;
  }
  
  public String getUserSri() {
    return this.userSri;
  }
  
  public void setUserSri(String userSri) {
    this.userSri = userSri;
  }
  
  public String getPasswordSri() {
    return this.passwordSri;
  }
  
  public void setPasswordSri(String passwordSri) {
    this.passwordSri = passwordSri;
  }
  
  public String getHostWsPrueba() {
    return this.hostWsPrueba;
  }
  
  public void setHostWsPrueba(String hostWsPrueba) {
    this.hostWsPrueba = hostWsPrueba;
  }
  
  public String getHostWsProduccion() {
    return this.hostWsProduccion;
  }
  
  public void setHostWsProduccion(String hostWsProduccion) {
    this.hostWsProduccion = hostWsProduccion;
  }
  
  @XmlTransient
  public List<Empresa> getEmpresaList() {
    return this.empresaList;
  }
  
  public void setEmpresaList(List<Empresa> empresaList) {
    this.empresaList = empresaList;
  }
  
  public String getRutaDocumentos() {
    return this.rutaDocumentos;
  }
  
  public void setRutaDocumentos(String rutaDocumentos) {
    this.rutaDocumentos = rutaDocumentos;
  }

  /*public String getDocLiquidacionConf() {
    return docLiquidacionConf;
  }

  public void setDocLiquidacionConf(String docLiquidacionConf) {
    this.docLiquidacionConf = docLiquidacionConf;
  }

  public String getDocLiquidacionPathConf() {
    return docLiquidacionPathConf;
  }

  public void setDocLiquidacionPathConf(String docLiquidacionPathConf) {
    this.docLiquidacionPathConf = docLiquidacionPathConf;
  }*/
}