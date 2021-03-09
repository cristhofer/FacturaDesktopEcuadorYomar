package facturadesktopecuadoryomar.vistas;
import facturadesktopecuadoryomar.logica.UsuarioFacade;
import facturadesktopecuadoryomar.modelo.servidor.Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LoginView extends JFrame {
  private int x, y; 
  private UsuarioFacade usuarioFacade; 
  private Usuario usuario; 
  
  public LoginView() {
    initComponents();
    this.usuarioFacade = new UsuarioFacade();
  }
  
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    this.txtContrasena = new JPasswordField();
    this.lbBotonCerrar = new JLabel();
    this.lbEcuasis = new JLabel();
    this.btLogin = new JButton();
    this.txtUsuario = new JTextField();
    this.lbFacturacion = new JLabel();
    this.lbLogoAguapen = new JLabel();
    this.lbFondo = new JLabel();
    
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setUndecorated(true);
    addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent evt) {
            formMouseDragged(evt);
          }
        });
    addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent evt) {
            formMousePressed(evt);
          }
        });
    getContentPane().setLayout(null);
    getContentPane().add(this.txtContrasena);
    this.txtContrasena.setBounds(80, 320, 210, 30);
    
    this.lbBotonCerrar.setIcon(new ImageIcon(getClass().getResource("/facturadesktopecuadoryomar/recursos/imagenes/boton_cerrar_new.png")));
    this.lbBotonCerrar.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
    this.lbBotonCerrar.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            lbBotonCerrarMouseClicked(evt);
          }
          public void mouseEntered(MouseEvent evt) {
            lbBotonCerrarMouseEntered(evt);
          }
          public void mouseExited(MouseEvent evt) {
            lbBotonCerrarMouseExited(evt);
          }
          public void mouseReleased(MouseEvent evt) {
            lbBotonCerrarMouseReleased(evt);
          }
        });
    getContentPane().add(this.lbBotonCerrar);
    this.lbBotonCerrar.setBounds(310, 0, 50, 50);
    
    this.lbEcuasis.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
    this.lbEcuasis.setForeground(new Color(255, 255, 255));
    getContentPane().add(this.lbEcuasis);
    this.lbEcuasis.setBounds(10, 520, 350, 0);
    
    this.btLogin.setBackground(new Color(65, 102, 241));
    this.btLogin.setForeground(new Color(255, 255, 255));
    this.btLogin.setText("INGRESAR");
    this.btLogin.setBorder(null);
    this.btLogin.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
    this.btLogin.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            btLoginActionPerformed(evt);
          }
        });
    getContentPane().add(this.btLogin);
    this.btLogin.setBounds(80, 370, 210, 40);
    
    this.txtUsuario.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            txtUsuarioActionPerformed(evt);
          }
        });
    getContentPane().add(this.txtUsuario);
    this.txtUsuario.setBounds(80, 270, 210, 30);
    
    this.lbFacturacion.setIcon(new ImageIcon(getClass().getResource("/facturadesktopecuadoryomar/recursos/imagenes/facturacion_elec.png")));
    getContentPane().add(this.lbFacturacion);
    this.lbFacturacion.setBounds(80, 150, 190, 90);
    
    this.lbLogoAguapen.setIcon(new ImageIcon(getClass().getResource("/facturadesktopecuadoryomar/recursos/imagenes/Logo_aguapen.png")));
    getContentPane().add(this.lbLogoAguapen);
    this.lbLogoAguapen.setBounds(30, 10, 280, 150);
    
    this.lbFondo.setIcon(new ImageIcon(getClass().getResource("/facturadesktopecuadoryomar/recursos/imagenes/Fondo.png")));
    getContentPane().add(this.lbFondo);
    this.lbFondo.setBounds(0, 0, 370, 550);
    
    pack();
  }
  
  private void txtUsuarioActionPerformed(ActionEvent evt) {}
  
  private void lbBotonCerrarMouseEntered(MouseEvent evt) {
    this.lbBotonCerrar.setIcon(new ImageIcon(getClass().getResource("/facturadesktopecuadoryomar/recursos/imagenes/boton_cerrar_sel_new.png")));
  }
  
  private void lbBotonCerrarMouseReleased(MouseEvent evt) {
    this.lbBotonCerrar.setIcon(new ImageIcon(getClass().getResource("/facturadesktopecuadoryomar/recursos/imagenes/boton_cerrar_new.png")));
  }
  
  private void lbBotonCerrarMouseClicked(MouseEvent evt) {
    System.exit(0);
  }
  
  private void lbBotonCerrarMouseExited(MouseEvent evt) {
    this.lbBotonCerrar.setIcon(new ImageIcon(getClass().getResource("/facturadesktopecuadoryomar/recursos/imagenes/boton_cerrar_new.png")));
  }
  
  private void formMousePressed(MouseEvent evt) {
    this.x = evt.getX();
    this.y = evt.getY();
  }
  
  private void formMouseDragged(MouseEvent evt) {
    Point p = MouseInfo.getPointerInfo().getLocation();
    setLocation(p.x - this.x, p.y - this.y);
  }
  
  private void btLoginActionPerformed(ActionEvent evt) {
    this.usuario = this.usuarioFacade.login(this.txtUsuario.getText(), this.txtContrasena.getText());
    if (this.usuario != null) {
      InicioView inicioView = new InicioView(this.usuario);
      inicioView.setSize(1030, 710);
      inicioView.setLocationRelativeTo(null);
      inicioView.setVisible(true);
      setVisible(false);
    } else {
      JOptionPane.showMessageDialog(this.rootPane, "Su usuario o contraseña son incorrectos", "Login", JOptionPane.ERROR_MESSAGE);
    } 
  }
  
  public static void main(String[] args) {
    try {
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        } 
      } 
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
    } 
    
    java.awt.EventQueue.invokeLater(new Runnable() {
          public void run() {
            (new LoginView()).setVisible(true);
          }
        });
  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private JButton btLogin;
  private JLabel lbBotonCerrar;
  private JLabel lbEcuasis;
  private JLabel lbFacturacion;
  private JLabel lbFondo;
  private JLabel lbLogoAguapen;
  private JPasswordField txtContrasena;
  private JTextField txtUsuario;
  // End of variables declaration//GEN-END:variables
}