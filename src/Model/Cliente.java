/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

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
    
    public boolean satisfecho(Set<String> pizza) {
        for (String like : likes) {
            if (!pizza.contains(like)) return false;
        }
        for (String dislike : dislikes) {
            if (pizza.contains(dislike)) return false;
        }
        return true;
    }

    public Set<String> getLikes() {
        return likes;
    }

    public Set<String> getDislikes() {
        return dislikes;
    }
}
