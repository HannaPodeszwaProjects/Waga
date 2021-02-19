package pl.polsl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.model.Register;
import pl.polsl.model.Person;

/**
 * Adds new student to register
 *
 * @author Hanna Podeszwa
 * @version 1.1
 */
@WebServlet(name = "AddStudent", urlPatterns = {"/AddStudent"})
public class AddStudent extends HttpServlet {

    /**
     * Counter as Integer
     *
     */
    private Integer counter;
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
            Register register = (Register) servletContext.getAttribute("registerId");
            String firstName = request.getParameter("firstname1");
            String lastName = request.getParameter("lastname1");
            String class1 = request.getParameter("class1");

            if (firstName.length() == 0 || lastName.length() == 0 || class1.length() == 0) {
                response.sendError(response.SC_BAD_REQUEST, "You should give three parameters!");
            } else {
                Person p = new Person(firstName, lastName, class1);
                boolean ok = register.addNewStudent(p, em);

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Dodaj ucznia</title>");
                out.println("</head>");
                out.println("<body>");

                if (ok) {
                    out.println("<h1>Dodano " + firstName + " " + lastName + "</h1><br>");
                    String message = ("dodano " + firstName + " " + lastName);

                    Cookie cookie = new Cookie("last", message);
                    cookie.setMaxAge(-1);
                    response.addCookie(cookie);

                    counter++;
                    session.setAttribute("counterId", counter);
                } else {
                    out.println("<h1>Niepoprawne dane nowego ucznia.</h1>\n");
                }
                out.println("<form action=\"PrintRegister\" method=\"GET\">");
                out.println("<input type=\"submit\" value=\"Powrót\" />");
                out.println(" </form>");

                out.println("</body>");
                out.println("</html>");
            }
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
