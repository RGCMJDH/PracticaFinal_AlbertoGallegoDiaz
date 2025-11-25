/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal_albertogallegodiaz;

import java.util.Random;

/**
 *
 * @author ramon
 */
public class Juego {

    private int opc;
    private int rondas;
    private FI fi;
    private LT tec = new LT();
    private Random rar;

    public Juego(int rondas) {
        this.rondas = rondas;

    }

    public void logicaJuegoCPU(Jugador j1) {
        opc = 0;
        Jugador CPU = new Jugador(new Cadena("CPU".toCharArray()), 0);

        while (opc != rondas) {//Juego letras
            if (opc % 2 == 0) {
                jugarPalabras(j1, CPU);
            } else {//Juego numeros

            }

            opc++;
        }
    }

    private char[] buscarPalabras(Cadena idioma) {
        rar = new Random();
        char[] palabraSeleccionada = null;

        fi = new FI(idioma);
        fi.obrir(); // <- imprescindible

        int numR = rar.nextInt(100); 
        int i = 0;
        char[] linea;

        while (i <= numR && (linea = fi.llegirLiniaArray()) != null) {
            palabraSeleccionada = linea;  // nos quedamos con la última leída
            i++;
        }

        fi.tancar();

        return palabraSeleccionada;
    }

    private char[] devuelveLetras(char[] letrasI) {
        rar = new Random();
        fi = new FI(new Cadena(letrasI));
        fi.obrir();

        char[] letras = new char[rar.nextInt(12) + 1];
        char[] opciones = fi.llegirLiniaArray();

        for (int i = 0; i < letras.length; i++) {
            letras[i] = opciones[rar.nextInt(opciones.length)];
        }

        fi.tancar();
        return letras;
    }

    private void jugarPalabras(Jugador j1, Jugador j2) {
        System.out.println("Idioma??");
        System.out.println("Catalan: (1), Castellano: (2), Ingles: (3)");
        int opc = tec.llegirSencer();
        char[] idioma = null, letras = null;

        switch (opc) {
            case 1:
                idioma = "files/dic_ca.txt".toCharArray();
                letras = "files/letras_ca.txt".toCharArray();
                break;
            case 2:
                idioma = "files/dic_es.txt".toCharArray();
                letras = "files/letras_es.txt".toCharArray();
                break;
            case 3:
                idioma = "files/dic_en.txt".toCharArray();
                letras = "files/letras_en.txt".toCharArray();
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }

        char[] letrasEscogidas = devuelveLetras(letras);
        String util = imprimeArray(letrasEscogidas);
        System.out.println(util);

        System.out.println("Dame una palabra que contenga las letras enseñadas");
        char[] creada = tec.llegirLiniaC();
        if (compruebaPalabra(new Cadena(creada), letrasEscogidas, idioma)) {
            String palabra = imprimeArray(creada);
            System.out.println(palabra);
            System.out.println("Puntos: " + creada.length);
            j1.setPuntos(creada.length);
        } else {
            System.out.println("Palabra erronea");
            System.out.println("");
        }

        System.out.println("Turno de la CPU");

    }

    private String imprimeArray(char[] p) {
        String res = "";
        for (int i = 0; i < p.length; i++) {
            res += p[i] + " ";
        }
        return res;
    }

    private boolean compruebaPalabra(Cadena palabraCreada, char[] letras, char[] idioma) {
        if (palabraCreada.getTam() > letras.length) {
            System.err.println("Palabra demasiado larga");
            return false;
        }

        fi = new FI(new Cadena(idioma));
        fi.obrir();

        Cadena palabraBuscada = fi.llegirLinia();  // primera lectura
        boolean encontrado = false;

        while (palabraBuscada != null && !encontrado) {
            String prueba1 = imprimeArray(palabraCreada.getPal());
            String prueba2 = imprimeArray(palabraBuscada.getPal());
//            System.out.println("P1: " + prueba1);
//            System.out.println("P2: " + prueba2);
            
            if (palabraCreada.sonIguales(palabraBuscada)) {
                encontrado = true;
            }

            if (!encontrado) {                     // solo leo si aún no la he encontrado
                palabraBuscada = fi.llegirLinia();
            }
        }

        return encontrado;
    }

}
