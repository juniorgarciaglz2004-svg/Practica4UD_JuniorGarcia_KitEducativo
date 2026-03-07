package kitEducativo.gui;

import kitEducativo.datos.Empresa;
import kitEducativo.datos.EstadoProducto;
import kitEducativo.datos.Kit_Educativo;
import kitEducativo.datos.Producto;
import kitEducativo.util.Util;

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
            listarProductos();
            listarEmpresas();
            listarKits();
        } catch (Exception ex) {
            Util.mostrarMensajeError("Imposible establecer conexión con el servidor.");
        }
    }

    private void addActionListeners(ActionListener listener){
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

    private void addListSelectionListeners(ListSelectionListener listener){
        vista.listProductos.addListSelectionListener(listener);
        vista.listEmpresa.addListSelectionListener(listener);
        vista.listKitEducativo.addListSelectionListener(listener);
    }

    private void addKeyListeners(KeyListener listener){
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
                        vista.setTitle("<CONECTADO>");
                        setBotonesActivados(true);
                        listarProductos();
                        listarEmpresas();
                        listarKits();
                    } else {
                        modelo.desconectar();
                        vista.itemConectar.setText("Conectar");
                        vista.setTitle("<SIN CONEXION>");
                        setBotonesActivados(false);
                        vista.dlmProductos.clear();
                        vista.dlmEmpleados.clear();
                        vista.dlmDepartamentos.clear();
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
                } else {
                    Util.mostrarMensajeError("No ha sido posible insertar el producto en la base de datos.\n" +
                            "Compruebe que los campos contengan el tipo de dato requerido.");
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
                    } else {
                        Util.mostrarMensajeError("No ha sido posible modificar el producto en la base de datos.\n" +
                                "Compruebe que los campos contengan el tipo de dato requerido.");
                    }
                    listarProductos();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "delProducto":
                if (vista.listProductos.getSelectedValue() != null) {
                    modelo.eliminarObjeto(vista.listProductos.getSelectedValue());
                    listarProductos();
                    limpiarCamposProducto();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "addEmpleado":
                if (comprobarCamposEmpresas()) {
//                    modelo.guardarObjeto(new Empresa(vista.txtNombreEmpresa.getText(),
//                            vista.txtApellidosEmpleado.getText(),
//                            vista.dateFechaDeCreacionEmpresa.getDate()));
                    limpiarCamposEmpresas();
                } else {
                    Util.mostrarMensajeError("No ha sido posible insertar el empleado en la base de datos.\n" +
                            "Compruebe que los campos contengan el tipo de dato requerido.");
                }
                listarEmpresas();
                break;

            case "modEmpleado":
                if (vista.listEmpresa.getSelectedValue() != null) {
                    if (comprobarCamposEmpresas()) {
                        Empresa empresa = vista.listEmpresa.getSelectedValue();
                        empresa.setNombre(vista.txtNombreEmpresa.getText());
//                        empresa.setApellidos(vista.txtApellidosEmpleado.getText());
//                        empresa.setNacimiento(vista.dateFechaDeCreacionEmpresa.getDate());
                        modelo.modificarObjeto(empresa);
                        limpiarCamposEmpresas();
                    } else {
                        Util.mostrarMensajeError("No ha sido posible modificar el empleado en la base de datos.\n" +
                                "Compruebe que los campos contengan el tipo de dato requerido.");
                    }
                    listarEmpresas();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "delEmpleado":
                if (vista.listEmpresa.getSelectedValue() != null) {
                    modelo.eliminarObjeto(vista.listEmpresa.getSelectedValue());
                    listarEmpresas();
                    limpiarCamposEmpresas();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "addKits":
                if (comprobarCamposKits()) {
//                    modelo.guardarObjeto(new Kit_Educativo(vista.txtNombreKit.getText()));
                    limpiarCamposKits();
                } else {
                    Util.mostrarMensajeError("No ha sido posible insertar el departamento en la base de datos.\n" +
                            "Compruebe que los campos contengan el tipo de dato requerido.");
                }
                listarKits();
                break;

            case "modDepartamento":
                if (vista.listKitEducativo.getSelectedValue() != null) {
                    if (comprobarCamposKits()) {
                        Kit_Educativo kitEducativo = vista.listKitEducativo.getSelectedValue();
                        kitEducativo.setNombre(vista.txtNombreKit.getText());
                        modelo.modificarObjeto(kitEducativo);
                        limpiarCamposKits();
                    } else {
                        Util.mostrarMensajeError("No ha sido posible modificar el departamento en la base de datos.\n" +
                                "Compruebe que los campos contengan el tipo de dato requerido.");
                    }
                    listarKits();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "delDepartamento":
                if (vista.listKitEducativo.getSelectedValue() != null) {
                    modelo.eliminarObjeto(vista.listKitEducativo.getSelectedValue());
                    listarKits();
                    limpiarCamposKits();
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
        if (vista.usadoCheckBox.isSelected())
        {
            p.setEstado(EstadoProducto.USADO);
        }
        else if (vista.nuevoCheckBox.isSelected())
        {
            p.setEstado(EstadoProducto.NUEVO);
        }
        else{
            p.setEstado(EstadoProducto.REACONDICIONADO);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.txtBuscarProducto) {
//            listarProductosBusqueda(modelo.getProductos(vista.txtBuscarProducto.getText()));
            if (vista.txtBuscarProducto.getText().isEmpty()) {
                vista.dlmProductosBusqueda.clear();
            }
        } else if (e.getSource() == vista.txtBuscarEmpresa) {
//            listarEmpresasBusqueda(modelo.getEmpleados(vista.txtBuscarEmpresa.getText()));
            if (vista.txtBuscarEmpresa.getText().isEmpty()) {
                vista.dlmEmpleadosBusqueda.clear();
            }
        } else if (e.getSource() == vista.txtBuscarKitEducativo) {
//            listarKitsBusqueda(modelo.getDepartamentos(vista.txtBuscarKitEducativo.getText()));
            if (vista.txtBuscarKitEducativo.getText().isEmpty()) {
                vista.dlmDepartamentosBusqueda.clear();
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
                if (producto.getEstado()==EstadoProducto.USADO)
                {
                    vista.usadoCheckBox.setSelected(true);
                }
                else if (producto.getEstado()==EstadoProducto.NUEVO)
                {
                    vista.nuevoCheckBox.setSelected(true);
                }
                else{
                    vista.reacondicionadoCheckBox.setSelected(true);
                }
            }
        } else if (e.getSource() == vista.listEmpresa) {
            if (vista.listEmpresa.getSelectedValue() != null) {
                Empresa empresa = vista.listEmpresa.getSelectedValue();
                vista.txtNombreEmpresa.setText(empresa.getNombre());
//                vista.txtApellidosEmpleado.setText(empresa.getApellidos());
//                vista.dateFechaDeCreacionEmpresa.setDate(empresa.getNacimiento());
            }
        } else if (e.getSource() == vista.listKitEducativo) {
            if (vista.listKitEducativo.getSelectedValue() != null) {
                Kit_Educativo kitEducativo = vista.listKitEducativo.getSelectedValue();
//                vista.txtNombreKit.setText(kitEducativo.getDepartamento());
            }
        }
    }

    private boolean comprobarCamposProducto() {
        return !vista.txtNombreProducto.getText().isEmpty() &&
                !vista.txtMarcaProducto.getText().isEmpty() &&
                !vista.txtDescripcionProducto.getText().isEmpty() &&
                !vista.txtModeloProducto.getText().isEmpty();
    }

    private boolean comprobarCamposEmpresas() {
        return !vista.txtNombreEmpresa.getText().isEmpty() &&
                !vista.txtApellidosEmpleado.getText().isEmpty() &&
                !vista.dateFechaDeCreacionEmpresa.getText().isEmpty();
    }

    private boolean comprobarCamposKits() {
        return !vista.txtNombreKit.getText().isEmpty();
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
        vista.txtApellidosEmpleado.setText("");
        vista.dateFechaDeCreacionEmpresa.clear();
        vista.txtBuscarEmpresa.setText("");
    }

    private void limpiarCamposKits() {
        vista.txtNombreKit.setText("");
        vista.txtBuscarKitEducativo.setText("");
    }

    private boolean comprobarInt(String txt) {
        try {
            Integer.parseInt(txt);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean comprobarFloat(String txt) {
        try {
            Float.parseFloat(txt);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void listarProductos() {
        vista.dlmProductos.clear();
        for (Producto producto : modelo.getProductos()) {
            vista.dlmProductos.addElement(producto);
        }
    }

    private void listarEmpresas() {
        vista.dlmEmpleados.clear();
        for (Empresa empresa : modelo.getEmpresas()) {
            vista.dlmEmpleados.addElement(empresa);
        }
    }

    private void listarKits() {
        vista.dlmDepartamentos.clear();
        for (Kit_Educativo kitEducativo : modelo.getKits()) {
            vista.dlmDepartamentos.addElement(kitEducativo);
        }
    }

    private void listarProductosBusqueda(ArrayList<Producto> lista) {
        vista.dlmProductosBusqueda.clear();
        for (Producto producto : lista) {
            vista.dlmProductosBusqueda.addElement(producto);
        }
    }

    private void listarEmpresasBusqueda(ArrayList<Empresa> lista) {
        vista.dlmEmpleadosBusqueda.clear();
        for (Empresa empresa : lista) {
            vista.dlmEmpleadosBusqueda.addElement(empresa);
        }
    }

    private void listarKitsBusqueda(ArrayList<Kit_Educativo> lista) {
        vista.dlmDepartamentosBusqueda.clear();
        for (Kit_Educativo kitEducativo : lista) {
            vista.dlmDepartamentosBusqueda.addElement(kitEducativo);
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
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
}
