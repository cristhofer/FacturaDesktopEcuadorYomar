package facturadesktopecuadoryomar.modelo.servidor;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.CascadeType;

/**
 *
 * @author CRISTHOFER JONATHAN TAPIA MORENO
 */

//TIPO_AGENTERET
@Entity
@Table(name = "TIPO_AGENTERET", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(
    name = "TipoAgente.findByCodigoTipoAgente", 
    query = "SELECT ta FROM TipoAgente ta WHERE ta.codigoTipoAgente = :codigoTipoAgente"
  )
})

public class TipoAgente implements Serializable {
  @Id  
  @Basic(optional = false)
  @Column(name = "CODIGO_TIPOAGENTE")
  private Integer codigoTipoAgente;
  @Column(name = "LEYENDA_TIPOAGENTE")
  private String leyendaTipoAgente;   
  @Column(name = "ESTADO_TIPOAGENTE")
  private String estadoTipoAgente;
  @Column(name = "ETIQUETA_TIPOAGENTE")
  private String etiquetaTipoAgente;
  @Column(name = "FECHA_ACTIVACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActivacion;
  @Column(name = "FECHA_INACTIVACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInactivacion;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoAgente")
  private List<EtiquetaAdicional> etiquetaList;

  @Override
  public String toString() {
      return String.join(
        "", 
        "TipoAgenteRet(codigo:" + codigoTipoAgente + ", leyenda: " + leyendaTipoAgente,
         ", estado: " + estadoTipoAgente + ", etiqueta: " + etiquetaTipoAgente,
        ", fecha activación: " + fechaInactivacion + ", fecha inactivación: " + fechaInactivacion
      );
  }

  /**
   * @return the codigoTipoAgente
   */
  public Integer getCodigoTipoAgente() {
    return codigoTipoAgente;
  }

  /**
   * @param codigoTipoAgente the codigoTipoAgente to set
   */
  public void setCodigoTipoAgente(Integer codigoTipoAgente) {
    this.codigoTipoAgente = codigoTipoAgente;
  }

  /**
   * @return the leyendaTipoAgente
   */
  public String getLeyendaTipoAgente() {
    return leyendaTipoAgente;
  }

  /**
   * @param leyendaTipoAgente the leyendaTipoAgente to set
   */
  public void setLeyendaTipoAgente(String leyendaTipoAgente) {
    this.leyendaTipoAgente = leyendaTipoAgente;
  }

  /**
   * @return the estadoTipoAgente
   */
  public String getEstadoTipoAgente() {
    return estadoTipoAgente;
  }

  /**
   * @param estadoTipoAgente the estadoTipoAgente to set
   */
  public void setEstadoTipoAgente(String estadoTipoAgente) {
    this.estadoTipoAgente = estadoTipoAgente;
  }

  /**
   * @return the etiquetaTipoAgente
   */
  public String getEtiquetaTipoAgente() {
    return etiquetaTipoAgente;
  }

  /**
   * @param etiquetaTipoAgente the etiquetaTipoAgente to set
   */
  public void setEtiquetaTipoAgente(String etiquetaTipoAgente) {
    this.etiquetaTipoAgente = etiquetaTipoAgente;
  }

  /**
   * @return the fechaActivacion
   */
  public Date getFechaActivacion() {
    return fechaActivacion;
  }

  /**
   * @param fechaActivacion the fechaActivacion to set
   */
  public void setFechaActivacion(Date fechaActivacion) {
    this.fechaActivacion = fechaActivacion;
  }

  /**
   * @return the fechaInactivacion
   */
  public Date getFechaInactivacion() {
    return fechaInactivacion;
  }

  /**
   * @param fechaInactivacion the fechaInactivacion to set
   */
  public void setFechaInactivacion(Date fechaInactivacion) {
    this.fechaInactivacion = fechaInactivacion;
  }
}
