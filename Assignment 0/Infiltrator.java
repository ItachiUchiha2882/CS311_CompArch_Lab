package ja_ca.assign0;

public class Infiltrator {
  // infiltrator props declared.
  int current_depth = 1;
  Sensor s = new Sensor();      // for current cell.
  Sensor front = new Sensor();      // for front cell.
  Sensor front_left = new Sensor();     // for front-left cell.
  Sensor front_right = new Sensor();    // for front-right cell.
}
