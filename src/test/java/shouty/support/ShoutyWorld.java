package shouty.support;

import shouty.Network;
import shouty.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoutyWorld {

    private static final int DEFAULT_RANGE = 100;
    public Network network;
    public Map<String, Person> people = new HashMap<String, Person>();
    public Map<String, List<String>> messagesShoutedBy = new HashMap<String, List<String>>();

    public void shout(String name,String message) {
        people.get(name).shout(message);
        List<String> messages = messagesShoutedBy.get(name);
        if (messages == null) {
            messages = new ArrayList<String>();
            messagesShoutedBy.put(people.get(name).getName(), messages);
        }
        messages.add(message);
    }

}
