package fds.hai811i.capteurs;

import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private final List<Sensor> sensorList;

    public SensorAdapter(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sensor, parent, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        Sensor sensor = sensorList.get(position);

        holder.tvName.setText(sensor.getName());
        holder.tvVendor.setText(sensor.getVendor());

        holder.tvType.setText(String.format(Locale.getDefault(), "Type: %d", sensor.getType()));
        holder.tvVersion.setText(String.format(Locale.getDefault(), "Version: %d", sensor.getVersion()));

        holder.tvPower.setText(String.format(Locale.getDefault(), "Power used: %.2f mA", sensor.getPower()));
        holder.tvResolution.setText(String.format(Locale.getDefault(), "Resolution: %.4f", sensor.getResolution()));

        holder.tvMaxRange.setText(String.format(Locale.getDefault(), "Max Range: %.2f", sensor.getMaximumRange()));
        holder.tvMinDelay.setText(String.format(Locale.getDefault(), "Delay: %d µs", sensor.getMinDelay()));
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    public static class SensorViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvVendor, tvType, tvVersion, tvPower, tvResolution, tvMaxRange, tvMinDelay;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvSensorName);
            tvVendor = itemView.findViewById(R.id.tvSensorVendor);
            tvType = itemView.findViewById(R.id.tvType);
            tvVersion = itemView.findViewById(R.id.tvVersion);
            tvPower = itemView.findViewById(R.id.tvPower);
            tvResolution = itemView.findViewById(R.id.tvResolution);
            tvMaxRange = itemView.findViewById(R.id.tvMaxRange);
            tvMinDelay = itemView.findViewById(R.id.tvMinDelay);
        }
    }
}
