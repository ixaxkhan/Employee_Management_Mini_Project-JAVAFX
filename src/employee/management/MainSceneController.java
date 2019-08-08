/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.management;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Wipro
 */
public class MainSceneController implements Initializable {
    
    private Label label;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private Button addEmploybtn;
    @FXML
    private Button projectbtn;
    @FXML
    private Button assign;
    @FXML
    private Button salary;
    @FXML
    private Button attendencebtn;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         changeScene("EmployeeScene.fxml");        // TODO
    }    

   
    @FXML
    private void setProject(ActionEvent event) {
         changeScene("ProjectScene.fxml");
    }

    @FXML
    private void setAssign(ActionEvent event) {
         changeScene("Assingn.fxml");
    }

    @FXML
    private void setsSlary(ActionEvent event) {
         changeScene("Salary.fxml");
    }

    @FXML
    private void setAttendence(ActionEvent event) {
         changeScene("Attendence.fxml");
    }
    
      public  void changeScene(String scenePath){
        
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource(scenePath));
        AnchorPane pane = new AnchorPane();
           
    try{
            pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
        }
        catch(Exception e){

        }
     
    }

    @FXML
    private void setEmployeeScene(ActionEvent event) {
        changeScene("EmployeeScene.fxml");   
    }
    
}
