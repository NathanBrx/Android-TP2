package fds.hai811i.capteurs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FlashActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor acc;
    private CameraManager cm;
    private String camera;
    private TextView flashState;

    private boolean isFlashOn = false;

    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;

    // seuil (pour éviter d'allumer le flash avec des secousses trop faibles)
    private static final int SHAKE_THRESHOLD = 12;

    // cooldown (pour éviter le clignotement)
    private long lastShakeTime = 0;
    private static final int SHAKE_COOLDOWN_MS = 1000; // 1 seconde entre chaque action

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.flash);

        flashState = findViewById(R.id.state);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (cm.getCameraIdList().length > 0) {
                camera = cm.getCameraIdList()[0];
            }
        } catch (CameraAccessException e) {
            System.err.println(e.getMessage());
            flashState.setText("Erreur caméra");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            lastAcceleration = currentAcceleration;
            currentAcceleration = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = currentAcceleration - lastAcceleration;

            acceleration = acceleration * 0.9f + delta;

            if (acceleration > SHAKE_THRESHOLD) {
                long now = System.currentTimeMillis();

                if ((now - lastShakeTime) > SHAKE_COOLDOWN_MS) {
                    lastShakeTime = now;
                    toggleFlash();
                }
            }
        }
    }

    private void toggleFlash() {
        if (camera == null) return;

        try {
            isFlashOn = !isFlashOn;

            cm.setTorchMode(camera, isFlashOn);

            String message = isFlashOn ? "Flash Allumé" : "Flash Éteint";
            flashState.setText(message);

        } catch (CameraAccessException e) {
            System.err.println(e.getMessage());
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
        if (isFlashOn) {
            toggleFlash();
        }
    }
}
