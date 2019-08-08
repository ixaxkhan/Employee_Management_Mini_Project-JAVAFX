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
public class SalaryController implements Initializable {

    @FXML
    private ComboBox<String> pMonth;
    @FXML
    private Button submit;
    @FXML
    private TextField sEmp;
    @FXML
    private DatePicker sDate;
    @FXML
    private Label sName;
    @FXML
    private Label sAmount;
    @FXML
    private Label warnMsg;
    @FXML
    private Label attendedL;
    @FXML
    private TableView<?> sTableView;

    /**
     * Initializes the controller class.
     */
     DisplayDatabase displaySal = new DisplayDatabase();
      ObservableList<String> monthList = FXCollections.observableArrayList();
      ObservableList<String> yearList = FXCollections.observableArrayList();
      
    @FXML
    private ComboBox<String> pYear;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sDate.setValue(LocalDate.now());
        monthList.add("Jan");
        monthList.add("Feb");
        monthList.add("Mar");
        monthList.add("Apr");
        monthList.add("May");
        monthList.add("Jun");
        monthList.add("Jul");
        monthList.add("Aug");
              monthList.add("Sep");
               monthList.add("Oct");
                monthList.add("Nov");
                 monthList.add("Dec");
        pMonth.setItems( monthList);  
        
        int month = LocalDate.now().getMonthValue();
        pMonth.setValue(monthList.get(month-1));
       
         
         for(int i=2010;i<2020;i++){
         yearList.add(String.valueOf(i));
         }
         pYear.setItems(yearList);  
         String year = String.valueOf(LocalDate.now().getYear());
         pYear.setValue(year);
        String query = "SELECT * FROM salaryTable WHERE  PayMonth ='"+monthList.get(month-1)+"' AND PayYear = '"+year+"';";
        displaySal.buildData(sTableView, query);
    }    
boolean calc=false;
    @FXML
    private void submitSalary(ActionEvent event) {
       
        if(!calc){
            warnMsg.setText("Please Calculate the salary first and then submit.");
        return;
        }
      
        
        LocalDate date = sDate.getValue();
        if(date==null){
           sDate.requestFocus();
         warnMsg.setText("Please select payment date.");
        return;
        }
        
       ResultSet rs = QueryDatabase.query("Select * from salaryTable where eid='"+eid+"' AND PayMonth ='"+month+"' AND PayYear = '"+year+"';");
       if(rs!=null){
            try {
                if(rs.next()){
                    warnMsg.setText("Salary has already been payed to this employee.");
                    return;
                }    } catch (SQLException ex) {
                Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
       } 
         Connection c;
         try{
            c = DBConnection.connect();
            
            String query = "insert into salaryTable (EId,Date,Amount,PayMonth,PayYear) values ('"+eid+"','"+date+"','"+String.format("%.2f", paySal)+"','"+month+"','"+year+"');";
            c.createStatement().execute(query);
           
           c.close();
        
          
        
    }   catch (SQLException ex) {
            Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "SELECT * FROM salaryTable WHERE  PayMonth ='"+month+"' AND PayYear = '"+year+"';";
        displaySal.buildData(sTableView, query);
        calc = false;
         
    }

    @FXML
    private void buildTableData(ActionEvent event) {
        String month = pMonth.getValue();
        String year = pYear.getValue();
        if(month==null || month.isEmpty()){
         warnMsg.setText("Pls select pay Month");
         pMonth.requestFocus();
         return;
        }
        if(year==null || year.isEmpty()){
         warnMsg.setText("Pls select pay Year");
         pYear.requestFocus();
         return;
        }
        
        String query = "SELECT * FROM salaryTable WHERE  PayMonth ='"+month+"' AND PayYear = '"+year+"';";
        displaySal.buildData(sTableView, query);
        
        
        
    }
String eid ="";
 int attendedDays = 0;    
double paySal=0; 
 String month ="";
 String year ="";
    @FXML
    private void calcSal(ActionEvent event) {
       
        
     if(!checkEid()){
     return;
     }
     
    month = pMonth.getValue();
      year = pYear.getValue();
        if(month==null || month.isEmpty()){
         warnMsg.setText("Pls select pay Month");
         pMonth.requestFocus();
         return;
        }
        
        if(year==null || year.isEmpty()){
         warnMsg.setText("Pls select pay Year");
         pYear.requestFocus();
         return;
        }
       
        int mon=11;
        
        switch(month){
        case "Jan":
          mon=1; 
          break;
          case "Feb":
          mon=2; 
          break;
          case "Mar":
          mon=3; 
          break;
          case "Apr":
          mon=4; 
          break;
          case "May":
          mon=5; 
          break;
          case "Jun":
          mon=6; 
          break;
          case "Jul":
          mon=7; 
          break;
          case "Aug":
          mon=8; 
          break;
          case "Sep":
          mon=9; 
          break;
          case "Oct":
          mon=10; 
          break;
          case "Nov":
          mon=11; 
          break;
          case "Dec":
          mon=12; 
          break;
             
        }
       
        
        String query = "Select COUNT(ID) from attendanceTable where Eid='"+eid+"' AND MONTH(Date)='"+mon+"' AND YEAR(Date)='"+year+"';";
        ResultSet rs =QueryDatabase.query(query);
        if(rs!=null){
         try {
             if(rs.next()){
                 attendedL.setText(rs.getString(1));
                 attendedDays = Integer.parseInt(rs.getString(1));
                 
             }} catch (SQLException ex) {
             Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
        
        // get the pay of the employee
         double salary = 0;
         
         rs =QueryDatabase.query("Select pay from employee where Id='"+eid+"';");
        if(rs!=null){
         try {
             if(rs.next()){
                 salary = Double.parseDouble(rs.getString(1));
                 
                 
             }} catch (SQLException ex) {
             Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
        
        double perDaySal = salary/22;
        paySal = perDaySal*attendedDays;
        warnMsg.setText("Salary of employee is Rs."+salary+" for working 22 days.");
        sAmount.setText(String.format("%.2f", perDaySal)+"*"+attendedDays+"=Rs."+String.format("%.2f", paySal));
      calc= true;
        
        
        
    }
    
     private boolean checkEid() {
        eid = sEmp.getText();
       if(eid==null || eid.isEmpty()){
        sEmp.requestFocus();
        warnMsg.setText("Please enter Employee ID.");
        return false;
       }
        ResultSet rs = QueryDatabase.query("Select Name from employee where Id = '"+eid+"';");
        if(rs!=null){
            try {
                if(rs.next()){
                
                 sName.setText(rs.getString(1));
                 warnMsg.setText("");
                  return true;
                }else{
                 sName.setText("Employee not found");
                  return false;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
         sName.setText("Employee not found");
          return false;
        }
        return true;
       
    }
    
}
