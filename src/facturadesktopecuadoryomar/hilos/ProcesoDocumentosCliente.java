package facturadesktopecuadoryomar.hilos;

import facturadesktopecuadoryomar.logica.DocumentoProveedorFacade;
import facturadesktopecuadoryomar.logica.DocumentoRecibidoFacade;
import facturadesktopecuadoryomar.modelo.servidor.DocumentosRecibidos;
import java.util.LinkedList;
import java.util.List;

public class ProcesoDocumentosCliente {
  DocumentoRecibidoFacade documentoLogica = new DocumentoRecibidoFacade();
  private DocumentoProveedorFacade datos = new DocumentoProveedorFacade(); 
  private List<DocumentosRecibidos> listaDocumentos;
    
  public void actualizarDocumentosProveedor() {
    this.listaDocumentos = this.documentoLogica.getDocumentosPendientes(50);
    if (this.listaDocumentos.size() > 0) {
      for (DocumentosRecibidos x : this.listaDocumentos) {
        System.out.println("DOCUMENTO RECIBIDO:" + x.getNumeroDocu());
        this.datos.actualizarDocumentoProveedor(x);
      } 
    }
    this.listaDocumentos = new LinkedList<>();
  }
}