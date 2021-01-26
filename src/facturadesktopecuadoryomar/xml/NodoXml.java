package facturadesktopecuadoryomar.xml;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NodoXml
{
  private String name;
  private String value;
  private Map<String, String> attributes;
  private List<NodoXml> childrenList;
  
  public NodoXml(String name, String value, Map<String, String> attributes, List<NodoXml> childrenList) {
    this.name = name;
    this.value = value;
    this.attributes = attributes;
    this.childrenList = childrenList;
  }
  
  public NodoXml() {}
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public Map<String, String> getAttributes() {
    return this.attributes;
  }
  
  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }
  
  public List<NodoXml> getChildrenList() {
    if (this.childrenList == null) {
      this.childrenList = new LinkedList<>();
    }
    return this.childrenList;
  }
  
  public void setChildrenList(List<NodoXml> childrenList) {
    this.childrenList = childrenList;
  }
}