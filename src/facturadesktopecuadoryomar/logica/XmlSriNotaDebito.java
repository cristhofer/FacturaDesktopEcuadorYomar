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

public class XmlSriNotaDebito extends XmlSriAbstratc {
  String correo;
  String direccion;
  
  public XmlSriNotaDebito(FacturacionIntermedia carga) throws Exception {
    super(carga);
  }
  
  public Documento getDocumento() throws FormatoCargaException {
    Documento doc = new Documento();
    try {
      String razon = this.utilFormato.getValueTag("infoNotaDebito", "razonSocialComprador", this.carga);
      String ruc = this.utilFormato.getValueTag("infoNotaDebito", "identificacionComprador", this.carga);
      String docSustento = this.utilFormato.getValueTag("infoNotaDebito", "numDocModificado", this.carga);
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
      doc.setXmlFirmDocu(generarNotaDebito());
      doc.setCuentaClienteDocu(this.carga.getIdDocumento());
      doc.setTipoDocuCliente(this.carga.getCodCliente());
      doc.setUrlRideDocu(this.configuracion.getDocNotadebitoConf());
      doc.setPathRideDocu(this.configuracion.getDocNotadebitoPathConf());
      doc.setIvaActual(this.configuracion.getIvaConf().floatValue());
      doc.setFirmadoDocu(Boolean.FALSE);
      doc.setDocSustentoDocu(docSustento);
    } catch (ClaveAccesoException ex) {
      Logger.getLogger(XmlSriNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(XmlSriNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
    } 
    return doc;
  }
  
  public String generarNotaDebito() throws ClaveAccesoException, SQLException, FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    if (this.carga.getListaInformacion().size() > 0) {
      String[] datoCarga = ((String)this.carga.getListaInformacion().get(0)).split("\\|");
      sb.append(XmlBuilder.initXml("1.0", "UTF-8"));
      sb.append(XmlBuilder.createOpenTag("notaDebito", "id", "comprobante", "version", "1.0.0"));
      sb.append(getInfoTributaria());
      sb.append(getInfoDocumento());
      sb.append(generarMotivos());
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
      
      /*COMENTADO CJTM 02/08/2021
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
      }*/

      sb.append(
        getInfoAdicional(
          this.informacionAdicional
              .toArray(
                new String[this.informacionAdicional.size()]
              )
        )
      );

      sb.append(XmlBuilder.createCloseTag("notaDebito"));
    } 
    return cleanXml(sb.toString());
  }
  
  public String getInfoDocumento() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("infoNotaDebito"));
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
    
    sb.append(this.utilFormato.getXmlValueTag("infoNotaDebito", "tipoIdentificacionComprador", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaDebito", "razonSocialComprador", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaDebito", "identificacionComprador", datoCarga));
    if (this.empresa.getNumeroEspecialEmpr() != null) {
      if (this.empresa.getNumeroEspecialEmpr().length() > 0) {
        sb.append(XmlBuilder.createTag("contribuyenteEspecial", this.empresa.getNumeroEspecialEmpr()));
      }
    }
    
    sb.append(XmlBuilder.createTag("obligadoContabilidad", this.empresa.getObligadoEmpr() == true ? "SI" : "NO"));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaDebito", "codDocModificado", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaDebito", "numDocModificado", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaDebito", "fechaEmisionDocSustento", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaDebito", "totalSinImpuestos", datoCarga));
    sb.append(generarTotalConImpuestos(this.carga));
    sb.append(this.utilFormato.getXmlValueTag("infoNotaDebito", "valorTotal", datoCarga));
    sb.append(generarPagos());
    sb.append(XmlBuilder.createCloseTag("infoNotaDebito"));
    return sb.toString();
  }
  
  private String generarTotalConImpuestos(FacturacionIntermedia carga) {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("impuestos"));
    sb.append(generarImpuesto("2", "0", carga));
    sb.append(generarImpuesto("2", "2", carga));
    sb.append(generarImpuesto("2", "6", carga));
    sb.append(generarImpuesto("2", "7", carga));
    sb.append(generarImpuesto("2", "3", carga));
    sb.append(XmlBuilder.createCloseTag("impuestos"));
    return sb.toString();
  }
  
  private String generarMotivos() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("motivos"));
    for (String c : this.carga.getListaInformacion()) {
      String m = this.utilFormato.getValueTag("motivos", "motivo", c);
      String[] motivoValor = m.split(";X;");
      if (motivoValor.length == 2) {
        sb.append(XmlBuilder.createOpenTag("motivo"));
        sb.append(XmlBuilder.createTag("razon", motivoValor[0]));
        sb.append(XmlBuilder.createTag("valor", motivoValor[1]));
        sb.append(XmlBuilder.createCloseTag("motivo"));
      } 
    } 
    sb.append(XmlBuilder.createCloseTag("motivos"));
    return sb.toString();
  }
  
  private String generarPagos() throws FormatoCargaException {
    String datoCarga = this.carga.getListaInformacion().get(0);
    String valorTotal = this.utilFormato.getValueTag("infoNotaDebito", "valorTotal", datoCarga);
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("pagos"));
    sb.append(XmlBuilder.createOpenTag("pago"));
    sb.append(XmlBuilder.createTag("formaPago", "20"));
    sb.append(XmlBuilder.createTag("total", valorTotal));
    sb.append(XmlBuilder.createCloseTag("pago"));
    sb.append(XmlBuilder.createCloseTag("pagos"));
    return sb.toString();
  }
  
  private String generarImpuesto(String cod, String codPor, FacturacionIntermedia cc) {
    StringBuilder sb = new StringBuilder();
    VewFormatoEmpresa vcod = this.utilFormato.getTag("detalle.impuestos", "codigo");
    VewFormatoEmpresa vcodPor = this.utilFormato.getTag("detalle.impuestos", "codigoPorcentaje");
    VewFormatoEmpresa vbaseImp = this.utilFormato.getTag("detalle.impuestos", "baseImponible");
    VewFormatoEmpresa vvalorImp = this.utilFormato.getTag("detalle.impuestos", "valor");
    VewFormatoEmpresa vtarifaImp = this.utilFormato.getTag("detalle.impuestos", "tarifa");
    BigDecimal baseImp = new BigDecimal(0);
    BigDecimal valorImp = new BigDecimal(0);
    BigDecimal tarifaImp = new BigDecimal(0);
    for (String c : cc.getListaInformacion()) {
      String[] dato = c.split("\\|");
      if (dato[vcod.getOrdenForm() - 1].trim().equals(cod) && dato[vcodPor.getOrdenForm() - 1].trim().equals(codPor)) {
        baseImp = baseImp.add(new BigDecimal(dato[vbaseImp.getOrdenForm() - 1]));
        valorImp = valorImp.add(new BigDecimal(dato[vvalorImp.getOrdenForm() - 1]));
        tarifaImp = new BigDecimal(dato[vtarifaImp.getOrdenForm() - 1]);
      } 
    } 
    if (baseImp.doubleValue() > 0) {
      sb.append(XmlBuilder.createOpenTag("impuesto"));
      sb.append(XmlBuilder.createTag("codigo", cod));
      sb.append(XmlBuilder.createTag("codigoPorcentaje", codPor));
      sb.append(XmlBuilder.createTag("tarifa", tarifaImp.toPlainString()));
      sb.append(XmlBuilder.createTag("baseImponible", baseImp.toPlainString()));
      sb.append(XmlBuilder.createTag("valor", valorImp.toPlainString()));
      sb.append(XmlBuilder.createCloseTag("impuesto"));
    } 
    return sb.toString().trim();
  }
}