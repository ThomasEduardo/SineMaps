package appsine.com.br.sinemaps;

import appsine.com.br.sinemaps.APIService;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface APIService {



    @GET("latitude/{lat}/longitude/{longitude}/raio/100")
    Call<List<DataReceive>> getSinesComRaio(@Path("latitude") double latitude, @Path("longitude") double longitude);

}
