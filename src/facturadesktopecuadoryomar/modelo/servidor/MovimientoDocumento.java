package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "movimiento_documento")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "MovimientoDocumento.findAll", query = "SELECT m FROM MovimientoDocumento m"), 
  @NamedQuery(name = "MovimientoDocumento.findById", query = "SELECT m FROM MovimientoDocumento m WHERE m.id = :id"), 
  @NamedQuery(name = "MovimientoDocumento.findByFecha", query = "SELECT m FROM MovimientoDocumento m WHERE m.fecha = :fecha"), 
  @NamedQuery(name = "MovimientoDocumento.findByTipo", query = "SELECT m FROM MovimientoDocumento m WHERE m.tipo = :tipo"), 
  @NamedQuery(name = "MovimientoDocumento.findByUsuario", query = "SELECT m FROM MovimientoDocumento m WHERE m.usuario = :usuario"), 
  @NamedQuery(name = "MovimientoDocumento.findByObservacion", query = "SELECT m FROM MovimientoDocumento m WHERE m.observacion = :observacion")
})
public class MovimientoDocumento implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  @Column(name = "fecha")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;
  @Column(name = "tipo")
  private String tipo;
  @Column(name = "usuario")
  private String usuario;
  @Column(name = "observacion")
  private String observacion;
  @Column(name = "clave_acceso_docu")
  private String claveAccesoDocu;
  
  public MovimientoDocumento() {}
  
  public MovimientoDocumento(Long id) {
    this.id = id;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Date getFecha() {
    return this.fecha;
  }
  
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
  
  public String getTipo() {
    return this.tipo;
  }
  
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  
  public String getUsuario() {
    return this.usuario;
  }
  
  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }
  
  public String getObservacion() {
    return this.observacion;
  }
  
  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof MovimientoDocumento)) {
      return false;
    }
    MovimientoDocumento other = (MovimientoDocumento)object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.optimusb.facturadesktopecuador.modelo.servidor.MovimientoDocumento_1[ id=" + this.id + " ]";
  }
  
  public String getClaveAccesoDocu() {
    return this.claveAccesoDocu;
  }
  
  public void setClaveAccesoDocu(String claveAccesoDocu) {
    this.claveAccesoDocu = claveAccesoDocu;
  }
}