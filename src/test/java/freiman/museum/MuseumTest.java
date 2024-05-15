package freiman.museum;

import com.andrewoid.ApiKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MuseumTest {

    @Test
    void page() {
        //given
        MuseumService service = new MuseumServiceFactory().getService();

        ApiKey apiKey = new ApiKey();
        int pageNum = 10;

        //when
        ArtObjects collection = service.page(
                apiKey.get(),
                pageNum
        ).blockingGet();

        //then
        ArtObject artObject = collection.artObjects[0];
        assertNotNull(artObject.title);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
    }

    @Test
    void query() {
        //given
        MuseumService service = new MuseumServiceFactory().getService();
        ApiKey apiKey = new ApiKey();
        int pageNum = 0;
        String q = "e";

        //when
        ArtObjects collection = service.query(
                apiKey.get(),
                pageNum,
                q
        ).blockingGet();

        //then
        ArtObject artObject = collection.artObjects[0];
        assertNotNull(artObject.title);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
    }

    @Test
    void artist() {
        //given
        MuseumService service = new MuseumServiceFactory().getService();
        ApiKey apiKey = new ApiKey();
        int pageNum = 0;
        String artist = "Joachim Bueckelaer";

        //when
        ArtObjects collection = service.artist(
                apiKey.get(),
                pageNum,
                artist
        ).blockingGet();

        //then
        ArtObject artObject = collection.artObjects[3];
        assertNotNull(artObject.title);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
    }

}
