package Archivos;

import Modelo.Receta_Datos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Archivo_Recetas {

    public static final String RUTA = "recursos/datos/recetas.txt";
    private static final String SEP = "~";
    private static final String SALTO = "@@";

    //Lee todas las recetas guardadas en el archivo de texto
    public static ArrayList<Receta_Datos> cargar(){
        ArrayList<Receta_Datos> recetas = new ArrayList<>();
        File archivo = new File(RUTA);
        if(!archivo.exists()){
            return recetas;
        }
        try{
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();
            while(linea != null){
                if(!linea.trim().isEmpty()){
                    String[] campos = linea.split(SEP, -1);
                    Receta_Datos r = new Receta_Datos();
                    r.setClave(campos[0]);
                    r.setNombre(campos[1]);
                    r.setCategoria(campos[2]);
                    r.setIngredientes(campos[3].replace(SALTO, "\n"));
                    r.setPreparacion(campos[4].replace(SALTO, "\n"));
                    r.setTiempoCoccion(Integer.parseInt(campos[5]));
                    r.setPorciones(Integer.parseInt(campos[6]));
                    r.setRutaImagen(campos[7]);
                    recetas.add(r);
                }
                linea = lector.readLine();
            }
            lector.close();
        }catch(IOException e){
            mostrarError("No se pudo leer el archivo de recetas: " + e.getMessage());
        }
        return recetas;
    }

    //Guarda toda la lista de recetas en el archivo de texto (sobreescribe)
    public static void guardar(ArrayList<Receta_Datos> recetas){
        File archivo = new File(RUTA);
        File carpeta = archivo.getParentFile();
        if(carpeta != null && !carpeta.exists()){
            carpeta.mkdirs();
        }
        try{
            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo));
            for(Receta_Datos r : recetas){
                String linea = r.getClave() + SEP + r.getNombre() + SEP + r.getCategoria() + SEP
                        + r.getIngredientes().replace("\n", SALTO) + SEP
                        + r.getPreparacion().replace("\n", SALTO) + SEP
                        + r.getTiempoCoccion() + SEP + r.getPorciones() + SEP + r.getRutaImagen();
                escritor.write(linea);
                escritor.newLine();
            }
            escritor.close();
        }catch(IOException e){
            mostrarError("No se pudo guardar el archivo de recetas: " + e.getMessage());
        }
    }

    //Muestra un mensaje emergente simple cuando falla la lectura/escritura
    private static void mostrarError(String mensaje){
        javax.swing.JOptionPane.showMessageDialog(null, mensaje, "Error de archivo", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
