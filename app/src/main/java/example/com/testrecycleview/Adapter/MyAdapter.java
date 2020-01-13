package example.com.testrecycleview.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.Model.Dosen;
import example.com.testrecycleview.R;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    private List<Dosen> dataList;

    public MyAdapter(List<Dosen> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyAdapterViewHolder holder, int position) {
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtId.setText(dataList.get(position).getId());
        holder.txtPelajaran.setText(dataList.get(position).getPelajaran());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtId, txtPelajaran;

        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.txt_id);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtPelajaran = (TextView) itemView.findViewById(R.id.txt_pelajaran);
        }

    }



}