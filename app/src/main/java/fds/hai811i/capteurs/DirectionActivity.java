package fds.hai811i.capteurs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DirectionActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor acc;
    private TextView direction;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.direction);

        direction = findViewById(R.id.directionTv);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];

            // seuil (pour pas détecter les micro mouvements)
            float threshold = 2.0f;

            if (Math.abs(x) < threshold && Math.abs(y) < threshold) {
                direction.setText("Immobile");
                return;
            }

            if (Math.abs(x) > Math.abs(y)) {
                if (x > 0) {
                    direction.setText("Gauche");
                } else {
                    direction.setText("Droite");
                }
            } else {
                if (y > 0) {
                    direction.setText("Bas");
                } else {
                    direction.setText("Haut");
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (acc != null) {
            sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }
}
