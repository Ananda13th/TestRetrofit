package example.com.testrecycleview.API;

import java.util.List;

import example.com.testrecycleview.Model.Dosen;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("TestApiDosen")
    Call<List<Dosen>> getDataDosen();
}
