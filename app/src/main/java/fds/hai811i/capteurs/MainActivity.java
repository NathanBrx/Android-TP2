package fds.hai811i.capteurs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.mainpage);

        Button toSensorList = findViewById(R.id.toSensorList);
        toSensorList.setOnClickListener(view -> {
            Intent sensorList = new Intent(MainActivity.this, SensorActivity.class);
            startActivity(sensorList);
        });
    }
}