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

public class HomeController implements Initializable {
  //Set up Table col titles text
  private final String[] TableCOL_Names = {"Company", "Name", "HQ Location"};
  private ObservableList<ObservableList> data;
  @FXML private TableView TView_Company;
  @FXML private JFXTextField txtFl_CName;
  @FXML private JFXTextField txtFl_CCEO;
  @FXML private JFXTextField txtFl_CLocation;
  @FXML private JFXButton btn_Add;
  @FXML private Label lbl_Error;

  /*btn_add clicked.
  *Checks JFXTextField are filled
  *Connects to db and inserts new index
  * Reset Tableview
  */
  public void addNewIndex() {
    if (txtFl_CName.getText().isEmpty() || txtFl_CCEO.getText().isEmpty() || txtFl_CLocation
        .getText().isEmpty()) {
      lbl_Error.setText("[Please fill out the form]");
    } else {
      try {
        lbl_Error.setText("");
        Connection conn;
        conn = DBConnect.connect();
        String sql = "INSERT INTO COMPANY(C_NAME, C_CEO, C_LOCATION)" +
            "VALUES (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, txtFl_CName.getText());
        statement.setString(2, txtFl_CCEO.getText());
        statement.setString(3, txtFl_CLocation.getText());
        statement.executeUpdate();
        System.out.println("what");
        setUpTable();
        conn.close();

      } catch (Exception e) {
        System.out.println("Error");
        System.err.println(e.getMessage());
      }
    }
  }


  public void initialize(URL url, ResourceBundle resources) {
    System.out.println("Hi");
    setUpTable();
  }

  public void setUpTable() {
    //Clear entire table before updating
    TView_Company.getItems().clear();
    TView_Company.getColumns().clear();
    System.out.println("build");

    Connection conn;
    data = FXCollections.observableArrayList();
    try {
      conn = DBConnect.connect();
      String SQL = "SELECT * from COMPANY";
      //ResultSet
      ResultSet rs = conn.createStatement().executeQuery(SQL);

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
        System.out.println("Column [" + i + "] ");

      }

      while (rs.next()) {
        ObservableList<String> row = FXCollections.observableArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
          row.add(rs.getString(i));
        }
        System.out.println("Row added " + row);
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
