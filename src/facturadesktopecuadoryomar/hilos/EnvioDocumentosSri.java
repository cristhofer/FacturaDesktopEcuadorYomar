package facturadesktopecuadoryomar.hilos;

import com.ecuasis.coresri.servicios.ServiciosSRI;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvioDocumentosSri extends Thread {
  private DocumentoFacade documentoLogica = new DocumentoFacade();
  
  private boolean exit = false;
  private ServiciosSRI sri;
  
  public EnvioDocumentosSri(ServiciosSRI sri) {
    this.sri = sri;
  }

  @Override
  public void run() {
    ThreadGroup tg = new ThreadGroup("senddocuments");
    while (!this.exit) {
      List<Documento> listaDocumentos = new LinkedList<>();
      
      if (tg.activeCount() == 0) {
        int ended = 0;
        int numeroThread = 1;
        listaDocumentos = this.documentoLogica.listaDocumentosPendientesEnvio(100);
        if (listaDocumentos.size() > 0) {
          for (int i = 0; i < listaDocumentos.size(); ) {
            ended = ended + 50;
            if (ended > listaDocumentos.size()) {
              ended = listaDocumentos.size();
            }
            EnvioDocumento nd = new EnvioDocumento(listaDocumentos.subList(i, ended), tg, "Hilo Envio Sri: " + numeroThread, this.sri);
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
        Logger.getLogger(EnvioDocumentosSri.class.getName()).log(Level.SEVERE, null, ex);
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

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    this.documentoLogica = null;
    this.sri = null;
  }
}