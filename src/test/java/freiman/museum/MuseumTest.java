package freiman.museum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MuseumTest {
    @Test
    void collection() {
        //given
        MuseumService service = new MuseumServiceFactory().getService();

        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();

        //when
        Collection collection = service.collection(
                keyString,
                10
        ).blockingGet();

        //then
        artObjects artObjects = collection.artObjects;
        assertNotNull(artObjects.title);
        assertNotNull(artObjects.longTitle);
        assertNotNull(artObjects.principalOrFirstMaker);
        assertNotNull(artObjects.webImage);
    }
}
