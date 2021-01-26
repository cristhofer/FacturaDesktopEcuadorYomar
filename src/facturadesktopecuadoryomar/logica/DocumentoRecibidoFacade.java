package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.DocumentosRecibidos;
import java.util.List;
import javax.persistence.Query;

public class DocumentoRecibidoFacade extends AbstractFacade<DocumentosRecibidos> {
  public List<DocumentosRecibidos> getDocumentosPendientes(int limite) {
    Query q = getEntitiManager().createNamedQuery("DocumentosRecibidos.findByEstadoClienteDocu");
    q.setParameter("estadoClienteDocu", "P");
    q.setMaxResults(limite);
    return q.getResultList();
  }
}