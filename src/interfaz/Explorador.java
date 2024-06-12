package interfaz;

import clases.Altas;
import clases.Bajas;
import clases.CopyPaste;
import clases.Elemento;
import estructuras.Multilista;
import estructuras.Nodo;
import estructuras.TablaHash;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Explorador extends JFrame 
{

    private JTree fileTree;
    private JTable fileTable;
    private DefaultTableModel tableModel;
    public static Nodo r = null;
    public static Multilista mult = new Multilista();
    public static List<Elemento> subElementos = new ArrayList<>();
    public static TablaHash th = new TablaHash();

    public Explorador() 
    {
        cargaDatos();
        initComponents();
        initializeFileTree();
        initializeFileTable();
    }

    private void initComponents() 
    {
        JSplitPane splitPane = new JSplitPane();
        fileTree = new JTree();
        fileTable = new JTable();
        
        JButton searchButton = new JButton("Buscar"); 
        searchButton.setPreferredSize(new Dimension(120, 30));
        
        JButton newButton = new JButton("Nuevo");
        newButton.setPreferredSize(new Dimension(120, 30));
        
        JButton deleteButton = new JButton();
        deleteButton.setPreferredSize(new Dimension(120, 30));
        
        ImageIcon searchIcon = new ImageIcon("src/recursos/search_icon.png");
        Image searchImage = searchIcon.getImage();
        Image searchImageScaled = searchImage.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // Cambiar dimensiones aquí
        searchIcon = new ImageIcon(searchImageScaled);
        searchButton.setIcon(searchIcon);
        
        ImageIcon addIcon = new ImageIcon("src/recursos/add_icon.png");
        Image addImage = addIcon.getImage();
        Image addImageScaled = addImage.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // Cambiar dimensiones aquí
        addIcon = new ImageIcon(addImageScaled);
        newButton.setIcon(addIcon);
        
        ImageIcon deleteIcon = new ImageIcon("src/recursos/delete_icon.png");
        Image deleteImage = deleteIcon.getImage();
        Image deleteImageScaled = deleteImage.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // Cambiar dimensiones aquí
        deleteIcon = new ImageIcon(deleteImageScaled);
        deleteButton.setIcon(deleteIcon);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Configurar el botón y su evento
        searchButton.addActionListener(e -> onSearchButtonClick());
        searchButton.setBackground(new Color(47, 79, 79));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        newButton.addActionListener(e -> onNewButtonClick());
        newButton.setBackground(new Color(47, 79, 79));
        newButton.setForeground(Color.WHITE);
        newButton.setFocusPainted(false);
        
        deleteButton.addActionListener(e -> 
        {
            int selectedOption = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar este elemento?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (selectedOption == JOptionPane.YES_OPTION) 
            {
                onDeleteButtonClick();
            }
        });

        deleteButton.setBackground(new Color(47, 79, 79));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(searchButton);
        controlPanel.add(newButton);
        controlPanel.add(deleteButton);
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        controlPanel.setBackground(new Color(47, 79, 79));

        splitPane.setLeftComponent(new JScrollPane(fileTree));
        splitPane.setRightComponent(new JScrollPane(fileTable));

        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.NORTH);  // Añadir el panel de control en la parte superior
        

        // Configuración de la apariencia de los componentes
        fileTable.setBackground(new Color(240, 248, 255));
        fileTable.setForeground(Color.BLACK);

        pack();
    }

    private void initializeFileTree() 
    {
        
        Elemento rootElemento = createSampleFileSystem(); // Método para crear un sistema de archivos de ejemplo
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootElemento);
        createChildren(root, rootElemento);
        fileTree.setModel(new DefaultTreeModel(root));
        
        // Crear el popup menu
        JPopupMenu treePopupMenu = new JPopupMenu();
        JMenuItem copy = new JMenuItem("Copiar");
        JMenuItem paste = new JMenuItem("Pegar");
        treePopupMenu.add(copy);
        treePopupMenu.add(paste);
        
        fileTree.addTreeSelectionListener(new TreeSelectionListener() 
        {
            @Override
            public void valueChanged(TreeSelectionEvent e) 
            {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
                if (selectedNode != null) 
                {
                    // Aquí poner lógica para desplegar lo de las carpetas
                    tableModel.setRowCount(0);
                    System.out.println("nombre " + extraerPalabraEntreParentesis(selectedNode.toString()));
                    Nodo nuevo = mult.buscarNodo2(r, extraerPalabraEntreParentesis(selectedNode.toString()));
                    mult.desp5(nuevo, tableModel);
                }
            }
        });
        
        fileTree.addMouseListener(new MouseAdapter() 
        {
        @Override
        public void mousePressed(MouseEvent e) 
        {
            if (SwingUtilities.isRightMouseButton(e)) 
            {
                int row = fileTree.getClosestRowForLocation(e.getX(), e.getY());
                fileTree.setSelectionRow(row);
                treePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
        });
    }

    private void createChildren(DefaultMutableTreeNode node, Elemento elemento) 
    {
        // Como ejemplo simple, crearemos subcarpetas y archivos de forma manual
        List<Elemento> subElementos = createSubElementos(elemento);
        for (Elemento subElemento : subElementos) 
        {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(subElemento);
            node.add(childNode);
            if (subElemento.getTipo() == 'C') 
            {
                createChildren(childNode, subElemento);
            }
        }
    }

    private void initializeFileTable() 
    {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Size");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Autor");
        tableModel.addColumn("Fecha");
        fileTable.setModel(tableModel);
        
         // Crear el popup menu
        JPopupMenu tablePopupMenu = new JPopupMenu();
        JMenuItem openItem = new JMenuItem("Copiar");
        JMenuItem deleteItem = new JMenuItem("Pegar");
        tablePopupMenu.add(openItem);
        tablePopupMenu.add(deleteItem);

        // Añadir mouse listener para mostrar el popup menu
        fileTable.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if (SwingUtilities.isRightMouseButton(e)) 
                {
                    int row = fileTable.rowAtPoint(e.getPoint());
                    fileTable.setRowSelectionInterval(row, row);
                    tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void updateFileTable(Elemento carpeta) 
    {
        List<Elemento> archivos = createSubElementos(carpeta);
        tableModel.setRowCount(0);
        for (Elemento archivo : archivos) 
        {
            tableModel.addRow(new Object[]
            {
                archivo.getNombre(),
                archivo.getTamanio(),
                archivo.getExtencion(),
                archivo.getAutor(),
                archivo.getFecha()
            });
        }
    }

    private Elemento createSampleFileSystem() 
    {
        Elemento root = new Elemento("Root", "", 'C', 0, "C:\\Root");
        return root;
    }

    // Subelementos
    private List<Elemento> createSubElementos(Elemento elemento) 
    {
	subElementos = new ArrayList<>();
	mult.desp3(r);
        return subElementos;
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            new Explorador().setVisible(true);
        });
    }

    public void cargaDatos() 
    {
        Altas alt = new Altas();
        Bajas bj = new Bajas();
        CopyPaste cp = new CopyPaste();
        if(r == null){
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
        alt.altaRuta("C:", "Juegos", 0, 'c');
        }
        mult.desp(r, "---: ");
    }

    public String extraerPalabraEntreParentesis(String texto) 
    {
        // Usamos una expresión regular para buscar contenido entre paréntesis
        Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(texto);
        if (matcher.find()) 
        {
            // Retorna el primer grupo capturado, que es la palabra entre paréntesis
            return matcher.group(1);
        }
        return null; // No se encontraron paréntesis con contenido
    }

    private void onSearchButtonClick() 
    {
        new Busqueda().setVisible(true);
    }

    private void onNewButtonClick() 
    {
        new Nuevo().setVisible(true);
        this.dispose();
    }

    private void onDeleteButtonClick() 
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

