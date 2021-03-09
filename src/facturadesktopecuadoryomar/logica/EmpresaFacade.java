package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.Establecimiento;
import java.util.List;
import javax.persistence.Query;

public class EmpresaFacade extends AbstractFacade<Empresa> {
  public List<Empresa> listaEmpresas() {
    Query q = getEntitiManager().createNamedQuery("Empresa.findByEstadoEmpr");
    q.setParameter("estadoEmpr", "ACTIVO");
    return q.getResultList();
  }
  
  public Empresa buscarEmpresaPorCodigo(Integer codigo) {
    Query q = getEntitiManager().createNamedQuery("Empresa.findByCodigoEmpr");
    q.setParameter("codigoEmpr", codigo);
    List<Empresa> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public Empresa buscarEmpresaPorReferencia(String codigo) {
    Query q = getEntitiManager().createNamedQuery("Empresa.findByReferencia");
    q.setParameter("referencia", codigo);
    List<Empresa> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public Empresa buscarEmpresaPorRuc(String ruc) {
    Query q = getEntitiManager().createNamedQuery("Empresa.findByRucEmpr");
    q.setParameter("rucEmpr", ruc);
    List<Empresa> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public Establecimiento buscarEstablecimientoMatriz(Empresa emp) {
    Query q = getEntitiManager().createNamedQuery("Establecimiento.findMatriz");
    q.setParameter("emp", emp);
    List<Establecimiento> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public Establecimiento buscarEstablecimiento(String codigo) {
    Query q = getEntitiManager().createNamedQuery("Establecimiento.findByIdentificadorEstab");
    q.setParameter("identificadorEstab", codigo);
    List<Establecimiento> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }

  /*
    * Agregado por CJTM
    */
    public Establecimiento buscarEstablecimientoDireccion(Empresa empresa, Integer codigoEstab) {
      Query q = getEntitiManager().createNamedQuery("Establecimiento.findByDireccionEstab");
      q.setParameter("emp", empresa);
      q.setParameter("codEstab", codigoEstab);
      List<Establecimiento> l = q.getResultList();
      if (l.size() > 0) {
          return l.get(0);
      } else {
          return null;
      }
  }
}