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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Wipro
 */
public class EmployeeSceneController implements Initializable {
    @FXML
    private Button addBtn;
    @FXML
    private DatePicker eDate;
    @FXML
    private TextField eName;
    @FXML
    private TextField eQualification;
    @FXML
    private TextField eAge;
    @FXML
    private TextField eAdd;
    @FXML
    private TextField eExperience;
    @FXML
    private TextField eDesignation;
    @FXML
    private TextField eSalary;
    @FXML
    private ComboBox<String> eGender;
    @FXML
    private TableView<?> eTableView;
    @FXML
    private Label warnMsg;

    /**
     * Initializes the controller class.
     */
    
    DisplayDatabase empData = new DisplayDatabase();
    @FXML
    private DatePicker eDob;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       ObservableList<String> genderList = FXCollections.observableArrayList();
        genderList.add("M");
        genderList.add("F");
        genderList.add("O");
        eGender.setItems( genderList);  
        empData.buildData(eTableView, "Select * from employee;");
        
        
    }    

     LocalDate date= null;
    String name="";
    String age="";
    String gender="";
    String add="";
    String qualification="";
    String exp="";
    String designation="";
    String sal="";
       LocalDate dob = null;
    @FXML
    private void addEmployee(ActionEvent event) {
       if(!getFields()){
        return;
       }
        Connection c;
        try{
            c = DBConnection.connect();
            String query = "INSERT INTO Employee(Date,Name,Age,Gender,Dob,Address,Qualification,Exp,Designation,Pay)VALUES("+
            "'"+date+"',\n" +
            "'"+name+"',\n" +
            "'"+age+"',\n" +
            "'"+gender+"',\n" +
            "'"+dob+"',\n" +
            "'"+add+"',\n" +
            "'"+qualification+"',\n" +
            "'"+exp+"',\n" +
            "'"+designation+"',\n" +
            "'"+sal+"');";                    
                   
            
        
            c.createStatement().execute(query);
            
            c.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
             clearFields();
             empData.buildData(eTableView, "Select * from employee;");
       
    }
    
  
  private boolean getFields() {
     
     date= eDate.getValue();
     dob= eDob.getValue();
     name=eName.getText();
     age=eAge.getText();
     gender=eGender.getValue();
     add=eAdd.getText();
     qualification=eQualification.getText();
     exp=eExperience.getText();
     designation=eDesignation.getText();
     sal=eSalary.getText();
     
     
     if(date==null){
         eDate.requestFocus();
      warnMsg.setText("Please Specify Date of joining.");
      return false;
     }
      if(dob==null){
         eDob.requestFocus();
      warnMsg.setText("Please Specify Date of Birth.");
      return false;
     }
      if(name==null || name.isEmpty()){
          eName.requestFocus();
      warnMsg.setText("Please Enter Employee Name.");
      return false;
     }
      
     if(age==null || age.isEmpty()){
          eAge.requestFocus();
      warnMsg.setText("Please Enter Employee Age.");
      return false;
     }
     if(name==null || name.isEmpty()){
          eName.requestFocus();
      warnMsg.setText("Please Enter Employee Name.");
      return false;
     }
     if(name==null || name.isEmpty()){
          eName.requestFocus();
      warnMsg.setText("Please Enter Employee Name.");
      return false;
     }
     if(name==null || name.isEmpty()){
          eName.requestFocus();
      warnMsg.setText("Please Enter Employee Name.");
      return false;
     }
     if(name==null || name.isEmpty()){
          eName.requestFocus();
      warnMsg.setText("Please Enter Employee Name.");
      return false;
     }
     if(name==null || name.isEmpty()){
          eName.requestFocus();
      warnMsg.setText("Please Enter Employee Name.");
      return false;
     }
     if(name==null || name.isEmpty()){
          eName.requestFocus();
      warnMsg.setText("Please Enter Employee Name.");
      return false;
     }
     
     
     return true;
     
    }

    private void clearFields() {
     eDate.setValue(LocalDate.now());
     eName.clear();
     eAge.clear();
     eGender.setValue("");
     eAdd.clear();
     eQualification.clear();
     eExperience.clear();
     eDesignation.clear();
     eSalary.clear();
     
    }

    @FXML
    private void mDeleteEmp(ActionEvent event) {
        
       int index = eTableView.getSelectionModel().getFocusedIndex();
        ObservableList<ObservableList> data = empData.getData();
       ObservableList<String> itemData = data.get(index);
       
       int id = Integer.parseInt(itemData.get(0));
      
       DeleteDatabase.deleteRecord(id, "Employee");
      empData.buildData(eTableView, "Select * from employee;");
      
    }
    
}
