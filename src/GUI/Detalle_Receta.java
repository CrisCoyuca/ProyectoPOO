package GUI;

import Archivos.Generador_PDF;
import Modelo.Receta_Datos;
import javax.swing.*;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Detalle_Receta extends JFrame {

    //Declaración de variables
    JLabel nombre, info, clave, foto, lingredientes, lpreparacion;
    JTextArea tingredientes, tpreparacion;
    JScrollPane scrollIngredientes, scrollPreparacion;
    JButton descargar;

    Receta_Datos receta;

    //Propiedades de la ventana
    public Detalle_Receta(Receta_Datos receta){
        this.receta = receta;
        setTitle("Detalle de la receta");
        setSize(757, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        componentes();
        setVisible(true);
    }

    //Creación de objetos
    public void componentes(){
        nombre = new JLabel(receta.getNombre(), SwingConstants.CENTER);
        nombre.setFont(new Font("SansSerif", Font.BOLD, 20));
        nombre.setBounds(0, 15, 741, 30);

        info = new JLabel(receta.getCategoria() + "   |   " + receta.getTiempoCoccion()
                + " min   |   " + receta.getPorciones() + " porciones", SwingConstants.RIGHT);
        info.setBounds(500, 65, 220, 22);

        clave = new JLabel("Clave: " + receta.getClave());
        clave.setBounds(430, 65, 70, 22);

        foto = new JLabel("Sin imagen", SwingConstants.CENTER);
        foto.setBounds(430, 90, 290, 230);
        foto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        String rutaImagen = receta.getRutaImagen();
        if(rutaImagen != null && !rutaImagen.isEmpty() && new File(rutaImagen).exists()){
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imgEscalada = icono.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH);
            foto.setIcon(new ImageIcon(imgEscalada));
            foto.setText(null);
        }

        lingredientes = new JLabel("Ingredientes:");
        lingredientes.setBounds(30, 65, 200, 22);

        tingredientes = new JTextArea(receta.getIngredientes());
        tingredientes.setEditable(false);
        tingredientes.setLineWrap(true);
        tingredientes.setWrapStyleWord(true);
        scrollIngredientes = new JScrollPane(tingredientes);
        scrollIngredientes.setBounds(30, 90, 370, 170);

        lpreparacion = new JLabel("Modo de preparacion:");
        lpreparacion.setBounds(30, 272, 250, 22);

        tpreparacion = new JTextArea(receta.getPreparacion());
        tpreparacion.setEditable(false);
        tpreparacion.setLineWrap(true);
        tpreparacion.setWrapStyleWord(true);
        scrollPreparacion = new JScrollPane(tpreparacion);
        scrollPreparacion.setBounds(30, 297, 370, 190);

        descargar = new JButton("Descargar PDF");
        descargar.setBounds(505, 335, 140, 34);
        descargar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                descargarPdf();
            }
        });

        //Agregar los objetos
        add(nombre);
        add(info);
        add(clave);
        add(foto);
        add(lingredientes);
        add(scrollIngredientes);
        add(lpreparacion);
        add(scrollPreparacion);
        add(descargar);
    }

    //Genera el PDF en un archivo elegido por el usuario
    public void descargarPdf(){
        JFileChooser selector = new JFileChooser();
        selector.setSelectedFile(new File(receta.getClave() + ".pdf"));
        int resultado = selector.showSaveDialog(this);
        if(resultado != JFileChooser.APPROVE_OPTION){
            return;
        }
        File destino = selector.getSelectedFile();
        try{
            Generador_PDF.generar(receta, destino);
            JOptionPane.showMessageDialog(this, "PDF generado correctamente en:\n" + destino.getPath(), "Descarga completa", JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "No se pudo generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Genera un PDF temporal y lo manda a imprimir (o lo abre si no hay impresora configurada)
    public void imprimirReceta(){
        try{
            File temporal = File.createTempFile("receta_" + receta.getClave() + "_", ".pdf");
            temporal.deleteOnExit();
            Generador_PDF.generar(receta, temporal);
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.PRINT)){
                Desktop.getDesktop().print(temporal);
            }else{
                Desktop.getDesktop().open(temporal);
                JOptionPane.showMessageDialog(this, "Se abrio el PDF para que lo imprimas desde el visor.", "Imprimir", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "No se pudo imprimir la receta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}