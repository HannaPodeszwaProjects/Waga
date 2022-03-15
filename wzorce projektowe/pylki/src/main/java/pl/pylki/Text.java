package pl.pylki;

import java.util.Objects;

public class Text {
   private String value;

    public Text(String value)
    {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return value.equals(text.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
