package com.example.reportes.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reportes.connection.MainActivity;
import com.example.reportes.R;
import com.example.reportes.directory.Main3Activity;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Main2Activity extends AppCompatActivity {

    LoginInterface user;

    public static int user_idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retrofit retrofit = Connection3.getInstance();
        user = retrofit.create(LoginInterface.class);

        final EditText usr = findViewById(R.id.txtTitle);
        final EditText pass = findViewById(R.id.txtDesc);

        final TextView registro = findViewById(R.id.tv_register);
        final Button login = findViewById(R.id.btn_login);


        //buttons
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(usr.getText().toString(), pass.getText().toString());
            }
        });
    }


    public void loginUser(String usr , String pass){

        Call<ResponseBody> call = user.login(usr,pass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;

               try {
                   if(response.code() == 200){
                       s = response.body().string();

                       System.out.println("Return 1: " + s);

                       String[] partes = s.split("[:,]");

                       for (int i = 0; i < partes.length; i++){
                           System.out.println(partes[i]);
                       }

                       System.out.println(partes[1]);
                       user_idd = Integer.parseInt(partes[1]);
                       System.out.println("id del usuario es " + user_idd);

                       finish();
                       Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                       startActivity(intent );

                   }
                   else {
                    s = response.errorBody().string();
                    Toast.makeText(Main2Activity.this, s, Toast.LENGTH_LONG).show();
                }

               }catch (IOException e){
                   e.printStackTrace();
               }

                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        Toast.makeText(Main2Activity.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
                //System.out.println(t);
                Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
