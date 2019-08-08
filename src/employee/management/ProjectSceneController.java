/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.management;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Wipro
 */
public class ProjectSceneController implements Initializable {

    @FXML
    private Button add;
    @FXML
    private TextField pName;
    @FXML
    private TextField pDesc;
    @FXML
    private DatePicker pEndDate;
    @FXML
    private DatePicker pStartDate;
    @FXML
    private TableView<?> pTableView;
    @FXML
    private Label warnMsg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pStartDate.setValue(LocalDate.now());
         pData.buildData(pTableView, "Select * from projectTable;");
    }    
    String name;
    String desc;
    LocalDate startDate;
    LocalDate endDate;
    DisplayDatabase pData = new DisplayDatabase();

    @FXML
    private void addNewProject(ActionEvent event) throws SQLException {
        name = pName.getText();
        desc = pDesc.getText();
        startDate = pStartDate.getValue();
        endDate = pEndDate.getValue();
        
        if(name==null || name.isEmpty()){
        warnMsg.setText("Pls enter Project Name.");
        pName.requestFocus();
        return;
        }
        
        if(desc==null || desc.isEmpty()){
        warnMsg.setText("Pls enter Project Description.");
        pDesc.requestFocus();
        return;
        }
     
        if(startDate==null){
        warnMsg.setText("Pls enter Project Start Date.");
        pStartDate.requestFocus();
        return;
        }
        
        if(endDate==null){
        warnMsg.setText("Pls enter Project End Date.");
        pEndDate.requestFocus();
        return;
        }
        
         Connection c;
          
        
            if(update == true){
            c = DBConnection.connect();
                String query = "Update ProjectTable set Name='"+name+"',Description='"+desc+"',StartDate='"+startDate+"',"
                   + "EndDate='"+endDate+"' Where Id='"+id+"';";
                  c.createStatement().execute(query);
               c.close();
            }else{
           c = DBConnection.connect();
            String query = "INSERT INTO ProjectTable(Name,Description,StartDate,EndDate)VALUES("+
           
            "'"+name+"',\n" +
            "'"+desc+"',\n" +
            "'"+startDate+"',\n" +
            "'"+endDate+"');";                    
                   
            
        
            c.createStatement().execute(query);
            
            c.close();}
            
       
          pData.buildData(pTableView, "Select * from projectTable;");
          pName.clear();
          pDesc.clear();
          pStartDate.setValue(LocalDate.now());
          add.setText("Add");
          
    }

    @FXML
    private void mDeleteProject(ActionEvent event) {
          
       int index = pTableView.getSelectionModel().getFocusedIndex();
        ObservableList<ObservableList> data = pData.getData();
       ObservableList<String> itemData = data.get(index);
       
       int id = Integer.parseInt(itemData.get(0));
      
       DeleteDatabase.deleteRecord(id, "ProjectTable");
      pData.buildData(pTableView, "Select * from ProjectTable;");
      
    }
 int id;
boolean update = false;
    
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void mUpdateProject(ActionEvent event) {
       
        int index = pTableView.getSelectionModel().getFocusedIndex();
        ObservableList<ObservableList> data = pData.getData();
       ObservableList<String> itemData = data.get(index);
        
       id = Integer.parseInt(itemData.get(0));
       
        pName.setText(itemData.get(1));
         pDesc.setText(itemData.get(2));
          String[] sdate = itemData.get(3).split(" ");  // get only date from DateTime
               pStartDate.setValue(LocalDate.parse(sdate[0],format));
     String[] edate = itemData.get(4).split(" ");  // get only date from DateTime
               pEndDate.setValue(LocalDate.parse(edate[0],format));
               
               update=true;
        add.setText("Update"); 
    }
    
    
}
