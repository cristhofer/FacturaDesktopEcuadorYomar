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
import java.util.logging.Level;
import java.util.logging.Logger;
import facturadesktopecuadoryomar.modelo.servidor.EtiquetaAdicional;

public class XmlSriNotaCredito extends XmlSriAbstratc {
  String correo;
  String direccion;
  
  public XmlSriNotaCredito(FacturacionIntermedia carga) throws Exception {
    super(carga);
  }
  
  public Documento getDocumento() throws FormatoCargaException {
    Documento doc = new Documento();
    try {
      String razon = this.utilFormato.getValueTag("infoNotaCredito", "razonSocialComprador", this.carga);
      String ruc = this.utilFormato.getValueTag("infoNotaCredito", "identificacionComprador", this.carga);
      String docSustento = this.utilFormato.getValueTag("infoNotaCredito", "numDocModificado", this.carga);
      this.correo = this.utilFormato.getValueTag("correo", this.carga);
      this.direccion = this.utilFormato.getValueTag("direccion", this.carga);
      Usuario usuario = getUsuarioDcoumento(ruc, razon, this.correo);
      doc.setCorreoDocu(this.correo);
      doc.setCodigoClien(usuario.getCodigoUsua());
      doc.setClaveAccesoDocu(generarClaveAcceso());
      doc.setCodigoEmpr(this.empresa);
      doc.setCodigoTido(this.tipoDoc.getCodigoTico());
      doc.setEstadoDocu("GENERADO");
      doc.setInformacionDocu(this.carga.getTramaDoc());
      doc.setNumeroDocu(this.carga.getNumeroDocumento());
      doc.setFechaEmisionDocu(this.carga.getFechaEmision());
      doc.setXmlFirmDocu(generarNotaCredito());
      doc.setCuentaClienteDocu(this.carga.getIdDocumento());
      doc.setTipoDocuCliente(this.carga.getCodCliente());
      doc.setUrlRideDocu(this.configuracion.getDocNotacreditoConf());
      doc.setPathRideDocu(this.configuracion.getDocNotacreditoPathConf());
      doc.setIvaActual(this.configuracion.getIvaConf().floatValue());
      doc.setFirmadoDocu(Boolean.FALSE);
      doc.setDocSustentoDocu(docSustento);
    } catch (ClaveAccesoException ex) {
      Logger.getLogger(XmlSriNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(XmlSriNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
    } 
    return doc;
  }
  
  public String generarNotaCredito() throws ClaveAccesoException, SQLException, FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    if (this.carga.getListaInformacion().size() > 0) {
      String[] datoCarga = ((String)this.carga.getListaInformacion().get(0)).split("\\|");
      sb.append(XmlBuilder.initXml("1.0", "UTF-8"));
      sb.append(XmlBuilder.createOpenTag("notaCredito", "id", "comprobante", "version", "1.1.0"));
      sb.append(getInfoTributaria());
      sb.append(getInfoDocumento());
      sb.append(getDetalle());
      /* if (this.empresa.getExportadora().booleanValue()) {
        sb.append(getInfoAdicional(new String[] { "email", this.correo, "direccion", this.direccion, "Agenteretención", "EXPORTADOR HABITUAL DE BIENES" }));
      } else {
        sb.append(getInfoAdicional(new String[] { "email", this.correo, "direccion", this.direccion }));
      }  */

      //Línea agregada por CJTM
      this.informacionAdicional.add("email");
      this.informacionAdicional.add(correo);
      this.informacionAdicional.add("direccion");
      this.informacionAdicional.add(direccion);
      
      /*for (
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
      }*/

      sb.append(
        getInfoAdicional(
          this.informacionAdicional
              .toArray(
                new String[this.informacionAdicional.size()]
              )
        )
      );

      sb.append(XmlBuilder.createCloseTag("notaCredito"));
    } 
    return cleanXml(sb.toString());
  }
  
  public String getInfoDocumento() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("infoNotaCredito"));
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    sb.append(XmlBuilder.createTag("fechaEmision", df.format(this.carga.getFechaEmision())));
    try {
      //sb.append(XmlBuilder.createTag("dirEstablecimiento", this.empresaFacade.buscarEstablecimiento(this.carga.getCodEstab()).getDireccionEstab()));
      sb.append(XmlBuilder.createTag(
        "dirEstablecimiento", 
        this.empresaFacade.buscarEstablecimientoDireccion(
        super.empresa,
        Integer.parseInt(carga.getCodEstab())).getDireccionEstab()
        )
      );
    } catch (Exception ex) {
      System.err.println("NO SE HA ENCONTRADO EL ESTABLECIMIENTO");
    } 
    
    String datoCarga = this.carga.getListaInformacion().get(0);
    
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "tipoIdentificacionComprador", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "razonSocialComprador", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "identificacionComprador", datoCarga));
    if (this.empresa.getNumeroEspecialEmpr() != null){
      if(this.empresa.getNumeroEspecialEmpr().length() > 0) {
        sb.append(XmlBuilder.createTag("contribuyenteEspecial", this.empresa.getNumeroEspecialEmpr()));
      }
    }
    
    sb.append(XmlBuilder.createTag("obligadoContabilidad", (this.empresa.getObligadoEmpr() == true) ? "SI" : "NO"));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "codDocModificado", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "numDocModificado", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "fechaEmisionDocSustento", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "totalSinImpuestos", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "valorModificacion", datoCarga));
    sb.append(XmlBuilder.createTag("moneda", "DOLAR"));
    sb.append(generarTotalConImpuestos(this.carga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaCredito", "motivo", datoCarga));
    sb.append(XmlBuilder.createCloseTag("infoNotaCredito"));
    
    return sb.toString();
  }
  
  private String getDetalle() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("detalles"));
    for (String c : this.carga.getListaInformacion()) {
      sb.append(XmlBuilder.createOpenTag("detalle"));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "codigoInterno", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "codigoAdicional", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "descripcion", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "cantidad", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "precioUnitario", c));
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
    return sb.toString();
  }
  
  private String generarTotalConImpuestos(FacturacionIntermedia carga) {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("totalConImpuestos"));
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
    VewFormatoEmpresa vcod = this.utilFormato.getTag("detalle.impuestos", "codigo");
    VewFormatoEmpresa vcodPor = this.utilFormato.getTag("detalle.impuestos", "codigoPorcentaje");
    VewFormatoEmpresa vbaseImp = this.utilFormato.getTag("detalle.impuestos", "baseImponible");
    VewFormatoEmpresa vvalorImp = this.utilFormato.getTag("detalle.impuestos", "valor");
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