package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "view_permisos", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "ViewPermisos.findAll", query = "SELECT v FROM ViewPermisos v"), 
  @NamedQuery(name = "ViewPermisos.findByCodigoPerm", query = "SELECT v FROM ViewPermisos v WHERE v.codigoPerm = :codigoPerm"), 
  @NamedQuery(name = "ViewPermisos.findByNombrePerm", query = "SELECT v FROM ViewPermisos v WHERE v.nombrePerm = :nombrePerm"), 
  @NamedQuery(name = "ViewPermisos.findByUrlPerm", query = "SELECT v FROM ViewPermisos v WHERE v.urlPerm = :urlPerm"), 
  @NamedQuery(name = "ViewPermisos.findByEstadoPerm", query = "SELECT v FROM ViewPermisos v WHERE v.estadoPerm = :estadoPerm"), 
  @NamedQuery(name = "ViewPermisos.findByTipoPerm", query = "SELECT v FROM ViewPermisos v WHERE v.tipoPerm = :tipoPerm"), 
  @NamedQuery(name = "ViewPermisos.findByPadrePerm", query = "SELECT v FROM ViewPermisos v WHERE v.padrePerm = :padrePerm"), 
  @NamedQuery(name = "ViewPermisos.findByIconoPerm", query = "SELECT v FROM ViewPermisos v WHERE v.iconoPerm = :iconoPerm"), 
  @NamedQuery(name = "ViewPermisos.findByNombreRole", query = "SELECT v FROM ViewPermisos v WHERE v.nombreRole = :nombreRole"), 
  @NamedQuery(name = "ViewPermisos.findByNombreUsua", query = "SELECT v FROM ViewPermisos v WHERE v.nombreUsua = :nombreUsua"), 
  @NamedQuery(name = "ViewPermisos.findByEstadoRole", query = "SELECT v FROM ViewPermisos v WHERE v.estadoRole = :estadoRole"), 
  @NamedQuery(name = "ViewPermisos.findByPosicionPerm", query = "SELECT v FROM ViewPermisos v WHERE v.posicionPerm = :posicionPerm")
})
public class ViewPermisos implements Serializable {
  @Id
  @Column(name = "codigo_perm")
  private Integer codigoPerm;
  @Column(name = "nombre_perm")
  private String nombrePerm;
  @Column(name = "url_perm")
  private String urlPerm;
  @Column(name = "estado_perm")
  private String estadoPerm;
  @Column(name = "tipo_perm")
  private String tipoPerm;
  @Column(name = "padre_perm")
  private Integer padrePerm;
  @Column(name = "icono_perm")
  private String iconoPerm;
  @Column(name = "nombre_role")
  private String nombreRole;
  @Column(name = "nombre_usua")
  private String nombreUsua;
  @Column(name = "estado_role")
  private String estadoRole;
  @Column(name = "posicion_perm")
  private Short posicionPerm;

  public ViewPermisos() {
  }
  
  public Integer getCodigoPerm() {
    return this.codigoPerm;
  }
  
  public void setCodigoPerm(Integer codigoPerm) {
    this.codigoPerm = codigoPerm;
  }
  
  public String getNombrePerm() {
    return this.nombrePerm;
  }
  
  public void setNombrePerm(String nombrePerm) {
    this.nombrePerm = nombrePerm;
  }
  
  public String getUrlPerm() {
    return this.urlPerm;
  }
  
  public void setUrlPerm(String urlPerm) {
    this.urlPerm = urlPerm;
  }
  
  public String getEstadoPerm() {
    return this.estadoPerm;
  }
  
  public void setEstadoPerm(String estadoPerm) {
    this.estadoPerm = estadoPerm;
  }
  
  public String getTipoPerm() {
    return this.tipoPerm;
  }
  
  public void setTipoPerm(String tipoPerm) {
    this.tipoPerm = tipoPerm;
  }
  
  public Integer getPadrePerm() {
    return this.padrePerm;
  }
  
  public void setPadrePerm(Integer padrePerm) {
    this.padrePerm = padrePerm;
  }
  
  public String getIconoPerm() {
    return this.iconoPerm;
  }
  
  public void setIconoPerm(String iconoPerm) {
    this.iconoPerm = iconoPerm;
  }
  
  public String getNombreRole() {
    return this.nombreRole;
  }
  
  public void setNombreRole(String nombreRole) {
    this.nombreRole = nombreRole;
  }
  
  public String getNombreUsua() {
    return this.nombreUsua;
  }
  
  public void setNombreUsua(String nombreUsua) {
    this.nombreUsua = nombreUsua;
  }
  
  public String getEstadoRole() {
    return this.estadoRole;
  }
  
  public void setEstadoRole(String estadoRole) {
    this.estadoRole = estadoRole;
  }
  
  public Short getPosicionPerm() {
    return this.posicionPerm;
  }
  
  public void setPosicionPerm(Short posicionPerm) {
    this.posicionPerm = posicionPerm;
  }
}