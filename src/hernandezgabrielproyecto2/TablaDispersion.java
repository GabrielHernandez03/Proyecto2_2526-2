/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 *
 * @author Gabriel
 */
public class TablaDispersion {
    private EntradaHash[] tabla;
    private int capacidad;

    public TablaDispersion(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new EntradaHash[capacidad];
    }
    
    private int hash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (hash * 31 + clave.charAt(i)) % capacidad;
        }
        return Math.abs(hash);
    }
    
    public void insertar(String claveUsuario, RegistroImpresion registro) {
        int indice = hash(claveUsuario);
        EntradaHash nueva = new EntradaHash(claveUsuario, registro);
        if (tabla[indice] == null) {
            tabla[indice] = nueva;
        } else {
            EntradaHash actual = tabla[indice];
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nueva;
        }
    }
    
    public RegistroImpresion obtener(String claveUsuario) {
        int indice = hash(claveUsuario);
        EntradaHash actual = tabla[indice];
        while (actual != null) {
            if (actual.clave.equals(claveUsuario)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public void eliminarPorRegistro(RegistroImpresion registro) {
        for (int i = 0; i < capacidad; i++) {
            EntradaHash actual = tabla[i];
            EntradaHash previo = null;
            while (actual != null) {
                if (actual.valor == registro) {
                    if (previo == null) {
                        tabla[i] = actual.siguiente;
                    } else {
                        previo.siguiente = actual.siguiente;
                    }
                    return;
                }
                previo = actual;
                actual = actual.siguiente;
            }
        }
    }
}