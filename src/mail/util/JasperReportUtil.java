package mail.util;

import com.ecuasis.coresri.util.UtilSRI;
import facturadesktopecuadoryomar.modelo.servidor.Configuracion;
import facturadesktopecuadoryomar.modelo.servidor.ViewDocumento;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.w3c.dom.Document;

public class JasperReportUtil
{
  public static ByteArrayOutputStream getOutputStreamFromReport(List list, Map map, String pathJasper) throws Exception {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
    JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, (JRDataSource)dataSource);
    JasperExportManager.exportReportToPdfStream(jp, os);
    os.flush();
    os.close();
    return os;
  }
  
  public static ByteArrayOutputStream getOutputStreamFromReportXML(Document docXml, String xPath, Map map, String pathJasper) throws Exception {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    JRXmlDataSource dataSource = new JRXmlDataSource(docXml, xPath);
    JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, (JRDataSource)dataSource);
    JasperExportManager.exportReportToPdfStream(jp, os);
    os.flush();
    os.close();
    return os;
  }
  
  public static String generarPDF(ViewDocumento vd, Map map, Configuracion conf) throws Exception {
    String urlRide = "", subreport = "";
    if (vd.getDescripcionDoc() != null) {
      switch (vd.getDescripcionDoc().trim()) {
        case "NO":
          urlRide = vd.getUrlRideDocu();
          System.out.println("ENTRO POR EL NORMAL");
          break;
        case "EX":
          urlRide = vd.getUrlRideDocu().replace(".jasper", "_ex.jasper");
          System.out.println("ENTRO POR EL EXPORTACION");
          break;
        case "RE":
          urlRide = vd.getUrlRideDocu().replace(".jasper", "_re.jasper");
          subreport = vd.getUrlRideDocu().replace(".jasper", "_re_reembolso.jasper");
          System.out.println("ENTRO POR EL REEMBOLSO");
          break;
        default:
          urlRide = vd.getUrlRideDocu();
          System.out.println("ENTRO POR EL DEFAULT");
          break;
      } 
    } else {
      urlRide = vd.getUrlRideDocu();
      System.out.println("ENTRO POR EL ELSE");
    } 
    String reportPath = conf.getRutaReporteConf() + File.separator + urlRide;
    JRXmlDataSource dataSource = new JRXmlDataSource(UtilSRI.convertStringToDocument(vd.getXmlFirmDocu()), vd.getPathRideDocu());
    if (subreport.trim().length() > 1) {
      map.put("SUBREPORT_DIR", conf.getRutaReporteConf() + File.separator);
      System.out.println("ENTRO POR EL SUBRPORTE");
    } 
    JasperPrint jp = JasperFillManager.fillReport(reportPath, map, dataSource);
    String nombreArchivo = vd.getClaveAccesoDocu() + ".pdf";
    String pathPDF = conf.getRutaDocumentos() + File.separator + "pdf" + File.separator + nombreArchivo;
    JasperExportManager.exportReportToPdfFile(jp, pathPDF);
    return pathPDF;
  }
  
  public static String generarDescargaPDF(ViewDocumento vd, Map map, Configuracion conf) throws Exception {
    String reportPath = conf.getRutaReporteConf() + File.separator + vd.getUrlRideDocu();
    String xpath = vd.getPathRideDocu();
    JRXmlDataSource dataSource = new JRXmlDataSource(UtilSRI.convertStringToDocument(vd.getXmlFirmDocu()), xpath);
    JasperPrint jp = JasperFillManager.fillReport(reportPath, map, dataSource);
    String pathTemporal = conf.getUrlTemporalConf() + File.separator + "c_" + vd.getNombreTico() + vd.getNumeroDocu() + ".pdf";
    String pathTemporal2 = "C:\\WindocE\\WindocE_Incarpalm\\DOCWF\\RIDE" + File.separator + "RIDE_" + vd.getClaveAccesoDocu() + ".pdf";
    File xd = new File(pathTemporal2);
    JasperExportManager.exportReportToPdfFile(jp, pathTemporal);
    if (!xd.exists()) {
      JasperExportManager.exportReportToPdfFile(jp, pathTemporal2);
    }
    return pathTemporal;
  }
  
  public static String generarXML(ViewDocumento vd, Map map, Configuracion conf) throws Exception {
    String serverPath = conf.getRutaDocumentos() + File.separator + "xml" + File.separator + vd.getClaveAccesoDocu() + ".xml";
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(UtilSRI.convertStringToDocument(vd.getXmlFirmDocu()));
    File xml = new File(serverPath);
    if (!xml.exists()) {
      xml.createNewFile();
    }
    StreamResult result = new StreamResult();
    result.setSystemId(serverPath);
    transformer.transform(source, result);
    return serverPath;
  }
}