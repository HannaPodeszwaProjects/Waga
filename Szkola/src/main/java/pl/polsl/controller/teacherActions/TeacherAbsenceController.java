package pl.polsl.controller.teacherActions;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.*;
import pl.polsl.utils.WindowSize;

import java.io.IOException;

import java.util.*;

public class TeacherAbsenceController implements ParametrizedController {

    public enum dayOrder{pon, wto, sro, czw, pia}

    @FXML
    private TableView<Nieobecnosci> table;
    @FXML
    private TableColumn<Nieobecnosci, String> columnExcuse;
    @FXML
    private TableColumn<Nieobecnosci, String> columnDate;
    @FXML
    private TableColumn<Nieobecnosci, String> columnLessonStart;
    @FXML
    private TableColumn<Nieobecnosci, String> columnLessonEnd;
    @FXML
    private TableColumn<Nieobecnosci, String> columnSubject;
    @FXML
    private ComboBox<String> comboboxSchedule;
    @FXML
    private ComboBox<String> comboboxStudent;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonExcuse;

    Integer id;
    ObservableList<Rozklady> scheduleList;
    ObservableList<Uczniowie> studentList;

    ObservableList<Nieobecnosci> absenceList;

    @Override
    public void receiveArguments(Map<String, Object> params) {
        id = (Integer) params.get("id");
        Integer numberStudent = (Integer) params.get("numberStudent");
        Integer numberSchedule = (Integer) params.get("numberSchedule");
        if(numberSchedule == null){
            numberSchedule = 0;
            numberStudent= 0;
        }
        scheduleList = FXCollections.observableList((new ScheduleModel()).findByTeacher(id));


        Comparator<Rozklady> byLesson = (r1, r2) -> {
            if(dayOrder.valueOf(r1.getDzien()).compareTo(dayOrder.valueOf(r2.getDzien())) > 0){
                return 1;
            } else if (dayOrder.valueOf(r1.getDzien()).equals(dayOrder.valueOf(r2.getDzien()))){
                return r1.getGodzina() - r2.getGodzina();
            } else {
                return 0;
            }
        };
        scheduleList = scheduleList.sorted(byLesson);

        if(!scheduleList.isEmpty()) {
            for (Rozklady r : scheduleList) {
                String date = r.getDzien();
                String lesson = r.getGodzina().toString();
                String subject = (new Subject()).getSubjectById(r.getIdPrzedmiotu()).getNazwa();
                comboboxSchedule.getItems().add(date + " " + lesson + " " + subject);
            }
            table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) deleteGrade);
            comboboxSchedule.getSelectionModel().select(numberSchedule);
            setStudents(numberSchedule,numberStudent);
            setAbsence(numberStudent);
        }

    }

    private final ListChangeListener<? extends TablePosition> deleteGrade = (
            javafx.collections.ListChangeListener.Change<? extends TablePosition> change) -> {
        Nieobecnosci tym = table.getSelectionModel().getSelectedItem();
        if (tym == null) {
            buttonDelete.setDisable(true);
            buttonExcuse.setDisable(true);
        } else {
            buttonDelete.setDisable(false);
            buttonExcuse.setDisable(tym.getCzyUsprawiedliwiona()!=0);
        }
    };

    public void clickButtonDelete() {
        Nieobecnosci n = table.getSelectionModel().getSelectedItem();
        (new AbsenceModel()).delete(n);

        Integer index = comboboxStudent.getSelectionModel().getSelectedIndex();
        absenceList.clear();
        setAbsence(index);

    }

    public void clickButtonExcuse() {
        Nieobecnosci n = table.getSelectionModel().getSelectedItem();
        n.setCzyUsprawiedliwiona(1);
        (new AbsenceModel()).update(n);

        Integer index = comboboxStudent.getSelectionModel().getSelectedIndex();
        absenceList.clear();
        setAbsence(index);
    }

    public void clickButtonAdd() throws IOException{
        Map<String, Object> params = new HashMap<>();

        params.put("teacherId", id);

        int studentIndex = comboboxStudent.getSelectionModel().getSelectedIndex();
        int scheduleIndex = comboboxSchedule.getSelectionModel().getSelectedIndex();


        params.put("student", studentList.get(studentIndex));
        params.put("subject", (new Subject()).getSubjectById(scheduleList.get(scheduleIndex).getIdPrzedmiotu()));
        params.put("lesson", scheduleList.get(scheduleIndex).getGodzina());
        params.put("numberSchedule", comboboxSchedule.getSelectionModel().getSelectedIndex());
        params.put("numberStudent", comboboxStudent.getSelectionModel().getSelectedIndex());
        Main.setRoot("teacherActions/teacherAddNewAbsenceForm", params, WindowSize.teacherAddNewAbsenceForm);
    }

    void setStudents(Integer index, Integer numberStudnet){
        studentList = FXCollections.observableArrayList((new Student()).getStudentInClass(scheduleList.get(index).getIdKlasy()));
        if(!studentList.isEmpty()){
            for (Uczniowie student : studentList) {
                comboboxStudent.getItems().add(student.getImie()+" " + student.getNazwisko());
            }
            comboboxStudent.getSelectionModel().select(numberStudnet);
        }
    }

    public void changeComboboxSchedule() {
        studentList.clear();
        comboboxStudent.getItems().clear();
        setStudents(comboboxSchedule.getSelectionModel().getSelectedIndex(),0);
    }



    public Integer getWitdh(String text){
        return (int)(new Text(text)).getBoundsInLocal().getWidth();
    }

    public String wrapString(String wraping,Integer wid){
        Integer width = wid-10;
        if(getWitdh(wraping) < width) {
            return wraping;
        }
        StringBuilder result = new StringBuilder();
        String[] words = wraping.split(" ");
        List<Integer> sizewords = new ArrayList<>();

        for(String word : words) sizewords.add(getWitdh(word));
        Integer act = 0;
        int i = 0;
        for(Integer size : sizewords) {

            if(size + act < width){
                result.append(words[i]).append(" ");
                act +=size+3;
            }
            else if (size + act >= width) {
                result.append("\n").append(words[i]).append(" ");
                act=size+3;
            }
            else {
                result.append("\n").append(words[i]).append("\n");
                act = 0;
            }
            i++;
        }


        return result.toString();
    }

    public void setAbsence(Integer index){
        if(!studentList.isEmpty()) {
            absenceList = FXCollections.observableArrayList((new AbsenceModel()).displayPresent(studentList.get(index).getID()));


            columnLessonEnd.setCellValueFactory(CellData -> {
                String tym = (new LessonTimeModel()).getById(CellData.getValue().getGodzina()).getKoniec().toString();
                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnLessonEnd.getWidth()));
            });

            columnSubject.setCellValueFactory(CellData -> {
                String tym = (new Subject()).getSubjectName(CellData.getValue().getPrzedmiotyId());
                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnSubject.getWidth()));
            });

            columnDate.setCellValueFactory(CellData -> {
                String tym = CellData.getValue().getData().toString();
                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnDate.getWidth()));
            });

            columnExcuse.setCellValueFactory(CellData -> {
                Integer pom = CellData.getValue().getCzyUsprawiedliwiona();
                String tym;
                if (pom.equals(0)) {
                    tym = "Nie";
                } else {
                    tym = "Tak";
                }
                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnExcuse.getWidth()));
            });

            columnLessonStart.setCellValueFactory(CellData -> {
                String tym = (new LessonTimeModel()).getById(CellData.getValue().getGodzina()).getPoczatek().toString();

                return new ReadOnlyStringWrapper(wrapString(tym, (int) columnLessonStart.getWidth()));
            });

            table.setItems(absenceList);
        }
    }

    public void changeComboboxStudent() {
        Integer index = comboboxStudent.getSelectionModel().getSelectedIndex();

        absenceList.clear();

        if(!studentList.isEmpty()){
            setAbsence(index);
            buttonAdd.setDisable(false);
        }
        else{
            buttonAdd.setDisable(true);
        }
    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("menu/teacherMenuForm", params, WindowSize.teacherMenuForm);
    }


}
