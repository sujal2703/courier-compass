package com.example.aicareeradvisor;

import com.example.aicareeradvisor.ui.MainApp;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        try {
            System.out.println("Starting AI Career Advisor Application...");
            Application.launch(MainApp.class, args);
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}