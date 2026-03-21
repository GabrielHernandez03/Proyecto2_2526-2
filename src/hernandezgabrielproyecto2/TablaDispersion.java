/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 *
 * @author Gabriel
 */
public class TablaDispersion {
    private EntradaHash[] tabla;
    private int capacidad;

    public TablaDispersion(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new EntradaHash[capacidad];
    }
}
