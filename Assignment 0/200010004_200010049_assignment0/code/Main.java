package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {
  static int time;
  static boolean[] grid = new boolean[3];     // declaring grid array for front three position.
  static boolean current_cell;

  public static class Time {      // defining 'Time' class.
    public void Increment_time() {
      time = time + 10;
    }
    public void display_time() {
      System.out.println(time);
    }
    public void reset_time() {
      time = 0;
    }
  }
  public static void main(String[] args) throws FileNotFoundException {
    double prob = 0.2;      // probability starts from 0.2 and goes till 0.8 with margin of 0.1 each time the loop runs.
    // int test = 0;           // no. of times cases to run.
    Border border = new Border();
    border.width = 3;       // border-width starts from 3 and goes till 30 with gap of 3 for each run time.
    Infiltrator inf = new Infiltrator();
    inf.current_depth = 1;
    // System.out.println("Current depth: " + inf.current_depth);
    Time t = new Time();   
    // int width = border.width;
    PrintStream op_file = new PrintStream(new File("output.txt"));
    while (prob <= 0.8) {
      System.out.println("Probability is: " + prob);
      border.width = 3;
      while (border.width <= 30) {
        while (true) {
          grid[0] = inf.front.isOn(prob);
          grid[1] = inf.front_left.isOn(prob);
          grid[2] = inf.front_right.isOn(prob);
          current_cell = inf.s.isOn(prob);
          // System.out.println("front sensor: " + grid[0]);
          // System.out.println("front left sensor: " + grid[1]);
          // System.out.println("front right sensor: " + grid[2]);
          // System.out.println("current sensor: " + current_cell);

          if (current_cell == false) {      // if-else for moving forward.
            if (grid[0] == false || grid[1] == false || grid[2] == false) {
              inf.current_depth = inf.current_depth + 1;
            }
          }
          t.Increment_time();     // incrementing time
          // System.out.println("time is: " + time);
          // System.out.println("current depth: " + inf.current_depth);
          if (inf.current_depth >= border.width) {
            op_file.println((float)prob+","+border.width+","+time);
            System.out.println("prob: "+(float)prob+" ,border-width: "+border.width+" ,time: "+time);
            inf.current_depth = 0;
            break;
          }
          // break;
        }
        // System.out.println("width: " + width);
        border.width += 3;
        t.reset_time();
      }
      // test += 1;
      prob += 0.1;
      // width+=3;
      border.width+=3;
    }
    op_file.close();
  }
}
