package com.example.reportes.connection;

public class Connection {

    public Connection() {}

    public static final String API_URL = "http://192.168.44.203:3000/";

    public static UsuarioInterface getServiceRemote(){
        return Client.getClient(API_URL).create(UsuarioInterface.class);
    }
}
