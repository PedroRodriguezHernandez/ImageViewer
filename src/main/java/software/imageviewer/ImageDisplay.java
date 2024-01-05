package software.imageviewer;

public interface ImageDisplay {
   int width();
   void clear();
   void paint(String image, int offset);
   void on(Dragged dragged);
   void on(Realeased realeased);

    interface Dragged {
        Dragged Null = offset -> {};
        void to(int offset);
    }

    interface Realeased {
        Realeased Null = offset -> {};
        void at(int offset);
    }
}
