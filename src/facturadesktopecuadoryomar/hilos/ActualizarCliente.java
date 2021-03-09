package facturadesktopecuadoryomar.hilos;

import facturadesktopecuadoryomar.logica.DocumentoClienteFacade;
import facturadesktopecuadoryomar.logica.DocumentoFacade;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActualizarCliente extends Thread {
  private DocumentoFacade documentoLogica = new DocumentoFacade();
  private DocumentoClienteFacade datos = new DocumentoClienteFacade();
  private List<Documento> listaDocumentos;
  
  public ActualizarCliente(List<Documento> listaDocumentos, ThreadGroup group, String name) {
    super(group, name);
    this.listaDocumentos = listaDocumentos;
  }
  
  public void run() {
    if (this.listaDocumentos.size() > 0) {
      for (Documento x : this.listaDocumentos) {
        System.err.println("DOCUMENTO A PROCESAR--: " + x.getCodigoDocu());
        try {
          int error = this.datos.actualizarCliente(x, Integer.valueOf(1));
          if (error != -1) {
            x.setEstadoClienteDocu("E");
          } else {
            x.setEstadoClienteDocu("X");
          } 
          this.documentoLogica.modificar(x);
        } catch (SQLException ex) {
          Logger.getLogger(ActualizarCliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
      } 
    }
    this.listaDocumentos = new LinkedList<>();
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.datos = null;
    this.documentoLogica = null;
    this.listaDocumentos = null;
  }
}