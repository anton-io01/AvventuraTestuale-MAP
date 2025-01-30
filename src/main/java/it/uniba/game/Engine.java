package it.uniba.game;

import it.uniba.game.util.GUI;

import javax.swing.*;


public class Engine {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            new GUI();
        });
    }
}