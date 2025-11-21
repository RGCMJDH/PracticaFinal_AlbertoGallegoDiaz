/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicafinal_albertogallegodiaz;

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

    private void inicio() {
        tec = new LT();

        do {
            // MENÚ PRINCIPAL
            mostrarMenuPrincipal();
            opcion = tec.llegirCaracter();

            switch (opcion) {
                case '1': // Jugar
                    mostrarMenuJugar();
                    char opcionJugar = tec.llegirCaracter();

                    switch (opcionJugar) {
                        case '1':
                            System.out.println("Jugar contra el ordenador");
                            // aquí llamarías al método que gestiona esta opción
                            break;
                        case '2':
                            System.out.println("Jugar contra otro jugador");
                            // aquí llamarías al método que gestiona esta opción
                            break;
                        case 's':
                            // vuelve al menú principal
                            break;
                        default:
                            System.out.println("Opción no válida en Jugar");
                    }
                    break;

                case '2': // Registro
                    mostrarMenuRegistro();
                    char opcionRegistro = tec.llegirCaracter();

                    switch (opcionRegistro) {
                        case '1':
                            System.out.println("Mostrar resultados de las partidas");
                            // llamada al método correspondiente
                            break;
                        case '2':
                            System.out.println("Mostrar estadísticas de un jugador");
                            // llamada al método correspondiente
                            break;
                        case 's':
                            // vuelve al menú principal
                            break;
                        default:
                            System.out.println("Opción no válida en Registro");
                    }
                    break;
//                    case '3': // OPCIONES
//                    mostrarMenuRegistro();
//                    char opcionRegistro = tec.llegirCaracter();
//
//                    switch (opcionRegistro) {
//                        case '1':
//                            System.out.println("Mostrar resultados de las partidas");
//                            // llamada al método correspondiente
//                            break;
//                        case '2':
//                            System.out.println("Mostrar estadísticas de un jugador");
//                            // llamada al método correspondiente
//                            break;
//                        case 's':
//                            // vuelve al menú principal
//                            break;
//                        default:
//                            System.out.println("Opción no válida en Registro");
//                    }
//                    break;

                    
                    
                case 's':
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida en el menú principal");
            }

        } while (opcion != 's');

    }

    public static void mostrarMenuPrincipal() {
        System.out.println("MENÚ PRINCIPAL");
        System.out.println("");
        System.out.println("1) Jugar");
        System.out.println("");
        System.out.println("2) Registro");
        System.out.println("");
        System.out.println("s) Salir");
        System.out.print("Opción: ");
    }

    public static void mostrarMenuJugar() {
        System.out.println("MENÚ JUGAR");
        System.out.println("");
        System.out.println("1) Jugar contra el ordenador");
        System.out.println("");
        System.out.println("2) Jugar contra otro jugador");
        System.out.println("");
        System.out.println("s) Volver al menú principal");
        System.out.print("Opción: ");
    }

    public static void mostrarMenuRegistro() {
        System.out.println("MENÚ REGISTRO");
        System.out.println("");
        System.out.println("1) Mostrar resultados de las partidas");
        System.out.println("");
        System.out.println("2) Mostrar estadísticas de un jugador");
        System.out.println("");
        System.out.println("s) Volver al menú principal");
        System.out.print("Opción: ");
    }

}
