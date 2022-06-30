package com.medsminder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.medsminder.model.Medic;

import java.util.Objects;

public class MedicsAdapter extends ListAdapter<Medic, MedicsAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private static final DiffUtil.ItemCallback<Medic> DIFF_CALLBACK = new DiffUtil.ItemCallback<Medic>() {
        @Override
        public boolean areItemsTheSame(@NonNull Medic oldItem, @NonNull Medic newItem) {
            return oldItem.getName() == newItem.getName();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Medic oldItem, @NonNull Medic newItem) {
            return (Objects.equals(oldItem.getName(), newItem.getName())
                    && Objects.equals(oldItem.getDisease(), newItem.getDisease())
                    && Objects.equals(oldItem.getConsumingQuantity(), newItem.getConsumingQuantity()));
        }
    };

    public MedicsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Medic medic = getItem(position);

        TextView medicName = holder.medicName;
        medicName.setText(medic.getName());

        TextView disease = holder.disease;
        disease.setText(medic.getDisease());

        TextView medicQuantity = holder.comnsumingQuantity;
        medicQuantity.setText(medic.consumingQuantity);


        ImageView medicColor = holder.medicColourID;
        medicColor.setImageBitmap(getCircleBitmap(medicColor, medic.getMedicColourID()));

        /* getting the medicine schedule from arraylist and
            setting medicine schedules in the textview */
        String s = "";
        for (String h :
                medic.getSchedule()) {
            s = s + "  " + h;
            s = s.replaceAll("\\|", "   ");
        }

        TextView schedule = holder.schedule;
        schedule.setText(s);


    }

    public Medic getTaskAtPosition(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Medic medic);
    }

    public Bitmap getCircleBitmap(ImageView imageView, String color) {
        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(color.toLowerCase()));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(8);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 5, paint);
        return bitmap;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView medicName;
        TextView disease;
        TextView comnsumingQuantity;
        ImageView medicColourID;
        TextView schedule;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            medicName = itemView.findViewById(R.id.tv_medic_name);
            disease = itemView.findViewById(R.id.tv_disease);
            comnsumingQuantity = itemView.findViewById(R.id.tv_medic_quatity);
            medicColourID = itemView.findViewById(R.id.iv_medic_colourID);
            schedule = itemView.findViewById(R.id.tv_schedule);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    Toast.makeText(itemView.getContext(), "you have clicked:" + position, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
