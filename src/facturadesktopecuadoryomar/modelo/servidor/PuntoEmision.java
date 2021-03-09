package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "punto_emision", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "PuntoEmision.findAll", query = "SELECT p FROM PuntoEmision p"), 
  @NamedQuery(name = "PuntoEmision.findByCodigoPtem", query = "SELECT p FROM PuntoEmision p WHERE p.codigoPtem = :codigoPtem"), 
  @NamedQuery(name = "PuntoEmision.findByIdentificadorEstab", query = "SELECT p FROM PuntoEmision p WHERE p.identificadorEstab = :identificadorEstab"), 
  @NamedQuery(name = "PuntoEmision.findByEstadoEstab", query = "SELECT p FROM PuntoEmision p WHERE p.estadoEstab = :estadoEstab")
})
public class PuntoEmision implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_ptem")
  private Integer codigoPtem;
  @Basic(optional = false)
  @Column(name = "identificador_estab")
  private String identificadorEstab;
  @Column(name = "estado_estab")
  private String estadoEstab;
  @JoinColumn(name = "codigo_estab", referencedColumnName = "codigo_estab")
  @ManyToOne(optional = false)
  private Establecimiento codigoEstab;
  
  public PuntoEmision() {}
  
  public PuntoEmision(Integer codigoPtem) {
    this.codigoPtem = codigoPtem;
  }
  
  public PuntoEmision(Integer codigoPtem, String identificadorEstab) {
    this.codigoPtem = codigoPtem;
    this.identificadorEstab = identificadorEstab;
  }
  
  public Integer getCodigoPtem() {
    return this.codigoPtem;
  }
  
  public void setCodigoPtem(Integer codigoPtem) {
    this.codigoPtem = codigoPtem;
  }
  
  public String getIdentificadorEstab() {
    return this.identificadorEstab;
  }
  
  public void setIdentificadorEstab(String identificadorEstab) {
    this.identificadorEstab = identificadorEstab;
  }
  
  public String getEstadoEstab() {
    return this.estadoEstab;
  }
  
  public void setEstadoEstab(String estadoEstab) {
    this.estadoEstab = estadoEstab;
  }
  
  public Establecimiento getCodigoEstab() {
    return this.codigoEstab;
  }
  
  public void setCodigoEstab(Establecimiento codigoEstab) {
    this.codigoEstab = codigoEstab;
  }

  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoPtem != null ? this.codigoPtem.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof PuntoEmision)) {
      return false;
    }
    PuntoEmision other = (PuntoEmision)object;
    if ((this.codigoPtem == null && other.codigoPtem != null) || (this.codigoPtem != null && !this.codigoPtem.equals(other.codigoPtem))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.PuntoEmision[ codigoPtem=" + this.codigoPtem + " ]";
  }
}