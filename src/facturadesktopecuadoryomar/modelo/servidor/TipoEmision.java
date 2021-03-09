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
@Table(name = "tipo_emision", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "TipoEmision.findAll", query = "SELECT t FROM TipoEmision t"), 
  @NamedQuery(name = "TipoEmision.findByCodigoTiem", query = "SELECT t FROM TipoEmision t WHERE t.codigoTiem = :codigoTiem"), 
  @NamedQuery(name = "TipoEmision.findByDescripcionTiem", query = "SELECT t FROM TipoEmision t WHERE t.descripcionTiem = :descripcionTiem"), 
  @NamedQuery(name = "TipoEmision.findByEstadoTiem", query = "SELECT t FROM TipoEmision t WHERE t.estadoTiem = :estadoTiem"), 
  @NamedQuery(name = "TipoEmision.findByIdentificadorTiem", query = "SELECT t FROM TipoEmision t WHERE t.identificadorTiem = :identificadorTiem")
})
public class TipoEmision implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_tiem")
  private Integer codigoTiem;
  @Basic(optional = false)
  @Column(name = "descripcion_tiem")
  private String descripcionTiem;
  @Column(name = "estado_tiem")
  private String estadoTiem;
  @Column(name = "identificador_tiem")
  private String identificadorTiem;
  
  public TipoEmision() {}
  
  public TipoEmision(Integer codigoTiem) {
    this.codigoTiem = codigoTiem;
  }
  
  public TipoEmision(Integer codigoTiem, String descripcionTiem) {
    this.codigoTiem = codigoTiem;
    this.descripcionTiem = descripcionTiem;
  }
  
  public Integer getCodigoTiem() {
    return this.codigoTiem;
  }
  
  public void setCodigoTiem(Integer codigoTiem) {
    this.codigoTiem = codigoTiem;
  }
  
  public String getDescripcionTiem() {
    return this.descripcionTiem;
  }
  
  public void setDescripcionTiem(String descripcionTiem) {
    this.descripcionTiem = descripcionTiem;
  }
  
  public String getEstadoTiem() {
    return this.estadoTiem;
  }
  
  public void setEstadoTiem(String estadoTiem) {
    this.estadoTiem = estadoTiem;
  }
  
  public String getIdentificadorTiem() {
    return this.identificadorTiem;
  }
  
  public void setIdentificadorTiem(String identificadorTiem) {
    this.identificadorTiem = identificadorTiem;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoTiem != null ? this.codigoTiem.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof TipoEmision)) {
      return false;
    }
    TipoEmision other = (TipoEmision)object;
    if ((this.codigoTiem == null && other.codigoTiem != null) || (this.codigoTiem != null && !this.codigoTiem.equals(other.codigoTiem))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.TipoEmision[ codigoTiem=" + this.codigoTiem + " ]";
  }
}