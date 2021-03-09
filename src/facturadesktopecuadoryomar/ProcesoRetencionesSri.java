package facturadesktopecuadoryomar;

import facturadesktopecuadoryomar.hilos.ProcesoDocumentosCliente;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcesoRetencionesSri {
    public static void main(String[] args) {
        System.out.println("INICIANDO PROCESO DOCUMENTOS RECIBIDOS");
        ProcesoDocumentosCliente pdo = new ProcesoDocumentosCliente();
        int contadorProcesos = 0;

        while (true) {
            System.out.println("EL PROCESO SE HA EJECUTADO " + contadorProcesos + " VECES");
            pdo.actualizarDocumentosProveedor();
            try {
                Thread.sleep(60000 * 3);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProcesoRetencionesSri.class.getName()).log(Level.SEVERE, null, ex);
            }
            contadorProcesos++;
        } 
    }
}