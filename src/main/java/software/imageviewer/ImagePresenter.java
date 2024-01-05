package software.imageviewer;
import software.imageviewer.ImageDisplay.*;

import static java.lang.Math.abs;

public class ImagePresenter {
    private Image image;
    private final ImageDisplay display;

    public ImagePresenter(Image image, ImageDisplay display) {
        this.image = image;
        this.display = display;
        this.display.on((Dragged) this::dragged);
        this.display.on((Realeased) this::realeased);
        this.display.clear();
        this.display.paint(image.name(), 0);
    }

    private void realeased(int offset) {
        ImageSelector imageSelector = new ImageSelector(display.width(), image);
        image = shouldChangeWith(offset) ? imageSelector.second(offset) : image;
        display.clear();
        display.paint(image.name(),0);
    }

    private boolean shouldChangeWith(int offset) {
        return abs(offset) > display.width()/2;
    }

    private void dragged(int offset) {
        display.clear();
        ImageSelector imageSelector = new ImageSelector(display.width(), image);
        display.paint(imageSelector.first(offset).name(),
                offset - (offset > display.width() ? display.width() : 0));
        display.paint(imageSelector.second(offset).name(),
                offset - sign(offset) * display.width() - (offset > display.width() ? display.width() : 0));
    }



    private int sign(int offset) {
        return offset < 0 ? -1 : 1;
    }
}
