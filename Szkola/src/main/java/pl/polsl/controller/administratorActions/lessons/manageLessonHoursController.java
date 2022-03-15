package pl.polsl.controller.administratorActions.lessons;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.*;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class manageLessonHoursController implements ParametrizedController {

    public TextField textFormBegin;
    public TextField textFormEnd;
    public TableView<GodzinyLekcji> table;
    public TableColumn<GodzinyLekcji,Integer> columnNum;
    public TableColumn<GodzinyLekcji,String> columnBegin;
    public TableColumn<GodzinyLekcji,String> columnEnd;
    public Button addingButton;

    private String mode;
    private Integer id;


    public void initialize() {
        columnNum.setCellValueFactory(new PropertyValueFactory<>("numer"));
        columnBegin.setCellValueFactory(CellData -> {
            DateFormat format = new SimpleDateFormat("HH:mm");
            String begin = format.format(CellData.getValue().getPoczatek());
            return new ReadOnlyStringWrapper(begin);
        });
        columnEnd.setCellValueFactory(CellData -> {
            DateFormat format = new SimpleDateFormat("HH:mm");
            String begin = format.format(CellData.getValue().getKoniec());
            return new ReadOnlyStringWrapper(begin);
        });
        List<GodzinyLekcji> classHours = (new LessonTimeModel().getTime());
        table.getItems().addAll(classHours);
        addingButton.setDisable(classHours.size() >= 14);
        table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) cellSelectListener);
    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");
    }

    private ListChangeListener<? extends TablePosition> cellSelectListener = (ListChangeListener.Change<? extends TablePosition> change) -> {
        if (change.getList().size() > 0) {
            TablePosition selectedCell = change.getList().get(0);
            if (selectedCell != null) {
                DateFormat format = new SimpleDateFormat("HH:mm");
                textFormBegin.setText(format.format(table.getSelectionModel().getSelectedItem().getPoczatek()));
                textFormEnd.setText(format.format(table.getSelectionModel().getSelectedItem().getKoniec()));
            }
        }
    };

    private void refreshTable() {
        Integer index = table.getSelectionModel().getSelectedIndex();

        table.getSelectionModel().getSelectedCells().removeListener((ListChangeListener<? super TablePosition>) cellSelectListener);
        table.getItems().clear();
        List<GodzinyLekcji> classHours = (new LessonTimeModel().getTime());
        table.getItems().addAll(classHours);
        addingButton.setDisable(classHours.size() >= 14);
        table.getSelectionModel().select(index);
        table.getFocusModel().focus(index);
        table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) cellSelectListener);
    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        if (mode.equals(Roles.PRINCIPAL))
            Main.setRoot("menu/adminMenuForm", params, WindowSize.principalMenuForm);
        else
            Main.setRoot("menu/adminMenuForm", params, WindowSize.adminMenuForm);

    }

    public void clickButtonAccept() {
        DateFormat format = new SimpleDateFormat("HH:mm");
        String begin = textFormBegin.getText();
        String end = textFormEnd.getText();
        if (table.getSelectionModel().getSelectedItem() != null
            && ((begin.length() == 5 && begin.charAt(2) == ':')
            || (begin.length() == 4 && begin.charAt(1) == ':'))
            && ((end.length() == 5 && end.charAt(2) == ':')
            || (end.length() == 4 && end.charAt(1) == ':'))
        )
        {
            try {
                Date beginTime = format.parse(textFormBegin.getText());
                Date endTime = format.parse(textFormEnd.getText());
                table.getSelectionModel().getSelectedItem().setPoczatek(new Time(beginTime.getTime()));
                table.getSelectionModel().getSelectedItem().setKoniec(new Time(endTime.getTime()));
                (new LessonTimeModel()).update(table.getSelectionModel().getSelectedItem());
                refreshTable();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clickAddNewButton() throws ParseException {
        GodzinyLekcji l = new GodzinyLekcji();
        DateFormat format = new SimpleDateFormat("HH:mm");
        l.setPoczatek(new Time(format.parse("12:00").getTime()));
        l.setKoniec(new Time(format.parse("13:00").getTime()));
        l.setNumer((new LessonTimeModel()).getHighestNumber() + 1);
        (new LessonTimeModel()).persist(l);
        refreshTable();
    }
}
