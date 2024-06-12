/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.ArrayList;
import java.util.List;
import estructuras.*;
import static clases.PrbAltas.r;
import static clases.PrbAltas.mult;

public class CopyPaste 
{
    //Atributos privados para almacenar información
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

    public void copiar_directorio(String fullPath, String dirName) 
    {
        System.out.println("COPIANDO DIRECTORIO:" + dirName);
        //almacena el nombre del directorio y busca el nodo correspondient
	this.directorio_o_archivo = true;
        this.clipboard_nombre_Directorio = dirName;
        this.clipboard_Directorio = mult.buscarNodo2(r, dirName);
    }

    public void pegar_directorio(String ruta_objetivo)
    {
        Altas alt = new Altas();
        //Se inserta el nodo inicial o la carpeta
        System.out.println(clipboard_nombre_Directorio);
        System.out.println(ruta_objetivo);
        //alt.altaRuta("(Copy)"+clipboard_nombre_Directorio, ruta_objetivo, 0, 'c');
        alt.altaRuta_Nodo(ruta_objetivo, this.clipboard_nombre_Directorio, this.clipboard_Directorio);

    }
}

