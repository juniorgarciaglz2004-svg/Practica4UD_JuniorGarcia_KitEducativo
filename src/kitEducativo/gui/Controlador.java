package kitEducativo.gui;

import kitEducativo.datos.Empresa;
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
            vista.setTitle("Bar Manolo - <CONECTADO>");
            setBotonesActivados(true);
            listarProductos();
            listarEmpleados();
            listarDepartamentos();
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
                        vista.setTitle("Bar Manolo - <CONECTADO>");
                        setBotonesActivados(true);
                        listarProductos();
                        listarEmpleados();
                        listarDepartamentos();
                    } else {
                        modelo.desconectar();
                        vista.itemConectar.setText("Conectar");
                        vista.setTitle("Bar Manolo - <SIN CONEXION>");
                        setBotonesActivados(false);
                        vista.dlmProductos.clear();
                        vista.dlmEmpleados.clear();
                        vista.dlmDepartamentos.clear();
                        limpiarCamposProducto();
                        limpiarCamposEmpleado();
                        limpiarCamposDepartamento();
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
                    modelo.guardarObjeto(new Producto(vista.txtMarcaProducto.getText(),
                            Integer.parseInt(vista.txtDescripcionProducto.getText()),
                            Float.parseFloat(vista.txtModeloProducto.getText())));
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
                        producto.setNombre(vista.txtMarcaProducto.getText());
                        producto.setGrados(Integer.parseInt(vista.txtDescripcionProducto.getText()));
                        producto.setPrecio(Float.parseFloat(vista.txtModeloProducto.getText()));
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
                if (comprobarCamposEmpleado()) {
                    modelo.guardarObjeto(new Empresa(vista.txtNombreEmpresa.getText(),
                            vista.txtApellidosEmpleado.getText(),
                            vista.dateFechaDeCreacionEmpresa.getDate()));
                    limpiarCamposEmpleado();
                } else {
                    Util.mostrarMensajeError("No ha sido posible insertar el empleado en la base de datos.\n" +
                            "Compruebe que los campos contengan el tipo de dato requerido.");
                }
                listarEmpleados();
                break;

            case "modEmpleado":
                if (vista.listEmpresa.getSelectedValue() != null) {
                    if (comprobarCamposEmpleado()) {
                        Empresa empresa = vista.listEmpresa.getSelectedValue();
                        empresa.setNombre(vista.txtNombreEmpresa.getText());
                        empresa.setApellidos(vista.txtApellidosEmpleado.getText());
                        empresa.setNacimiento(vista.dateFechaDeCreacionEmpresa.getDate());
                        modelo.modificarObjeto(empresa);
                        limpiarCamposEmpleado();
                    } else {
                        Util.mostrarMensajeError("No ha sido posible modificar el empleado en la base de datos.\n" +
                                "Compruebe que los campos contengan el tipo de dato requerido.");
                    }
                    listarEmpleados();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "delEmpleado":
                if (vista.listEmpresa.getSelectedValue() != null) {
                    modelo.eliminarObjeto(vista.listEmpresa.getSelectedValue());
                    listarEmpleados();
                    limpiarCamposEmpleado();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "addDepartamento":
                if (comprobarCamposDepartamento()) {
                    modelo.guardarObjeto(new Kit_Educativo(vista.txtNombreKit.getText()));
                    limpiarCamposDepartamento();
                } else {
                    Util.mostrarMensajeError("No ha sido posible insertar el departamento en la base de datos.\n" +
                            "Compruebe que los campos contengan el tipo de dato requerido.");
                }
                listarDepartamentos();
                break;

            case "modDepartamento":
                if (vista.listKitEducativo.getSelectedValue() != null) {
                    if (comprobarCamposDepartamento()) {
                        Kit_Educativo kitEducativo = vista.listKitEducativo.getSelectedValue();
                        kitEducativo.setDepartamento(vista.txtNombreKit.getText());
                        modelo.modificarObjeto(kitEducativo);
                        limpiarCamposDepartamento();
                    } else {
                        Util.mostrarMensajeError("No ha sido posible modificar el departamento en la base de datos.\n" +
                                "Compruebe que los campos contengan el tipo de dato requerido.");
                    }
                    listarDepartamentos();
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
                break;

            case "delDepartamento":
                if (vista.listKitEducativo.getSelectedValue() != null) {
                    modelo.eliminarObjeto(vista.listKitEducativo.getSelectedValue());
                    listarDepartamentos();
                    limpiarCamposDepartamento();
                    break;
                } else {
                    Util.mostrarMensajeError("No hay ningún elemento seleccionado.");
                }
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
            listarEmpleadosBusqueda(modelo.getEmpleados(vista.txtBuscarEmpresa.getText()));
            if (vista.txtBuscarEmpresa.getText().isEmpty()) {
                vista.dlmEmpleadosBusqueda.clear();
            }
        } else if (e.getSource() == vista.txtBuscarKitEducativo) {
            listarDepartamentosBusqueda(modelo.getDepartamentos(vista.txtBuscarKitEducativo.getText()));
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
                vista.txtMarcaProducto.setText(producto.getNombre());
                vista.txtDescripcionProducto.setText(String.valueOf(producto.getGrados()));
                vista.txtModeloProducto.setText(String.valueOf(producto.getPrecio()));
            }
        } else if (e.getSource() == vista.listEmpresa) {
            if (vista.listEmpresa.getSelectedValue() != null) {
                Empresa empresa = vista.listEmpresa.getSelectedValue();
                vista.txtNombreEmpresa.setText(empresa.getNombre());
                vista.txtApellidosEmpleado.setText(empresa.getApellidos());
                vista.dateFechaDeCreacionEmpresa.setDate(empresa.getNacimiento());
            }
        } else if (e.getSource() == vista.listKitEducativo) {
            if (vista.listKitEducativo.getSelectedValue() != null) {
                Kit_Educativo kitEducativo = vista.listKitEducativo.getSelectedValue();
                vista.txtNombreKit.setText(kitEducativo.getDepartamento());
            }
        }
    }

    private boolean comprobarCamposProducto() {
        return !vista.txtMarcaProducto.getText().isEmpty() &&
                !vista.txtDescripcionProducto.getText().isEmpty() &&
                !vista.txtModeloProducto.getText().isEmpty() &&
                comprobarInt(vista.txtDescripcionProducto.getText()) &&
                comprobarFloat(vista.txtModeloProducto.getText());
    }

    private boolean comprobarCamposEmpleado() {
        return !vista.txtNombreEmpresa.getText().isEmpty() &&
                !vista.txtApellidosEmpleado.getText().isEmpty() &&
                !vista.dateFechaDeCreacionEmpresa.getText().isEmpty();
    }

    private boolean comprobarCamposDepartamento() {
        return !vista.txtNombreKit.getText().isEmpty();
    }

    private void limpiarCamposProducto() {
        vista.txtMarcaProducto.setText("");
        vista.txtDescripcionProducto.setText("");
        vista.txtModeloProducto.setText("");
        vista.txtBuscarProducto.setText("");
    }

    private void limpiarCamposEmpleado() {
        vista.txtNombreEmpresa.setText("");
        vista.txtApellidosEmpleado.setText("");
        vista.dateFechaDeCreacionEmpresa.clear();
        vista.txtBuscarEmpresa.setText("");
    }

    private void limpiarCamposDepartamento() {
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

    private void listarEmpleados() {
        vista.dlmEmpleados.clear();
        for (Empresa empresa : modelo.getEmpleados()) {
            vista.dlmEmpleados.addElement(empresa);
        }
    }

    private void listarDepartamentos() {
        vista.dlmDepartamentos.clear();
        for (Kit_Educativo kitEducativo : modelo.getDepartamentos()) {
            vista.dlmDepartamentos.addElement(kitEducativo);
        }
    }

    private void listarProductosBusqueda(ArrayList<Producto> lista) {
        vista.dlmProductosBusqueda.clear();
        for (Producto producto : lista) {
            vista.dlmProductosBusqueda.addElement(producto);
        }
    }

    private void listarEmpleadosBusqueda(ArrayList<Empresa> lista) {
        vista.dlmEmpleadosBusqueda.clear();
        for (Empresa empresa : lista) {
            vista.dlmEmpleadosBusqueda.addElement(empresa);
        }
    }

    private void listarDepartamentosBusqueda(ArrayList<Kit_Educativo> lista) {
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
