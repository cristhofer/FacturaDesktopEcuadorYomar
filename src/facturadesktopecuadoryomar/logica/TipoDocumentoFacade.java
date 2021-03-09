package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.TipoDocumento;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;

public class TipoDocumentoFacade extends AbstractFacade<TipoDocumento> {
  public TipoDocumento buscarPorCodigo(String codigo) {
    Query q = getEntitiManager().createNamedQuery("TipoDocumento.findByIdentificadorTico");
    q.setParameter("identificadorTico", codigo);
    List<TipoDocumento> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public TipoDocumento buscarPorCodigoTipo(Integer codigo) {
    Query q = getEntitiManager().createNamedQuery("TipoDocumento.findByCodigoTico");
    q.setParameter("codigoTico", codigo);
    List<TipoDocumento> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public List<String> listarTipoDoc() {
    Query q = getEntitiManager().createNamedQuery("TipoDocumento.findByEstadoTico");
    q.setParameter("estadoTico", "ACTIVO");
    List<TipoDocumento> l = q.getResultList();
    List<String> lista = new LinkedList<>();
    for (TipoDocumento x : l) {
      lista.add(x.getIdentificadorTico());
    }
    return lista;
  }
}