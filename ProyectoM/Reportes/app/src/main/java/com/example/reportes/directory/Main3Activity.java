package com.example.reportes.directory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reportes.R;
import com.example.reportes.report.Main4Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    ListView lv;
    List<Report> reports;
    ReportInterface report;
    ArrayList<Report> reportes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_layout);
        report = Connection2.getServiceRemote();

        reports = new ArrayList<>();
        reportes  = new ArrayList<Report>();

        final Button neew = findViewById(R.id.btn_NewReport);
        Reportes();



        neew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(intent);
            }
        });

    }



    public void Reportes(){
        String url = "http://192.168.44.203:3000/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReportInterface rep = retrofit.create(ReportInterface.class);
        Call<List<Report>> lista = rep.getReport();

        lista.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                if (response.isSuccessful()) {
                    reports = response.body();
                    Toast.makeText(Main3Activity.this, "Funcionó esto", Toast.LENGTH_SHORT).show();

                    for(int i =0; i<reports.size(); i++){
                        reportes.add(reports.get(i));
                    }
                    insert(reportes);
                }
            }
            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                Toast.makeText(Main3Activity.this, "No funcionó", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insert(ArrayList arr){
        lv = findViewById(R.id.lv);
        AdapterReport adapter = new AdapterReport(this, arr);
        lv.setAdapter(adapter);
    }


}

