package facturadesktopecuadoryomar;

import com.ecuasis.coresri.servicios.ServiciosSRI;
import facturadesktopecuadoryomar.hilos.ProcesoSRI;
import facturadesktopecuadoryomar.logica.EmpresaFacade;
import facturadesktopecuadoryomar.modelo.servidor.Configuracion;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcesoSri {
  public static void main(String[] args) {
    EmpresaFacade empresaFacade = new EmpresaFacade();
    ServiciosSRI sri = new ServiciosSRI();
    
    long contadorProcesos = 0;
    while (true) {
      for (Empresa emp : empresaFacade.listaEmpresas()) {
        if (emp.getEstadoServicioEmpr()) {
          Configuracion conf = emp.getIdConfiguracion();
          if (conf.getAmbienteConf().equals("1")) {
            
            sri.setHost(conf.getHostWsPrueba());
            sri.setWsRecepcion(conf.getUrlWsEnvioOfflinePrueba());
            sri.setWsAutorizacion(conf.getUrlWsRecepcionOfflinePrueba());
            sri.setMetodo("POST");
          } else {
            
            sri.setHost(conf.getHostWsProduccion());
            sri.setWsRecepcion(conf.getUrlWsEnvioOffline());
            sri.setWsAutorizacion(conf.getUrlWsRecepcionOffline());
            sri.setMetodo("POST");
          } 
          ProcesoSRI psri = new ProcesoSRI(sri);
          System.out.println("EL PROCESO SE HA EJECUTADO " + contadorProcesos + " VECES");
          System.out.println("EL PROCESO DE ENVIO DE DOC AL SRI SE HA EJECUTADO ");
          psri.enviarDocumentosSRI(emp);
          System.out.println("FIN DE ENVIO AL SRI ");
        } 
      } 
      try {
        Thread.sleep(60000 * 3);
      } catch (InterruptedException ex) {
        Logger.getLogger(ProcesoSri.class.getName()).log(Level.SEVERE, null, ex);
      } 
      for (Empresa emp : empresaFacade.listaEmpresas()) {
        if (emp.getEstadoServicioEmpr()) {
          Configuracion conf = emp.getIdConfiguracion();
          if (conf.getAmbienteConf().equals("1")) {
            
            sri.setHost(conf.getHostWsPrueba());
            sri.setWsRecepcion(conf.getUrlWsEnvioOfflinePrueba());
            sri.setWsAutorizacion(conf.getUrlWsRecepcionOfflinePrueba());
            sri.setMetodo("POST");
          } else {
            
            sri.setHost(conf.getHostWsProduccion());
            sri.setWsRecepcion(conf.getUrlWsEnvioOffline());
            sri.setWsAutorizacion(conf.getUrlWsRecepcionOffline());
            sri.setMetodo("POST");
          } 
          ProcesoSRI psri = new ProcesoSRI(sri);
          System.out.println("EL PROCESO DE RECEPCION AUTORIZACION DE DOCUMENTOS");
          psri.recibirDocumentosSRI(emp);
          System.out.println("FIN DE RECEPCION AL SRI ");
        } 
      } 
      
      contadorProcesos++;
    } 
  }
}