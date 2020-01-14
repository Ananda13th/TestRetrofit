package example.com.testrecycleview.API;

import java.util.List;

import example.com.testrecycleview.Model.Dosen;
import example.com.testrecycleview.Model.ResponseDosen;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("dosens")
    Call<ResponseDosen> getDataDosen();

    @POST("dosen")
    Call<Dosen> addDosen(@Body Dosen dosen);

    @PUT("dosens/{id}")
    Call<Dosen> updateUser(@Path("id") int id, @Body Dosen dosen);

    @DELETE("delete/{id}")
    Call<Dosen> deleteUser(@Path("id") int id);

}
