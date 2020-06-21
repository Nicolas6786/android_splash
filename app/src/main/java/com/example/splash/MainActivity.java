package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.splash.adapters.DoctorAdaptador;
import com.example.splash.helpers.QueueUtils;
import com.example.splash.models.Doctores;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView doctoresList;
    DoctorAdaptador doctoresAdaptador;
    QueueUtils.QueueObject queue = null;
    ArrayList<Doctores> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctoresList = findViewById(R.id.doctoresList);
        queue = QueueUtils.getInstance(this.getApplicationContext());
        items = new ArrayList<>();
        Doctores.injectContactsFromCloud(queue, items, this);
        doctoresAdaptador = new DoctorAdaptador(this, items);
       // doctoresAdaptador = new DoctorAdaptador(this, Doctores.getCollection());
        doctoresList.setAdapter(doctoresAdaptador);
    }
    public void refreshList() {
        if (doctoresAdaptador != null) {
            doctoresAdaptador.notifyDataSetChanged();
        }
    }
}