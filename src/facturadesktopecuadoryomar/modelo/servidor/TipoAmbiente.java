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
@Table(name = "tipo_ambiente", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "TipoAmbiente.findAll", query = "SELECT t FROM TipoAmbiente t"), 
  @NamedQuery(name = "TipoAmbiente.findByCodigoTiam", query = "SELECT t FROM TipoAmbiente t WHERE t.codigoTiam = :codigoTiam"), 
  @NamedQuery(name = "TipoAmbiente.findByNombreTiam", query = "SELECT t FROM TipoAmbiente t WHERE t.nombreTiam = :nombreTiam"), 
  @NamedQuery(name = "TipoAmbiente.findByIdentificadorTiam", query = "SELECT t FROM TipoAmbiente t WHERE t.identificadorTiam = :identificadorTiam"), 
  @NamedQuery(name = "TipoAmbiente.findByEstadoTiam", query = "SELECT t FROM TipoAmbiente t WHERE t.estadoTiam = :estadoTiam")
})
public class TipoAmbiente implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_tiam")
  private Integer codigoTiam;
  @Basic(optional = false)
  @Column(name = "nombre_tiam")
  private String nombreTiam;
  @Basic(optional = false)
  @Column(name = "identificador_tiam")
  private String identificadorTiam;
  @Column(name = "estado_tiam")
  private String estadoTiam;
  
  public TipoAmbiente() {}
  
  public TipoAmbiente(Integer codigoTiam) {
    this.codigoTiam = codigoTiam;
  }
  
  public TipoAmbiente(Integer codigoTiam, String nombreTiam, String identificadorTiam) {
    this.codigoTiam = codigoTiam;
    this.nombreTiam = nombreTiam;
    this.identificadorTiam = identificadorTiam;
  }
  
  public Integer getCodigoTiam() {
    return this.codigoTiam;
  }
  
  public void setCodigoTiam(Integer codigoTiam) {
    this.codigoTiam = codigoTiam;
  }
  
  public String getNombreTiam() {
    return this.nombreTiam;
  }
  
  public void setNombreTiam(String nombreTiam) {
    this.nombreTiam = nombreTiam;
  }
  
  public String getIdentificadorTiam() {
    return this.identificadorTiam;
  }
  
  public void setIdentificadorTiam(String identificadorTiam) {
    this.identificadorTiam = identificadorTiam;
  }
  
  public String getEstadoTiam() {
    return this.estadoTiam;
  }
  
  public void setEstadoTiam(String estadoTiam) {
    this.estadoTiam = estadoTiam;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoTiam != null ? this.codigoTiam.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof TipoAmbiente)) {
      return false;
    }
    TipoAmbiente other = (TipoAmbiente)object;
    if ((this.codigoTiam == null && other.codigoTiam != null) || (this.codigoTiam != null && !this.codigoTiam.equals(other.codigoTiam))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.TipoAmbiente[ codigoTiam=" + this.codigoTiam + " ]";
  }
}