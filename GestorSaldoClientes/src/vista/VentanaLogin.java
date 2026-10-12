package vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.UsuarioDAO;
import modelo.Usuario;

public class VentanaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private UsuarioDAO usuarioDAO;

    public VentanaLogin() {
    	
        usuarioDAO = new UsuarioDAO();
        crearVentanaLogin();
    }

    private void crearVentanaLogin() {
        setTitle("Inicio de Sesión - Administrador");
        setSize(320, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //让窗口出现在屏幕中央
        JPanel panel = new JPanel(); //容器 （按钮 输入框）
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //从上到下排序
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20)); //四周留空

        JLabel lblUsuario = new JLabel("Usuario (Admin):");
        txtUsuario = new JTextField();
        JLabel lblPass = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");

        btnLogin.addActionListener(e -> {
            String user = txtUsuario.getText();
            String pass = new String(txtContrasena.getPassword());
            Usuario usuarioEncontrado = usuarioDAO.login(user, pass);
            if(usuarioEncontrado != null){
                JOptionPane.showMessageDialog(null, "Bienvenido Administrador");
                this.dispose();
                new VentanaPrincipal().setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }
        });

        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(Box.createVerticalStrut(8)); //ESPACIO
        panel.add(lblPass);
        panel.add(txtContrasena);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnLogin);
        this.add(panel);
    }
}
