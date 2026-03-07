package kitEducativo.main;

import kitEducativo.gui.Controlador;
import kitEducativo.gui.Modelo;
import kitEducativo.gui.Vista;

public class Main {
    public static void main(String[] args) {
        new Controlador(new Modelo(), new Vista());
    }
}
