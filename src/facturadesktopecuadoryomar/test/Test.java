package facturadesktopecuadoryomar.test;

import com.ecuasis.coresri.excepciones.ClaveAccesoException;
import facturadesktopecuadoryomar.logica.DocumentoProveedorFacade;
import facturadesktopecuadoryomar.logica.DocumentoRecibidoFacade;
import facturadesktopecuadoryomar.modelo.servidor.DocumentosRecibidos;
import java.sql.SQLException;
import javax.xml.xpath.XPathExpressionException;

public class Test
{
  public static boolean creacion = false;
  public static boolean actualizacion = false;
  public static boolean envioSRI = false;
  public static boolean recepcionSRI = true;
  public static boolean correo = false;
  
  public static void main(String[] args) throws ClaveAccesoException, SQLException, XPathExpressionException {
    DocumentoRecibidoFacade dco = new DocumentoRecibidoFacade();
    DocumentosRecibidos dr = dco.getDocumentosPendientes(1).get(0);
    DocumentoProveedorFacade proveedorFacade = new DocumentoProveedorFacade();
  }
}