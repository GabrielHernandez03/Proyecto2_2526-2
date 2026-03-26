/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * nodo para la lista de usuarios del sistema
 * @author Gabriel
 */
public class NodoUsuario {
    Usuario valor;
    NodoUsuario siguiente;

    /**
     * constructor para crear el nodo del usuario
     * @param valor los datos del usuario
     */
    public NodoUsuario(Usuario valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}
