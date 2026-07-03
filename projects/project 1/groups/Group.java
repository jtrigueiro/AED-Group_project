package groups;

import datastructures.Iterator;
import messages.Message;
import users.User;

public interface Group {

    String getName();
    void addMember(User user);
    Iterator<User> listMembers();
    void removeMember(User user);
    void receiveMessage(Message message);

    Iterator<Message> getMessages();

    boolean hasMember(User user);

    String getDescription();
}
