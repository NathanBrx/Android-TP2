package fds.hai811i.capteurs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MissingSensorAdapter extends RecyclerView.Adapter<MissingSensorAdapter.SensorViewHolder> {

    private final List<String> sensorList;

    public MissingSensorAdapter(List<String> sensorList) {
        this.sensorList = sensorList;
    }

    @NonNull
    @Override
    public MissingSensorAdapter.SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.missingsensor, parent, false);
        return new MissingSensorAdapter.SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MissingSensorAdapter.SensorViewHolder holder, int position) {
        String sensor = sensorList.get(position);

        holder.tvType.setText(sensor);
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    public static class SensorViewHolder extends RecyclerView.ViewHolder {
        TextView tvType;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvSensorType);
        }
    }
}
