package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "permiso", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p"), 
  @NamedQuery(name = "Permiso.findByCodigoPerm", query = "SELECT p FROM Permiso p WHERE p.codigoPerm = :codigoPerm"), 
  @NamedQuery(name = "Permiso.findByNombrePerm", query = "SELECT p FROM Permiso p WHERE p.nombrePerm = :nombrePerm"), 
  @NamedQuery(name = "Permiso.findByUrlPerm", query = "SELECT p FROM Permiso p WHERE p.urlPerm = :urlPerm"), 
  @NamedQuery(name = "Permiso.findByTipoPerm", query = "SELECT p FROM Permiso p WHERE p.tipoPerm = :tipoPerm"), 
  @NamedQuery(name = "Permiso.findByEstadoPerm", query = "SELECT p FROM Permiso p WHERE p.estadoPerm = :estadoPerm"), 
  @NamedQuery(name = "Permiso.findByIconoPerm", query = "SELECT p FROM Permiso p WHERE p.iconoPerm = :iconoPerm"), 
  @NamedQuery(name = "Permiso.findByPosicionPerm", query = "SELECT p FROM Permiso p WHERE p.posicionPerm = :posicionPerm")
})
public class Permiso implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_perm")
  private Integer codigoPerm;
  @Basic(optional = false)
  @Column(name = "nombre_perm")
  private String nombrePerm;
  @Column(name = "url_perm")
  private String urlPerm;
  @Basic(optional = false)
  @Column(name = "tipo_perm")
  private String tipoPerm;
  @Column(name = "estado_perm")
  private String estadoPerm;
  @Column(name = "icono_perm")
  private String iconoPerm;
  @Column(name = "posicion_perm")
  private Short posicionPerm;
  @OneToMany(mappedBy = "padrePerm")
  private List<Permiso> permisoList;
  @JoinColumn(name = "padre_perm", referencedColumnName = "codigo_perm")
  @ManyToOne
  private Permiso padrePerm;
  @OneToMany(mappedBy = "codigoPerm")
  private List<PermisoRol> permisoRolList;
  
  public Permiso() {}
  
  public Permiso(Integer codigoPerm) {
    this.codigoPerm = codigoPerm;
  }
  
  public Permiso(Integer codigoPerm, String nombrePerm, String tipoPerm) {
    this.codigoPerm = codigoPerm;
    this.nombrePerm = nombrePerm;
    this.tipoPerm = tipoPerm;
  }
  
  public Integer getCodigoPerm() {
    return this.codigoPerm;
  }
  
  public void setCodigoPerm(Integer codigoPerm) {
    this.codigoPerm = codigoPerm;
  }
  
  public String getNombrePerm() {
    return this.nombrePerm;
  }
  
  public void setNombrePerm(String nombrePerm) {
    this.nombrePerm = nombrePerm;
  }
  
  public String getUrlPerm() {
    return this.urlPerm;
  }
  
  public void setUrlPerm(String urlPerm) {
    this.urlPerm = urlPerm;
  }
  
  public String getTipoPerm() {
    return this.tipoPerm;
  }
  
  public void setTipoPerm(String tipoPerm) {
    this.tipoPerm = tipoPerm;
  }
  
  public String getEstadoPerm() {
    return this.estadoPerm;
  }
  
  public void setEstadoPerm(String estadoPerm) {
    this.estadoPerm = estadoPerm;
  }
  
  public String getIconoPerm() {
    return this.iconoPerm;
  }
  
  public void setIconoPerm(String iconoPerm) {
    this.iconoPerm = iconoPerm;
  }
  
  public Short getPosicionPerm() {
    return this.posicionPerm;
  }
  
  public void setPosicionPerm(Short posicionPerm) {
    this.posicionPerm = posicionPerm;
  }
  
  @XmlTransient
  public List<Permiso> getPermisoList() {
    return this.permisoList;
  }
  
  public void setPermisoList(List<Permiso> permisoList) {
    this.permisoList = permisoList;
  }
  
  public Permiso getPadrePerm() {
    return this.padrePerm;
  }
  
  public void setPadrePerm(Permiso padrePerm) {
    this.padrePerm = padrePerm;
  }
  
  @XmlTransient
  public List<PermisoRol> getPermisoRolList() {
    return this.permisoRolList;
  }
  
  public void setPermisoRolList(List<PermisoRol> permisoRolList) {
    this.permisoRolList = permisoRolList;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoPerm != null ? this.codigoPerm.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Permiso)) {
      return false;
    }
    Permiso other = (Permiso)object;
    if ((this.codigoPerm == null && other.codigoPerm != null) || (this.codigoPerm != null && !this.codigoPerm.equals(other.codigoPerm))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Permiso[ codigoPerm=" + this.codigoPerm + " ]";
  }
}