package pl.pylki;

import java.util.Random;

public abstract class Detector {
    public Detection detect(Image image, Text[] text, int count)
    {
        Random rand = new Random();
        Detection detected = new Detection();

        for(int i=0; i<count; i++) {
            //random bounding of detected text
            int left = rand.nextInt(image.getWidth());
            int right = 1 + left + rand.nextInt(image.getWidth() - left);
            int top = rand.nextInt(image.getHeight());
            int bottom = 1 + top + rand.nextInt(image.getHeight() - top);
            BoundingBox boundingBox = new BoundingBox(left, right, top, bottom);
            //random detected text
            int whichText = rand.nextInt(text.length);

            detected = detected.addTextBox(boundingBox, text[whichText]);
        }
        return detected;
    }
}
