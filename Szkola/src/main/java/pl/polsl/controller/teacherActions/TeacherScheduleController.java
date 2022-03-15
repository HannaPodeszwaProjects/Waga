package pl.polsl.controller.teacherActions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.GodzinyLekcji;
import pl.polsl.model.*;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherScheduleController implements ParametrizedController {

    @FXML
    private TableView<ScheduleTableTeacher> table;
    @FXML
    private TableColumn<ScheduleTableTeacher, ScheduleTeacher> columnNum;
    @FXML
    private TableColumn<ScheduleTableTeacher, ScheduleTeacher> columnHours;
    @FXML
    private TableColumn<ScheduleTableTeacher, ScheduleTeacher> columnMon;
    @FXML
    private TableColumn<ScheduleTableTeacher, ScheduleTeacher> columnTue;
    @FXML
    private TableColumn<ScheduleTableTeacher, ScheduleTeacher> columnWen;
    @FXML
    private TableColumn<ScheduleTableTeacher, ScheduleTeacher> columnThu;
    @FXML
    private TableColumn<ScheduleTableTeacher, ScheduleTeacher> columnFri;

    Integer id;
    private ObservableList<GodzinyLekcji> hour;

    @Override
    public void receiveArguments(Map<String, Object> params) {
        id = (Integer) params.get("id");
        hour = FXCollections.observableArrayList((new LessonTimeModel()).getTime());
        setTable();
    }

    void setTable() {
        ObservableList<ScheduleTableTeacher> schedule = (new ScheduleTableTeacher()).getSchedule(id, hour);

        columnNum.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        columnMon.setCellValueFactory(new PropertyValueFactory<>("mon"));
        columnTue.setCellValueFactory(new PropertyValueFactory<>("tue"));
        columnWen.setCellValueFactory(new PropertyValueFactory<>("wen"));
        columnThu.setCellValueFactory(new PropertyValueFactory<>("thu"));
        columnFri.setCellValueFactory(new PropertyValueFactory<>("fri"));


        table.setItems(schedule);
    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("menu/TeacherMenuForm", params, WindowSize.teacherMenuForm);
    }


}
