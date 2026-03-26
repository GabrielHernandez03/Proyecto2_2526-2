/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * tabla hash para encontrar registros de impresion volando sin buscar uno por uno
 * @author Gabriel
 */
public class TablaDispersion {
    private EntradaHash[] tabla;
    private int capacidad;

    /**
     * inicializa la tabla con un tamaño que no cambie
     * @param capacidad cuantos huecos tiene la tabla
     */
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
    
    /**
     * mete un registro nuevo asociandolo al nombre del usuario
     * @param claveUsuario el dueño del documento
     * @param registro la info de la impresion
     */
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
    
    /**
     * busca un registro solo con el usuario
     * @param claveUsuario el nombre a buscar
     * @return el primer registro que aparesca
     */
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
    
    /**
     * busca un registro especifico usando usuario y nombre del doc
     * @param claveUsuario el dueño
     * @param nombreDoc como se llama el archivo
     * @return el registro exacto si lo encuentra
     */
    public RegistroImpresion obtener(String claveUsuario, String nombreDoc) {
        int indice = hash(claveUsuario);
        EntradaHash actual = tabla[indice];
        while (actual != null) {
            if (actual.clave.equals(claveUsuario) && 
                actual.valor.documento.nombre.equals(nombreDoc)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * borra un registro de la tabla buscando en todos los indices
     * @param registro el objeto registro que queremos sacar de la hash
     */
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