package fds.hai811i.capteurs;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SensorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensorlist);

        TextView title = findViewById(R.id.listTitle);

        Intent intent = getIntent();
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Integer> typesToTest = List.of(4,6,1,21,13,15,9,42,31,5,10,2,30,8,12,11,17,18,19);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<Integer> sensorTypeList = sensorList.stream().map(Sensor::getType).collect(Collectors.toList());

        RecyclerView recyclerView = findViewById(R.id.rvSensorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (intent != null) {
            switch (Objects.requireNonNull(intent.getStringExtra("MODE"))) {
                case "present" :
                    title.setText("Liste des capteurs présents");
                    SensorAdapter sAdapter = new SensorAdapter(sensorList);
                    recyclerView.setAdapter(sAdapter);
                    break;
                case "missing" :
                    title.setText("Liste des capteurs absents");
                    List<String> missingSensorList = new ArrayList<>();
                    for (Integer type : typesToTest) {
                        if (!sensorTypeList.contains(type)) {
                            String sensorName = "Unknown ID: " + type;

                            for (Field field : Sensor.class.getDeclaredFields()) {
                                try {
                                    if (field.getType() == int.class && field.getName().startsWith("TYPE_") && field.getInt(null) == type) {
                                        sensorName = field.getName();
                                        break;
                                    }
                                } catch (Exception e) {
                                    System.err.println(e.getMessage());
                                }
                            }
                            missingSensorList.add(sensorName);
                        }
                    }
                    MissingSensorAdapter msAdapter = new MissingSensorAdapter(missingSensorList);
                    recyclerView.setAdapter(msAdapter);
                    break;
                default:
                    break;
            }
        }
    }
}