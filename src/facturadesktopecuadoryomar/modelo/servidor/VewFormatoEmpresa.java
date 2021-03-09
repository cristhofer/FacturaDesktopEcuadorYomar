package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "vew_formato_empresa", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "VewFormatoEmpresa.findAll", query = "SELECT v FROM VewFormatoEmpresa v"), 
  @NamedQuery(name = "VewFormatoEmpresa.findCargasEmpresa", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.codigoEmpr=:emp AND v.identificadorTico=:tipo"), 
  @NamedQuery(name = "VewFormatoEmpresa.findCargasByTipo", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.identificadorTico=:tipo"), 
  @NamedQuery(name = "VewFormatoEmpresa.findCargasTag", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.codigoEmpr=:emp AND v.identificadorTico=:tipo AND v.tagForm=:tag"), 
  @NamedQuery(name = "VewFormatoEmpresa.findCargasTagCompleto", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.codigoEmpr=:emp AND v.identificadorTico=:tipo AND v.padreTagForm LIKE :padre AND  v.tagForm=:tag"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByCodigoFoem", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.codigoFoem = :codigoFoem"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByOrdenForm", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.ordenForm = :ordenForm"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByCodigoEmpr", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.codigoEmpr = :codigoEmpr"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByIdentificadorTico", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.identificadorTico = :identificadorTico"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByCampoForm", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.campoForm = :campoForm"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByTagForm", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.tagForm = :tagForm"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByPadreTagForm", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.padreTagForm = :padreTagForm"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByTamanioMinTagForm", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.tamanioMinTagForm = :tamanioMinTagForm"), 
  @NamedQuery(name = "VewFormatoEmpresa.findByTamanioMaxTagForm", query = "SELECT v FROM VewFormatoEmpresa v WHERE v.tamanioMaxTagForm = :tamanioMaxTagForm")
})
public class VewFormatoEmpresa implements Serializable {
  @Id
  @Column(name = "codigo_foem")
  private Integer codigoFoem;
  @Column(name = "orden_form")
  private Integer ordenForm;
  @Column(name = "codigo_empr")
  private Long codigoEmpr;
  @Column(name = "identificador_tico")
  private String identificadorTico;
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
  @Transient
  private String tagCompleto;
  
  public Integer getCodigoFoem() {
    return this.codigoFoem;
  }
  
  public void setCodigoFoem(Integer codigoFoem) {
    this.codigoFoem = codigoFoem;
  }
  
  public Integer getOrdenForm() {
    return this.ordenForm;
  }
  
  public void setOrdenForm(Integer ordenForm) {
    this.ordenForm = ordenForm;
  }
  
  public Long getCodigoEmpr() {
    return this.codigoEmpr;
  }
  
  public void setCodigoEmpr(Long codigoEmpr) {
    this.codigoEmpr = codigoEmpr;
  }
  
  public String getIdentificadorTico() {
    return this.identificadorTico;
  }
  
  public void setIdentificadorTico(String identificadorTico) {
    this.identificadorTico = identificadorTico;
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
  
  public String getTagCompleto() {
    if (this.padreTagForm != null) {
      this.tagCompleto += this.padreTagForm + ".";
    }
    if (this.tagForm != null) {
      this.tagCompleto += this.tagForm;
    }
    return this.tagCompleto;
  }
  
  public void setTagCompleto(String tagCompleto) {
    this.tagCompleto = tagCompleto;
  }
}