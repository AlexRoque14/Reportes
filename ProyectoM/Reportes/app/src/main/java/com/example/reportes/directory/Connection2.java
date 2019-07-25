package com.example.reportes.directory;

import com.example.reportes.connection.Client;
import com.example.reportes.connection.UsuarioInterface;

public class Connection2 {

    public Connection2() {}

    public static final String API_URL = "http://192.168.0.7:3000/";

    public static ReportInterface getServiceRemote(){
        return Client.getClient(API_URL).create(ReportInterface.class);
    }
}
