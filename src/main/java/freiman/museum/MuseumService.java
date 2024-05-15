package freiman.museum;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MuseumService {
    @GET("/api/en/collection")
    Single<Collection> collection(
            @Query("key") String key,
            @Query("p") int p
    );
}
