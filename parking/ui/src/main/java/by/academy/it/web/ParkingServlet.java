package by.academy.it.web;

import by.academy.it.service.TicketService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

//@WebServlet(name = "parkingServlet", urlPatterns = "/parking")
public class ParkingServlet extends HttpServlet {

    private TicketService controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            controller = new TicketService();
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrintWriter writer = resp.getWriter();
            resp.setContentType("text/html");
            HttpSession session = req.getSession();

            String number = req.getParameter("number");
            Date currentDate = new Date();
            List<String> messages = controller.handleTicketRequest(number, currentDate);
            messages.forEach(writer::println);
            writer.println("Car Number: " + number);

            session.setAttribute("number", number);
            addParkingCookies(resp, number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void addParkingCookies(HttpServletResponse resp, String number) {
        Cookie cookie = new Cookie("PLATENUMBER", number);
        cookie.setMaxAge(300);
        resp.addCookie(cookie);
    }
}
