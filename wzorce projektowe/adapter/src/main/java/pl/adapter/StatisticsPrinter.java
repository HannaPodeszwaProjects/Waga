package pl.adapter;

public class StatisticsPrinter {
    public void print(Statistics statistics)
    {
        System.out.println("Min value: " + statistics.getMinValue());
        System.out.println("Max value: " + statistics.getMaxValue());
        System.out.println("Average: " + statistics.getAverage());
        System.out.println("Count of element: " + statistics.getElementCount());
        System.out.println("Count of unique element: " + statistics.getUniqueElementCount());
    }
}
