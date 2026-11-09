package GUI;

import Control.Sesion_Control;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login_Frame extends JFrame {

    //Declaración de variables
    JLabel usuario, contrasenia, titulo;
    JTextField tusuario;
    JPasswordField pcontrasenia;
    JButton acceder;

    //Propiedades de la ventana
    public Login_Frame(){
        setTitle("Iniciar sesion");
        setSize(400, 330);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        componentes();
        setVisible(true);
    }

    //Creación de objetos
    public void componentes(){
        titulo = new JLabel("Recetario");
        titulo.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 20));
        titulo.setBounds(150, 30, 300, 30);

        usuario = new JLabel("Usuario:");
        usuario.setBounds(40, 85, 200, 22);

        tusuario = new JTextField();
        tusuario.setBounds(110,85, 230, 30);

        contrasenia = new JLabel("Contraseña:");
        contrasenia.setBounds(30, 155, 200, 22);

        pcontrasenia = new JPasswordField();
        pcontrasenia.setBounds(110, 155, 230, 30);

        acceder = new JButton("Acceder");
        acceder.setBounds(155, 220, 100, 32);
        acceder.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                validarAcceso();
            }
        });

      
        //Agregar los objetos
        add(titulo);
        add(usuario);
        add(tusuario);
        add(contrasenia);
        add(pcontrasenia);
        add(acceder);
        //Permite entrar con la tecla Enter
        getRootPane().setDefaultButton(acceder);
    }

    //Revisa usuario/contraseña y abre la ventana que corresponde
    public void validarAcceso(){
        String nombreUsuario = tusuario.getText().trim();
        String clave = new String(pcontrasenia.getPassword());

        if(nombreUsuario.isEmpty() || clave.isEmpty()){
            JOptionPane.showMessageDialog(this, "Ingresa usuario y contraseña.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean acceso = Sesion_Control.iniciarSesion(nombreUsuario, clave);
        if(!acceso){
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose();
        if(Sesion_Control.usuarioActual.esAdmin()){
            Ventana_Administrador ventana = new Ventana_Administrador();
        }else{
            Ventana_Usuario ventana = new Ventana_Usuario();
        }
    }
}
