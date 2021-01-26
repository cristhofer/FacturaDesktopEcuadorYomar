package facturadesktopecuadoryomar.hilos;

import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirmadoDocumentos extends Thread {
  DocumentoFacade documentoLogica = new DocumentoFacade();
  private boolean exit = false;
  
  @Override
  public void run() {
    ThreadGroup tg = new ThreadGroup("firmdocuments");
    while (!this.exit) {
      List<Documento> listaDocumentos = new LinkedList<>();
      
      if (tg.activeCount() == 0) {
        int ended = 0;
        int numeroThread = 1;
        listaDocumentos = this.documentoLogica.listaDocumentosPendientesFirma(100);
        if (listaDocumentos.size() > 0) {
          for (int i = 0; i < listaDocumentos.size(); ) {
            ended = ended + 50;
            if (ended > listaDocumentos.size()) {
              ended = listaDocumentos.size();
            }
            FirmarDocumento nd = new FirmarDocumento(listaDocumentos.subList(i, ended), tg, "Hilo Firmado: " + numeroThread);
            nd.start();
            numeroThread++;
            i = ended;
          } 
          listaDocumentos = new LinkedList<>();
        } 
      } 
      try {
        Thread.sleep(240000L);
      } catch (InterruptedException ex) {
        Logger.getLogger(GenerarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
      } 
    } 
    if (this.exit == false) {
      tg.destroy();
      System.out.println("SE HA DETENIDO EL PROCESO DE FIRMADO DE DOCUMENTOS");
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