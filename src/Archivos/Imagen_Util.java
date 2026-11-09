package Archivos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;

public class Imagen_Util {

    public static final String CARPETA_DESTINO = "recursos/imagenes";
    private static final int ANCHO_MINIMO = 200;
    private static final int ALTO_MINIMO = 200;

    //Revisa que la imagen elegida tenga al menos 200x200 px, que es lo que
    //pide el enunciado ("las imagenes (resolucion)")
    public static boolean resolucionValida(File imagen){
        try{
            BufferedImage img = ImageIO.read(imagen);
            if(img == null){
                return false;
            }
            return img.getWidth() >= ANCHO_MINIMO && img.getHeight() >= ALTO_MINIMO;
        }catch(IOException e){
            return false;
        }
    }

    //Copia la imagen elegida por el usuario a la carpeta de imagenes de la
    //app (para no depender de donde haya quedado el archivo original) y
    //devuelve la ruta nueva, o null si algo sale mal
    public static String copiar(File origen, String clave){
        new File(CARPETA_DESTINO).mkdirs();
        String nombre = origen.getName();
        String extension = nombre.substring(nombre.lastIndexOf('.') + 1).toLowerCase();
        File destino = new File(CARPETA_DESTINO, clave + "." + extension);
        try{
            Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destino.getPath();
        }catch(IOException e){
            return null;
        }
    }
}
