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
        toSensorList.setOnClickListener(view -> goToSensorList("present"));

        Button toMissingSensorList = findViewById(R.id.toMissingSensorList);
        toMissingSensorList.setOnClickListener(view -> goToSensorList("missing"));

        Button toAccelerometer = findViewById(R.id.toAccelerometer);
        toAccelerometer.setOnClickListener(view -> goTo(AccelerometerActivity.class));

        Button toDirection = findViewById(R.id.toDirection);
        toDirection.setOnClickListener(view -> goTo(DirectionActivity.class));

        Button toFlash = findViewById(R.id.toFlash);
        toFlash.setOnClickListener(view -> goTo(FlashActivity.class));

        Button toProximity = findViewById(R.id.toProximity);
        toProximity.setOnClickListener(view -> goTo(ProximityActivity.class));

        Button toGPS = findViewById(R.id.toGPS);
        toGPS.setOnClickListener(view -> goTo(GPSActivity.class));
    }

    private void goToSensorList(String mode) {
        Intent sensorList = new Intent(MainActivity.this, SensorActivity.class);
        sensorList.putExtra("MODE", mode);
        startActivity(sensorList);
    }

    private void goTo(Class<?> clazz) {
        Intent i = new Intent(MainActivity.this, clazz);
        startActivity(i);
    }
}