package sample.FXML;
import SQL.DBConnect;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/******************************************************************************
 * File: HomeController.java
 * Author: Carlos Perez
 *Handles I/O for the homeScreen.fxml GUI
 * Connects to 'CompanyDB'  database by using the 'DBconnect' class.
 *
 * [Current Function]
 * Add/Remove index
 ******************************************************************************/


public class HomeController implements Initializable {
  //Set up Table col titles text
  private final String[] TableCOL_Names = {"Organization", "CEO", "HQ Location","Test"};
  private ObservableList<ObservableList> data;
  @FXML private TableView<ObservableList> TView_Company;
  @FXML private JFXTextField txtFl_CName;
  @FXML private JFXTextField txtFl_CCEO;
  @FXML private JFXTextField txtFl_CLocation;
  @FXML private JFXTextField txtFl_SearchQuery;
  @FXML private JFXButton btn_Add;
  @FXML private JFXButton btn_Remove;
  @FXML private JFXButton btn_Search;
  @FXML private Label lbl_Error;

  /*btn_add clicked.
  *Checks JFXTextField are filled
  *Connects to db and inserts new index
  * Reset Tableview
  */

  //Search
  public void selectIndex(){
    if (txtFl_CName.getText().isEmpty()){

    }
    else{
      try {
        lbl_Error.setText("");
        //Prepare Connection and SQL STATEMENT
        Connection conn = DBConnect.connect();
        String sql = "SELECT * FROM %s WHERE name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, txtFl_SearchQuery.getText());
        statement.executeUpdate();
        //Update table
        setUpTable();
        conn.close();
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }

  //
  public void addNewIndex() {
    //Check fields are filled
    if (txtFl_CName.getText().isEmpty() || txtFl_CCEO.getText().isEmpty() || txtFl_CLocation
        .getText().isEmpty()) {
      lbl_Error.setText("[Please fill out the form]");
    } else {
      try {
        lbl_Error.setText("");
        //Prepare Connection and SQL STATEMENT
        Connection conn = DBConnect.connect();
        String sql = "INSERT INTO COMPANY(C_NAME, C_CEO, C_LOCATION)" +
            "VALUES (?,?,?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, txtFl_CName.getText());
        statement.setString(2, txtFl_CCEO.getText());
        statement.setString(3, txtFl_CLocation.getText());
        statement.executeUpdate();
        //Update table
        setUpTable();
        conn.close();

      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }

  public void removeIndex() {
    //Get & Create String from selected table row.
    String selectedIndex = (TView_Company.getSelectionModel().getSelectedItem().toString());
    //Format string to only get the
    selectedIndex =selectedIndex.substring(1,selectedIndex.indexOf(','));
    System.out.println("Remove: "+selectedIndex);

    try {
      lbl_Error.setText("");
      //Prepare Connection and SQL STATEMENT
      Connection conn = DBConnect.connect();
      String sql = "DELETE FROM COMPANY "
          + "WHERE C_NAME = ?";
     PreparedStatement statement = conn.prepareStatement(sql);
     statement.setString(1,selectedIndex);
     statement.executeUpdate();

     //Update table
     setUpTable();
     conn.close();

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void initialize(URL url, ResourceBundle resources) {
      setUpTable();
      //Table row selected listener
      TView_Company.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
          System.out.println("Table select:" +newSelection);
        }      });

  }

  public void setUpTable() {
    //Reset table
    TView_Company.getSelectionModel().clearSelection();

    data = FXCollections.observableArrayList();
    data.clear();
    try{
      //Prepare Connection and SQL STATEMENT
      Connection conn = DBConnect.connect();
      String SQL = "SELECT * from COMPANY";
      ResultSet rs = conn.createStatement().executeQuery(SQL);
      //Dynamical make table columns)
      for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn col = new TableColumn(TableCOL_Names[i]);
        col.setCellValueFactory(
            new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
              public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(j).toString());
              }
            });

        TView_Company.getColumns().addAll(col);
      }
      while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
          row.add(rs.getString(i));
        }
        data.add(row);
      }
      conn.close();
      // Add To TableView
      TView_Company.setItems(data);

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error on Building Data");
    }

  }


}
