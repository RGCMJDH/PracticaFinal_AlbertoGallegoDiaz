/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicafinal_albertogallegodiaz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mascport
 */
public class FI {

    private final Cadena nom;
    private FileReader fic;
    private BufferedReader reader;

    public FI(Cadena n) {
        nom = n;
    }

    public void obrir() {
        try {
            fic = new FileReader(nom.toString());
            reader = new BufferedReader(fic);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Cadena llegirLinia() {
        char[] res = null;
        try {
            String fr = reader.readLine();
            if (fr != null) {
                res = fr.toCharArray();
            }
        } catch (Exception ex) {
            Logger.getLogger(FI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Cadena cad = null;
        if (res != null) {
            cad = new Cadena();
            for (int i = 0; i < res.length; i++) {
                cad.posar(res[i]);
            }
        }
        return cad;
    }

    public int[] leerInts() {
    int[] res = new int[0];

    try {
        String fr = reader.readLine();
        if (fr == null) {
            return res;
        }

        // Permitido por el enunciado
        char[] linea = fr.toCharArray();

        // 1) Contar cuántos números hay
        int count = 0;
        boolean inNum = false;

        for (int i = 0; i < linea.length; i++) {
            char c = linea[i];
            boolean esDigito = (c >= '0' && c <= '9');

            if (esDigito) {
                if (!inNum) {
                    count++;
                    inNum = true;
                }
            } else {
                inNum = false;
            }
        }

        // 2) Crear array resultado
        res = new int[count];

        // 3) Parsear números (incluye 10, 25, 50, 75, 100)
        int idx = 0;
        int valor = 0;
        inNum = false;

        for (int i = 0; i < linea.length; i++) {
            char c = linea[i];
            boolean esDigito = (c >= '0' && c <= '9');

            if (esDigito) {
                valor = valor * 10 + (c - '0');
                inNum = true;
            } else {
                if (inNum) {
                    res[idx] = valor;
                    idx++;
                    valor = 0;
                    inNum = false;
                }
            }
        }

        // Si la línea acaba en número sin separador
        if (inNum && idx < res.length) {
            res[idx] = valor;
        }

    } catch (Exception ex) {
        Logger.getLogger(FI.class.getName()).log(Level.SEVERE, null, ex);
    }

    return res;
}


    public char[] llegirLiniaArray() {
        char[] res = null;
        try {
            String fr = reader.readLine();
            if (fr != null) {
                res = fr.toCharArray();
            }
        } catch (Exception ex) {
            Logger.getLogger(FI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public void tancar() {
        try {
            reader.close();
            fic.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fic.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
