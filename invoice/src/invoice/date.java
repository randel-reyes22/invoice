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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        
        return dtf.format(now);
    }

    public String getDuedate() {
        return Duedate;
    }

    public void setDuedate(String Duedate) {
        this.Duedate = Duedate;
    }
    
    
}
