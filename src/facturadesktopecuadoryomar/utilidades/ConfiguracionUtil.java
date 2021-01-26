package facturadesktopecuadoryomar.utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfiguracionUtil
{
  public Map<String, String> getConfiguracion(String nombreArchivo) throws IOException {
    Map<String, String> config = new HashMap<>();
    File f = new File(nombreArchivo + ".fe");
    if (!f.exists()) {
      try {
        f.createNewFile();
      } catch (IOException ex) {
        throw new IOException("No se ha podido crear el archivo", ex);
      } 
    } else {
      List<String> contenido = getContenidoFile(f);
      for (String c : contenido) {
        String dato[] = c.split("=");
        config.put(dato[0], dato[1]);
      } 
    } 
    return config;
  }
  
  public void setConfiguracion(Map<String, String> configuracion, String nombreArchivo) throws IOException {
    File f = new File(nombreArchivo + ".fe");
    if (!f.exists()) {
      try {
        f.createNewFile();
      } catch (IOException ex) {
        throw new IOException("No se ha podido crear el archivo", ex);
      } 
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      f.renameTo(new File(nombreArchivo + sdf.format(new Date()) + ".fe"));
      f = new File(nombreArchivo + ".fe");
      try {
        f.createNewFile();
      } catch (IOException ex) {
        throw new IOException("No se ha podido crear el archivo", ex);
      } 
    } 
    RandomAccessFile raf = new RandomAccessFile(f, "rw");
    for (Map.Entry<String, String> entrySet : configuracion.entrySet()) {
      StringBuilder sb = new StringBuilder();
      sb.append(entrySet.getKey()).append("=").append(entrySet.getValue());
      raf.writeBytes(sb.toString());
    } 
    raf.close();
  }
  
  private List<String> getContenidoFile(File f) throws FileNotFoundException, IOException {
    List<String> contenido = new LinkedList<>();
    RandomAccessFile raf = new RandomAccessFile(f, "r");
    String aux = "";
    while (aux != null) {
      aux = raf.readLine();
      contenido.add(aux);
    } 
    raf.close();
    return contenido;
  }
}