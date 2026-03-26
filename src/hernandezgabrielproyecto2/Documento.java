/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * representa un documento que se quiere imprimir en el sistema
 * @author Gabriel
 */
public class Documento {
    String nombre;
    int tamano;
    String tipo;
    boolean enCola;

    /**
     * crea un nuevo documento con sus datos basicos
     * @param nombre el nombre del archivo
     * @param tamano que tan grande es el doc
     * @param tipo el formato del archivo
     */
    public Documento(String nombre, int tamano, String tipo) {
        this.nombre = nombre;
        this.tamano = tamano;
        this.tipo = tipo;
        this.enCola = false;
    }
}