package code;

public class Sensor {
  // p is probability and r is random num between 0 to 1.
  public boolean isOn(double p){
    double r = Math.random();     // generates random r.
    // System.out.println("r :" + r);
    // System.out.println("p :" + p);
    if(r<p){      // if r is less than p, then sensor is on.
      return true;
    } else {      // if r is greater than p, then sensor is off.
      return false;
    }
  }
}