package Modelo;

public class Receta_Datos {

    private String clave;
    private String nombre;
    private String categoria;
    private String ingredientes;
    private String preparacion;
    private int tiempoCoccion;
    private int porciones;
    private String rutaImagen;

    public Receta_Datos(){
    }

    public Receta_Datos(String clave, String nombre, String categoria, String ingredientes,
                   String preparacion, int tiempoCoccion, int porciones, String rutaImagen){
        this.clave = clave;
        this.nombre = nombre;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
        this.tiempoCoccion = tiempoCoccion;
        this.porciones = porciones;
        this.rutaImagen = rutaImagen;
    }

    public String getClave(){
        return clave;
    }

    public void setClave(String clave){
        this.clave = clave;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getCategoria(){
        return categoria;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getIngredientes(){
        return ingredientes;
    }

    public void setIngredientes(String ingredientes){
        this.ingredientes = ingredientes;
    }

    public String getPreparacion(){
        return preparacion;
    }

    public void setPreparacion(String preparacion){
        this.preparacion = preparacion;
    }

    public int getTiempoCoccion(){
        return tiempoCoccion;
    }

    public void setTiempoCoccion(int tiempoCoccion){
        this.tiempoCoccion = tiempoCoccion;
    }

    public int getPorciones(){
        return porciones;
    }

    public void setPorciones(int porciones){
        this.porciones = porciones;
    }

    public String getRutaImagen(){
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen){
        this.rutaImagen = rutaImagen;
    }
}
