package oracle.fe.logica;

import oracle.fe.modelo.FacturacionIntermedia;
import java.util.List;
import javax.persistence.Query;

public class FacturacionIntermediaFacade extends AbstractFacade<FacturacionIntermedia>
{
  public long getId() {
    Query q = getEntitiManager().createQuery("SELECT f FROM FacturacionIntermedia f ORDER BY f.id DESC");
    q.setMaxResults(1);
    List<FacturacionIntermedia> lista = q.getResultList();
    if (lista.size() > 0) {
      return lista.get(0).getId() + 1;
    }
    return 1L;
  }
  
  public List<FacturacionIntermedia> getPendientes(int limite) {
    Query q = getEntitiManager().createNamedQuery("FacturacionIntermedia.findByEstado");
    q.setParameter("estado", "P");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<FacturacionIntermedia> getPendientesByEmpresa(int limite, String codigoInternoCompania) {
    Query q = getEntitiManager().createNamedQuery("FacturacionIntermedia.findByEmpresa");
    q.setParameter("estado", "P");
    q.setParameter("compania", codigoInternoCompania);
    q.setMaxResults(limite);
    return q.getResultList();
  }
}