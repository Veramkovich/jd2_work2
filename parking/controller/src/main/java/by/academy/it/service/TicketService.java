package by.academy.it.service;

import by.academy.it.data.TicketDaoImpl;
import by.academy.it.pojo.Ticket;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    TicketDaoImpl ticketDao;

    public TicketService() throws ClassNotFoundException {
        ticketDao = new TicketDaoImpl();
    }

    public List<String> handleTicketRequest(String number, Date currentDate) throws SQLException {
        List<String> messages = new ArrayList<>();
        if (ticketDao.getTicketByNumber(number) != null) {
            Date startDate = ticketDao.getTicketByNumber(number).getDate();
            ticketDao.removeByNumber(number);
            long seconds = (currentDate.getTime() - startDate.getTime()) / 1000;
            messages.add("You stayed in our parking:");
            messages.add(seconds + " seconds");
        } else {
            Ticket ticket = new Ticket();
            ticket.setLicensePlateNumber(number);
            ticket.setDate(currentDate);
            ticketDao.saveNewTicket(ticket);
            messages.add("Welcome to our Parking!");
            messages.add(currentDate.toString());
        }
        return messages;
    }

}
