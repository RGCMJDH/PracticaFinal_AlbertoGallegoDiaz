/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal_albertogallegodiaz;

/**
 *
 * @author ramon
 */
public class Jugador {

    //datos
    private Cadena nombre;
    private int puntos;

    public Jugador(Cadena nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public void setNombre(Cadena nombre) {
        this.nombre = nombre;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Cadena getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }
    
    
}
