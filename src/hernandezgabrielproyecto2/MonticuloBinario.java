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
    
    public void insertar(RegistroImpresion registro) {
        if (tamanoActual >= capacidadMaxima) {
            return;
        }
        tamanoActual++;
        monticulo[tamanoActual] = registro;
        flotar(tamanoActual);
    }
    
    private void flotar(int posicion) {
        while (posicion > 1 && monticulo[posicion / 2].etiquetaTiempo > monticulo[posicion].etiquetaTiempo) {
            intercambiar(posicion, posicion / 2);
            posicion = posicion / 2;
        }
    }
    
    private void intercambiar(int i, int j) {
        RegistroImpresion temp = monticulo[i];
        monticulo[i] = monticulo[j];
        monticulo[j] = temp;
    }
}
