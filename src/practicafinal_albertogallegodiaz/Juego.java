/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal_albertogallegodiaz;

import java.util.Random;

public class Juego {

    private Registro reg;
    private int opc;
    private int rondas;
    private FI fi;
    private LT tec = new LT();
    private Random rar = new Random();
    private Jugador j1;
    private Jugador j2;
    private Jugador CPU = new Jugador(new Cadena("CPU".toCharArray()), 0);

    public Juego(int rondas) {
        reg = new Registro();
        this.rondas = rondas;
    }

    public void logicaJuegoCPU(Jugador j1) {
        this.j1 = j1;
        opc = 0;

        int totalJugador = 0;
        int totalCPU = 0;

        // Cabecera de la partida en el registro
        reg.guardarInfo(new Cadena("############################".toCharArray()));
        reg.guardarInfo(new Cadena(("# Partida de " + imprimeArray(j1.getNombre().getPal()) + " vs CPU").toCharArray()));

        while (opc != rondas) {
            int numRonda = opc + 1;

            // Marcamos el inicio de la ronda en el registro
            reg.guardarInfo(new Cadena(("--- Ronda " + numRonda).toCharArray()));

            // Reseteo de puntos de ronda (mismos puntos para ambos juegos)
            j1.setPuntos(0);
            CPU.setPuntos(0);

            if (opc % 2 == 0) {
                // PAR -> juego de letras
                System.out.println(" - PALABRAS ");
                jugarPalabras(j1, CPU);
            } else {
                // IMPAR -> juego letras
                System.out.println(" - NUMEROS");
                jugarNumeros(j1, CPU);
            }

            // Sumamos puntuaciones de la ronda actual a los totales
            totalJugador += j1.getPuntos();
            totalCPU += CPU.getPuntos();

            // Guardamos puntuación acumulada en el registro
            reg.guardarInfo(new Cadena(("Puntuación acumulada " + imprimeNombre(j1.getNombre().getPal()) + ": " + totalJugador).toCharArray()));
            reg.guardarInfo(new Cadena(("Puntuación acumulada CPU: " + totalCPU).toCharArray()));

            // Quién va ganando tras esta ronda
            String ganadorParcial;
            if (totalJugador > totalCPU) {
                ganadorParcial = "Va ganando " + imprimeArray(j1.getNombre().getPal());
            } else if (totalCPU > totalJugador) {
                ganadorParcial = "Va ganando la CPU";
            } else {
                ganadorParcial = "De momento hay empate";
            }
            reg.guardarInfo(new Cadena(ganadorParcial.toCharArray()));

            opc++;
        }

        // Resultado final de la partida
        reg.guardarInfo(new Cadena(("Resultado final " + imprimeNombre(j1.getNombre().getPal()) + ": " + totalJugador + " puntos").toCharArray()));
        reg.guardarInfo(new Cadena(("Resultado final CPU: " + totalCPU + " puntos").toCharArray()));

        String ganadorFinal;
        if (totalJugador > totalCPU) {
            ganadorFinal = "Ganador final: " + imprimeNombre(j1.getNombre().getPal());
        } else if (totalCPU > totalJugador) {
            ganadorFinal = "Ganador final: CPU";
        } else {
            ganadorFinal = "La partida ha terminado en EMPATE";
        }
        reg.guardarInfo(new Cadena(ganadorFinal.toCharArray()));
        reg.guardarInfo(new Cadena("############################".toCharArray()));
    }

    private void jugarNumeros(Jugador j1, Jugador j2) {
        char[] nombreJ1 = j1.getNombre().getPal();
        char[] nombreCPU = CPU.getNombre().getPal();

        int objetivo = rar.nextInt(100, 999);
        System.out.println("Te dare unos numeros y tu deberas acercarte lo maximo o llegar al siguiente numeros");
        System.out.println("Objetivo " + objetivo);

        char[] numeros = numerosAleatorios();
        String res = imprimeArray(numeros);
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

            System.out.print("Dame otro numero del array: ");
            int n2 = tec.llegirSencer();

            while (!perteneceNum(n2, numeros) || n1 == n2 || n2 == 0) {
                System.err.println("El numero no pertenece al array... Dame otro");
                n2 = tec.llegirSencer();
            }
            
            // Una vez hecha las comprobaciones
            

        }

    }
    
    private void quitar(int[] p, int quitar, int quitar2, int añadir){
        for (int i = 0; i < p.length; i++) {
            if (p[i] == quitar || p[i] == quitar2) {
                p[i] = 0;
            }
        }

        System.out.print("Dame un numero del array: ");
        char n1 = tec.llegirCaracter();

        while (!perteneceNum(n1, numeros)) {
            System.err.println("El numero no pertenece al array... Dame otro");
            n1 = tec.llegirCaracter();
        }

        System.out.print("Dame otro numero del array: ");
        char n2 = tec.llegirCaracter();

        while (!perteneceNum(n2, numeros) && n1 != n2) {
            System.err.println("El numero no pertenece al array... Dame otro");
            n2 = tec.llegirCaracter();
        }
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

    private String imprimeArrayN(int[] p) {
        String res = "";
        for (int i = 0; i < p.length; i++) {
            res += p[i] + " ";
        }
        return res;
    }

    private char[] numerosAleatorios() {
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
                valor = valor * 10 + (c - '0'); // <- ASCII aquí
                inNum = true;
            } else if (inNum) {
                opciones[idx++] = valor;
                valor = 0;
                inNum = false;
            }
        }

        // Por si la línea acaba en número
        if (inNum) {
            opciones[idx] = valor;
        }

        // 3) Elegir 6 números al azar
        int[] creados = new int[20];
=======
        char[] opciones = fi.llegirLiniaArray();
        //System.out.println(imprimeArray(opciones)); Coge lo que toca y lo imprime bien
        fi.tancar();

        char[] creados = new char[20];
>>>>>>> 8952eefc99e7511c5ad0169881520fd8fba97cff
        for (int i = 0; i < 6; i++) {
            creados[i] = opciones[rar.nextInt(opciones.length)];
        }

        return creados;
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
                reg.guardarInfo(new Cadena("Idioma elegido no válido en esta ronda".toCharArray()));
                return;
        }

        char[] letrasEscogidas = devuelveLetras(letras);
        String util = imprimeArray(letrasEscogidas);
        System.out.println(util);

        // Guardamos en el registro las letras de esta ronda
        reg.guardarInfo(new Cadena(("Letras de la ronda: " + util).toCharArray()));

        System.out.println("Dame una palabra que contenga las letras enseñadas");
        char[] creada = tec.llegirLiniaC();

        String palabraJugador = "";
        if (compruebaPalabra(new Cadena(creada), letrasEscogidas, idioma)) {
            palabraJugador = imprimeArray(creada);
            System.out.println(palabraJugador);
            System.out.println("Puntos " + imprimeNombre(nombreJ1) + ": " + creada.length);
            j1.setPuntos(creada.length);

            // Guardamos jugada del jugador
            reg.guardarInfo(new Cadena(("Palabra de " + imprimeNombre(nombreJ1) + ": " + palabraJugador + " (" + creada.length + " puntos)").toCharArray()));
        } else {
            System.out.println("Palabra errónea");
            System.out.println("");
            reg.guardarInfo(new Cadena(("Palabra de " + imprimeNombre(nombreJ1) + " NO válida: " + imprimeArray(creada)).toCharArray()));
        }

        System.out.println("Turno de la CPU");
        char[] palabraCPU = palabrasCPU(letrasEscogidas, idioma);

        if (palabraCPU == null) {
            System.out.println("La CPU no ha encontrado ninguna palabra válida");
            System.out.println("");
            reg.guardarInfo(new Cadena("La CPU no ha encontrado ninguna palabra válida".toCharArray()));
        } else {
            if (compruebaPalabra(new Cadena(palabraCPU), letrasEscogidas, idioma)) {
                String palabraCPUString = imprimeArray(palabraCPU);
                System.out.println(palabraCPUString);
                System.out.println("Puntos CPU: " + palabraCPU.length);
                CPU.setPuntos(palabraCPU.length);

                // Guardamos jugada de la CPU
                reg.guardarInfo(new Cadena(("Palabra de CPU: " + palabraCPUString + " (" + palabraCPU.length + " puntos)").toCharArray()));
            } else {
                System.out.println("Palabra errónea");
                System.out.println("");
                reg.guardarInfo(new Cadena("Palabra propuesta por la CPU NO válida".toCharArray()));
            }
        }

        // Puntos de la ronda en el registro
        reg.guardarInfo(new Cadena(("Puntos " + imprimeNombre(nombreJ1) + " (ronda): " + j1.getPuntos()).toCharArray()));
        reg.guardarInfo(new Cadena(("Puntos CPU (ronda): " + CPU.getPuntos()).toCharArray()));

        // Ganador de la ronda en el registro
        if (j1.getPuntos() > CPU.getPuntos()) {
            reg.guardarInfo(new Cadena(("Ganador de la ronda: " + imprimeNombre(nombreJ1)).toCharArray()));
        } else if (CPU.getPuntos() > j1.getPuntos()) {
            reg.guardarInfo(new Cadena("Ganador de la ronda: CPU".toCharArray()));
        } else {
            reg.guardarInfo(new Cadena("Ronda empatada".toCharArray()));
        }
    }

    private char[] devuelveLetras(char[] letrasI) {
        //rar = new Random();
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

        // 1) Si la palabra es más larga que el número de letras, imposible
        if (palabraCreada.getTam() > letras.length) {
            System.err.println("Palabra demasiado larga");
            return false;
        }

        // 2) Comprobar que se puede formar con 'letras'
        boolean[] usada = new boolean[letras.length];

        // Recorremos cada carácter de la palabra creada
        for (int i = 0; i < palabraCreada.getTam(); i++) {
            char c = palabraCreada.get(i);
            boolean encontrada = false;

            // Buscamos una letra igual en 'letras' que todavía no se haya usado
            for (int j = 0; j < letras.length && !encontrada; j++) {
                if (!usada[j] && letras[j] == c) {
                    usada[j] = true;
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
