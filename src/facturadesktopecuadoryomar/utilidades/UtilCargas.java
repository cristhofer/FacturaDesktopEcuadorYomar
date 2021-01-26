package facturadesktopecuadoryomar.utilidades;

public class UtilCargas
{
  public static String obtenerInfo(String datoCarga, int indice) {
    return datoCarga.split("\\|")[indice];
  }
}