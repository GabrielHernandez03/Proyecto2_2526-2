/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 *
 * @author Gabriel
 */
public class NodoUsuario {
    Usuario valor;
    NodoUsuario siguiente;

    public NodoUsuario(Usuario valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}
