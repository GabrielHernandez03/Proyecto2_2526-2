/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

/**
 * un simple empaque que junta un documento con su etiqueta de tiempo
 * @author Gabriel
 */
public class RegistroImpresion {
    Documento documento;
    int etiquetaTiempo;

    /**
     * crea el registro para mandarlo a la cola
     * @param documento el archivo que se va a imprimir
     * @param etiquetaTiempo el numero de prioridad segun el reloj
     */
    public RegistroImpresion(Documento documento, int etiquetaTiempo) {
        this.documento = documento;
        this.etiquetaTiempo = etiquetaTiempo;
    }
}
