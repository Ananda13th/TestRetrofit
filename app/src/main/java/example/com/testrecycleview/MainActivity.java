package example.com.testrecycleview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.API.ApiClient;
import example.com.testrecycleview.API.ApiInterface;
import example.com.testrecycleview.Adapter.ClickListener;
import example.com.testrecycleview.Adapter.MyAdapter;
import example.com.testrecycleview.Model.BaseResponse;
import example.com.testrecycleview.Model.Dosen;
import example.com.testrecycleview.Model.ResponseDosen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    //pakai arraylist agar tidak error waktu null
    private List<Dosen> dosenArrayList;
    private ClickListener listener;
    ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button delete_button = findViewById(R.id.button_delete);
        final ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDosen> call = service.getDataDosen();
        call.enqueue(new Callback<ResponseDosen>() {
            @Override
            public void onResponse(Call<ResponseDosen> call, final Response<ResponseDosen> response) {
                if (response.body() == null) {
                    Log.d("dosen", "data kosong");
                } else if(response.body().getErrorCode().equals("00")) {
                    dosenArrayList = response.body().getDosenList();
                    //Inisialisasi Recycleview
                    recyclerView = findViewById(R.id.recycler_view);
                    adapter = new MyAdapter(dosenArrayList, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                    //Menampilkan Toast Saat Klik
                    adapter.setOnClick(new ClickListener() {
                        @Override
                        public void onClickListener(int idDosen) {
                            Dosen dosen = dosenArrayList.get(idDosen);
                            String id_dosen = dosen.getId();
                            //Toast.makeText(MainActivity.this, "diklik "+dosen.getNama(), Toast.LENGTH_SHORT).show();
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
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDosen> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Menyambung", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton add_button = findViewById(R.id.fab_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToInsertActivity = new Intent(getApplicationContext(), InsertActivity.class);
                startActivity(goToInsertActivity);
            }
        });
    }

    public  void deleteDosen(String id) {
        Call<BaseResponse> call = service.deleteDosen(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.body() != null)
                {
                    if(response.body().getErrorCode().equals("00"))
                        Toast.makeText(MainActivity.this, "Dosen deleted!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "Salah pilih!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }

    public void updateUser(String id, Dosen dosen){
        Call<Dosen> call = service.updateDosen(id,dosen);
        call.enqueue(new Callback<Dosen>() {
            @Override
            public void onResponse(Call<Dosen> call, Response<Dosen> response) {
                if(response.body().getErrorCode().equals("00")){
                    Toast.makeText(MainActivity.this, "Dosen Updated!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Dosen> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void detailDosen(final String id) {
        Call<Dosen> call = service.getDetailDosen(id);
        call.enqueue(new Callback<Dosen>() {
            @Override
            public void onResponse(Call<Dosen> call, Response<Dosen> response) {
                if(response.isSuccessful()){

                    String nama = response.body().getNama().toString();
                    String pelajaran = response.body().getPelajaran().toString();
                    String id_dosen = response.body().getId().toString();
                    String foto = response.body().getFoto().toString();
                    //Toast.makeText(MainActivity.this, "Diklik" +pelajaran, Toast.LENGTH_SHORT).show();
                    detailDialog(nama, pelajaran, id_dosen, foto);
                }
            }

            @Override
            public void onFailure(Call<Dosen> call, Throwable t) {

            }
        });
    }

    private AlertDialog deleteConfirmation(final String id)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
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
        return myQuittingDialogBox;
    }

    public void updateDialog(final String id) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Dosen");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.update_layout, null);
        builder.setView(customLayout);
        // add a button
        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                EditText editNama = customLayout.findViewById(R.id.editNama);
                EditText editPelajaran = customLayout.findViewById(R.id.editPelajaran);
                Dosen dosen = new Dosen(editNama.getText().toString(), id, editPelajaran.getText().toString(), null);
                updateUser(id, dosen);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void detailDialog(String nama, String pelajaran, String id,String foto) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.detail_layout, null);
        builder.setView(customLayout);
        // add a button
        TextView view_nama = customLayout.findViewById(R.id.view_nama);
        TextView view_pelajaran = customLayout.findViewById(R.id.view_pelajaran);
        TextView view_id = customLayout.findViewById(R.id.view_id);
        ImageView view_foto = customLayout.findViewById(R.id.view_foto);

        view_nama.setText(nama);
        view_id.setText(id);
        view_pelajaran.setText(pelajaran);
        Glide.with(this).load(foto)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view_foto);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
