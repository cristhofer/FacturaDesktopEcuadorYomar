package facturadesktopecuadoryomar.hilos;

import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.modelo.servidor.ViewDocumento;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvioCorreoDocumentos extends Thread {
  private DocumentoFacade documentoLogica = new DocumentoFacade();
  private boolean exit = false;

  public EnvioCorreoDocumentos() {
  }
  

  @Override
  public void run() {
    ThreadGroup tg = new ThreadGroup("sendmaildocuments");
    while (!this.exit) {
      List<ViewDocumento> listaDocumentos = new LinkedList<>();
      
      if (tg.activeCount() == 0) {
        int ended = 0;
        int numeroThread = 1;
        listaDocumentos = this.documentoLogica.listaDocumentosEnvioCorreo(50);
        if (listaDocumentos.size() > 0) {
          for (int i = 0; i < listaDocumentos.size(); ) {
            ended = ended + 50;
            if (ended > listaDocumentos.size()) {
              ended = listaDocumentos.size();
            }
            EnviarCorreo nd = new EnviarCorreo(listaDocumentos.subList(i, ended), tg, "Hilo Envio de Correos al Cliente: " + numeroThread);
            nd.start();
            numeroThread++;
            i = ended;
          } 
          listaDocumentos = new LinkedList<>();
        } 
      } 
      try {
        Thread.sleep(120000L);
      } catch (InterruptedException ex) {
        Logger.getLogger(EnvioCorreoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
      } 
    } 
    if (this.exit == false) {
      tg.destroy();
      System.out.println("SE HA DETENIDO EL PROCESO DE ENVIO DE DOCUMENTOS AL SRI");
    } 
  }
  
  public void detenerProceso() {
    this.exit = true;
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.documentoLogica = null;
  }
}