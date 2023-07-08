package shouty;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import shouty.support.ShoutyWorld;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class StepDefinitions {

    private final ShoutyWorld world;
    public StepDefinitions(ShoutyWorld world) {
        this.world = world;
    }

    static class Whereabouts {
        private final String name;
        private final Integer location;

        public Whereabouts(String name, int location) {
            this.name = name;
            this.location = location;
        }
    }

    @DataTableType
    public Whereabouts defineWhereabouts(Map<String, String> entry) {
        return new Whereabouts(entry.get("name"), Integer.parseInt(entry.get("location")));
    }

    @Given("the range is {int}")
    public void the_range_is(int range) {
        world.network = new Network(range);
    }

    @Given("people are located at")
    public void people_are_located_at(@Transpose List<Whereabouts> whereabouts) {
        for (Whereabouts whereabouts1 : whereabouts) {
            String name = whereabouts1.name;
            world.people.put(name, new Person(name, world.network, whereabouts1.location,0));
        }
    }

    @Given("{person} has bought {int} credits")
    public void person_has_bought_credits(Person person,int credits) {
        person.setCredits(credits);
    }


    @Then("{person} should hear {person}'s message")
    public void person_should_hear_sean_s_message(Person person, Person person2) {
        List<String> messages = world.messagesShoutedBy.get(person2.getName());
        assertEquals(messages, world.people.get(person.getName()).getMessagesHeard());
    }

    @Then("{person} should hear a shout")
    public void person_should_hear_a_shout(Person person) {
        assertEquals(1, world.people.get(person.getName()).getMessagesHeard().size());
    }

    @Then("{person} should not hear a shout")
    public void person_should_not_hear_a_shout(Person person) {
        assertEquals(0, world.people.get(person.getName()).getMessagesHeard().size());
    }

    @Then("{person} hears the following messages:")
    public void person_hears_the_following_messages(Person person, io.cucumber.datatable.DataTable expectedMessages) {
        List<List<String>> actualMessages = new ArrayList<List<String>>();
        List<String> heardMessages = world.people.get(person.getName()).getMessagesHeard();

        for (String message : heardMessages) {
            actualMessages.add(Collections.singletonList(message));
        }

        expectedMessages.diff(DataTable.create(actualMessages));
    }

    @Then("{person} hears all {person}'s messages")
    public void lucy_hears_all_Sean_s_messages(Person person, Person person2) throws Throwable {
        List<String> heardByLucy = world.people.get(person.getName()).getMessagesHeard();
        List<String> messagesFromSean = world.messagesShoutedBy.get(person2.getName());

        // HamCrest's hasItems matcher wants an Array, not a List.
        String[] messagesFromSeanArray = messagesFromSean.toArray(new String[messagesFromSean.size()]);
        assertThat(heardByLucy, hasItems(messagesFromSeanArray));
    }

    @Then("{person} should have {int} credits")
    public void sean_should_have_credits(Person person, int expectedCredits) {
        assertEquals(expectedCredits, world.people.get(person.getName()).getCredits());
    }

}