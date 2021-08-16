package facturadesktopecuadoryomar.logica;

import com.ecuasis.coresri.excepciones.ClaveAccesoException;
import facturadesktopecuadoryomar.excepciones.FormatoCargaException;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.Usuario;
import facturadesktopecuadoryomar.utilidades.XmlBuilder;
import oracle.fe.modelo.FacturacionIntermedia;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import facturadesktopecuadoryomar.modelo.servidor.EtiquetaAdicional;

public class XmlSriRetencion extends XmlSriAbstratc {
  private String correo, direccion;
  
  public XmlSriRetencion(FacturacionIntermedia carga) throws Exception {
    super(carga);
  }
  
  public Documento getDocumento() throws FormatoCargaException, ClaveAccesoException, SQLException {
    Documento doc = new Documento();
    String razon = this.utilFormato.getValueTag("infoCompRetencion", "razonSocialSujetoRetenido", this.carga);
    String ruc = this.utilFormato.getValueTag("infoCompRetencion", "identificacionSujetoRetenido", this.carga);
    String docSustento = this.utilFormato.getValueTag("impuestos.impuesto", "numDocSustento", this.carga);
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
    doc.setXmlFirmDocu(generarRetencion());
    doc.setUrlRideDocu(this.configuracion.getDocRetencionConf());
    doc.setPathRideDocu(this.configuracion.getDocRetencionPathConf());
    doc.setIvaActual(this.configuracion.getIvaConf().floatValue());
    doc.setFirmadoDocu(Boolean.FALSE);
    doc.setDocSustentoDocu(docSustento);
    doc.setCuentaClienteDocu(this.carga.getIdDocumento());
    doc.setTipoDocuCliente(this.carga.getCodCliente());
    return doc;
  }
  
  public String generarRetencion() throws ClaveAccesoException, SQLException, FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    if (this.carga.getListaInformacion().size() > 0) {
      String[] datoCarga = this.carga.getListaInformacion().get(0).split("\\|");
      sb.append(XmlBuilder.initXml("1.0", "UTF-8"));
      sb.append(XmlBuilder.createOpenTag("comprobanteRetencion", "id", "comprobante", "version", "1.0.0"));
      sb.append(getInfoTributaria());
      sb.append(getInfoDocumento());
      sb.append(getImpuestos());
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
      
      /*CJTM COMENTADO 02/08/2021
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

      sb.append(XmlBuilder.createCloseTag("comprobanteRetencion"));
    } 
    return cleanXml(sb.toString());
  }
  
  public String getInfoDocumento() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("infoCompRetencion"));
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
    if (this.empresa.getNumeroEspecialEmpr() != null) {
      if (this.empresa.getNumeroEspecialEmpr().length() > 0) {
        sb.append(XmlBuilder.createTag("contribuyenteEspecial", this.empresa.getNumeroEspecialEmpr()));
      }
    }

    
    String datoCarga = this.carga.getListaInformacion().get(0);
    sb.append(XmlBuilder.createTag("obligadoContabilidad", this.empresa.getObligadoEmpr() == true ? "SI" : "NO"));
    
    sb.append(this.utilFormato.getXmlValueTag("infoCompRetencion", "tipoIdentificacionSujetoRetenido", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoCompRetencion", "razonSocialSujetoRetenido", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoCompRetencion", "identificacionSujetoRetenido", datoCarga));
    String periodoFiscal = this.utilFormato.getValueTag("infoCompRetencion", "periodoFiscal", datoCarga);
    sb.append(XmlBuilder.createTag("periodoFiscal", periodoFiscal));
    sb.append(XmlBuilder.createCloseTag("infoCompRetencion"));
    return sb.toString();
  }
  
  private String getImpuestos() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("impuestos"));
    for (String c : this.carga.getListaInformacion()) {
      sb.append(XmlBuilder.createOpenTag("impuesto"));
      sb.append(this.utilFormato.getXmlValueTag("impuestos.impuesto", "codigo", c));
      sb.append(this.utilFormato.getXmlValueTag("impuestos.impuesto", "codigoRetencion", c));
      sb.append(this.utilFormato.getXmlValueTag("impuestos.impuesto", "baseImponible", c));
      sb.append(this.utilFormato.getXmlValueTag("impuestos.impuesto", "porcentajeRetener", c));
      sb.append(this.utilFormato.getXmlValueTag("impuestos.impuesto", "valorRetenido", c));
      sb.append(this.utilFormato.getXmlValueTag("impuestos.impuesto", "codDocSustento", c));
      String numdocsustento = this.utilFormato.getValueTag("impuestos.impuesto", "numDocSustento", c);
      String valorNumSustento = "";
      Formatter fmt = new Formatter();
      if (numdocsustento != null) {
        if (numdocsustento.trim().length() > 0) {
        if (numdocsustento.split("\\-").length > 1) {
            String[] dato = numdocsustento.split("\\-");
            if (dato.length == 3) {
              try {
                valorNumSustento = fmt.format("%03d", Integer.parseInt(dato[0])).toString();
                fmt = new Formatter();
                valorNumSustento += fmt.format("%03d", Integer.parseInt(dato[1])).toString();
                fmt = new Formatter();
                valorNumSustento += fmt.format("%09d", Integer.parseInt(dato[2])).toString();
              } catch (NumberFormatException ex) {
                throw new FormatoCargaException(c, "Numero de Documento de Sustento", "Error al Tratar de Formatear", numdocsustento);
              } 
            }
          } else {
            valorNumSustento = numdocsustento;
          } 
        }
      }
      
      sb.append(XmlBuilder.createTag("numDocSustento", valorNumSustento));
      sb.append(this.utilFormato.getXmlValueTag("impuestos.impuesto", "fechaEmisionDocSustento", c));
      sb.append(XmlBuilder.createCloseTag("impuesto"));
    } 
    sb.append(XmlBuilder.createCloseTag("impuestos"));
    return sb.toString();
  }
}