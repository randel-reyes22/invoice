/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
    
    //button
    @FXML private Button btn_Add;
    @FXML private Button close;
    
    //text area
    @FXML private TextArea area;
    
    //instance
    private double total;
    private int ctr = 0; 
    private double TotalAmount;
    
    //clasess
    customer c = new customer();
    date d = new date();
    items i = new items();
    
    @Override
    public void initialize(URL url, ResourceBundle rep) {
       
    }   
   
    @FXML
    public void AddItem(ActionEvent event)
    {  
        i.setItem_name(description.getText()); //item name
        i.setQty(Integer.valueOf(qty.getText())); // quantity
        i.setPrice(Double.valueOf(price.getText())); // price
        i.setTotal_amount(computePrice(i.getPrice(),  i.getQty())); //item total price
        i.setMainTotalAmount(i.getTotal_amount());//get the main total amount; tendered amount
        
        //clears the textfields
        description.clear();
        qty.clear();
        price.clear();
        
        //sets the purchase order date
        po_date.setText(d.getDate());
        
        //append to the text area
        area.appendText(i.getItem_name());
        area.appendText("             ");
        area.appendText(String.valueOf(i.getQty()));
        area.appendText("             ");
        area.appendText(String.valueOf(i.getPrice()));
        area.appendText("             ");
        area.appendText(String.valueOf(i.getTotal_amount()));
        area.appendText("\n");
        
        
        item_count.setText(String.valueOf(count_item(i.getQty())));
        total_render_amount.setText(String.valueOf(computeTotalAmount(i.getMainTotalAmount())));
    }
       
    @FXML
    public void closeWindow(ActionEvent event)
    {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @Override
    public double computePrice(double price1, int qty1) {
        
        return price1 * qty1;
        
    }

    @Override
    public double computeTotalAmount(double mainTotalAmount) {
        TotalAmount = TotalAmount + mainTotalAmount;
        return 0;
    }

    @Override
    public int count_item(int item) {
        ctr = ctr +  item;
        return ctr;
    }
    
}
