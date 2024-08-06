package vichungbach.com.example.shopgaminggear.retrofit;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vichungbach.com.example.shopgaminggear.model.pdTDModel;
import vichungbach.com.example.shopgaminggear.model.productModel;
import vichungbach.com.example.shopgaminggear.model.userModel;

public interface ApiGaminggearshop {
    @GET("getloaisp.php")
    Observable<productModel> getloaiSp();

    @GET("getsp.php")
    Observable<pdTDModel>  getProduct();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<pdTDModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );


    @POST("dangky.php")
    @FormUrlEncoded
    Observable<userModel> dangky(
            @Field("email") String email,
            @Field("password") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile
    );


    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<userModel> dangnhap(
            @Field("email") String email,
            @Field("password") String pass);

}
