package software.imageviewer.swing;

import software.imageviewer.ImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final List<PaintOrder> orders;
    private int shiftStart;
    private Dragged dragged = Dragged.Null;
    private Realeased realeased = Realeased.Null;
    private BufferedImage bitmap;

    public SwingImageDisplay() {
        this.orders =  new ArrayList<>();
        this.addMouseListener(mouselistener());
        this.addMouseMotionListener(MouseMotionListeners());
    }

    private MouseMotionListener MouseMotionListeners() {
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {dragged.to(e.getX() - shiftStart);}

            @Override
            public void mouseMoved(MouseEvent e) {}
        };
    }

    private MouseListener mouselistener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {shiftStart = e.getX();}

            @Override
            public void mouseReleased(MouseEvent e) {realeased.at(e.getX() - shiftStart);}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };
    }

    @Override
    public int width() {
        return this.getWidth();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (PaintOrder order : orders) {
            this.bitmap = load(order.image);
            Resizer resizer = new Resizer(new Dimension(this.getWidth(),this.getHeight()));
            Dimension resized = resizer.resize(new Dimension(bitmap.getWidth(), bitmap.getHeight()));
            int y = (this.getHeight() - resized.height) / 2;
            int x =  order.offset + (width() - resized.width) / 2;
            System.out.println(order.offset);
            g.drawImage(this.bitmap, x, y, resized.width, resized.height,null);
        }
    }

    public static class Resizer {
        private final Dimension dimension;

        public Resizer(Dimension dimension) {
            this.dimension = dimension;
        }

        public Dimension resize(Dimension dimension) {
            if (dimension.width <= this.dimension.width && dimension.height <= this.dimension.height) {
                return dimension;
            } else {
                double aspectRatio = (double) dimension.width / dimension.height;
                double panelAspectRatio = (double) this.dimension.width / this.dimension.height;

                if (aspectRatio > panelAspectRatio) {
                    return new Dimension(this.dimension.width, (int) (this.dimension.width / aspectRatio));
                } else {
                    return new Dimension((int) (this.dimension.height * aspectRatio), this.dimension.height);
                }
            }
        }
    }


    private BufferedImage load(String path) {
        try{
            return ImageIO.read(new File(path));
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void clear() {
        orders.clear();
        repaint();
    }


    @Override
    public void paint(String image, int offset) {
        orders.add(new PaintOrder(image, offset));
        repaint();
    }

    @Override
    public void on(Dragged dragged) {
        this.dragged = dragged != null ? dragged : Dragged.Null;
    }

    @Override
    public void on(Realeased realeased) {
        this.realeased = realeased != null ? realeased : Realeased.Null;
    }

    private record PaintOrder(String image, int offset) {}
}
