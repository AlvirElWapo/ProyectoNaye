/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

/**
 *
 * @author Nayeli
 */
public class TablaHash
{
    private ArbolBinario arr[]= new ArbolBinario[26];
 
    public TablaHash()
    {
        for (int i = 0; i < arr.length; i++)
        {
            arr[i]= new ArbolBinario();
        }
    }
    
    public void inserta(NodoArbol n)
    {
        if (n!=null)
        {
            int pos =n.getEtq().trim().toUpperCase().charAt(0)-65;
            arr[pos].setR(arr[pos].inserta(arr[pos].getR(), n));
            System.out.println("se inserto correctamente");
        } else
        {
            System.out.println("no se puede insertar");
        }
    }
    
    public NodoArbol busca(String et)
    {
        if(et.isEmpty())
        {
            System.out.println("error... la cadena esta vacia");
            return null;
        } else
        {
            int pos =et.trim().toUpperCase().charAt(0)-65;
            return  arr[pos].busca(arr[pos].getR(), et);
        }
    }
    
    public NodoArbol buscaYElimina(String et)
    {
        if (et.isEmpty())
        {
            System.out.println("Error... la cadena está vacía");
            return null;
        } else
        {
            int pos = et.trim().toUpperCase().charAt(0) - 'A'; // Calculamos la posición en el array

            if (arr[pos] == null)
            {
                System.out.println("Error... no existe un nodo en esta posición");
                return null;
            } else
            {
                NodoArbol[] auxArr = new NodoArbol[2]; // Array auxiliar para la eliminación
                auxArr[0] = null; // Inicialización del nodo a eliminar
                auxArr[1] = arr[pos].getR(); // Inicialización del nodo resultante tras la eliminación

                // Llamamos al método elimina con la raíz del subárbol
                arr[pos].elimina(arr[pos].getR(), et, auxArr);

                if (auxArr[0] == null)
                {
                    System.out.println("Error... elemento no encontrado");
                    return null;
                } else
                {
                    // Si el nodo existe y fue encontrado, mostramos el nodo eliminado
                    System.out.println("Elemento eliminado correctamente");
                    arr[pos].setR(auxArr[1]); // Actualizamos la raíz del subárbol en la posición calculada
                    return auxArr[0];
                }
            }
        }
    }



}
