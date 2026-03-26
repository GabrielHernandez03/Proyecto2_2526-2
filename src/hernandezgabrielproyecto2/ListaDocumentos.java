/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * lista enlazada simple para guardar barios documentos
 * @author Gabriel
 */
public class ListaDocumentos {
    NodoLista cabeza;

    /**
     * añade un documento al final de la lista
     * @param doc el objeto documento que se va a agregar
     */
    public void agregar(Documento doc) {
        NodoLista nuevo = new NodoLista(doc);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoLista actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }
}
