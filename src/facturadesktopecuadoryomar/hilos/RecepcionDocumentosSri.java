package facturadesktopecuadoryomar.hilos;

import com.ecuasis.coresri.servicios.ServiciosSRI;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecepcionDocumentosSri extends Thread {
  private DocumentoFacade documentoLogica = new DocumentoFacade();
  private boolean exit = false;
  private ServiciosSRI sri;
  
  public RecepcionDocumentosSri(ServiciosSRI sri) {
    this.sri = sri;
  }
  
  @Override
  public void run() {
    ThreadGroup tg = new ThreadGroup("receptiondocuments");
    while (!this.exit) {
      List<Documento> listaDocumentos = new LinkedList<>();
      
      if (tg.activeCount() == 0) {
        int ended = 0;
        int numeroThread = 1;
        listaDocumentos = this.documentoLogica.listaDocumentosEnviados(300);
        if (listaDocumentos.size() > 0) {
          for (int i = 0; i < listaDocumentos.size(); ) {
            ended = ended + 50;
            if (ended > listaDocumentos.size()) {
              ended = listaDocumentos.size();
            }
            RecepcionDocumento nd = new RecepcionDocumento(listaDocumentos.subList(i, ended), tg, "Hilo Recepcion SRI: " + numeroThread, this.sri);
            nd.start();
            numeroThread++;
            i = ended;
          } 
          listaDocumentos = new LinkedList<>();
        } 
      } 
      try {
        Thread.sleep(60000l);
      } catch (InterruptedException ex) {
        Logger.getLogger(RecepcionDocumentosSri.class.getName()).log(Level.SEVERE, null, ex);
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
}