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

    // --------------------------------------------------
    //  MODO 2 JUGADORES
    // --------------------------------------------------
    public void logicaJuegoJJ(Jugador j1, Jugador j2) {
        this.j1 = j1;
        this.j2 = j2;
        opc = 0;

        int totalJugador1 = 0;
        int totalJugador2 = 0;
        Date inicio = new Date();

        while (opc != rondas) {

            j1.setPuntos(0);
            j2.setPuntos(0);

            if (opc % 2 == 0) {
                System.out.println(" - PALABRAS ");
                jugarPalabrasJJ(j1, j2);
            } else {
                System.out.println(" - NUMEROS");
                jugarNumerosJJ(j1, j2);
            }

            totalJugador1 += j1.getPuntos();
            totalJugador2 += j2.getPuntos();

            opc++;
        }

        reg.guardarPartida(inicio, vsHumano, j1.getNombre().getPal(), j2.getNombre().getPal(),
                rondas, totalJugador1, totalJugador2);
    }

    // --------------------------------------------------
    //  MODO VS CPU
    // --------------------------------------------------
    public void logicaJuegoCPU(Jugador j1) {
        this.j1 = j1;
        opc = 0;

        int totalJugador = 0;
        int totalCPU = 0;
        Date inicio = new Date();

        while (opc != rondas) {

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

    // ==================================================
    //  CIFRAS VS CPU (tu lógica + consola más limpia)
    // ==================================================
    private void jugarNumeros(Jugador j1, Jugador j2) {

        int objetivo = rar.nextInt(100, 999);
        System.out.println("Objetivo: " + objetivo);

        int[] base = numerosAleatorios();
        System.out.println("Números: " + imprimeArrayN(base));
        System.out.println("Turno jugador (-1 para acabar)");

        int[] numerosJ = copiaArray(base);
        int[] numerosC = copiaArray(base);

        // -------- TURNO JUGADOR --------
        while (true) {

            System.out.println("Objetivo: " + objetivo);
            System.out.print("n1: ");
            int n1 = tec.llegirSencer();
            if (n1 == -1) {
                System.out.println("Fin turno jugador.");
                break;
            }

            while (!perteneceNum(n1, numerosJ) || n1 == 0) {
                System.err.print("n1 inválido: ");
                n1 = tec.llegirSencer();
            }

            System.out.print("op: ");
            char op = tec.llegirCaracter();
            while (op != '+' && op != '-' && op != '*' && op != '/') {
                System.err.print("op inválida: ");
                op = tec.llegirCaracter();
            }

            System.out.print("n2: ");
            int n2 = tec.llegirSencer();

            while (!perteneceNum(n2, numerosJ) || n2 == 0
                    || (n1 == n2 && cuentaNum(n1, numerosJ) < 2)) {
                System.err.print("n2 inválido: ");
                n2 = tec.llegirSencer();
            }

            int res = 0;
            boolean ok = true;

            switch (op) {
                case '+':
                    res = n1 + n2;
                    quitar(numerosJ, n1, n2, res);
                    break;
                case '-':
                    if (!operacion(n1, op, n2)) {
                        ok = false;
                    } else {
                        res = n1 - n2;
                        quitar(numerosJ, n1, n2, res);
                    }
                    break;
                case '*':
                    res = n1 * n2;
                    quitar(numerosJ, n1, n2, res);
                    break;
                case '/':
                    if (!operacion(n1, op, n2)) {
                        ok = false;
                    } else {
                        res = n1 / n2;
                        quitar(numerosJ, n1, n2, res);
                    }
                    break;
            }

            if (ok) {
                System.out.println("= " + res);
            } else {
                System.err.println("Operación no válida");
            }

            System.out.println("Números: " + imprimeArrayN(numerosJ));
        }

        j1.setPuntos(evaluaFinal(numerosJ, objetivo));

        // -------- TURNO CPU --------
        System.out.println("Turno CPU");
        jugarNumerosCPUSimple(numerosC, objetivo);
        j2.setPuntos(evaluaFinalCPU(numerosC, objetivo));
    }

    // ==================================================
    //  CIFRAS 2 JUGADORES
    //  - J2 tiene otra secuencia de números
    //  - Objetivo visible en cada iteración
    //  - Consola más limpia
    // ==================================================
    private void jugarNumerosJJ(Jugador j1, Jugador j2) {

        int objetivo = rar.nextInt(100, 999);
        System.out.println("Objetivo: " + objetivo);

        int[] numerosJ1 = numerosAleatorios();
        int[] numerosJ2 = numerosAleatorios();

        System.out.println("Números " + imprimeNombre(j1.getNombre().getPal()) + ": " + imprimeArrayN(numerosJ1));
        System.out.println("Números " + imprimeNombre(j2.getNombre().getPal()) + ": " + imprimeArrayN(numerosJ2));

        // -------- TURNO J1 --------
        System.out.println("Turno " + imprimeNombre(j1.getNombre().getPal()) + " (-1 para acabar)");

        while (true) {

            System.out.println("Objetivo: " + objetivo);
            System.out.print("n1: ");
            int n1 = tec.llegirSencer();
            if (n1 == -1) {
                break;
            }

            while (!perteneceNum(n1, numerosJ1) || n1 == 0) {
                System.err.print("n1 inválido: ");
                n1 = tec.llegirSencer();
            }

            System.out.print("op: ");
            char op = tec.llegirCaracter();
            while (op != '+' && op != '-' && op != '*' && op != '/') {
                System.err.print("op inválida: ");
                op = tec.llegirCaracter();
            }

            System.out.print("n2: ");
            int n2 = tec.llegirSencer();

            while (!perteneceNum(n2, numerosJ1) || n2 == 0
                    || (n1 == n2 && cuentaNum(n1, numerosJ1) < 2)) {
                System.err.print("n2 inválido: ");
                n2 = tec.llegirSencer();
            }

            int res = 0;
            boolean ok = true;

            switch (op) {
                case '+':
                    res = n1 + n2;
                    quitar(numerosJ1, n1, n2, res);
                    break;
                case '-':
                    if (!operacion(n1, op, n2)) {
                        ok = false;
                    } else {
                        res = n1 - n2;
                        quitar(numerosJ1, n1, n2, res);
                    }
                    break;
                case '*':
                    res = n1 * n2;
                    quitar(numerosJ1, n1, n2, res);
                    break;
                case '/':
                    if (!operacion(n1, op, n2)) {
                        ok = false;
                    } else {
                        res = n1 / n2;
                        quitar(numerosJ1, n1, n2, res);
                    }
                    break;
            }

            if (ok) {
                System.out.println("= " + res);
            } else {
                System.err.println("Operación no válida");
            }

            System.out.println("Números: " + imprimeArrayN(numerosJ1));
        }

        j1.setPuntos(evaluaFinal(numerosJ1, objetivo));

        // -------- TURNO J2 --------
        System.out.println("Turno " + imprimeNombre(j2.getNombre().getPal()) + " (-1 para acabar)");

        while (true) {

            System.out.println("Objetivo: " + objetivo);
            System.out.print("n1: ");
            int n1 = tec.llegirSencer();
            if (n1 == -1) {
                break;
            }

            while (!perteneceNum(n1, numerosJ2) || n1 == 0) {
                System.err.print("n1 inválido: ");
                n1 = tec.llegirSencer();
            }

            System.out.print("op: ");
            char op = tec.llegirCaracter();
            while (op != '+' && op != '-' && op != '*' && op != '/') {
                System.err.print("op inválida: ");
                op = tec.llegirCaracter();
            }

            System.out.print("n2: ");
            int n2 = tec.llegirSencer();

            while (!perteneceNum(n2, numerosJ2) || n2 == 0
                    || (n1 == n2 && cuentaNum(n1, numerosJ2) < 2)) {
                System.err.print("n2 inválido: ");
                n2 = tec.llegirSencer();
            }

            int res = 0;
            boolean ok = true;

            switch (op) {
                case '+':
                    res = n1 + n2;
                    quitar(numerosJ2, n1, n2, res);
                    break;
                case '-':
                    if (!operacion(n1, op, n2)) {
                        ok = false;
                    } else {
                        res = n1 - n2;
                        quitar(numerosJ2, n1, n2, res);
                    }
                    break;
                case '*':
                    res = n1 * n2;
                    quitar(numerosJ2, n1, n2, res);
                    break;
                case '/':
                    if (!operacion(n1, op, n2)) {
                        ok = false;
                    } else {
                        res = n1 / n2;
                        quitar(numerosJ2, n1, n2, res);
                    }
                    break;
            }

            if (ok) {
                System.out.println("= " + res);
            } else {
                System.err.println("Operación no válida");
            }

            System.out.println("Números: " + imprimeArrayN(numerosJ2));
        }

        j2.setPuntos(evaluaFinal(numerosJ2, objetivo));
    }

    // ==================================================
    //  EVALUACIÓN FINAL HUMANO
    // ==================================================
    private int evaluaFinal(int[] numeros, int objetivo) {
        int resultado = 0;

        System.out.print("Elige tu resultado final: ");
        int temp = tec.llegirSencer();

        boolean esta = perteneceNum(temp, numeros);

        if (esta && temp != 0) {
            int dif = Math.abs(objetivo - temp);

            if (dif == 0) {
                resultado = 10;
            } else if (dif <= 5) {
                resultado = 7;
            } else if (dif <= 10) {
                resultado = 5;
            } else if (dif <= 25) {
                resultado = 3;
            } else if (dif <= 50) {
                resultado = 1;
            } else {
                resultado = 0;
            }

            System.out.println("Puntos: " + resultado);
        } else {
            System.err.println("Número no válido.");
            resultado = 0;
        }

        return resultado;
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

        if (anadir != 0) {
            int last = 0;

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

    //  GENERAR NÚMEROS
    private int[] numerosAleatorios() {
        fi = new FI(new Cadena("files/cifras.txt".toCharArray()));
        fi.obrir();
        char[] linea = fi.llegirLiniaArray();
        fi.tancar();

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
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == n) {
                return true;
            }
        }
        return false;
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

    private int[] copiaArray(int[] p) {
        int[] c = new int[p.length];
        for (int i = 0; i < p.length; i++) {
            c[i] = p[i];
        }
        return c;
    }

    // ==================================================
//  CPU PARA CIFRAS - Búsqueda aleatoria simplificada
// ==================================================
    private void jugarNumerosCPUSimple(int[] numeros, int objetivo) {
        int mejorDiferencia = 999;
        int[] mejorConfiguracion = copiaArray(numeros);

        // Arrays para guardar los pasos (5 operaciones máximo)
        int[] mejorN1 = new int[5];
        int[] mejorN2 = new int[5];
        char[] mejorOp = new char[5];
        int[] mejorRes = new int[5];
        int numPasosMejor = 0;

        System.out.println("Calculando...");

        // Probar muchas veces con operaciones aleatorias
        for (int intento = 0; intento < 8; intento++) {
            int[] temp = copiaArray(numeros);

            // Arrays temporales para guardar pasos
            int[] pasosN1 = new int[5];
            int[] pasosN2 = new int[5];
            char[] pasosOp = new char[5];
            int[] pasosRes = new int[5];
            int numPasos = 0;

            // Hacer hasta 5 operaciones
            for (int op = 0; op < 5; op++) {
                int n1 = obtenerNumeroAleatorio(temp);
                if (n1 == 0) {
                    break;
                }

                int n2 = obtenerNumeroAleatorio(temp);
                if (n2 == 0) {
                    break;
                }

                char operador = obtenerOperadorAleatorio();
                int res = calcularOperacion(n1, operador, n2);

                if (res != -1) {
                    quitar(temp, n1, n2, res);

                    // Guardar el paso
                    pasosN1[numPasos] = n1;
                    pasosN2[numPasos] = n2;
                    pasosOp[numPasos] = operador;
                    pasosRes[numPasos] = res;
                    numPasos++;

                    // Si encontramos el objetivo, terminar
                    if (res == objetivo) {
                        break;
                    }
                }
            }

            // Buscar el mejor número de esta configuración
            int mejorAqui = buscarMasCercano(temp, objetivo);
            int difAqui = Math.abs(objetivo - mejorAqui);

            // ¿Es mejor que lo que teníamos?
            if (difAqui < mejorDiferencia) {
                mejorDiferencia = difAqui;
                mejorConfiguracion = copiaArray(temp);

                // Copiar pasos
                for (int i = 0; i < 5; i++) {
                    mejorN1[i] = pasosN1[i];
                    mejorN2[i] = pasosN2[i];
                    mejorOp[i] = pasosOp[i];
                    mejorRes[i] = pasosRes[i];
                }
                numPasosMejor = numPasos;
            }

            // Si es exacto, terminar
            if (mejorDiferencia == 0) {
                break;
            }
        }

        // Copiar la mejor solución al array original
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = mejorConfiguracion[i];
        }

        // Mostrar los pasos
        System.out.println("");
        if (numPasosMejor == 0) {
            System.out.println("No se pudo realizar ninguna operación válida.");
        } else {
            for (int i = 0; i < numPasosMejor; i++) {
                System.out.println("Operación " + (i + 1) + ": "
                        + mejorN1[i] + " " + mejorOp[i] + " " + mejorN2[i] + " = " + mejorRes[i]);
            }
        }
    }

// Obtener un número aleatorio disponible del array
    private int obtenerNumeroAleatorio(int[] nums) {
        int disponibles = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                disponibles++;
            }
        }

        if (disponibles == 0) {
            return 0;
        }

        int cual = rar.nextInt(disponibles);
        int cont = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (cont == cual) {
                    return nums[i];
                }
                cont++;
            }
        }
        return 0;
    }

// Obtener un operador aleatorio
    private char obtenerOperadorAleatorio() {
        char[] ops = {'+', '-', '*', '/'};
        return ops[rar.nextInt(4)];
    }

// Calcular una operación si es válida
    private int calcularOperacion(int n1, char op, int n2) {
        if (!operacion(n1, op, n2)) {
            return -1;
        }

        int resultado = -1;

        if (op == '+') {
            resultado = n1 + n2;
        } else if (op == '-') {
            resultado = n1 - n2;
        } else if (op == '*') {
            resultado = n1 * n2;
        } else if (op == '/') {
            resultado = n1 / n2;
        }

        return resultado;
    }

// Buscar el número más cercano al objetivo
    private int buscarMasCercano(int[] nums, int objetivo) {
        int mejor = 0;
        int mejorDif = 999;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int dif = Math.abs(objetivo - nums[i]);
                if (dif < mejorDif) {
                    mejorDif = dif;
                    mejor = nums[i];
                }
            }
        }
        return mejor;
    }

// ==================================================
//  EVALUACIÓN FINAL CPU
// ==================================================
    private int evaluaFinalCPU(int[] numeros, int objetivo) {
        int resultado = 0;
        int mejorNum = 0;
        int mejorDif = 999;

        // Buscar el número más cercano al objetivo
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] != 0) {
                int dif = Math.abs(objetivo - numeros[i]);
                if (dif < mejorDif) {
                    mejorDif = dif;
                    mejorNum = numeros[i];
                }
            }
        }

        System.out.println("Resultado final CPU: " + mejorNum);

        // Calcular puntuación según la diferencia
        if (mejorDif == 0) {
            resultado = 10;
        } else if (mejorDif <= 5) {
            resultado = 7;
        } else if (mejorDif <= 10) {
            resultado = 5;
        } else if (mejorDif <= 25) {
            resultado = 3;
        } else if (mejorDif <= 50) {
            resultado = 1;
        } else {
            resultado = 0;
        }

        System.out.println("Puntos CPU: " + resultado);

        return resultado;
    }

    // --------------------------------------------------
    private void jugarPalabras(Jugador j1, Jugador j2) {

        char[] nombreJ1 = j1.getNombre().getPal();

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
        System.out.println(imprimeArray(letrasEscogidas));

        System.out.println("Palabra: ");
        char[] creada = tec.llegirLiniaC();

        if (compruebaPalabra(new Cadena(creada), letrasEscogidas, idioma)) {
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
                System.out.println(imprimeArray(palabraCPU));
                System.out.println("Puntos CPU: " + palabraCPU.length);
                CPU.setPuntos(palabraCPU.length);
            } else {
                System.out.println("Palabra errónea");
                System.out.println("");
            }
        }
    }

    private void jugarPalabrasJJ(Jugador j1, Jugador j2) {

        System.out.println("Idioma (1 CA, 2 ES, 3 EN): ");
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

        // -------- J1 letras --------
        char[] letrasJ1 = devuelveLetras(letras);
        System.out.println("Letras " + imprimeNombre(j1.getNombre().getPal()) + ": " + imprimeArray(letrasJ1));
        System.out.print("Palabra: ");
        char[] creada1 = tec.llegirLiniaC();

        if (compruebaPalabra(new Cadena(creada1), letrasJ1, idioma)) {
            j1.setPuntos(creada1.length);
            System.out.println("Puntos " + imprimeNombre(j1.getNombre().getPal()) + ": " + creada1.length);
        } else {
            System.out.println("Palabra errónea");
        }

        // -------- J2 letras (DISTINTAS) --------
        char[] letrasJ2 = devuelveLetras(letras);
        System.out.println("Letras " + imprimeNombre(j2.getNombre().getPal()) + ": " + imprimeArray(letrasJ2));
        System.out.print("Palabra: ");
        char[] creada2 = tec.llegirLiniaC();

        if (compruebaPalabra(new Cadena(creada2), letrasJ2, idioma)) {
            j2.setPuntos(creada2.length);
            System.out.println("Puntos " + imprimeNombre(j2.getNombre().getPal()) + ": " + creada2.length);
        } else {
            System.out.println("Palabra errónea");
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
