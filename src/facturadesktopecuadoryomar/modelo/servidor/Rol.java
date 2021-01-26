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
@Table(name = "rol", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"), 
  @NamedQuery(name = "Rol.findByCodigoRole", query = "SELECT r FROM Rol r WHERE r.codigoRole = :codigoRole"), 
  @NamedQuery(name = "Rol.findByNombreRole", query = "SELECT r FROM Rol r WHERE r.nombreRole = :nombreRole"), 
  @NamedQuery(name = "Rol.findByEstadoRole", query = "SELECT r FROM Rol r WHERE r.estadoRole = :estadoRole"), 
  @NamedQuery(name = "Rol.findByCarpetaRole", query = "SELECT r FROM Rol r WHERE r.carpetaRole = :carpetaRole")
})
public class Rol implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_role")
  private Integer codigoRole;
  @Basic(optional = false)
  @Column(name = "nombre_role")
  private String nombreRole;
  @Column(name = "estado_role")
  private String estadoRole;
  @Column(name = "carpeta_role")
  private String carpetaRole;
  @OneToMany(mappedBy = "codigoRole")
  private List<UsuarioRol> usuarioRolList;
  @OneToMany(mappedBy = "codigoRol")
  private List<PermisoRol> permisoRolList;
  
  public Rol() {}
  
  public Rol(Integer codigoRole) {
    this.codigoRole = codigoRole;
  }
  
  public Rol(Integer codigoRole, String nombreRole) {
    this.codigoRole = codigoRole;
    this.nombreRole = nombreRole;
  }
  
  public Integer getCodigoRole() {
    return this.codigoRole;
  }
  
  public void setCodigoRole(Integer codigoRole) {
    this.codigoRole = codigoRole;
  }
  
  public String getNombreRole() {
    return this.nombreRole;
  }
  
  public void setNombreRole(String nombreRole) {
    this.nombreRole = nombreRole;
  }
  
  public String getEstadoRole() {
    return this.estadoRole;
  }
  
  public void setEstadoRole(String estadoRole) {
    this.estadoRole = estadoRole;
  }
  
  public String getCarpetaRole() {
    return this.carpetaRole;
  }
  
  public void setCarpetaRole(String carpetaRole) {
    this.carpetaRole = carpetaRole;
  }
  
  @XmlTransient
  public List<UsuarioRol> getUsuarioRolList() {
    return this.usuarioRolList;
  }
  
  public void setUsuarioRolList(List<UsuarioRol> usuarioRolList) {
    this.usuarioRolList = usuarioRolList;
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
    hash += (this.codigoRole != null ? this.codigoRole.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Rol)) {
      return false;
    }
    Rol other = (Rol)object;
    if ((this.codigoRole == null && other.codigoRole != null) || (this.codigoRole != null && !this.codigoRole.equals(other.codigoRole))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Rol[ codigoRole=" + this.codigoRole + " ]";
  }
}