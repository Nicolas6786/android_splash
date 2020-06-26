package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.splash.helpers.QueueUtils;
import com.example.splash.models.Doctores;

public class DoctorDetalleActivity extends AppCompatActivity {
    int doctorId;
    Doctores doctor=new Doctores(0,"","","");
    QueueUtils.QueueObject queue = null;
    ImageLoader queueImage=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detalle);

        Button btnReservar=findViewById(R.id.btnReservar);
        Intent o=getIntent();
        doctorId=o.getIntExtra("doctorId",-1);
        if(doctorId<=-1){
            Toast.makeText(this,"No se selecciono un doctor",Toast.LENGTH_SHORT).show();
        }
        //Consumimos informacion detallada
        doctor.id=doctorId;
        queue = QueueUtils.getInstance(this.getApplicationContext());
        queueImage=queue.getImageLoader();
        Doctores.injectDoctorFromCloud(queue,doctor,this);


        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o =new Intent(DoctorDetalleActivity.this,ReservarActivity.class);
                startActivity(o);
            }
        });
    }

    public void refresh(){
        TextView x=findViewById(R.id.txtNombre);
        x.setText(doctor.first_name);
        NetworkImageView imgFoto=findViewById(R.id.imgFoto);
        imgFoto.setImageUrl(doctor.url_image,queueImage);
    }
}