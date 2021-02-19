package pl.polsl.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import pl.polsl.model.Register;
import pl.polsl.model.Grade;
import pl.polsl.model.Person;
import pl.polsl.model.Subject;

/**
 * Reading and writing to a file
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class File1 {

    //private String fileName;
    /**
     * Writes data to a file from register
     *
     * @param fileName name of output file
     * @param students register
     * @return true if reading is correct
     */
    public boolean readFile(String fileName, Register students) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Person newPerson = new Person();
                newPerson.setName(scanner.nextLine());
                newPerson.setSurname(scanner.nextLine());
                newPerson.setClass(scanner.nextLine());
                readSubject(newPerson, scanner); //reads subject and grades from file

                students.addFromFile(newPerson);//add new student to register

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Reads data from a file and writes it to subjects
     *
     * @param newPerson new person to write
     * @param scanner input data stream
     */
    private void readSubject(Person newPerson, Scanner scanner) {
        String tmp; //new line
        if ((scanner.nextLine()).equals("+p")) { //subjects marker

            while (true) {
                tmp = (scanner.nextLine());
                if (tmp.equals("-p")) {//subjects end marker
                    break;
                }
                Subject newSubject = new Subject();
                newSubject.setName(tmp);
                if ((scanner.nextLine()).equals("+o")) {//grades marker

                    while (true) {
                        tmp = (scanner.nextLine());
                        if (tmp.equals("-o")) {//grades end marker
                            break;
                        }
                        Grade newGrade = new Grade();
                        newGrade.setGrade(Double.parseDouble(tmp));
                        newSubject.addFromFile(newGrade); //add new grade to subject
                    }
                }
                newPerson.addFromFile(newSubject);//add new subject to student
            }

        }
    }

    /**
     * Writes data to a file from register
     *
     * @param fileName name of output file
     * @param register
     */
    public void writeFile(String fileName, Register register) {
        try {
            java.io.File file = new java.io.File(fileName);
            PrintWriter print = new PrintWriter(file);

            for (Person person : register.getStudents()) {

                print.println(person.getName());
                print.println(person.getSurname());
                print.println(person.getClass1());
                print.println("+p"); //subjects marker
                if (!(person.getSubjects().isEmpty())) {
                    for (Subject subject : person.getSubjects()) {
                        print.println(subject.getName());
                        print.println("+o");//grades marker
                        if (!(subject.getGrades().isEmpty())) {
                            for (Grade grade : subject.getGrades()) {
                                print.println(grade.getGrade());
                            }
                        }
                        print.println("-o");//grades end marker
                    }
                }
                print.println("-p");//subjects end marker
            }

            print.close();
        } catch (FileNotFoundException e) {
        }
    }

}
