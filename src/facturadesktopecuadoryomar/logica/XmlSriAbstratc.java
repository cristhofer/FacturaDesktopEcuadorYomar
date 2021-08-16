package facturadesktopecuadoryomar.logica;

import com.ecuasis.coresri.excepciones.ClaveAccesoException;
import com.ecuasis.coresri.util.GeneracionClaveAcceso;
import facturadesktopecuadoryomar.excepciones.FormatoCargaException;
import facturadesktopecuadoryomar.modelo.servidor.Configuracion;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import facturadesktopecuadoryomar.modelo.servidor.EtiquetaAdicional;
import facturadesktopecuadoryomar.modelo.servidor.TipoDocumento;
import facturadesktopecuadoryomar.modelo.servidor.Usuario;
import facturadesktopecuadoryomar.modelo.servidor.UsuarioRol;
import facturadesktopecuadoryomar.modelo.servidor.VewFormatoEmpresa;
import facturadesktopecuadoryomar.utilidades.UtilFormato;
import facturadesktopecuadoryomar.utilidades.XmlBuilder;
import oracle.fe.modelo.FacturacionIntermedia;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList; 

public abstract class XmlSriAbstratc {
  protected Configuracion configuracion = null;
  protected Empresa empresa = null;
  protected final DecimalFormat decimalFormat = new DecimalFormat("000000000");
  protected TipoDocumento tipoDoc;
  protected List<VewFormatoEmpresa> formatoDocs;
  protected FacturacionIntermedia carga = null;
  protected EmpresaFacade empresaFacade = new EmpresaFacade();
  protected TipoDocumentoFacade tipoDocFacade = new TipoDocumentoFacade();
  protected VistaFormatoFacade vistaFacade = new VistaFormatoFacade();
  private final TipoEmisionFacade tipoEmiFacade = new TipoEmisionFacade();
  private final TipoAmbienteFacade tipoAmbFacade = new TipoAmbienteFacade();
  protected UsuarioFacade usuarioFacade = new UsuarioFacade();
  protected ConfiguracionFacade configuracionFacade = new ConfiguracionFacade();
  protected RolFacade rolFacade = new RolFacade();
  protected UtilFormato utilFormato = null;

  //CJTM
  protected List<String> informacionAdicional = new ArrayList<String>(); 
  
  public XmlSriAbstratc(FacturacionIntermedia carga) throws Exception {
    if (carga == null) {
      throw new Exception("La carga recibida no puede ser nula");
    }
    if (carga.getListaInformacion() == null) {
      StringBuilder sberror = new StringBuilder();
      sberror.append("La carga no contiene lineas o el formato no es correcto.Carga: ");
      sberror.append(carga.getTramaDoc());
      throw new Exception(sberror.toString());
    } 
    this.carga = carga;
    String ruc = ((String)this.carga.getListaInformacion().get(0)).split("\\|")[0];
    this.empresa = this.empresaFacade.buscarEmpresaPorRuc(ruc);
    if (this.empresa == null) {
      StringBuilder sberror = new StringBuilder();
      sberror.append("La empresa no se encuentra registrada. Ruc ingresado: [");
      sberror.append(ruc);
      sberror.append("]");
      throw new Exception(sberror.toString());
    } 
    this.tipoDoc = this.tipoDocFacade.buscarPorCodigo(carga.getTipoDoc());
    if (this.tipoDoc == null) {
      StringBuilder sberror = new StringBuilder();
      sberror.append("ERROR: FC-03=>El tipo de documento ingresado no existe o no se encuentra registrado: [");
      sberror.append(carga.getTipoDoc());
      sberror.append("]");
      throw new Exception(sberror.toString());
    } 
    this.formatoDocs = this.vistaFacade.buscarFormatoPorTipo(this.tipoDoc);
    this.configuracion = this.empresa.getIdConfiguracion();
    if (this.configuracion == null) {
      StringBuilder sberror = new StringBuilder();
      sberror.append("La configuracion de la empresa no se encuentra: [");
      sberror.append(this.empresa.getRazonSocialEmpr());
      sberror.append("]");
      throw new Exception(sberror.toString());
    } 
    this.utilFormato = new UtilFormato(this.formatoDocs);
  }
  
  protected Usuario getUsuarioDcoumento(String ruc, String razonSocial, String correo) {
    Usuario usuario = this.usuarioFacade.buscarPorRuc(ruc);
    if (usuario == null) {
      usuario = new Usuario();
      usuario.setIdentificacionUsua(ruc);
      usuario.setEstadoUsua("ACTIVO");
      usuario.setNombreUsua(ruc);
      usuario.setPasswordUsua(ruc);
      usuario.setNombreCompletoUsua(razonSocial);
      usuario.setNombreComercialUsua(razonSocial);
      usuario.setEsEmpresaUsua(Boolean.TRUE);
      usuario.setCorreoUsua(correo);
      List<UsuarioRol> listaRoles = new LinkedList<>();
      UsuarioRol ur = new UsuarioRol();
      ur.setCodigoRole(this.rolFacade.buscarPorNombreRol("CLIENTE"));
      ur.setCodigoUsua(usuario);
      listaRoles.add(ur);
      usuario.setUsuarioRolList(listaRoles);
      try {
        this.usuarioFacade.guardar(usuario);
      } catch (SQLException ex) {
        Logger.getLogger(XmlSriFacturaExportacion.class.getName()).log(Level.SEVERE, null, ex);
      } 
    } 
    return usuario;
  }
  
  public abstract Documento getDocumento() throws ClaveAccesoException, SQLException, FormatoCargaException;
  
  public abstract String getInfoDocumento() throws FormatoCargaException;
  
  protected String getInfoTributaria() throws ClaveAccesoException {
    StringBuilder sb = new StringBuilder();
    sb.append(XmlBuilder.createOpenTag("infoTributaria", new Object[0]));
    sb.append(XmlBuilder.createTag("ambiente", this.configuracion.getAmbienteConf()));
    sb.append(XmlBuilder.createTag("tipoEmision", this.configuracion.getTipoEmisionConf()));
    sb.append(XmlBuilder.createTag("razonSocial", this.empresa.getRazonSocialEmpr()));
    sb.append(XmlBuilder.createTag("nombreComercial", this.empresa.getNombreComercialEmpr()));
    sb.append(XmlBuilder.createTag("ruc", this.empresa.getRucEmpr()));
    sb.append(XmlBuilder.createTag("claveAcceso", generarClaveAcceso()));
    sb.append(XmlBuilder.createTag("codDoc", this.carga.getTipoDoc()));
    sb.append(XmlBuilder.createTag("estab", this.carga.getCodEstab()));
    sb.append(XmlBuilder.createTag("ptoEmi", this.carga.getPtoEmi()));
    sb.append(XmlBuilder.createTag("secuencial", this.decimalFormat.format(Integer.parseInt(this.carga.getNumFact()))));
    sb.append(XmlBuilder.createTag("dirMatriz", this.empresaFacade.buscarEstablecimientoMatriz(this.empresa).getDireccionEstab()));
    
    /*AGREGADO CJTM 04/08/2021*/
    for (EtiquetaAdicional etiquetaAdicional : this.empresa.getEtiquetaList()) {
        sb.append(
            XmlBuilder.createTag(
                etiquetaAdicional.getTipoAgente().getEtiquetaTipoAgente(), 
                etiquetaAdicional.getTipoAgente().getLeyendaTipoAgente()
            )
        );
    }
    /*AGREGADO CJTM 04/08/2021*/
    
    sb.append(XmlBuilder.createCloseTag("infoTributaria"));
    return sb.toString().trim();
  }
  
  protected String getInfoAdicional(String... adicional) {
    StringBuilder sb = new StringBuilder();
    String infoAdicional = "";
    try {
      infoAdicional = this.utilFormato.getValueTag("campoAdicional", this.carga);
    } catch (FormatoCargaException ex) {
      infoAdicional = null;
    } 
    int existe = 0;
    if (infoAdicional != null || adicional.length > 0) {
      sb.append(XmlBuilder.createOpenTag("infoAdicional"));
      if (infoAdicional != null && 
        infoAdicional.trim().length() > 0) {
        String[] info = infoAdicional.split(":X:");
        for (int x = 0; x < info.length; x++) {
          String[] infodato = info[x].split(";X;");
          if (infodato.length == 2) {
            //sb.append(XmlBuilder.createTag("campoAdicional", new Object[] { "nombre", infodato[0], infodato[1] }));
            sb.append(XmlBuilder.createTag("campoAdicional", "nombre", infodato[0], infodato[1]));
            existe = 1;
          } 
        } 
      } 
      for (int i = 0; i < adicional.length; i += 2) {
        if (adicional[i + 1] != null) {
          if (adicional[i + 1].trim().length() > 0) {
            //sb.append(XmlBuilder.createTag("campoAdicional", new Object[] { "nombre", adicional[i], adicional[i + 1] }));
            sb.append(XmlBuilder.createTag("campoAdicional", "nombre", adicional[i], adicional[i + 1]));
            existe = 1;
          }
        }
      } 
      
      sb.append(XmlBuilder.createCloseTag("infoAdicional"));
    } 
    if (existe == 1) {
      return sb.toString();
    }
    return "";
  }

  /* AGREGADO POR CJTM */
  protected String getInfoAdicional2(String infoAdicional,String ...adicional) {
    StringBuilder sb = new StringBuilder();
    int existe = 0;
    if (infoAdicional != null || adicional.length > 0) {
        sb.append(XmlBuilder.createOpenTag("infoAdicional"));
        if (infoAdicional != null) {
            if (infoAdicional.trim().length() > 0) {
                String[] info = infoAdicional.split(":X:");
                for (int x = 0; x < info.length; x++) {
                    String[] infodato = info[x].split(";X;");
                    if (infodato.length == 2) {
                        sb.append(XmlBuilder.createTag("campoAdicional", "nombre", infodato[0], infodato[1]));
                        existe = 1;
                    }
                }
            }
        }
        for (int i = 0; i < adicional.length; i = i + 2) {
            if (adicional[i + 1] != null) {
                if (adicional[i + 1].trim().length() > 0) {
                    sb.append(XmlBuilder.createTag("campoAdicional", "nombre", adicional[i], adicional[i + 1]));
                    existe = 1;
                }
            }
        }
        sb.append(XmlBuilder.createCloseTag("infoAdicional"));
    }
    if (existe == 1) {
        return sb.toString();
    } else {
        return "";
    }
}
  
  protected String generarClaveAcceso() throws ClaveAccesoException {
    GeneracionClaveAcceso gc = new GeneracionClaveAcceso(this.tipoDocFacade.listarTipoDoc(), this.tipoAmbFacade.listarTipoDoc(), this.tipoEmiFacade.listarTipoDoc());
    return gc.generarClave(this.carga.getFechaEmision(), this.carga.getTipoDoc(), this.empresa.getRucEmpr(), this.configuracion
        .getAmbienteConf(), this.carga.getCodEstab() + this.carga.getPtoEmi(), this.decimalFormat
        .format(Integer.parseInt(this.carga.getNumFact())), this.configuracion.getCodigoNumericoConf(), this.configuracion
        .getTipoEmisionConf());
  }
  
  protected String cleanXml(String xml) {
    if (xml != null) {
      String xmlResult = xml.replace("#", " ");
      xmlResult = xml.replace("&", " ");
      xmlResult = xml.replace("\\", " ");
      xmlResult = xml.replace("Í", " ");
      return xmlResult;
    } 
    return "";
  }
}