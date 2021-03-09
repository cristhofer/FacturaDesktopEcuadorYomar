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
@Table(name = "procesos", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Procesos.findAll", query = "SELECT p FROM Procesos p"), 
  @NamedQuery(name = "Procesos.findByCodigoProc", query = "SELECT p FROM Procesos p WHERE p.codigoProc = :codigoProc"), 
  @NamedQuery(name = "Procesos.findByNombreProc", query = "SELECT p FROM Procesos p WHERE p.nombreProc = :nombreProc"), 
  @NamedQuery(name = "Procesos.findByEstadoProc", query = "SELECT p FROM Procesos p WHERE p.estadoProc = :estadoProc"), 
  @NamedQuery(name = "Procesos.findByObservacionProc", query = "SELECT p FROM Procesos p WHERE p.observacionProc = :observacionProc")
})
public class Procesos implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_proc")
  private Integer codigoProc;
  @Column(name = "nombre_proc")
  private String nombreProc;
  @Column(name = "estado_proc")
  private String estadoProc;
  @Column(name = "observacion_proc")
  private String observacionProc;
  
  public Procesos() {}
  
  public Procesos(Integer codigoProc) {
    this.codigoProc = codigoProc;
  }
  
  public Integer getCodigoProc() {
    return this.codigoProc;
  }
  
  public void setCodigoProc(Integer codigoProc) {
    this.codigoProc = codigoProc;
  }
  
  public String getNombreProc() {
    return this.nombreProc;
  }
  
  public void setNombreProc(String nombreProc) {
    this.nombreProc = nombreProc;
  }
  
  public String getEstadoProc() {
    return this.estadoProc;
  }
  
  public void setEstadoProc(String estadoProc) {
    this.estadoProc = estadoProc;
  }
  
  public String getObservacionProc() {
    return this.observacionProc;
  }
  
  public void setObservacionProc(String observacionProc) {
    this.observacionProc = observacionProc;
  }

  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoProc != null ? this.codigoProc.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Procesos)) {
      return false;
    }
    Procesos other = (Procesos)object;
    if ((this.codigoProc == null && other.codigoProc != null) || (this.codigoProc != null && !this.codigoProc.equals(other.codigoProc))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Procesos[ codigoProc=" + this.codigoProc + " ]";
  }
}