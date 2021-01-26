package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "permiso_rol", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "PermisoRol.findAll", query = "SELECT p FROM PermisoRol p"), 
  @NamedQuery(name = "PermisoRol.findByCodigoRope", query = "SELECT p FROM PermisoRol p WHERE p.codigoRope = :codigoRope")
})
public class PermisoRol  implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_rope")
  private Long codigoRope;
  @JoinColumn(name = "codigo_perm", referencedColumnName = "codigo_perm")
  @ManyToOne
  private Permiso codigoPerm;
  @JoinColumn(name = "codigo_rol", referencedColumnName = "codigo_role")
  @ManyToOne
  private Rol codigoRol;
  
  public PermisoRol() {}
  
  public PermisoRol(Long codigoRope) {
    this.codigoRope = codigoRope;
  }
  
  public Long getCodigoRope() {
    return this.codigoRope;
  }
  
  public void setCodigoRope(Long codigoRope) {
    this.codigoRope = codigoRope;
  }
  
  public Permiso getCodigoPerm() {
    return this.codigoPerm;
  }
  
  public void setCodigoPerm(Permiso codigoPerm) {
    this.codigoPerm = codigoPerm;
  }
  
  public Rol getCodigoRol() {
    return this.codigoRol;
  }
  
  public void setCodigoRol(Rol codigoRol) {
    this.codigoRol = codigoRol;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoRope != null ? this.codigoRope.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof PermisoRol)) {
      return false;
    }
    PermisoRol other = (PermisoRol)object;
    if ((this.codigoRope == null && other.codigoRope != null) || (this.codigoRope != null && !this.codigoRope.equals(other.codigoRope))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.PermisoRol[ codigoRope=" + this.codigoRope + " ]";
  }
}