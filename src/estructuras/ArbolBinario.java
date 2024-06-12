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
public class ArbolBinario
{
    private NodoArbol r = null;
    
    /**
     * @return the r
     */
    public NodoArbol getR() 
    {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(NodoArbol r) 
    {
        this.r = r;
    }
    
    public NodoArbol inserta(NodoArbol r, NodoArbol n)
    {
        if (r == null) 
        {
            return n;
        }else
        {
            if (r.getEtq().compareTo(n.getEtq()) >0) 
            {
                r.setIzq(inserta(r.getIzq(), n));
            }else
            {
                r.setDer(inserta(r.getDer(), n)); 
            }
            return r;
        }
    }
    
    public void elimina(NodoArbol r, String etq, NodoArbol arr[])
    {
        if (r == null) 
        {
           arr[0]=null;
           arr[1]=r;
           System.out.println("No hay datos a eliminar");
        }else
        {
            //Busco el dato a eliminar y aplico la recursividad
            if (r.getEtq().equals(etq)) 
            {
                arr[0]=r;
                //Cuando no tiene hijos
                if (r.getDer() == null && r.getIzq() == null) 
                {
                    arr[1]=null;
                } else 
                {
                    //Cuando tiene un hijo
                    if (!(r.getDer() != null && r.getIzq() != null)) 
                    {
                        if (r.getDer() != null) 
                        {
                            arr[1]=r.getDer();
                        } else 
                        {
                            arr[1]=r.getIzq();
                        } 
                    } else //Tiene dos hijos
                    {
                        if (r.getDer().getIzq() == null) 
                        {
//                            NodoArbol se = r.getDer();
//                            se.setIzq(r.getIzq());
//                            arr[1] = se;
                            
                            //simplificado (no usamos la variable se)
                            arr[1]=r.getDer();
                            r.getDer().setIzq(r.getIzq());
                        } else 
                        {
//                            NodoArbol aux = sucesorE(r.getDer());
//                            NodoArbol se = aux.getIzq();
//                            aux.setIzq(se.getDer());
//                            se.setDer(r.getDer());
//                            se.setIzq(r.getIzq());
//                            arr[1]=se;
                            
                            //simplificado (no usamos un aux)
                            NodoArbol se = sucesorE(r);
                            arr[1] = se.getIzq();
                            se.setIzq(arr[1].getDer());
                            arr[1].setDer(r.getDer());
                            arr[1].setIzq(r.getIzq());
                        }
                        //hace la desconexion
                        r.setDer(null);
                        r.setIzq(null);
                    }
                }
                
            }else
            {
                //Si es mayor a lo que tengo en etq, se va a la izq
                if (r.getEtq().compareTo(etq) > 0) 
                {
                    elimina(r.getIzq(), etq, arr);
                    r.setIzq(arr[1]);
                    
                } else 
                {
                    elimina(r.getDer(), etq, arr);
                    r.setDer(arr[1]);
                }
                //Sustituyo y defino la nueva raíz
                arr[1] = r;
            }
        }
    }
    
    public NodoArbol sucesorE(NodoArbol r)
    {
        if (r.getIzq().getIzq() != null) 
        {
            return sucesorE(r.getIzq());
        }else
        {
            return r;
        }
    }
    
    
    
    public String enOrden(NodoArbol r)
    {
        String s="";
        if (r != null) 
        {
            s+=enOrden(r.getIzq());
            s+=r.getEtq();
            s+=enOrden(r.getDer());
        }
        return s;
    }
    
    public String preOrden(NodoArbol r)
    {
        String s="";
        if (r != null) 
        {
            s+=r.getEtq();
            s+=preOrden(r.getIzq());
            s+=preOrden(r.getDer());
        }
        return s;
    }
    
    public String postOrden(NodoArbol r)
    {
        String s="";
        if (r != null) 
        {
            s+=postOrden(r.getIzq());
            s+=postOrden(r.getDer());
            s+=r.getEtq();
        }
        return s;
    }
    
    public NodoArbol busca(NodoArbol r,String et)
    {
        if (r!=null)
        {
            if (r.getEtq().equals(et))
            {
                return r;
            } else
            {
                if (r.getEtq().compareTo(et)>0)
                {
                    return busca(r.getIzq(),et);
                } else
                {
                    return busca(r.getDer(),et);
                }
            }
        } else
        {
            System.out.println("no se puede hacer la busqueda");
            return null;
        }
    }

}
