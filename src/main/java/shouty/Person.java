package shouty;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private final List<String> messagesHeard = new ArrayList<String>();
    private final String name;
    private final Network network;
    private int location;
    private int credits;

    public Person(String name, Network network, int location, int credits) {
        this.name = name;
        this.network = network;
        this.location = location;
        this.credits = credits;
        network.subscribe(this);
    }

    public int getLocation() {
        return location;
    }

    public Person moveTo(int newLocation) {
        this.location = newLocation;
        return this;
    }

    public void shout(String message) {
        network.broadcast(message, this);
    }

    public void hear(String message) {
        messagesHeard.add(message);
    }

    public List<String> getMessagesHeard() {
        return messagesHeard;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getName() {
        return name;
    }
}
