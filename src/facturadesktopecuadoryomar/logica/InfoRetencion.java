package facturadesktopecuadoryomar.logica;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class InfoRetencion
{
  private List<Object[]> impuesto;
  private BigDecimal totalRetenido;
  
  public InfoRetencion() {}
  
  public InfoRetencion(List<Object[]> impuesto, BigDecimal totalRetenido) {
    this.impuesto = impuesto;
    this.totalRetenido = totalRetenido;
  }
  
  public List<Object[]> getImpuesto() {
    if (this.impuesto == null) {
      this.impuesto = new LinkedList();
    }
    return this.impuesto;
  }
  
  public void setImpuesto(List<Object[]> impuesto) {
    this.impuesto = impuesto;
  }
  
  public BigDecimal getTotalRetenido() {
    return this.totalRetenido;
  }
  
  public void setTotalRetenido(BigDecimal totalRetenido) {
    this.totalRetenido = totalRetenido;
  }
}