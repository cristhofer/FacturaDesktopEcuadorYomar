package facturadesktopecuadoryomar.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionSql
{
  private Connection connection = null;
  private InformacionConexion infoConexion = null;
  
  public ConexionSql(InformacionConexion ic) {
    try {
      this.infoConexion = ic;
      Class.forName(this.infoConexion.getDriver());
      this.connection = DriverManager.getConnection(this.infoConexion.getUrl(), this.infoConexion.getUsuario(), this.infoConexion.getPassword());
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(ConexionSql.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(ConexionSql.class.getName()).log(Level.SEVERE, null, ex);
    } 
  }
  
  public boolean openConnection() {
    if (this.connection != null) {
      try {
        if (this.connection.isClosed()) {
          this.connection = DriverManager.getConnection(this.infoConexion.getUrl(), this.infoConexion.getUsuario(), this.infoConexion.getPassword());
        }
      } catch (SQLException ex) {
        Logger.getLogger(ConexionSql.class.getName()).log(Level.SEVERE, null, ex);
      } 
    }
    return false;
  }
  
  public boolean closeConnection() {
    if (this.connection != null) {
      try {
        if (!this.connection.isClosed()) {
          this.connection.close();
          return true;
        } 
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "No se ha podido cerrar la conexion: [" + ex.getMessage() + "]");
        return false;
      } 
    }
    return false;
  }
  
  public Object singleResult(String sql, Object... params) {
    try {
      PreparedStatement stm = this.connection.prepareStatement(sql);
      if (params != null) {
        int contParam = 1;
        for (Object p : params) {
          stm.setObject(contParam, p);
          contParam++;
        } 
      } 
      ResultSet rs = stm.executeQuery();
      if (rs.next() == true) {
        return rs.getObject(1);
      }
      return null;
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Ha sucedido un error : [" + ex.getMessage() + "]");
      return null;
    } 
  }
  
  public List<List<Object>> listResult(String sql, Object... params) {
    List<List<Object>> listado = new LinkedList<>();
    try {
      PreparedStatement stm = this.connection.prepareStatement(sql);
      if (params != null) {
        int contParam = 1;
        for (Object p : params) {
          stm.setObject(contParam, p);
          contParam++;
        } 
      } 
      ResultSet rs = stm.executeQuery();
      ResultSetMetaData rsm = rs.getMetaData();
      while (rs.next()) {
        List<Object> l = new LinkedList();
        for (int i = 0; i < rsm.getColumnCount(); i++) {
          l.add(rs.getObject(i + 1));
        }
        listado.add(l);
      } 
      return listado;
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Ha sucedido un error : [" + ex.getMessage() + "]");
      return listado;
    } 
  }
  
  public List<List<Object>> listResult(String sql) {
    List<List<Object>> listado = new LinkedList<>();
    try {
      PreparedStatement stm = this.connection.prepareStatement(sql);
      ResultSet rs = stm.executeQuery();
      ResultSetMetaData rsm = rs.getMetaData();
      while (rs.next()) {
        List<Object> l = new LinkedList<>();
        for (int i = 0; i < rsm.getColumnCount(); i++) {
          l.add(rs.getObject(i + 1));
        }
        listado.add(l);
      } 
      return listado;
    } catch (SQLException ex) {
      System.err.println("HA SUCEDIDO UN ERROR " + ex.getMessage());
      return listado;
    } 
  }
  
  public List<List<Object>> listResult(String sql, int limite) {
    List<List<Object>> listado = new LinkedList<>();
    try {
      PreparedStatement stm = this.connection.prepareStatement(sql);
      stm.setMaxRows(limite);
      ResultSet rs = stm.executeQuery();
      ResultSetMetaData rsm = rs.getMetaData();
      while (rs.next()) {
        List<Object> l = new LinkedList<>();
        for (int i = 0; i < rsm.getColumnCount(); i++) {
          l.add(rs.getObject(i + 1));
        }
        listado.add(l);
      } 
      return listado;
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Ha sucedido un error : [" + ex.getMessage() + "]");
      return listado;
    } 
  }
  
  public List<List<Object>> listResult(String sql, int inicio, int limite) {
    List<List<Object>> listado = new LinkedList<>();
    try {
      PreparedStatement stm = this.connection.prepareStatement(sql);
      stm.setFetchSize(inicio);
      stm.setMaxRows(limite);
      ResultSet rs = stm.executeQuery();
      ResultSetMetaData rsm = rs.getMetaData();
      while (rs.next()) {
        List<Object> l = new LinkedList<>();
        for (int i = 0; i < rsm.getColumnCount(); i++) {
          l.add(rs.getObject(i + 1));
        }
        listado.add(l);
      } 
      return listado;
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Ha sucedido un error : [" + ex.getMessage() + " " + ex.getSQLState() + " " + ex.getErrorCode() + " " + ex.getCause().getMessage() + "]");
      return listado;
    } 
  }
  
  public boolean execute(String sql, Object... params) throws SQLException {
    PreparedStatement stm = this.connection.prepareStatement(sql);
    if (params != null) {
      int contParam = 1;
      for (Object p : params) {
        stm.setObject(contParam, p);
        contParam++;
      } 
    } 
    return stm.execute();
  }
  
  public boolean execute(String sql) {
    try {
      PreparedStatement stm = this.connection.prepareStatement(sql);
      return stm.execute();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Ha sucedido un error : [" + ex.getMessage() + " " + ex.getSQLState() + " " + ex.getErrorCode() + " " + ex.getCause().getMessage() + "]");
      return false;
    } 
  }
  
  public ResultSet resultSQL(String sql) {
    try {
      Statement stm = this.connection.createStatement();
      return stm.executeQuery(sql);
    } catch (SQLException ex) {
      Logger.getLogger(ConexionSql.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } 
  }
  
  public void beginTransaction() throws SQLException {
    this.connection.setAutoCommit(false);
  }
  
  public void commitTransaction() throws SQLException {
    this.connection.commit();
    this.connection.setAutoCommit(true);
  }
  
  public void rollbackTransaction() throws SQLException {
    this.connection.rollback();
    this.connection.setAutoCommit(true);
  }
}