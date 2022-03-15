package pl.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random rand = new Random();
        StatisticsPrinter printer = new StatisticsPrinter();
        List<Element> list= new ArrayList<>();

        //add elements to list
        for(int i=0;i<20;i++)
        {
            Element newElement1= new Element(rand.nextDouble() *100, TypeEnum.TYPE_1);
            Element newElement2= new Element(rand.nextDouble() *100, TypeEnum.TYPE_2);
            list.add(newElement1);
            list.add(newElement2);
        }
        //add non unique elements
        Element newElement2= new Element(1, TypeEnum.TYPE_2);
        list.add(newElement2);
        newElement2= new Element(1, TypeEnum.TYPE_2);
        list.add(newElement2);
         newElement2= new Element(1, TypeEnum.TYPE_2);
        list.add(newElement2);

        System.out.println("List:");
        for(Element element:list)
        {
            System.out.println(element.getType() + " " + element.getValue());
        }

        Adapter listAdapter = new Adapter(list);
        System.out.println("\nTYPE_1 and TYPE_2:");
        printer.print(listAdapter);

        List <Element>list1 = new ArrayList();
        list.stream().filter(element -> element.getType()==TypeEnum.TYPE_1).forEach(element -> list1.add(element));
        listAdapter = new Adapter(list1);
        System.out.println("\nOnly TYPE_1:");
        printer.print(listAdapter);

        List <Element>list2 = new ArrayList();
        list.stream().filter(element -> element.getType()==TypeEnum.TYPE_2).forEach(element -> list2.add(element));
        listAdapter = new Adapter(list2);
        System.out.println("\nOnly TYPE_2:");
        printer.print(listAdapter);
    }
}
