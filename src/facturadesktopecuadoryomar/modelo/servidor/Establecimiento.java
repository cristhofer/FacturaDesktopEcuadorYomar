package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "establecimiento", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Establecimiento.findAll", query = "SELECT e FROM Establecimiento e"), 
  @NamedQuery(name = "Establecimiento.findMatriz", query = "SELECT e FROM Establecimiento e  WHERE e.codigoEmpr=:emp AND e.tipoEstab='M' AND e.estadoEstab='ACTIVO'"), 
  @NamedQuery(name = "Establecimiento.findByCodigoEstab", query = "SELECT e FROM Establecimiento e WHERE e.codigoEstab = :codigoEstab"), 
  @NamedQuery(name = "Establecimiento.findByIdentificadorEstab", query = "SELECT e FROM Establecimiento e WHERE e.identificadorEstab = :identificadorEstab"), 
  //@NamedQuery(name = "Establecimiento.findByDireccionEstab", query = "SELECT e FROM Establecimiento e WHERE e.direccionEstab = :direccionEstab"), 
  @NamedQuery(name = "Establecimiento.findByDireccionEstab", query = "SELECT e FROM Establecimiento e WHERE e.codigoEmpr=:emp AND e.codigoEstab=:codEstab AND e.tipoEstab='S' AND e.estadoEstab='ACTIVO'"),
  @NamedQuery(name = "Establecimiento.findByTipoEstab", query = "SELECT e FROM Establecimiento e WHERE e.tipoEstab = :tipoEstab"), 
  @NamedQuery(name = "Establecimiento.findByEstadoEstab", query = "SELECT e FROM Establecimiento e WHERE e.estadoEstab = :estadoEstab")
})
public class Establecimiento implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_estab")
  private Integer codigoEstab;
  @Basic(optional = false)
  @Column(name = "identificador_estab")
  private String identificadorEstab;
  @Basic(optional = false)
  @Column(name = "direccion_estab")
  private String direccionEstab;
  @Column(name = "tipo_estab")
  private String tipoEstab;
  @Column(name = "estado_estab")
  private String estadoEstab;
  @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "codigoEstab")
  private List<Telefono> telefonoList;
  @JoinColumn(name = "codigo_empr", referencedColumnName = "codigo_empr")
  @ManyToOne(optional = false)
  private Empresa codigoEmpr;
  @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "codigoEstab")
  private List<PuntoEmision> puntoEmisionList;
  
  public Establecimiento() {}
  
  public Establecimiento(Integer codigoEstab) {
    this.codigoEstab = codigoEstab;
  }
  
  public Establecimiento(Integer codigoEstab, String identificadorEstab, String direccionEstab) {
    this.codigoEstab = codigoEstab;
    this.identificadorEstab = identificadorEstab;
    this.direccionEstab = direccionEstab;
  }
  
  public Integer getCodigoEstab() {
    return this.codigoEstab;
  }
  
  public void setCodigoEstab(Integer codigoEstab) {
    this.codigoEstab = codigoEstab;
  }
  
  public String getIdentificadorEstab() {
    return this.identificadorEstab;
  }
  
  public void setIdentificadorEstab(String identificadorEstab) {
    this.identificadorEstab = identificadorEstab;
  }
  
  public String getDireccionEstab() {
    return this.direccionEstab;
  }
  
  public void setDireccionEstab(String direccionEstab) {
    this.direccionEstab = direccionEstab;
  }
  
  public String getTipoEstab() {
    return this.tipoEstab;
  }
  
  public void setTipoEstab(String tipoEstab) {
    this.tipoEstab = tipoEstab;
  }
  
  public String getEstadoEstab() {
    return this.estadoEstab;
  }
  
  public void setEstadoEstab(String estadoEstab) {
    this.estadoEstab = estadoEstab;
  }
  
  @XmlTransient
  public List<Telefono> getTelefonoList() {
    return this.telefonoList;
  }
  
  public void setTelefonoList(List<Telefono> telefonoList) {
    this.telefonoList = telefonoList;
  }
  
  public Empresa getCodigoEmpr() {
    return this.codigoEmpr;
  }
  
  public void setCodigoEmpr(Empresa codigoEmpr) {
    this.codigoEmpr = codigoEmpr;
  }
  
  @XmlTransient
  public List<PuntoEmision> getPuntoEmisionList() {
    return this.puntoEmisionList;
  }
  
  public void setPuntoEmisionList(List<PuntoEmision> puntoEmisionList) {
    this.puntoEmisionList = puntoEmisionList;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoEstab != null) ? this.codigoEstab.hashCode() : 0;
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Establecimiento)) {
      return false;
    }
    Establecimiento other = (Establecimiento)object;
    if ((this.codigoEstab == null && other.codigoEstab != null) || (this.codigoEstab != null && !this.codigoEstab.equals(other.codigoEstab))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Establecimiento[ codigoEstab=" + this.codigoEstab + " ]";
  }
}