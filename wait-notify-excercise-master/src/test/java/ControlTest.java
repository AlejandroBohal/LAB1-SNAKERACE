import edu.eci.arsw.primefinder.Control;
import org.junit.Assert;
import org.junit.Test;

public class ControlTest {

    @Test
    public void getT(){
        Control control = Control.newControl();
        control.getT();
        Assert.assertEquals(control.getT(),5000);
    }
}
