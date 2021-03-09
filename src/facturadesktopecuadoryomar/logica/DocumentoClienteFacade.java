package facturadesktopecuadoryomar.logica;

import facturadesktopecuadoryomar.modelo.cliente.Carga;
import facturadesktopecuadoryomar.modelo.servidor.Documento;
import facturadesktopecuadoryomar.modelo.servidor.TipoDocumento;
import facturadesktopecuadoryomar.utilidades.ConexionSql;
import facturadesktopecuadoryomar.utilidades.InformacionConexion;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentoClienteFacade
{
  private ConexionSql conexion;
  private TipoDocumentoFacade tipoDocFacade = new TipoDocumentoFacade();
  
  public DocumentoClienteFacade() {
    InformacionConexion infoConn = new InformacionConexion("oracle.jdbc.OracleDriver", "USUARIO_FE", "palmar2017", "jdbc:oracle:thin:@192.168.51.12:1521:PALMAR");
    this.conexion = new ConexionSql(infoConn);
  }
  
  public List<Carga> listarCargasCliente(int limite) {
    List<Carga> lista = new LinkedList<>();
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT id,numero,establecimiento,punto_emision,tipo_documento,codigo_cliente,informacion,tipo_envio,fecha_emision,tipo FROM dbo.documentos_electronicos WHERE ");
    sql.append("estado='P';");
    this.conexion.openConnection();
    List<List<Object>> l = this.conexion.listResult(sql.toString(), limite);
    for (List<Object> lis : l) {
      Carga nueva = new Carga();
      nueva.setCodigo(Long.parseLong(lis.get(0).toString()));
      nueva.setSecuencial(lis.get(1).toString());
      nueva.setEstablecimiento(lis.get(2).toString());
      nueva.setPuntoEmision(lis.get(3).toString());
      nueva.setTipoDocumento(lis.get(4).toString());
      if (lis.get(5) != null) {
        nueva.setCodigoCliente(lis.get(5).toString());
      }
      nueva.setInformacion(lis.get(6).toString());
      nueva.setTipoEnvio(Integer.parseInt(lis.get(7).toString()));
      nueva.setFechaEmision((Date)lis.get(8));
      nueva.setTipo(lis.get(9).toString());
      lista.add(nueva);
    } 
    this.conexion.closeConnection();
    return lista;
  }
  
  public List<Carga> listarCargasCliente(int inicio, int limite) {
    List<Carga> lista = new LinkedList<>();
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT ID,num_fact,cod_estab,pto_emi,tipo_doc,cod_cliente,trama_doc,tipo_envio,fecha_emision FROM dbo.fe_doc_data3 WHERE ");
    sql.append("estado='P';");
    this.conexion.openConnection();
    List<List<Object>> l = this.conexion.listResult(sql.toString(), limite);
    for (List<Object> lis : l) {
      Carga nueva = new Carga();
      nueva.setCodigo(Long.parseLong(lis.get(0).toString()));
      nueva.setSecuencial(lis.get(1).toString());
      nueva.setEstablecimiento(lis.get(2).toString());
      nueva.setPuntoEmision(lis.get(3).toString());
      nueva.setTipoDocumento(lis.get(4).toString());
      nueva.setCodigoCliente(lis.get(5).toString());
      nueva.setInformacion(lis.get(6).toString());
      nueva.setTipoEnvio(Integer.parseInt(lis.get(7).toString()));
      nueva.setFechaEmision((Date)lis.get(8));
      lista.add(nueva);
    } 
    this.conexion.closeConnection();
    return lista;
  }
  
  public void actualizarCargasCliente(List<Carga> listaCargas) {
    StringBuilder sql = new StringBuilder();
    this.conexion.openConnection();
    sql.append("UPDATE dbo.documentos_electronicos  SET estado='E',fecha_registro=? WHERE tipo_documento=? AND establecimiento=? AND punto_emision=? AND numero=?");
    for (int i = 0; i < listaCargas.size(); i++) {
      Carga ca = listaCargas.get(i);
      try {
        this.conexion.execute(sql.toString(), new Date(), ca.getTipoDocumento(), ca.getEstablecimiento(), ca.getPuntoEmision(), ca.getSecuencial());
      } catch (SQLException ex) {
        Logger.getLogger(DocumentoClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
      } 
    } 
    this.conexion.closeConnection();
  }
  
  public void actualizarCargaEnviada(Carga ca) throws SQLException {
    StringBuilder sql = new StringBuilder();
    this.conexion.openConnection();
    this.conexion.beginTransaction();
    sql.append("UPDATE dbo.documentos_electronicos  SET estado='E',fecha_registro=? WHERE tipo_documento=? AND establecimiento=? AND punto_emision=? AND numero=?");
    this.conexion.execute(sql.toString(), new java.sql.Date(new Date().getTime()), ca.getTipoDocumento(), ca.getEstablecimiento(), ca.getPuntoEmision(), ca.getSecuencial());
    this.conexion.commitTransaction();
    this.conexion.closeConnection();
  }

















  
  public int actualizarCliente(Documento doc, Integer bandera) {
    StringBuilder sqlUpdate = new StringBuilder();
    sqlUpdate.append("UPDATE ");
    TipoDocumento tipDoc = this.tipoDocFacade.buscarPorCodigoTipo(doc.getCodigoTido());
    
    this.conexion.openConnection();
    
    try {
      this.conexion.beginTransaction();
      
      Timestamp t = null;
      if (doc.getFechaAutorizacionDocu() != null) {
        t = new Timestamp(doc.getFechaAutorizacionDocu().getTime());
      }
      switch (tipDoc.getIdentificadorTico()) {
        case "01":
          sqlUpdate.append("IG_FACTURA ");
          sqlUpdate.append("SET RMD_STATE=?,AUTORIZACION_SRI=?,CLAVE_SRI=? ");
          sqlUpdate.append("WHERE COMPANIA=? AND TIPO=? AND NUMERO=? AND ESTABLECIMIENTO=? AND PUNTO_EMISION=?");
          this.conexion.execute(sqlUpdate.toString(), new Object[] { bandera, doc.getNumeroAutorizacionDocu(), doc.getClaveAccesoDocu(), doc
                .getCodigoEmpr().getCodigoInterno(), doc.getTipoDocuCliente(), doc.getCuentaClienteDocu(), doc
                .getNumeroDocu().substring(0, 3), doc.getNumeroDocu().substring(4, 7) });
          break;
        case "04":
          sqlUpdate.append("IG_FACTURA ");
          sqlUpdate.append("SET RMD_STATE=?,AUTORIZACION_SRI=?,CLAVE_SRI=? ");
          sqlUpdate.append("WHERE COMPANIA=? AND TIPO=? AND NUMERO=? AND ESTABLECIMIENTO=? AND PUNTO_EMISION=?");
          this.conexion.execute(sqlUpdate.toString(), new Object[] { bandera, doc.getNumeroAutorizacionDocu(), doc.getClaveAccesoDocu(), doc
                .getCodigoEmpr().getCodigoInterno(), doc.getTipoDocuCliente(), doc.getCuentaClienteDocu(), doc
                .getNumeroDocu().substring(0, 3), doc.getNumeroDocu().substring(4, 7) });
          break;
        case "05":
          sqlUpdate.append("IG_FACTURA ");
          sqlUpdate.append("SET RMD_STATE=?,AUTORIZACION_SRI=?,CLAVE_SRI=? ");
          sqlUpdate.append("WHERE COMPANIA=? AND TIPO=? AND NUMERO=? AND ESTABLECIMIENTO=? AND PUNTO_EMISION=?");
          this.conexion.execute(sqlUpdate.toString(), new Object[] { bandera, doc.getNumeroAutorizacionDocu(), doc.getClaveAccesoDocu(), doc
                .getCodigoEmpr().getCodigoInterno(), doc.getTipoDocuCliente(), doc.getCuentaClienteDocu(), doc
                .getNumeroDocu().substring(0, 3), doc.getNumeroDocu().substring(4, 7) });
          break;
        case "06":
          sqlUpdate.append("INV_GUIA_REMISION ");
          break;
        case "07":
          if (doc.getDescripcionDoc().equals("NO")) {
            String[] num = doc.getNumeroDocu().split("\\-");
            sqlUpdate.append("IG_COMPROBANTE ");
            sqlUpdate.append("SET RMD_STATE=?,AUTORIZACION_SRI=?,CLAVE_SRI=? ");
            sqlUpdate.append("WHERE COMPANIA=? AND TIPO=? AND NUMERO=? AND SRI_NSERIE1_COMPROBANTE=? AND SRI_NSERIE2_COMPROBANTE=? AND SRI_NUMERO_COMPROBANTE=?");
            this.conexion.execute(sqlUpdate.toString(), new Object[] { bandera, doc.getNumeroAutorizacionDocu(), doc.getClaveAccesoDocu(), doc
                  .getCodigoEmpr().getCodigoInterno(), doc.getTipoDocuCliente(), doc.getCuentaClienteDocu(), num[0], num[1], 
                  quitarCeros(num[2]) }); break;
          } 
          if (doc.getDescripcionDoc().equals("PR")) {
            sqlUpdate.append("IG_COMPROBANTE_DET_FACTURA ");
            sqlUpdate.append("SET RMD_STATE=?,AUTORIZACION_SRI=?,CLAVE_SRI=? ");
            sqlUpdate.append("WHERE COMPANIA=? AND TIPO=? AND NUMERO=? AND NO_EST_RET=? AND NO_PTO_RET=? AND NO_RETENCION=?");
            String[] num = doc.getNumeroDocu().split("\\-");
            this.conexion.execute(sqlUpdate.toString(), new Object[] { bandera, doc.getNumeroAutorizacionDocu(), doc.getClaveAccesoDocu(), doc
                  .getCodigoEmpr().getCodigoInterno(), doc.getTipoDocuCliente(), doc.getCuentaClienteDocu(), num[0], num[1], 
                  quitarCeros(num[2]) });
          } 
          break;
      } 


      
      this.conexion.commitTransaction();
    } catch (SQLException ex) {
      Logger.getLogger(DocumentoClienteFacade.class.getName()).log(Level.SEVERE, (String)null, ex);
      System.err.println("ESTADO SQL: " + ex.getSQLState());
      System.err.println("CODIGO ERROR: " + ex.getErrorCode());
      System.err.println("OTRO: " + ex.getLocalizedMessage());
      doc.setObservacionDocu(doc.getObservacionDocu() + ", ERROR ACT CLIENTE: " + ex.getSQLState() + "-" + ex.getErrorCode() + "-" + ex.getLocalizedMessage());
      try {
        this.conexion.rollbackTransaction();
      } catch (SQLException ex1) {
        Logger.getLogger(DocumentoClienteFacade.class.getName()).log(Level.SEVERE, (String)null, ex1);
      } 
      return -1;
    } 
    this.conexion.closeConnection();
    return 1;
  }
  
  public String quitarCeros(String n) {
    String d = "";
    Integer indice = Integer.valueOf(0);
    for (int i = 0; i < (n.toCharArray()).length; i++) {
      if (n.toCharArray()[i] != '0') {
        indice = Integer.valueOf(i);
        break;
      } 
    } 
    d = n.substring(indice.intValue());
    return d;
  }
}