package interfaz;
import clases.Altas;
import clases.Bajas;
import clases.CopyPaste;
import clases.Elemento;
import estructuras.Multilista;
import estructuras.Nodo;

import javax.swing.*;

import javax.swing.event.TreeSelectionEvent;

import javax.swing.event.TreeSelectionListener;

import javax.swing.table.DefaultTableModel;

import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;

import java.awt.*;

import java.util.ArrayList;

import java.util.Date;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class Explorador extends JFrame {

    private JTree fileTree;

    private JTable fileTable;

    private DefaultTableModel tableModel;
    public static Nodo r = null;
    public static  Multilista mult = new Multilista();
    public static List<Elemento> subElementos = new ArrayList<>();
 
    public Explorador() {
        cargaDatos();

        initComponents();

        initializeFileTree();

        initializeFileTable();

    }
 
    private void initComponents() {

        JSplitPane splitPane = new JSplitPane();

        fileTree = new JTree();

        fileTable = new JTable();
 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        splitPane.setLeftComponent(new JScrollPane(fileTree));

        splitPane.setRightComponent(new JScrollPane(fileTable));

        getContentPane().add(splitPane, BorderLayout.CENTER);
 
        pack();

    }
 
    private void initializeFileTree() {
       
        Elemento rootElemento = createSampleFileSystem(); // Método para crear un sistema de archivos de ejemplo

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootElemento);

        createChildren(root, rootElemento);

        fileTree.setModel(new DefaultTreeModel(root));
 
        fileTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override

            public void valueChanged(TreeSelectionEvent e) {

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
               
                if (selectedNode != null) {
                    //aqui poner logica para desplegar lo de las carpetas
                    System.out.println("nombre "+extraerPalabraEntreParentesis(selectedNode.toString()));
                    mult.buscarNodoLis(r, extraerPalabraEntreParentesis(selectedNode.toString()), tableModel);
                }

            }

        });

    }
 
    private void createChildren(DefaultMutableTreeNode node, Elemento elemento) {

        // Aquí deberías agregar la lógica para obtener las subcarpetas y archivos de 'elemento'

        // Como ejemplo simple, crearemos subcarpetas y archivos de forma manual

        List<Elemento> subElementos = createSubElementos(elemento);

        for (Elemento subElemento : subElementos) {

            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(subElemento);

            node.add(childNode);

            if (subElemento.getTipo() == 'C') {

                createChildren(childNode, subElemento);

            }

        }

    }
 
    private void initializeFileTable() {

        tableModel = new DefaultTableModel();

        tableModel.addColumn("Name");

        tableModel.addColumn("Size");

        tableModel.addColumn("Type");

        tableModel.addColumn("Author");

        tableModel.addColumn("Date");

        fileTable.setModel(tableModel);

    }
 
    private void updateFileTable(Elemento carpeta) {

        // Aquí deberías agregar la lógica para obtener los elementos dentro de 'carpeta'

        // Como ejemplo simple, crearemos archivos de forma manual

        List<Elemento> archivos = createSubElementos(carpeta);

        tableModel.setRowCount(0);
        
        for (Elemento archivo : archivos) {

//            if (archivo.getTipo() == 'A') {

                tableModel.addRow(new Object[]{

                        archivo.getNombre(),

                        archivo.getTamanio(),

                        archivo.getExtencion(),

                        archivo.getAutor(),

                        archivo.getFecha()

                });

//            }

        }

    }
 
    private Elemento createSampleFileSystem() {

        Elemento root = new Elemento("Root", "", 'C', 0, "C:\\Root");

        return root;

    }
 
    private List<Elemento> createSubElementos(Elemento elemento) {

       

        mult.desp3(r);

        return subElementos;

    }
 
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new Explorador().setVisible(true);

        });

    }

    
    public void cargaDatos()
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
       mult.desp(r, "1: ");
            Nodo<Elemento> fileNode = mult.buscarNodo(r, "ARchivo2");
            System.out.println(((Elemento)fileNode.getObj()).getRuta());
            //mult.desp(fileNode, "---");
            Nodo<Elemento> dirNode = mult.buscarNodo(r, "Musica"); 
            System.out.println(((Elemento)dirNode.getObj()).getRuta());
            //mult.desp(dirNode, "**\t");
            cp.Copiar_archivo(((Elemento)fileNode.getObj()).getRuta(), "ARchivo2",((Elemento)fileNode.getObj()).getExtencion());
            
            cp.Pegar_Archivo("C:/Musica/Carpeta_Sub");
            
            cp.Copiar_directorio(((Elemento)dirNode.getObj()).getRuta(), "Musica");
            cp.Pegar_directorio("C:/Escritorio");
        mult.desp(r, "etq: ");

       Elemento elemento = bj.bajaElimina("Archivo_random");
        if (elemento!=null) {
            System.out.println("Elemento: "+elemento.getAutor());
        } else {
            System.out.println("No elimno el hd****");
        }
       
        System.out.println("-----------------");
       
        mult.desp(r,"etq: ");
     }
    
    public String extraerPalabraEntreParentesis(String texto) {
        // Usamos una expresión regular para buscar contenido entre paréntesis
        Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(texto);
        if (matcher.find()) {
            // Retorna el primer grupo capturado, que es la palabra entre paréntesis
            return matcher.group(1);
        }
        return null; // No se encontraron paréntesis con contenido
    }
    }
