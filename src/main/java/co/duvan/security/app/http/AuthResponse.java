package co.duvan.security.app.http;

public class AuthResponse {

    //* Vars
    private String token;

    //* Constructor
    public AuthResponse() {}

    public AuthResponse(String token) {
        this.token = token;
    }

    //* Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
