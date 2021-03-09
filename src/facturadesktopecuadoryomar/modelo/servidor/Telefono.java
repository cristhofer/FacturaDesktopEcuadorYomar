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
@Table(name = "telefono", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Telefono.findAll", query = "SELECT t FROM Telefono t"), 
  @NamedQuery(name = "Telefono.findByCodigoTele", query = "SELECT t FROM Telefono t WHERE t.codigoTele = :codigoTele"), 
  @NamedQuery(name = "Telefono.findByNumeroTele", query = "SELECT t FROM Telefono t WHERE t.numeroTele = :numeroTele"), 
  @NamedQuery(name = "Telefono.findByOperadoraTele", query = "SELECT t FROM Telefono t WHERE t.operadoraTele = :operadoraTele"), 
  @NamedQuery(name = "Telefono.findByEstadoEstab", query = "SELECT t FROM Telefono t WHERE t.estadoEstab = :estadoEstab")
})
public class Telefono implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_tele")
  private Long codigoTele;
  @Basic(optional = false)
  @Column(name = "numero_tele")
  private String numeroTele;
  @Basic(optional = false)
  @Column(name = "operadora_tele")
  private String operadoraTele;
  @Column(name = "estado_estab")
  private String estadoEstab;
  @JoinColumn(name = "codigo_estab", referencedColumnName = "codigo_estab")
  @ManyToOne(optional = false)
  private Establecimiento codigoEstab;
  
  public Telefono() {}
  
  public Telefono(Long codigoTele) {
    this.codigoTele = codigoTele;
  }
  
  public Telefono(Long codigoTele, String numeroTele, String operadoraTele) {
    this.codigoTele = codigoTele;
    this.numeroTele = numeroTele;
    this.operadoraTele = operadoraTele;
  }
  
  public Long getCodigoTele() {
    return this.codigoTele;
  }
  
  public void setCodigoTele(Long codigoTele) {
    this.codigoTele = codigoTele;
  }
  
  public String getNumeroTele() {
    return this.numeroTele;
  }
  
  public void setNumeroTele(String numeroTele) {
    this.numeroTele = numeroTele;
  }
  
  public String getOperadoraTele() {
    return this.operadoraTele;
  }
  
  public void setOperadoraTele(String operadoraTele) {
    this.operadoraTele = operadoraTele;
  }
  
  public String getEstadoEstab() {
    return this.estadoEstab;
  }
  
  public void setEstadoEstab(String estadoEstab) {
    this.estadoEstab = estadoEstab;
  }
  
  public Establecimiento getCodigoEstab() {
    return this.codigoEstab;
  }
  
  public void setCodigoEstab(Establecimiento codigoEstab) {
    this.codigoEstab = codigoEstab;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoTele != null ? this.codigoTele.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Telefono)) {
      return false;
    }
    Telefono other = (Telefono)object;
    if ((this.codigoTele == null && other.codigoTele != null) || (this.codigoTele != null && !this.codigoTele.equals(other.codigoTele))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Telefono[ codigoTele=" + this.codigoTele + " ]";
  }
}