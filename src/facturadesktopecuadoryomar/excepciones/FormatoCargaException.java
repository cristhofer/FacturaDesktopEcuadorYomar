package facturadesktopecuadoryomar.excepciones;

public class FormatoCargaException
  extends Exception
{
  private final String carga;
  private final String tag;
  private final int posicion;
  private final Object valor;
  private final String msg;
  
  public FormatoCargaException(String tag, String carga, int posicion) {
    this.carga = carga;
    this.tag = tag;
    this.posicion = posicion;
    this.valor = null;
    this.msg = null;
  }
  
  public FormatoCargaException(String carga, String tag, String msg, Object valor) {
    this.carga = carga;
    this.tag = tag;
    this.posicion = -1;
    this.valor = valor;
    this.msg = msg;
  }

  
  public String getMessage() {
    StringBuilder sberror = new StringBuilder();
    if (this.valor != null) {
      if (this.valor.equals("msg")) {
        sberror.append(this.msg);
      } else {
        sberror.append("No se encuentra en la carga la informacion de : [");
        sberror.append(this.tag);
        sberror.append("] que deberia estar en la posicion: [");
        sberror.append(this.posicion);
        sberror.append("] de la carga : ");
        sberror.append(this.carga);
      } 
    } else {
      sberror.append(this.msg);
      sberror.append(": ");
      sberror.append(this.valor);
      sberror.append(", correspondiente a: ");
      sberror.append(this.tag);
      sberror.append(" de la carga: ");
      sberror.append(this.carga);
    } 
    return sberror.toString();
  }
}