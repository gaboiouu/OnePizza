/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Usuario
 */
public class Cliente {
    Set<String> likes;
    Set<String> dislikes;

    public Cliente(Set<String> likes, Set<String> dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }
    
    public boolean satisfecho(Set<String>pizza){
        return pizza.containsAll(likes) && Collections.disjoint(pizza, dislikes);
    }

    public Set<String> getLikes() {
        return likes;
    }

    public Set<String> getDislikes() {
        return dislikes;
    }
}
