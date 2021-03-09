/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturadesktopecuadoryomar;

import facturadesktopecuadoryomar.hilos.ProcesoGeneracionDocumentos;
import facturadesktopecuadoryomar.logica.EmpresaFacade;
import facturadesktopecuadoryomar.modelo.servidor.Empresa;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sistemas
 */
public class ProcesoFacturacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("INICIANDO PROCESO DE DOCUMENTOS");
        EmpresaFacade empresaFacade = new EmpresaFacade();
        ProcesoGeneracionDocumentos pgd = new ProcesoGeneracionDocumentos();
        int contadorProcesos = 0;
        while (true) {
            for (Empresa emp : empresaFacade.listaEmpresas()) {
                if (emp.getEstadoServicioEmpr()) {
                    System.out.println(
                            "EL PROCESO SE HA EJECUTADO " + contadorProcesos 
                            + " VECES"
                    );
                    pgd.generarDocumentosCliente(emp);
                    pgd.firmarDocumentos(emp);
                    pgd.enviarDocumentosAutorizadosCliente();
                    pgd.enviarCorreos(emp);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProcesoFacturacion.class.getName())
                              .log(Level.SEVERE, null, ex);
                    }
                }
            }

            contadorProcesos++;
            try {
                Thread.sleep(60000 * 2);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProcesoFacturacion.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
