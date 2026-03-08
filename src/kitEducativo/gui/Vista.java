package kitEducativo.gui;

import kitEducativo.datos.Kit_Educativo;
import kitEducativo.datos.Empresa;
import kitEducativo.datos.Producto;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {
     JPanel panelPrincipal;


    JTextField txtMarcaProducto;
    JTextField txtDescripcionProducto;
    JTextField txtModeloProducto;

    JList<Producto> listProductos;

    JButton btnAddProducto;
    JButton btnModProducto;
    JButton btnDelProducto;

    JTextField txtBuscarProducto;
    JList<Producto> listBusquedaProducto;


    JTextField txtNombreEmpresa;
    DatePicker dateFechaDeCreacionEmpresa;

    JList<Empresa> listEmpresa;

    JButton btnAddEmpresa;
    JButton btnModEmpresa;
    JButton btnDelEmpresa;

    JTextField txtBuscarEmpresa;
    JList<Empresa> listBusquedaEmpresa;


    JTextField txtNombreKit;

    JList<Kit_Educativo> listKitEducativo;

    JButton btnAddKitEducativo;
    JButton btnModKitEducativo;
    JButton btnDelKitEducativo;

    JTextField txtBuscarKitEducativo;
    JList<Kit_Educativo> listBusquedaKitEducativo;
     JCheckBox usadoCheckBox;
     JCheckBox nuevoCheckBox;
     JCheckBox reacondicionadoCheckBox;
     JTextField txtDescripcionKit;
     JTextField txtCantidadKit;
     JComboBox comboBoxKitEmpresa;
     JTextField txtPrecioKit;
     JSlider sliderKitEducativo;
     JTextField txtNombreProducto;
     JTextField txtDescripcionEmpresa;
     JTextField txtUbicacionEmpresa;
     JSlider sliderValorcaion;
     JComboBox comboKitProducto;
     DatePicker dateFechaCreacionKit;
     DatePicker dateFechaActualizacionKit;


    DefaultListModel<Producto> dlmProductos;
    DefaultListModel<Empresa> dlmEmpresas;
    DefaultListModel<Kit_Educativo> dlmKits;
    DefaultListModel<Producto> dlmProductosBusqueda;
    DefaultListModel<Empresa> dlmEmpresasBusqueda;
    DefaultListModel<Kit_Educativo> dlmKitsBusqueda;

    // Menu
    JMenuItem itemConectar;
    JMenuItem itemSalir;

    public Vista() {
        setTitle("Kit Educativos");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 650));
        setResizable(false);
        pack();
        setVisible(true);

        inicializarModelos();
        inicializarMenu();
    }

     void inicializarModelos() {
        dlmProductos = new DefaultListModel<>();
        listProductos.setModel(dlmProductos);
        dlmEmpresas = new DefaultListModel<>();
        listEmpresa.setModel(dlmEmpresas);
        dlmKits = new DefaultListModel<>();
        listKitEducativo.setModel(dlmKits);
        dlmProductosBusqueda = new DefaultListModel<>();
        listBusquedaProducto.setModel(dlmProductosBusqueda);
        dlmEmpresasBusqueda = new DefaultListModel<>();
        listBusquedaEmpresa.setModel(dlmEmpresasBusqueda);
        dlmKitsBusqueda = new DefaultListModel<>();
        listBusquedaKitEducativo.setModel(dlmKitsBusqueda);
    }

     void inicializarMenu() {
        itemConectar = new JMenuItem("Conectar");
        itemConectar.setActionCommand("conexion");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("salir");

        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.add(itemConectar);
        menuArchivo.add(itemSalir);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuArchivo);

        setJMenuBar(menuBar);
    }
}
