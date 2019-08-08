/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.management;

import DB.DBConnection;
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
public class AttendenceController implements Initializable {

    @FXML
    private TextField employeeId;
    @FXML
    private Label employeeName;
    @FXML
    private Button submit;
    @FXML
    private DatePicker aDate;

    /**
     * Initializes the controller class.
     */
     DisplayDatabase displayAttendance = new DisplayDatabase();
    @FXML
    private TableView<?> aTableView;
    @FXML
    private Label warnMsg;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        aDate.setValue(LocalDate.now());
        displayAttendance.buildData(aTableView, "Select * from attendanceTable where date='"+LocalDate.now()+"';");
        
    }    
LocalDate date;
    @FXML
    private void submitAttendence(ActionEvent event) {
        
        if(checkEid()){
            
        if(attendanceExits()){
            warnMsg.setText("Attendance already exits.");
            return;
        }else{
        warnMsg.setText("");
        }
        
        
              
         
            
             Connection c;
         try{
            c = DBConnection.connect();
            
            String query = "insert into AttendanceTable (Date,Eid) values ('"+date+"','"+id+"');";
            c.createStatement().execute(query);
            } catch (SQLException ex) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
           buildTable();
        }
               
               
    }

    @FXML
    private void buildTable() {
           displayAttendance.buildData(aTableView, "Select * from attendanceTable where date='"+aDate.getValue()+"';");
    }

    @FXML
    private void getEName(ActionEvent event) {
        checkEid();
    }
    String id ="";
    private boolean checkEid() {
        id = employeeId.getText();
       if(id==null || id.isEmpty()){
        employeeId.requestFocus();
        return false;
       }
        ResultSet rs = QueryDatabase.query("Select Name from employee where Id = '"+id+"';");
        if(rs!=null){
            try {
                if(rs.next()){
                
                 employeeName.setText(rs.getString(1));
                  return true;
                }else{
                 employeeName.setText("Employee not found");
                  return false;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
         employeeName.setText("Employee not found");
          return false;
        }
        return true;
       
    }

    private boolean attendanceExits() {
       
         date = aDate.getValue();
        if(date==null){
            aDate.requestFocus();
            warnMsg.setText("Pls Specify Date.");
            return false;
        }
       
         id = employeeId.getText();
       if(id==null || id.isEmpty()){
        employeeId.requestFocus();
        warnMsg.setText("Pls Enter Employee Id.");
        return false;
       }
        ResultSet rs = QueryDatabase.query("Select * from attendanceTable where EId='"+id+"' AND Date='"+date+"';");
        if(rs!=null){
            try {
                if(rs.next()){
                        return true;
                }else{
                 
                   return false;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
         
          return true;
        }
        return true;
    }
    
}
