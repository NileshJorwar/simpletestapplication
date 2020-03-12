package com.springboottest.simpletestapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    TicketService ticketService;

    private JdbcTemplate jdbcTemplate;
    private DataSource source;
    

    @PostMapping(value = "/createticket", produces = "application/json")
    public TicketEntity createTicket(@RequestBody TicketEntity ticketEntity) {
        return ticketService.createTicket(ticketEntity);
    }

    @GetMapping(value = "/getticket/{ticketId}")
    public TicketEntity getTicketById(@PathVariable("ticketId") String tId) {
        return ticketService.getTicketEntityById(tId);
    }

    // Testing the application
    /*
    @RequestParam is used to get the request parameters from URL, also known as query parameters, while @PathVariable extracts values from URI.
    * For example, if the incoming HTTP request to retrieve a book on topic "Java" is http://localhost:8080/shop/order/1001/receipts?date=12-05-2017, then you can use the @RequestParam annotation to retrieve the query parameter date and you can use @PathVariable to extract the orderId i.e. "1001" as shown below:

    @RequestMapping(value="/order/{orderId}/receipts", method = RequestMethod.GET)
    public List listUsersInvoices(                               @PathVariable("orderId") int order,
    @RequestParam(value = "date", required = false) Date dateOrNull) {

        }
    * */
    @GetMapping("/greeting")
    public ResponseEntity<String> greet(@RequestParam("name") String name,
                                        @RequestParam("gender") String gender) {
        String salute = "male".equals(gender) ? "Mr" : "Mrs";
        return new ResponseEntity<>(String.format("Hi %s%s how are ya ", salute, name), HttpStatus.OK);
    }

    @GetMapping("/greetingwithpathvariables/{age}")
    public ResponseEntity<String> greetUsingPathVariables(@PathVariable int age) {
        boolean ifChild = age < 16 ? true : false;
        return new ResponseEntity<>("If child or not" + ifChild,HttpStatus.OK);
    }
}