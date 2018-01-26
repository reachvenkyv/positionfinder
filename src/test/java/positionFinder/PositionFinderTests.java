package positionFinder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PositionFinderTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void invalidInput() throws Exception {
        // Vertical input check
        this.mockMvc.perform(get("/position?input=X,5:RFL")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Input in invalid format!"));
        // Horizontal input check
        this.mockMvc.perform(get("/position?input=5,X:RFL")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Input in invalid format!"));
        // Missing comma check
        this.mockMvc.perform(get("/position?input=5X:RFL")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Input in invalid format!"));
        // Invalid  seperator
        this.mockMvc.perform(get("/position?input=5:X:RFL")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Input in invalid format!"));
        // Missing quotes
        this.mockMvc.perform(get("/position?input=5,5RFL")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Input in invalid format!"));
        // Invalid Seperator
        this.mockMvc.perform(get("/position?input=5,5,RFL")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Input in invalid format!"));
    }

    @Test
    public void outOfGrid() throws Exception {
        // Out of Grid
        this.mockMvc.perform(get("/position?input=6,6:FFFFFFFFFF")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Out of grid for the given input!"));
    }

    @Test
    public void validInput() throws Exception {
        // Valid Input - 5,5:RFLFRFLF5
        this.mockMvc.perform(get("/position?input=5,5:RFLFRFLF")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Final Position : 7,7"));
        // Valid Input - 5,5:RFLFRFLF5
        this.mockMvc.perform(get("/position?input=6,6:FFLFFLFFLFF")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Final Position : 6,6"));
        // Valid Input - 5,5:RFLFRFLF5
        this.mockMvc.perform(get("/position?input=5,5:FLFLFFRFFF")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.output").value("Final Position : 4,1"));
    }

}
