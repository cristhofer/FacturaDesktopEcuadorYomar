package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tipo_documento", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "TipoDocumento.findAll", query = "SELECT t FROM TipoDocumento t"), 
  @NamedQuery(name = "TipoDocumento.findByCodigoTico", query = "SELECT t FROM TipoDocumento t WHERE t.codigoTico = :codigoTico"), 
  @NamedQuery(name = "TipoDocumento.findByNombreTico", query = "SELECT t FROM TipoDocumento t WHERE t.nombreTico = :nombreTico"), 
  @NamedQuery(name = "TipoDocumento.findByIdentificadorTico", query = "SELECT t FROM TipoDocumento t WHERE t.identificadorTico = :identificadorTico"), 
  @NamedQuery(name = "TipoDocumento.findByEstadoTico", query = "SELECT t FROM TipoDocumento t WHERE t.estadoTico = :estadoTico")
})
public class TipoDocumento implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_tico")
  private Integer codigoTico;
  @Basic(optional = false)
  @Column(name = "nombre_tico")
  private String nombreTico;
  @Basic(optional = false)
  @Column(name = "identificador_tico")
  private String identificadorTico;
  @Column(name = "estado_tico")
  private String estadoTico;
  
  public TipoDocumento() {}
  
  public TipoDocumento(Integer codigoTico) {
    this.codigoTico = codigoTico;
  }
  
  public TipoDocumento(Integer codigoTico, String nombreTico, String identificadorTico) {
    this.codigoTico = codigoTico;
    this.nombreTico = nombreTico;
    this.identificadorTico = identificadorTico;
  }
  
  public Integer getCodigoTico() {
    return this.codigoTico;
  }
  
  public void setCodigoTico(Integer codigoTico) {
    this.codigoTico = codigoTico;
  }
  
  public String getNombreTico() {
    return this.nombreTico;
  }
  
  public void setNombreTico(String nombreTico) {
    this.nombreTico = nombreTico;
  }
  
  public String getIdentificadorTico() {
    return this.identificadorTico;
  }
  
  public void setIdentificadorTico(String identificadorTico) {
    this.identificadorTico = identificadorTico;
  }
  
  public String getEstadoTico() {
    return this.estadoTico;
  }
  
  public void setEstadoTico(String estadoTico) {
    this.estadoTico = estadoTico;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoTico != null ? this.codigoTico.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof TipoDocumento)) {
      return false;
    }
    TipoDocumento other = (TipoDocumento)object;
    if ((this.codigoTico == null && other.codigoTico != null) || (this.codigoTico != null && !this.codigoTico.equals(other.codigoTico))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.TipoDocumento[ codigoTico=" + this.codigoTico + " ]";
  }
}