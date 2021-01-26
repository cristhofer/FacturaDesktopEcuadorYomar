package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.Usuario;
import java.util.List;
import javax.persistence.Query;

public class UsuarioFacade extends AbstractFacade<Usuario> {
  public Usuario buscarPorRuc(String ruc) {
    Query q = getEntitiManager().createNamedQuery("Usuario.findByIdentificacionUsua");
    q.setParameter("identificacionUsua", ruc);
    List<Usuario> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public Usuario login(String usua, String password) {
    Query q = getEntitiManager().createNamedQuery("Usuario.login");
    q.setParameter("usua", usua);
    q.setParameter("pass", password);
    List<Usuario> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public List<Usuario> getUsuariosNotificacion() {
    Query q = getEntitiManager().createQuery("SELECT u FROM Usuario u WHERE u.recibeNotificacion =:recibe");
    q.setParameter("recibe", Boolean.valueOf(true));
    return q.getResultList();
  }
}