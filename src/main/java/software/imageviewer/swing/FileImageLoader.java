package software.imageviewer.swing;

import software.imageviewer.Image;
import software.imageviewer.ImageLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

public class FileImageLoader implements ImageLoader {

    private final File[] file;
    private static final Set<String> imageExtensions = Set.of(".jpg", ".png");
    public FileImageLoader(File folder) {
        this.file = folder.listFiles(isImage());
    }

    private FilenameFilter isImage() {
        return ((dir, name) -> imageExtensions.stream().anyMatch(name::endsWith));
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    private Image imageAt(int i) {
        return new Image() {
            @Override
            public String name() {
                return file[i].getAbsolutePath();
            }

            @Override
            public Image next() {
                return imageAt((i+1) % file.length);
            }

            @Override
            public Image prev() {
                return imageAt((i-1 + file.length) % file.length);
            }
        };
    }
}
