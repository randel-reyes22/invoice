/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Randel Reyes
 */
public class Invoice extends Application{

    @Override
    public void start(Stage primaryStage) 
    {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Model.fxml"));
            Scene scene = new Scene(root,1124,856);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        
        catch(IOException e){
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {       
        launch(args);
    }
}
