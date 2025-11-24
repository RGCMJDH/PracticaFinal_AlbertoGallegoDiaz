/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal_albertogallegodiaz;

/**
 *
 * @author ramon
 */
public class Registro {
    
    private FO fo;
    
    public Registro(){
    
    }
    
    public void guardarInfo(Cadena c){
        fo = new FO(new Cadena("registro.txt".toCharArray()));
        fo.obrir(true); // No sobreescribe
        fo.gravarLinia(c);
        fo.tancar();
    }
}
