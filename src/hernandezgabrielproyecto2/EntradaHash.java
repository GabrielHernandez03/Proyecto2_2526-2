/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * es un nodo para la tabla de dispersion que guarda los registros
 * @author Gabriel
 */
public class EntradaHash {
    String clave;
    RegistroImpresion valor;
    EntradaHash siguiente;

    /**
     * constructor para meter una nueva entrada en el hash
     * @param clave el nombre del usuario para buscarlo
     * @param valor la informacion del registro de impresion
     */
    public EntradaHash(String clave, RegistroImpresion valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}
