package GUI;

import Control.Receta_Control;
import Modelo.Receta_Datos;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Consultar_Receta extends JFrame {

    //Declaración de variables
    JLabel titulo, lclave;
    JTextField tclave;
    JButton consultar;

    //Propiedades de la ventana
    public Consultar_Receta(){
        setTitle("Consultar receta");
        setSize(480, 240);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        componentes();
        setVisible(true);
    }

    //Creación de objetos
    public void componentes(){
        titulo = new JLabel("Consultar receta");
        titulo.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 18));
        titulo.setBounds(40, 25, 300, 30);

        lclave = new JLabel("Clave de la receta:");
        lclave.setBounds(40, 85, 250, 22);

        tclave = new JTextField();
        tclave.setBounds(40, 110, 230, 30);

        consultar = new JButton("Consultar");
        consultar.setBounds(285, 110, 125, 30);
        consultar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                buscar();
            }
        });

        //Agregar los objetos
        add(titulo);
        add(lclave);
        add(tclave);
        add(consultar);
    }

    //Busca la receta y abre su detalle si existe
    public void buscar(){
        String clave = tclave.getText().trim();
        if(clave.isEmpty()){
            JOptionPane.showMessageDialog(this, "Ingresa una clave para consultar.", "Dato requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Receta_Datos r = Receta_Control.buscar(clave);
        if(r == null){
            JOptionPane.showMessageDialog(this, "No existe ninguna receta con esa clave.", "No encontrada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Detalle_Receta ventana = new Detalle_Receta(r);
    }
}
