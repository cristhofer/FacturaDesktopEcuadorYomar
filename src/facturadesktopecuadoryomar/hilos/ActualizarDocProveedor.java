package facturadesktopecuadoryomar.hilos;

import facturadesktopecuadoryomar.logica.DocumentoProveedorFacade;
import facturadesktopecuadoryomar.modelo.servidor.DocumentosRecibidos;
import java.util.LinkedList;
import java.util.List;

public class ActualizarDocProveedor extends Thread
{
  private DocumentoProveedorFacade datos = new DocumentoProveedorFacade();
  private List<DocumentosRecibidos> listaDocumentos;
  
  public ActualizarDocProveedor(List<DocumentosRecibidos> listaDocumentos, ThreadGroup group, String name) {
    super(group, name);
    this.listaDocumentos = listaDocumentos;
  }
  
  public void run() {
    if (this.listaDocumentos.size() > 0) {
      for (DocumentosRecibidos x : this.listaDocumentos) {
        System.out.println("DOCUMENTO RECIBIDO:" + x.getNumeroDocu());
        this.datos.actualizarDocumentoProveedor(x);
      } 
    }
    this.listaDocumentos = new LinkedList<>();
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.datos = null;
    this.listaDocumentos = null;
  }
}