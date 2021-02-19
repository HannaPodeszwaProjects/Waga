package pl.polsl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.entities.Grade1;
import pl.polsl.entities.Person1;
import pl.polsl.entities.Subject1;
import pl.polsl.model.Register;

/**
 * Prints register and allows its modifications
 *
 * @author Hanna Podeszwa
 * @version 2.1
 */
@WebServlet(name = "PrintRegister", urlPatterns = {"/PrintRegister"})
public class PrintRegister extends HttpServlet {

    /**
     * Register as Register
     */
    private Register register = new Register();
    /**
     * Counter of modyfication as Integer
     *
     */
    private Integer counter;

    /**
     * Entity manager factory
     */
    EntityManagerFactory emf;
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
            counter = 0; //number of modyfication in this session
        } else {
            counter = (Integer) obj;
        }

        session.setAttribute("counterId", counter);

        Cookie[] cookies = request.getCookies();
        String last = " ";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("last")) {
                    last = cookie.getValue(); //last modyfication
                    break;
                }
            }
        }

        response.setContentType("text/html;charset=UTF-8");

        try ( PrintWriter out = response.getWriter()) {

            register.getStudents().clear();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style>");

            out.println("table, th, td { \n border: 1px solid black;\nborder-collapse: collapse;\n}\n");
            out.println("</style>");
            out.println("<title>Servlet PrintRegister</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Dziennik</h1>"); //title
            out.println("Ostatnia modyfikacja: " + last); //last modyfication
            out.println("<br>Liczba modyfikacji w tej sesji: " + counter); //number of modyfications

            //options to add new student or grade
            out.println("<div style=\" width: 300px; position: -webkit-sticky; position: sticky;  left: 700px; top: 0px; border:solid 1px black;text-align: right\">");

            //buttons
            //add new grade
            out.println("<form action=\"AddGrade\" method=\"GET\">");
            out.println(" <p>Imię:<input type=text size=20 name=firstname></p>");
            out.println(" <p>Nazwisko:<input type=text size=20 name=lastname></p>");
            out.println(" <p>Klasa:<input type=text size=20 name=class></p>");
            out.println(" <p>Przedmiot:<input type=text size=20 name=subject></p>");
            out.println(" <p>Ocena:<input type=text size=20 name=grade></p>");
            out.println(" <input type=\"submit\" value=\"Dodaj ocene\" id = \"grade\" />");
            out.println("</form>");
            out.println(" <br>");

            //add new student
            out.println("<form action=\"AddStudent\" method=\"POST\">");
            out.println(" <p>Imię:<input type=text size=20 name=firstname1></p>");
            out.println(" <p>Nazwisko:<input type=text size=20 name=lastname1></p>");
            out.println(" <p>Klasa:<input type=text size=20 name=class1></p>");
            out.println("<input type=\"submit\" value=\"Dodaj nowego ucznia\" id = \"student\"/>");
            out.println(" </form>");
            out.println(" <br>");
            out.println(" </div>");

            //table with register
            out.println("<table id \"tb\" style=\"width:1000\">");
            out.println("<tr>");
            out.println("<th>Imie</th>");
            out.println("<th>Nazwisko</th>");
            out.println("<th>Klasa</th>");
            out.println("<th>Przedmiot</th>");
            out.println("<th>Ocena</th>");
            out.println("<th>Średnia</th>");
            out.println("</tr>");

            out.println("<tr>");

            out.println("</tr>");

            //find students
            Query q = em.createQuery("SELECT a FROM Person1 a");
            List l = q.getResultList();

            for (Object o : l) {
                out.println("<tr>");
                out.println("<td>" + ((Person1) o).getName() + "</td>");
                out.println("<td>" + ((Person1) o).getSurname() + "</td>");
                out.println("<td>" + ((Person1) o).getClass1() + "</td>");

                //find subjects
                Query q2 = em.createQuery("SELECT s FROM Person1 p, PersonSubject ps, Subject1 s "
                        + "WHERE p = :student AND ps.idPerson = p AND ps.idSubject = s")
                        .setParameter("student", ((Person1) o));
                List l2 = q2.getResultList();

                for (Object o2 : l2) {
                    out.println("<tr>");
                    out.println("<td></td><td></td><td></td>");
                    out.println("<td>" + ((Subject1) o2).getName() + "</td>");
                    out.println("<td>");

                    //find grades
                    Query q3 = em.createQuery("SELECT g FROM Person1 p, Subject1 s, Grade1 g, PersonGrade pg "
                            + "WHERE p=:student AND p = pg.personId AND pg.subjectId = s  AND s=:subject AND pg.gradeId=g")
                            .setParameter("student", ((Person1) o)).setParameter("subject", ((Subject1) o2));

                    List l3 = q3.getResultList();
                    for (Object o3 : l3) {
                        out.println(" " + ((Grade1) o3).getGrade());
                    }

                    out.println("</td>");
                    //find average
                    out.println("<td>" + register.average(l3) + "</td>");
                    l3.clear();
                    out.println("</tr>");
                }
                l2.clear();
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

        }
    }

    /**
     * Initializes register
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        emf = Persistence.createEntityManagerFactory("pl.polsl_DziennikOcen_war_1.0-SNAPSHOTPU");
        em = emf.createEntityManager();

        getServletContext().setAttribute("registerId", register);
        getServletContext().setAttribute("entityId", em);
        counter = 0;
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

        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("registerId", register);
        em = (EntityManager) servletContext.getAttribute("entityId");

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
        ServletContext servletContext = getServletContext();
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
