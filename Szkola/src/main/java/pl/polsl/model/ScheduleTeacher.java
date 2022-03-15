package pl.polsl.model;

import lombok.ToString;
import pl.polsl.entities.Rozklady;

public class ScheduleTeacher extends Rozklady {

    public ScheduleTeacher(){
    }

    public ScheduleTeacher(Rozklady act){
        setDzien(act.getDzien());
        setID(act.getID());
        setIdKlasy(act.getIdKlasy());
        setGodzina(act.getGodzina());
        setIdNauczyciela(act.getIdNauczyciela());
        setIdPrzedmiotu(act.getIdPrzedmiotu());
        setIdSali(act.getIdSali());
    }

    @Override
    public String toString() {
        String subjectName = (new Subject()).getSubjectName(getIdPrzedmiotu());
        if (subjectName.length() >= 30) {
            String shortenedName = "";
            for (String word : subjectName.split(" ")) {
                shortenedName += word.charAt(0);
            }
            subjectName = shortenedName;
        }
        else if (subjectName.length() >= 20) {
            String shortenedName = "";
            for (String word : subjectName.split(" ")) {
                shortenedName += word.charAt(0);
                shortenedName += ". ";
            }
            subjectName = shortenedName;
        }
        return subjectName + "\ns. " + (new Classroom()).getNameById(getIdSali()) + "\nk. " + (new SchoolClass()).getClassNumber(getIdKlasy());


    }

}
