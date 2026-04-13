package api;

import java.util.List;

import dto.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {
    @GET("users")
    Call<List<UserResponse>> getUsers();
}