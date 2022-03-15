package pl.adapter;

public class Element {

    private double value;
    private TypeEnum type;

    Element(double v, TypeEnum t)
    {
        value=v;
        type=t;
    }

    public double getValue() {
        return value;
    }

    public TypeEnum getType() {
        return type;
    }
}
