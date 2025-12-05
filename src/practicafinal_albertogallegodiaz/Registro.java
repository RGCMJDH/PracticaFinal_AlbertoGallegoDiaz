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
    private FI fi;

    public Registro() {

    }

    public void guardarInfo(Cadena c) {
        fo = new FO(new Cadena("registro.txt".toCharArray()));
        fo.obrir(true); // No sobreescribe - > true
        fo.gravarLinia(c);
        fo.tancar();
    }

    public void leerEstadisticasGenerales() {
        fi = new FI(new Cadena("registro.txt".toCharArray()));
        fi.obrir();
        Cadena linea = fi.llegirLinia();
        if (linea == null) {
            System.out.println("Registro Vacio");
        }
        while(linea != null){
            System.out.println(linea.toString());
            linea = fi.llegirLinia();
        }
        
        fi.tancar();
    }
}
