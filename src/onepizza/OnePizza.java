/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package onepizza;

import Model.Cliente;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import util.Evaluar;

/**
 *
 * @author Usuario
 */
public class OnePizza implements Runnable{

    /**
     * @param args the command line arguments
     */
    @Override
    public void run() {
        List<Cliente> clientes = new ArrayList<>();
        Set<String> todosIngredientes = new HashSet<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("entrada2.txt"));
            Scanner scanner = new Scanner(reader);

            int C = scanner.nextInt();

            for (int i = 0; i < C; i++) {
                int G = scanner.nextInt(); //cantidad de gustos
                Set<String> gustos = new HashSet<>();
                for (int j = 0; j < G; j++) {
                    String ingrediente = scanner.next();
                    gustos.add(ingrediente);
                    todosIngredientes.add(ingrediente);
                }

                int D = scanner.nextInt(); // cantidad de disgustos
                Set<String> disgustos = new HashSet<>();
                for (int j = 0; j < D; j++) {
                    String ingrediente = scanner.next();
                    disgustos.add(ingrediente);
                    todosIngredientes.add(ingrediente);
                }

                clientes.add(new Cliente(gustos, disgustos));
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
            return;
        }
        // NUEVA HEURÍSTICA AQUÍ
        Map<String, Double> puntaje = new HashMap<>();
        for (Cliente c : clientes) {
            for (String like : c.getLikes()) {
                double peso = (c.getDislikes().isEmpty()) ? 2.0 : 1.0; // Doble peso si no tiene dislikes
                puntaje.put(like, puntaje.getOrDefault(like, 0.0) + peso);
            }
            for (String dislike : c.getDislikes()) {
                puntaje.put(dislike, puntaje.getOrDefault(dislike, 0.0) - 3.0); // Penalización fuerte
            }
        }

        Set<String> mejorPizza = new HashSet<>();
        int mejorSatisfaccion = 0;

        for (int intento = 0; intento < 1000; intento++) {
            List<String> ordenados = new ArrayList<>(todosIngredientes);
            ordenados.sort((a, b) -> Double.compare(puntaje.getOrDefault(b, 0.0), puntaje.getOrDefault(a, 0.0)));

             // Pequeño shuffle para evitar determinismo en top ingredientes
            if (ordenados.size() > 5) {
                List<String> top = ordenados.subList(0, 5);
                Collections.shuffle(top);
            }

            Set<String> pizza = new HashSet<>();
            int satisfaccion = 0;
            
            //greedy + validacion
            for (String ing : ordenados) {
                Set<String> nuevaPizza = new HashSet<>(pizza);
                nuevaPizza.add(ing);
                int nuevaSatisfaccion = Evaluar.contarClientesSatisfechos(nuevaPizza, clientes);
                if (nuevaSatisfaccion >= satisfaccion) {
                    pizza = nuevaPizza;
                    satisfaccion = nuevaSatisfaccion;
                }
            }

            // Mejora local 
            boolean hayMejora = true;
            while (hayMejora) {
                hayMejora = false;
                for (String ing : todosIngredientes) {
                    Set<String> prueba = new HashSet<>(pizza);
                    if (pizza.contains(ing)) {
                        prueba.remove(ing);
                    } else {
                        prueba.add(ing);
                    }
                    int nuevaSatisfaccion = Evaluar.contarClientesSatisfechos(prueba, clientes);
                    if (nuevaSatisfaccion > satisfaccion) {
                        pizza = prueba;
                        satisfaccion = nuevaSatisfaccion;
                        hayMejora = true;
                        break;
                    }
                }
            }
            // guarda la mejor pizza
            if (satisfaccion > mejorSatisfaccion) {
                mejorSatisfaccion = satisfaccion;
                mejorPizza = pizza;
            }
        }

        int totalClientes = clientes.size();
        int insatisfechos = totalClientes - mejorSatisfaccion;

        // Imprimir resultados
        System.out.print(mejorPizza.size());
        for (String ing : mejorPizza) {
            System.out.print(" " + ing);
        }
        System.out.println();
        System.out.println("Clientes satisfechos: " + mejorSatisfaccion);
        System.out.println("Clientes insatisfechos: " + insatisfechos);
        System.out.println("Total de clientes: " + totalClientes);
        System.out.println("Pizza generada: " + mejorPizza);

        System.out.println("\n--- Clientes insatisfechos ---");
        int index = 1;
        for (Cliente c : clientes) {
            if (!c.satisfecho(mejorPizza)) {
                System.out.println("Cliente " + index + ":");
                System.out.println("  Likes: " + c.getLikes());
                System.out.println("  Dislikes: " + c.getDislikes());
            }
            index++;
        }
    }
}