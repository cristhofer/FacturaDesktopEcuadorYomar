package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.TipoAmbiente;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;

public class TipoAmbienteFacade extends AbstractFacade<TipoAmbiente> {
  public List<String> listarTipoDoc() {
    Query q = getEntitiManager().createNamedQuery("TipoAmbiente.findByEstadoTiam");
    q.setParameter("estadoTiam", "ACTIVO");
    List<TipoAmbiente> l = q.getResultList();
    List<String> lista = new LinkedList<>();
    for (TipoAmbiente x : l) {
      lista.add(x.getIdentificadorTiam());
    }
    return lista;
  }
}