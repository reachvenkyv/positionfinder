package positionfinder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PositionController {

    @RequestMapping("/index")
    public String init() {
        // Load the index page to get the command from user.
        return "index";
    }

}
