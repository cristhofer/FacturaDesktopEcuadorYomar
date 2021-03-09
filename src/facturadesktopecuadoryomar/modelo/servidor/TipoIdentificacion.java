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
@Table(name = "tipo_identificacion", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "TipoIdentificacion.findAll", query = "SELECT t FROM TipoIdentificacion t"), 
  @NamedQuery(name = "TipoIdentificacion.findByCodigoTiid", query = "SELECT t FROM TipoIdentificacion t WHERE t.codigoTiid = :codigoTiid"), 
  @NamedQuery(name = "TipoIdentificacion.findByNombreTiid", query = "SELECT t FROM TipoIdentificacion t WHERE t.nombreTiid = :nombreTiid"), 
  @NamedQuery(name = "TipoIdentificacion.findByIdentificadorTiid", query = "SELECT t FROM TipoIdentificacion t WHERE t.identificadorTiid = :identificadorTiid"), 
  @NamedQuery(name = "TipoIdentificacion.findByEstadoTiid", query = "SELECT t FROM TipoIdentificacion t WHERE t.estadoTiid = :estadoTiid")
})
public class TipoIdentificacion implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_tiid")
  private Integer codigoTiid;
  @Basic(optional = false)
  @Column(name = "nombre_tiid")
  private String nombreTiid;
  @Basic(optional = false)
  @Column(name = "identificador_tiid")
  private String identificadorTiid;
  @Column(name = "estado_tiid")
  private String estadoTiid;
  
  public TipoIdentificacion() {}
  
  public TipoIdentificacion(Integer codigoTiid) {
    this.codigoTiid = codigoTiid;
  }
  
  public TipoIdentificacion(Integer codigoTiid, String nombreTiid, String identificadorTiid) {
    this.codigoTiid = codigoTiid;
    this.nombreTiid = nombreTiid;
    this.identificadorTiid = identificadorTiid;
  }
  
  public Integer getCodigoTiid() {
    return this.codigoTiid;
  }
  
  public void setCodigoTiid(Integer codigoTiid) {
    this.codigoTiid = codigoTiid;
  }
  
  public String getNombreTiid() {
    return this.nombreTiid;
  }
  
  public void setNombreTiid(String nombreTiid) {
    this.nombreTiid = nombreTiid;
  }
  
  public String getIdentificadorTiid() {
    return this.identificadorTiid;
  }
  
  public void setIdentificadorTiid(String identificadorTiid) {
    this.identificadorTiid = identificadorTiid;
  }
  
  public String getEstadoTiid() {
    return this.estadoTiid;
  }
  
  public void setEstadoTiid(String estadoTiid) {
    this.estadoTiid = estadoTiid;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoTiid != null ? this.codigoTiid.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof TipoIdentificacion)) {
      return false;
    }
    TipoIdentificacion other = (TipoIdentificacion)object;
    if ((this.codigoTiid == null && other.codigoTiid != null) || (this.codigoTiid != null && !this.codigoTiid.equals(other.codigoTiid))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.TipoIdentificacion[ codigoTiid=" + this.codigoTiid + " ]";
  }
}