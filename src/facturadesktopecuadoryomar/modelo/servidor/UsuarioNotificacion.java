package facturadesktopecuadoryomar.modelo.servidor;

import java.io.Serializable;

public class UsuarioNotificacion implements Serializable {
  private String usuario;
  private Boolean visto;
  
  public UsuarioNotificacion() {}
  
  public UsuarioNotificacion(String texto) {
    if (texto != null) {
      String[] t = texto.split(":X:");
      this.usuario = t[0];
      this.visto = Boolean.valueOf(t[1]);
    } 
  }
  
  public UsuarioNotificacion(String usuario, Boolean visto) {
    this.usuario = usuario;
    this.visto = visto;
  }
  
  public String getUsuario() {
    return this.usuario;
  }
  
  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }
  
  public Boolean getVisto() {
    return this.visto;
  }
  
  public void setVisto(Boolean visto) {
    this.visto = visto;
  }
  
  public String toString() {
    return this.usuario + ":X:" + this.visto;
  }
}