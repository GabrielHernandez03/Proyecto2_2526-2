/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 *
 * @author Gabriel
 */
public class MonticuloBinario {
    
    private RegistroImpresion[] monticulo;
    private int tamanoActual;
    private int capacidadMaxima;

    public MonticuloBinario(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.tamanoActual = 0;
        this.monticulo = new RegistroImpresion[this.capacidadMaxima + 1];
    }
}
