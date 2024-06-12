package estructuras;

import clases.Elemento;
import static interfaz.Explorador.subElementos;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Multilista<T>
{

    public Nodo inserta(Nodo r, int nivel, String s[], Nodo n)
    {
        if (s.length - 1 == nivel)
        {
            ListaDobleCircular obj = new ListaDobleCircular();
            obj.r = r;
            obj.inserta(n);
            return obj.r;
        } else
        {
            Nodo aux = busca(r, s[nivel]);
            if (aux == null)
            {  // Si no se encuentra, se crea un nuevo nodo en ese nivel
                aux = new Nodo(null, s[nivel]);
                if (r == null)
                {
                    r = aux; // Si r es null, establece r como aux
                } else
                {
                    // Lógica para insertar aux en r si no es el primer nodo
                    Nodo temp = r;
                    while (temp.getSig() != null)
                    {
                        temp = temp.getSig(); // Encuentra el último nodo
                    }
                    temp.setSig(aux); // Conecta el último nodo con aux
                    aux.setAnt(temp); // Establece el anterior de aux
                }
                aux.setAbj(inserta(null, nivel + 1, s, n));
            } else
            {
                aux.setAbj(inserta(aux.getAbj(), nivel + 1, s, n));
            }
            return r;
        }
    }

    public Nodo eliminarNodo(Nodo r, String etiqueta)
    {
        if (r == null)
        {
            return null; // Si el nodo inicial es nulo, retornamos null
        }

        Nodo start = r; // Nodo de inicio para detectar ciclos
        Nodo previo = null; // Nodo previo para poder modificar enlaces

        do
        {
            if (r.getEtq().equals(etiqueta))
            {
                if (previo == null)
                {
                    // Si el nodo a eliminar es el primero, manejamos el caso de ciclo
                    Nodo ultimo = r;
                    while (ultimo.getSig() != start && ultimo.getSig() != r)
                    {
                        ultimo = ultimo.getSig();
                    }
                    if (ultimo == r)
                    {
                        // Si sólo hay un nodo, retornamos null
                        return null;
                    } else
                    {
                        // Si hay más de un nodo, ajustamos el enlace del último nodo
                        ultimo.setSig(r.getSig());
                        return r.getSig(); // Retornamos el nuevo inicio
                    }
                } else
                {
                    // Nodo a eliminar está en el medio o final
                    previo.setSig(r.getSig());
                    return start; // Retornamos el inicio original
                }
            }

            // Llamada recursiva para subniveles
            r.setAbj(eliminarNodo(r.getAbj(), etiqueta));

            previo = r;
            r = r.getSig(); // Avanzamos al siguiente nodo en el nivel actual
            if (r == start)
            {
                return start; // Salimos si detectamos un ciclo
            }
        } while (r != start && r != null); // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo

        return start; // Retornamos el inicio si no se encuentra el nodo
    }

    public Nodo elimina(Nodo r, String etq)
    {
        if (r == null)
        {
            System.out.println("La multilista está vacía");
            return null;
        }

        Nodo ne = busca(r, etq);

        if (ne == null)
        {
            System.out.println("El nodo con la etiqueta " + etq + " no fue encontrado en la multilista");
            return null;
        }

        if (ne == r)
        {
            // Si el nodo a eliminar es el primer nodo de la multilista
            // Actualizamos la referencia al primer nodo (r)
            if (r.getSig() == r)
            {
                // Si la multilista tiene solo un nodo
                r = null;
            } else
            {
                // Si hay más de un nodo en la multilista
                r = r.getSig(); // Movemos la referencia al siguiente nodo
            }
        }

        // Eliminamos el nodo de la lista doble circular
        ne.getAnt().setSig(ne.getSig());
        ne.getSig().setAnt(ne.getAnt());

        // Limpiamos las referencias del nodo eliminado
        ne.setSig(null);
        ne.setAnt(null);

        System.out.println("Nodo con la etiqueta " + etq + " eliminado de la multilista");
        return ne;
    }

    public Nodo busca(Nodo r, String s)
    {
        ListaDobleCircular obj = new ListaDobleCircular();
        obj.r = r;
        return obj.busca(s);
    }

    public Nodo buscaBajas(Nodo r, String s)
    {
        ListaDobleCircular obj = new ListaDobleCircular();
        obj.r = r;
        return obj.buscaBajas(s);
    }

    public void desp(Nodo r, String s)
    {
        if (r == null)
        {
            return;
        }

        Nodo start = r;
        do
        {
//            Elemento elemento=(Elemento)r.getObj();
//            System.out.println("Autor: "+elemento.getAutor());
//            System.out.println("Ruta: "+elemento.getRuta());
            System.out.println(s + r.getEtq());
            desp(r.getAbj(), s + "\t");  // Recursividad para manejar subniveles
            r = r.getSig();
            if (r == start)
            {
                return;  // Salir si se detecta un ciclo
            }
        } while (r != start && r != null);  // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo
    }

    public Nodo buscarNodo(Nodo r, String etiqueta)
    {
        if (r == null)
        {
            return null; // Si el nodo inicial es nulo, retornamos null
        }

        Nodo start = r; // Nodo de inicio para detectar ciclos
        do
        {
            if (r.getEtq().equals(etiqueta))
            {
                return r; // Si encontramos el nodo con la etiqueta deseada, lo retornamos
            }

            Nodo resultado = buscarNodo(r.getAbj(), etiqueta); // Llamada recursiva para subniveles
            if (resultado != null)
            {
                return resultado; // Si encontramos el nodo en los subniveles, lo retornamos
            }

            r = r.getSig(); // Avanzamos al siguiente nodo en el nivel actual
            if (r == start)
            {
                return null; // Salimos si detectamos un ciclo
            }
        } while (r != start && r != null); // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo

        return null; // Si no se encuentra el nodo, retornamos null
    }

    public Nodo buscarNodo2(Nodo r, String etiqueta)
    {
        if (r == null)
        {
            return null; // Si el nodo inicial es nulo, retornamos null
        }

        Nodo start = r; // Nodo de inicio para detectar ciclos
        do
        {
            if (r.getEtq().equals(etiqueta))
            {
                return r; // Si encontramos el nodo con la etiqueta deseada, lo retornamos
            }

            Nodo resultado = buscarNodo2(r.getAbj(), etiqueta); // Llamada recursiva para subniveles
            if (resultado != null)
            {
                //System.out.println("ENCONTRADO!!!: "+((Elemento)resultado.getObj()).getNombre());
                //System.out.println("ENCONTRADO EN !!!: " + ((Elemento) resultado.getAbj().getObj()).getRuta());
                resultado = resultado.getAbj(); // Explora el nodo a pegar.
                return resultado; // Si encontramos el nodo en los subniveles, lo retornamos
            }

            r = r.getSig(); // Avanzamos al siguiente nodo en el nivel actual
            if (r == start)
            {
                return null; // Salimos si detectamos un ciclo
            }
        } while (r != start && r != null); // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo

        return null; // Si no se encuentra el nodo, retornamos null
    }

    
    public Nodo buscarNodoLis(Nodo r, String etiqueta,DefaultTableModel tableModel)
    {
        if (r == null)
        {
            return null; // Si el nodo inicial es nulo, retornamos null
        }

        Nodo start = r; // Nodo de inicio para detectar ciclos
        do
        {
            if (r.getEtq().equals(etiqueta))
            {
                return r; // Si encontramos el nodo con la etiqueta deseada, lo retornamos
            }

            Nodo resultado = buscarNodo2(r.getAbj(), etiqueta); // Llamada recursiva para subniveles
            if (resultado != null)
            {
                //System.out.println("ENCONTRADO!!!: "+((Elemento)resultado.getObj()).getNombre());
                System.out.println("ENCONTRADO EN !!!: " + ((Elemento) resultado.getAbj().getObj()).getRuta());
                resultado = resultado.getAbj(); // Explora el nodo a pegar.
                return resultado; // Si encontramos el nodo en los subniveles, lo retornamos
            }

            r = r.getSig(); // Avanzamos al siguiente nodo en el nivel actual
            if (r == start)
            {
                return null; // Salimos si detectamos un ciclo
            }
        } while (r != start && r != null); // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo

        return null; // Si no se encuentra el nodo, retornamos null
    }
    
    public void desp2(Nodo r,DefaultTableModel model)

    {

        if (r == null)

        {

            return;

        }

        Nodo start = r;

        do

        {

//            Elemento elemento=(Elemento)r.getObj();
//            System.out.println("Autor: "+elemento.getAutor());
//            System.out.println("Ruta: "+elemento.getRuta());
            //model.addRow(((Elemento) r.getObj()).getRuta());

            r = r.getSig();

            if (r == start)

            {

                return;  // Salir si se detecta un ciclo

            }

        } while (r != start && r != null);  // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo

    }
    
public void desp3(Nodo r)

    {

        r = r.getAbj();

        if (r == null)

        {

            return;

        }
 
        Nodo start = r;

        do

        {

//            Elemento elemento=(Elemento)r.getObj();

//            System.out.println("Autor: "+elemento.getAutor());

//            System.out.println("Ruta: "+elemento.getRuta());

            System.out.println(r.getEtq());
            subElementos.add((Elemento)r.getObj());
            r = r.getSig();

            if (r == start)

            {

                return;  // Salir si se detecta un ciclo

            }

        } while (r != start && r != null);  // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo

    }

public void desp4(Nodo r,DefaultTableModel tableModel)

    {

        r = r.getAbj();

        if (r == null)

        {

            return;

        }
 
        Nodo start = r;

        do

        {

//            Elemento elemento=(Elemento)r.getObj();

//            System.out.println("Autor: "+elemento.getAutor());

//            System.out.println("Ruta: "+elemento.getRuta());

            System.out.println(r.getEtq());
            Elemento elem=(Elemento)r.getObj();
                tableModel.addRow(new Object[]{elem.getNombre(), elem.getTamanio(), elem.getTipo(), elem.getAutor(), elem.getFecha()});
            r = r.getSig();
            if (r == start)

            {

                return;  // Salir si se detecta un ciclo

            }

        } while (r != start && r != null);  // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo

    }

public void desp5(Nodo r, DefaultTableModel tableModel)
    {
        if (r == null)
        {
            return;
        }

        Nodo start = r;
        do
        {
//            Elemento elemento=(Elemento)r.getObj();
//            System.out.println("Autor: "+elemento.getAutor());
//            System.out.println("Ruta: "+elemento.getRuta());
            System.out.println(r.getEtq());
            Elemento elem=(Elemento)r.getObj();
            tableModel.addRow(new Object[]{elem.getNombre(), elem.getTamanio(), elem.getTipo(), elem.getAutor(), elem.getFecha()});
            desp5(r.getAbj(),tableModel);  // Recursividad para manejar subniveles
            r = r.getSig();
            if (r == start)
            {
                return;  // Salir si se detecta un ciclo
            }
        } while (r != start && r != null);  // Continuar mientras no se regrese al inicio o se encuentre un nodo nulo
    }
}
