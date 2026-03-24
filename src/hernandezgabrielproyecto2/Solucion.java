/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hernandezgabrielproyecto2;

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
}
