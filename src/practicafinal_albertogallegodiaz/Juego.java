/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal_albertogallegodiaz;

import java.util.Date;
import java.util.Random;

public class Juego {

    private int opc;
    private int rondas;
    private FI fi;
    private LT tec = new LT();
    private Random rar = new Random();
    private Jugador j1;
    private Jugador j2;
    private Jugador CPU = new Jugador(new Cadena("CPU".toCharArray()), 0);
    private Registro reg = new Registro();
    private char[] vsCPU = "vs cpu".toCharArray();
    private char[] vsHumano = "vs humano".toCharArray();

    public Juego(int rondas) {
        this.rondas = rondas;
    }

    public void logicaJuegoCPU(Jugador j1) {
        this.j1 = j1;
        opc = 0;

        int totalJugador = 0;
        int totalCPU = 0;
        Date inicio = new Date();

        while (opc != rondas) {

            // Reseteo de puntos de ronda
            j1.setPuntos(0);
            CPU.setPuntos(0);

            if (opc % 2 == 0) {
                System.out.println(" - PALABRAS ");
                jugarPalabras(j1, CPU);
            } else {
                System.out.println(" - NUMEROS");
                jugarNumeros(j1, CPU);
            }

            totalJugador += j1.getPuntos();
            totalCPU += CPU.getPuntos();

            opc++;
        }

        reg.guardarPartida(inicio, vsCPU, j1.getNombre().getPal(), CPU.getNombre().getPal(),
                rondas, totalJugador, totalCPU);
    }

    private void jugarNumeros(Jugador j1, Jugador j2) {
        char[] nombreJ1 = j1.getNombre().getPal();
        char[] nombreCPU = CPU.getNombre().getPal();

        int objetivo = rar.nextInt(100, 999);
        System.out.println("Te dare unos numeros y tu deberas acercarte lo maximo o llegar al siguiente numeros");
        System.out.println("Objetivo " + objetivo);

        int[] numeros = numerosAleatorios();
        String res = imprimeArrayN(numeros);
        System.out.println(res);

        System.out.println("Comienza el juego");

        while (true) {

         

            System.out.println("num = -1, para acabar la partida");

            System.out.print("Dame un numero del array: ");
            int n1 = tec.llegirSencer();
            if (n1 == -1) {
                break;
            }

            while (!perteneceNum(n1, numeros) || n1 == 0) {
                System.err.println("El numero no pertenece al array... Dame otro");
                n1 = tec.llegirSencer();
            }

            System.out.println("Que operacion quieres hacer");
            System.out.print(" +, -, *, / :");
            char opc = tec.llegirCaracter();

            while (opc != '+' && opc != '-' && opc != '*' && opc != '/') {
                System.err.println("Operacion no valida");
                opc = tec.llegirCaracter();
            }

            System.out.print("Dame otro numero del array: ");
            int n2 = tec.llegirSencer();

            // Permitir n1 == n2 solo si hay al menos 2 apariciones
            while (!perteneceNum(n2, numeros) || n2 == 0 ||
                   (n1 == n2 && cuentaNum(n1, numeros) < 2)) {
                System.err.println("El numero no pertenece al array... Dame otro");
                n2 = tec.llegirSencer();
            }

            int resultado = 0;
            switch (opc) {
                case '+':
                    resultado = n1 + n2;
                    break;

                case '-':
                    if (!operacion(n1, opc, n2)) {
                        System.out.println("No valida");
                        resultado = 0;
                    } else {
                        resultado = n1 - n2;
                    }
                    break;

                case '*':
                    resultado = n1 * n2;
                    break;

                case '/':
                    if (!operacion(n1, opc, n2)) {
                        System.out.println("No valida");
                        resultado = 0;
                    } else {
                        resultado = n1 / n2;
                    }
                    break;

                default:
                    System.out.println("Operacion no valida");
                    resultado = 0;
                    break;
            }

            quitar(numeros, n1, n2, resultado);

            // Imprimir cambios tras la operación
            res = imprimeArrayN(numeros);
            System.out.println(res);
        }
    }

    private boolean operacion(int n1, char op, int n2) {
        switch (op) {
            case '+':
                return true;

            case '-':
                return (n1 - n2) >= 0;

            case '*':
                return true;

            case '/':
                return n1 >= n2 && (n1 % n2 == 0);

            default:
                return false;
        }
    }

    private void quitar(int[] p, int quitar1, int quitar2, int anadir) {

        // Quitar UNA ocurrencia de cada número (o DOS si son iguales)
        if (quitar1 == quitar2) {
            int quitadas = 0;
            for (int i = 0; i < p.length && quitadas < 2; i++) {
                if (p[i] == quitar1) {
                    p[i] = 0;
                    quitadas++;
                }
            }
        } else {
            boolean q1 = false, q2 = false;

            for (int i = 0; i < p.length; i++) {
                if (!q1 && p[i] == quitar1) {
                    p[i] = 0;
                    q1 = true;
                } else if (!q2 && p[i] == quitar2) {
                    p[i] = 0;
                    q2 = true;
                }

                if (q1 && q2) {
                    break;
                }
            }
        }

        // Añadir el resultado al FINAL (después del último no-cero)
        if (anadir != 0) {
            int last = -1;

            for (int i = 0; i < p.length; i++) {
                if (p[i] != 0) {
                    last = i;
                }
            }

            if (last + 1 < p.length) {
                p[last + 1] = anadir;
            }
        }
    }

    private int[] numerosAleatorios() {
        fi = new FI(new Cadena("files/cifras.txt".toCharArray()));
        fi.obrir();
        char[] linea = fi.llegirLiniaArray();
        fi.tancar();

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

        // 2) Parsear números usando ASCII
        int[] opciones = new int[count];
        int idx = 0;
        int valor = 0;
        inNum = false;

        for (int i = 0; i < linea.length; i++) {
            char c = linea[i];
            boolean esDigito = (c >= '0' && c <= '9');

            if (esDigito) {
                valor = valor * 10 + (c - '0');
                inNum = true;
            } else if (inNum) {
                opciones[idx++] = valor;
                valor = 0;
                inNum = false;
            }
        }

        if (inNum && idx < opciones.length) {
            opciones[idx] = valor;
        }

        int[] creados = new int[20];
        for (int i = 0; i < 6; i++) {
            creados[i] = opciones[rar.nextInt(opciones.length)];
        }

        return creados;
    }

    private boolean perteneceNum(int n, int[] numeros) {
        boolean esta = false;
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == n) {
                return true;
            }
        }
        return esta;
    }

    private int cuentaNum(int n, int[] numeros) {
        int c = 0;
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == n) {
                c++;
            }
        }
        return c;
    }

    private String imprimeArrayN(int[] p) {
        String res = "";
        for (int i = 0; i < p.length; i++) {
            res += p[i] + " ";
        }
        return res;
    }

    //  --------------------------------------------------
    private void jugarPalabras(Jugador j1, Jugador j2) {

        char[] nombreJ1 = j1.getNombre().getPal();
        char[] nombreCPU = CPU.getNombre().getPal();

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

        String palabraJugador = "";
        if (compruebaPalabra(new Cadena(creada), letrasEscogidas, idioma)) {
            palabraJugador = imprimeArray(creada);
            System.out.println(palabraJugador);
            System.out.println("Puntos " + imprimeNombre(nombreJ1) + ": " + creada.length);
            j1.setPuntos(creada.length);

        } else {
            System.out.println("Palabra errónea");
            System.out.println("");
        }

        System.out.println("Turno de la CPU");
        char[] palabraCPU = palabrasCPU(letrasEscogidas, idioma);

        if (palabraCPU == null) {
            System.out.println("La CPU no ha encontrado ninguna palabra válida");
            System.out.println("");
        } else {
            if (compruebaPalabra(new Cadena(palabraCPU), letrasEscogidas, idioma)) {
                String palabraCPUString = imprimeArray(palabraCPU);
                System.out.println(palabraCPUString);
                System.out.println("Puntos CPU: " + palabraCPU.length);
                CPU.setPuntos(palabraCPU.length);

            } else {
                System.out.println("Palabra errónea");
                System.out.println("");
            }
        }
    }

    private char[] devuelveLetras(char[] letrasI) {
        fi = new FI(new Cadena(letrasI));
        fi.obrir();

        char[] letras = new char[rar.nextInt(10) + 1];
        char[] opciones = fi.llegirLiniaArray();

        for (int i = 0; i < letras.length; i++) {
            letras[i] = opciones[rar.nextInt(opciones.length)];
        }

        fi.tancar();
        return letras;
    }

    private String imprimeNombre(char[] p) {
        String res = "";
        for (int i = 0; i < p.length; i++) {
            res += p[i];
        }
        return res;
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

        boolean[] usada = new boolean[letras.length];

        for (int i = 0; i < palabraCreada.getTam(); i++) {
            char c = palabraCreada.get(i);
            boolean encontrada = false;

            for (int j = 0; j < letras.length && !encontrada; j++) {
                if (!usada[j] && letras[j] == c) {
                    usada[j] = true;
                    encontrada = true;
                }
            }

            if (!encontrada) {
                System.err.println("La palabra no se puede formar con las letras disponibles");
                return false;
            }
        }

        fi = new FI(new Cadena(idioma));
        fi.obrir();

        Cadena palabraBuscada = fi.llegirLinia();
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
                    mejor = lineaF;
                }
            }

            lineaF = fi.llegirLiniaArray();
        }

        fi.tancar();
        return mejor;
    }

}
