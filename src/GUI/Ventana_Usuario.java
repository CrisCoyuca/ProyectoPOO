package GUI;

import Control.Receta_Control;
import Control.Sesion_Control;
import Modelo.Categorias_Comida;
import Modelo.Receta_Datos;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Ventana_Usuario extends JFrame {

    //Declaración de variables
    JLabel titulo, lcategoria;
    JComboBox<String> ccategoria;
    JList<String> listaRecetas;
    DefaultListModel<String> modeloLista;
    JScrollPane scrollLista;
    JButton verReceta, cerrarSesion;

    ArrayList<Receta_Datos> recetasMostradas;

    //Propiedades de la ventana
    public Ventana_Usuario(){
        setTitle("Recetario - Catalogo");
        setSize(770, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        componentes();
        cargarCategoria();
        setVisible(true);
    }

    //Creación de objetos
    public void componentes(){
        titulo = new JLabel("Catalogo de recetas");
        titulo.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 20));
        titulo.setBounds(30, 25, 400, 30);

        lcategoria = new JLabel("Categoria:");
        lcategoria.setBounds(30, 80, 100, 25);

        ccategoria = new JComboBox<>(Categorias_Comida.LISTA);
        ccategoria.setBounds(135, 80, 230, 28);
        ccategoria.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cargarCategoria();
            }
        });

        modeloLista = new DefaultListModel<>();
        listaRecetas = new JList<>(modeloLista);
        scrollLista = new JScrollPane(listaRecetas);
        scrollLista.setBounds(30, 125, 570, 380);

        verReceta = new JButton("Ver receta");
        verReceta.setBounds(620, 125, 120, 32);
        verReceta.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                verRecetaSeleccionada();
            }
        });

        cerrarSesion = new JButton("Cerrar sesion");
        cerrarSesion.setBounds(620, 170, 120, 32);
        cerrarSesion.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Sesion_Control.cerrarSesion();
                dispose();
                Login_Frame ventana = new Login_Frame();
            }
        });

        //Agregar los objetos
        add(titulo);
        add(lcategoria);
        add(ccategoria);
        add(scrollLista);
        add(verReceta);
        add(cerrarSesion);
    }

    //Llena la lista con las recetas de la categoria elegida en el combo
    public void cargarCategoria(){
        String categoria = (String) ccategoria.getSelectedItem();
        recetasMostradas = Receta_Control.listarPorCategoria(categoria);
        modeloLista.clear();
        for(Receta_Datos r : recetasMostradas){
            modeloLista.addElement(r.getNombre() + "  (" + r.getTiempoCoccion() + " min, " + r.getPorciones() + " porciones)");
        }
    }

    //Abre el detalle de la receta seleccionada en la lista
    public void verRecetaSeleccionada(){
        int indice = listaRecetas.getSelectedIndex();
        if(indice == -1){
            JOptionPane.showMessageDialog(this, "Selecciona una receta de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Receta_Datos r = recetasMostradas.get(indice);
        Detalle_Receta ventana = new Detalle_Receta(r);
    }
}
