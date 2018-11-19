package sample;

import javafx.collections.ObservableList;

public class Company  {
public String CName;
public  String CCEO;
public String CLocation;
public Company(){
  CName = "";
  CCEO= "";
  CLocation = "";

}
  public Company(String CName, String CCEO, String CLocation) {
    this.CName = CName;
    this.CCEO = CCEO;
    this.CLocation = CLocation;
  }

  public void setCName(String CName) {
    this.CName = CName;
  }

  public void setCCEO(String CCEO) {
    this.CCEO = CCEO;
  }

  public void setCLocation(String CLocation) {
    this.CLocation = CLocation;
  }

  public String getCName() {
    return CName;
  }

  public String getCCEO() {
    return CCEO;
  }

  public String getCLocation() {
    return CLocation;
  }

  @Override
  public String toString() {
    return "Company{" +
        "CName='" + CName + '\'' +
        ", CCEO='" + CCEO + '\'' +
        ", CLocation='" + CLocation + '\'' +
        '}';
  }
}
