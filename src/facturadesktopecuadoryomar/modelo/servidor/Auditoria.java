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
@Table(name = "auditoria", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Auditoria.findAll", query = "SELECT a FROM Auditoria a"), 
  @NamedQuery(name = "Auditoria.findByCodigoAudi", query = "SELECT a FROM Auditoria a WHERE a.codigoAudi = :codigoAudi"), 
  @NamedQuery(name = "Auditoria.findByTipoAudi", query = "SELECT a FROM Auditoria a WHERE a.tipoAudi = :tipoAudi"), 
  @NamedQuery(name = "Auditoria.findByDatosAnterioresAudi", query = "SELECT a FROM Auditoria a WHERE a.datosAnterioresAudi = :datosAnterioresAudi"), 
  @NamedQuery(name = "Auditoria.findByDatosNuevosAudi", query = "SELECT a FROM Auditoria a WHERE a.datosNuevosAudi = :datosNuevosAudi"), 
  @NamedQuery(name = "Auditoria.findByUsuarioAudi", query = "SELECT a FROM Auditoria a WHERE a.usuarioAudi = :usuarioAudi"), 
  @NamedQuery(name = "Auditoria.findByFechaAudi", query = "SELECT a FROM Auditoria a WHERE a.fechaAudi = :fechaAudi")})
public class Auditoria implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_audi")
  private Long codigoAudi;
  @Basic(optional = false)
  @Column(name = "tipo_audi")
  private String tipoAudi;
  @Column(name = "datos_anteriores_audi")
  private String datosAnterioresAudi;
  @Column(name = "datos_nuevos_audi")
  private String datosNuevosAudi;
  @Basic(optional = false)
  @Column(name = "usuario_audi")
  private String usuarioAudi;
  @Basic(optional = false)
  @Column(name = "fecha_audi")
  @Temporal(TemporalType.DATE)
  private Date fechaAudi;
  
  public Auditoria() {}
  
  public Auditoria(Long codigoAudi) {
    this.codigoAudi = codigoAudi;
  }
  
  public Auditoria(Long codigoAudi, String tipoAudi, String usuarioAudi, Date fechaAudi) {
    this.codigoAudi = codigoAudi;
    this.tipoAudi = tipoAudi;
    this.usuarioAudi = usuarioAudi;
    this.fechaAudi = fechaAudi;
  }
  
  public Long getCodigoAudi() {
    return this.codigoAudi;
  }
  
  public void setCodigoAudi(Long codigoAudi) {
    this.codigoAudi = codigoAudi;
  }
  
  public String getTipoAudi() {
    return this.tipoAudi;
  }
  
  public void setTipoAudi(String tipoAudi) {
    this.tipoAudi = tipoAudi;
  }
  
  public String getDatosAnterioresAudi() {
    return this.datosAnterioresAudi;
  }
  
  public void setDatosAnterioresAudi(String datosAnterioresAudi) {
    this.datosAnterioresAudi = datosAnterioresAudi;
  }
  
  public String getDatosNuevosAudi() {
    return this.datosNuevosAudi;
  }
  
  public void setDatosNuevosAudi(String datosNuevosAudi) {
    this.datosNuevosAudi = datosNuevosAudi;
  }
  
  public String getUsuarioAudi() {
    return this.usuarioAudi;
  }
  
  public void setUsuarioAudi(String usuarioAudi) {
    this.usuarioAudi = usuarioAudi;
  }
  
  public Date getFechaAudi() {
    return this.fechaAudi;
  }
  
  public void setFechaAudi(Date fechaAudi) {
    this.fechaAudi = fechaAudi;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoAudi != null) ? this.codigoAudi.hashCode() : 0;
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Auditoria)) {
      return false;
    }
    Auditoria other = (Auditoria)object;
    if ((this.codigoAudi == null && other.codigoAudi != null) || (this.codigoAudi != null && !this.codigoAudi.equals(other.codigoAudi))) {
      return false;
    }
    return true;
  }

  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Auditoria[ codigoAudi=" + this.codigoAudi + " ]";
  }
}