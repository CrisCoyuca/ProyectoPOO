package Modelo;

public class Usuario_Datos {

    private String usuario;
    private String contrasenia;
    private String tipo; 

    public Usuario_Datos(){
    }

    public Usuario_Datos(String usuario, String contrasenia, String tipo){
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
    }

    public String getUsuario(){
        return usuario;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public String getContrasenia(){
        return contrasenia;
    }

    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public boolean esAdmin(){
        return tipo.equals("ADMIN");
    }
}
