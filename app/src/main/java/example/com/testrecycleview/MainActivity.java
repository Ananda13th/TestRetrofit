package example.com.testrecycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.API.ApiClient;
import example.com.testrecycleview.API.ApiInterface;
import example.com.testrecycleview.Adapter.ClickListener;
import example.com.testrecycleview.Adapter.MyAdapter;
import example.com.testrecycleview.Model.Dosen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Dosen> dosenArrayList = new ArrayList<>();
    //agar tidak error waktu null
    private ClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Dosen>> call = service.getDataDosen();
        call.enqueue(new Callback<List<Dosen>>() {
            @Override
            public void onResponse(Call<List<Dosen>> call, Response<List<Dosen>> response) {
                if (response.body() == null) {
                    Log.d("dosen", "data kosong");
                } else {
                    dosenArrayList = response.body();
                    recyclerView = findViewById(R.id.recycler_view);
                    adapter = new MyAdapter(dosenArrayList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(adapter);
                    adapter.setOnClick(new ClickListener() {
                        @Override
                        public void onClickListener(int idDosen) {
                            Dosen dosen = dosenArrayList.get(idDosen);
                            Toast.makeText(MainActivity.this, "diklik "+dosen.getId(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.d("dosen", "data masuk : " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Dosen>> call, Throwable t) {
                Log.d("dosen", t.getMessage());
            }
        });


    }
}
