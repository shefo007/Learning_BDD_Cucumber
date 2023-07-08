package shouty;

import io.cucumber.java.en.When;
import shouty.support.ShoutyWorld;

public class ShoutSteps {

    private final ShoutyWorld world;

    public ShoutSteps(ShoutyWorld world) {
        this.world = world;
    }


    @When("{person} shouts")
    public void person_shouts(Person person) {
        world.shout(person.getName(), "Hello world");
    }

    @When("{person} shouts {int} messages containing the word {string}")
    public void person_shouts_messages_containing_the_word(Person person, int count, String word) {
        String message = "a message containing the word " + word;
        for (int i = 0; i < count; i++) {
            world.shout(person.getName(), message);
        }
    }

    @When("{person} shouts {string}")
    public void person_shouts(Person person,String message) {
        world.shout(person.getName(), message);
    }

    @When("{person} shouts the following message")
    public void person_shouts_the_following_message(Person person, String message) {
        world.shout(person.getName(), message);
    }

    @When("{person} shouts a message")
    public void sean_shouts_a_message(Person person) {
        world.shout(person.getName(), "here is a message");
    }

    @When("{person} shouts a long message")
    public void sean_shouts_a_long_message(Person person) {
        String longMessage = String.join(
                "\n",
                "A message from Sean",
                "that spans multiple lines");
        world.shout(person.getName(), longMessage);
    }

    @When("{person} shouts {int} over-long messages")
    public void person_shouts_some_over_long_messages(Person person, int count) {
        String baseMessage = "A message from Sean that is 181 characters long ";
        String padding = "x";
        String overlongMessage = baseMessage + padding.repeat(181 - baseMessage.length());

        for (int i = 0; i < count; i++) {
            world.shout(person.getName(),overlongMessage);
        }
    }
}
