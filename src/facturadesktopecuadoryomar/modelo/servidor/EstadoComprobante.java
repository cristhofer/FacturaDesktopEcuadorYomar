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
@Table(name = "estado_comprobante", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "EstadoComprobante.findAll", query = "SELECT e FROM EstadoComprobante e"), 
  @NamedQuery(name = "EstadoComprobante.findByCodigoEsco", query = "SELECT e FROM EstadoComprobante e WHERE e.codigoEsco = :codigoEsco"), 
  @NamedQuery(name = "EstadoComprobante.findByDescripcionEsco", query = "SELECT e FROM EstadoComprobante e WHERE e.descripcionEsco = :descripcionEsco"), 
  @NamedQuery(name = "EstadoComprobante.findByIdentificadorEsco", query = "SELECT e FROM EstadoComprobante e WHERE e.identificadorEsco = :identificadorEsco"), 
  @NamedQuery(name = "EstadoComprobante.findByEstadoEsco", query = "SELECT e FROM EstadoComprobante e WHERE e.estadoEsco = :estadoEsco")
})
public class EstadoComprobante implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_esco")
  private Integer codigoEsco;
  @Basic(optional = false)
  @Column(name = "descripcion_esco")
  private String descripcionEsco;
  @Basic(optional = false)
  @Column(name = "identificador_esco")
  private String identificadorEsco;
  @Column(name = "estado_esco")
  private String estadoEsco;
  
  public EstadoComprobante() {}
  
  public EstadoComprobante(Integer codigoEsco) {
    this.codigoEsco = codigoEsco;
  }
  
  public EstadoComprobante(Integer codigoEsco, String descripcionEsco, String identificadorEsco) {
    this.codigoEsco = codigoEsco;
    this.descripcionEsco = descripcionEsco;
    this.identificadorEsco = identificadorEsco;
  }
  
  public Integer getCodigoEsco() {
    return this.codigoEsco;
  }
  
  public void setCodigoEsco(Integer codigoEsco) {
    this.codigoEsco = codigoEsco;
  }
  
  public String getDescripcionEsco() {
    return this.descripcionEsco;
  }
  
  public void setDescripcionEsco(String descripcionEsco) {
    this.descripcionEsco = descripcionEsco;
  }
  
  public String getIdentificadorEsco() {
    return this.identificadorEsco;
  }
  
  public void setIdentificadorEsco(String identificadorEsco) {
    this.identificadorEsco = identificadorEsco;
  }
  
  public String getEstadoEsco() {
    return this.estadoEsco;
  }
  
  public void setEstadoEsco(String estadoEsco) {
    this.estadoEsco = estadoEsco;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoEsco != null ? this.codigoEsco.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof EstadoComprobante)) {
      return false;
    }
    EstadoComprobante other = (EstadoComprobante)object;
    if ((this.codigoEsco == null && other.codigoEsco != null) || (this.codigoEsco != null && !this.codigoEsco.equals(other.codigoEsco))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.EstadoComprobante[ codigoEsco=" + this.codigoEsco + " ]";
  }
}