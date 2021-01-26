package facturadesktopecuadoryomar.hilos;

import facturadesktopecuadoryomar.logica.ConfiguracionFacade;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.logica.EmpresaFacade;
import facturadesktopecuadoryomar.logica.EnvioCorreoFacade;
import facturadesktopecuadoryomar.modelo.servidor.Configuracion;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.ViewDocumento;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class EnviarCorreo extends Thread {
  private List<ViewDocumento> listaDocumentos;
  DocumentoFacade documentoFacade = new DocumentoFacade();
  ConfiguracionFacade configuracionFacade = new ConfiguracionFacade();
  EmpresaFacade empresaFacade = new EmpresaFacade();
  private Configuracion config = null;
  private Empresa empresa = null;
  
  public EnviarCorreo(List<ViewDocumento> listaDocumentos, ThreadGroup group, String name) {
    super(group, name);
    this.listaDocumentos = listaDocumentos;
  }

  @Override
  public void run() {
    for (ViewDocumento x : this.listaDocumentos) {
      this.empresa = this.empresaFacade.buscarEmpresaPorCodigo(x.getCodigoEmpr());
      this.config = this.empresa.getIdConfiguracion();
      System.out.println("*********************INICIO DE ENVIO DE CORREOS A:" + x.getCorreoDocu() + " ************************");
      if (x.getCorreoDocu() != null) {
        if (x.getCorreoDocu().trim().length() > 5) {
          try {
            Documento doc = this.documentoFacade.buscarPorCodigo(x.getCodigoDocu());
            if (doc.getFirmadoDocu().booleanValue() == true) {
              EnvioCorreoFacade envioFacade = new EnvioCorreoFacade(this.config, this.empresa);
              envioFacade.enviarCorreo(x);
              
              if (x.getCorreoDocu() != null) {
                doc.setEstadoCorreoDocu("ENVIADO");
                doc.setFechaEnvioCorreoDocu(new Date());
              } else {
                doc.setEstadoCorreoDocu("NO_ENVIADO");
              } 
              this.documentoFacade.modificar(doc);
            } 
          } catch (MessagingException ex) {
            Logger.getLogger(EnviarCorreo.class.getName()).log(Level.SEVERE, null, ex);
          } catch (Exception ex) {
            Logger.getLogger(EnviarCorreo.class.getName()).log(Level.SEVERE, null, ex);
          } 
          try {
            Thread.sleep(5000L);
          } catch (InterruptedException ex) {
            Logger.getLogger(EnvioCorreoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
          } 
        } else {
          System.err.println("NO HAY CORREO: " + x.getCorreoUsua());
        } 
      }
      
      System.out.println("*********************************************");
    } 
    this.listaDocumentos = new LinkedList<>();
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.listaDocumentos = null;
    this.config = null;
    this.configuracionFacade = null;
    this.empresa = null;
    this.empresaFacade = null;
  }
}