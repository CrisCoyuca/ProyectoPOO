package Control;

import Archivos.Archivo_Usuarios;
import Modelo.Usuario_Datos;
import java.util.ArrayList;

public class Sesion_Control {

    public static ArrayList<Usuario_Datos> usuarios = new ArrayList<>();
    public static Usuario_Datos usuarioActual;

    static{
        usuarios = Archivo_Usuarios.cargar();
    }

    //Revisa usuario y contraseña; si coinciden guarda el usuario actual y
    //devuelve true, si no devuelve false
    public static boolean iniciarSesion(String usuario, String contrasenia){
        for(Usuario_Datos u : usuarios){
            if(u.getUsuario().equals(usuario) && u.getContrasenia().equals(contrasenia)){
                usuarioActual = u;
                return true;
            }
        }
        return false;
    }

    public static void cerrarSesion(){
        usuarioActual = null;
    }
}
