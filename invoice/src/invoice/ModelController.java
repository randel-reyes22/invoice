/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Randel Reyes
 */
public class ModelController implements Initializable, compute {

    //textfields
    @FXML private TextField description; 
    @FXML private TextField price;
    @FXML private TextField qty;
    @FXML private TextField po_date;
    @FXML private TextField item_count;
    @FXML private TextField total_render_amount;
    @FXML private TextField customer_address;
    @FXML private TextField customer_name;
    @FXML private TextField customer_signature;
    @FXML private DatePicker due_date;
    @FXML private TextField discount_textField;
    @FXML private TextField status_display;
    
    //button
    @FXML private Button btn_Add;
    @FXML private Button close;
    @FXML private Button btn_Save;
    
    //text area
    @FXML private TextArea area;
    
    //instance variables
    private double total;
    private int ctr = 0; 
    private double TotalAmount;
    private double decimal;
    String fileName;
    
    //clasess
    customer c = new customer();
    date d = new date();
    items i = new items();
    private boolean start = true;
    
    //Arraylists
    private ArrayList<items> AlItems = new ArrayList<>(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rep) {
       if(start)
       {
           status_display.setText("Receipt is running");
       }
    }   
   
    @FXML
    public void AddItem(ActionEvent event)
    {  
        //item class
        //d.setDuedate(due_date.getText());
        try{
            i.setItem_name(description.getText()); //item name
            i.setQty(Integer.valueOf(qty.getText())); // quantity
            i.setPrice(Double.valueOf(price.getText())); // price
            i.setDiscount(Integer.valueOf(discount_textField.getText()));
        }
        
        catch(NumberFormatException e){
            description.setText("Provide data");
        }
        
        i.setUndiscountedAmount(undiscountedAmount(i.getPrice(),  i.getQty())); // get the undisocounted amount
        i.setTotal_amount(computePrice(i.getUndiscountedAmount(), i.getDiscount())); //item total price
        i.setMainTotalAmount(i.getTotal_amount());//get the main total amount; tendered amount
        
        //clears the textfields
        description.clear();
        qty.clear();
        price.clear();
        discount_textField.clear();
        
        //customer class
        c.setCustomer_name(customer_name.getText()); // set the customer name
        c.setCustomer_address(customer_address.getText()); // set the customer address
        
        //concatonate the customer name, current date, and the extension .txt
        fileName = c.getCustomer_name() + "-" + d.getDate()+ ".txt";
        
        //sets the content of the textfields
        po_date.setText(d.getDate());
        item_count.setText(String.valueOf(count_item(i.getQty())));
        total_render_amount.setText(String.valueOf(computeTotalAmount(i.getMainTotalAmount())));
        customer_signature.setText(c.getCustomer_name());
         
        //append to the text area
        area.appendText(i.getItem_name());
        area.appendText("       ");
        area.appendText(String.valueOf(i.getQty()));
        area.appendText("       ");
        area.appendText(String.valueOf(i.getPrice()));
        area.appendText("       ");
        area.appendText(String.valueOf(i.getUndiscountedAmount()));
        area.appendText("       ");
        area.appendText(String.valueOf(i.getDiscount()));
        area.appendText("       ");
        area.appendText(String.valueOf(i.getTotal_amount()));
        area.appendText("\n");  
        
        //add the items to the arraylist 
        //for printing
        AlItems.add(new items(i.getItem_name(), i.getQty(), i.getPrice(), i.getUndiscountedAmount(), i.getDiscount(), i.getTotal_amount()));
    }
       
    //close the scene
    @FXML
    public void closeWindow(ActionEvent event)
    {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    
    //prints the list of the items
    @FXML
    public void printToTextFile(ActionEvent event) throws IOException
    {
        for(items obj_items: AlItems)
        { 
            out.print(obj_items.getItem_name() + "   " + obj_items.getQty() + "   " + obj_items.getPrice() + "   " + obj_items.getUndiscountedAmount());
            out.println( "   " + obj_items.getDiscount() + "   " + obj_items.getTotal_amount());
        }
        
        try{
            generateTheFile(); //generates the output file
            generateOutput(); //prints the content to a text file
            status_display.setText("Text file is created");
            AlItems.clear(); // clear arraylist
            //clear text area and text fields
            area.clear(); 
            customer_name.clear();
            customer_address.clear();
            po_date.clear();
            item_count.clear();
            total_render_amount.clear();
            customer_signature.clear();
        }
        
        catch(IOException e){
            out.println("EWWOW");
        }
    }
    
    //will generate the file output
    public void generateTheFile() throws IOException
    {
        File dirFile = new File("d:\\Invoice List");
        if(!dirFile.exists())
            dirFile.mkdir();
       
        dirFile = new File(dirFile, fileName);
        dirFile.createNewFile();
        
        out.println("- The file is processed");
        out.println("- The output: " + dirFile.getAbsoluteFile());
    }
    
    public void generateOutput() throws IOException
    {
        String str_dir = "d:\\Invoice List\\"+ fileName;
        
        try (PrintWriter printContent = new PrintWriter(new FileWriter(str_dir, true))) 
        {
            for(items obj_items: AlItems)
            { 
                printContent.print(obj_items.getItem_name() + "   " + obj_items.getQty() + "   " + obj_items.getPrice() + "   " + obj_items.getUndiscountedAmount());
                printContent.println( "   " + obj_items.getDiscount() + "   " + obj_items.getTotal_amount());
            }
        }
        
        catch(IOException e)
        {
            out.println("Can't write to the directory created");
        }
    } 
    
    //belongs to the compute interface
    //computes the undiscounted amount of the item
    @Override
    public double undiscountedAmount(double price1, int qty1) {
        return price1 * qty1;
    }
    
    //computes the item price
    //this will converts the discount values to percentage
    @Override
    public double computePrice(double total, int discount) {
        decimal = (discount * 0.01);
        return total - (total * decimal);
    }
    
    //compute the total amount of all the items
    @Override
    public double computeTotalAmount(double mainTotalAmount) {
        return TotalAmount = TotalAmount + mainTotalAmount;
    }

    //count the quantity of the each items
    @Override
    public int count_item(int item) {
        ctr = ctr +  item;
        return ctr;
    }

    

        
}
