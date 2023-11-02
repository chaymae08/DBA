/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pr2;
import jade.core.Agent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author chaymae
 */
public class Pr2 extends Agent{
     private int[][] mapa;  // Representación del mapa
    private int filaActual=0; // Fila actual del agente 
    private int columnaActual=0; // Columna actual del agente
    private int objetivoFila=0; // Fila del objetivo
    private int objetivoColumna=0; // Columna del objetivo

    @Override
    protected void setup() {
        // Cargar el mapa desde un archivo de texto 
        cargarMapa();
        // Definir la posición inicial y el objetivo
        filaActual = 0; // Definir la posición inicial del agente
        columnaActual = 0;
        objetivoFila = 4; // Definir la posición del objetivo
        objetivoColumna = 4;
        resolverMapa(); // Llamar a un método para resolver el mapa
        doDelete();
    }

    
    
    // Método para cargar el mapa desde un archivo de texto
    private void cargarMapa() {
        String filePath = "basicMap.txt";
        System.out.println("Ruta del archivo: " + new File(filePath).getAbsolutePath());

        
        try (BufferedReader br = new BufferedReader(new FileReader("basicMap.txt"))) {
        // Leer la primera línea para obtener el número de filas
        int filas = Integer.parseInt(br.readLine());

        // Leer la segunda línea para obtener el número de columnas
        int columnas = Integer.parseInt(br.readLine());

        // Inicializar la matriz del mapa con las dimensiones obtenidas
        mapa = new int[filas][columnas];

        String linea;
        int fila = 0;

        while ((linea = br.readLine()) != null) {
            String[] elementos = linea.split("\t"); // Delimitador de tabulación
            for (int i = 0; i < columnas; i++) {
                int valorCelda = Integer.parseInt(elementos[i]);
                if (valorCelda == -1 || valorCelda == 0) {
                    mapa[fila][i] = valorCelda;
                } else {
                    System.err.println("Valor no válido en el mapa: " + valorCelda);
                }
            }
            fila++;
        }
    } catch (IOException e) {
        System.err.println("Error al cargar el mapa desde el archivo.");
    }
    }
    
    // Método para verificar si una celda es válida y libre de obstáculos
    private boolean esCeldaValida(int fila, int columna) {
    // Verificar si la celda está dentro de los límites del mapa y si es una celda libre (sin obstáculos)
    System.out.println("La fila es" + fila);                  //PARA DEPURAR EL CODIGO 
    System.out.println("la columna es " + columna);


    return fila >= 0 /*&& fila < mapa.length*/ && columna >= 0 /*&& columna < mapa[0].length && mapa[fila][columna] == 0*/;
    
}
    // Método para resolver el mapa
    private void resolverMapa() {
    // Mientras el agente no haya alcanzado el objetivo
    while (filaActual != objetivoFila || columnaActual != objetivoColumna) {
        
        // Calcular la dirección hacia el objetivo
        int filaDireccion = objetivoFila - filaActual;
        int columnaDireccion = objetivoColumna - columnaActual;

        // Verificar si la siguiente celda en la dirección es válida y no tiene obstáculos
        if (esCeldaValida(filaActual + filaDireccion, columnaActual + columnaDireccion)) {
            // Mover el agente en esa dirección
            filaActual += filaDireccion;
            columnaActual += columnaDireccion;
        } else {
            // Si la dirección no es válida, intentar otras direcciones
            // Por ejemplo, puede intentar moverse primero horizontalmente y luego verticalmente
            if (esCeldaValida(filaActual, columnaActual + 1)) {
                columnaActual += 1;
            } else if (esCeldaValida(filaActual + 1, columnaActual)) {
                filaActual += 1;
            } else {
                // En caso de que todas las direcciones estén bloqueadas, no se puede avanzar más
                System.out.println("El agente no puede alcanzar el objetivo debido a obstáculos.");
                break;
            }
        }

        
    }

    // El agente ha alcanzado el objetivo
    System.out.println("El agente ha alcanzado el objetivo.");
    }
}
