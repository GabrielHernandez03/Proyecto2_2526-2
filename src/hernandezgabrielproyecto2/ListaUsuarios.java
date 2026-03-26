/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * maneja el grupo de usuarios que estan registrados en la simulacion
 * @author Gabriel
 */
public class ListaUsuarios {
    NodoUsuario cabeza;
    
    /**
     * inicializa la lista de usuarios basia
     */
    public ListaUsuarios(){
        cabeza = null;
    }
    
    /**
     * mete a un usuario nuevo en la lista
     * @param user el usuario a registrar
     */
    public void agregar(Usuario user) {
        NodoUsuario nuevo = new NodoUsuario(user);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoUsuario actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = (nuevo);
        }
    }
    
    /**
     * saca a un usuario de la lista usando su nombre
     * @param nombre el identificador del usuario a borrar
     */
    public void eliminar(String nombre) {
        if (cabeza == null) return;
        if (cabeza.valor.nombre.equals(nombre)) {
            cabeza = cabeza.siguiente;
            return;
        }
        NodoUsuario actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.valor.nombre.equals(nombre)) {
            actual = actual.siguiente;
        }
        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
        }
    }
    
    /**
     * busca un usuario por su nombre para ver si existe
     * @param nombre el nombre que estamos buscando
     * @return el usuario si se encontro o null si no
     */
    public Usuario buscar(String nombre) {
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.valor.nombre.equals(nombre)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }
}
