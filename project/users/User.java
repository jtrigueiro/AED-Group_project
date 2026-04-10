package users;

import datastructures.Iterator;
import groups.Group;
import groups.GroupClass;
import messages.Message;

public interface User extends Comparable {
    int compareTo(Object o);
    String getName();

    String getLogin();

    void addContact(User user);

    void removeContact(User user);
    Iterator<User> listContacts();
    Iterator<Group> getGroups();

    void removeGroup(Group group);
    void addGroup(Group group);

    void receiveMessage(Message message);
    Iterator<Message> listMessages();

    boolean hasContact(User secondUser);

    int getAge();

    String getAddress();

    String getProfession();
}
