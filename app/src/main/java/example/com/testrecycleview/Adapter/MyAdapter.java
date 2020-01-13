package example.com.testrecycleview.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.Model.Dosen;
import example.com.testrecycleview.R;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    private final List<Dosen> dataList;
    private ClickListener clickListener;

    public MyAdapter(List<Dosen> dataList) {
        this.dataList = dataList;
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

        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.txt_id);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtPelajaran = (TextView) itemView.findViewById(R.id.txt_pelajaran);
            cardView = itemView.findViewById(R.id.card_view_dosen);
        }
    }

    public void setOnClick( ClickListener listener) {

        this.clickListener = listener;
    }

}