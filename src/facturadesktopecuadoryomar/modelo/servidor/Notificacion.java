package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "notificacion", schema = "dbo")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Notificacion.findAll", query = "SELECT n FROM Notificacion n"), 
  @NamedQuery(name = "Notificacion.findByUser", query = "SELECT n FROM Notificacion n WHERE n.usuarios like :user ORDER BY n.id DESC")
})
public class Notificacion implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;
  @Column(name = "titulo")
  private String titulo;
  @Column(name = "descripcion_corta")
  private String descripcionCorta;
  @Column(name = "descripcion_larga")
  private String descripcionLarga;
  @Column(name = "usuarios")
  private String usuarios;
  @Column(name = "estado")
  private String estado;
  @Column(name = "empresa")
  private String empresa;
  @Column(name = "clave_acceso")
  private String claveAcceso;
  @Column(name = "numero_documento")
  private String numeroDocumento;
  @Column(name = "codigo_safpro")
  private String codigoSafpro;
  @Column(name = "cliente")
  private String cliente;
  @Column(name = "tipo_safpro")
  private String tipoSafpro;
  @Transient
  private List<UsuarioNotificacion> listaUsuario;
  
  public Notificacion() {}
  
  public Notificacion(Long id) {
    this.id = id;
  }
  
  public Notificacion(String titulo, String descripcionCorta, String descripcionLarga, String usuarios) {
    this.titulo = titulo;
    this.descripcionCorta = descripcionCorta;
    this.descripcionLarga = descripcionLarga;
    this.usuarios = usuarios;
  }
  
  public Notificacion(String titulo, String descripcionCorta, String usuarios) {
    this.titulo = titulo;
    this.descripcionCorta = descripcionCorta;
    this.usuarios = usuarios;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTitulo() {
    return this.titulo;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  
  public String getDescripcionCorta() {
    return this.descripcionCorta;
  }
  
  public void setDescripcionCorta(String descripcionCorta) {
    this.descripcionCorta = descripcionCorta;
  }
  
  public String getDescripcionLarga() {
    return this.descripcionLarga;
  }
  
  public void setDescripcionLarga(String descripcionLarga) {
    this.descripcionLarga = descripcionLarga;
  }
  
  public String getUsuarios() {
    return this.usuarios;
  }
  
  public void setUsuarios(String usuarios) {
    this.usuarios = usuarios;
  }
  
  public String getEstado() {
    return this.estado;
  }
  
  public void setEstado(String estado) {
    this.estado = estado;
  }
  
  public String getEmpresa() {
    return this.empresa;
  }
  
  public void setEmpresa(String empresa) {
    this.empresa = empresa;
  }
  
  public String getClaveAcceso() {
    return this.claveAcceso;
  }
  
  public void setClaveAcceso(String claveAcceso) {
    this.claveAcceso = claveAcceso;
  }
  
  public String getNumeroDocumento() {
    return this.numeroDocumento;
  }
  
  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }
  
  public List<UsuarioNotificacion> getListaUsuario() {
    this.listaUsuario = new LinkedList<>();
    if (this.usuarios != null) {
      String s[] = this.usuarios.split(";X;");
      for (String x : s) {
        this.listaUsuario.add(new UsuarioNotificacion(x));
      }
    } 
    return this.listaUsuario;
  }
  
  public String getCodigoSafpro() {
    return this.codigoSafpro;
  }
  
  public void setCodigoSafpro(String codigoSafpro) {
    this.codigoSafpro = codigoSafpro;
  }
  
  public String getCliente() {
    return this.cliente;
  }
  
  public void setCliente(String cliente) {
    this.cliente = cliente;
  }
  
  public String getTipoSafpro() {
    return this.tipoSafpro;
  }
  
  public void setTipoSafpro(String tipoSafpro) {
    this.tipoSafpro = tipoSafpro;
  }
}