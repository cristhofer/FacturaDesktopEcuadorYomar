package facturadesktopecuadoryomar.hilos;

import com.ecuasis.coresri.firma.XAdESBESSignature;
import com.ecuasis.coresri.util.UtilSRI;
import facturadesktopecuadoryomar.logica.ConfiguracionFacade;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.logica.EmpresaFacade;
import facturadesktopecuadoryomar.modelo.servidor.Configuracion;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.utilidades.UtilFiles;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class FirmarDocumento extends Thread {
  private DocumentoFacade documentoLogica = new DocumentoFacade();
  private List<Documento> listaDocumentos;
  private ConfiguracionFacade configuracionFacade = new ConfiguracionFacade();
  private EmpresaFacade empresaFacade = new EmpresaFacade();
  private Configuracion config = null;
  private Empresa empresa = null;
  XAdESBESSignature firmar = new XAdESBESSignature();
  
  public FirmarDocumento(List<Documento> listaDocumentos, ThreadGroup group, String name) {
    super(group, name);
    this.listaDocumentos = listaDocumentos;
  }
  
  @Override
  public void run() {
    for (Documento x : this.listaDocumentos) {
      this.empresa = x.getCodigoEmpr();
      this.config = this.empresa.getIdConfiguracion();
      System.out.println("EL DOCUMENTO A FIRMAR ES :" + x.getNumeroDocu() + x.getFirmadoDocu());
      if (x.getXmlFirmDocu() != null && !x.getFirmadoDocu().booleanValue()) {
        String tmp = this.config.getUrlTemporalConf();
        File archivoXML = new File(tmp + File.separator + "sistema" + x.getNumeroDocu().replace("-", "") + ".xml");
        if (!archivoXML.exists()) {
          try {
            archivoXML.createNewFile();
          } catch (IOException ex) {
            Logger.getLogger(FirmarDocumento.class.getName()).log(Level.SEVERE, null, ex);
          } 
        }
        Document xmlFirmado = null;
        try {
          UtilFiles.EcribirFichero(archivoXML, x.getXmlFirmDocu());
          Thread.sleep(1000l);
          xmlFirmado = this.firmar.firmar(archivoXML.getAbsolutePath(), this.config.getUrlFirmasConf() + File.separator + this.empresa.getFirmaEmpr(), this.empresa
              .getClaveFirmaEmpr(), tmp, x.getNumeroDocu().replace("-", "") + "firmado.XML");
          Thread.sleep(1000l);
        } catch (IOException ex) {
          JOptionPane.showMessageDialog(null, "No se ha encontrado la FIRMA: " + ex.getMessage());
        } catch (TransformerException ex) {
          JOptionPane.showMessageDialog(null, "No se ha podido embeber la frma en el documento la FIRMA: " + ex.getMessage());
        } catch (InterruptedException ex) {
          Logger.getLogger(FirmarDocumento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
          Logger.getLogger(FirmarDocumento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
          Logger.getLogger(FirmarDocumento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
          Logger.getLogger(FirmarDocumento.class.getName()).log(Level.SEVERE, null, ex);
        } 
        try {
          x.setXmlFirmDocu(UtilSRI.convertDocumentToString(xmlFirmado));
        } catch (TransformerException ex) {
          Logger.getLogger(FirmarDocumento.class.getName()).log(Level.SEVERE, null, ex);
        } 
        try {
          x.setFirmadoDocu(Boolean.valueOf(true));
          this.documentoLogica.modificar(x);
          System.out.println("Se ha modificado la firma en el documento");
        } catch (SQLException ex) {
          Logger.getLogger(FirmarDocumento.class.getName()).log(Level.SEVERE, null, ex);
        } 
        archivoXML.delete();
      } 
    } 
    this.listaDocumentos = new LinkedList<>();
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.config = null;
    this.configuracionFacade = null;
    this.documentoLogica = null;
    this.empresa = null;
    this.empresaFacade = null;
    this.listaDocumentos = null;
    this.firmar = null;
  }
}