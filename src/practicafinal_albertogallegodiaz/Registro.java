/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal_albertogallegodiaz;

import java.util.Date;

/**
 *
 * @author ramon
 */
public class Registro {

    private FO fo;
    private FI fi;

    public Registro() {

    }

    private void guardarInfo(Cadena c) {
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
        while (linea != null) {
            System.out.println(linea.toString());
            linea = fi.llegirLinia();
        }

        fi.tancar();
    }

    public void guardarPartida(Date tiempoP, char[] tipoP, char[] nJ1, char[] nJ2, int rondas, int puntos1, int puntos2) {
        Cadena c = null;

        long ts = 0;
        if (tiempoP != null) {
            ts = tiempoP.getTime() / 1000; // timestamp en segundos
        }
        String tipo = new String(tipoP);
        String j1 = new String(nJ1);
        String j2 = new String(nJ2);

        c = new Cadena((String.valueOf(ts) + "#" + tipo + "#" + j1 + "#" + j2 + "#" + rondas + "#" + puntos1 + "#" + puntos2).toCharArray());

        guardarInfo(c);
    }
}
