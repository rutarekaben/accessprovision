package rw.iraguha.secureaccess.utils;

import android.graphics.Bitmap;

public class ImageHolder {

    private static ImageHolder instance;

    public synchronized static ImageHolder get() {
        if (instance == null) {
            instance = new ImageHolder ();
        }
        return instance;
    }

    private Bitmap image;

    public void setLargeData(Bitmap image) {
        this.image = image;
    }

    public Bitmap getLargeData() {
        return image.getByteCount() > 0 ? image : null;
    }
}