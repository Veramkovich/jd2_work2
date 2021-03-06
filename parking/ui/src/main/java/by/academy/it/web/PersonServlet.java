package by.academy.it.web;

import by.academy.it.parking.pojo.Person;
import by.academy.it.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet(urlPatterns = "/person", name = "personServlet")
public class PersonServlet extends HttpServlet {

    private PersonService personController;

    @Override
    public void init() throws ServletException {
        personController = new PersonService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        if ("new".equals(command)) {
            Person person = new Person();
            person.setName(req.getParameter("name"));
            person.setSecondName(req.getParameter("second_name"));
            List<String> errors =
                    personController.saveNewPerson(person);
            if (errors.isEmpty()) {
                req.setAttribute("persons", personController.getAllPersons());
                req.getRequestDispatcher("/persons.jsp").forward(req, resp);
            } else {
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/add-person.jsp").forward(req, resp);
            }
        }
    }
}
