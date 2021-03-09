package mail.util;

import facturadesktopecuadoryomar.modelo.servidor.ViewDocumento;

public class UtilHtml
{
  public static String importarDatos(ViewDocumento d, String plantilla) {
    String texto = plantilla;
    return texto;
  }
  
  private static String mes(String fecha) {
    String mes = "";
    String[] f = fecha.split("/");
    int m = Integer.parseInt(f[1]);
    switch (m) {
      case 1:
        return "ENERO";
      case 2:
        return "FEBRERO";
      case 3:
        return "MARZO";
      case 4:
        return "ABRIL";
      case 5:
        return "MAYO";
      case 6:
        return "JUNIO";
      case 7:
        return "JULIO";
      case 8:
        return "AGOSTO";
      case 9:
        return "SEPTIEMBRE";
      case 10:
        return "OCTUBRE";
      case 11:
        return "NOVIEMBRE";
      case 12:
        return "DICIEMBRE";
    } 
    return mes;
  }
}