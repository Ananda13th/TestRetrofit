package example.com.testrecycleview.fragment;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.testrecycleview.InsertActivity;
import example.com.testrecycleview.MainActivity;
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

public class HomeFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home, container, false);
        return myView;
    }
}
