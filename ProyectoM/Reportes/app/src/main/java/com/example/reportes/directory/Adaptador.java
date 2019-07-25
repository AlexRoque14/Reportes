package com.example.reportes.directory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import com.example.reportes.R;
import com.example.reportes.report.Main4Activity;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adaptador extends BaseAdapter {


    protected Activity activity;
    protected ArrayList<Report> items;
    public static int id;
    ReportInterface rep ;


    public Adaptador (Activity activity, ArrayList<Report> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Report> category) {
        for (int i = 0 ; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.elemento_lista, null);
        }

        final Report dir = items.get(position);

        TextView title = (TextView) v.findViewById(R.id.tvTitulo);
        title.setText(dir.getTitle());

        TextView description = (TextView) v.findViewById(R.id.tvDirector);
        description.setText(dir.getDescription());

        ImageView imagen = (ImageView) v.findViewById(R.id.ivImagen);
        imagen.setImageResource(R.drawable.camara);

        TextView fecha = (TextView) v.findViewById(R.id.tvDuracion);
        fecha.setText(dir.getFechita());

        Button edit = (Button) v.findViewById(R.id.btnEdit);
        Button delete = (Button) v.findViewById(R.id.btnDelete);


        rep = Connection2.getServiceRemote();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = dir.getId();

                activity.startActivity(new Intent(activity, EditReport.class));

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("id: "+ dir.getId() + "Nombre: " + dir.getTitle());
                 id = dir.getId();
                deleteReport(id);
                Main3Activity.adapter.notifyDataSetChanged();

            }
        });

        notifyDataSetChanged();
        return v;
    }

    public void deleteReport(int id){
            Call<Report> call = rep.deleteReport(id);

            call.enqueue(new Callback<Report>() {
                @Override
                public void onResponse(Call<Report> call, Response<Report> response) {
                  if(response.isSuccessful()){
                      System.out.println("Borrado con exito");


                  }
                }

                @Override
                public void onFailure(Call<Report> call, Throwable t) {
                    System.out.println("Se fue alv todo");
                }
            });
    }
}




