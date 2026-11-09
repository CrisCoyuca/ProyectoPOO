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

public class Modificar_Receta extends JFrame {

    //Declaración de variables
    JLabel lbuscar, lmin, lclave, lcategoria, lnombre, ltiempo, lporciones, lingredientes, lpreparacion, foto;
    JTextField tbuscar, tclave, tnombre, ttiempo, tporciones;
    JButton buscar, guardar;
    JComboBox<String> ccategoria;
    JTextArea tingredientes, tpreparacion;
    JScrollPane scrollIngredientes, scrollPreparacion;
    JFileChooser fileChooser;

    File imagenSeleccionada;
    String claveOriginal;
    String rutaImagenActual;

    //Propiedades de la ventana
    public Modificar_Receta(){
        setTitle("Modificar receta");
        setSize(1000, 770);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        componentes();
        habilitarFormulario(false);
        setVisible(true);
    }

    //Creación de objetos
    public void componentes(){
        lbuscar = new JLabel("Clave a modificar:");
        lbuscar.setBounds(30, 28, 155, 32);

        tbuscar = new JTextField();
        tbuscar.setBounds(190, 28, 140, 32);

        buscar = new JButton("Buscar");
        buscar.setBounds(345, 28, 100, 32);
        buscar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                buscarReceta();
            }
        });

        lclave = new JLabel("Clave:");
        lclave.setBounds(30, 85, 130, 30);

        tclave = new JTextField();
        tclave.setBounds(170, 85, 220, 32);

        lcategoria = new JLabel("Categoria:");
        lcategoria.setBounds(30, 130, 130, 30);

        ccategoria = new JComboBox<>(Categorias_Comida.LISTA);
        ccategoria.setBounds(170, 130, 220, 32);

        lnombre = new JLabel("Nombre:");
        lnombre.setBounds(30, 175, 130, 30);

        tnombre = new JTextField();
        tnombre.setBounds(170, 175, 300, 32);

        ltiempo = new JLabel("Tiempo coccion:");
        ltiempo.setBounds(30, 220, 130, 30);

        ttiempo = new JTextField();
        ttiempo.setBounds(170, 220, 100, 32);

        lmin = new JLabel("min");
        lmin.setBounds(280, 220, 40, 30);

        lporciones = new JLabel("Porciones:");
        lporciones.setBounds(30, 265, 130, 30);

        tporciones = new JTextField();
        tporciones.setBounds(170, 265, 100, 32);

        lingredientes = new JLabel("Ingredientes (uno por linea):");
        lingredientes.setBounds(30, 317, 300, 22);

        tingredientes = new JTextArea();
        tingredientes.setLineWrap(true);
        tingredientes.setWrapStyleWord(true);
        scrollIngredientes = new JScrollPane(tingredientes);
        scrollIngredientes.setBounds(30, 343, 440, 150);

        lpreparacion = new JLabel("Modo de preparacion (un paso por linea):");
        lpreparacion.setBounds(30, 507, 380, 22);

        tpreparacion = new JTextArea();
        tpreparacion.setLineWrap(true);
        tpreparacion.setWrapStyleWord(true);
        scrollPreparacion = new JScrollPane(tpreparacion);
        scrollPreparacion.setBounds(30, 533, 440, 160);

        foto = new JLabel("Foto", SwingConstants.CENTER);
        foto.setBounds(500, 85, 450, 230);
        foto.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Selector de archivos embebido (JFileChooser), igual que en Practica3
        fileChooser = new JFileChooser();
        fileChooser.setBounds(500, 330, 450, 300);

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
                }
            }
        });

        guardar = new JButton("Guardar cambios");
        guardar.setBounds(770, 650, 180, 40);
        guardar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                guardarCambios();
            }
        });

        //Agregar los objetos
        add(lbuscar);
        add(tbuscar);
        add(buscar);
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
        add(guardar);
    }

    //Busca la receta por clave y llena el formulario con sus datos
    public void buscarReceta(){
        String clave = tbuscar.getText().trim();
        Receta_Datos r = Receta_Control.buscar(clave);
        if(r == null){
            JOptionPane.showMessageDialog(this, "No existe ninguna receta con esa clave.", "No encontrada", JOptionPane.WARNING_MESSAGE);
            habilitarFormulario(false);
            claveOriginal = null;
            return;
        }

        claveOriginal = r.getClave();
        rutaImagenActual = r.getRutaImagen();
        imagenSeleccionada = null;

        tclave.setText(r.getClave());
        ccategoria.setSelectedItem(r.getCategoria());
        tnombre.setText(r.getNombre());
        ttiempo.setText(String.valueOf(r.getTiempoCoccion()));
        tporciones.setText(String.valueOf(r.getPorciones()));
        tingredientes.setText(r.getIngredientes());
        tpreparacion.setText(r.getPreparacion());

        if(rutaImagenActual != null && !rutaImagenActual.isEmpty() && new File(rutaImagenActual).exists()){
            ImageIcon icono = new ImageIcon(rutaImagenActual);
            Image imgEscalada = icono.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH);
            foto.setIcon(new ImageIcon(imgEscalada));
            foto.setText(null);
        }else{
            foto.setIcon(null);
            foto.setText("Foto");
        }

        habilitarFormulario(true);
    }

    //Valida y guarda los cambios sobre la receta encontrada
    public void guardarCambios(){
        if(claveOriginal == null){
            JOptionPane.showMessageDialog(this, "Primero busca una receta valida por su clave.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String claveNueva = tclave.getText().trim();
        String nombre = tnombre.getText().trim();
        String ingredientes = tingredientes.getText().trim();
        String preparacion = tpreparacion.getText().trim();

        if(claveNueva.isEmpty()){
            JOptionPane.showMessageDialog(this, "La clave es obligatoria.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!claveNueva.equalsIgnoreCase(claveOriginal) && Receta_Control.existeClave(claveNueva)){
            JOptionPane.showMessageDialog(this, "Ya existe otra receta con esa clave.", "Clave repetida", JOptionPane.WARNING_MESSAGE);
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

        String rutaImagen = rutaImagenActual;
        if(imagenSeleccionada != null){
            if(!Imagen_Util.resolucionValida(imagenSeleccionada)){
                JOptionPane.showMessageDialog(this, "La imagen debe tener una resolucion minima de 200x200 px.", "Imagen invalida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            rutaImagen = Imagen_Util.copiar(imagenSeleccionada, claveNueva);
            if(rutaImagen == null){
                JOptionPane.showMessageDialog(this, "No se pudo guardar la imagen seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if(rutaImagen == null || rutaImagen.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debes seleccionar una imagen del producto terminado.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String categoria = (String) ccategoria.getSelectedItem();
        Receta_Datos actualizada = new Receta_Datos(claveNueva, nombre, categoria, ingredientes, preparacion, tiempo, porciones, rutaImagen);
        Receta_Control.modificar(claveOriginal, actualizada);

        JOptionPane.showMessageDialog(this, "Receta modificada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    //Activa o desactiva los campos del formulario (hasta que se busque una receta valida)
    public void habilitarFormulario(boolean activo){
        tclave.setEnabled(activo);
        ccategoria.setEnabled(activo);
        tnombre.setEnabled(activo);
        ttiempo.setEnabled(activo);
        tporciones.setEnabled(activo);
        tingredientes.setEnabled(activo);
        tpreparacion.setEnabled(activo);
        fileChooser.setEnabled(activo);
        guardar.setEnabled(activo);
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
