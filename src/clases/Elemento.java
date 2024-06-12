/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.Date;

/**
 *
 * @author Manuel
 */
public class Elemento 
{
    private String nombre;
    private String extension;
    private Date fecha;
    private String autor;
    private char tipo; // A para archivo C para carpeta
    private int tamanio;
    private String ruta;
    
    public Elemento(String nombre, String extencion, char tipo, int tamanio, String ruta)
    {
        this.autor = System.getProperty("user.name");
        this.extension = extencion;
        this.fecha = new Date();
        this.nombre = nombre;
        this.ruta = ruta;
        this.tamanio = tamanio;
        this.tipo = tipo;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() 
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    /**
     * @return the extencion
     */
    public String getExtencion() 
    {
        return extension;
    }

    /**
     * @param extencion the extencion to set
     */
    public void setExtencion(String extencion) 
    {
        this.extension = extencion;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() 
    {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) 
    {
        this.fecha = fecha;
    }

    /**
     * @return the autor
     */
    public String getAutor() 
    {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor) 
    {
        this.autor = autor;
    }

    /**
     * @return the tipo
     */
    public char getTipo() 
    {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(char tipo) 
    {
        this.tipo = tipo;
    }

    /**
     * @return the tamanio
     */
    public int getTamanio() 
    {
        return tamanio;
    }

    /**
     * @param tamanio the tamanio to set
     */
    public void setTamanio(int tamanio) 
    {
        this.tamanio = tamanio;
    }

    /**
     * @return the ruta
     */
    public String getRuta() 
    {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) 
    {
        this.ruta = ruta;
    }
    
    @Override
    public String toString() 
    {
        return tipo == 'A' ? "Archivo(" + nombre + "." + extension + ", " + tamanio + "B)" : "Carpeta(" + nombre + ")";
    }
}
