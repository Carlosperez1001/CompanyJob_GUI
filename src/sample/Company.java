package sample;


public class Company {

  private String cName;
  private String cCeo;
  private String cLocation;
  private long cId;

  public Company(String cName, String cCeo, String cLocation, long cId) {
    this.cName = cName;
    this.cCeo = cCeo;
    this.cLocation = cLocation;
    this.cId = cId;
  }

  public String getCName() {
    return cName;
  }

  public void setCName(String cName) {
    this.cName = cName;
  }


  public String getCCeo() {
    return cCeo;
  }

  public void setCCeo(String cCeo) {
    this.cCeo = cCeo;
  }


  public String getCLocation() {
    return cLocation;
  }

  public void setCLocation(String cLocation) {
    this.cLocation = cLocation;
  }


  public long getCId() {
    return cId;
  }

  public void setCId(long cId) {
    this.cId = cId;
  }

}
