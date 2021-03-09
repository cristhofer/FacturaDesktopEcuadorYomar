package facturadesktopecuadoryomar.xml;

public class XmlInformationUtil
{
  public static String getValueTag(String tag, String xml) {
    StringBuilder tagInicio = new StringBuilder();
    tagInicio.append("<");
    tagInicio.append(tag);
    tagInicio.append(">");
    StringBuilder tagFin = new StringBuilder();
    tagFin.append("</");
    tagFin.append(tag);
    tagFin.append(">");
    int indice = xml.indexOf(tagInicio.toString()) + tagInicio.length();
    int fin = xml.indexOf(tagFin.toString());
    return xml.substring(indice, fin).trim();
  }
  
  public static void getChildrens(NodoXml n) {
    getNodos(n);
    for (int i = 0; i < n.getChildrenList().size(); i++) {
      if (!n.getChildrenList().get(i).getValue().contains("<")) {
        break;
      } else {
        getChildrens(n.getChildrenList().get(i));
      }
    }
  }
  
  public static NodoXml getNodo(String tag, String xml) {
    NodoXml n = new NodoXml();
    n.setName(tag);
    n.setValue(getValueTag(tag, xml));
    return n;
  }
  
  private static void getNodos(NodoXml n) {
    String xmlSig = n.getValue();
    while (true) {
      NodoXml n2 = new NodoXml();
      n2.setName(XmlInformationUtil.getFirstTag(xmlSig));
      n2.setValue(XmlInformationUtil.getValueTag(n2.getName(), xmlSig));
      StringBuilder sb = new StringBuilder();
      sb.append("<").append(n2.getName()).append(">").append(n2.getValue()).append("</").append(n2.getName()).append(">");
      xmlSig = xmlSig.replace(sb.toString(), "");
      n.getChildrenList().add(n2);

      if (xmlSig.length() <= 0) {
        break;
      }
    }
  }
  
  public static String getFirstTag(String xml) {
    String tagInicio = "<";
    String tagFin = ">";
    int indice = xml.indexOf(tagInicio) + tagInicio.length();
    int fin = xml.indexOf(tagFin);
    return xml.substring(indice, fin).trim();
  }
}