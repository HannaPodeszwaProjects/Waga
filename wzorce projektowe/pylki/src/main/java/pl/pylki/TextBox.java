package pl.pylki;

import java.util.Objects;

public class TextBox {


    private BoundingBox boundingBox;
    private Text text;

    public TextBox (BoundingBox boundingBox, Text text)
    {
     this.boundingBox =boundingBox;
     this.text = text;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public Text getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextBox textBox = (TextBox) o;
        return boundingBox.equals(textBox.boundingBox) && text.equals(textBox.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boundingBox, text);
    }
}
