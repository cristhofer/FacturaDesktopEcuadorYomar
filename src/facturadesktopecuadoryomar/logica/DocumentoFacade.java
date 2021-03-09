package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.ViewDocumento;
import facturadesktopecuadoryomar.utilidades.StateEntity;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

public class DocumentoFacade extends AbstractFacade<Documento>
{
  public void guardar(Documento entidad, Empresa emp) throws SQLException {
    Documento xd = getDocumento(entidad.getNumeroDocu(), entidad.getCodigoTido(), emp);
    
    System.out.println("SE ESTA GENERANDO UN DOCUMENTO: " + entidad.getNumeroDocu());
    if (xd == null) {
      entidad.setEstadoCorreoDocu("PENDIENTE");
      entidad.setFirmadoDocu(false);
      entidad.setFechaLlegadaDocu(new Date());
      if (entidad.getEstadoDocu() != null) {
        if (!entidad.getEstadoDocu().equals("DEVUELTA")) {
          entidad.setEstadoDocu("AUTORIZADO");
        } else {
          entidad.setEstadoDocu("DEVUELTA");
        } 
      } else {
        entidad.setEstadoDocu("AUTORIZADO");
      } 
      guardar(entidad);
      System.out.println("SE HA GUARDADO EL DOCUMENTO NUEVO " + entidad.getNumeroDocu());
    }
    else { 
      if (xd.getEstadoDocu().equals("DEVUELTA") || xd.getEstadoDocu().equals("RECHAZADO") || xd.getEstadoDocu().equals("RECHAZADA")) {
      xd.setInformacionDocu(entidad.getInformacionDocu());
      xd.setXmlFirmDocu(entidad.getXmlFirmDocu());
      xd.setEstadoDocu("AUTORIZADO");
      xd.setEstadoClienteDocu(entidad.getEstadoClienteDocu());
      xd.setFirmadoDocu(Boolean.valueOf(false));
      xd.setFechaLlegadaDocu(new Date());
      xd.setCodigoClien(entidad.getCodigoClien());
      xd.setClaveAccesoDocu(entidad.getClaveAccesoDocu());
      xd.setCodigoEmpr(entidad.getCodigoEmpr());
      xd.setCodigoTido(entidad.getCodigoTido());
      xd.setCorreoDocu(entidad.getCorreoDocu());
      xd.setCuentaClienteDocu(entidad.getCuentaClienteDocu());
      xd.setDocSustentoDocu(entidad.getDocSustentoDocu());
      xd.setEstadoClienteDocu(entidad.getEstadoClienteDocu());
      xd.setEstadoCorreoDocu(entidad.getEstadoCorreoDocu());
      xd.setEstadoDMZ("P");
      xd.setFechaEmisionDocu(entidad.getFechaEmisionDocu());
      xd.setIvaActual(entidad.getIvaActual());
      xd.setMensajeDocu(entidad.getMensajeDocu());
      xd.setObservacionDocu(entidad.getObservacionDocu());
      xd.setPathRideDocu(entidad.getPathRideDocu());
      xd.setUrlRideDocu(entidad.getUrlRideDocu());
      xd.setTipoDocuCliente(entidad.getTipoDocuCliente());
      modificar(xd);
      System.out.println("SE HA ACTUALIZADO EL DOCUMENTO : " + xd.getNumeroDocu());
    } 
  }
  }
  
  public Documento getDocumento(String establecimiento, String puntoEmision, String secuencial, int codigoTipoDoc) {
    StringBuilder sb = new StringBuilder();
    Query q = getEntitiManager().createNamedQuery("Documento.findDocumento");
    q.setParameter("numero", sb.toString());
    q.setParameter("codigoTipoDoc", codigoTipoDoc);
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    List<Documento> lista = q.getResultList();
    if (lista.size() > 0) {
      return lista.get(0);
    }
    return null;
  }
  
  public Documento getDocumento(String numero, int codigoTipoDoc, Empresa emp) {
    Query q = getEntitiManager().createNamedQuery("Documento.findDocumento");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setParameter("numero", numero);
    q.setParameter("empresa", emp.getCodigoInterno());
    q.setParameter("codigoTipoDoc", codigoTipoDoc);
    List<Documento> lista = q.getResultList();
    if (lista.size() > 0) {
      return lista.get(0);
    }
    return null;
  }
  
  public Documento buscarPorCodigo(long codigo) {
    Query q = getEntitiManager().createNamedQuery("Documento.findByCodigoDocu");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setParameter("codigoDocu", codigo);
    List<Documento> lista = q.getResultList();
    if (lista.size() > 0) {
      return lista.get(0);
    }
    return null;
  }
  
  public ViewDocumento buscarViewDocPorCodigo(long codigo) {
    Query q = getEntitiManager().createNamedQuery("ViewDocumento.findByCodigoDocu");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setParameter("codigoDocu", codigo);
    List<ViewDocumento> lista = q.getResultList();
    if (lista.size() > 0) {
      return lista.get(0);
    }
    return null;
  }

  
  public List<Documento> listaDocumentosPendientes(int limite) {
    Query q = getEntitiManager().createNamedQuery("Documento.findActualizarDocumentos");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosAutorizadosPendientesCliente(int limite) {
    Query q = getEntitiManager().createNamedQuery("Documento.findActualizarDocumentosAutCliente");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosPendientesDMZ(int limite) throws SQLException {
    Query q = getEntitiManager().createNamedQuery("Documento.findActualizarDMZ");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosPendientesDMZAnulados(int limite) throws SQLException {
    Query q = getEntitiManager().createNamedQuery("Documento.findActualizarDMZPendiente");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosPendientesDMZEnviados(int limite) {
    Query q = getEntitiManager().createNamedQuery("Documento.findActualizarDMZAutorizado");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosPendientesFirma(int limite) {
    Query q = getEntitiManager().createNamedQuery("Documento.findPendienteFirma");
    q.setMaxResults(limite);
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosPendientesFirma(int limite, Empresa emp) {
    Query q = getEntitiManager().createNamedQuery("Documento.findPendienteFirmaPorEmpresa");
    q.setParameter("empresa", emp.getCodigoInterno());
    q.setMaxResults(limite);
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosPendientesEnvio(int limite) {
    Query q = getEntitiManager().createNamedQuery("Documento.findPendienteEnvio");
    q.setMaxResults(limite);
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosPendientesEnvio(int limite, Empresa emp, Date fechaEmisionIni) {
    Query q = getEntitiManager().createNamedQuery("Documento.findPendienteEnvio");
    q.setParameter("empresa", emp.getCodigoInterno());
    q.setParameter("fechaEmisionIni", fechaEmisionIni);
    q.setMaxResults(limite);
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosPendientesEnvioPorFechaEmision(int limite, Date fechaEmisionIni) {
    Query q = getEntitiManager().createNamedQuery("Documento.findPendienteEnvio");
    q.setParameter("fechaEmisionIni", fechaEmisionIni);
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosGenerados(int limite) {
    Query q = getEntitiManager().createNamedQuery("Documento.findGenerados");
    q.setParameter("estado", StateEntity.GENERADO.name());
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<ViewDocumento> listaDocumentosEnvioCorreo(int limite) {
    Query q = getEntitiManager().createNamedQuery("ViewDocumento.findEnvioCorreo");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<ViewDocumento> listaDocumentosEnvioCorreo(int limite, Empresa emp) {
    Query q = getEntitiManager().createNamedQuery("ViewDocumento.findEnvioCorreoPorEmpresa");
    q.setParameter("empresa", emp.getCodigoInterno());
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosEnviados(int limite) {
    Query q = getEntitiManager().createNamedQuery("Documento.findEnviados");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> listaDocumentosEnviados(int limite, Empresa emp) {
    Query q = getEntitiManager().createNamedQuery("Documento.findEnviados");
    q.setParameter("empresa", emp.getCodigoInterno());
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  public List<Documento> listaDocumentosClaveAccesoRegistrada(int limite, Empresa emp) {
    Query q = getEntitiManager().createNamedQuery("Documento.findClaveAccesoRegistrada");
    q.setParameter("empresa", emp.getCodigoInterno());
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
  
  public List<Documento> docsprueba(int limite) {
    Query q = getEntitiManager().createQuery("SELECT d FROM Documento d WHERE (d.firmadoDocu=0 or d.firmadoDocu is null)");
    q.setHint("javax.persistence.cache.storeMode", "REFRESH");
    q.setMaxResults(limite);
    return q.getResultList();
  }
}