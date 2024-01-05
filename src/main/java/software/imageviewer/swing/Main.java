package software.imageviewer.swing;

import software.imageviewer.Image;
import software.imageviewer.ImageDisplay;
import software.imageviewer.ImageLoader;
import software.imageviewer.ImagePresenter;

import java.io.File;

public class Main {
    public static final String root = "bus";

    public static void main(String[] args) {
        Image image = new FileImageLoader(new File(root)).load();
        MainFrame frame = new MainFrame();
        ImageDisplay imageDisplay = frame.imageDisplay();
        new ImagePresenter(image,imageDisplay);
        frame.setVisible(true);
    }
}
