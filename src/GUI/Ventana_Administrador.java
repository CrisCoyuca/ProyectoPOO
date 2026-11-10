package GUI;

import Control.Receta_Control;
import Control.Sesion_Control;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana_Administrador extends JFrame {

    //Declaración de variables
    JMenuBar menuBar;
    JMenu archivo, editar;
    JMenuItem agregar, consultar, eliminar;
    JMenuItem modificar;
    JMenuItem salir;
    JLabel titulo, info;

    //Propiedades de la ventana
    public Ventana_Administrador(){
        setTitle("Recetario - Administrador");
        setSize(760, 540);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        componentes();
        setVisible(true);
    }

    //Creación de objetos
    public void componentes(){
        titulo = new JLabel("Panel de administrador");
        titulo.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 22));
        titulo.setBounds(40, 40, 400, 30);
        add(titulo);

        info = new JLabel("Recetas registradas: " + Receta_Control.recetas.size());
        info.setBounds(40, 90, 300, 30);
        add(info);

        //Menu Archivo
        agregar = new JMenuItem("Agregar receta");
        agregar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Agregar_Receta ventana = new Agregar_Receta();
            }
        });

        consultar = new JMenuItem("Consultar receta");
        consultar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Consultar_Receta ventana = new Consultar_Receta();
            }
        });

        eliminar = new JMenuItem("Eliminar receta");
        eliminar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Eliminar_Receta ventana = new Eliminar_Receta();
            }
        });

        archivo = new JMenu("Archivo");
        archivo.add(agregar);
        archivo.add(consultar);
        archivo.add(eliminar);

        //Menu Editar
        modificar = new JMenuItem("Modificar receta");
        modificar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Modificar_Receta ventana = new Modificar_Receta();
            }
        });

        editar = new JMenu("Editar");
        editar.add(modificar);

        //Menu Salir
        salir = new JMenuItem("Cerrar sesion");
        salir.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Desea cerrar la sesion?",
                        "Cerrar sesion",
                        JOptionPane.YES_NO_OPTION);
                if(opcion == JOptionPane.YES_OPTION){
                    Sesion_Control.cerrarSesion();
                    dispose();
                    Login_Frame ventana = new Login_Frame();
                }
            }
        });

        //Barra de menú
        menuBar = new JMenuBar();
        menuBar.add(archivo);
        menuBar.add(editar);
        menuBar.add(salir);

        setJMenuBar(menuBar);

    }
}