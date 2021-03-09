/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Id;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.List;

/**
 *
 * @author ctapia
 */

//ETIQUETA_ADICIONAL
@Entity
@Table(name = "ETIQUETA_ADICIONAL", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(
    name = "TipoAgente.findByCodigoTipoAgente", 
    query = "SELECT ta FROM TipoAgente ta WHERE ta.codigoTipoAgente = :codigoTipoAgente"
  )
})

public class EtiquetaAdicional implements Serializable {
  @Id  
  @Basic(optional = false)
  @Column(name = "CODIGO_LBL")
  private Integer codigoEtiquetaAdicional;
  
  @JoinColumn(name = "codigo_empr", referencedColumnName = "codigo_empr")
  @ManyToOne(optional = false)
  private Empresa empresa;
  
  @JoinColumn(name = "CODIGO_TIPOAGENTE", referencedColumnName = "CODIGO_TIPOAGENTE")
  @ManyToOne(optional = false)
  private TipoAgente tipoAgente;
  
  @Column(name = "ESTADO")
  private String estado;
  
  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;
  
  @Column(name = "FECHA_UPDATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaUpdate;
  
  @Override
  public String toString() {
      return "com.ecuasis.facturadesktopecuador.modelo.servidor.EtiquetaAdicional[ codigoEtiquetaAdicional=" + getCodigoEtiquetaAdicional() + " ]";
  }

  /**
   * @return the codigoEtiquetaAdicional
   */
  public Integer getCodigoEtiquetaAdicional() {
    return codigoEtiquetaAdicional;
  }

  /**
   * @param codigoEtiquetaAdicional the codigoEtiquetaAdicional to set
   */
  public void setCodigoEtiquetaAdicional(Integer codigoEtiquetaAdicional) {
    this.codigoEtiquetaAdicional = codigoEtiquetaAdicional;
  }

  /**
   * @return the empresa
   */
  public Empresa getEmpresa() {
    return empresa;
  }

  /**
   * @param empresa the empresa to set
   */
  public void setEmpresa(Empresa empresa) {
    this.empresa = empresa;
  }

  /**
   * @return the tipoAgente
   */
  public TipoAgente getTipoAgente() {
    return tipoAgente;
  }

  /**
   * @param tipoAgente the tipoAgente to set
   */
  public void setTipoAgente(TipoAgente tipoAgente) {
    this.tipoAgente = tipoAgente;
  }

  /**
   * @return the estado
   */
  public String getEstado() {
    return estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(String estado) {
    this.estado = estado;
  }

  /**
   * @return the fechaCreacion
   */
  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  /**
   * @param fechaCreacion the fechaCreacion to set
   */
  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  /**
   * @return the fechaUpdate
   */
  public Date getFechaUpdate() {
    return fechaUpdate;
  }

  /**
   * @param fechaUpdate the fechaUpdate to set
   */
  public void setFechaUpdate(Date fechaUpdate) {
    this.fechaUpdate = fechaUpdate;
  }
  
}
