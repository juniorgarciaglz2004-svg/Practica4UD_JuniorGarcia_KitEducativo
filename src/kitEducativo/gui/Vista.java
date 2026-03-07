package kitEducativo.gui;

import kitEducativo.datos.Kit_Educativo;
import kitEducativo.datos.Empresa;
import kitEducativo.datos.Producto;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {
    private JPanel panelPrincipal;

    // Productos
    JTextField txtMarcaProducto;
    JTextField txtDescripcionProducto;
    JTextField txtModeloProducto;

    JList<Producto> listProductos;

    JButton btnAddProducto;
    JButton btnModProducto;
    JButton btnDelProducto;

    JTextField txtBuscarProducto;
    JList<Producto> listBusquedaProducto;

    // Empleados
    JTextField txtNombreEmpresa;
    JTextField txtApellidosEmpleado;
    DatePicker dateFechaDeCreacionEmpresa;

    JList<Empresa> listEmpresa;

    JButton btnAddEmpresa;
    JButton btnModEmpresa;
    JButton btnDelEmpresa;

    JTextField txtBuscarEmpresa;
    JList<Empresa> listBusquedaEmpresa;

    // Departamentos
    JTextField txtNombreKit;

    JList<Kit_Educativo> listKitEducativo;

    JButton btnAddKitEducativo;
    JButton btnModKitEducativo;
    JButton btnDelKitEducativo;

    JTextField txtBuscarKitEducativo;
    JList<Kit_Educativo> listBusquedaKitEducativo;
    private JCheckBox usadoCheckBox;
    private JCheckBox nuevoCheckBox;
    private JCheckBox reacondicionadoCheckBox;
    private JTextField txtDescripcionKit;
    private JTextField txtCantidadKit;
    private JComboBox comboBoxKitEmpresa;
    private JTextField txtPrecioKit;
    private JSlider sliderKitEducativo;
    private JTextField txtNombreProducto;
    private JTextField txtDescripcionEmpresa;
    private JTextField txtUbicacionEmpresa;
    private JSlider sliderValorcaion;
    private JComboBox comboKitProducto;

    // Modelos
    DefaultListModel<Producto> dlmProductos;
    DefaultListModel<Empresa> dlmEmpleados;
    DefaultListModel<Kit_Educativo> dlmDepartamentos;
    DefaultListModel<Producto> dlmProductosBusqueda;
    DefaultListModel<Empresa> dlmEmpleadosBusqueda;
    DefaultListModel<Kit_Educativo> dlmDepartamentosBusqueda;

    // Menu
    JMenuItem itemConectar;
    JMenuItem itemSalir;

    public Vista() {
        setTitle("Bar Manolo - <SIN CONEXION>");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 650));
        setResizable(false);
        pack();
        setVisible(true);

        inicializarModelos();
        inicializarMenu();
    }

    private void inicializarModelos() {
        dlmProductos = new DefaultListModel<>();
        listProductos.setModel(dlmProductos);
        dlmEmpleados = new DefaultListModel<>();
        listEmpresa.setModel(dlmEmpleados);
        dlmDepartamentos = new DefaultListModel<>();
        listKitEducativo.setModel(dlmDepartamentos);
        dlmProductosBusqueda = new DefaultListModel<>();
        listBusquedaProducto.setModel(dlmProductosBusqueda);
        dlmEmpleadosBusqueda = new DefaultListModel<>();
        listBusquedaEmpresa.setModel(dlmEmpleadosBusqueda);
        dlmDepartamentosBusqueda = new DefaultListModel<>();
        listBusquedaKitEducativo.setModel(dlmDepartamentosBusqueda);
    }

    private void inicializarMenu() {
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
