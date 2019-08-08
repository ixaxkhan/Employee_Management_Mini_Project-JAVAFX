/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.management;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import DB.QueryDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Wipro
 */
public class AssingnController implements Initializable {
    @FXML
    private TextField aEid;
    @FXML
    private DatePicker aDate;
    @FXML
    private Button submitBtn;
  

    /**
     * Initializes the controller class.
     */
    DisplayDatabase assignData = new DisplayDatabase();
    ObservableList<String> pList = FXCollections.observableArrayList();
    @FXML
    private TableView<?> aTableView;
    @FXML
    private ComboBox<String> aPName;
    @FXML
    private Label warnMsg;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          assignData.buildData(aTableView, "Select * from assignmentTable;");
          
            ResultSet rs = QueryDatabase.query("Select Name from ProjectTable where Name NOT IN (Select ProjectName from assignmentTable);");
        if(rs!=null){
            try {
                while(rs.next()){
                    pList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AssingnController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        aPName.setItems(pList);
        aDate.setValue(LocalDate.now());
    }    
    
    LocalDate date= null;
    String eid="";
    String pName="";
    
    @FXML
    private void setAssignment(ActionEvent event) {

        date = aDate.getValue();
        eid = aEid.getText();
        pName = aPName.getValue();
        
         if(date==null){
         aDate.requestFocus();
      warnMsg.setText("Please Specify Date.");
      return ;
        }
       if(eid==null || eid.isEmpty()){
          aEid.requestFocus();
      warnMsg.setText("Please Enter Employee Id.");
      return;
     }
        if(pName==null || pName.isEmpty()){
          aPName.requestFocus();
      warnMsg.setText("Please Select a project.");
      return;
     }
         
        Connection c;
        try{
            c = DBConnection.connect();
            String query = "INSERT INTO AssignmentTable(Date,EID,ProjectName)VALUES("+
            "'"+date+"',\n" +
            "'"+eid+"',\n" +
            "'"+pName+"');";                    
                   
            
        
            c.createStatement().execute(query);
            
            c.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
             aEid.clear();
             aPName.setValue("");
             assignData.buildData(aTableView, "Select * from assignmentTable;");
       
        pList.remove(pName);
        
    }

    @FXML
    private void mDeleteAssignment(ActionEvent event) {
            
       int index = aTableView.getSelectionModel().getFocusedIndex();
        ObservableList<ObservableList> data = assignData.getData();
       ObservableList<String> itemData = data.get(index);
       
       int id = Integer.parseInt(itemData.get(0));
      
       DeleteDatabase.deleteRecord(id, "AssignmentTable");
      assignData.buildData(aTableView, "Select * from AssignmentTable;");
      
    }
    
}
