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
    
    public RegistroImpresion eliminar_min() {
        if (tamanoActual == 0) {
            return null;
        }
        RegistroImpresion min = monticulo[1];
        monticulo[1] = monticulo[tamanoActual];
        tamanoActual--;
        hundir(1);
        return min;
    }
    
    private void hundir(int posicion) {
        while (2 * posicion <= tamanoActual) {
            int hijo = 2 * posicion;
            if (hijo < tamanoActual && monticulo[hijo].etiquetaTiempo > monticulo[hijo + 1].etiquetaTiempo) {
                hijo++;
            }
            if (monticulo[posicion].etiquetaTiempo <= monticulo[hijo].etiquetaTiempo) {
                break;
            }
            intercambiar(posicion, hijo);
            posicion = hijo;
        }
    }
    
    public void alterarPrioridadAlMaximo(RegistroImpresion registro) {
        for (int i = 1; i <= tamanoActual; i++) {
            if (monticulo[i] == registro) {
                monticulo[i].etiquetaTiempo = -1;
                flotar(i);
                break;
            }
        }
    }
}
