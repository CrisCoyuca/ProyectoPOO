package Control;

import Archivos.Archivo_Recetas;
import Modelo.Receta_Datos;
import java.util.ArrayList;

public class Receta_Control {

    public static ArrayList<Receta_Datos> recetas = new ArrayList<>();

    static{
        recetas = Archivo_Recetas.cargar();
    }

    //Revisa si ya existe una receta con esa clave (ignorando mayusculas)
    public static boolean existeClave(String clave){
        for(Receta_Datos r : recetas){
            if(r.getClave().equalsIgnoreCase(clave)){
                return true;
            }
        }
        return false;
    }

    //Busca una receta por su clave; devuelve null si no la encuentra
    public static Receta_Datos buscar(String clave){
        for(Receta_Datos r : recetas){
            if(r.getClave().equalsIgnoreCase(clave)){
                return r;
            }
        }
        return null;
    }

    //Devuelve solo las recetas de una categoria
    public static ArrayList<Receta_Datos> listarPorCategoria(String categoria){
        ArrayList<Receta_Datos> resultado = new ArrayList<>();
        for(Receta_Datos r : recetas){
            if(r.getCategoria().equalsIgnoreCase(categoria)){
                resultado.add(r);
            }
        }
        return resultado;
    }

    public static void agregar(Receta_Datos r){
        recetas.add(r);
        Archivo_Recetas.guardar(recetas);
    }

    public static void modificar(String claveOriginal, Receta_Datos actualizada){
        Receta_Datos existente = buscar(claveOriginal);
        int indice = recetas.indexOf(existente);
        recetas.set(indice, actualizada);
        Archivo_Recetas.guardar(recetas);
    }

    public static void eliminar(String clave){
        Receta_Datos existente = buscar(clave);
        recetas.remove(existente);
        Archivo_Recetas.guardar(recetas);
    }
}
