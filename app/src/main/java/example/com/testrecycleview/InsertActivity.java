package example.com.testrecycleview;

import androidx.appcompat.app.AppCompatActivity;
import example.com.testrecycleview.API.ApiClient;
import example.com.testrecycleview.API.ApiInterface;
import example.com.testrecycleview.Model.Dosen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    ApiInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        final EditText txtId = findViewById(R.id.id_txt);
        final EditText txtNama = findViewById(R.id.nama_txt);
        final EditText txtPelajaran = findViewById(R.id.pelajaran_txt);
        Button insert_button = findViewById(R.id.button_insertData);

        service = ApiClient.getClient().create(ApiInterface.class);
        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dosen dosen = new Dosen(txtId.getText().toString(), txtNama.getText().toString(), txtPelajaran.getText().toString(), null);
                addDosen(dosen);
            }
        });

    }

    public  void addDosen(Dosen dosen) {
        Call<Dosen> call = service.addDosen(dosen);
        call.enqueue(new Callback<Dosen>() {
            @Override
            public void onResponse(Call<Dosen> call, Response<Dosen> response) {
                if(response.body() != null)
                {
                    if(response.body().getErrorCode().equals("00"))
                    {
                        Toast.makeText(InsertActivity.this, "Dosen created successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Dosen> call, Throwable t) {
                Log.e("ERROR", t.getMessage());

            }
        });
    }
}
