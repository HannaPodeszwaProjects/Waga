package pl.polsl.controller.principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.entities.Oceny;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Student;
import pl.polsl.model.Subject;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.io.IOException;
import java.awt.print.*;
import java.awt.*;
import java.sql.Date;
import java.util.*;
import java.util.List;

public class RaportMenuController {

    @FXML
    private Button highestAvg;
    @FXML
    private TableView<Przedmioty> tableSubjects;
    @FXML
    private TableColumn<Przedmioty, String> nameC;
    @FXML
    private DatePicker date1;
    @FXML
    private DatePicker date2;
    @FXML
    public void initialize()
    {
        displayTableSubjects();
        tableSubjects.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                highestAvg.setDisable(false);
            }
            else {
                highestAvg.setDisable(true);
            }
        });
    }

    private void displayTableSubjects()
    {
        tableSubjects.getItems().clear();
        Subject s= new Subject();
        List l=s.displaySubjects();
        nameC.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        for (Object p: l) {
            tableSubjects.getItems().add((Przedmioty) p);
        }
    }
    private void alert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepoprawne daty");
            alert.setContentText("Data początku jest większa niż data końca!!!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void alertNull()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawne daty");
        alert.setContentText("Podaj daty!!!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    public void clickButtonAverageSubject(ActionEvent event) throws IOException {
        if(date1.getValue()==null || date2.getValue()==null)
        {
            alertNull();
            return;
        }
        if((Date.valueOf(date1.getValue())).compareTo(Date.valueOf(date2.getValue()))>0)
        {
           alert();
            return;
        }
        Student s = new Student();
        Przedmioty p = tableSubjects.getSelectionModel().getSelectedItem();
        List <Object []> l=s.getGradeFromSubject(p.getID(), (Date.valueOf(date1.getValue())),(Date.valueOf(date2.getValue())));

        Map<Uczniowie, Float> sum = new HashMap<>();
        Map<Uczniowie, Float> size = new HashMap<>();

        for(Object o[]: l)
        {
            if((sum.keySet().isEmpty()) || !(sum.keySet().contains((Uczniowie) o[1])))
            {
                sum.put((Uczniowie) o[1],((Oceny)o[0]).getWaga() * ((Oceny) o[0]).getOcena());
                size.put((Uczniowie) o[1], ((Oceny)o[0]).getWaga());
            }
            else
            {
                float oldSize = size.get((Uczniowie) o[1]);
                float oldGrade = sum.get((Uczniowie) o[1]);

                size.remove((Uczniowie) o[1]);
                sum.remove((Uczniowie) o[1]);

                size.put((Uczniowie) o[1], oldSize+1);
                sum.put((Uczniowie) o[1], oldGrade + ((Oceny) o[0]).getOcena());
            }
        }

        Map<Uczniowie, Float> avg = new HashMap<>();
        for(Uczniowie u :sum.keySet())
        {
            avg.put(u,sum.get(u)/size.get(u));
        }

        ArrayList list = new ArrayList();
        list.add("Średnia z przedmiotu: " + p.getNazwa());
        list.add("");
        avg.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(m -> list.add(m.getKey().getImie() + " " + m.getKey().getNazwisko() +": " + m.getValue()));
        (new RaportPrinter()).printingCall(list);
    }

    public void clickButtonSelectStudent(ActionEvent event) throws IOException {
        Student s = new Student();
        List<Uczniowie> l=s.getAllStudents();

        ArrayList<String> textToPrint = new ArrayList<>();

        textToPrint.add("Lista studentów:");
        textToPrint.add("");
        for (Uczniowie u : l) {
            textToPrint.add(u.getImie() + " " + u.getNazwisko());
        }

        (new RaportPrinter()).printingCall(textToPrint);
    }


    public void clickButtonBack(ActionEvent event) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", Roles.PRINCIPAL);
        Main.setRoot("menu/adminMenuForm", params, WindowSize.principalMenuForm);
    }

    public void clickButtonAverageGrade(ActionEvent actionEvent) {
        if(date1.getValue()==null || date2.getValue()==null)
        {
            alertNull();
            return;
        }
        if((Date.valueOf(date1.getValue())).compareTo(Date.valueOf(date2.getValue()))>0)
        {
            alert();
            return;
        }
        Student s = new Student();
        List<Uczniowie> l=s.getAllStudents();
        Map<Uczniowie, Float> average = new HashMap<>();

        for(Uczniowie u :l)
        {
            List <Object[]> l2=s.getGradeFromStudent(u.ID,(Date.valueOf(date1.getValue())),(Date.valueOf(date2.getValue())));

            Map<Przedmioty, Float> sum = new HashMap<>();
            Map<Przedmioty, Float> size = new HashMap<>();

            for(Object[] o: l2)
            {
                if((sum.keySet().isEmpty()) || !(sum.keySet().contains((Przedmioty) o[1])))
                {
                    sum.put((Przedmioty) o[1], ((Oceny)o[0]).getWaga() * ((Oceny)o[0]).getOcena());
                    size.put((Przedmioty) o[1], ((Oceny)o[0]).getWaga());
                }
                else
                {
                    float oldSize = size.get((Przedmioty) o[1]);
                    float oldGrade = sum.get((Przedmioty) o[1]);

                    size.remove((Przedmioty) o[1]);
                    sum.remove((Przedmioty) o[1]);

                    size.put((Przedmioty) o[1], oldSize+1);
                    sum.put((Przedmioty) o[1], oldGrade + ((Oceny) o[0]).getOcena());
                }
            }

            Map<Przedmioty, Float> avg = new HashMap<>();
            float allAvg=0;
            for(Przedmioty p :sum.keySet())
            {
                float a =sum.get(p)/size.get(p);
                avg.put(p,a);
                allAvg +=a;
            }

            average.put(u, allAvg/avg.keySet().size());
        }
        ArrayList list = new ArrayList();
        list.add("Średnia z wszystkich przedmiotów:");
        list.add("");

        average.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(m -> list.add(m.getKey().getImie() + " " + m.getKey().getNazwisko() +": " + m.getValue()));

        (new RaportPrinter()).printingCall(list);
    }
}


class RaportPrinter implements Printable {

    private ArrayList<String> textLines;

    public ArrayList<String> getTextToPrint() {
        return textLines;
    }

    public void setTextToPrint(ArrayList<String> textToPrint) {
        this.textLines = textToPrint;
    }

    private int[] pageBreaks;

    private int margin = 60;

    public int print(Graphics g, PageFormat pf, int pageIndex) throws
            PrinterException {

        Font font = new Font("Serif", Font.PLAIN, 10);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        if (pageBreaks == null) {
            int linesPerPage = (int)((pf.getImageableHeight() - margin * 2)/lineHeight);
            int numBreaks = (textLines.size()-1)/linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b=0; b<numBreaks; b++) {
                pageBreaks[b] = (b+1)*linesPerPage;
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        int y = margin;
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end   = (pageIndex == pageBreaks.length)
                ? textLines.size() : pageBreaks[pageIndex];
        for (int line=start; line<end; line++) {
            y += lineHeight;
            g.drawString(textLines.get(line), 50, y);
        }

        return PAGE_EXISTS;
    }

    public void printingCall(ArrayList<String> text) {try {
        this.setTextToPrint(text);
        String cn = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(cn);
        }
        catch (Exception cnf) {
        }

        PrinterJob job = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

        Book book = new Book();
        book.append(this, job.defaultPage());
        job.setPageable(book);

        job.setPrintable(this);
        boolean ok = job.printDialog(aset);
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
            }
        }
    }
}

