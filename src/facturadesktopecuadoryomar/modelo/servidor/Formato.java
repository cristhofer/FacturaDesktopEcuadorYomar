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
@Table(name = "formato", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Formato.findAll", query = "SELECT f FROM Formato f"), 
  @NamedQuery(name = "Formato.findByCampoForm", query = "SELECT f FROM Formato f WHERE f.campoForm = :campoForm"), 
  @NamedQuery(name = "Formato.findByTagForm", query = "SELECT f FROM Formato f WHERE f.tagForm = :tagForm"), 
  @NamedQuery(name = "Formato.findByPadreTagForm", query = "SELECT f FROM Formato f WHERE f.padreTagForm = :padreTagForm"), 
  @NamedQuery(name = "Formato.findByTamanioMinTagForm", query = "SELECT f FROM Formato f WHERE f.tamanioMinTagForm = :tamanioMinTagForm"), 
  @NamedQuery(name = "Formato.findByTamanioMaxTagForm", query = "SELECT f FROM Formato f WHERE f.tamanioMaxTagForm = :tamanioMaxTagForm"), 
  @NamedQuery(name = "Formato.findByCodigoForm", query = "SELECT f FROM Formato f WHERE f.codigoForm = :codigoForm")
})
public class Formato implements Serializable {
  @Column(name = "campo_form")
  private String campoForm;
  @Column(name = "tag_form")
  private String tagForm;
  @Column(name = "padre_tag_form")
  private String padreTagForm;
  @Column(name = "tamanio_min_tag_form")
  private Integer tamanioMinTagForm;
  @Column(name = "tamanio_max_tag_form")
  private Integer tamanioMaxTagForm;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_form")
  private Integer codigoForm;
  @OneToMany(mappedBy = "codigoForm")
  private List<FormatoEmp> formatoEmpList;
  
  public Formato() {}
  
  public Formato(Integer codigoForm) {
    this.codigoForm = codigoForm;
  }
  
  public String getCampoForm() {
    return this.campoForm;
  }
  
  public void setCampoForm(String campoForm) {
    this.campoForm = campoForm;
  }
  
  public String getTagForm() {
    return this.tagForm;
  }
  
  public void setTagForm(String tagForm) {
    this.tagForm = tagForm;
  }
  
  public String getPadreTagForm() {
    return this.padreTagForm;
  }
  
  public void setPadreTagForm(String padreTagForm) {
    this.padreTagForm = padreTagForm;
  }
  
  public Integer getTamanioMinTagForm() {
    return this.tamanioMinTagForm;
  }
  
  public void setTamanioMinTagForm(Integer tamanioMinTagForm) {
    this.tamanioMinTagForm = tamanioMinTagForm;
  }
  
  public Integer getTamanioMaxTagForm() {
    return this.tamanioMaxTagForm;
  }
  
  public void setTamanioMaxTagForm(Integer tamanioMaxTagForm) {
    this.tamanioMaxTagForm = tamanioMaxTagForm;
  }
  
  public Integer getCodigoForm() {
    return this.codigoForm;
  }
  
  public void setCodigoForm(Integer codigoForm) {
    this.codigoForm = codigoForm;
  }
  
  @XmlTransient
  public List<FormatoEmp> getFormatoEmpList() {
    return this.formatoEmpList;
  }
  
  public void setFormatoEmpList(List<FormatoEmp> formatoEmpList) {
    this.formatoEmpList = formatoEmpList;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoForm != null ? this.codigoForm.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Formato)) {
      return false;
    }
    Formato other = (Formato)object;
    if ((this.codigoForm == null && other.codigoForm != null) || (this.codigoForm != null && !this.codigoForm.equals(other.codigoForm))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Formato[ codigoForm=" + this.codigoForm + " ]";
  }
}