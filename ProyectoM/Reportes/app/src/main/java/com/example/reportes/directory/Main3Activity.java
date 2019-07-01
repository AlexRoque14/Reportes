package com.example.reportes.directory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.reportes.R;

import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    final ListView lv_rep = findViewById(R.id.lv_rep);
    List<Report> reports;
    ReportInterface report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_layout);

        report = Connection2.getServiceRemote();
    }

    private void repor(){

        Call<Report> call = report.getByIdReport(1);
        reports = new ArrayList<Report>();


    }

}
