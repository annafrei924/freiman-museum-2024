package freiman.museum;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MuseumService {

    @GET("/api/en/collection")
    Single<ArtObjects> page(
            @Query("key") String key,
            @Query("p") int pageNum
    );

    @GET("/api/en/collection")
    Single<ArtObjects> query(
            @Query("key") String key,
            @Query("p") int pageNum,
            @Query("q") String query
    );

    @GET("/api/en/collection")
    Single<ArtObjects> artist(
            @Query("key") String key,
            @Query("p") int pageNum,
            @Query("involvedMaker") String artist
    );
}
