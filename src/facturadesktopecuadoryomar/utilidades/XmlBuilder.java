package facturadesktopecuadoryomar.utilidades;

public class XmlBuilder
{
  public static String createTag(String tag, Object info) {
    StringBuilder sb = new StringBuilder();
    sb.append("<");
    sb.append(tag.trim());
    sb.append(">");
    sb.append(info.toString().trim());
    sb.append("</");
    sb.append(tag.trim());
    sb.append(">");
    return sb.toString().trim();
  }
  
  public static String createTag(String tag, Object... attrib) {
    StringBuilder sb = new StringBuilder();
    sb.append("<");
    sb.append(tag.trim());
    if (attrib.length % 2 == 0) {
      for (int i = 0; i < attrib.length; i++) {
        sb.append(" ");
        sb.append(attrib[i].toString().trim());
        sb.append("=\"");
        i++;
        sb.append(attrib[i].toString().trim());
        sb.append("\" ");
      } 
      sb.append("/>");
    } else {
      for (int i = 0; i < attrib.length - 1; i++) {
        sb.append(" ");
        sb.append(attrib[i].toString().trim());
        sb.append("=\"");
        i++;
        sb.append(attrib[i].toString().trim());
        sb.append("\" ");
      } 
      sb.append(">");
      sb.append(attrib[attrib.length - 1].toString().trim());
      sb.append("</");
      sb.append(tag.trim());
      sb.append(">");
    } 
    return sb.toString().trim();
  }
  
  public static String createOpenTag(String tag, Object... attrib) {
    StringBuilder sb = new StringBuilder();
    sb.append("<");
    sb.append(tag.trim());
    for (int i = 0; i < attrib.length; i++) {
      sb.append(" ");
      sb.append(attrib[i].toString().trim());
      sb.append("=\"");
      i++;
      sb.append(attrib[i].toString().trim());
      sb.append("\" ");
    } 
    sb.append(">");
    return sb.toString().trim();
  }
  
  public static String createCloseTag(String tag) {
    StringBuilder sb = new StringBuilder();
    sb.append("</");
    sb.append(tag.trim());
    sb.append(">");
    return sb.toString().trim();
  }
  
  public static String initXml(String version, String code) {
    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"").append(version.trim()).append("\" encoding=\"").append(code.trim()).append("\"?>");
    return sb.toString().trim();
  }
}