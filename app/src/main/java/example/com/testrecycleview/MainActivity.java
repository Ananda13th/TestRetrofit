package example.com.testrecycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.API.ApiClient;
import example.com.testrecycleview.API.ApiInterface;
import example.com.testrecycleview.Adapter.ClickListener;
import example.com.testrecycleview.Adapter.MyAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDosen> call = service.getDataDosen();
        call.enqueue(new Callback<ResponseDosen>() {
            @Override
            public void onResponse(Call<ResponseDosen> call, Response<ResponseDosen> response) {
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
                            Toast.makeText(MainActivity.this, "diklik "+dosen.getFoto(), Toast.LENGTH_SHORT).show();
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
        Button add_button = (Button) findViewById(R.id.button_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToInsertActivity = new Intent(getApplicationContext(), InsertActivity.class);
                startActivity(goToInsertActivity);
            }
        });
    }
}
