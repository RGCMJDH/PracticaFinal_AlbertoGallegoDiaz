/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal_albertogallegodiaz;

/**
 *
 * @author ramon
 */
public class Juego {

    private int opc;
    private int rondas;
    
    
    
    public Juego(int rondas) {
        this.rondas = rondas;
        
    }

    public void logicaJuegoCPU(Jugador j1) {
        opc = 0;
        Jugador CPU = new Jugador("CPU".toCharArray(), 0);
        
        while (opc != rondas) {//Juego letras
            if (opc % 2 == 0) {
                jugarPalabras(j1,CPU);
            }else{//Juego numeros
                
            }
            
            opc++;
        }
    }
    
    
    private void jugarPalabras(Jugador j1, Jugador j2){
       
    }
    
    
    
    
    
    
    
    
}