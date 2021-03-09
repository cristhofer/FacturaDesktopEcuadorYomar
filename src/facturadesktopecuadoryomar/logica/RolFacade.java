package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.Rol;
import java.util.List;
import javax.persistence.Query;

public class RolFacade extends AbstractFacade<Rol> {
  public Rol buscarPorNombreRol(String ruc) {
    Query q = getEntitiManager().createNamedQuery("Rol.findByNombreRole");
    q.setParameter("nombreRole", ruc);
    List<Rol> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
}