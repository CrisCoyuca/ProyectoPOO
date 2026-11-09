package Archivos;

import Modelo.Receta_Datos;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Generador_PDF {

    private static final float ANCHO_PAGINA = 595f;
    private static final float ALTO_PAGINA = 842f;
    private static final float MARGEN = 50f;
    private static final float INTERLINEA = 16f;
    private static final int LINEAS_POR_PAGINA = 44;

    //Genera un PDF de una sola columna con todos los datos de la receta.
    //No usa ninguna libreria externa: arma el archivo escribiendo
    //directamente el texto del formato PDF.
    public static void generar(Receta_Datos r, File destino) throws IOException{
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add(r.getNombre());
        lineas.add("");
        lineas.add("Clave: " + r.getClave() + "    Categoria: " + r.getCategoria());
        lineas.add("Tiempo de coccion: " + r.getTiempoCoccion() + " min    Porciones: " + r.getPorciones());
        lineas.add("");
        lineas.add("Ingredientes:");
        for(String ing : r.getIngredientes().split("\n")){
            agregarConWrap(lineas, "- " + ing);
        }
        lineas.add("");
        lineas.add("Modo de preparacion:");
        int numero = 1;
        for(String paso : r.getPreparacion().split("\n")){
            agregarConWrap(lineas, numero + ". " + paso);
            numero++;
        }
        //La imagen no se incrusta en el PDF para mantener el generador
        //simple; solo se deja el nombre del archivo como referencia
        //(la imagen se ve en la propia ventana de la app)
        if(r.getRutaImagen() != null && !r.getRutaImagen().trim().isEmpty()){
            lineas.add("");
            lineas.add("Imagen: " + new File(r.getRutaImagen()).getName());
        }

        ArrayList<ArrayList<String>> paginas = paginar(lineas);

        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        ArrayList<Integer> offsets = new ArrayList<>();
        offsets.add(0);

        escribir(salida, "%PDF-1.4\n");

        int numPaginas = paginas.size();
        int objCatalogo = 1;
        int objPaginas = 2;
        int primerObjPagina = 3;
        int primerObjContenido = primerObjPagina + numPaginas;
        int objFuente = primerObjContenido + numPaginas;

        marcar(offsets, salida);
        escribir(salida, objCatalogo + " 0 obj\n<< /Type /Catalog /Pages " + objPaginas + " 0 R >>\nendobj\n");

        String hijos = "";
        for(int i = 0; i < numPaginas; i++){
            hijos = hijos + (primerObjPagina + i) + " 0 R ";
        }
        marcar(offsets, salida);
        escribir(salida, objPaginas + " 0 obj\n<< /Type /Pages /Kids [" + hijos.trim() + "] /Count " + numPaginas + " >>\nendobj\n");

        for(int i = 0; i < numPaginas; i++){
            int numObjPagina = primerObjPagina + i;
            int numObjContenido = primerObjContenido + i;
            marcar(offsets, salida);
            escribir(salida, numObjPagina + " 0 obj\n<< /Type /Page /Parent " + objPaginas
                    + " 0 R /MediaBox [0 0 " + (int) ANCHO_PAGINA + " " + (int) ALTO_PAGINA
                    + "] /Resources << /Font << /F1 " + objFuente + " 0 R >> >> /Contents "
                    + numObjContenido + " 0 R >>\nendobj\n");
        }

        for(int i = 0; i < numPaginas; i++){
            int numObjContenido = primerObjContenido + i;
            String contenido = contenidoPagina(paginas.get(i));
            byte[] bytes = contenido.getBytes(StandardCharsets.ISO_8859_1);
            marcar(offsets, salida);
            escribir(salida, numObjContenido + " 0 obj\n<< /Length " + bytes.length + " >>\nstream\n");
            salida.write(bytes);
            escribir(salida, "\nendstream\nendobj\n");
        }

        marcar(offsets, salida);
        escribir(salida, objFuente + " 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica /Encoding /WinAnsiEncoding >>\nendobj\n");

        int offsetXref = salida.size();
        int totalObjetos = objFuente;
        escribir(salida, "xref\n0 " + (totalObjetos + 1) + "\n0000000000 65535 f \n");
        for(int i = 1; i <= totalObjetos; i++){
            escribir(salida, formatearOffset(offsets.get(i)) + " 00000 n \n");
        }
        escribir(salida, "trailer\n<< /Size " + (totalObjetos + 1) + " /Root " + objCatalogo
                + " 0 R >>\nstartxref\n" + offsetXref + "\n%%EOF");

        File carpeta = destino.getParentFile();
        if(carpeta != null && !carpeta.exists()){
            carpeta.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(destino);
        fos.write(salida.toByteArray());
        fos.close();
    }

    private static void agregarConWrap(ArrayList<String> lineas, String texto){
        int maximo = 95;
        while(texto.length() > maximo){
            int corte = texto.lastIndexOf(' ', maximo);
            if(corte <= 0){
                corte = maximo;
            }
            lineas.add(texto.substring(0, corte));
            texto = "   " + texto.substring(corte).trim();
        }
        lineas.add(texto);
    }

    private static ArrayList<ArrayList<String>> paginar(ArrayList<String> lineas){
        ArrayList<ArrayList<String>> paginas = new ArrayList<>();
        int indice = 0;
        while(indice < lineas.size()){
            int fin = Math.min(lineas.size(), indice + LINEAS_POR_PAGINA);
            paginas.add(new ArrayList<>(lineas.subList(indice, fin)));
            indice = fin;
        }
        if(paginas.isEmpty()){
            paginas.add(new ArrayList<String>());
        }
        return paginas;
    }

    private static String contenidoPagina(ArrayList<String> lineas){
        StringBuilder sb = new StringBuilder();
        sb.append("BT\n/F1 11 Tf\n").append(MARGEN).append(" ").append(ALTO_PAGINA - MARGEN).append(" Td\n");
        boolean primera = true;
        for(String linea : lineas){
            if(primera){
                sb.append("(").append(escapar(linea)).append(") Tj\n");
                primera = false;
            }else{
                sb.append("0 -").append((int) INTERLINEA).append(" Td\n");
                sb.append("(").append(escapar(linea)).append(") Tj\n");
            }
        }
        sb.append("ET\n");
        return sb.toString();
    }

    private static String escapar(String texto){
        return texto.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }

    private static void marcar(ArrayList<Integer> offsets, ByteArrayOutputStream salida){
        offsets.add(salida.size());
    }

    private static void escribir(ByteArrayOutputStream salida, String texto) throws IOException{
        salida.write(texto.getBytes(StandardCharsets.ISO_8859_1));
    }

    private static String formatearOffset(int offset){
        String texto = String.valueOf(offset);
        String relleno = "";
        for(int i = texto.length(); i < 10; i++){
            relleno = relleno + "0";
        }
        return relleno + texto;
    }
}
