package demo.istio;

import java.io.File;

public class ImageService {

    private final String imageType;
    private final String extension;

    public ImageService(String imageType, String extension) {
        this.imageType = imageType;
        this.extension = extension;
    }

    public File findImage(String image) {
        var classLoader = ImageService.class.getClassLoader();
        var resource = classLoader.getResource(imageType + "/" + image + "." + extension);
        if (resource == null) {
            return new File("default." + extension);
        }
        return new File(resource.getPath());
    }
}
