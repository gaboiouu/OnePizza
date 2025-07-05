/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package onepizza;

/**
 *
 * @author Usuario
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread hilo = new Thread(new OnePizza());
        hilo.start();
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
