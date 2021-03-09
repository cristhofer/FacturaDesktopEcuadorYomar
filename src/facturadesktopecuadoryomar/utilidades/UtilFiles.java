package facturadesktopecuadoryomar.utilidades;

import com.ecuasis.coresri.util.UtilSRI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

public class UtilFiles
{
  public static void EcribirFichero(File xml, String SCadena) throws IOException, TransformerConfigurationException, TransformerException, SAXException, ParserConfigurationException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(UtilSRI.convertStringToDocument(SCadena));
    if (!xml.exists()) {
      xml.createNewFile();
    }
    StreamResult result = new StreamResult();
    result.setSystemId(xml.getAbsolutePath());
    transformer.transform(source, result);
    if (result.getOutputStream() != null) {
      result.getOutputStream().close();
    }
    transformer = null;
  }
  
  public static void BorrarFichero(File Ffichero) {
    try {
      if (Ffichero.exists()) {
        Ffichero.delete();
        System.out.println("Fichero Borrado con Exito");
      } 
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    } 
  }
  
  public static String getStringOfFile(String ruta) throws FileNotFoundException, IOException {
    RandomAccessFile ra = new RandomAccessFile(ruta, "r");
    StringBuilder string = new StringBuilder();
    String aux = "";
    while (aux != null) {
      aux = ra.readLine();
      if (aux != null) {
        string.append(aux);
      }
    } 
    ra.close();
    return string.toString();
  }
}