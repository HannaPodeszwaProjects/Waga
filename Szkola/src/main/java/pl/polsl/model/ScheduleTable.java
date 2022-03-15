package pl.polsl.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.GodzinyLekcji;
import pl.polsl.entities.Rozklady;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


public class ScheduleTable implements ManageDataBase {

    EntityManager entityManager;
    Integer number;
    String hours;
    Rozklady mon;
    Rozklady tue;
    Rozklady wen;
    Rozklady thu;
    Rozklady fri;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Rozklady getMon() {
        return mon;
    }

    public void setMon(Rozklady mon) {
        this.mon = mon;
    }

    public Rozklady getTue() {
        return tue;
    }

    public void setTue(Rozklady tue) {
        this.tue = tue;
    }

    public Rozklady getWen() {
        return wen;
    }

    public void setWen(Rozklady wen) {
        this.wen = wen;
    }

    public Rozklady getThu() {
        return thu;
    }

    public void setThu(Rozklady thu) {
        this.thu = thu;
    }

    public Rozklady getFri() {
        return fri;
    }

    public void setFri(Rozklady fri) {
        this.fri = fri;
    }




    public ObservableList<ScheduleTable> getSchedule(Integer IdClass, ObservableList<GodzinyLekcji> lessonTime){


        List<Rozklady> results = (new ScheduleModel()).findByClass(IdClass);

        ObservableList<ScheduleTable> list = FXCollections.observableArrayList();

        DateFormat format = new SimpleDateFormat("HH:mm");

        for(Integer i = 0; i<lessonTime.size();i++){
            list.add(new ScheduleTable());
            list.get(i).number= lessonTime.get(i).getNumer();
            list.get(i).hours = format.format(lessonTime.get(i).getPoczatek()) +  " - " + format.format(lessonTime.get(i).getKoniec());
        }

        for(Rozklady act : results) {
            Optional<GodzinyLekcji> opt = lessonTime.stream().filter(a -> a.getNumer().equals(act.getGodzina())).findAny();
            GodzinyLekcji hour = opt.orElse(null);
            if (hour != null) {
                Integer num = hour.getNumer();
                switch (act.getDzien()) {
                    case "pon":
                        list.get(num - 1).mon = act;
                        break;
                    case "wto":
                        list.get(num - 1).tue = act;
                        break;
                    case "sro":
                        list.get(num - 1).wen = act;
                        break;
                    case "czw":
                        list.get(num - 1).thu = act;
                        break;
                    case "pia":
                        list.get(num - 1).fri = act;
                        break;
                }
            }
        }

        return list;
    }





}
