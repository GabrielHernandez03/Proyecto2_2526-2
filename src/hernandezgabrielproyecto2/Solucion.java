/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;

/**
 * esta es la clase maestra que controla toda la operasion de la impresora
 * @author Gabriel
 */
public class Solucion {
    private MonticuloBinario colaImpresion;
    private TablaDispersion tablaUsuariosRegistros;
    private ListaUsuarios usuariosRegistrados;
    private long tiempoInicioNano; 
    private final long NANOS_POR_SEGUNDO = 1_000_000_000L;

    /**
     * prepara todo el sistema y empiesa a contar el tiempo real
     */
    public Solucion() {
        this.colaImpresion = new MonticuloBinario(100);
        this.tablaUsuariosRegistros = new TablaDispersion(100);
        this.usuariosRegistrados = new ListaUsuarios();
        this.tiempoInicioNano = System.nanoTime();
    }
    
    /**
     * nos da los segundos que han pasado desde que el programa arranco
     * @return tiempo trascurrido en enteros
     */
    public int getRelojActual() {
        long diferenciaNanos = System.nanoTime() - tiempoInicioNano;
        return (int) (diferenciaNanos / NANOS_POR_SEGUNDO);
    }

    public void avanzarReloj() {
    }
    
    /**
     * busca el doc de un usuario y lo mete a la cola con su prioridad si es nesesario
     * @param nombreUsuario quien lo manda
     * @param nombreDoc como se llama el archivo
     * @param esPrioritario si queremos que use su beneficio de tipo de usuario
     */
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

        int etiqueta = getRelojActual();
        
        if (esPrioritario) {
            if (user.tipo.equals("prioridad_alta")) {
                etiqueta -= 20; 
            } else if (user.tipo.equals("prioridad_media")) {
                etiqueta -= 10;
            }
        }
        
        RegistroImpresion registro = new RegistroImpresion(docAImprimir, etiqueta);
        docAImprimir.enCola = true;
        colaImpresion.insertar(registro);
        tablaUsuariosRegistros.insertar(user.nombre, registro);
    }
    
    /**
     * crea un usuario nuevo si no existe ya
     * @param nombre el nombre del compa
     * @param tipo si es de alta, media o baja prioridad
     */
    public void agregarUsuario(String nombre, String tipo) {
        if (usuariosRegistrados.buscar(nombre) == null) {
            usuariosRegistrados.agregar(new Usuario(nombre, tipo));
        }
    }
    
    /**
     * lee un archivo de texto para meter varios usuarios de un solo golpe
     * @param rutaArchivo donde esta el csv guardado
     */
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
                if (doc.nombre.equals(nombreDoc)) {
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

    /**
     * saca el primer documento de la cola y lo marca como ya impreso
     */
    public void liberarImpresora() {
        RegistroImpresion impreso = colaImpresion.eliminar_min();
        if (impreso != null) {
            impreso.documento.enCola = false; 
            tablaUsuariosRegistros.eliminarPorRegistro(impreso);
        }
    }
    
    /**
     * quita un archivo de la cola de espera usando la tabla hash para ayarlo rapido
     * @param nombreUsuario el dueño del documento
     * @param nombreDoc el nombre del archivo a sacar
     */
    public void eliminarDocumentoDeCola(String nombreUsuario, String nombreDoc) {
        RegistroImpresion registro = tablaUsuariosRegistros.obtener(nombreUsuario, nombreDoc);
        
        if (registro != null) {
            colaImpresion.alterarPrioridadAlMaximo(registro);
            RegistroImpresion eliminado = colaImpresion.eliminar_min();
            
            if (eliminado != null) {
                eliminado.documento.enCola = false;
                tablaUsuariosRegistros.eliminarPorRegistro(eliminado);
                System.out.println("Documento '" + nombreDoc + "' del usuario " + nombreUsuario + " eliminado de la cola.");
            }
        } else {
            System.out.println("No se encontró el documento en la cola de impresión.");
        }
    }
    
    public void mostrarUsuariosYDocumentos() {
        NodoUsuario actual = usuariosRegistrados.cabeza;
        while (actual != null) {
            System.out.println("Usuario: " + actual.valor.nombre + " [" + actual.valor.tipo + "]");
            NodoLista nodoDoc = actual.valor.documentos.cabeza;
            while (nodoDoc != null) {
                Documento doc = (Documento) nodoDoc.valor;
                String estado = doc.enCola ? "En Cola" : "No Encolado";
                System.out.println("  - Doc: " + doc.nombre + " (" + estado + ")");
                nodoDoc = nodoDoc.siguiente;
            }
            actual = actual.siguiente;
        }
    }
    
    public void mostrarColaSecuencia() {
        RegistroImpresion[] arreglo = colaImpresion.obtenerArregloInterno();
        int tamano = colaImpresion.obtenerTamano();
        for (int i = 1; i <= tamano; i++) {
            System.out.println("Doc: " + arreglo[i].documento.nombre + " | Etiqueta: " + arreglo[i].etiquetaTiempo);
        }
    }

    public void mostrarColaArbol() {
        RegistroImpresion[] arreglo = colaImpresion.obtenerArregloInterno();
        int tamano = colaImpresion.obtenerTamano();
        if (tamano > 0) {
            mostrarNodoArbol(arreglo, 1, "", true, tamano);
        }
    }

    private void mostrarNodoArbol(RegistroImpresion[] arreglo, int indice, String prefijo, boolean esIzquierdo, int tamano) {
        if (indice <= tamano) {
            System.out.println(prefijo + (esIzquierdo ? "├── " : "└── ") + arreglo[indice].documento.nombre + " (" + arreglo[indice].etiquetaTiempo + ")");
            mostrarNodoArbol(arreglo, 2 * indice, prefijo + (esIzquierdo ? "│   " : "     "), true, tamano);
            mostrarNodoArbol(arreglo, 2 * indice + 1, prefijo + (esIzquierdo ? "│   " : "     "), false, tamano);
        }
    }
    
    public String[] obtenerNombresUsuarios() {
        int contador = 0;
        NodoUsuario actual = usuariosRegistrados.cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }

        String[] nombres = new String[contador];
        actual = usuariosRegistrados.cabeza;
        for (int i = 0; i < contador; i++) {
            nombres[i] = actual.valor.nombre;
            actual = actual.siguiente;
        }
        return nombres;
    }
    
    public String[] obtenerNombresDocumentosUsuario(String nombreUsuario) {
        Usuario user = usuariosRegistrados.buscar(nombreUsuario);
        if (user == null) return new String[0];

        int contador = 0;
        NodoLista actual = user.documentos.cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }

        String[] nombres = new String[contador];
        actual = user.documentos.cabeza;
        for (int i = 0; i < contador; i++) {
            nombres[i] = actual.valor.nombre;
            actual = actual.siguiente;
        }
        return nombres;
    }
    
    /**
     * arma un string con la lista de docs que estan esperando turno
     * @return un reporte con el orden de la cola
     */
    public String obtenerColaImpresionTexto() {
        RegistroImpresion[] arreglo = colaImpresion.obtenerArregloInterno();
        int tamano = colaImpresion.obtenerTamano();
        if (tamano == 0) return "La cola está vacía.";

        StringBuilder sb = new StringBuilder();
        sb.append("ORDEN DE IMPRESIÓN:\n");
        for (int i = 1; i <= tamano; i++) {
            sb.append(i).append(". ").append(arreglo[i].documento.nombre)
              .append(" (Prioridad: ").append(arreglo[i].etiquetaTiempo).append(")\n");
        }
        return sb.toString();
    }
    
    /**
     * genera un reporte visual de todos los usuarios y sus archivos
     * @return el reporte completo formateado
     */
    public String obtenerReporteUsuariosYDocs() {
        StringBuilder sb = new StringBuilder();
        NodoUsuario actual = usuariosRegistrados.cabeza;
        while (actual != null) {
            sb.append("Usuario: ").append(actual.valor.nombre)
              .append(" [").append(actual.valor.tipo).append("]\n");
            
            NodoLista nodoDoc = actual.valor.documentos.cabeza;
            if (nodoDoc == null) sb.append("  (Sin documentos)\n");
            
            while (nodoDoc != null) {
                Documento doc = (Documento) nodoDoc.valor;
                String estado = doc.enCola ? "[EN COLA]" : "[NO ENCOLADO]";
                sb.append("  - ").append(doc.nombre).append(" ").append(estado).append("\n");
                nodoDoc = nodoDoc.siguiente;
            }
            sb.append("----------------------------\n");
            actual = actual.siguiente;
        }
        return sb.toString();
    }
    
    public String obtenerDetallesDocumento(String nombreUsuario, String nombreDoc) {
        Usuario user = usuariosRegistrados.buscar(nombreUsuario);
        if (user == null) return "Usuario no encontrado";

        NodoLista actual = user.documentos.cabeza;
        while (actual != null) {
            Documento d = (Documento) actual.valor;
            if (d.nombre.equals(nombreDoc)) {
                return "Nombre: " + d.nombre + "\n" +
                       "Tamaño: " + d.tamano + " KB\n" +
                       "Tipo: " + d.tipo + "\n" +
                       "Estado: " + (d.enCola ? "Enviado a imprimir" : "En espera");
            }
            actual = actual.siguiente;
        }
        return "Documento no encontrado";
    }
    
    public String obtenerDetallesTodosDocumentosUsuario(String nombreUsuario) {
        Usuario user = usuariosRegistrados.buscar(nombreUsuario);
        if (user == null) {
            return "Usuario no encontrado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("DOCUMENTOS DE: ").append(user.nombre.toUpperCase()).append("\n");
        sb.append("========================================\n");

        NodoLista actual = user.documentos.cabeza;
        if (actual == null) {
            sb.append("El usuario no tiene documentos registrados.");
        } else {
            while (actual != null) {
                Documento doc = (Documento) actual.valor;
                sb.append("Archivo: ").append(doc.nombre).append("\n");
                sb.append("  > Tamaño: ").append(doc.tamano).append(" KB\n");
                sb.append("  > Formato: ").append(doc.tipo).append("\n");
                
                String estado = doc.enCola ? "EN COLA DE IMPRESIÓN" : "DISPONIBLE (No encolado)";
                sb.append("  > Estado: ").append(estado).append("\n");
                sb.append("----------------------------------------\n");
                
                actual = actual.siguiente;
            }
        }
        return sb.toString();
    }
    
    public String[] obtenerNombresDocsEnCola() {
        RegistroImpresion[] arreglo = colaImpresion.obtenerArregloInterno();
        int tamano = colaImpresion.obtenerTamano();
        
        if (tamano == 0) {
            return new String[0];
        }

        String[] nombres = new String[tamano];

        for (int i = 1; i <= tamano; i++) {
            nombres[i - 1] = arreglo[i].documento.nombre;
        }

        return nombres;
    }
    
    /**
     * usa la libreria graphstream para dibujar el monticulo como un arbol real
     */
    public void graficarColaArbol() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Cola de Impresion");

        String css = "node { " +
                     "   fill-color: #3498db; " +
                     "   text-color: #333; " +
                     "   text-size: 14px; " +
                     "   text-alignment: under; " +
                     "   size: 25px; " +
                     "} " +
                     "edge { " +
                     "   fill-color: #ACB9CA; " +
                     "   size: 2px; " +
                     "}";
        graph.setAttribute("ui.stylesheet", css);

        RegistroImpresion[] arreglo = colaImpresion.obtenerArregloInterno();
        int tamano = colaImpresion.obtenerTamano();

        if (tamano == 0) {
            System.out.println("La cola está vacía, nada que graficar.");
            return;
        }

        for (int i = 1; i <= tamano; i++) {
            String id = String.valueOf(i);
            Node n = graph.addNode(id);
            String etiqueta = arreglo[i].documento.nombre + " (T:" + arreglo[i].etiquetaTiempo + ")";
            n.setAttribute("ui.label", etiqueta);
        }

        for (int i = 1; i <= tamano; i++) {
            int izq = 2 * i;
            int der = 2 * i + 1;

            if (izq <= tamano) {
                graph.addEdge("E_I_" + i, String.valueOf(i), String.valueOf(izq));
            }
            if (der <= tamano) {
                graph.addEdge("E_D_" + i, String.valueOf(i), String.valueOf(der));
            }
        }

        Viewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        viewer.addDefaultView(true); 
    }
}