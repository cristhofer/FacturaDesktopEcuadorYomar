package mail.util;

import com.ecuasis.coresri.util.UtilSRI;
import facturadesktopecuadoryomar.modelo.servidor.Configuracion;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.ViewDocumento;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.xml.sax.SAXException;

public class UtilIreport
{
  private static String documentoAutorizado(ViewDocumento d, Configuracion conf) {
    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sb.append("<autorizacion>");
    sb.append("<ambiente>").append(conf.getAmbienteConf().equals("2") ? "PRODUCCION" : "PRUEBA").append("</ambiente>");
    sb.append("<estado>AUTORIZADO</estado>");
    sb.append("<numeroAutorizacion>").append(d.getNumeroAutorizacionDocu()).append("</numeroAutorizacion>");
    sb.append("<claveAcceso>").append(d.getClaveAccesoDocu()).append("</claveAcceso>");
    sb.append("<fechaAutorizacion>").append(d.getFechaAutorizacionDocu()).append("</fechaAutorizacion>");
    sb.append("<comprobante>");
    sb.append("<![CDATA[");
    sb.append(d.getXmlFirmDocu());
    sb.append("]]>");
    sb.append("</comprobante>");
    sb.append("</autorizacion>");
    return sb.toString();
  }
  
  public static File temporalXML(ViewDocumento d, Configuracion conf, String nombreArchivo, String rutaTmp) throws IOException {
    File f = new File(rutaTmp + File.separatorChar + nombreArchivo);
    if (!f.exists()) {
      f.createNewFile();
    }
    FileWriter fichero = new FileWriter(f);
    PrintWriter wr = new PrintWriter(fichero);
    wr.write(documentoAutorizado(d, conf));
    wr.close();
    fichero.close();
    return new File(rutaTmp + File.separatorChar + nombreArchivo);
  }
  
  public static File temporalPDF(Empresa empresa, ViewDocumento d, Configuracion conf, String nombreArchivo, String reporte, String paht, String rutaTmp, String logo, String mensaje) throws JRException, FileNotFoundException, IOException, SAXException, ParserConfigurationException {
    DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("P_PLogo", empresa.getLogoEmpr());
    map.put("P_TMensaje", conf.getMensajeConf());
    map.put("P_Banner", empresa.getLogoEmpr());
    if (d.getFechaAutorizacionDocu() != null) {
      map.put("P_TAutoriza", d.getNumeroAutorizacionDocu());
      map.put("P_FAutoriza", df2.format(d.getFechaAutorizacionDocu()));
    } else {
      map.put("P_TAutoriza", "EN ESPERA DE AUTORIZACION");
      map.put("P_FAutoriza", "");
    } 
    map.put("P_DCliente", "");
    map.put("P_CCliente", "");
    JRXmlDataSource ds = new JRXmlDataSource(UtilSRI.convertStringToDocument(d.getXmlFirmDocu()), paht);
    InputStream is = new FileInputStream(reporte);
    JasperPrint print = JasperFillManager.fillReport(is, map, ds);
    /* ANTES
    JRPdfExporter jRPdfExporter = new JRPdfExporter();
    jRPdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, rutaTmp + File.separatorChar + nombreArchivo);
    jRPdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
    jRPdfExporter.exportReport(); */
    JRExporter jRPdfExporter = new JRPdfExporter();
    jRPdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, rutaTmp + File.separatorChar + nombreArchivo);
    jRPdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
    jRPdfExporter.exportReport();
    return new File(rutaTmp + File.separatorChar + nombreArchivo);
  }
  
  public static String getRandomFileName() {
    int i = (int)(Math.random() * 1.0E7D);
    return String.valueOf(i);
  }
  
  public static void eliminarArchivosTemporales(File f) {
    f.delete();
  }
  
  public static void eliminarArchivosTemporales(File[] f) {
    for (File c : f) {
      if (c.exists())
        c.delete(); 
    } 
  }
}