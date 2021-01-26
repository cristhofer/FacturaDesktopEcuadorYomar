package facturadesktopecuadoryomar.utilidades;

import facturadesktopecuadoryomar.excepciones.FormatoCargaException;
import facturadesktopecuadoryomar.modelo.servidor.VewFormatoEmpresa;
import oracle.fe.modelo.FacturacionIntermedia;
import java.util.List;

public class UtilFormato
{
  private final List<VewFormatoEmpresa> listaFormato;
  
  public UtilFormato(List<VewFormatoEmpresa> lista) {
    this.listaFormato = lista;
  }
  
  public VewFormatoEmpresa getTag(String tag) {
    for (VewFormatoEmpresa f : this.listaFormato) {
      if (f.getTagForm().trim().toUpperCase().equals(tag.trim().toUpperCase())) {
        return f;
      }
    } 
    return null;
  }
  
  public String getValueTag(String tag, FacturacionIntermedia carga) throws FormatoCargaException {
    for (VewFormatoEmpresa f : this.listaFormato) {
      if (f.getTagForm().trim().toUpperCase().equals(tag.trim().toUpperCase())) {
        String data[] = carga.getListaInformacion().get(0).split("\\|");
        if (f.getOrdenForm() > data.length) {
          throw new FormatoCargaException(tag, carga.getListaInformacion().get(0), f.getOrdenForm());
        }
        return cleanValue(data[f.getOrdenForm() - 1]);
      } 
    } 
    return "";
  }
  
  public String getValueTag(String tag, String carga) throws FormatoCargaException {
    for (VewFormatoEmpresa f : this.listaFormato) {
      if (f.getTagForm().trim().toUpperCase().equals(tag.trim().toUpperCase())) {
        String data[] = carga.split("\\|");
        if (f.getOrdenForm() > data.length) {
          throw new FormatoCargaException(tag, carga, f.getOrdenForm());
        }
        return cleanValue(data[f.getOrdenForm() - 1]);
      } 
    } 
    return "";
  }
  
  public String getXmlValueTag(String tagPadre, String tag, String carga) throws FormatoCargaException {
    for (VewFormatoEmpresa f : this.listaFormato) {
      if (f.getPadreTagForm() != null){
        if (f.getPadreTagForm().toUpperCase().trim().contains(tagPadre.trim().toUpperCase()) && 
            f.getTagForm().trim().toUpperCase().equals(tag.trim().toUpperCase())) {
          StringBuilder sb = new StringBuilder();
          String data[] = carga.split("\\|");
          if (f.getOrdenForm() > data.length) {
            throw new FormatoCargaException(tag, carga, f.getOrdenForm());
          }
          if (data[f.getOrdenForm() - 1].trim().length() > 0) {
            sb.append(XmlBuilder.createTag(f.getTagForm(), data[f.getOrdenForm() - 1].trim()));
            return cleanValue(sb.toString());
          } else {
            return "";
          }
        }
      }
    } 
    return "";
  }
  
  public String getXmlValueTag(String tag, String carga) throws FormatoCargaException {
    for (VewFormatoEmpresa f : this.listaFormato) {
      if (f.getTagForm().trim().toUpperCase().equals(tag.trim().toUpperCase())) {
        StringBuilder sb = new StringBuilder();
        String data[] = carga.split("\\|");
        if (f.getOrdenForm() > data.length) {
          throw new FormatoCargaException(tag, carga, f.getOrdenForm());
        }
        if (data[f.getOrdenForm() - 1].trim().length() > 0) {
          sb.append(XmlBuilder.createTag(f.getTagForm(), data[f.getOrdenForm() - 1].trim()));
          return cleanValue(sb.toString());
        } else {
          return "";
        }
      } 
    } 
    return "";
  }
  
  public VewFormatoEmpresa getTag(String tagPadre, String tag) {
    for (VewFormatoEmpresa f : this.listaFormato) {
      if (f.getPadreTagForm() != null){
        if(f.getPadreTagForm().toUpperCase().trim().contains(tagPadre.trim().toUpperCase()) && 
          f.getTagForm().trim().toUpperCase().equals(tag.trim().toUpperCase())) {
          return f;
        }
      }
    } 
    return null;
  }
  
  public String getValueTag(String tagPadre, String tag, FacturacionIntermedia carga) throws FormatoCargaException {
    for (VewFormatoEmpresa f : this.listaFormato) {
      if (f.getPadreTagForm() != null) {
        if (f.getPadreTagForm().toUpperCase().trim().contains(tagPadre.trim().toUpperCase()) && f
        .getTagForm().trim().toUpperCase().equals(tag.trim().toUpperCase())) {
          String data[] = carga.getListaInformacion().get(0).split("\\|");
          if (f.getOrdenForm() > data.length) {
            throw new FormatoCargaException(tag, carga.getListaInformacion().get(0), f.getOrdenForm());
          }
          return cleanValue(data[f.getOrdenForm() - 1]);
        }
      }
    } 
    return "";
  }
  
  public String getValueTag(String tagPadre, String tag, String carga) throws FormatoCargaException {
    for (VewFormatoEmpresa f : this.listaFormato) {
      if (f.getPadreTagForm() != null) {
        if(f.getPadreTagForm().toUpperCase().trim().contains(tagPadre.trim().toUpperCase()) && 
          f.getTagForm().trim().toUpperCase().equals(tag.trim().toUpperCase())) {
          String data[] = carga.split("\\|");
          if (f.getOrdenForm() > data.length) {
            throw new FormatoCargaException(tag, carga, f.getOrdenForm());
          }
          return cleanValue(data[f.getOrdenForm() - 1]);
        } 
      }
    }
    return "";
  }
  
  private String cleanValue(String value) {
    value = value.replace("&#x02;", "");
    value = value.replace("&", "");
    value = value.replace("í", "i");
    value = value.replace("#", "");
    value = value.replace("Í", "I");
    return value.trim();
  }
}