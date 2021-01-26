package facturadesktopecuadoryomar.logica;

import com.ecuasis.coresri.excepciones.ClaveAccesoException;
import facturadesktopecuadoryomar.excepciones.FormatoCargaException;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.Usuario;
import facturadesktopecuadoryomar.modelo.servidor.VewFormatoEmpresa;
import facturadesktopecuadoryomar.utilidades.XmlBuilder;
import oracle.fe.modelo.FacturacionIntermedia;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import facturadesktopecuadoryomar.modelo.servidor.EtiquetaAdicional;

public class XmlSriFactura extends XmlSriAbstratc {
  String correo;
  String cuenta;
  String direccion;
  
  public XmlSriFactura(FacturacionIntermedia carga) throws Exception {
    super(carga);
  }
  
  public Documento getDocumento() throws ClaveAccesoException, SQLException, FormatoCargaException {
    Documento doc = new Documento();
    String razon = this.utilFormato.getValueTag("infoFactura", "razonSocialComprador", this.carga);
    String ruc = this.utilFormato.getValueTag("infoFactura", "identificacionComprador", this.carga);
    this.correo = this.utilFormato.getValueTag("correo", this.carga);
    this.direccion = this.utilFormato.getValueTag("direccionComprador", this.carga);
    Usuario usuario = getUsuarioDcoumento(ruc, razon, this.correo);
    doc.setCorreoDocu(this.correo);
    doc.setDescripcionDoc(this.carga.getDescripcionDoc());
    doc.setCodigoClien(usuario.getCodigoUsua());
    doc.setClaveAccesoDocu(generarClaveAcceso());
    doc.setCodigoEmpr(this.empresa);
    doc.setCodigoTido(this.tipoDoc.getCodigoTico());
    doc.setEstadoDocu("AUTORIZADO");
    doc.setInformacionDocu(this.carga.getTramaDoc());
    doc.setNumeroDocu(this.carga.getNumeroDocumento());
    doc.setFechaEmisionDocu(this.carga.getFechaEmision());
    doc.setXmlFirmDocu(generarFactura());
    doc.setCuentaClienteDocu(this.carga.getIdDocumento());
    doc.setTipoDocuCliente(this.carga.getCodCliente());
    doc.setUrlRideDocu(this.configuracion.getDocFacturaConf());
    doc.setPathRideDocu(this.configuracion.getDocFacturaPathConf());
    doc.setIvaActual(this.configuracion.getIvaConf().floatValue());
    doc.setFirmadoDocu(Boolean.FALSE);
    return doc;
  }
  
  public String generarFactura() throws ClaveAccesoException, FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    if (this.carga.getListaInformacion().size() > 0) {
      sb.append(XmlBuilder.initXml("1.0", "UTF-8"));
      sb.append(XmlBuilder.createOpenTag("factura", "id", "comprobante", "version", "1.1.0"));
      sb.append(getInfoTributaria());
      sb.append(getInfoDocumento());
      sb.append(getDetalle());
      /* if (this.empresa.getExportadora().booleanValue()) {
        sb.append(getInfoAdicional(new String[] { "email", this.correo, "direccion", this.direccion, "Agenteretención", "EXPORTADOR HABITUAL DE BIENES" }));
      } else {
        sb.append(getInfoAdicional(new String[] { "email", this.correo, "direccion", this.direccion }));
      } */ 

      /* AGREGADO POR CJTM */
      this.informacionAdicional.add("email");
      this.informacionAdicional.add(correo);
      this.informacionAdicional.add("direccion");
      this.informacionAdicional.add(direccion);
            
      for (
        EtiquetaAdicional etiquetaAdicional : this.empresa.getEtiquetaList()
      ) {
        this.informacionAdicional.add(
          etiquetaAdicional.getTipoAgente()
                            .getEtiquetaTipoAgente()
        );
        
        this.informacionAdicional.add(
          etiquetaAdicional.getTipoAgente()
                            .getLeyendaTipoAgente()
        );
      }

      sb.append(
        getInfoAdicional(
          this.informacionAdicional
              .toArray(
                new String[this.informacionAdicional.size()]
              )
        )
      );

      sb.append(XmlBuilder.createCloseTag("factura"));
    } 
    return cleanXml(sb.toString());
  }
  
  public String getInfoDocumento() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("infoFactura"));
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    sb.append(XmlBuilder.createTag("fechaEmision", df.format(this.carga.getFechaEmision())));
    try {
      /* sb.append(XmlBuilder.createTag(
        "dirEstablecimiento", this.empresaFacade.buscarEstablecimiento(
          this.carga.getCodEstab()).getDireccionEstab()
        )
      ); */
      /* AGREGADO POR CJTM */
      sb.append(XmlBuilder.createTag(
        "dirEstablecimiento", this.empresaFacade.buscarEstablecimientoDireccion(
          super.empresa,
          Integer.parseInt(carga.getCodEstab())).getDireccionEstab()
        )
      );
    } catch (Exception ex) {
      System.err.println("NO SE HA ENCONTRADO EL ESTABLECIMIENTO");
    } 
    if (this.empresa.getNumeroEspecialEmpr() != null){ 
      if (this.empresa.getNumeroEspecialEmpr().length() > 0) {
        sb.append(XmlBuilder.createTag("contribuyenteEspecial", this.empresa.getNumeroEspecialEmpr()));
      }
    }
    
    String datoCarga = this.carga.getListaInformacion().get(0);
    sb.append(XmlBuilder.createTag("obligadoContabilidad", (this.empresa.getObligadoEmpr() == true) ? "SI" : "NO"));
    sb.append(this.utilFormato.getXmlValueTag("infoFactura", "tipoIdentificacionComprador", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoFactura", "razonSocialComprador", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoFactura", "identificacionComprador", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoFactura", "direccionComprador", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoFactura", "totalSinImpuestos", datoCarga));
    
    String ts = this.utilFormato.getValueTag("infoFactura", "totalSubsidio", datoCarga);
    Double tsd = 0d;
    if (ts.trim().length() > 0) {
      tsd = Double.parseDouble(ts);
    }
    if (tsd > 0) {
      sb.append(this.utilFormato.getXmlValueTag("infoFactura", "totalSubsidio", datoCarga));
    }
    sb.append(this.utilFormato.getXmlValueTag("infoFactura", "totalDescuento", datoCarga));
    sb.append(generarTotalConImpuestos(this.carga));
    sb.append(this.utilFormato.getXmlValueTag("infoFactura", "propina", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoFactura", "importeTotal", datoCarga));
    sb.append(XmlBuilder.createTag("moneda", "DOLAR"));
    sb.append(pagos());
    sb.append(XmlBuilder.createCloseTag("infoFactura"));
    return sb.toString();
  }
  
  private String pagos() throws FormatoCargaException {
    String datoCarga = this.carga.getListaInformacion().get(0);
    String datoFormaPago = this.utilFormato.getValueTag("infoFactura", "pago", datoCarga);
    if (datoFormaPago != null) {
      if (!datoFormaPago.isEmpty()) {
        String[] forma = datoFormaPago.split("\\:");
        StringBuilder sb = new StringBuilder();
        sb.append(XmlBuilder.createOpenTag("pagos"));
        sb.append(XmlBuilder.createOpenTag("pago"));
        sb.append(XmlBuilder.createTag("formaPago", forma[0]));
        sb.append(XmlBuilder.createTag("total", forma[1]));
        sb.append(XmlBuilder.createTag("plazo", forma[2]));
        sb.append(XmlBuilder.createTag("unidadTiempo", forma[3]));
        sb.append(XmlBuilder.createCloseTag("pago"));
        sb.append(XmlBuilder.createCloseTag("pagos"));
        return sb.toString();
      } 
      return pagoDefault();
    } 
    
    return pagoDefault();
  }
  
  private String pagoDefault() throws FormatoCargaException {
    String datoCarga = this.carga.getListaInformacion().get(0);
    String valorTotal = this.utilFormato.getValueTag("infoFactura", "importeTotal", datoCarga);
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("pagos"));
    sb.append(XmlBuilder.createOpenTag("pago"));
    sb.append(XmlBuilder.createTag("formaPago", "20"));
    sb.append(XmlBuilder.createTag("total", valorTotal));
    sb.append(XmlBuilder.createCloseTag("pago"));
    sb.append(XmlBuilder.createCloseTag("pagos"));
    return sb.toString();
  }
  
  private String getDetalle() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("detalles"));
    
    String cargaInicial = this.carga.getListaInformacion().get(0);
    BigDecimal precioTotalSinImpuesto = new BigDecimal(this.utilFormato.getValueTag("infoFactura", "totalSinImpuestos", cargaInicial));
    BigDecimal valorTotal = new BigDecimal(this.utilFormato.getValueTag("infoFactura", "importeTotal", cargaInicial));
    BigDecimal sumaPrecioTSI = new BigDecimal(0);
    BigDecimal sumaPrecioVT = new BigDecimal(0);
    for (String c : this.carga.getListaInformacion()) {
      sb.append(XmlBuilder.createOpenTag("detalle"));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "codigoPrincipal", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "descripcion", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "cantidad", c));
      
      String pu = this.utilFormato.getValueTag("detalles.detalle", "precioUnitario", c);
      String pss = this.utilFormato.getValueTag("detalles.detalle", "precioSinSubsidio", c);
      
      sumaPrecioTSI = sumaPrecioTSI.add(new BigDecimal(this.utilFormato.getValueTag("detalles.detalle", "precioTotalSinImpuesto", c)));
      sumaPrecioVT = sumaPrecioVT.add(new BigDecimal(this.utilFormato.getValueTag("detalle.impuestos", "valor", c)));
      
      Double pud = 0d, pssd = 0d;
      if (pu.trim().length() > 0) {
        pud = Double.parseDouble(pu);
      }
      if (pss.trim().length() > 0) {
        pssd = Double.parseDouble(pss);
      }
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "precioUnitario", c));
      if (pssd > pud) {
        sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "precioSinSubsidio", c));
      }
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "descuento", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "precioTotalSinImpuesto", c));
      sb.append(XmlBuilder.createOpenTag("impuestos"));
      sb.append(XmlBuilder.createOpenTag("impuesto"));
      sb.append(this.utilFormato.getXmlValueTag("detalle.impuestos", "codigo", c));
      sb.append(this.utilFormato.getXmlValueTag("detalle.impuestos", "codigoPorcentaje", c));
      sb.append(this.utilFormato.getXmlValueTag("detalle.impuestos", "tarifa", c));
      sb.append(this.utilFormato.getXmlValueTag("detalle.impuestos", "baseImponible", c));
      sb.append(this.utilFormato.getXmlValueTag("detalle.impuestos", "valor", c));
      sb.append(XmlBuilder.createCloseTag("impuesto"));
      sb.append(XmlBuilder.createCloseTag("impuestos"));
      sb.append(XmlBuilder.createCloseTag("detalle"));
    } 
    sb.append(XmlBuilder.createCloseTag("detalles"));
    if (sumaPrecioTSI.doubleValue() != precioTotalSinImpuesto.doubleValue()) {
      throw new FormatoCargaException(cargaInicial, "totalSinImpuestos", "ERROR EN DIFERENCIAS EN TOTAL SIN IMPUESTOS=>suma:" + sumaPrecioTSI.doubleValue() + ", totalsinim: " + precioTotalSinImpuesto.doubleValue(), "msg");
    } else {
      BigDecimal aux = sumaPrecioTSI.add(sumaPrecioVT);
      if (aux.doubleValue() != valorTotal.doubleValue()) {
        throw new FormatoCargaException(cargaInicial, "importeTotal", "ERROR EN DIFERENCIAS EN IMPORTE TOTAL", "msg");
      }
    }
    
    return sb.toString();
  }
  
  private String generarTotalConImpuestos(FacturacionIntermedia carga) {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("totalConImpuestos", new Object[0]));
    sb.append(generarImpuesto("2", "0", carga));
    sb.append(generarImpuesto("2", "2", carga));
    sb.append(generarImpuesto("2", "6", carga));
    sb.append(generarImpuesto("2", "7", carga));
    sb.append(generarImpuesto("2", "3", carga));
    sb.append(XmlBuilder.createCloseTag("totalConImpuestos"));
    return sb.toString();
  }
  
  private String generarImpuesto(String cod, String codPor, FacturacionIntermedia cc) {
    StringBuilder sb = new StringBuilder();
    VewFormatoEmpresa vcod = this.utilFormato.getTag("detalles.detalle.impuestos", "codigo");
    VewFormatoEmpresa vcodPor = this.utilFormato.getTag("detalles.detalle.impuestos", "codigoPorcentaje");
    VewFormatoEmpresa vbaseImp = this.utilFormato.getTag("detalles.detalle.impuestos", "baseImponible");
    VewFormatoEmpresa vvalorImp = this.utilFormato.getTag("detalles.detalle.impuestos", "valor");
    BigDecimal baseImp = new BigDecimal(0);
    BigDecimal valorImp = new BigDecimal(0);
    for (String c : cc.getListaInformacion()) {
      String[] dato = c.split("\\|");
      if (dato[vcod.getOrdenForm() - 1].trim().equals(cod) && dato[vcodPor.getOrdenForm() - 1].trim().equals(codPor)) {
        baseImp = baseImp.add(new BigDecimal(dato[vbaseImp.getOrdenForm() - 1]));
        valorImp = valorImp.add(new BigDecimal(dato[vvalorImp.getOrdenForm() - 1]));
      } 
    } 
    if (baseImp.doubleValue() > 0) {
      sb.append(XmlBuilder.createOpenTag("totalImpuesto"));
      sb.append(XmlBuilder.createTag("codigo", cod));
      sb.append(XmlBuilder.createTag("codigoPorcentaje", codPor));
      sb.append(XmlBuilder.createTag("baseImponible", baseImp.toPlainString()));
      sb.append(XmlBuilder.createTag("valor", valorImp.toPlainString()));
      sb.append(XmlBuilder.createCloseTag("totalImpuesto"));
    } 
    
    return sb.toString().trim();
  }
}