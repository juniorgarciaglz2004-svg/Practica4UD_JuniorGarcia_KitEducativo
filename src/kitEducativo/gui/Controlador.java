package kitEducativo.gui;

import kitEducativo.datos.Empresa;
import kitEducativo.datos.EstadoProducto;
import kitEducativo.datos.Kit_Educativo;
import kitEducativo.datos.Producto;
import kitEducativo.util.Util;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Controlador implements ActionListener, KeyListener, ListSelectionListener {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.vista = vista;
        this.modelo = modelo;

        addActionListeners(this);
        addKeyListeners(this);
        addListSelectionListeners(this);

        try {
            modelo.conectar();
            vista.itemConectar.setText("Desconectar");
            vista.setTitle("Kit Educativos");
            setBotonesActivados(true);
            refrescarTodo();
        } catch (Exception ex) {
            Util.mostrarMensajeError("Imposible establecer conexión con el servidor.");
        }
    }

    private void addActionListeners(ActionListener listener) {
        vista.btnAddProducto.addActionListener(listener);
        vista.btnModProducto.addActionListener(listener);
        vista.btnDelProducto.addActionListener(listener);

        vista.btnAddEmpresa.addActionListener(listener);
        vista.btnModEmpresa.addActionListener(listener);
        vista.btnDelEmpresa.addActionListener(listener);

        vista.btnAddKitEducativo.addActionListener(listener);
        vista.btnModKitEducativo.addActionListener(listener);
        vista.btnDelKitEducativo.addActionListener(listener);

        vista.itemConectar.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);
    }

    private void addListSelectionListeners(ListSelectionListener listener) {
        vista.listProductos.addListSelectionListener(listener);
        vista.listEmpresa.addListSelectionListener(listener);
        vista.listKitEducativo.addListSelectionListener(listener);
    }

    private void addKeyListeners(KeyListener listener) {
        vista.txtBuscarProducto.addKeyListener(listener);
        vista.txtBuscarEmpresa.addKeyListener(listener);
        vista.txtBuscarKitEducativo.addKeyListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "conexion":
                try {
                    if (modelo.getCliente() == null) {
                        modelo.conectar();
                        vista.itemConectar.setText("Desconectar");
                        vista.setTitle("Kit Educativo <CONECTADO>");
                        setBotonesActivados(true);
                        listarProductos();
                        listarEmpresas();
                        listarKits();
                    } else {
                        modelo.desconectar();
                        vista.itemConectar.setText("Conectar");
                        vista.setTitle("Kit Educativo <SIN CONEXION>");
                        setBotonesActivados(false);
                        vista.dlmProductos.clear();
                        vista.dlmEmpresas.clear();
                        vista.dlmKits.clear();
                        limpiarCamposProducto();
                        limpiarCamposEmpresas();
                        limpiarCamposKits();
                    }
                } catch (Exception ex) {
                    Util.mostrarMensajeError("Imposible establecer conexión con el servidor.");
                }
                break;

            case "salir":
                modelo.desconectar();
                System.exit(0);
                break;

            case "addProducto":

                if (comprobarCamposProducto()) {
                    Producto p = new Producto();
                    rellenarProducto(p);
                    modelo.guardarObjeto(p);
                    limpiarCamposProducto();
                    resfrescarProductos();
                } else {
                    Util.mostrarMensajeError("No ha sido posible insertar datos de producto en la base de datos.\n" +
                            "Compruebe si faltan campos o se ha introducido un dato incorrecto.");
                }
                listarProductos();
                break;

            case "modProducto":
                if (vista.listProductos.getSelectedValue() != null) {
                    if (comprobarCamposProducto()) {
                        Producto producto = vista.listProductos.getSelectedValue();
                        rellenarProducto(producto);
                        modelo.modificarObjeto(producto);
                        limpiarCamposProducto();
                        resfrescarProductos();
                    } else {
                        Util.mostrarMensajeError("No ha sido posible modificar el producto en la base de datos.\n" +
                                "Compruebe si faltan campos o se ha introducido un dato incorrecto.");
                    }
                    listarProductos();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "delProducto":
                if (vista.listProductos.getSelectedValue() != null) {
                    if (modelo.productoEnUso(vista.listProductos.getSelectedValue().getId()))
                    {
                        Util.mostrarMensajeError("No se puede eliminar el producto porque se esta usando");
                    }
                    else{
                    modelo.eliminarObjeto(vista.listProductos.getSelectedValue());
                    listarProductos();
                    limpiarCamposProducto();
                    resfrescarProductos();
                    }
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "addEmpresa":
                if (comprobarCamposEmpresas()) {
                    Empresa em = new Empresa();
                    rellenarEmpresa(em);
                    modelo.guardarObjeto(em);
                    limpiarCamposEmpresas();
                    resfrecarEmpresa();
                } else {
                    Util.mostrarMensajeError("No ha sido posible insertar la empresa en la base de datos.\n" +
                            "Compruebe si faltan campos o se ha introducido un dato incorrecto.");
                }
                listarEmpresas();
                break;

            case "modEmpresa":
                if (vista.listEmpresa.getSelectedValue() != null) {
                    if (comprobarCamposEmpresas()) {
                        Empresa empresa = vista.listEmpresa.getSelectedValue();
                        rellenarEmpresa(empresa);
                        modelo.modificarObjeto(empresa);
                        limpiarCamposEmpresas();
                        resfrecarEmpresa();
                    } else {
                        Util.mostrarMensajeError("No ha sido posible modificar la empresa en la base de datos.\n" +
                                "Compruebe si faltan campos o se ha introducido un dato incorrecto.");
                    }
                    listarEmpresas();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "delEmpresa":
                if (vista.listEmpresa.getSelectedValue() != null) {
                    if (modelo.empresaEnUso(vista.listEmpresa.getSelectedValue().getId()))
                    {
                        Util.mostrarMensajeError("No se puede eliminar la empresa porque se esta usando");
                    }
                    else{
                    modelo.eliminarObjeto(vista.listEmpresa.getSelectedValue());
                    listarEmpresas();
                    limpiarCamposEmpresas();
                    resfrecarEmpresa();
                    }
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "addKit":
                if (comprobarCamposKits()) {
                    Kit_Educativo k = new Kit_Educativo();
                    rellenarKit(k);
                    modelo.guardarObjeto(k);
                    limpiarCamposKits();
                    resfrecarKits();
                } else {
                    Util.mostrarMensajeError("No ha sido posible insertar el kit en la base de datos.\n" +
                            "Compruebe si faltan campos o se ha introducido un dato incorrecto.");
                }
                listarKits();
                break;

            case "modKit":
                if (vista.listKitEducativo.getSelectedValue() != null) {
                    if (comprobarCamposKits()) {
                        Kit_Educativo kitEducativo = vista.listKitEducativo.getSelectedValue();
                        rellenarKit(kitEducativo);
                        modelo.modificarObjeto(kitEducativo);
                        limpiarCamposKits();
                        resfrecarKits();
                    } else {
                        Util.mostrarMensajeError("No ha sido posible modificar el kit en la base de datos.\n" +
                                "Compruebe si faltan campos o se ha introducido un dato incorrecto.");
                    }
                    listarKits();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "delKit":
                if (vista.listKitEducativo.getSelectedValue() != null) {
                    modelo.eliminarObjeto(vista.listKitEducativo.getSelectedValue());
                    listarKits();
                    limpiarCamposKits();
                    resfrecarKits();
                    break;
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
        }
    }

    private void rellenarProducto(Producto p) {
        p.setNombre(vista.txtNombreProducto.getText());
        p.setDescripcion(vista.txtDescripcionProducto.getText());
        p.setModelo(vista.txtModeloProducto.getText());
        p.setMarca(vista.txtMarcaProducto.getText());
        if (vista.usadoCheckBox.isSelected()) {
            p.setEstado(EstadoProducto.USADO);
        } else if (vista.nuevoCheckBox.isSelected()) {
            p.setEstado(EstadoProducto.NUEVO);
        } else {
            p.setEstado(EstadoProducto.REACONDICIONADO);
        }
    }


    private void rellenarEmpresa(Empresa e) {
        e.setNombre(vista.txtNombreEmpresa.getText());
        e.setDescripcion(vista.txtDescripcionEmpresa.getText());
        e.setFechaCreacion(vista.dateFechaDeCreacionEmpresa.getDate());
        e.setUbicacion(vista.txtUbicacionEmpresa.getText());
        e.setValoracion(vista.sliderValorcaion.getValue());

    }

    private void rellenarKit(Kit_Educativo k) {

        k.setNombre(vista.txtNombreKit.getText());
        k.setDescripcion(vista.txtDescripcionKit.getText());
        k.setCantidad(Integer.parseInt(vista.txtCantidadKit.getText()));

        k.setEmpresasKit(((Empresa) vista.comboBoxKitEmpresa.getSelectedItem()).getId());
        k.setProductoKit(((Producto) vista.comboKitProducto.getSelectedItem()).getId());

        k.setFechaCreacion(vista.dateFechaCreacionKit.getDate());
        k.setFechaActualizacion(vista.dateFechaActualizacionKit.getDate());

        k.setPrecio(Double.parseDouble(vista.txtPrecioKit.getText()));
        k.setValoracion(vista.sliderKitEducativo.getValue());


    }


    private void refrescarTodo() {
        resfrescarProductos();
        resfrecarEmpresa();
        resfrecarKits();
    }

    void resfrescarProductos() {


        vista.dlmProductos.clear();
        vista.comboKitProducto.removeAllItems();

        for (Producto p : modelo.getProductos()) {
            vista.dlmProductos.addElement(p);
            vista.comboKitProducto.addItem(p);
        }

    }

    void resfrecarEmpresa() {
        vista.dlmEmpresas.clear();
        vista.comboBoxKitEmpresa.removeAllItems();

        for (Empresa e : modelo.getEmpresas()) {
            vista.dlmEmpresas.addElement(e);
            vista.comboBoxKitEmpresa.addItem(e);
        }

    }

    void resfrecarKits() {
        vista.dlmKits.clear();

        for (Kit_Educativo e : modelo.getKits()) {
            vista.dlmKits.addElement(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.txtBuscarProducto) {
            listarProductosBusqueda(modelo.getProductos(vista.txtBuscarProducto.getText()));
            if (vista.txtBuscarProducto.getText().isEmpty()) {
                vista.dlmProductosBusqueda.clear();
            }
        } else if (e.getSource() == vista.txtBuscarEmpresa) {
            listarEmpresasBusqueda(modelo.getEmpresas(vista.txtBuscarEmpresa.getText()));
            if (vista.txtBuscarEmpresa.getText().isEmpty()) {
                vista.dlmEmpresasBusqueda.clear();
            }
        } else if (e.getSource() == vista.txtBuscarKitEducativo) {
            listarKitsBusqueda(modelo.getKits(vista.txtBuscarKitEducativo.getText()));
            if (vista.txtBuscarKitEducativo.getText().isEmpty()) {
                vista.dlmKitsBusqueda.clear();
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == vista.listProductos) {
            if (vista.listProductos.getSelectedValue() != null) {
                Producto producto = vista.listProductos.getSelectedValue();
                vista.txtNombreProducto.setText(producto.getNombre());
                vista.txtMarcaProducto.setText(producto.getMarca());
                vista.txtDescripcionProducto.setText(producto.getDescripcion());
                vista.txtModeloProducto.setText(producto.getModelo());
                if (producto.getEstado() == EstadoProducto.USADO) {
                    vista.usadoCheckBox.setSelected(true);
                } else if (producto.getEstado() == EstadoProducto.NUEVO) {
                    vista.nuevoCheckBox.setSelected(true);
                } else {
                    vista.reacondicionadoCheckBox.setSelected(true);
                }
            }
        } else if (e.getSource() == vista.listEmpresa) {
            if (vista.listEmpresa.getSelectedValue() != null) {
                Empresa empresa = vista.listEmpresa.getSelectedValue();
                vista.txtNombreEmpresa.setText(empresa.getNombre());
                vista.dateFechaDeCreacionEmpresa.setDate(empresa.getFechaCreacion());
                vista.txtDescripcionEmpresa.setText(empresa.getDescripcion());
                vista.txtUbicacionEmpresa.setText(empresa.getUbicacion());
                vista.sliderValorcaion.setValue(empresa.getValoracion());

            }
        } else if (e.getSource() == vista.listKitEducativo) {
            if (vista.listKitEducativo.getSelectedValue() != null) {
                Kit_Educativo k = vista.listKitEducativo.getSelectedValue();
                vista.txtNombreKit.setText(k.getNombre());
                vista.txtDescripcionKit.setText(k.getDescripcion());
                vista.txtCantidadKit.setText(String.valueOf(k.getCantidad()));
                ponKitEmpresa(k.getEmpresasKit());
                ponKitProducto(k.getProductoKit());
                vista.dateFechaCreacionKit.setDate(k.getFechaCreacion());
                vista.dateFechaActualizacionKit.setDate(k.getFechaActualizacion());
                vista.txtPrecioKit.setText(String.valueOf(k.getPrecio()));
                vista.sliderKitEducativo.setValue(k.getValoracion());

            }
        }
    }

    private void ponKitEmpresa(ObjectId empresasKit) {

        ComboBoxModel<Empresa> model = vista.comboBoxKitEmpresa.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).getId().equals(empresasKit)) {
                vista.comboBoxKitEmpresa.setSelectedIndex(i);
                return;
            }
        }

        vista.comboBoxKitEmpresa.setSelectedIndex(-1);
    }

    private void ponKitProducto(ObjectId productosKit) {

        ComboBoxModel<Producto> model = vista.comboKitProducto.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).getId().equals(productosKit)) {
                vista.comboKitProducto.setSelectedIndex(i);
                return;
            }
        }

        vista.comboKitProducto.setSelectedIndex(-1);
    }

    private boolean comprobarCamposProducto() {
        return !vista.txtNombreProducto.getText().isEmpty() &&
                !vista.txtMarcaProducto.getText().isEmpty() &&
                !vista.txtDescripcionProducto.getText().isEmpty() &&
                !vista.txtModeloProducto.getText().isEmpty();
    }

    private boolean comprobarCamposEmpresas() {
        return !vista.txtNombreEmpresa.getText().isEmpty() &&
                !vista.txtDescripcionEmpresa.getText().isEmpty() &&
                !vista.txtUbicacionEmpresa.getText().isEmpty() &&
                vista.sliderValorcaion.getValue() >= 0 &&
                !vista.dateFechaDeCreacionEmpresa.getText().isEmpty();
    }

    private boolean comprobarCamposKits() {
        return !vista.txtNombreKit.getText().isEmpty() &&
                !vista.txtDescripcionKit.getText().isEmpty() &&
                !vista.txtCantidadKit.getText().isEmpty() &&

                vista.comboBoxKitEmpresa.getSelectedIndex() >= 0 &&
                vista.comboKitProducto.getSelectedIndex() >= 0 &&

                !vista.dateFechaCreacionKit.getText().isEmpty() &&
                !vista.dateFechaActualizacionKit.getText().isEmpty() &&
                !vista.txtPrecioKit.getText().isEmpty() &&
                vista.sliderKitEducativo.getValue() >= 0;
    }

    private void limpiarCamposProducto() {
        vista.txtNombreProducto.setText("");
        vista.txtMarcaProducto.setText("");
        vista.txtDescripcionProducto.setText("");
        vista.txtModeloProducto.setText("");
        vista.txtBuscarProducto.setText("");
        vista.usadoCheckBox.setSelected(true);
    }

    private void limpiarCamposEmpresas() {
        vista.txtNombreEmpresa.setText("");
        vista.txtDescripcionEmpresa.setText("");
        vista.dateFechaDeCreacionEmpresa.clear();
        vista.txtBuscarEmpresa.setText("");
        vista.sliderValorcaion.setValue(0);
        vista.txtUbicacionEmpresa.setText("");

    }

    private void limpiarCamposKits() {


        vista.txtNombreKit.setText("");
        vista.txtDescripcionKit.setText("");
        vista.txtCantidadKit.setText("");
        vista.comboBoxKitEmpresa.setSelectedIndex(-1);
        vista.comboKitProducto.setSelectedIndex(-1);
        vista.dateFechaCreacionKit.setDate(null);
        vista.dateFechaActualizacionKit.setDate(null);
        vista.txtPrecioKit.setText("");
        vista.sliderKitEducativo.setValue(0);
        vista.txtBuscarKitEducativo.setText("");
    }


    private void listarProductos() {
        vista.dlmProductos.clear();
        for (Producto producto : modelo.getProductos()) {
            vista.dlmProductos.addElement(producto);
        }
    }

    private void listarEmpresas() {
        vista.dlmEmpresas.clear();
        for (Empresa empresa : modelo.getEmpresas()) {
            vista.dlmEmpresas.addElement(empresa);
        }
    }

    private void listarKits() {
        vista.dlmKits.clear();
        for (Kit_Educativo kitEducativo : modelo.getKits()) {
            vista.dlmKits.addElement(kitEducativo);
        }
    }

    private void listarProductosBusqueda(ArrayList<Producto> lista) {
        vista.dlmProductosBusqueda.clear();
        for (Producto producto : lista) {
            vista.dlmProductosBusqueda.addElement(producto);
        }
    }

    private void listarEmpresasBusqueda(ArrayList<Empresa> lista) {
        vista.dlmEmpresasBusqueda.clear();
        for (Empresa empresa : lista) {
            vista.dlmEmpresasBusqueda.addElement(empresa);
        }
    }

    private void listarKitsBusqueda(ArrayList<Kit_Educativo> lista) {
        vista.dlmKitsBusqueda.clear();
        for (Kit_Educativo kitEducativo : lista) {
            vista.dlmKitsBusqueda.addElement(kitEducativo);
        }
    }

    private void setBotonesActivados(boolean activados) {
        vista.btnAddProducto.setEnabled(activados);
        vista.btnModProducto.setEnabled(activados);
        vista.btnDelProducto.setEnabled(activados);
        vista.btnAddEmpresa.setEnabled(activados);
        vista.btnModEmpresa.setEnabled(activados);
        vista.btnDelEmpresa.setEnabled(activados);
        vista.btnAddKitEducativo.setEnabled(activados);
        vista.btnModKitEducativo.setEnabled(activados);
        vista.btnDelKitEducativo.setEnabled(activados);
    }

    // Métodos innecesarios
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}
