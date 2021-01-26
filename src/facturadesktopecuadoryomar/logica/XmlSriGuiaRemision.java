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
import java.util.logging.Level;
import java.util.logging.Logger;
import facturadesktopecuadoryomar.modelo.servidor.EtiquetaAdicional;

public class XmlSriGuiaRemision
  extends XmlSriAbstratc
{
  String correo;
  
  public XmlSriGuiaRemision(FacturacionIntermedia carga) throws Exception {
    super(carga);
  }
  
  public Documento getDocumento() throws FormatoCargaException {
    Documento doc = new Documento();
    try {
      String razon = this.utilFormato.getValueTag("destinatarios.destinatario", "razonSocialDestinatario", this.carga);
      String ruc = this.utilFormato.getValueTag("destinatarios.destinatario", "identificacionDestinatario", this.carga);
      String docSustento = this.utilFormato.getValueTag("destinatarios.destinatario", "numDocSustento", this.carga);
      this.correo = this.utilFormato.getValueTag("correo", this.carga);
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
      doc.setXmlFirmDocu(generarGuiaRemision());
      doc.setCuentaClienteDocu(this.carga.getIdDocumento());
      doc.setTipoDocuCliente(this.carga.getCodCliente());
      doc.setUrlRideDocu(this.configuracion.getDocGuiaConf());
      doc.setPathRideDocu(this.configuracion.getDocGuiaPathConf());
      doc.setIvaActual(this.configuracion.getIvaConf().floatValue());
      doc.setFirmadoDocu(Boolean.FALSE);
      doc.setDocSustentoDocu(docSustento);
    } catch (ClaveAccesoException ex) {
      Logger.getLogger(XmlSriGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(XmlSriGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
    } 
    return doc;
  }
  
  public String generarGuiaRemision() throws ClaveAccesoException, SQLException, FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    if (this.carga.getListaInformacion().size() > 0) {
      String datoCarga = this.carga.getListaInformacion().get(0);
      sb.append(XmlBuilder.initXml("1.0", "UTF-8"));
      sb.append(XmlBuilder.createOpenTag("guiaRemision", "id", "comprobante", "version", "1.1.0"));
      sb.append(getInfoTributaria());
      sb.append(getInfoDocumento());
      sb.append(generarDestinatarios(datoCarga));
      /* if (this.empresa.getExportadora().booleanValue()) {
        sb.append(getInfoAdicional(new String[] { "email", this.correo, "Agenteretención", "EXPORTADOR HABITUAL DE BIENES" }));
      } else {
        sb.append(getInfoAdicional(new String[] { "email", this.correo }));
      }  */

      //Línea agregada por CJTM
      this.informacionAdicional.add("email");
      this.informacionAdicional.add(correo);
      
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

      sb.append(XmlBuilder.createCloseTag("guiaRemision"));
    } 
    return cleanXml(sb.toString());
  }
  
  public String getInfoDocumento() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("infoGuiaRemision"));
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
    sb.append(this.utilFormato.getXmlValueTag("infoGuiaRemision", "dirPartida", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoGuiaRemision", "razonSocialTransportista", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoGuiaRemision", "tipoIdentificacionTransportista", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoGuiaRemision", "rucTransportista", datoCarga));
    sb.append(XmlBuilder.createTag("obligadoContabilidad", this.empresa.getObligadoEmpr() == true ? "SI" : "NO"));
    if (this.empresa.getNumeroEspecialEmpr() != null) {
      if (this.empresa.getNumeroEspecialEmpr().length() > 0) {
        sb.append(XmlBuilder.createTag("contribuyenteEspecial", this.empresa.getNumeroEspecialEmpr()));
      }
    }
    
    sb.append(this.utilFormato.getXmlValueTag("infoGuiaRemision", "fechaIniTransporte", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoGuiaRemision", "fechaFinTransporte", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("infoGuiaRemision", "placa", datoCarga));
    sb.append(XmlBuilder.createCloseTag("infoGuiaRemision"));
    return sb.toString();
  }
  
  private String generarDestinatarios(String datoCarga) throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("destinatarios"));
    sb.append(XmlBuilder.createOpenTag("destinatario"));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "identificacionDestinatario", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "razonSocialDestinatario", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "dirDestinatario", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "motivoTraslado", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "docAduaneroUnico", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "codEstabDestino", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "ruta", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "codDocSustento", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "numDocSustento", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "numAutDocSustento", datoCarga));
    sb.append(this.utilFormato.getXmlValueTag("destinatarios.destinatario", "fechaEmisionDocSustento", datoCarga));
    sb.append(getDetalleDestinatario());
    sb.append(XmlBuilder.createCloseTag("destinatario"));
    sb.append(XmlBuilder.createCloseTag("destinatarios"));
    return sb.toString();
  }
  
  private String getDetalleDestinatario() throws FormatoCargaException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("detalles"));
    for (String c : this.carga.getListaInformacion()) {
      sb.append(XmlBuilder.createOpenTag("detalle"));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "codigoInterno", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "codigoAdicional", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "descripcion", c));
      sb.append(this.utilFormato.getXmlValueTag("detalles.detalle", "cantidad", c));
      sb.append(XmlBuilder.createCloseTag("detalle"));
    } 
    sb.append(XmlBuilder.createCloseTag("detalles"));
    return sb.toString();
  }
}