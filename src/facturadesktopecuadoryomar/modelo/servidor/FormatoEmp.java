package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "formato_emp", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "FormatoEmp.findAll", query = "SELECT f FROM FormatoEmp f"), 
  @NamedQuery(name = "FormatoEmp.findByCodigoTido", query = "SELECT f FROM FormatoEmp f WHERE f.codigoTido = :codigoTido"), 
  @NamedQuery(name = "FormatoEmp.findByCodigoEmpr", query = "SELECT f FROM FormatoEmp f WHERE f.codigoEmpr = :codigoEmpr"), 
  @NamedQuery(name = "FormatoEmp.findByOrdenForm", query = "SELECT f FROM FormatoEmp f WHERE f.ordenForm = :ordenForm"), 
  @NamedQuery(name = "FormatoEmp.findByCodigoFoem", query = "SELECT f FROM FormatoEmp f WHERE f.codigoFoem = :codigoFoem")
})
public class FormatoEmp implements Serializable {
  @Column(name = "codigo_tido")
  private Integer codigoTido;
  @Column(name = "codigo_empr")
  private BigInteger codigoEmpr;
  @Column(name = "orden_form")
  private Integer ordenForm;
  @Id
  @Basic(optional = false)
  @Column(name = "codigo_foem")
  private Integer codigoFoem;
  @JoinColumn(name = "codigo_form", referencedColumnName = "codigo_form")
  @ManyToOne
  private Formato codigoForm;
  
  public FormatoEmp() {}
  
  public FormatoEmp(Integer codigoFoem) {
    this.codigoFoem = codigoFoem;
  }
  
  public Integer getCodigoTido() {
    return this.codigoTido;
  }
  
  public void setCodigoTido(Integer codigoTido) {
    this.codigoTido = codigoTido;
  }
  
  public BigInteger getCodigoEmpr() {
    return this.codigoEmpr;
  }
  
  public void setCodigoEmpr(BigInteger codigoEmpr) {
    this.codigoEmpr = codigoEmpr;
  }
  
  public Integer getOrdenForm() {
    return this.ordenForm;
  }
  
  public void setOrdenForm(Integer ordenForm) {
    this.ordenForm = ordenForm;
  }
  
  public Integer getCodigoFoem() {
    return this.codigoFoem;
  }
  
  public void setCodigoFoem(Integer codigoFoem) {
    this.codigoFoem = codigoFoem;
  }
  
  public Formato getCodigoForm() {
    return this.codigoForm;
  }
  
  public void setCodigoForm(Formato codigoForm) {
    this.codigoForm = codigoForm;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoFoem != null ? this.codigoFoem.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof FormatoEmp)) {
      return false;
    }
    FormatoEmp other = (FormatoEmp)object;
    if ((this.codigoFoem == null && other.codigoFoem != null) || (this.codigoFoem != null && !this.codigoFoem.equals(other.codigoFoem))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.FormatoEmp[ codigoFoem=" + this.codigoFoem + " ]";
  }
}