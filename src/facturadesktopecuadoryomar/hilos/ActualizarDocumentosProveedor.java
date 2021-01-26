package facturadesktopecuadoryomar.hilos;

import facturadesktopecuadoryomar.logica.DocumentoRecibidoFacade;
import facturadesktopecuadoryomar.modelo.servidor.DocumentosRecibidos;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActualizarDocumentosProveedor extends Thread {
  DocumentoRecibidoFacade documentoLogica = new DocumentoRecibidoFacade(); 
  private boolean exit = false;
  
  @Override
  public void run() {
    ThreadGroup tg = new ThreadGroup("updatedocumentsprov");
    tg.setDaemon(true);
    while (!this.exit) {
      List<DocumentosRecibidos> listaDocumentos = new LinkedList<>();
      if (tg.activeCount() == 0) {
        int ended = 0;
        int numeroThread = 1;
        listaDocumentos = this.documentoLogica.getDocumentosPendientes(50);
        if (listaDocumentos.size() > 0) {
          for (int i = 0; i < listaDocumentos.size(); ) {
            ended = ended + 50;
            if (ended > listaDocumentos.size()) {
              ended = listaDocumentos.size();
            }
            ActualizarDocProveedor nd = new ActualizarDocProveedor(listaDocumentos.subList(i, ended), tg, "Hilo Act Cliente: " + numeroThread);
            nd.start();
            numeroThread++;
            i = ended;
          } 
          listaDocumentos = new LinkedList<>();
        } 
      } 
      try {
        Thread.sleep(60000L);
      } catch (InterruptedException ex) {
        Logger.getLogger(GenerarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
      } 
    } 
    if (this.exit == false) {
      tg.destroy();
      System.out.println("SE HA DETENIDO EL PROCESO DE ACTUALIZACION DE DOCUMENTOS EN EL CLIENTE");
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