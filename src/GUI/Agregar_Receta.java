package GUI;

import Archivos.Imagen_Util;
import Control.Receta_Control;
import Modelo.Categorias_Comida;
import Modelo.Receta_Datos;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Agregar_Receta extends JFrame {

    //Declaración de variables
    JLabel lmin, lclave, lcategoria, lnombre, ltiempo, lporciones, lingredientes, lpreparacion, foto;
    JTextField tclave, tnombre, ttiempo, tporciones;
    JComboBox<String> ccategoria;
    JTextArea tingredientes, tpreparacion;
    JScrollPane scrollIngredientes, scrollPreparacion;
    JFileChooser fileChooser;
    JButton nuevo, guardar;

    File imagenSeleccionada;

    //Propiedades de la ventana
    public Agregar_Receta(){
        setTitle("Agregar receta");
        setSize(1000, 740);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        componentes();
        setVisible(true);
    }

    //Creación de objetos
    public void componentes(){
        lclave = new JLabel("Clave:");
        lclave.setBounds(30, 30, 130, 30);

        tclave = new JTextField();
        tclave.setBounds(170, 30, 220, 32);

        lcategoria = new JLabel("Categoria:");
        lcategoria.setBounds(30, 75, 130, 30);

        ccategoria = new JComboBox<>(Categorias_Comida.LISTA);
        ccategoria.setBounds(170, 75, 220, 32);

        lnombre = new JLabel("Nombre:");
        lnombre.setBounds(30, 120, 130, 30);

        tnombre = new JTextField();
        tnombre.setBounds(170, 120, 300, 32);

        ltiempo = new JLabel("Tiempo coccion:");
        ltiempo.setBounds(30, 165, 130, 30);

        ttiempo = new JTextField();
        ttiempo.setBounds(170, 165, 100, 32);

        lmin = new JLabel("min");
        lmin.setBounds(280, 165, 40, 30);

        lporciones = new JLabel("Porciones:");
        lporciones.setBounds(30, 210, 130, 30);

        tporciones = new JTextField();
        tporciones.setBounds(170, 210, 100, 32);

        lingredientes = new JLabel("Ingredientes (uno por linea):");
        lingredientes.setBounds(30, 262, 300, 22);

        tingredientes = new JTextArea();
        tingredientes.setLineWrap(true);
        tingredientes.setWrapStyleWord(true);
        scrollIngredientes = new JScrollPane(tingredientes);
        scrollIngredientes.setBounds(30, 288, 440, 150);

        lpreparacion = new JLabel("Modo de preparacion (un paso por linea):");
        lpreparacion.setBounds(30, 452, 380, 22);

        tpreparacion = new JTextArea();
        tpreparacion.setLineWrap(true);
        tpreparacion.setWrapStyleWord(true);
        scrollPreparacion = new JScrollPane(tpreparacion);
        scrollPreparacion.setBounds(30, 478, 440, 160);

        //Espacio para la foto (JLabel usado como marco)
        foto = new JLabel("Foto", SwingConstants.CENTER);
        foto.setBounds(500, 30, 450, 230);
        foto.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Selector de archivos embebido (JFileChooser), igual que en Practica3
        fileChooser = new JFileChooser();
        fileChooser.setBounds(500, 275, 450, 300);

        FileNameExtensionFilter filtroImagenes = new FileNameExtensionFilter(
                "Imagenes (*.jpg, *.png)", "jpg", "jpeg", "png");
        fileChooser.addChoosableFileFilter(filtroImagenes);
        fileChooser.setFileFilter(filtroImagenes);

        ocultarBotonesDeVista(fileChooser);

        fileChooser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){
                    File archivo = fileChooser.getSelectedFile();
                    if(archivo != null){
                        imagenSeleccionada = archivo;
                        ImageIcon icono = new ImageIcon(archivo.getPath());
                        Image imgEscalada = icono.getImage().getScaledInstance(
                                foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH);
                        foto.setIcon(new ImageIcon(imgEscalada));
                        foto.setText(null);
                    }
                }else if(e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)){
                    imagenSeleccionada = null;
                    foto.setIcon(null);
                    foto.setText("Foto");
                }
            }
        });

        nuevo = new JButton("Limpiar");
        nuevo.setBounds(680, 600, 120, 40);
        nuevo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                limpiarFormulario();
            }
        });

        guardar = new JButton("Guardar");
        guardar.setBounds(815, 600, 135, 40);
        guardar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                guardarReceta();
            }
        });

        //Agregar los objetos
        add(lclave);
        add(tclave);
        add(lcategoria);
        add(ccategoria);
        add(lnombre);
        add(tnombre);
        add(ltiempo);
        add(ttiempo);
        add(lmin);
        add(lporciones);
        add(tporciones);
        add(lingredientes);
        add(scrollIngredientes);
        add(lpreparacion);
        add(scrollPreparacion);
        add(foto);
        add(fileChooser);
        add(nuevo);
        add(guardar);
    }

    //Valida los campos y guarda la receta nueva
    public void guardarReceta(){
        String clave = tclave.getText().trim();
        String nombre = tnombre.getText().trim();
        String ingredientes = tingredientes.getText().trim();
        String preparacion = tpreparacion.getText().trim();

        if(clave.isEmpty()){
            JOptionPane.showMessageDialog(this, "La clave es obligatoria.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(Receta_Control.existeClave(clave)){
            JOptionPane.showMessageDialog(this, "Ya existe una receta con esa clave.", "Clave repetida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(nombre.isEmpty()){
            JOptionPane.showMessageDialog(this, "El nombre de la receta es obligatorio.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(ingredientes.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debes ingresar al menos un ingrediente.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(preparacion.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debes ingresar el modo de preparacion.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int tiempo;
        try{
            tiempo = Integer.parseInt(ttiempo.getText().trim());
            if(tiempo <= 0){
                JOptionPane.showMessageDialog(this, "El tiempo de coccion debe ser mayor a cero.", "Dato invalido", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "El tiempo de coccion debe ser un numero.", "Dato invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int porciones;
        try{
            porciones = Integer.parseInt(tporciones.getText().trim());
            if(porciones <= 0){
                JOptionPane.showMessageDialog(this, "Las porciones deben ser mayores a cero.", "Dato invalido", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Las porciones deben ser un numero.", "Dato invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(imagenSeleccionada == null){
            JOptionPane.showMessageDialog(this, "Debes seleccionar una imagen del producto terminado.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!Imagen_Util.resolucionValida(imagenSeleccionada)){
            JOptionPane.showMessageDialog(this, "La imagen debe tener una resolucion minima de 200x200 px.", "Imagen invalida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String rutaImagen = Imagen_Util.copiar(imagenSeleccionada, clave);
        if(rutaImagen == null){
            JOptionPane.showMessageDialog(this, "No se pudo guardar la imagen seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String categoria = (String) ccategoria.getSelectedItem();
        Receta_Datos receta = new Receta_Datos(clave, nombre, categoria, ingredientes, preparacion, tiempo, porciones, rutaImagen);
        Receta_Control.agregar(receta);

        JOptionPane.showMessageDialog(this, "Receta agregada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        limpiarFormulario();
    }

    //Deja el formulario listo para capturar otra receta
    public void limpiarFormulario(){
        tclave.setText("");
        tnombre.setText("");
        ttiempo.setText("");
        tporciones.setText("");
        tingredientes.setText("");
        tpreparacion.setText("");
        ccategoria.setSelectedIndex(0);
        imagenSeleccionada = null;
        foto.setIcon(null);
        foto.setText("Foto");
        fileChooser.setSelectedFile(null);
    }

    //Recorre el árbol interno del JFileChooser y oculta sus botones de vista
    public void ocultarBotonesDeVista(Container contenedor){
        for(Component comp : contenedor.getComponents()){
            if(comp instanceof JToggleButton){
                comp.setVisible(false);
            }else if(comp instanceof Container){
                ocultarBotonesDeVista((Container) comp);
            }
        }
    }
}
