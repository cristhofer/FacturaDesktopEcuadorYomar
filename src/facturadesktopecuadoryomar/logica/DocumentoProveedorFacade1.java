package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.servidor.DocumentosRecibidos;
import facturadesktopecuadoryomar.utilidades.ConexionSql;
import facturadesktopecuadoryomar.utilidades.InformacionConexion;
import facturadesktopecuadoryomar.xml.NodoXml;
import facturadesktopecuadoryomar.xml.XmlInformationUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentoProveedorFacade1
{
  private ConexionSql conexion;
  
  public DocumentoProveedorFacade1() {
    InformacionConexion infoConn = new InformacionConexion("oracle.jdbc.OracleDriver", "USERFAC", "INCARPALM", "jdbc:oracle:thin:@192.168.7.28:1521:DBINCARPALM");
    this.conexion = new ConexionSql(infoConn);
  }
  
  public void actualizarDocumentoProveedor(DocumentosRecibidos dr) {
    StringBuilder sqlInsert = new StringBuilder();
    sqlInsert.append("INSERT INTO INCARPALM.BA_COMPROBANTE_TMP (");
    sqlInsert.append("COMPANIA,");
    sqlInsert.append("TIPO,");
    sqlInsert.append("NUMERO,");
    sqlInsert.append("FECHA,");
    sqlInsert.append("PROVEEDOR,");
    sqlInsert.append("DOLARES,");
    sqlInsert.append("DETALLE,");
    sqlInsert.append("RENGLON,");
    sqlInsert.append("FECHA_INSERT,");
    sqlInsert.append("USUARIO_INSERT,");
    sqlInsert.append("FECHA_UPDATE,");
    sqlInsert.append("FECHA_DEPOSITO )");
    sqlInsert.append(" VALUES ");
    sqlInsert.append("('IN','BATD02',?,?,?,?,?,?,?,?,?,?)");


    
    try {
      InfoRetencion infoRet = getListadoInfoRetencion(dr);
      this.conexion.execute(sqlInsert.toString(),
                    dr.getCodigoDocu(),
                    new java.sql.Date(new Date().getTime()),
                    dr.getRucClienteDocu(),
                    infoRet.getTotalRetenido().floatValue(),
                    "INTERFACE DE DOCUMENTOS RETENCIONES ELECTRONICAS",
                    infoRet.getImpuesto().size(),
                    new java.sql.Date(new Date().getTime()),
                    "USERFAC",
                    new java.sql.Date(new Date().getTime()),
                    new java.sql.Date(dr.getFechaEmisionDocu().getTime()));
    }
    catch (SQLException ex) {
      Logger.getLogger(DocumentoProveedorFacade1.class.getName()).log(Level.SEVERE, null, ex);
      System.err.println("" + ex.getMessage());
      System.out.println("" + ex.getMessage());
    } 
  }
  
  private void guardarDetalleRetencion(InfoRetencion info, DocumentosRecibidos dr) throws SQLException {
    StringBuilder sqlInsert = new StringBuilder();
    sqlInsert.append("INSERT INTO INCARPALM.BA_DCOMPROBANTE_TMP ");
    sqlInsert.append("(COMPANIA,TIPO, NUMERO, RENGLON, DETALLE, DOLARES,");
    sqlInsert.append("FECHA_INSERT, USUARIO_INSERT, FECHA_UPDATE, USUARIO_UPDATE,");
    sqlInsert.append("FACTURA, NSERIE1, NSERIE2, NO_RETENCION, CODIGO_RETENCION,");
    sqlInsert.append("FECHA_IMPRESION_RETENCION,FECHA_CADUCIDAD_RETENCION, NUMERO_AUT_RETENCION) ");
    sqlInsert.append(" VALUES ");
    sqlInsert.append("('IN','BATD02',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    String[] numRet = dr.getNumeroDocu().split("\\-");
    
    for (int i = 0; i < info.getImpuesto().size(); i++) {
      this.conexion.execute(sqlInsert.toString(), 
              dr.getCodigoDocu(), 
            (i + 1), 
            dr.getRazonsocialClienteDocu() + " Cpto.", 
            info.getImpuesto().get(i)[3], 
            new java.sql.Date(new Date().getTime()),
                    "USERFAC",
                    new java.sql.Date(new Date().getTime()),
                    "USERFAC",
                    info.getImpuesto().get(i)[4].toString(),
                    numRet[0],
                    numRet[1],
                    numRet[2],
                    Integer.parseInt(info.getImpuesto().get(i)[2].toString()),
                    new java.sql.Date(new Date().getTime()),
                    new java.sql.Date(new Date().getTime()),
                    dr.getNumeroAutorizacionDocu());
    } 
  }
  
  private InfoRetencion getListadoInfoRetencion(DocumentosRecibidos dr) {
    InfoRetencion info = new InfoRetencion();
    NodoXml nodo = XmlInformationUtil.getNodo("impuestos", dr.getXmlFirmDocu());
    XmlInformationUtil.getChildrens(nodo);
    if (dr.getTipoComprobante().equals("07")) {
      BigDecimal totalDolares = new BigDecimal(BigInteger.ZERO);
      BigDecimal dolares = new BigDecimal(BigInteger.ZERO);
      Integer totalRenglon = 0;
      String numeroDocumento = "", codigotipo = "", codigoRetencion = "";
      for (int i = 0; i < nodo.getChildrenList().size(); i++) {
        for (int j = 0; j < nodo.getChildrenList().get(i).getChildrenList().size(); j++) {
          
          NodoXml n = nodo.getChildrenList().get(i).getChildrenList().get(j);
          switch (n.getName()) {
            case "valorRetenido":
              totalDolares = totalDolares.add(new BigDecimal(n.getValue()));
              dolares = new BigDecimal(n.getValue());
              break;
            case "numDocSustento":
              numeroDocumento = n.getValue();
              break;
            case "codigo":
              codigotipo = n.getValue();
              break;
            case "codigoRetencion":
              codigoRetencion = n.getValue();
              break;
          } 
        } 
        totalRenglon++;
        info.getImpuesto().add(new Object[]{totalRenglon, codigotipo, codigoRetencion, dolares.floatValue(), numeroDocumento});
      } 
      info.setTotalRetenido(totalDolares);
    } 
    
    return info;
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    this.conexion = null;
  }
}