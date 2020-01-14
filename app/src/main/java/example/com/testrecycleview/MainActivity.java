package example.com.testrecycleview;

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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                            Toast.makeText(MainActivity.this, "diklik "+dosen.getNama(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCLickDeleteButton(int idDosen) {
                            Dosen dosen = dosenArrayList.get(idDosen);
                            String id_dosen = dosen.getId();
                            //Toast.makeText(MainActivity.this, "diklik "+id_dosen, Toast.LENGTH_SHORT).show();
                            deleteDosen(id_dosen);
                        }
                    });

//
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
        Button add_button = (Button) findViewById(R.id.button_add);
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
}
