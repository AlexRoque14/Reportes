package com.example.reportes.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.reportes.connection.Connection;
import com.example.reportes.connection.MainActivity;
import com.example.reportes.R;
import com.example.reportes.connection.UsuarioInterface;
import com.example.reportes.directory.Main3Activity;

public class Main2Activity extends AppCompatActivity {

    UsuarioInterface user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = Connection.getServiceRemote();

        final EditText usr = findViewById(R.id.txtUser);
        final EditText pass = findViewById(R.id.txtPass);

        final TextView registro = findViewById(R.id.tv_register);
        final Button login = findViewById(R.id.btn_login);


        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name = usr.getText().toString();
                //String pasw = pass.getText().toString();
                //login(name);

                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent );
            }
        });
    }

    /*
    public void login(String name){

        Call<Usuario> call = user.getByNameUser(name);

            ((Call) call).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    System.out.println(response);
                    if(response.isSuccessful()){
                        Toast.makeText(Main2Activity.this, "Inicio de Sesion Exitoso", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call call, Throwable t) {
                    System.out.println(t.getMessage());
                    Log.e("Error", t.getMessage());
                }
            });

    }
*/
}
