/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package onepizza;

import Model.Cliente;
import java.util.ArrayList;
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
        Scanner scanner = new Scanner(System.in);

        int C = scanner.nextInt(); // número de clientes
        List<Cliente> clientes = new ArrayList<>();

        Map<String, Integer> contadorGustos = new HashMap<>();
        Map<String, Integer> contadorDisgustos = new HashMap<>();

        for (int i = 0; i < C; i++) {
            int G = scanner.nextInt(); // cantidad de gustos
            Set<String> gustos = new HashSet<>();
            for (int j = 0; j < G; j++) {
                String ingrediente = scanner.next();
                gustos.add(ingrediente);
                contadorGustos.put(ingrediente, contadorGustos.getOrDefault(ingrediente, 0) + 1);
            }

            int D = scanner.nextInt(); // cantidad de disgustos
            Set<String> disgustos = new HashSet<>();
            for (int j = 0; j < D; j++) {
                String ingrediente = scanner.next();
                disgustos.add(ingrediente);
                contadorDisgustos.put(ingrediente, contadorDisgustos.getOrDefault(ingrediente, 0) + 1);
            }

            clientes.add(new Cliente(gustos, disgustos));
        }

        // Calcular el puntaje de cada ingrediente con penalización a los disgustos
        Map<String, Integer> puntaje = new HashMap<>();
        Set<String> todosIngredientes = new HashSet<>();
        todosIngredientes.addAll(contadorGustos.keySet());
        todosIngredientes.addAll(contadorDisgustos.keySet());

        for (String ingrediente : todosIngredientes) {
            int likes = contadorGustos.getOrDefault(ingrediente, 0);
            int dislikes = contadorDisgustos.getOrDefault(ingrediente, 0);
            int score = likes - dislikes; // Penalización más fuerte para los disgustos
            puntaje.put(ingrediente, score);
        }

        // Ordenar por puntaje
        List<String> ordenados = new ArrayList<>(todosIngredientes);
        ordenados.sort((a, b) -> Integer.compare(puntaje.getOrDefault(b, 0), puntaje.getOrDefault(a, 0)));

        // Seleccionar ingredientes con puntaje positivo
        // Ahora se evita que agregar ingredientes que puedan perjudicar la satisfacción general.
        Set<String> pizza = new HashSet<>();
        int mejorSatisfaccion = 0;

        for (String ingrediente : ordenados) {
            Set<String> nuevaPizza = new HashSet<>(pizza);
            nuevaPizza.add(ingrediente);

            int nuevaSatisfaccion = Evaluar.contarClientesSatisfechos(nuevaPizza, clientes);

            if (nuevaSatisfaccion >= mejorSatisfaccion) {
                pizza = nuevaPizza;
                mejorSatisfaccion = nuevaSatisfaccion;
            }
        }

        // Evaluar la cantidad de clientes satisfechos
        int satisfechos = Evaluar.contarClientesSatisfechos(pizza, clientes);

        // Imprimir resultado
        System.out.print(pizza.size());
        for (String ingrediente : pizza) {
            System.out.print(" " + ingrediente);
        }
        System.out.println();
        System.out.println("Clientes satisfechos: " + satisfechos);
        System.out.println("Pizza generada: " + pizza);
    }
}
