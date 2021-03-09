package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.Notificacion;
import facturadesktopecuadoryomar.modelo.servidor.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificacionFacade extends AbstractFacade<Notificacion>
{
  public void guardarNotificacion(Notificacion entidad, List<Usuario> listaUsuario) {
    StringBuilder usuario = new StringBuilder();
    listaUsuario.forEach(u -> usuario.append(u.getNombreUsua()).append(":X:").append("false").append(";X;"));

    usuario.delete(usuario.length() - 3, usuario.length());
    entidad.setUsuarios(usuario.toString());
    try {
      //guardar((T)entidad);
      guardar(entidad);
    } catch (SQLException ex) {
      Logger.getLogger(NotificacionFacade.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
  }
}