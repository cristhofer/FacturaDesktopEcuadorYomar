package facturadesktopecuadoryomar.modelo.cliente;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Carga
{
  private long codigo;
  private String establecimiento;
  private String puntoEmision;
  private String secuencial;
  private String tipoDocumento;
  private String codigoCliente;
  private String informacion;
  private Date fechaEmision;
  private int tipoEnvio;
  private String estado;
  private List<String> listaInformacion;
  private String numeroDocumento;
  private String tipo;
  
  public Carga() {}
  
  public Carga(long codigo, String establecimiento, String puntoEmision, String secuencial, String tipoDocumento, String codigoCliente, String informacion, Date fechaEmision, int tipoEnvio, String estado) {
    this.codigo = codigo;
    this.establecimiento = establecimiento;
    this.puntoEmision = puntoEmision;
    this.secuencial = secuencial;
    this.tipoDocumento = tipoDocumento;
    this.codigoCliente = codigoCliente;
    this.informacion = informacion;
    this.fechaEmision = fechaEmision;
    this.tipoEnvio = tipoEnvio;
    this.estado = estado;
  }
  
  public long getCodigo() {
    return this.codigo;
  }
  
  public void setCodigo(long codigo) {
    this.codigo = codigo;
  }
  
  public String getEstablecimiento() {
    return this.establecimiento;
  }
  
  public void setEstablecimiento(String establecimiento) {
    this.establecimiento = establecimiento;
  }
  
  public String getPuntoEmision() {
    return this.puntoEmision;
  }
  
  public void setPuntoEmision(String puntoEmision) {
    this.puntoEmision = puntoEmision;
  }
  
  public String getSecuencial() {
    return this.secuencial;
  }
  
  public void setSecuencial(String secuencial) {
    this.secuencial = secuencial;
  }
  
  public String getTipoDocumento() {
    return this.tipoDocumento;
  }
  
  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }
  
  public String getInformacion() {
    return this.informacion;
  }
  
  public void setInformacion(String informacion) {
    this.informacion = informacion;
  }
  
  public Date getFechaEmision() {
    return this.fechaEmision;
  }
  
  public void setFechaEmision(Date fechaEmision) {
    this.fechaEmision = fechaEmision;
  }
  
  public String getEstado() {
    return this.estado;
  }
  
  public void setEstado(String estado) {
    this.estado = estado;
  }
  
  public int getTipoEnvio() {
    return this.tipoEnvio;
  }
  
  public void setTipoEnvio(int tipoEnvio) {
    this.tipoEnvio = tipoEnvio;
  }
  
  public String getCodigoCliente() {
    return this.codigoCliente;
  }
  
  public void setCodigoCliente(String codigoCliente) {
    this.codigoCliente = codigoCliente;
  }
  
  public List<String> getListaInformacion() {
    if (this.informacion != null) {
      this.listaInformacion = new LinkedList<>();
      String info = this.informacion;
      while (info.indexOf("{") != -1) {
        int inicio = info.indexOf("{");
        int fin = info.indexOf("}");
        String cadena = info.substring(inicio + 1, fin);
        info = info.replace(info.substring(inicio, fin + 1), "");
        this.listaInformacion.add(cadena);
      } 
    } 
    return this.listaInformacion;
  }
  
  public String getNumeroDocumento() {
    String sec = "";
    if (this.secuencial != null) {
      DecimalFormat decimalFormat = new DecimalFormat("000000000");
      try {
        sec = decimalFormat.format(Integer.parseInt(this.secuencial));
      } catch (NumberFormatException ex) {
        sec = null;
      } 
    } 
    if (this.puntoEmision != null && this.establecimiento != null && this.secuencial != null) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.establecimiento).append("-").append(this.puntoEmision).append("-").append(sec);
      this.numeroDocumento = sb.toString();
    } 
    return this.numeroDocumento;
  }
  
  public String getTipo() {
    return this.tipo;
  }
  
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}