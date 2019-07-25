package com.example.reportes.directory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reportes.R;
import com.example.reportes.login.Main2Activity;
import com.example.reportes.report.Main4Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    public static ListView lv;
    public static Adaptador adapter;
    List<Report> reports;
    ReportInterface report;
    ArrayList<Report> reportes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        report = Connection2.getServiceRemote();

        reports = new ArrayList<>();
        reportes  = new ArrayList<Report>();

        final Button neew = findViewById(R.id.btnRR);
        final Button salir = findViewById(R.id.btnSalir);
        Reportes();

        neew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(intent);

            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    public void Reportes(){
        String url = "http://192.168.0.7:3000/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        System.out.println("USER_ID: " + Main2Activity.user_idd);
        ReportInterface rep = retrofit.create(ReportInterface.class);

        Call<List<Report>> lista = rep.getById(Main2Activity.user_idd);

        lista.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                if (response.isSuccessful()) {

                    reports = response.body();

                    Toast.makeText(Main3Activity.this, "Buscando reportes...", Toast.LENGTH_SHORT).show();

                    for(int i =0; i<reports.size(); i++){
                        reportes.add(reports.get(i));
                    }

                    insert(reportes);
                }
            }
            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                Toast.makeText(Main3Activity.this, "No se encontraron reportes...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void insert(ArrayList arr){
        lv = findViewById(R.id.lvLista);
       adapter = new Adaptador(this, arr);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }




}

