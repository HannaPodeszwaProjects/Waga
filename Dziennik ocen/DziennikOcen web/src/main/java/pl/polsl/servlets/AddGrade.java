package pl.polsl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.entities.Person1;
import pl.polsl.entities.Subject1;
import pl.polsl.model.Person;
import pl.polsl.model.Subject;
import pl.polsl.model.Grade;

/**
 * Adds new grade to register
 *
 * @author Hanna Podeszwa
 * @version 2.1
 */
@WebServlet(name = "AddGrade", urlPatterns = {"/AddGrade"})
public class AddGrade extends HttpServlet {

    /**
     * True if student's data are wrong
     */
    private boolean wrongPerson;
    /**
     * True if subject is wrong
     */
    private boolean wrongSubject;
    /**
     * True if grade is wrong
     */
    private boolean wrongGrade;

    /**
     * Counter as Integer
     *
     */
    private Integer counter;
    /**
     * Entity manager
     */
    EntityManager em;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        Object obj = session.getAttribute("counterId");
        if (obj == null) {
            counter = 0;
        } else {
            counter = (Integer) obj;
        }

        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            ServletContext servletContext = getServletContext();
            em = (EntityManager) servletContext.getAttribute("entityId");
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String class1 = request.getParameter("class");
            String subject = request.getParameter("subject");
            String gr = request.getParameter("grade");

            if (firstName.length() == 0 || lastName.length() == 0 || class1.length() == 0 || subject.length() == 0 || gr.length() == 0) {
                response.sendError(response.SC_BAD_REQUEST, "You should give five parameters!");
            } else {
                double grade = Double.parseDouble(gr);

                wrongPerson = false;
                wrongSubject = false;
                wrongGrade = false;
                Person p = new Person(firstName, lastName, class1);
                Subject s = new Subject(subject);
                Grade g = new Grade(grade);
                Person1 thisPerson = null;

                //find selected student
                Query q = em.createQuery("SELECT a FROM Person1 a WHERE a.name=:name AND a.surname=:surname AND a.class1=:class")
                        .setParameter("name", firstName)
                        .setParameter("surname", lastName)
                        .setParameter("class", class1);

                List l = q.getResultList();
                if (!l.isEmpty()) {
                    thisPerson = (Person1) l.get(0);
                }

                if (thisPerson != null) {
                    Subject1 inList = p.subjectAlreadyIn(thisPerson, subject, em);
                    if (inList != null) { //subject already is in list
                        addGrade(thisPerson, inList, g);//add grade if it is correct
                    } else if (!(p.addNewSubject(thisPerson, s, em))) { //add new subject if it is correct
                        wrongSubject = true;
                    } else {
                        //find selected subject
                        Query q2 = em.createQuery("SELECT s FROM Person1 p, PersonSubject ps, Subject1 s "
                                + "WHERE p = :student AND ps.idPerson = p AND ps.idSubject = s AND s.name=:subject ")
                                .setParameter("student", thisPerson)
                                .setParameter("subject", s.getName());

                        List l2 = q2.getResultList();
                        Subject1 sub = (Subject1) l2.get(0);
                        addGrade(thisPerson, sub, g); //add grade if it is correct
                    }

                } else {
                    wrongPerson = true;
                }

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Dodaj ocenę</title>");
                out.println("</head>");
                out.println("<body>");
                if (!wrongPerson && !wrongSubject && !wrongGrade) {
                    out.println("<h1>Dodano " + grade + " dla " + firstName + " " + lastName + " z " + subject + "</h1>");
                    String message = ("dodano " + grade + " dla " + firstName + " " + lastName + " z " + subject);

                    Cookie cookie = new Cookie("last", message);
                    cookie.setMaxAge(-1);
                    response.addCookie(cookie);

                    counter++;
                    session.setAttribute("counterId", counter);
                } else {
                    out.println("<h1>Niepoprawne dane nowej oceny.</h1>\n");
                }
                out.println("<form action=\"PrintRegister\" method=\"GET\">");
                out.println("<input type=\"submit\" value=\"Powrót\" />");
                out.println(" </form>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    /**
     * Add new grades to register
     *
     * @param s subject
     * @param g grade to add
     */
    private void addGrade(Person1 p, Subject1 s, Grade g) {
        Subject sub = new Subject();
        if (!(sub.addNewGrade(p, s, g, em))) {
            wrongGrade = true;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
