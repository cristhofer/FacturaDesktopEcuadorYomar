package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.Configuracion;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.Usuario;
import facturadesktopecuadoryomar.modelo.servidor.ViewDocumento;
import facturadesktopecuadoryomar.utilidades.UtilFiles;
import mail.util.JasperReportUtil;
import mail.util.UtilMail;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class EnvioCorreoFacade
{
  private Configuracion config;
  private Empresa empr;
  private UsuarioFacade usuarioFacade;
  UtilMail email;
  private ConfiguracionFacade configFacade = new ConfiguracionFacade();
  private EmpresaFacade empresaFacade = new EmpresaFacade();
  private DocumentoFacade documentoFacade = new DocumentoFacade();
  
  public EnvioCorreoFacade(Configuracion config, Empresa empr) {
    this.config = config;
    this.empr = empr;
    this.usuarioFacade = new UsuarioFacade();
    System.out.println("DATOS: " + this.config.getCorreoHostConf().trim());
    System.out.println("DATOS: " + this.config.getCorreoPortConf().trim());
    System.out.println("DATOS: " + this.config.getCorreoEnvioConf().trim());
    System.out.println("DATOS: " + this.config.getCorreoPassConf().trim());
    System.out.println("DATOS: " + this.config.getCorreoEnvioConf().trim());
    System.out.println("DATOS: true");
    this.email = new UtilMail(this.config.getCorreoHostConf().trim(), this.config.getCorreoPortConf().trim(), this.config.getCorreoEnvioConf().trim(), this.config.getCorreoPassConf().trim(), this.config.getCorreoEnvioConf().trim(), true, false, "");
  }
  
  public EnvioCorreoFacade() {
    this.email = new UtilMail(
      "smtp.office365.com", 
      "587", "comersur.facturacion@palmar.com.ec", "Exp0rtad0r123!", 
      "comersur.facturacion@palmar.com.ec", true, false, "");
  }
  
  public void enviarCorreo(ViewDocumento documento) throws IOException, MessagingException, Exception {
    String contenido = importarInfoPlantilla(documento);
    if (documento.getCorreoDocu() != null) {
      System.out.println("CORREO A ENVIAR: " + documento.getCorreoDocu());
      String docscorreo = "";
      String subject = "";
      int errorSeparador = 0;
      if (documento.getCorreoDocu().contains(",")) {
        errorSeparador++;
      }
      if (documento.getCorreoDocu().contains(";")) {
        errorSeparador++;
      }
      System.out.println("imprimir separador: " + errorSeparador);
      if (errorSeparador > 1) {
        documento.setEstadoCorreoDocu("NO ENVIADO");
        docscorreo = "perchoburgos@gmail.com";
        subject = "ERROR EN ENVIO DE CORREO DEL DOCUMENTO " + documento.getNumeroDocu() + " " + documento.getNombreTico();
      } else {
        docscorreo = documento.getCorreoDocu();
        subject = "HA RECIBIDO UN NUEVO DOCUMENTO " + documento.getNumeroDocu() + " " + documento.getNombreTico();
      } 
      System.out.println("imprimir CORREO FINAL : " + docscorreo);
      this.email.setTo(docscorreo.trim());
      this.email.setSubject(subject);
      
      this.email.addContent(contenido);
      String rutaPDF = "", rutaXML = "";
      try {
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("P_PLogo", this.empr.getLogoEmpr());
        map.put("P_TMensaje", this.config.getMensajeConf());
        map.put("P_PBanner", this.empr.getBannerEmpr());
        if (documento.getFechaAutorizacionDocu() != null) {
          map.put("P_TAutoriza", documento.getNumeroAutorizacionDocu());
          map.put("P_FAutoriza", df2.format(documento.getFechaAutorizacionDocu()));
        } else {
          
          map.put("P_TAutoriza", "EN ESPERA DE AUTORIZACION");
          map.put("P_FAutoriza", "");
        } 
        map.put("P_DCliente", "");
        map.put("P_CCliente", "");
        map.put("P_Correo", documento.getCorreoDocu());
        map.put("P_exportador", this.empr.getExportadora());
        rutaPDF = JasperReportUtil.generarPDF(documento, map, this.config);
        rutaXML = JasperReportUtil.generarXML(documento, map, this.config);
      } catch (Exception ex) {
        Logger.getLogger(EnvioCorreoFacade.class.getName()).log(Level.SEVERE, null, ex);
      } 
      File pdf = new File(rutaPDF);
      File xml = new File(rutaXML);
      try {
        this.email.addAttach(pdf);
      } catch (Exception ex) {
        Logger.getLogger(EnvioCorreoFacade.class.getName()).log(Level.SEVERE, null, ex);
      } 
      this.email.addAttach(xml);
      System.out.println("MULTIPART XD XD::" + this.email.getTo());
      this.email.sendMultipart();
    } else {
      
      System.out.println("NO CUENTA CON CORREO EL DOCUMENTO " + documento.getNumeroDocu());
    } 
  }
  
  public void enviarCorreoPrueba() throws Exception {
    this.email.setTo("PERCHOBURGOS@GMAIL.COM");
    this.email.setSubject("prueba de correo");
    this.email.addContent("<h1>HOLA MUNDO</h1>");
    this.email.sendMultipart();
  }
  
  public void enviarCorreo(long codigoDoc, String correo) throws IOException, MessagingException, Exception {
    ViewDocumento documento = this.documentoFacade.buscarViewDocPorCodigo(codigoDoc);
    String contenido = importarInfoPlantilla(documento);
    if (documento.getCorreoUsua() != null) {
      this.email.setTo(correo);
      this.email.setSubject("HA RECIBIDO UN NUEVO DOCUMENTO " + documento.getNumeroDocu() + " " + documento.getNombreTico());
      
      this.email.addContent(contenido);
      String rutaPDF = "", rutaXML = "";
      try {
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("P_PLogo", this.empr.getLogoEmpr());
        map.put("P_TMensaje", this.config.getMensajeConf());
        map.put("P_PBanner", this.empr.getLogoEmpr());
        if (documento.getFechaAutorizacionDocu() != null) {
          map.put("P_TAutoriza", documento.getNumeroAutorizacionDocu());
          map.put("P_FAutoriza", df2.format(documento.getFechaAutorizacionDocu()));
        } else {
          
          map.put("P_TAutoriza", "EN ESPERA DE AUTORIZACION");
          map.put("P_FAutoriza", "");
        } 
        map.put("P_DCliente", "");
        map.put("P_CCliente", "");
        rutaPDF = JasperReportUtil.generarDescargaPDF(documento, map, this.config);
        rutaXML = JasperReportUtil.generarXML(documento, map, this.config);
      } catch (Exception ex) {
        Logger.getLogger(EnvioCorreoFacade.class.getName()).log(Level.SEVERE, null, ex);
      } 
      File pdf = new File(rutaPDF);
      File xml = new File(rutaXML);
      try {
        this.email.addAttach(pdf);
      } catch (Exception ex) {
        Logger.getLogger(EnvioCorreoFacade.class.getName()).log(Level.SEVERE, null, ex);
      } 
      this.email.addAttach(xml);
      
      this.email.sendMultipart();
    } else {
      System.out.println("NO CUENTA CON CORREO EL DOCUMENTO " + documento.getNumeroDocu());
    } 
  }
  
  private String importarInfoPlantilla(ViewDocumento doc) throws IOException {
    String texto = getTextoPlantilla(doc.getIdentificadorTico());
    Usuario usuario = this.usuarioFacade.buscarPorRuc(doc.getIdentificacionUsua());
    texto = texto.replace("#{cliente}", usuario.getNombreCompletoUsua());
    texto = texto.replace("#{usuario}", usuario.getNombreUsua());
    texto = texto.replace("#{clave}", usuario.getPasswordUsua());
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
    texto = texto.replace("#{fechaEmision}", sdf.format(doc.getFechaEmisionDocu()));
    texto = texto.replace("#{mesEmision}", mes(sdf.format(doc.getFechaEmisionDocu())));
    texto = texto.replace("#{ruc}", doc.getIdentificacionUsua());
    texto = texto.replace("#{correos}", doc.getCorreoUsua());
    texto = texto.replace("#{tipoDocumento}", doc.getNombreTico());
    texto = texto.replace("#{numeroDocumento}", doc.getNumeroDocu());
    texto = texto.replace("#{numeroAutorizacion}", doc.getClaveAccesoDocu());
    texto = texto.replace("#{claveAcceso}", doc.getClaveAccesoDocu());
    
    return texto;
  }
  
  private String getTextoPlantilla(String tipoDoc) throws IOException {
    StringBuilder rutaPlantilla = new StringBuilder();
    rutaPlantilla.append(this.config.getRutaPlantillaConf()).append(File.separator).append(tipoDoc).append(".html");
    return UtilFiles.getStringOfFile(rutaPlantilla.toString());
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