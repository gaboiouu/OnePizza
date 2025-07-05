/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import Model.Cliente;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Usuario
 */
public class Evaluar {
    public static int contarClientesSatisfechos(Set<String>pizza, List<Cliente> clientes){
        int total = 0;
        for (Cliente c : clientes) {
            if(c.satisfecho(pizza)) total++;
        }
        return total;
    }
}
