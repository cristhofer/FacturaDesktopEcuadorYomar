package facturadesktopecuadoryomar.hilos;

import facturadesktopecuadoryomar.logica.DocumentoClienteFacade;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.modelo.cliente.Carga;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerarDocumentos extends Thread {
  DocumentoClienteFacade datos = new DocumentoClienteFacade();
  DocumentoFacade documentoLogica = new DocumentoFacade(); 
  private boolean exit = false;
  
  @Override
  public void run() {
    ThreadGroup tg = new ThreadGroup("createdocuments");
    while (!this.exit) {
      
      if (tg.activeCount() == 0) {
        int ended = 0;
        int numeroThread = 1;
        List<Carga> listaCargas = this.datos.listarCargasCliente(50);
        if (listaCargas.size() > 0) {
          for (int i = 0; i < listaCargas.size(); ) {
            ended = ended + 50;
            if (ended > listaCargas.size()) {
              ended = listaCargas.size();
            }
            NuevoDocumento nd = new NuevoDocumento(listaCargas.subList(i, ended), tg, "Hilo" + numeroThread);
            nd.start();
            numeroThread++;
            i = ended;
            System.err.println("HILO EJECUTANDOSE: #" + nd.getName());
          } 
          listaCargas = new LinkedList<>();
        } 
      } 
      try {
        Thread.sleep(90000l);
      } catch (InterruptedException ex) {
        Logger.getLogger(GenerarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
      } 
    } 
    if (this.exit == false) {
      tg.destroy();
    }
  }
  
  public void detenerProceso() {
    this.exit = true;
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.datos = null;
    this.documentoLogica = null;
  }
}