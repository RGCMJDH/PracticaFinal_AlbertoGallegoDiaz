/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicafinal_albertogallegodiaz;

import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author ramon
 */
public class PracticaFinal_AlbertoGallegoDiaz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PracticaFinal_AlbertoGallegoDiaz().inicio();
    }

    private LT tec;
    private int opcion;
    private Juego juego;
    private Registro reg;

    package practicafinal_albertogallegodiaz ;

    public class PracticaFinal_AlbertoGallegoDiaz {

        public static void main(String[] args) {
            new PracticaFinal_AlbertoGallegoDiaz().inicio();
        }

        private LT tec;
        private char opcion;
        private Juego juego;
        private Registro reg;

        private void inicio() {
            tec = new LT();

            do {
                // MENÚ PRINCIPAL (todo aquí, sin métodos extra)
                System.out.println("MENÚ PRINCIPAL");
                System.out.println("1) Jugar");
                System.out.println("2) Registro");
                System.out.println("s) Salir");
                System.out.print("Opción: ");
                opcion = tec.llegirCaracter();

                switch (opcion) {

                    case '1': // Jugar
                        System.out.println();
                        System.out.println("MENÚ JUGAR");
                        System.out.println("1) Jugar contra el ordenador");
                        System.out.println("2) Jugar contra otro jugador");
                        System.out.println("s) Volver al menú principal");
                        System.out.print("Opción: ");
                        char opcionJugar = tec.llegirCaracter();

                        switch (opcionJugar) {
                            case '1':
                                System.out.println("Jugar contra el ordenador");
                                Jugador j1 = new Jugador(pedirDatos(),0);
                                juego.logicaJuegoCPU(j1);
                                break;
                            case '2':
                                System.out.println("Jugar contra otro jugador");
                                // aquí lógica 2 jugadores
                                break;
                            case 's':
                                // volver al menú principal
                                break;
                            default:
                                System.out.println("Opción no válida en Jugar");
                        }
                        break;

                    case '2': // Registro
                        System.out.println();
                        System.out.println("MENÚ REGISTRO");
                        System.out.println("1) Mostrar resultados de las partidas");
                        System.out.println("2) Mostrar estadísticas de un jugador");
                        System.out.println("s) Volver al menú principal");
                        System.out.print("Opción: ");
                        char opcionRegistro = tec.llegirCaracter();

                        switch (opcionRegistro) {
                            case '1':
                                System.out.println("Mostrar resultados de las partidas");
                                // llamada al registro
                                break;
                            case '2':
                                System.out.println("Mostrar estadísticas de un jugador");
                                // llamada a estadísticas
                                break;
                            case 's':
                                // volver al menú principal
                                break;
                            default:
                                System.out.println("Opción no válida en Registro");
                        }
                        break;

                    case 's':
                        System.out.println("Saliendo del programa...");
                        break;

                    default:
                        System.out.println("Opción no válida en el menú principal");
                }

                System.out.println();

            } while (opcion != 's');
        }
    }

    private char[] pedirDatos() {
        char[] nombreJ;
        return nombreJ = tec.llegirLiniaC();

    }

    private void jugarSolo(int rondas) {

    }

    private void Estadisticas() {
        FI fi = new FI(new Cadena("registro.txt".toCharArray()));
        fi.obrir();
        char[] llegida = fi.llegirLiniaArray();
        String res = "";
        do {
            llegida = fi.llegirLiniaArray();
            if (llegida != null) {

                res += imprimirArray(llegida) + "\n";

            }
        } while (llegida != null);
        fi.tancar();
        System.out.println(res);
    }

    private String imprimirArray(char[] c) {
        String res = " ";
        for (int i = 0; i < c.length; i++) {
            res += c[i];
        }
        return res;
    }
}
