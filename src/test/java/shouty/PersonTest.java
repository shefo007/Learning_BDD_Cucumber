package shouty;

import org.junit.Test;
import org.mockito.Mockito;

import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PersonTest {

    private final Network network = Mockito.mock(Network.class);
    private final String message = "Free bagels!";

    @Test
    public void it_subscribes_to_the_network() {
        Person person = new Person("A Person:", network, 100, 0);
        verify(network).subscribe(person);
    }

    @Test
    public void it_has_a_location() {
        Person person = new Person("A Person:", network, 100,0);
        assertEquals(100, person.getLocation());
    }

    @Test
    public void broadcasts_shouts_to_the_network() {
        Person sean = new Person("Sean", network, 0,0);
        sean.shout(message);
        verify(network).broadcast(message, sean);
    }

    @Test
    public void remembers_messages_heard() {
        Person lucy = new Person("Lucy", network, 100,0);
        lucy.hear(message);
        assertEquals(singletonList(message), lucy.getMessagesHeard());
    }

    @Test
    public void can_be_moved_to_new_location() {
        Person lucy = new Person("Lucy", network, 0,0);
        assertEquals(lucy.moveTo(100).getLocation(), 100);
    }

}
