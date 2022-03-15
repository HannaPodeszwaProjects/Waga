package pl.adapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter implements Statistics {
private List <Element>list;

Adapter(List l)
{
    list=l;
}
    @Override
    public double getMinValue() {
    double min = Double.MAX_VALUE;
    for(Element element:list)
    {
        if(element.getValue()<min)
        {
            min=element.getValue();
        }
    }
        return min;
    }

    @Override
    public double getMaxValue() {
        double max = Double.MIN_VALUE;
        for(Element element:list)
        {
            if(element.getValue()>max)
            {
                max=element.getValue();
            }
        }
        return max;
    }

    @Override
    public double getAverage() {
    double sum = 0;
        for(Element element:list)
        {
            sum += element.getValue();
        }
        return sum/list.size();
    }

    @Override
    public int getElementCount() {
        return list.size();
    }

    @Override
    public int getUniqueElementCount() {
List  uniqueList = new ArrayList<>();
for(Element element: list)
{
   if(!(uniqueList.contains(element.getValue())))
   {
       uniqueList.add(element.getValue());
   }
}
        return uniqueList.size();
    }
}
