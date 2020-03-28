package com.makeitation.tasbihpintar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    public ArrayList<DataController> list;

    public DataAdapter(ArrayList<DataController> list){
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DataController data = list.get(position);
        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getGambar())
                .into(holder.gambar);
        holder.number.setText(String.valueOf(data.numberItem));
        holder.namaDzikir.setText(data.namaDzikir);
        holder.keterangan.setText(data.keterangan);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        ImageView gambar;
        TextView number, namaDzikir, keterangan;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.img_feature);
            number = itemView.findViewById(R.id.item_number);
            namaDzikir = itemView.findViewById(R.id.nama_dzikir);
            keterangan = itemView.findViewById(R.id.keterangan);

        }
    }
}
