package oracle.fe.modelo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "FACTURACION_INTERMEDIA")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "FacturacionIntermedia.findAll", query = "SELECT f FROM FacturacionIntermedia f"), 
  @NamedQuery(name = "FacturacionIntermedia.findById", query = "SELECT f FROM FacturacionIntermedia f WHERE f.id = :id"), 
  @NamedQuery(name = "FacturacionIntermedia.findByNumFact", query = "SELECT f FROM FacturacionIntermedia f WHERE f.numFact = :numFact"), 
  @NamedQuery(name = "FacturacionIntermedia.findByCodEstab", query = "SELECT f FROM FacturacionIntermedia f WHERE f.codEstab = :codEstab"), 
  @NamedQuery(name = "FacturacionIntermedia.findByPtoEmi", query = "SELECT f FROM FacturacionIntermedia f WHERE f.ptoEmi = :ptoEmi"), 
  @NamedQuery(name = "FacturacionIntermedia.findByTipoDoc", query = "SELECT f FROM FacturacionIntermedia f WHERE f.tipoDoc = :tipoDoc"), 
  @NamedQuery(name = "FacturacionIntermedia.findByCodCliente", query = "SELECT f FROM FacturacionIntermedia f WHERE f.codCliente = :codCliente"), 
  @NamedQuery(name = "FacturacionIntermedia.findByTramaDoc", query = "SELECT f FROM FacturacionIntermedia f WHERE f.tramaDoc = :tramaDoc"), 
  @NamedQuery(name = "FacturacionIntermedia.findByTipoEnvio", query = "SELECT f FROM FacturacionIntermedia f WHERE f.tipoEnvio = :tipoEnvio"), 
  @NamedQuery(name = "FacturacionIntermedia.findByFechaEmision", query = "SELECT f FROM FacturacionIntermedia f WHERE f.fechaEmision = :fechaEmision"), 
  @NamedQuery(name = "FacturacionIntermedia.findByFechaInsercion", query = "SELECT f FROM FacturacionIntermedia f WHERE f.fechaInsercion = :fechaInsercion"), 
  @NamedQuery(name = "FacturacionIntermedia.findByEstado", query = "SELECT f FROM FacturacionIntermedia f WHERE f.estado = :estado"), 
  @NamedQuery(name = "FacturacionIntermedia.findByEmpresa", query = "SELECT f FROM FacturacionIntermedia f WHERE f.estado = :estado and f.compania=:compania and f.tramaDoc is not null"),
  @NamedQuery(name = "FacturacionIntermedia.findByPrioridad", query = "SELECT f FROM FacturacionIntermedia f WHERE f.prioridad = :prioridad"), 
  @NamedQuery(name = "FacturacionIntermedia.findByIdDocumento", query = "SELECT f FROM FacturacionIntermedia f WHERE f.idDocumento = :idDocumento"), 
  @NamedQuery(name = "FacturacionIntermedia.findByCompania", query = "SELECT f FROM FacturacionIntermedia f WHERE f.compania = :compania")
})
public class FacturacionIntermedia implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "ID")
  private Long id;
  @Column(name = "NUM_FACT")
  private String numFact;
  @Column(name = "COD_ESTAB")
  private String codEstab;
  @Column(name = "PTO_EMI")
  private String ptoEmi;
  @Column(name = "TIPO_DOC")
  private String tipoDoc;
  @Column(name = "COD_CLIENTE")
  private String codCliente;
  @Column(name = "TRAMA_DOC")
  @Lob
  private String tramaDoc;
  @Column(name = "TIPO_ENVIO")
  private Integer tipoEnvio;
  @Column(name = "FECHA_EMISION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEmision;
  @Column(name = "FECHA_INSERCION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInsercion;
  @Column(name = "ESTADO")
  private String estado;
  @Column(name = "PRIORIDAD")
  private Integer prioridad;
  @Column(name = "ID_DOCUMENTO")
  private String idDocumento;
  @Column(name = "COMPANIA")
  private String compania;
  @Column(name = "DESCRIPCION_DOC")
  private String descripcionDoc;
  @Transient
  private List<String> listaInformacion;
  @Transient
  private String numeroDocumento;
  
  public FacturacionIntermedia() {}
  
  public FacturacionIntermedia(Long id) {
    this.id = id;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getNumFact() {
    return this.numFact;
  }
  
  public void setNumFact(String numFact) {
    this.numFact = numFact;
  }
  
  public String getCodEstab() {
    return this.codEstab;
  }
  
  public void setCodEstab(String codEstab) {
    this.codEstab = codEstab;
  }
  
  public String getPtoEmi() {
    return this.ptoEmi;
  }
  
  public void setPtoEmi(String ptoEmi) {
    this.ptoEmi = ptoEmi;
  }
  
  public String getTipoDoc() {
    return this.tipoDoc;
  }
  
  public void setTipoDoc(String tipoDoc) {
    this.tipoDoc = tipoDoc;
  }
  
  public String getCodCliente() {
    return this.codCliente;
  }
  
  public void setCodCliente(String codCliente) {
    this.codCliente = codCliente;
  }
  
  public String getTramaDoc() {
    return this.tramaDoc;
  }
  
  public void setTramaDoc(String tramaDoc) {
    this.tramaDoc = tramaDoc;
  }
  
  public Integer getTipoEnvio() {
    return this.tipoEnvio;
  }
  
  public void setTipoEnvio(Integer tipoEnvio) {
    this.tipoEnvio = tipoEnvio;
  }
  
  public Date getFechaEmision() {
    return this.fechaEmision;
  }
  
  public void setFechaEmision(Date fechaEmision) {
    this.fechaEmision = fechaEmision;
  }
  
  public Date getFechaInsercion() {
    return this.fechaInsercion;
  }
  
  public void setFechaInsercion(Date fechaInsercion) {
    this.fechaInsercion = fechaInsercion;
  }
  
  public String getEstado() {
    return this.estado;
  }
  
  public void setEstado(String estado) {
    this.estado = estado;
  }
  
  public Integer getPrioridad() {
    return this.prioridad;
  }
  
  public void setPrioridad(Integer prioridad) {
    this.prioridad = prioridad;
  }
  
  public String getIdDocumento() {
    return this.idDocumento;
  }
  
  public void setIdDocumento(String idDocumento) {
    this.idDocumento = idDocumento;
  }
  
  public String getCompania() {
    return this.compania;
  }
  
  public void setCompania(String compania) {
    this.compania = compania;
  }
  
  public String getDescripcionDoc() {
    return this.descripcionDoc;
  }
  
  public void setDescripcionDoc(String descripcionDoc) {
    this.descripcionDoc = descripcionDoc;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof FacturacionIntermedia)) {
      return false;
    }
    FacturacionIntermedia other = (FacturacionIntermedia)object;
    if (
        (
            this.id == null && other.id != null) 
            || (this.id != null && !this.id.equals(other.id)
        )
    ) {
      return false;
    }
    return true;
  }
  
  public List<String> getListaInformacion() {
    this.listaInformacion = new LinkedList<>();
    if (this.getTramaDoc() != null) {
      String[] info = this.getTramaDoc().split("},");
      for (String ic : info) {
        String val = ic.replace("}", "").replace("{", "");
        if (val.trim().length() > 10) {
          this.listaInformacion.add(val);
        }
      } 
    } 
    return this.listaInformacion;
  }
  
  public String getNumeroDocumento() {
    String sec = "";
    if (this.getNumFact() != null) {
      DecimalFormat decimalFormat = new DecimalFormat("000000000");
      try {
        sec = decimalFormat.format(Integer.parseInt(getNumFact()));
      } catch (NumberFormatException ex) {
        sec = null;
      } 
    } 
    if (
        this.getPtoEmi() != null && this.getCodEstab() != null 
        && this.getNumFact() != null
    ) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getCodEstab()).append("-").append(this.getPtoEmi())
        .append("-").append(sec);
      this.numeroDocumento = sb.toString();
    } 
    return this.numeroDocumento;
  }
  
  public String toString() {
    return "FacturacionIntermedia{id=" + this.id + ", numFact=" + this.numFact 
           + ", codEstab=" + this.codEstab + ", ptoEmi=" + this.ptoEmi 
           + ", tipoDoc=" + this.tipoDoc + ", codCliente=" + this.codCliente 
           + ", tramaDoc=" + this.tramaDoc + ", tipoEnvio=" + this.tipoEnvio 
           + ", fechaEmision=" + this.fechaEmision + ", fechaInsercion=" 
           + this.fechaInsercion + ", estado=" + this.estado + ", prioridad=" 
           + this.prioridad + ", idDocumento=" + this.idDocumento + ", compania=" 
           + this.compania + '}';
  }
}