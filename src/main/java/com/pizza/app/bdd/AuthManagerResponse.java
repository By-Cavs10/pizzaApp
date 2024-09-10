package com.pizza.app.bdd;

public class AuthManagerResponse <T> {

    public String code;
    public String message;
    public T data;

    public static <T> AuthManagerResponse<T> performResponse(String code, String message, T data) {
        // Cas 1 : Succès
        AuthManagerResponse<T> response = new AuthManagerResponse<T>();
        response.code = code;
        response.message = message;
        response.data = data;

        // Afficher la réponse métier dans la console/log (avant de return réponse)
        System.out.println(String.format("Response Metier - Code : %s - Message : %s", response.code, response.message));

        return response;
    }
}
