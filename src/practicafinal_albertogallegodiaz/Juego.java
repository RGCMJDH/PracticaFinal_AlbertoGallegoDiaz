/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal_albertogallegodiaz;

import java.util.Random;

public class Juego {

    private int opc;
    private int rondas;
    private FI fi;
    private LT tec = new LT();
    private Random rar;
    private Jugador j1;
    private Jugador j2;
    private Jugador CPU = new Jugador(new Cadena("CPU".toCharArray()), 0);

    public Juego(int rondas) {
        this.rondas = rondas;
    }

    public void logicaJuegoCPU(Jugador j1) {
        opc = 0;

        while (opc != rondas) { // Juego letras
            if (opc % 2 == 0) {
                jugarPalabras(j1, CPU);
            } else { // Juego numeros
                // pendiente implementar
            }
            opc++;
        }
    }

    private char[] buscarPalabras(Cadena idioma) {
        rar = new Random();
        char[] palabraSeleccionada = null;

        fi = new FI(idioma);
        fi.obrir();

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

        String palabra = "";
        if (compruebaPalabra(new Cadena(creada), letrasEscogidas, idioma)) {
            palabra = imprimeArray(creada);
            System.out.println(palabra);
            System.out.println("Puntos " + j1.getNombre().toString() + ": " + creada.length);
            j1.setPuntos(creada.length);
        } else {
            System.out.println("Palabra erronea");
            System.out.println("");
        }

        System.out.println("Turno de la CPU");
        char[] palabraCPU = palabrasCPU(letrasEscogidas, idioma);

        if (palabraCPU == null) {
            System.out.println("La CPU no ha encontrado ninguna palabra válida");
            System.out.println("");
        } else {
            if (compruebaPalabra(new Cadena(palabraCPU), letrasEscogidas, idioma)) {
                palabra = imprimeArray(palabraCPU);
                System.out.println(palabra);
                System.out.println("Puntos CPU: " + palabraCPU.length);
                CPU.setPuntos(palabraCPU.length);
            } else {
                System.out.println("Palabra erronea");
                System.out.println("");
            }
        }
    }

    private String imprimeArray(char[] p) {
        String res = "";
        for (int i = 0; i < p.length; i++) {
            res += p[i] + " ";
        }
        return res;
    }

   private boolean compruebaPalabra(Cadena palabraCreada, char[] letras, char[] idioma) {

    // 1) Si la palabra es más larga que el número de letras, imposible
    if (palabraCreada.getTam() > letras.length) {
        System.err.println("Palabra demasiado larga");
        return false;
    }

    // 2) Comprobar que se puede formar con 'letras'
    boolean[] usada = new boolean[letras.length];

    // Recorremos cada carácter de la palabra creada
    for (int i = 0; i < palabraCreada.getTam(); i++) {
        char c = palabraCreada.get(i);  // <- usamos Cadena, no String
        boolean encontrada = false;

        // Buscamos una letra igual en 'letras' que todavía no se haya usado
        for (int j = 0; j < letras.length && !encontrada; j++) {
            if (!usada[j] && letras[j] == c) {
                usada[j] = true;     // marcamos esa letra como usada
                encontrada = true;
            }
        }

        // Si no hemos encontrado ninguna posición libre con esa letra, no se puede formar
        if (!encontrada) {
            System.err.println("La palabra no se puede formar con las letras disponibles");
            return false;
        }
    }

    // 3) Si se puede formar, comprobamos si está en el diccionario

    fi = new FI(new Cadena(idioma));
    fi.obrir();

    Cadena palabraBuscada = fi.llegirLinia();  // primera lectura
    boolean encontrado = false;

    while (palabraBuscada != null && !encontrado) {
        if (palabraCreada.sonIguales(palabraBuscada)) {
            encontrado = true;
        } else {
            palabraBuscada = fi.llegirLinia();
        }
    }

    fi.tancar();
    return encontrado;
}


    private char[] palabrasCPU(char[] letras, char[] idioma) {
        char[] palabraCPU = busca(letras, idioma);
        return palabraCPU;
    }

    private char[] busca(char[] letras, char[] idioma) {
        fi = new FI(new Cadena(idioma));
        fi.obrir();

        char[] lineaF = fi.llegirLiniaArray();
        char[] mejor = null; 

        while (lineaF != null) {

            boolean sePuedeFormar = true;
            boolean[] usadas = new boolean[letras.length];

            for (int i = 0; i < lineaF.length && sePuedeFormar; i++) {
                char c = lineaF[i];
                boolean letraEncontrada = false;

                for (int j = 0; j < letras.length && !letraEncontrada; j++) {
                    if (!usadas[j] && letras[j] == c) {
                        usadas[j] = true;
                        letraEncontrada = true;
                    }
                }

                if (!letraEncontrada) {
                    sePuedeFormar = false;
                }
            }

            if (sePuedeFormar) {
                if (mejor == null || lineaF.length > mejor.length) {
                    mejor = lineaF; // nos quedamos con la más larga
                }
            }

            lineaF = fi.llegirLiniaArray();
        }

        fi.tancar();
        return mejor; // puede ser null si no hay ninguna palabra posible
    }

}
