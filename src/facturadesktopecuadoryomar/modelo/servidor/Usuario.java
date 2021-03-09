package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "usuario", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"), 
  @NamedQuery(name = "Usuario.login", query = "SELECT u FROM Usuario u WHERE u.nombreUsua=:usua AND u.passwordUsua=:pass AND u.estadoUsua='ACTIVO'"), 
  @NamedQuery(name = "Usuario.findByCodigoUsua", query = "SELECT u FROM Usuario u WHERE u.codigoUsua = :codigoUsua"), 
  @NamedQuery(name = "Usuario.findByNombreUsua", query = "SELECT u FROM Usuario u WHERE u.nombreUsua = :nombreUsua"), 
  @NamedQuery(name = "Usuario.findByPasswordUsua", query = "SELECT u FROM Usuario u WHERE u.passwordUsua = :passwordUsua"), 
  @NamedQuery(name = "Usuario.findByEstadoUsua", query = "SELECT u FROM Usuario u WHERE u.estadoUsua = :estadoUsua"), 
  @NamedQuery(name = "Usuario.findByCorreoUsua", query = "SELECT u FROM Usuario u WHERE u.correoUsua = :correoUsua"), 
  @NamedQuery(name = "Usuario.findByTelefonoUsua", query = "SELECT u FROM Usuario u WHERE u.telefonoUsua = :telefonoUsua"), 
  @NamedQuery(name = "Usuario.findByCelularUsua", query = "SELECT u FROM Usuario u WHERE u.celularUsua = :celularUsua"), 
  @NamedQuery(name = "Usuario.findBySubscripcionUsua", query = "SELECT u FROM Usuario u WHERE u.subscripcionUsua = :subscripcionUsua"), 
  @NamedQuery(name = "Usuario.findByNombreCompletoUsua", query = "SELECT u FROM Usuario u WHERE u.nombreCompletoUsua = :nombreCompletoUsua"), 
  @NamedQuery(name = "Usuario.findBySexoUsua", query = "SELECT u FROM Usuario u WHERE u.sexoUsua = :sexoUsua"), 
  @NamedQuery(name = "Usuario.findByIdentificacionUsua", query = "SELECT u FROM Usuario u WHERE u.identificacionUsua = :identificacionUsua"), 
  @NamedQuery(name = "Usuario.findByEsEmpresaUsua", query = "SELECT u FROM Usuario u WHERE u.esEmpresaUsua = :esEmpresaUsua")
})
public class Usuario implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "codigo_usua")
  private Long codigoUsua;
  @Basic(optional = false)
  @Column(name = "nombre_usua")
  private String nombreUsua;
  @Basic(optional = false)
  @Column(name = "password_usua")
  private String passwordUsua;
  @Column(name = "estado_usua")
  private String estadoUsua;
  @Column(name = "correo_usua")
  private String correoUsua;
  @Column(name = "telefono_usua")
  private String telefonoUsua;
  @Column(name = "celular_usua")
  private String celularUsua;
  @Column(name = "subscripcion_usua")
  private Boolean subscripcionUsua;
  @Column(name = "nombre_completo_usua")
  private String nombreCompletoUsua;
  @Column(name = "sexo_usua")
  private String sexoUsua;
  @Column(name = "identificacion_usua")
  private String identificacionUsua;
  @Column(name = "es_empresa_usua")
  private Boolean esEmpresaUsua;
  @OneToMany(mappedBy = "codigoUsua", cascade = {CascadeType.ALL})
  private List<UsuarioRol> usuarioRolList;
  @Column(name = "nombre_comercial_usua")
  private String nombreComercialUsua;
  @Column(name = "recibe_notificacion")
  private boolean recibeNotificacion;
  
  public Usuario() {}
  
  public Usuario(Long codigoUsua) {
    this.codigoUsua = codigoUsua;
  }
  
  public Usuario(Long codigoUsua, String nombreUsua, String passwordUsua, String correoUsua) {
    this.codigoUsua = codigoUsua;
    this.nombreUsua = nombreUsua;
    this.passwordUsua = passwordUsua;
    this.correoUsua = correoUsua;
  }
  
  public Long getCodigoUsua() {
    return this.codigoUsua;
  }
  
  public void setCodigoUsua(Long codigoUsua) {
    this.codigoUsua = codigoUsua;
  }
  
  public String getNombreUsua() {
    return this.nombreUsua;
  }
  
  public void setNombreUsua(String nombreUsua) {
    this.nombreUsua = nombreUsua;
  }
  
  public String getPasswordUsua() {
    return this.passwordUsua;
  }
  
  public void setPasswordUsua(String passwordUsua) {
    this.passwordUsua = passwordUsua;
  }
  
  public String getEstadoUsua() {
    return this.estadoUsua;
  }
  
  public void setEstadoUsua(String estadoUsua) {
    this.estadoUsua = estadoUsua;
  }
  
  public String getCorreoUsua() {
    return this.correoUsua;
  }
  
  public void setCorreoUsua(String correoUsua) {
    this.correoUsua = correoUsua;
  }
  
  public String getTelefonoUsua() {
    return this.telefonoUsua;
  }
  
  public void setTelefonoUsua(String telefonoUsua) {
    this.telefonoUsua = telefonoUsua;
  }
  
  public String getCelularUsua() {
    return this.celularUsua;
  }
  
  public void setCelularUsua(String celularUsua) {
    this.celularUsua = celularUsua;
  }
  
  public Boolean getSubscripcionUsua() {
    return this.subscripcionUsua;
  }
  
  public void setSubscripcionUsua(Boolean subscripcionUsua) {
    this.subscripcionUsua = subscripcionUsua;
  }
  
  public String getNombreCompletoUsua() {
    return this.nombreCompletoUsua;
  }
  
  public void setNombreCompletoUsua(String nombreCompletoUsua) {
    this.nombreCompletoUsua = nombreCompletoUsua;
  }
  
  public String getSexoUsua() {
    return this.sexoUsua;
  }
  
  public void setSexoUsua(String sexoUsua) {
    this.sexoUsua = sexoUsua;
  }
  
  public String getIdentificacionUsua() {
    return this.identificacionUsua;
  }
  
  public void setIdentificacionUsua(String identificacionUsua) {
    this.identificacionUsua = identificacionUsua;
  }
  
  public Boolean getEsEmpresaUsua() {
    return this.esEmpresaUsua;
  }
  
  public void setEsEmpresaUsua(Boolean esEmpresaUsua) {
    this.esEmpresaUsua = esEmpresaUsua;
  }
  
  @XmlTransient
  public List<UsuarioRol> getUsuarioRolList() {
    return this.usuarioRolList;
  }
  
  public void setUsuarioRolList(List<UsuarioRol> usuarioRolList) {
    this.usuarioRolList = usuarioRolList;
  }
  
  public int hashCode() {
    int hash = 0;
    hash += (this.codigoUsua != null ? this.codigoUsua.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Usuario)) {
      return false;
    }
    Usuario other = (Usuario)object;
    if ((this.codigoUsua == null && other.codigoUsua != null) || (this.codigoUsua != null && !this.codigoUsua.equals(other.codigoUsua))) {
      return false;
    }
    return true;
  }
  
  public String toString() {
    return "com.ecuasis.facturadesktopecuador.modelo.servidor.Usuario[ codigoUsua=" + this.codigoUsua + " ]";
  }
  
  public String getNombreComercialUsua() {
    return this.nombreComercialUsua;
  }
  
  public void setNombreComercialUsua(String nombreComercialUsua) {
    this.nombreComercialUsua = nombreComercialUsua;
  }
  
  public boolean getRecibeNotificacion() {
    return this.recibeNotificacion;
  }
  
  public void setRecibeNotificacion(boolean recibeNotificacion) {
    this.recibeNotificacion = recibeNotificacion;
  }
}