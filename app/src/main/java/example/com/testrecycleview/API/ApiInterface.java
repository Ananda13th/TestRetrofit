package example.com.testrecycleview.API;

import java.util.List;

import example.com.testrecycleview.Model.BaseResponse;
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
    Call<Dosen> updateDosen(@Path("id") String id, @Body Dosen dosen);

    @DELETE("dosens/{id}")
    Call<BaseResponse> deleteDosen(@Path("id") String id);

    @GET("dosens/{id}")
    Call<Dosen> getDetailDosen(@Path("id") String id);

}
