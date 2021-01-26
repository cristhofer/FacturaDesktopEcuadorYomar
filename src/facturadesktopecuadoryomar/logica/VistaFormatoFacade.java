package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.TipoDocumento;
import facturadesktopecuadoryomar.modelo.servidor.VewFormatoEmpresa;
import facturadesktopecuadoryomar.utilidades.XmlBuilder;
import java.util.List;
import javax.persistence.Query;

public class VistaFormatoFacade extends AbstractFacade<VewFormatoEmpresa>
{
  public List<VewFormatoEmpresa> buscarFormatoPorEmpresa(Empresa emp, TipoDocumento tipo) {
    Query q = getEntitiManager().createNamedQuery("VewFormatoEmpresa.findCargasEmpresa");
    q.setParameter("emp", emp.getCodigoEmpr());
    q.setParameter("tipo", tipo.getIdentificadorTico());
    return q.getResultList();
  }
  
  public List<VewFormatoEmpresa> buscarFormatoPorTipo(TipoDocumento tipo) {
    Query q = getEntitiManager().createNamedQuery("VewFormatoEmpresa.findCargasByTipo");
    q.setParameter("tipo", tipo.getIdentificadorTico());
    return q.getResultList();
  }
  
  public VewFormatoEmpresa buscarTag(String tag, Empresa emp, TipoDocumento tipo) {
    Query q = getEntitiManager().createNamedQuery("VewFormatoEmpresa.findCargasTag");
    q.setParameter("emp", emp.getCodigoEmpr());
    q.setParameter("tipo", tipo.getIdentificadorTico());
    q.setParameter("tag", tag);
    List<VewFormatoEmpresa> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public VewFormatoEmpresa buscarTagCompleto(String tag, Empresa emp, TipoDocumento tipo) {
    String[] datoTag = tag.split("\\.");
    String padreTag = "";
    for (int i = 0; i < datoTag.length - 1; i++) {
      padreTag += datoTag[i] + ".";
    }
    padreTag = "%" + padreTag.substring(0, padreTag.length() - 1) + "%";
    Query q = getEntitiManager().createNamedQuery("VewFormatoEmpresa.findCargasTagCompleto");
    q.setParameter("emp", emp.getCodigoEmpr());
    q.setParameter("tipo", tipo.getIdentificadorTico());
    q.setParameter("tag", datoTag[datoTag.length - 1]);
    q.setParameter("padre", padreTag);
    List<VewFormatoEmpresa> l = q.getResultList();
    if (l.size() > 0) {
      return l.get(0);
    }
    return null;
  }
  
  public String obtenerNodosXmlPadreTag(String padreTag, String carga, List<VewFormatoEmpresa> formatoList) {
    StringBuilder sb = new StringBuilder();
    String[] infoCarga = carga.split("\\|");
    
    for (VewFormatoEmpresa vf : formatoList) {
      if (vf.getPadreTagForm() != null && 
        vf.getPadreTagForm().trim().contains(padreTag.trim()))
      {
        sb.append(XmlBuilder.createTag(vf.getTagForm(), infoCarga[vf.getOrdenForm() - 1].trim()));
      }
    }
    
    return sb.toString();
  }
  
  public String obtenerNodosXmlPadreTag(String padreTag, String tag, String carga, List<VewFormatoEmpresa> formatoList) {
    StringBuilder sb = new StringBuilder();
    String[] infoCarga = carga.split("\\|");
    
    for (VewFormatoEmpresa vf : formatoList) {
      if (vf.getPadreTagForm() != null) {
        if (vf.getPadreTagForm().trim().contains(padreTag.trim()) && vf.getTagForm().equals(tag))
        {
          sb.append(XmlBuilder.createTag(vf.getTagForm(), infoCarga[vf.getOrdenForm() - 1].trim()));
        }
      }
    }
    
    return sb.toString();
  }
}