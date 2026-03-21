/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 *
 * @author Gabriel
 */
public class EntradaHash {
    String clave;
    RegistroImpresion valor;
    EntradaHash siguiente;

    public EntradaHash(String clave, RegistroImpresion valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}
