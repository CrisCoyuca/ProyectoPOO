package Archivos;

import Modelo.Usuario_Datos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Archivo_Usuarios {

    public static final String RUTA = "recursos/datos/usuarios.txt";
    private static final String SEP = "~";

    //Lee la lista de usuarios; si el archivo no existe crea uno por defecto
    public static ArrayList<Usuario_Datos> cargar(){
        File archivo = new File(RUTA);
        if(!archivo.exists()){
            crearPorDefecto(archivo);
        }
        ArrayList<Usuario_Datos> usuarios = new ArrayList<>();
        try{
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();
            while(linea != null){
                if(!linea.trim().isEmpty()){
                    String[] campos = linea.split(SEP, -1);
                    usuarios.add(new Usuario_Datos(campos[0], campos[1], campos[2]));
                }
                linea = lector.readLine();
            }
            lector.close();
        }catch(IOException e){
            javax.swing.JOptionPane.showMessageDialog(null,
                    "No se pudo leer el archivo de usuarios: " + e.getMessage(),
                    "Error de archivo", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        return usuarios;
    }

    private static void crearPorDefecto(File archivo){
        try{
            File carpeta = archivo.getParentFile();
            if(carpeta != null && !carpeta.exists()){
                carpeta.mkdirs();
            }
            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo));
            escritor.write("admin~admin123~ADMIN");
            escritor.newLine();
            escritor.write("invitado~invitado123~NORMAL");
            escritor.newLine();
            escritor.close();
        }catch(IOException e){
            javax.swing.JOptionPane.showMessageDialog(null,
                    "No se pudo crear el archivo de usuarios: " + e.getMessage(),
                    "Error de archivo", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
