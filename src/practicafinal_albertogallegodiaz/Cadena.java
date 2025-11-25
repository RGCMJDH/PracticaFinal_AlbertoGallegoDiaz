/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicafinal_albertogallegodiaz;

/**
 *
 * @author mascport
 */
public class Cadena {

    private final int llarginicial = 1;
    private char[] pal;
    private int ind = 0;

    //////////////////CONSTRUCTORES///////////////////////
    public Cadena() {
        pal = new char[llarginicial];
        ind = 0;
        pal[ind] = ' ';
    }

    public Cadena(char c) {
        pal = new char[llarginicial];
        ind = 0;
        pal[ind] = c;
    }

    public Cadena(char[] p) {
        pal = new char[llarginicial];
        ind = 0;
        pal = p;
    }

    //////////////////////////////////////////////////////
    public char[] getPal() {
        return pal;
    }

    public void posar(char c) {
        if (ind == pal.length) {
            char[] aux = new char[pal.length + 1];
            for (int i = 0; i < pal.length; i++) {
                aux[i] = pal[i];
            }
            pal = aux;
        }

        pal[ind++] = c;
    }
    
    public char getPrimer() {
        return pal[0];
    }

    public void sustitueix(char c) {
        pal[0] = c;
    }

    public void append(Cadena p) {
        for (int i = 0; i < p.ind; i++) {
            this.posar(p.pal[i]);
        }
    }

    public boolean buida() {
        return (ind == 0);
    }

    public int getTam() {
        return ind;
    }

    public char get(int i) {
        return pal[i];
    }

    public int comptar(char c) {
        int res = 0;
        for (int i = 0; i < ind; i++) {
            if (pal[i] == c) {
                res++;
            }
        }
        return res;
    }
    
    public boolean sonIguales(Cadena p){
        boolean igual = true;
        if (pal.length != p.getPal().length) {
            return false;
        }
        
        for (int i = 0; i < pal.length; i++) {
            if (p.getPal()[i] != pal[i]) {
                return false;
            }
        }
        
        return igual;
    }
    
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < pal.length; i++) {
            res += pal[i];
        }
        return res;
    }

    public Integer toInt() {
        Integer res = null;
        boolean num = true;
        int negatiu = 0;
        if (buida()) {
            num = false;
        } else if (get(0) == '-') {
            negatiu = 1;
        }
        for (int i = negatiu; num && (i < getTam()); i++) {
            if ((pal[i] < '0') || (pal[i] > '9')) {
                num = false;
            }
        }
        if (num) {
            res = 0;
            for (int i = negatiu; i < getTam(); i++) {
                res = (res * 10) + (pal[i] - '0');
            }
        }
        if (res != null) {
            if (negatiu == 1) {
                res = -1 * res;
            }
        }
        return res;
    }

}
