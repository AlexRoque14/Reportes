package com.example.reportes.directory;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.reportes.R;
import com.example.reportes.connection.Connection;
import com.example.reportes.connection.MainActivity;
import com.example.reportes.connection.Usuario;
import com.example.reportes.directory.Connection2;
import com.example.reportes.directory.Main3Activity;
import com.example.reportes.directory.Report;
import com.example.reportes.directory.ReportInterface;
import com.example.reportes.login.Main2Activity;
import com.example.reportes.report.Main4Activity;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditReport extends AppCompatActivity {

    ImageView imagen;
    Button button;
    Button btn;
    Button btnC;
    private  final String CARPETA_RAIZ="misImagenesPruebs/";
    private  final String RUTA_IMAGEN=CARPETA_RAIZ + "misFotos";
    private static  String nombreImagen="";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    public static  String path = "";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    ReportInterface report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);

        report = Connection2.getServiceRemote();

        imagen = (ImageView) findViewById(R.id.imagen);
        button = (Button) findViewById(R.id.btnCamara);
        btn = (Button) findViewById(R.id.btn_registrar);
        btnC = (Button) findViewById(R.id.btn_cancelar);

        final EditText txtT = findViewById(R.id.txtTitle);
        final EditText txtD = findViewById(R.id.txtDesc);

        System.out.println("User: " + Main2Activity.user_idd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llamarIntent();
                cargarImagen();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report rep = new Report();
                int id = Adaptador.id;

                rep.setId(Adaptador.id);
                rep.setTitle(txtT.getText().toString());
                rep.setDescription(txtD.getText().toString());
                rep.setImage(nombreImagen);
                rep.setUserId(Main2Activity.user_idd);
                addReport( id, rep);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditReport.this, Main3Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void addReport(int id , Report rep){
        Call<Report> call = report.updateReport(id , rep);

        ((Call) call).enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                System.out.println(response);
                if(response.isSuccessful()){

                    finish();
                    Intent intent = new Intent(EditReport.this, Main3Activity.class);
                    startActivity(intent);
                    Toast.makeText(EditReport.this, "Reporte actualizado correctamente", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println(t.getMessage());
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void cargarImagen() {

        final CharSequence[] opciones = {"Tomar Foto","Cargar imagen", "Cancelar"};
        final AlertDialog.Builder alertaOpciones = new AlertDialog.Builder(EditReport.this);

        alertaOpciones.setTitle("Seleccione una opcion");
        alertaOpciones.setItems(opciones, new DialogInterface.OnClickListener(){

            @Override
            public  void  onClick(DialogInterface dialogInterface, int i ){
                if(opciones[i].equals("Tomar Foto")){
                    tomarFotografia();
                }
                else{
                    if(opciones[i].equals("Cargar Imagen")){
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicacion"), COD_SELECCIONA);
                    }
                    else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertaOpciones.show();
    }

    private void tomarFotografia() {
        File fileImagen= new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists(); //indica si la imagen si existe

        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }
        if(isCreada==true){
            nombreImagen  = (System.currentTimeMillis()/1000)+".jpg";
        }

        path = Environment.getExternalStorageDirectory()+ File.separator + RUTA_IMAGEN + File.separator + nombreImagen;

        File imagen = new File (path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        startActivityForResult(intent,COD_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    imagen.setImageURI(miPath);
                    break;
                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Ruta de almacenamiento","Path: " + path );
                        }
                    });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);

                    break;
            }

        }

    }

    /*public void llamarIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
        }
    }*/

    public void onClick(View view) {
        cargarImagen();
    }
}