package cn.tsthuah.zuanqianbaay;

import java.io.Serializable;

public class DataInfo implements Serializable{
private String ptName;
private int ptlogo;
private String ptms;
private String ptxqsm;
private int ptewm;
private String pturl;

  public String getPtName() {
    return ptName;
  }

  public void setPtName(String ptName) {
    this.ptName = ptName;
  }

  public int getPtlogo() {
    return ptlogo;
  }

  public void setPtlogo(int ptlogo) {
    this.ptlogo = ptlogo;
  }

  public String getPtms() {
    return ptms;
  }

  public void setPtms(String ptms) {
    this.ptms = ptms;
  }

  public String getPtxqsm() {
    return ptxqsm;
  }

  public void setPtxqsm(String ptxqsm) {
    this.ptxqsm = ptxqsm;
  }

  public int getPtewm() {
    return ptewm;
  }

  public void setPtewm(int ptewm) {
    this.ptewm = ptewm;
  }

  public String getPturl() {
    return pturl;
  }

  public void setPturl(String pturl) {
    this.pturl = pturl;
  }
}
