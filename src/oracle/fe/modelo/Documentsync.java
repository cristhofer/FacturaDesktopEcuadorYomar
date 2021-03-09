package oracle.fe.modelo;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "DOCUMENTSYNC")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Documentsync.findAll", query = "SELECT d FROM Documentsync d"), 
  @NamedQuery(name = "Documentsync.findById", query = "SELECT d FROM Documentsync d WHERE d.id = :id"), 
  @NamedQuery(name = "Documentsync.findBySyncdate", query = "SELECT d FROM Documentsync d WHERE d.syncdate = :syncdate"), 
  @NamedQuery(name = "Documentsync.findByAccesskey", query = "SELECT d FROM Documentsync d WHERE d.accesskey = :accesskey"), 
  @NamedQuery(name = "Documentsync.findByDocumenttype", query = "SELECT d FROM Documentsync d WHERE d.documenttype = :documenttype"), 
  @NamedQuery(name = "Documentsync.findByIssuerruc", query = "SELECT d FROM Documentsync d WHERE d.issuerruc = :issuerruc"), 
  @NamedQuery(name = "Documentsync.findByXmlpath", query = "SELECT d FROM Documentsync d WHERE d.xmlpath = :xmlpath"), 
  @NamedQuery(name = "Documentsync.findByRidepath", query = "SELECT d FROM Documentsync d WHERE d.ridepath = :ridepath"), 
  @NamedQuery(name = "Documentsync.findByPdfpath", query = "SELECT d FROM Documentsync d WHERE d.pdfpath = :pdfpath"), 
  @NamedQuery(name = "Documentsync.findByAppstate", query = "SELECT d FROM Documentsync d WHERE d.appstate = :appstate"), 
  @NamedQuery(name = "Documentsync.findByAppid", query = "SELECT d FROM Documentsync d WHERE d.appid = :appid"), 
  @NamedQuery(name = "Documentsync.findByIssuername", query = "SELECT d FROM Documentsync d WHERE d.issuername = :issuername"), 
  @NamedQuery(name = "Documentsync.findByCompania", query = "SELECT d FROM Documentsync d WHERE d.compania = :compania"), 
  @NamedQuery(name = "Documentsync.findByCompanyruc", query = "SELECT d FROM Documentsync d WHERE d.companyruc = :companyruc"), 
  @NamedQuery(name = "Documentsync.findByAutorizacion", query = "SELECT d FROM Documentsync d WHERE d.autorizacion = :autorizacion"), 
  @NamedQuery(name = "Documentsync.findByEstablecimiento", query = "SELECT d FROM Documentsync d WHERE d.establecimiento = :establecimiento"), 
  @NamedQuery(name = "Documentsync.findByPuntoemision", query = "SELECT d FROM Documentsync d WHERE d.puntoemision = :puntoemision"), 
  @NamedQuery(name = "Documentsync.findByNumerodocumento", query = "SELECT d FROM Documentsync d WHERE d.numerodocumento = :numerodocumento"), 
  @NamedQuery(name = "Documentsync.findByFechaautorizaciondoc", query = "SELECT d FROM Documentsync d WHERE d.fechaautorizaciondoc = :fechaautorizaciondoc"), 
  @NamedQuery(name = "Documentsync.findBySubtotal", query = "SELECT d FROM Documentsync d WHERE d.subtotal = :subtotal"), 
  @NamedQuery(name = "Documentsync.findByIva", query = "SELECT d FROM Documentsync d WHERE d.iva = :iva"), 
  @NamedQuery(name = "Documentsync.findByTotal", query = "SELECT d FROM Documentsync d WHERE d.total = :total"), 
  @NamedQuery(name = "Documentsync.findByAmbiente", query = "SELECT d FROM Documentsync d WHERE d.ambiente = :ambiente"), 
  @NamedQuery(name = "Documentsync.findByEmision", query = "SELECT d FROM Documentsync d WHERE d.emision = :emision"), 
  @NamedQuery(name = "Documentsync.findByObligadollevarcontab", query = "SELECT d FROM Documentsync d WHERE d.obligadollevarcontab = :obligadollevarcontab"), 
  @NamedQuery(name = "Documentsync.findByContribuyenteespecial", query = "SELECT d FROM Documentsync d WHERE d.contribuyenteespecial = :contribuyenteespecial"), 
  @NamedQuery(name = "Documentsync.findByNroresolucion", query = "SELECT d FROM Documentsync d WHERE d.nroresolucion = :nroresolucion"), 
  @NamedQuery(name = "Documentsync.findByRise", query = "SELECT d FROM Documentsync d WHERE d.rise = :rise"), 
  @NamedQuery(name = "Documentsync.findByEstadodocumento", query = "SELECT d FROM Documentsync d WHERE d.estadodocumento = :estadodocumento"), 
  @NamedQuery(name = "Documentsync.findByEmissiondate", query = "SELECT d FROM Documentsync d WHERE d.emissiondate = :emissiondate"), 
  @NamedQuery(name = "Documentsync.findByIva0", query = "SELECT d FROM Documentsync d WHERE d.iva0 = :iva0"), 
  @NamedQuery(name = "Documentsync.findByIce", query = "SELECT d FROM Documentsync d WHERE d.ice = :ice"), 
  @NamedQuery(name = "Documentsync.findByIrbpnr", query = "SELECT d FROM Documentsync d WHERE d.irbpnr = :irbpnr"), 
  @NamedQuery(name = "Documentsync.findByCompaniaComprobante", query = "SELECT d FROM Documentsync d WHERE d.companiaComprobante = :companiaComprobante"), 
  @NamedQuery(name = "Documentsync.findByTipoComprobante", query = "SELECT d FROM Documentsync d WHERE d.tipoComprobante = :tipoComprobante"), 
  @NamedQuery(name = "Documentsync.findByNumeroComprobante", query = "SELECT d FROM Documentsync d WHERE d.numeroComprobante = :numeroComprobante")
})
public class Documentsync implements Serializable {
  private static final long serialVersionUID = 1L;
  @Basic(optional = false)
  @Column(name = "ID")
  private BigInteger id;
  @Basic(optional = false)
  @Column(name = "SYNCDATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date syncdate;
  @Id
  @Basic(optional = false)
  @Column(name = "ACCESSKEY")
  private String accesskey;
  @Basic(optional = false)
  @Column(name = "DOCUMENTTYPE")
  private BigInteger documenttype;
  @Basic(optional = false)
  @Column(name = "ISSUERRUC")
  private String issuerruc;
  @Column(name = "XMLPATH")
  private String xmlpath;
  @Column(name = "RIDEPATH")
  private String ridepath;
  @Column(name = "PDFPATH")
  private String pdfpath;
  @Column(name = "APPSTATE")
  private BigInteger appstate;
  @Column(name = "APPID")
  private String appid;
  @Basic(optional = false)
  @Column(name = "ISSUERNAME")
  private String issuername;
  @Basic(optional = false)
  @Column(name = "COMPANIA")
  private String compania;
  @Basic(optional = false)
  @Column(name = "COMPANYRUC")
  private String companyruc;
  @Basic(optional = false)
  @Column(name = "AUTORIZACION")
  private String autorizacion;
  @Basic(optional = false)
  @Column(name = "ESTABLECIMIENTO")
  private String establecimiento;
  @Basic(optional = false)
  @Column(name = "PUNTOEMISION")
  private String puntoemision;
  @Basic(optional = false)
  @Column(name = "NUMERODOCUMENTO")
  private String numerodocumento;
  @Basic(optional = false)
  @Column(name = "FECHAAUTORIZACIONDOC")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaautorizaciondoc;
  @Basic(optional = false)
  @Column(name = "SUBTOTAL")
  private BigInteger subtotal;
  @Basic(optional = false)
  @Column(name = "IVA")
  private BigInteger iva;
  @Basic(optional = false)
  @Column(name = "TOTAL")
  private BigInteger total;
  @Basic(optional = false)
  @Column(name = "AMBIENTE")
  private String ambiente;
  @Basic(optional = false)
  @Column(name = "EMISION")
  private String emision;
  @Basic(optional = false)
  @Column(name = "OBLIGADOLLEVARCONTAB")
  private String obligadollevarcontab;
  @Basic(optional = false)
  @Column(name = "CONTRIBUYENTEESPECIAL")
  private String contribuyenteespecial;
  @Column(name = "NRORESOLUCION")
  private String nroresolucion;
  @Column(name = "RISE")
  private String rise;
  @Basic(optional = false)
  @Column(name = "ESTADODOCUMENTO")
  private String estadodocumento;
  @Basic(optional = false)
  @Column(name = "EMISSIONDATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date emissiondate;
  @Column(name = "IVA0")
  private BigInteger iva0;
  @Column(name = "ICE")
  private BigInteger ice;
  @Column(name = "IRBPNR")
  private BigInteger irbpnr;
  @Column(name = "COMPANIA_COMPROBANTE")
  private String companiaComprobante;
  @Column(name = "TIPO_COMPROBANTE")
  private String tipoComprobante;
  @Column(name = "NUMERO_COMPROBANTE")
  private BigInteger numeroComprobante;
  
  public Documentsync() {}
  
  public Documentsync(String accesskey) {
    this.accesskey = accesskey;
  }
  
  public Documentsync(String accesskey, BigInteger id, Date syncdate, BigInteger documenttype, String issuerruc, String issuername, String compania, String companyruc, String autorizacion, String establecimiento, String puntoemision, String numerodocumento, Date fechaautorizaciondoc, BigInteger subtotal, BigInteger iva, BigInteger total, String ambiente, String emision, String obligadollevarcontab, String contribuyenteespecial, String estadodocumento, Date emissiondate) {
    this.accesskey = accesskey;
    this.id = id;
    this.syncdate = syncdate;
    this.documenttype = documenttype;
    this.issuerruc = issuerruc;
    this.issuername = issuername;
    this.compania = compania;
    this.companyruc = companyruc;
    this.autorizacion = autorizacion;
    this.establecimiento = establecimiento;
    this.puntoemision = puntoemision;
    this.numerodocumento = numerodocumento;
    this.fechaautorizaciondoc = fechaautorizaciondoc;
    this.subtotal = subtotal;
    this.iva = iva;
    this.total = total;
    this.ambiente = ambiente;
    this.emision = emision;
    this.obligadollevarcontab = obligadollevarcontab;
    this.contribuyenteespecial = contribuyenteespecial;
    this.estadodocumento = estadodocumento;
    this.emissiondate = emissiondate;
  }
  
  public BigInteger getId() {
    return this.id;
  }
  
  public void setId(BigInteger id) {
    this.id = id;
  }
  
  public Date getSyncdate() {
    return this.syncdate;
  }
  
  public void setSyncdate(Date syncdate) {
    this.syncdate = syncdate;
  }
  
  public String getAccesskey() {
    return this.accesskey;
  }
  
  public void setAccesskey(String accesskey) {
    this.accesskey = accesskey;
  }
  
  public BigInteger getDocumenttype() {
    return this.documenttype;
  }
  
  public void setDocumenttype(BigInteger documenttype) {
    this.documenttype = documenttype;
  }
  
  public String getIssuerruc() {
    return this.issuerruc;
  }
  
  public void setIssuerruc(String issuerruc) {
    this.issuerruc = issuerruc;
  }
  
  public String getXmlpath() {
    return this.xmlpath;
  }
  
  public void setXmlpath(String xmlpath) {
    this.xmlpath = xmlpath;
  }
  
  public String getRidepath() {
    return this.ridepath;
  }
  
  public void setRidepath(String ridepath) {
    this.ridepath = ridepath;
  }
  
  public String getPdfpath() {
    return this.pdfpath;
  }
  
  public void setPdfpath(String pdfpath) {
    this.pdfpath = pdfpath;
  }
  
  public BigInteger getAppstate() {
    return this.appstate;
  }
  
  public void setAppstate(BigInteger appstate) {
    this.appstate = appstate;
  }
  
  public String getAppid() {
    return this.appid;
  }
  
  public void setAppid(String appid) {
    this.appid = appid;
  }
  
  public String getIssuername() {
    return this.issuername;
  }
  
  public void setIssuername(String issuername) {
    this.issuername = issuername;
  }
  
  public String getCompania() {
    return this.compania;
  }
  
  public void setCompania(String compania) {
    this.compania = compania;
  }
  
  public String getCompanyruc() {
    return this.companyruc;
  }
  
  public void setCompanyruc(String companyruc) {
    this.companyruc = companyruc;
  }
  
  public String getAutorizacion() {
    return this.autorizacion;
  }
  
  public void setAutorizacion(String autorizacion) {
    this.autorizacion = autorizacion;
  }
  
  public String getEstablecimiento() {
    return this.establecimiento;
  }
  
  public void setEstablecimiento(String establecimiento) {
    this.establecimiento = establecimiento;
  }
  
  public String getPuntoemision() {
    return this.puntoemision;
  }
  
  public void setPuntoemision(String puntoemision) {
    this.puntoemision = puntoemision;
  }
  
  public String getNumerodocumento() {
    return this.numerodocumento;
  }
  
  public void setNumerodocumento(String numerodocumento) {
    this.numerodocumento = numerodocumento;
  }
  
  public Date getFechaautorizaciondoc() {
    return this.fechaautorizaciondoc;
  }
  
  public void setFechaautorizaciondoc(Date fechaautorizaciondoc) {
    this.fechaautorizaciondoc = fechaautorizaciondoc;
  }
  
  public BigInteger getSubtotal() {
    return this.subtotal;
  }
  
  public void setSubtotal(BigInteger subtotal) {
    this.subtotal = subtotal;
  }
  
  public BigInteger getIva() {
    return this.iva;
  }
  
  public void setIva(BigInteger iva) {
    this.iva = iva;
  }
  
  public BigInteger getTotal() {
    return this.total;
  }
  
  public void setTotal(BigInteger total) {
    this.total = total;
  }
  
  public String getAmbiente() {
    return this.ambiente;
  }
  
  public void setAmbiente(String ambiente) {
    this.ambiente = ambiente;
  }
  
  public String getEmision() {
    return this.emision;
  }
  
  public void setEmision(String emision) {
    this.emision = emision;
  }
  
  public String getObligadollevarcontab() {
    return this.obligadollevarcontab;
  }
  
  public void setObligadollevarcontab(String obligadollevarcontab) {
    this.obligadollevarcontab = obligadollevarcontab;
  }
  
  public String getContribuyenteespecial() {
    return this.contribuyenteespecial;
  }
  
  public void setContribuyenteespecial(String contribuyenteespecial) {
    this.contribuyenteespecial = contribuyenteespecial;
  }
  
  public String getNroresolucion() {
    return this.nroresolucion;
  }
  
  public void setNroresolucion(String nroresolucion) {
    this.nroresolucion = nroresolucion;
  }
  
  public String getRise() {
    return this.rise;
  }
  
  public void setRise(String rise) {
    this.rise = rise;
  }
  
  public String getEstadodocumento() {
    return this.estadodocumento;
  }
  
  public void setEstadodocumento(String estadodocumento) {
    this.estadodocumento = estadodocumento;
  }
  
  public Date getEmissiondate() {
    return this.emissiondate;
  }
  
  public void setEmissiondate(Date emissiondate) {
    this.emissiondate = emissiondate;
  }
  
  public BigInteger getIva0() {
    return this.iva0;
  }
  
  public void setIva0(BigInteger iva0) {
    this.iva0 = iva0;
  }
  
  public BigInteger getIce() {
    return this.ice;
  }
  
  public void setIce(BigInteger ice) {
    this.ice = ice;
  }
  
  public BigInteger getIrbpnr() {
    return this.irbpnr;
  }
  
  public void setIrbpnr(BigInteger irbpnr) {
    this.irbpnr = irbpnr;
  }
  
  public String getCompaniaComprobante() {
    return this.companiaComprobante;
  }
  
  public void setCompaniaComprobante(String companiaComprobante) {
    this.companiaComprobante = companiaComprobante;
  }
  
  public String getTipoComprobante() {
    return this.tipoComprobante;
  }
  
  public void setTipoComprobante(String tipoComprobante) {
    this.tipoComprobante = tipoComprobante;
  }
  
  public BigInteger getNumeroComprobante() {
    return this.numeroComprobante;
  }
  
  public void setNumeroComprobante(BigInteger numeroComprobante) {
    this.numeroComprobante = numeroComprobante;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.accesskey != null ? this.accesskey.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Documentsync)) {
      return false;
    }
    Documentsync other = (Documentsync)object;
    if ((this.accesskey == null && other.accesskey != null) || (this.accesskey != null && !this.accesskey.equals(other.accesskey))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "modelo.Documentsync[ accesskey=" + this.accesskey + " ]";
  }
}