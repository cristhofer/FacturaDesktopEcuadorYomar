package mail.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class UtilMail {
  public static int SIMPLE = 0;
  public static int MULTIPART = 1;
  public static int AUTENTICACION = 0;
  public static String ERROR_01_LOADFILE = "Error al cargar el fichero";
  public static String ERROR_02_SENDMAIL = "Error al enviar el mail";
  private Properties props = new Properties();
  private String host;
  private String protocol;
  private String user;
  private String password;
  private String from;
  private String content;
  private String to;
  private String subject = "";
  MimeMultipart multipart = new MimeMultipart("related");
  
  public UtilMail(String host, String user, String password) {
    this.props = new Properties();
    this.props.setProperty("mail.transport.protocol", "smtp");
    this.props.setProperty("mail.host", host);
    this.props.setProperty("mail.user", user);
    this.props.setProperty("mail.password", password);
  }
  
  public UtilMail(ResourceBundle rs) {
    this.props = new Properties();
    this.props.setProperty("mail.transport.protocol", "smtp");
    this.props.setProperty("mail.smtp.host", rs.getString("MAIL.HOST"));
    this.props.setProperty("mail.smtp.auth", rs.getString("MAIL.AUTH"));
    this.props.setProperty("mail.smtp.port", rs.getString("MAIL.PORT"));
    this.password = rs.getString("MAIL.PASSWORD");
    this.user = rs.getString("MAIL.USER");
    if (rs.getString("MAIL.SSL").trim().toUpperCase().equals("TRUE")) {
      this.props.setProperty("mail.smtp.sasl.enable", "true");
      this.props.setProperty("mail.smtp.sasl.mechanisms", rs.getString("MAIL.SSLAUTH"));
      setFrom(rs.getString("MAIL.CORREO"));
      AUTENTICACION = 1;
      System.out.println("SI SSL " + AUTENTICACION);
    } else {
      this.props.setProperty("mail.user", rs.getString("MAIL.USER"));
      this.props.setProperty("mail.password", rs.getString("MAIL.PASSWORD"));
      setFrom(rs.getString("MAIL.USER"));
      AUTENTICACION = 0;
    } 
  }
  
  public UtilMail(String host, String port, String usuario, String password, String correo, Boolean permiteAutorizacion, Boolean permiteSSL, String mecanismoSSL) {
    this.props = new Properties();
    //tenían this. antes todos los props de aquí abajo
    props = new Properties();
    props.setProperty("mail.transport.protocol", "smtp");
    props.setProperty("mail.host", host);
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", permiteAutorizacion);
    props.setProperty("mail.smtp.auth.mechanisms", "LOGIN PLAIN");
    props.put("mail.smtp.ssl.trust", host);
    this.from = correo;
    this.user = usuario;
    this.password = password;
  }
  
  public static void trazas(String metodo, String mensaje) {
    System.out.println("[" + UtilMail.class.getName() + "][" + metodo + "]:[" + mensaje + "]");
  }
  
  public static String loadHTMLFile(String pathname) throws Exception {
    String content = "";
    File f = null;
    BufferedReader in = null;
    try {
      f = new File(pathname);
      if (f.exists()) {
        long len_bytes = f.length();
        trazas("loadHTMLFile", "pathname:" + pathname + ", len:" + len_bytes);
      } 
      in = new BufferedReader(new FileReader(f));
      
      String str;
      String d;

      while ((str = in.readLine()) != null) {
        str = str.trim();
        d = new String(str.getBytes("UTF-8"));
        content = content + d;
      } 
      in.close();
      return content;
    } catch (Exception e) {
      String MENSAJE_ERROR = ERROR_01_LOADFILE + ": ['" + pathname + "'] : " + e.toString();
      throw new Exception(MENSAJE_ERROR);
    } finally {
      if (in != null) {
        in.close();
      }
    } 
  }
  
  public void addContentToMultipart() throws Exception {
    BodyPart mimeBodyPart = new MimeBodyPart();
    String htmlText = getContent();
    mimeBodyPart.setContent(htmlText, "text/html");
    
    this.multipart.addBodyPart((BodyPart)mimeBodyPart);
  }
  
  public void addContent(String htmlText) throws Exception {
    BodyPart mimeBodyPart = new MimeBodyPart();
    mimeBodyPart.setContent(htmlText, "text/html");
    this.multipart.addBodyPart(mimeBodyPart);
  }
  
  public void addCID(String cidname, String pathname) throws Exception {
    DataSource fds = new FileDataSource(pathname);
    BodyPart mimeBodyPart = new MimeBodyPart();
    mimeBodyPart.setDataHandler(new DataHandler(fds));
    mimeBodyPart.setHeader("Content-ID", "<" + cidname + ">");
    this.multipart.addBodyPart(mimeBodyPart);
  }
  
  public void addAttach(String pathname) throws Exception {
    File file = new File(pathname);
    BodyPart mimeBodyPart = new MimeBodyPart();
    DataSource ds = new FileDataSource(file);
    mimeBodyPart.setDataHandler(new DataHandler(ds));
    mimeBodyPart.setFileName(file.getName());
    mimeBodyPart.setDisposition("attachment");
    this.multipart.addBodyPart(mimeBodyPart);
  }
  
  public void addAttach(File file) throws Exception {
    BodyPart mimeBodyPart = new MimeBodyPart();
    DataSource ds = new FileDataSource(file);
    mimeBodyPart.setDataHandler(new DataHandler(ds));
    mimeBodyPart.setFileName(file.getName());
    mimeBodyPart.setDisposition("attachment");
    this.multipart.addBodyPart(mimeBodyPart);
  }
  
  public void sendMultipart() throws NoSuchProviderException, MessagingException {
    System.out.println("INICIANDO SEND MULTIPART" + this.user + "    " + this.password);
    Session mailSession = null;
    Transport transport = null;
    mailSession = Session.getInstance(this.props, new Authenticator()
        {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from, password);
          }
        });
    
    mailSession.setDebug(true);
    transport = mailSession.getTransport();
    MimeMessage message = new MimeMessage(mailSession);
    message.setSubject(getSubject());
    message.setFrom(new InternetAddress(getFrom()));
    if (getTo() != null) {
      message.addRecipients(Message.RecipientType.TO, listaAddresses(getTo()));
    }
    message.addRecipient(Message.RecipientType.BCC, new InternetAddress("perchoburgos@gmail.com"));
    message.setContent(this.multipart);
    transport.connect();
    transport.sendMessage(message, message
        .getRecipients(Message.RecipientType.TO));
    transport.close();
    this.multipart = new MimeMultipart("related");
  }
  
  public Address[] listaAddresses(String correos) throws AddressException {
    System.out.println("INICIANDO LISTA ADDRES: " + correos);
    String[] listaCorreosTo = null;
    if (correos.contains(";")) {
      listaCorreosTo = correos.split(";");
    }
    else {
      if (correos.contains(",")) {
        listaCorreosTo = correos.split(",");
      } else {
        listaCorreosTo = correos.split(",");
      }
    }
    
    List<String> listaSaneada = new LinkedList<>();
    for (String listaCorreo : listaCorreosTo) {
      if (listaCorreo.trim().length() > 5) {
        listaSaneada.add(listaCorreo.trim());
      }
      System.out.println("FOR LISTA SANEADA: " + listaCorreo);
    } 
    Address[] lista = new Address[listaSaneada.size()];
    for (int i = 0; i < listaSaneada.size(); i++) {
      lista[i] = new InternetAddress(listaSaneada.get(i));
      System.out.println("CORREO A ENVIAR MULTIPART: " + lista[i].toString());
    } 
    return lista;
  }
  
  public void send() throws NoSuchProviderException, MessagingException {
    Session mailSession = null;
    
    if (AUTENTICACION == 1) {
      mailSession = Session.getDefaultInstance(this.props, null);
    } else {
      mailSession = Session.getInstance(this.props, new AutenticatorProvider(this.props.getProperty("mail.user"), this.props.getProperty("mail.password")));
    } 
    mailSession.setDebug(true);
    Transport transport = mailSession.getTransport();
    MimeMessage message = new MimeMessage(mailSession);
    message.setSubject(getSubject());
    message.setFrom(new InternetAddress(getFrom()));
    message.setContent(getContent(), "text/html");
    
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(getTo()));
    
    transport.connect();
    transport.sendMessage((Message)message, message.getRecipients(Message.RecipientType.TO));
    
    transport.close();
    this.multipart = new MimeMultipart("related");
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getFrom() {
    return this.from;
  }
  
  public void setFrom(String from) {
    this.from = from;
  }
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public String getTo() {
    return this.to;
  }
  
  public void setTo(String to) {
    this.to = to;
  }
}