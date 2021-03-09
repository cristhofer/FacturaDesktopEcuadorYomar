package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.TipoEmision;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;

public class TipoEmisionFacade extends AbstractFacade<TipoEmision> {
  public List<String> listarTipoDoc() {
    Query q = getEntitiManager().createNamedQuery("TipoEmision.findByEstadoTiem");
    q.setParameter("estadoTiem", "ACTIVO");
    List<TipoEmision> l = q.getResultList();
    List<String> lista = new LinkedList<>();
    for (TipoEmision x : l) {
      lista.add(x.getIdentificadorTiem());
    }
    return lista;
  }
}