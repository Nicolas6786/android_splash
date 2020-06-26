package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.splash.helpers.QueueUtils;
import com.example.splash.models.Doctores;

public class ReservarActivity extends AppCompatActivity {
    QueueUtils.QueueObject queue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        queue = QueueUtils.getInstance(this.getApplicationContext());
        final EditText txtNombre=findViewById(R.id.txtNombre);
        final EditText txtApellido=findViewById(R.id.txtApellido);
        Button btnCrear=findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doctores doctorObject=new Doctores(0,txtNombre.getText().toString(),txtApellido.getText().toString(),"");
                Doctores.postDoctorFromLocal(queue,doctorObject,ReservarActivity.this);
            }
        });
    }
}