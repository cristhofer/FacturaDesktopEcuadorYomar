package facturadesktopecuadoryomar.utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Propiedades extends Properties {
  private String file;
  
  public void loadFromFile(String file) throws IOException {
    this.file = file;
    File f = new File(file);
    if (!f.exists()) {
      f.createNewFile();
    }
    this.load(new FileInputStream(f));
  }
  
  public void saveProperties() throws FileNotFoundException {
    FileOutputStream salida = new FileOutputStream(new File(this.file));
    this.save(salida, "");
  }
}