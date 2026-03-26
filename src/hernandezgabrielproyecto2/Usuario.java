/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * guarda los datos de una persona y sus documentos personales
 * @author Gabriel
 */
public class Usuario {
    String nombre;
    String tipo;
    ListaDocumentos documentos;

    /**
     * crea un usuario y le prepara su lista de archivos basia
     * @param nombre como se llama el usuario
     * @param tipo que nivel de prioridad tiene
     */
    public Usuario(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.documentos = new ListaDocumentos();
    }
}
