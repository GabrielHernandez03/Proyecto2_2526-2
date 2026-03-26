/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * un nodo sensillo para la lista de documentos
 * @author Gabriel
 */
public class NodoLista {
    Documento valor;
    NodoLista siguiente;

    /**
     * crea el nodo con el documento adentro
     * @param valor el documento que se guarda aki
     */
    public NodoLista(Documento valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}