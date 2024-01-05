package software.imageviewer;

public interface Image {
    String name();
    Image next();
    Image prev();
}
