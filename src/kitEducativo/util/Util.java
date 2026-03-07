package kitEducativo.util;

import javax.swing.*;

/**
 * Clase que contiene métodos que pueden ser necesitados desde cualquier otra clase.
 *
 * @author José Adrián Palacios Romo
 */
public class Util {
    /**
     * Muestra el mensaje de error deseado en una ventana emergente.
     *
     * @param mensaje Representa el texto deseado para el mensaje de error.
     */
    public static void mostrarMensajeError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
