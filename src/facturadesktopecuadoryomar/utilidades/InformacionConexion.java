package facturadesktopecuadoryomar.utilidades;

public class InformacionConexion
{
  private String driver;
  private String usuario;
  private String password;
  private String url;
  
  public InformacionConexion() {}
  
  public InformacionConexion(String driver, String usuario, String password, String url) {
    this.driver = driver;
    this.usuario = usuario;
    this.password = password;
    this.url = url;
  }
  
  public String getDriver() {
    return this.driver;
  }
  
  public void setDriver(String driver) {
    this.driver = driver;
  }
  
  public String getUsuario() {
    return this.usuario;
  }
  
  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
}