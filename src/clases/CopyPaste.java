
package clases;

import estructuras.*;
import static interfaz.Explorador.r;
import static interfaz.Explorador.mult;

public class CopyPaste 
{
    private String clipboard;
    private String clipboard_fullpath;
    private Nodo clipboard_Directorio;
    private String clipboard_nombre_Directorio;
    // archivo = false   // directorio = true
    public boolean directorio_o_archivo;

    public CopyPaste() 
    {
        this.clipboard = "";
        this.clipboard_fullpath = "";
    }

    public void copiar_archivo(String fullPath, String fileName, String file_ext) 
    {
        //pasa a portapapeles
       this.clipboard_fullpath = fullPath; 
       this.clipboard = fileName+"."+file_ext;
       this.directorio_o_archivo = false;
       System.out.println("ARCHIVO COPIADO: " + fullPath +"/"+ fileName +"."+ file_ext);
    }

    public void pegar_Archivo(String objetivo)
    {
        Altas alt = new Altas();
        alt.altaRuta(objetivo, clipboard, 6, 'A');
    }

    public void copiar_directorio(String dirName) 
    {
        System.out.println("COPIANDO DIRECTORIO:" + dirName);
	this.directorio_o_archivo = true;
        this.clipboard_nombre_Directorio = dirName;
        this.clipboard_Directorio = mult.buscarNodo2(r, dirName);
    }

    public void pegar_directorio(String ruta_objetivo)
    {
        Altas alt = new Altas();
        System.out.println(clipboard_nombre_Directorio);
        System.out.println(ruta_objetivo);
        alt.altaRuta("(Copy)"+clipboard_nombre_Directorio, ruta_objetivo, 0, 'c');
        alt.altaRuta_Nodo(ruta_objetivo, "(Copy)"+this.clipboard_nombre_Directorio, this.clipboard_Directorio);

    }
}

