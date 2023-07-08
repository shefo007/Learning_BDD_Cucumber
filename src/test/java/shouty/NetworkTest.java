package shouty;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NetworkTest {

    private final int DEFAULT_RANGE = 100;
    private final Network network = new Network(DEFAULT_RANGE);
    private final String message = "Free bagels!";

    @Test
    public void broadcasts_a_message_to_a_listener_within_range() {
        Person sean = new Person("Sean", network, 0, 0);
        Person lucy = Mockito.mock(Person.class);
        network.subscribe(lucy);
        network.broadcast(message, sean);

        verify(lucy).hear(message);
    }

    @Test
    public void does_not_broadcast_a_message_to_a_listener_out_of_range() {
        Person sean = new Person("Sean", network, 0, 0);
        Person laura = Mockito.mock(Person.class);
        when(laura.getLocation()).thenReturn(101);
        network.subscribe(laura);
        network.broadcast(message, sean);

        verify(laura, never()).hear(message);
    }

    @Test
    public void does_not_broadcast_a_message_to_a_listener_out_of_range_negative_distance() {
        Person sally = new Person("Sally", network, 101, 0);
        Person lionel = Mockito.mock(Person.class);
        when(lionel.getLocation()).thenReturn(0);
        network.subscribe(sally);
        network.broadcast(message, lionel);

        verify(lionel, never()).hear(message);
    }

    @Test
    public void does_not_broadcast_a_message_over_180_characters_even_if_listener_is_in_range() {
        Person sean = new Person("Sean", network, 0, 0);

        char[] chars = new char[181];
        Arrays.fill(chars, 'x');
        String longMessage = String.valueOf(chars);

        Person laura = Mockito.mock(Person.class);
        network.subscribe(laura);
        network.broadcast(longMessage, sean);

        verify(laura, never()).hear(longMessage);
    }

    @Test
    public void deducts_2_credits_for_a_shout_over_180_characters() {
        char[] chars = new char[181];
        Arrays.fill(chars, 'x');
        String longMessage = String.valueOf(chars);

        Person sean = new Person("Sean", network, 0, 0);
        Person laura = new Person("Laura", network, 0, 10);


        network.subscribe(laura);
        network.broadcast(longMessage, sean);

        assertEquals(-2, sean.getCredits());
    }

    @Test
    public void deducts_5_credits_for_mentioning_the_word_buy() {
        String message = "Come buy these awesome croissants";

        Person sean = new Person("Sean", network, 0, 100);
        Person laura = new Person("Laura", network, 0, 10);

        network.subscribe(laura);
        network.broadcast(message, sean);

        assertEquals(95, sean.getCredits());
    }

    @Test
    public void deducts_5_credits_for_mentioning_the_word_buy_several_times() {
        String message = "Come buy buy buy these awesome croissants";

        Person sean = new Person("Sean", network, 0, 100);
        Person laura = new Person("Laura", network, 0, 10);

        network.subscribe(laura);
        network.broadcast(message, sean);

        assertEquals(95, sean.getCredits());
    }

    @Test
    public void deducts_5_credits_if_the_word_buy_is_capitalized() {
        String message = "Come Buy these awesome croissants";

        Person sean = new Person("Sean", network, 0, 100);
        Person laura = new Person("Laura", network, 0, 10);

        network.subscribe(laura);
        network.broadcast(message, sean);

        assertEquals(95, sean.getCredits());
    }
}
