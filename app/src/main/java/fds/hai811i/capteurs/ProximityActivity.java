package fds.hai811i.capteurs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor prox;
    private ImageView indicateur;
    private TextView currentDistance;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.proximity);

        indicateur = findViewById(R.id.indicateur);
        currentDistance = findViewById(R.id.currentDistance);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        prox = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (prox == null) {
            Toast.makeText(this, "Pas de capteur de proximité", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];
            float maxDistance = prox.getMaximumRange();

            if (distance < maxDistance) {
                indicateur.setImageResource(R.drawable.magnifyingglass);
                currentDistance.setText("PROCHE");
            } else {
                indicateur.setImageResource(R.drawable.binoculars);
                currentDistance.setText("LOIN");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prox != null) {
            sm.registerListener(this, prox, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }
}
