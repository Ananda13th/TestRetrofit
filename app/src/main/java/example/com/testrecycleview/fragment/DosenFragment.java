package example.com.testrecycleview.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.R;
import example.com.testrecycleview.adapter.ClickListener;
import example.com.testrecycleview.adapter.MyAdapter;
import example.com.testrecycleview.api.ApiClient;
import example.com.testrecycleview.api.ApiInterface;
import example.com.testrecycleview.model.BaseResponse;
import example.com.testrecycleview.model.Dosen;
import example.com.testrecycleview.model.ResponseDosen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DosenFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    //Pakai Arraylist Agar Tidak Error Waktu Data Null
    private List<Dosen> dosenArrayList;
    //Inisialisasi ApiInterface Sebagai Penghubung Activity Dengan ApiClient
    private ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.activity_main, container, false);
        Call<ResponseDosen> call = service.getDataDosen();
        call.enqueue(new Callback<ResponseDosen>() {
            @Override
            public void onResponse(Call<ResponseDosen> call, final Response<ResponseDosen> response) {
                if (response.body() == null) {
                    Log.d("dosen", "data kosong");
                } else if (response.body().getErrorCode().equals("00")) {
                    //Ambil Respon Dari API
                    dosenArrayList = response.body().getDosenList();
                    //Inisialisasi Recycleview
                    recyclerView = myView.findViewById(R.id.recycler_view);
                    adapter = new MyAdapter(dosenArrayList, getActivity());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    //Pengaturan Fungsi Saat Klik Pada Cardview/Tombol
                    adapter.setOnClick(new ClickListener() {
                        @Override
                        public void onClickListener(int idDosen) {
                            Dosen dosen = dosenArrayList.get(idDosen);
                            String id_dosen = dosen.getId();
                            detailDosen(id_dosen);
                        }
                        @Override
                        public void onCLickDeleteButton(int idDosen) {
                            Dosen dosen = dosenArrayList.get(idDosen);
                            String id_dosen = dosen.getId();
                            AlertDialog deleteBox = deleteConfirmation(id_dosen);
                            deleteBox.show();
                        }
                        @Override
                        public void onClickUpdateButton(int idDosen) {
                            Dosen dosen = dosenArrayList.get(idDosen);
                            String id_dosen = dosen.getId();
                            updateDialog(id_dosen);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDosen> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menyambung", Toast.LENGTH_SHORT).show();
            }
        });
        return myView;
    }

    //Delete Dosen
    public void deleteDosen (String id){
        //Ambil Method Dari ApiInterface
        Call<BaseResponse> call = service.deleteDosen(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null) {
                    if (response.body().getErrorCode().equals("00"))
                        Toast.makeText(getActivity(), "Dosen deleted!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Salah pilih!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }

    //Update Data Dosen
    public void updateDosen (String id, Dosen dosen){
        //Ambil Method Dari ApiInterface
        Call<Dosen> call = service.updateDosen(id, dosen);
        call.enqueue(new Callback<Dosen>() {
            @Override
            public void onResponse(Call<Dosen> call, Response<Dosen> response) {
                if (response.body()!= null && response.body().getErrorCode().equals("00")) {
                    Toast.makeText(getActivity(), "Dosen Updated!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(), "Salah pilih!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Dosen> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    //Lihat Detail Dosen yg di Klik
    public void detailDosen ( final String id){
        //Ambil Method Dari ApiInterface
        Call<Dosen> call = service.getDetailDosen(id);
        call.enqueue(new Callback<Dosen>() {
            @Override
            public void onResponse(Call<Dosen> call, Response<Dosen> response) {
                if (response.body()!= null && response.isSuccessful()) {

                    String nama = response.body().getNama().toString();
                    String pelajaran = response.body().getPelajaran().toString();
                    String id_dosen = response.body().getId().toString();
                    String foto = response.body().getFoto().toString();
                    detailDialog(nama, pelajaran, id_dosen, foto);
                }
                else
                    Toast.makeText(getActivity(), "Salah pilih!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Dosen> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Alert Dialog Hapus Dosen
    private AlertDialog deleteConfirmation ( final String id){
        AlertDialog deleteDialog = new AlertDialog.Builder(getActivity())
            // set message, title, and icon
            .setTitle("Delete")
            .setMessage("Do you want to Delete")
            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    deleteDosen(id);
                }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .create();
        return deleteDialog;
    }

    //Dialog Update Dosen
    public void updateDialog ( final String id){
        // Inisialisasi Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Dosen");
        // Pemilihan Layout Yang Dipakai
        final View customLayout = getLayoutInflater().inflate(R.layout.update_layout, null);
        builder.setView(customLayout);
        // Pemberian Tombol Opsi
        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Inisialisasi Elemen Pada Layout
                EditText editNama = customLayout.findViewById(R.id.editNama);
                EditText editPelajaran = customLayout.findViewById(R.id.editPelajaran);
                Dosen dosen = new Dosen(editNama.getText().toString(), id, editPelajaran.getText().toString(), null);
                updateDosen(id, dosen);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void detailDialog (String nama, String pelajaran, String id, String foto){
        // Inisialisasi Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Pemilihan Layout Yang Dipakai
        final View customLayout = getLayoutInflater().inflate(R.layout.detail_layout, null);
        builder.setView(customLayout);
        //Inisialisasi Elemen Pada Layout
        TextView view_nama = customLayout.findViewById(R.id.view_nama);
        TextView view_pelajaran = customLayout.findViewById(R.id.view_pelajaran);
        TextView view_id = customLayout.findViewById(R.id.view_id);
        ImageView view_foto = customLayout.findViewById(R.id.view_foto);
        //Pengambilan Data Dari Input USer
        view_nama.setText(nama);
        view_id.setText(id);
        view_pelajaran.setText(pelajaran);
        Glide.with(getActivity()).load(foto)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view_foto);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
