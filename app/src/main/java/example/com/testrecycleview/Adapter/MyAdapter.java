package example.com.testrecycleview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.Model.Dosen;
import example.com.testrecycleview.R;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    private final List<Dosen> dataList;
    private ClickListener clickListener;
    private Context context;

    public MyAdapter(List<Dosen> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    //membuat view baru
    public MyAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new MyAdapterViewHolder(view);
    }

    @Override
    //untuk aksi di setiap list
    public void onBindViewHolder(@NonNull MyAdapter.MyAdapterViewHolder holder, final int position) {
        final Dosen dosen = dataList.get(position);
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtId.setText(dataList.get(position).getId());
        holder.txtPelajaran.setText(dataList.get(position).getPelajaran());
        //holder.img.setImageResource(dataList.get(position).getFoto());
        Glide.with(this.context)
                .load(dataList.get(position).getFoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
        //waktu klik cardview mengambil posisi list
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtId, txtPelajaran;
        private CardView cardView;
        private ImageView image;

        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.txt_id);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtPelajaran = (TextView) itemView.findViewById(R.id.txt_pelajaran);
            cardView = itemView.findViewById(R.id.card_view_dosen);
            image = itemView.findViewById(R.id.image_view);
        }
    }

    public void setOnClick( ClickListener listener) {

        this.clickListener = listener;
    }

}