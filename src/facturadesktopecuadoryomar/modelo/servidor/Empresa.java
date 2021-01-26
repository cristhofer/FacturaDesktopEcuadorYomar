package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "empresa", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"), 
  @NamedQuery(name = "Empresa.findByCodigoEmpr", query = "SELECT e FROM Empresa e WHERE e.codigoEmpr = :codigoEmpr"), 
  @NamedQuery(name = "Empresa.findByNombreComercialEmpr", query = "SELECT e FROM Empresa e WHERE e.nombreComercialEmpr = :nombreComercialEmpr"), 
  @NamedQuery(name = "Empresa.findByRazonSocialEmpr", query = "SELECT e FROM Empresa e WHERE e.razonSocialEmpr = :razonSocialEmpr"), 
  @NamedQuery(name = "Empresa.findByRucEmpr", query = "SELECT e FROM Empresa e WHERE e.rucEmpr = :rucEmpr"), 
  @NamedQuery(name = "Empresa.findByRepresentanteLegalEmpr", query = "SELECT e FROM Empresa e WHERE e.representanteLegalEmpr = :representanteLegalEmpr"), 
  @NamedQuery(name = "Empresa.findByContadorEmpr", query = "SELECT e FROM Empresa e WHERE e.contadorEmpr = :contadorEmpr"), 
  @NamedQuery(name = "Empresa.findByTipoContribuyenteEmpr", query = "SELECT e FROM Empresa e WHERE e.tipoContribuyenteEmpr = :tipoContribuyenteEmpr"), 
  @NamedQuery(name = "Empresa.findByNumeroEspecialEmpr", query = "SELECT e FROM Empresa e WHERE e.numeroEspecialEmpr = :numeroEspecialEmpr"), 
  @NamedQuery(name = "Empresa.findByEstadoEmpr", query = "SELECT e FROM Empresa e WHERE e.estadoEmpr = :estadoEmpr"),
  @NamedQuery(name = "Empresa.findByFirmaEmpr", query = "SELECT e FROM Empresa e WHERE e.firmaEmpr = :firmaEmpr"), 
  @NamedQuery(name = "Empresa.findByClaveFirmaEmpr", query = "SELECT e FROM Empresa e WHERE e.claveFirmaEmpr = :claveFirmaEmpr"), 
  @NamedQuery(name = "Empresa.findByFechaCaducaFirmaEmpr", query = "SELECT e FROM Empresa e WHERE e.fechaCaducaFirmaEmpr = :fechaCaducaFirmaEmpr"), 
  @NamedQuery(name = "Empresa.findByLogoEmpr", query = "SELECT e FROM Empresa e WHERE e.logoEmpr = :logoEmpr"), 
  @NamedQuery(name = "Empresa.findByReferencia", query = "SELECT e FROM Empresa e WHERE e.codigoInterno = :referencia"), 
  @NamedQuery(name = "Empresa.findByBannerEmpr", query = "SELECT e FROM Empresa e WHERE e.bannerEmpr = :bannerEmpr")
})
public class Empresa
  implements Serializable
{
  @Column(name = "obligado_empr")
  private Boolean obligadoEmpr;
  @Column(name = "exportadora")
  private Boolean exportadora;
  @Column(name = "url_web_empr")
  private String urlWebEmpr;
  @Column(name = "logo_web_empr")
  private String logoWebEmpr;
  @Column(name = "estado_servicio_empr")
  private Boolean estadoServicioEmpr;
  @Column(name = "estado_servicio_factura")
  private Boolean estadoServicioFactura;
  @Column(name = "estado_servicio_guia")
  private Boolean estadoServicioGuia;
  @Column(name = "estado_servicio_retencion")
  private Boolean estadoServicioRetencion;
  @Column(name = "estado_servicio_nota_credito")
  private Boolean estadoServicioNotaCredito;
  @Column(name = "estado_servicio_nota_debito")
  private Boolean estadoServicioNotaDebito;
  @Column(name = "estado_servicio_reembolso")
  private Boolean estadoServicioReembolso;
  @Column(name = "estado_servicio_exportacion")
  private Boolean estadoServicioExportacion;
  @Column(name = "estado_servicio_liq_compra")
  private Boolean estadoServicioLiqCompra;
  @OneToMany(mappedBy = "codigoEmpr")
  private List<Documento> documentoList;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_empr")
  private Long codigoEmpr;
  @Basic(optional = false)
  @Column(name = "nombre_comercial_empr")
  private String nombreComercialEmpr;
  @Basic(optional = false)
  @Column(name = "razon_social_empr")
  private String razonSocialEmpr;
  @Basic(optional = false)
  @Column(name = "ruc_empr")
  private String rucEmpr;
  @Column(name = "representante_legal_empr")
  private String representanteLegalEmpr;
  @Column(name = "contador_empr")
  private String contadorEmpr;
  @Column(name = "tipo_contribuyente_empr")
  private String tipoContribuyenteEmpr;
  @Column(name = "numero_especial_empr")
  private String numeroEspecialEmpr;
  @Column(name = "estado_empr")
  private String estadoEmpr;
  @Column(name = "firma_empr")
  private String firmaEmpr;
  @Column(name = "clave_firma_empr")
  private String claveFirmaEmpr;
  @Column(name = "fecha_caduca_firma_empr")
  @Temporal(TemporalType.DATE)
  private Date fechaCaducaFirmaEmpr;
  @Column(name = "logo_empr")
  private String logoEmpr;
  @Column(name = "banner_empr")
  private String bannerEmpr;
  @Column(name = "codigo_interno")
  private String codigoInterno;
  @Column(name = "estado_proceso")
  private String estadoProceso;
  @JoinColumn(name = "id_configuracion", referencedColumnName = "codigo_conf")
  @OneToOne(cascade = {CascadeType.ALL})
  private Configuracion idConfiguracion;
  @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "codigoEmpr")
  private List<Establecimiento> establecimientoList;

  //AGREGADO POR CJTM
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private List<EtiquetaAdicional> etiquetaList;
  
  public Empresa() {}
  
  public Empresa(Long codigoEmpr) {
    this.codigoEmpr = codigoEmpr;
  }
  
  public Empresa(Long codigoEmpr, String nombreComercialEmpr, String razonSocialEmpr, String rucEmpr) {
    this.codigoEmpr = codigoEmpr;
    this.nombreComercialEmpr = nombreComercialEmpr;
    this.razonSocialEmpr = razonSocialEmpr;
    this.rucEmpr = rucEmpr;
  }
  
  public Long getCodigoEmpr() {
    return this.codigoEmpr;
  }
  
  public void setCodigoEmpr(Long codigoEmpr) {
    this.codigoEmpr = codigoEmpr;
  }
  
  public String getNombreComercialEmpr() {
    return this.nombreComercialEmpr;
  }
  
  public void setNombreComercialEmpr(String nombreComercialEmpr) {
    this.nombreComercialEmpr = nombreComercialEmpr;
  }
  
  public String getRazonSocialEmpr() {
    return this.razonSocialEmpr;
  }
  
  public void setRazonSocialEmpr(String razonSocialEmpr) {
    this.razonSocialEmpr = razonSocialEmpr;
  }
  
  public String getRucEmpr() {
    return this.rucEmpr;
  }
  
  public void setRucEmpr(String rucEmpr) {
    this.rucEmpr = rucEmpr;
  }
  
  public String getRepresentanteLegalEmpr() {
    return this.representanteLegalEmpr;
  }
  
  public void setRepresentanteLegalEmpr(String representanteLegalEmpr) {
    this.representanteLegalEmpr = representanteLegalEmpr;
  }
  
  public String getContadorEmpr() {
    return this.contadorEmpr;
  }
  
  public void setContadorEmpr(String contadorEmpr) {
    this.contadorEmpr = contadorEmpr;
  }
  
  public String getTipoContribuyenteEmpr() {
    return this.tipoContribuyenteEmpr;
  }
  
  public void setTipoContribuyenteEmpr(String tipoContribuyenteEmpr) {
    this.tipoContribuyenteEmpr = tipoContribuyenteEmpr;
  }
  
  public String getNumeroEspecialEmpr() {
    return this.numeroEspecialEmpr;
  }
  
  public void setNumeroEspecialEmpr(String numeroEspecialEmpr) {
    this.numeroEspecialEmpr = numeroEspecialEmpr;
  }
  
  public String getEstadoEmpr() {
    return this.estadoEmpr;
  }
  
  public void setEstadoEmpr(String estadoEmpr) {
    this.estadoEmpr = estadoEmpr;
  }
  
  public String getFirmaEmpr() {
    return this.firmaEmpr;
  }
  
  public void setFirmaEmpr(String firmaEmpr) {
    this.firmaEmpr = firmaEmpr;
  }
  
  public String getClaveFirmaEmpr() {
    return this.claveFirmaEmpr;
  }
  
  public void setClaveFirmaEmpr(String claveFirmaEmpr) {
    this.claveFirmaEmpr = claveFirmaEmpr;
  }
  
  public Date getFechaCaducaFirmaEmpr() {
    return this.fechaCaducaFirmaEmpr;
  }
  
  public void setFechaCaducaFirmaEmpr(Date fechaCaducaFirmaEmpr) {
    this.fechaCaducaFirmaEmpr = fechaCaducaFirmaEmpr;
  }
  
  public String getLogoEmpr() {
    return this.logoEmpr;
  }
  
  public void setLogoEmpr(String logoEmpr) {
    this.logoEmpr = logoEmpr;
  }
  
  public String getBannerEmpr() {
    return this.bannerEmpr;
  }
  
  public void setBannerEmpr(String bannerEmpr) {
    this.bannerEmpr = bannerEmpr;
  }
  
  @XmlTransient
  public List<Establecimiento> getEstablecimientoList() {
    return this.establecimientoList;
  }
  
  public void setEstablecimientoList(List<Establecimiento> establecimientoList) {
    this.establecimientoList = establecimientoList;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoEmpr != null) ? this.codigoEmpr.hashCode() : 0;
    return hash;
  }

  public boolean equals(Object object) {
    if (!(object instanceof Empresa)) {
      return false;
    }
    Empresa other = (Empresa)object;
    if ((this.codigoEmpr == null && other.codigoEmpr != null) || (this.codigoEmpr != null && !this.codigoEmpr.equals(other.codigoEmpr))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Empresa[ codigoEmpr=" + this.codigoEmpr + " ]";
  }
  
  public boolean getObligadoEmpr() {
    return this.obligadoEmpr;
  }
  
  public void setObligadoEmpr(boolean obligadoEmpr) {
    this.obligadoEmpr = obligadoEmpr;
  }
  
  public Configuracion getIdConfiguracion() {
    return this.idConfiguracion;
  }
  
  public void setIdConfiguracion(Configuracion idConfiguracion) {
    this.idConfiguracion = idConfiguracion;
  }
  
  public String getCodigoInterno() {
    return this.codigoInterno;
  }
  
  public void setCodigoInterno(String codigoInterno) {
    this.codigoInterno = codigoInterno;
  }
  
  public String getEstadoProceso() {
    return this.estadoProceso;
  }
  
  public void setEstadoProceso(String estadoProceso) {
    this.estadoProceso = estadoProceso;
  }
  
  public boolean getEstadoServicioEmpr() {
    if (Objects.equals(this.estadoServicioExportacion, Boolean.TRUE)) {
      this.estadoServicioEmpr = true;
    }
    if (Objects.equals(this.estadoServicioFactura, Boolean.TRUE)) {
      this.estadoServicioEmpr = true;
    }
    if (Objects.equals(this.estadoServicioGuia, Boolean.TRUE)) {
      this.estadoServicioEmpr = true;
    }
    if (Objects.equals(this.estadoServicioLiqCompra, Boolean.TRUE)) {
      this.estadoServicioEmpr = true;
    }
    if (Objects.equals(this.estadoServicioNotaCredito, Boolean.TRUE)) {
      this.estadoServicioEmpr = true;
    }
    if (Objects.equals(this.estadoServicioNotaDebito, Boolean.TRUE)) {
      this.estadoServicioEmpr = true;
    }
    if (Objects.equals(this.estadoServicioReembolso, Boolean.TRUE)) {
      this.estadoServicioEmpr = true;
    }
    if (Objects.equals(this.estadoServicioRetencion, Boolean.TRUE)) {
      this.estadoServicioEmpr = true;
    }
    if (Objects.equals(this.estadoServicioEmpr, Boolean.FALSE)) {
      this.estadoServicioEmpr = false;
    }
    return this.estadoServicioEmpr;
  }
  
  public void setEstadoServicioEmpr(boolean estadoServicioEmpr) {
    this.estadoServicioEmpr = estadoServicioEmpr;
  }
  
  public void setObligadoEmpr(Boolean obligadoEmpr) {
    this.obligadoEmpr = obligadoEmpr;
  }
  
  public Boolean getExportadora() {
    return this.exportadora;
  }
  
  public void setExportadora(Boolean exportadora) {
    this.exportadora = exportadora;
  }
  
  public String getUrlWebEmpr() {
    return this.urlWebEmpr;
  }
  
  public void setUrlWebEmpr(String urlWebEmpr) {
    this.urlWebEmpr = urlWebEmpr;
  }
  
  public String getLogoWebEmpr() {
    return this.logoWebEmpr;
  }
  
  public void setLogoWebEmpr(String logoWebEmpr) {
    this.logoWebEmpr = logoWebEmpr;
  }
  
  public void setEstadoServicioEmpr(Boolean estadoServicioEmpr) {
    this.estadoServicioEmpr = estadoServicioEmpr;
  }
  
  @XmlTransient
  public List<Documento> getDocumentoList() {
    return this.documentoList;
  }
  
  public void setDocumentoList(List<Documento> documentoList) {
    this.documentoList = documentoList;
  }
  
  public Boolean getEstadoServicioFactura() {
    return this.estadoServicioFactura;
  }
  
  public void setEstadoServicioFactura(Boolean estadoServicioFactura) {
    this.estadoServicioFactura = estadoServicioFactura;
  }
  
  public Boolean getEstadoServicioGuia() {
    return this.estadoServicioGuia;
  }
  
  public void setEstadoServicioGuia(Boolean estadoServicioGuia) {
    this.estadoServicioGuia = estadoServicioGuia;
  }
  
  public Boolean getEstadoServicioRetencion() {
    return this.estadoServicioRetencion;
  }
  
  public void setEstadoServicioRetencion(Boolean estadoServicioRetencion) {
    this.estadoServicioRetencion = estadoServicioRetencion;
  }
  
  public Boolean getEstadoServicioNotaCredito() {
    return this.estadoServicioNotaCredito;
  }
  
  public void setEstadoServicioNotaCredito(Boolean estadoServicioNotaCredito) {
    this.estadoServicioNotaCredito = estadoServicioNotaCredito;
  }
  
  public Boolean getEstadoServicioNotaDebito() {
    return this.estadoServicioNotaDebito;
  }
  
  public void setEstadoServicioNotaDebito(Boolean estadoServicioNotaDebito) {
    this.estadoServicioNotaDebito = estadoServicioNotaDebito;
  }
  
  public Boolean getEstadoServicioReembolso() {
    return this.estadoServicioReembolso;
  }
  
  public void setEstadoServicioReembolso(Boolean estadoServicioReembolso) {
    this.estadoServicioReembolso = estadoServicioReembolso;
  }
  
  public Boolean getEstadoServicioExportacion() {
    return this.estadoServicioExportacion;
  }
  
  public void setEstadoServicioExportacion(Boolean estadoServicioExportacion) {
    this.estadoServicioExportacion = estadoServicioExportacion;
  }
  
  public Boolean getEstadoServicioLiqCompra() {
    return this.estadoServicioLiqCompra;
  }
  
  public void setEstadoServicioLiqCompra(Boolean estadoServicioLiqCompra) {
    this.estadoServicioLiqCompra = estadoServicioLiqCompra;
  }

  /**
   * @return the etiquetaList
   */
  public List<EtiquetaAdicional> getEtiquetaList() {
    return etiquetaList;
  }

  /**
   * @param etiquetaList the etiquetaList to set
   */
  public void setEtiquetaList(List<EtiquetaAdicional> etiquetaList) {
    this.etiquetaList = etiquetaList;
  }
}