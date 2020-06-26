package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.example.splash.adapters.DoctorAdaptador;
import com.example.splash.helpers.QueueUtils;
import com.example.splash.models.Doctores;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView doctoresList;
    DoctorAdaptador doctoresAdaptador;
    QueueUtils.QueueObject queue = null;
    ArrayList<Doctores> items;
    ImageLoader queueImage=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctoresList = findViewById(R.id.doctoresList);
        queue = QueueUtils.getInstance(this.getApplicationContext());
        queueImage=queue.getImageLoader();
        items = new ArrayList<>();
        Doctores.injectDoctoresFromCloud(queue, items, this);
        doctoresAdaptador = new DoctorAdaptador(this, items,queueImage);
       // doctoresAdaptador = new DoctorAdaptador(this, Doctores.getCollection());
        doctoresList.setAdapter(doctoresAdaptador);

        doctoresList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Doctores registro =items.get(position);
                showDetails(registro);
                //Toast.makeText(MainActivity.this,"Persona: "+registro.first_name,Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showDetails(Doctores item){
        Intent o =new Intent(this,DoctorDetalleActivity.class);
        o.putExtra("doctorId", item.id);
        startActivity(o);
    }

    public void refreshList() {
        if (doctoresAdaptador != null) {
            doctoresAdaptador.notifyDataSetChanged();
        }
    }
}