package com.pizza.app.bll;

import com.pizza.app.bo.Utilisateur;

 public class AppManagerResponse<T> {

    // Attributs de la réponse
    private String code;       // Code de réponse (ex : "200" pour succès, "400" pour erreur)
    private String message;    // Message à renvoyer (ex : "Inscription réussie")
    private T data;            // Données spécifiques à l'opération (ex : l'objet Utilisateur)
    private boolean success;   // Statut de succès (true si l'opération est réussie)

    // Constructeurs
    public AppManagerResponse() {
        // Constructeur vide par défaut
    }

    // Constructeur avec paramètres
    public AppManagerResponse(String code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    // Méthode statique pour créer une réponse rapide avec succès ou échec
    public static <T> AppManagerResponse<T> performResponse(String code, String message, T data, boolean success) {
        // Afficher la réponse métier dans la console/log avant de renvoyer la réponse
        System.out.println(String.format("Response Metier - Code : %s - Message : %s", code, message));

        // Retourner une nouvelle instance de AppManagerResponse avec les paramètres passés
        return new AppManagerResponse<>(code, message, data, success);
    }

    // Getters et Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
