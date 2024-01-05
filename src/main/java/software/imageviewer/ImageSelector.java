package software.imageviewer;

import static java.lang.Math.abs;

public class ImageSelector {
    private final int width;
    private final Image image;

    public ImageSelector(int width, Image image) {
        this.width = width;
        this.image = image;
    }

    public Image first(int offset){return select(offset);}
    public Image second(int offset) {
        return offset < 0 ? select(offset).next() : select(offset).prev();
    }


    private Image select(int offset) {
        Image image = this.image;
        while (abs(offset) > width){
            offset -= sign(offset) * width;
            image = offset < 0 ? image.next() : image.prev();
        }
        return image;
    }

    private int sign(int offset) {
        return offset < 0 ? -1 : 1;
    }
}
