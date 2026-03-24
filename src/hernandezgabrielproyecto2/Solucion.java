/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Gabriel
 */
public class Solucion {
    private MonticuloBinario colaImpresion;
    private TablaDispersion tablaUsuariosRegistros;
    private ListaUsuarios usuariosRegistrados;
    private int relojSimulacion;

    public Solucion() {
        this.colaImpresion = new MonticuloBinario(100);
        this.tablaUsuariosRegistros = new TablaDispersion(100);
        this.usuariosRegistrados = new ListaUsuarios();
        this.relojSimulacion = 0;
    }
    
    public void avanzarReloj() {
        this.relojSimulacion++;
    }
    
    public void agregarUsuario(String nombre, String tipo) {
        if (usuariosRegistrados.buscar(nombre) == null) {
            usuariosRegistrados.agregar(new Usuario(nombre, tipo));
        }
    }
    
    public void cargarUsuariosCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    agregarUsuario(datos[0].trim(), datos[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    
    public void eliminarUsuario(String nombre) {
        usuariosRegistrados.eliminar(nombre);
    }
    
    public void crearDocumentoParaUsuario(String nombreUsuario, String nombreDoc, int tamano, String tipoDoc) {
        Usuario user = usuariosRegistrados.buscar(nombreUsuario);
        if (user != null) {
            user.documentos.agregar(new Documento(nombreDoc, tamano, tipoDoc));
        }
    }
    
    public void eliminarDocumentoNoEncolado(String nombreUsuario, String nombreDoc) {
        Usuario user = usuariosRegistrados.buscar(nombreUsuario);
        if (user != null) {
            NodoLista actual = user.documentos.cabeza;
            NodoLista previo = null;
            while (actual != null) {
                Documento doc = (Documento) actual.valor;
                if (doc.nombre.equals(nombreDoc) && !doc.enCola) {
                    if (previo == null) {
                        user.documentos.cabeza = actual.siguiente;
                    } else {
                        previo.siguiente = actual.siguiente;
                    }
                    break;
                }
                previo = actual;
                actual = actual.siguiente;
            }
        }
    }
    
    public void enviarAImprimir(String nombreUsuario, String nombreDoc, boolean esPrioritario) {
        Usuario user = usuariosRegistrados.buscar(nombreUsuario);
        if (user == null) return;

        Documento docAImprimir = null;
        NodoLista actual = user.documentos.cabeza;
        while (actual != null) {
            Documento doc = (Documento) actual.valor;
            if (doc.nombre.equals(nombreDoc) && !doc.enCola) {
                docAImprimir = doc;
                break;
            }
            actual = actual.siguiente;
        }

        if (docAImprimir == null) return;

        int etiqueta = relojSimulacion;
        if (esPrioritario) {
            if (user.tipo.equals("prioridad_alta")) {
                etiqueta = Math.max(0, etiqueta - 10);
            } else if (user.tipo.equals("prioridad_media")) {
                etiqueta = Math.max(0, etiqueta - 5);
            }
        }
        
        RegistroImpresion registro = new RegistroImpresion(docAImprimir, etiqueta);
        docAImprimir.enCola = true;
        colaImpresion.insertar(registro);
        tablaUsuariosRegistros.insertar(user.nombre, registro);
    }
}
