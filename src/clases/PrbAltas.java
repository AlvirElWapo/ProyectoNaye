/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clases;

import estructuras.Multilista;
import estructuras.Nodo;
import clases.*;
import estructuras.NodoArbol;
import estructuras.TablaHash;

/**
 *
 * @author Nayeli
 */
public class PrbAltas 
{
    public static Nodo r = null;
    public static  Multilista mult = new Multilista();
    public static TablaHash th = new TablaHash();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
     
       Altas alt = new Altas();
       Bajas bj = new Bajas();
       CopyPaste cp = new CopyPaste();
       
       alt.altaRuta("C:", "C:", 0, 'c');
       alt.altaRuta("C:", "Documentos", 0, 'c');
       alt.altaRuta("C:/Documentos", "archivo.txt", 6, 'A');
       alt.altaRuta("C:", "Descarga", 0, 'c');
       alt.altaRuta("C:", "Musica", 0, 'c');
       alt.altaRuta("C:/Musica", "PerdidoEnLaOscuridadJoseJose.mp3", 6, 'A');
       alt.altaRuta("C:/Musica", "Carpeta_Sub", 6, 'c');
       alt.altaRuta("C:/Musica/Carpeta_Sub", "Archivo_random.txt", 6, 'A');
       alt.altaRuta("C:", "Escritorio", 0, 'c');
       alt.altaRuta("C:/Escritorio", "ProyectoMauro.java", 6, 'A');
       alt.altaRuta("C:/Descarga", "ARchivo2.txt", 6, 'A');
       alt.altaRuta("C:/Documentos", "EDD", 0, 'c');

            Nodo<Elemento> fileNode = mult.buscarNodo(r, "ARchivo2");
            System.out.println(((Elemento)fileNode.getObj()).getRuta());
            //mult.desp(fileNode, "---");
            Nodo<Elemento> dirNode = mult.buscarNodo(r, "Musica"); 
            System.out.println(((Elemento)dirNode.getObj()).getRuta());
            //mult.desp(dirNode, "**\t");
            cp.copiar_archivo(((Elemento)fileNode.getObj()).getRuta(), "ARchivo2",((Elemento)fileNode.getObj()).getExtencion());
            
            cp.pegar_Archivo("C:/Musica/Carpeta_Sub");
            
            cp.copiar_directorio(((Elemento)dirNode.getObj()).getRuta(), "Musica");
            cp.pegar_directorio("C:/Escritorio");
        mult.desp(r, "etq: ");

       Elemento elemento = bj.bajaElimina("Archivo_random");
        if (elemento!=null) {
            System.out.println("Elemento: "+elemento.getAutor());
        } else {
            System.out.println("No elimno el hd****");
        }
       
        System.out.println("-----------------");
       
        mult.desp(r,"etq: ");
        
        
        System.out.println("----Busqueda de en tablas hash ------");
        NodoArbol nodoBusqueda=th.busca("Musica");
        Nodo nodo=(Nodo)nodoBusqueda.getObj();
        System.out.println("Elemento: "+nodo.getEtq());
        
        Elemento ele= (Elemento) nodo.getObj();
        
        if (ele!=null)
        {
            System.out.println("Autor: "+ele.getAutor());
        } else
        {
            System.out.println("NO hay datos");
        }
        
     }
}
