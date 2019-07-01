package com.example.reportes.connection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.reportes.R;
import com.example.reportes.login.Main2Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    UsuarioInterface user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        final Button btnAdd = findViewById(R.id.btnAdd);
        final EditText nombre = findViewById(R.id.txtNombre);
        final EditText correo = findViewById(R.id.txtCorreo);
        final EditText password = findViewById(R.id.txtPassword);

        user = Connection.getServiceRemote();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario user = new Usuario();
                user.setId(0);  //se setea 0 por mi configuración en la DB
                user.setName(nombre.getText().toString());
                user.setEmail(correo.getText().toString());
                user.setPassword(password.getText().toString());
                addUsuario(user);
            }
        });
    }

    public void addUsuario(Usuario r){
        Call<Usuario> call = user.addUser(r);

        ((Call) call).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                System.out.println(response);
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println(t.getMessage());
                Log.e("Error", t.getMessage());
            }
        });
    }
}
