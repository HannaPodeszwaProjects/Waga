package pl.pylki;

public class Main {
    public static void main(String[] args) {

        final int count = 5000;
        final int imageWidth = 200;
        final int imageHeight = 600;

        final Text[] textsAvailable = new Text[] {
                new Text("Text 1"),
                new Text("Text 143"),
                new Text("Text 4543"),
                new Text("Text 13")
        };

        final ObjectDetector objectDetector = new ObjectDetector();
        final Detection detection = objectDetector.detect(
                new Image(imageWidth, imageHeight),
                textsAvailable,
                count
        );

        print(detection);
    }

    private static void print(Detection detection) {
        System.out.println("Detected text:");
        int i=1;
            for (TextBox textBox : detection.getTextBoxList()) {
                System.out.println(i+". In: Left: " + textBox.getBoundingBox().getLeft() +
                        " Right: " + textBox.getBoundingBox().getRight() +
                        " Top: " + textBox.getBoundingBox().getTop() +
                        " Bottom: " + textBox.getBoundingBox().getBottom());
                System.out.println(textBox.getText().getValue());
                i++;
            }

    }
}
