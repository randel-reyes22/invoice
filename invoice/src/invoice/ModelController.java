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
import java.text.DecimalFormat;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Randel Reyes
 */
public class ModelController implements Initializable, compute  {

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
    @FXML private TextArea status_display;
    
    //button
    @FXML private Button btn_Add;
    @FXML private Button close;
    @FXML private Button btn_Save;
    @FXML private Button btn_Print;
    
    //text area
    @FXML private TextArea area;
    
    //instance variables
    private double total;
    private int ctr = 0; 
    private double TotalAmount;
    private double decimal;
    String fileName;
    String outputDir;
    
    //clasess
    customer c = new customer();
    date d = new date();
    items i = new items();
    private boolean start = true;
    
    //Arraylists
    private ArrayList<items> AlItems = new ArrayList<>(); 
    
    //for 2 decimal point on
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    
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
        LocalDate value = due_date.getValue();
        d.setDuedate(String.valueOf(value));
        
        try{
            i.setItem_name(description.getText()); //item name
            i.setQty(Integer.valueOf(qty.getText())); // quantity
            i.setPrice(Double.valueOf(price.getText())); // price
            i.setDiscount(Integer.valueOf(discount_textField.getText()));
        }
        
        catch(NumberFormatException e){
            status_display.setText("Provide data");
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
        fileName = c.getCustomer_name() + "-P.O." + d.getDate()+ "Due" + d.getDuedate() + ".txt";
         
        //sets the content of the textfields
        po_date.setText(d.getDate());
        item_count.setText(String.valueOf(count_item(i.getQty())));
        total_render_amount.setText(String.valueOf(df2.format(computeTotalAmount(i.getMainTotalAmount()))));
        customer_signature.setText(c.getCustomer_name());
         
        //append to the text area
        area.appendText(i.getItem_name());
        area.appendText("       ");
        area.appendText(String.valueOf(i.getQty()));
        area.appendText("       ");
        area.appendText(String.valueOf(df2.format(i.getPrice())));
        area.appendText("       ");
        area.appendText(String.valueOf(df2.format(i.getUndiscountedAmount())));
        area.appendText("       ");
        area.appendText(String.valueOf(df2.format(i.getDiscount())));
        area.appendText("       ");
        area.appendText(String.valueOf(df2.format(i.getTotal_amount())));
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
        
        if(c.getCustomer_name() != null) //if customer name is not empty or null
        {
            due_date.setValue(null);//resets the date picker
            
            try{
                generateTheFile(); //generates the output file
                generateOutput(); //prints the content to a text file
                status_display.setText(outputDir);
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

            catch(Exception e){
                status_display.clear();
                status_display.setText("Please add item before saving");
            }
            
        }else{
            status_display.clear();
            status_display.setText("Please input the name of the customer");
        }
        
    }
    
    //will generate the file output
    public void generateTheFile() throws IOException
    {
        File dirFile = new File("d:\\Invoice List");
        
        if(!dirFile.exists())
            dirFile.mkdir();
        
        try{
            dirFile = new File(dirFile, fileName);
            dirFile.createNewFile();
        }
        
        catch(IOException e)
        {
            status_display.clear();
            status_display.setText("Unable to create the text file");
        }
        
        out.println("- The file is processed");
        outputDir = "- The output: " + dirFile.getAbsoluteFile();
    }
    
    public void generateOutput() throws IOException
    {
        if(!AlItems.isEmpty() || c.getCustomer_name() != null) //if arralylist is not empty print the contents
        {
            String str_dir = "d:\\Invoice List\\"+ fileName; //concatonates the directory the the file name string
             
            try (PrintWriter printContent = new PrintWriter(new FileWriter(str_dir, true))) 
            {
                printContent.println("Name: " + c.getCustomer_name() + "\t\t\t" + "P.O. Date: " + d.getDate());
                printContent.println("Address: " + c.getCustomer_address() + "\t\t\t" + "Due date: " + d.getDuedate() + "\n\n");

                printContent.println("Description" + "\t\t" + "Qty" + "\t\t" + "Price" + "\t\t" + "Total" + "\t\t" + "Discount" + "\t\t" + "Total" + "\n");

                for(items obj_items: AlItems)
                { 
                    printContent.print(obj_items.getItem_name() + "\t\t\t" + obj_items.getQty() + "\t\t" + obj_items.getPrice() + "\t\t" + obj_items.getUndiscountedAmount());
                    printContent.println( "\t\t" + obj_items.getDiscount() + "\t\t\t" + obj_items.getTotal_amount());
                }

                printContent.println("\n\n" + "\t\t\t\t\t\t\t\t\t" + "Qty: " + item_count.getText() + "    " + "Total: " + total_render_amount.getText());
                printContent.println("\n" + "I hereby certify that I have received the above mentioned"+  "\n" + "goods in good order an"
                        + "condtion I agree to my obligation on or \nbefore due date (30 days after the date of purchase).");

                printContent.println("\n\n" + "\t\t\t" + c.getCustomer_name());
                printContent.println("\t\t(Name & Signature Over Printed Name)");
            }
        
            catch(Exception e)
            {
                out.println("Can't write to the directory created");
            }
            
        }else{
            if(c.getCustomer_name() == null){
                //else display this erropr message
                status_display.setText("Please add the item to the list");
            }else{
                status_display.setText("Please provide a customer name");
            }
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
