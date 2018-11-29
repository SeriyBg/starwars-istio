package demo.istio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

class ImageService {

    private final String imageType;
    private final String extension;

    ImageService(String imageType, String extension) {
        this.imageType = imageType;
        this.extension = extension;
    }

    InputStream findImage(String image) throws IOException {
        var classLoader = ClassLoader.getSystemClassLoader();
        var resource = classLoader.getResourceAsStream(imageType + "/" + image + "." + extension);
        if (resource == null) {
            resource = classLoader.getResourceAsStream("default." + extension);
        }
        return Objects.requireNonNull(resource);
    }
}
