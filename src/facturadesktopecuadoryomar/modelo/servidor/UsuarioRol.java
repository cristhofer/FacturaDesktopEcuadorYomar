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
@Table(name = "usuario_rol", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "UsuarioRol.findAll", query = "SELECT u FROM UsuarioRol u"), 
  @NamedQuery(name = "UsuarioRol.findByCodigoUsro", query = "SELECT u FROM UsuarioRol u WHERE u.codigoUsro = :codigoUsro")
})
public class UsuarioRol implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_usro")
  private Long codigoUsro;
  @JoinColumn(name = "codigo_role", referencedColumnName = "codigo_role")
  @ManyToOne
  private Rol codigoRole;
  @JoinColumn(name = "codigo_usua", referencedColumnName = "codigo_usua")
  @ManyToOne
  private Usuario codigoUsua;
  
  public UsuarioRol() {}
  
  public UsuarioRol(Long codigoUsro) {
    this.codigoUsro = codigoUsro;
  }
  
  public Long getCodigoUsro() {
    return this.codigoUsro;
  }
  
  public void setCodigoUsro(Long codigoUsro) {
    this.codigoUsro = codigoUsro;
  }
  
  public Rol getCodigoRole() {
    return this.codigoRole;
  }
  
  public void setCodigoRole(Rol codigoRole) {
    this.codigoRole = codigoRole;
  }
  
  public Usuario getCodigoUsua() {
    return this.codigoUsua;
  }
  
  public void setCodigoUsua(Usuario codigoUsua) {
    this.codigoUsua = codigoUsua;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoUsro != null ? this.codigoUsro.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof UsuarioRol)) {
      return false;
    }
    UsuarioRol other = (UsuarioRol)object;
    if ((this.codigoUsro == null && other.codigoUsro != null) || (this.codigoUsro != null && !this.codigoUsro.equals(other.codigoUsro))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.UsuarioRol[ codigoUsro=" + this.codigoUsro + " ]";
  }
}