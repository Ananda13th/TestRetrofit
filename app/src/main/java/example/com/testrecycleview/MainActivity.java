package example.com.testrecycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.api.ApiClient;
import example.com.testrecycleview.api.ApiInterface;
import example.com.testrecycleview.adapter.ClickListener;
import example.com.testrecycleview.adapter.MyAdapter;
import example.com.testrecycleview.fragment.DosenFragment;
import example.com.testrecycleview.fragment.HomeFragment;
import example.com.testrecycleview.fragment.WorkFragment;
import example.com.testrecycleview.model.BaseResponse;
import example.com.testrecycleview.model.Dosen;
import example.com.testrecycleview.model.ResponseDosen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    ActionBar toolbar;
    //pakai arraylist agar tidak error waktu null
    private List<Dosen> dosenArrayList;
    private ClickListener listener;
    FragmentManager fragment = getSupportFragmentManager();
    ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fragment.beginTransaction().replace(R.id.fragmentLayout, new HomeFragment()).commit();
                        break;
                    case R.id.navigation_artists:
                        fragment.beginTransaction().replace(R.id.fragmentLayout, new DosenFragment()).commit();
                        break;
                    case R.id.navigation_works:
                        fragment.beginTransaction().replace(R.id.fragmentLayout, new WorkFragment()).commit();
                        break;
                }
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

}
