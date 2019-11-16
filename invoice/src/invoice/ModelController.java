/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Randel Reyes
 */
public class ModelController implements Initializable {

    //textfields
    @FXML private TextField description; 
    @FXML private TextField price;
    @FXML private TextField qty;
    @FXML private TextField po_date;
    
    //button
    @FXML private Button btn_Add;
    @FXML private Button close;
    
    //table
    @FXML private TableView<items> table;
    @FXML private TableColumn<items, String> descriptioncol;
    @FXML private TableColumn<items, Integer> quantitycol;
    @FXML private TableColumn<items, Double> pricecol;
    @FXML private TableColumn<items, Double> undiscounted_total;
    
    //instance
    private double total;
    
    //clasess
    customer c = new customer();
    date d = new date();
    
    @Override
    public void initialize(URL url, ResourceBundle rep) {
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantitycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        undiscounted_total.setCellValueFactory(new PropertyValueFactory<>("undiscounted_total"));
        table.setItems(getItemList());
    }   
    
    @FXML
    public void closeWindow(ActionEvent event)
    {
        Stage stage = (Stage) close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void AddItem(ActionEvent event)
    {  
        items i = new items();
        i.setItem_name(description.getText());
        i.setQty(Integer.valueOf(qty.getText()));
        i.setPrice(Double.valueOf(price.getText()));
        i.setTotal_amount(i.getPrice() * i.getQty());
        table.getItems().add(i);     
        
        po_date.setText(d.getDate());
    }
    
    ObservableList<items> getItemList()
    {
        ObservableList<items> Items = FXCollections.observableArrayList();
        Items.add(new items("Suka", 1, 300, 300));
        Items.add(new items("Suka", 1, 200, 300));
        return Items;
    }
       
    
}
