package GUI;

import Control.Receta_Control;
import Modelo.Receta_Datos;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Eliminar_Receta extends JFrame {

    //Declaración de variables
    JLabel titulo, lclave, resultado;
    JTextField tclave;
    JButton buscar, eliminar;

    Receta_Datos recetaEncontrada;

    //Propiedades de la ventana
    public Eliminar_Receta(){
        setTitle("Eliminar receta");
        setSize(470, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        componentes();
        setVisible(true);
    }

    //Creación de objetos
    public void componentes(){
        titulo = new JLabel("Eliminar receta");
        titulo.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 18));
        titulo.setBounds(160, 25, 300, 30);

        lclave = new JLabel("Clave de la receta:");
        lclave.setBounds(40, 85, 250, 22);

        tclave = new JTextField();
        tclave.setBounds(40, 110, 230, 30);

        buscar = new JButton("Buscar");
        buscar.setBounds(285, 110, 125, 30);
        buscar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                buscarReceta();
            }
        });

        eliminar = new JButton("Eliminar");
        eliminar.setBounds(40, 165, 370, 34);
        eliminar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                eliminarReceta();
            }
        });

        resultado = new JLabel(" ");
        resultado.setBounds(40, 215, 410, 40);

        //Agregar los objetos
        add(titulo);
        add(lclave);
        add(tclave);
        add(buscar);
        add(eliminar);
        add(resultado);
    }

    //Busca la receta y muestra un resumen antes de eliminar
    public void buscarReceta(){
        String clave = tclave.getText().trim();
        recetaEncontrada = Receta_Control.buscar(clave);
        if(recetaEncontrada == null){
            resultado.setText("No existe ninguna receta con esa clave.");
            return;
        }
        resultado.setText("Encontrada: " + recetaEncontrada.getNombre() + " (" + recetaEncontrada.getCategoria() + ")");
    }

    //Pide confirmación y elimina la receta encontrada
    public void eliminarReceta(){
        if(recetaEncontrada == null){
            JOptionPane.showMessageDialog(this, "Primero busca una receta valida por su clave.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar la receta '" + recetaEncontrada.getNombre() + "'?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION);
        if(opcion != JOptionPane.YES_OPTION){
            return;
        }
        Receta_Control.eliminar(recetaEncontrada.getClave());
        JOptionPane.showMessageDialog(this, "Receta eliminada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        recetaEncontrada = null;
        tclave.setText("");
        resultado.setText(" ");
    }
}