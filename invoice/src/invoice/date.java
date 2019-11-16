/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Randel Reyes
 * description: holds the purchase order date and
 *              due date
 */
public class date {
    
    private String Duedate;

    public date() {
    }
    
    public date(String Duedate) {
        this.Duedate = Duedate;
    }
    
    //return the current date
    //this will serve as the purchase order date
    public  String getDate()
    {
        return setDate();
    }
    
    //gets the current date of the system
    //this will serve as the purchase order date
    public String setDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
        LocalDateTime now = LocalDateTime.now();  
        
        return dtf.format(now);
    }
    
    //will get the due date from the date picker
    public String getDuedate() {
        return Duedate;
    }

    public void setDuedate(String Duedate) {
        this.Duedate = Duedate;
    }
    
    
}
