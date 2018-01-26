package positionfinder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class PositionFinder {

    public static final String INPUT_FORMAT= "([0-9]|1[0-5]),([0-9]|1[0-5]):([LRF]+)";

    public static final String NORTH= "N";

    public static final String SOUTH= "S";

    public static final String EAST= "E";

    public static final String WEST= "W";

    public class Output {

        private final String output;

        public Output(String output) {
          this.output = output;
        }

        public String getOutput() {
            return output;
        }
    }
    @RequestMapping("/position")
    public Output getPosition(@RequestParam(value="input",required = true) String input){
        Output output;
        Matcher matcher = Pattern.compile(INPUT_FORMAT).matcher(input);
        // Parsing input
        if (!matcher.matches()) {
            output=new Output("Input in invalid format!");
        }else{
            //Valid Input
            int y = Integer.parseInt(matcher.group(1));//6
            int x = Integer.parseInt(matcher.group(2));//6
            String moves = matcher.group(3);//FFLFFLFFLFF
            String direction = NORTH;

            for (int i = 0; i < moves.length(); i++) {
                char move = moves.charAt(i);//F
                switch (move) {
                    //Turn Left
                    case 'L':
                        direction = getDirection(direction, move);
                        break;
                    //Turn Right
                    case 'R':
                        direction = getDirection(direction, move);
                        break;
                    //Move Forward
                    case 'F':
                        if (direction.equals(NORTH)) {
                            y++;
                        } else if (direction.equals(SOUTH)) {
                            y--;
                        } else if (direction.equals(EAST)) {
                            x++;
                        } else if (direction.equals(WEST)) {
                            x--;
                        }
                }
            }
            // Out of grid validation.
            if (x > 15 || x < 0 || y > 15 || y < 0) {
                output=new Output("Out of grid for the given input!");
            } else {
                output=new Output("Final Position : " + y + "," + x);
            }
        }
        return output;
    }

    /*
        Returns the current direction facing based on the turns.
     */
    private String getDirection(String direction, char move) {
        if(move=='L') {
            if (direction.equals(NORTH)) {
                direction = WEST;
            } else if (direction.equals(WEST)) {
                direction = SOUTH;
            } else if (direction.equals(SOUTH)) {
                direction = EAST;
            } else if (direction.equals(EAST)) {
                direction = NORTH;
            }
        }else if(move=='R') {
            if (direction.equals(NORTH)) {
                direction = EAST;
            } else if (direction.equals(EAST)) {
                direction = SOUTH;
            } else if (direction.equals(SOUTH)) {
                direction = WEST;
            } else if (direction.equals(WEST)) {
                direction = NORTH;
            }
        }
        return direction;
    }

}
