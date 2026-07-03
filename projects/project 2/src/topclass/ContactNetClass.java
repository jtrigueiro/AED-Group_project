package topclass;

import datastructures.Dictionary;
import datastructures.Iterator;
import exceptions.*;
import groups.Group;
import groups.GroupClass;
import datastructures.ChainedHashTable;
import messages.Message;
import messages.MessageClass;
import users.User;
import users.UserClass;

public class ContactNetClass implements ContactNet {
    private final Dictionary<String, User> users;
    private final Dictionary<String, Group> groups;

    public ContactNetClass() {
        // Constant time of inclusion, removal and search
        groups = new ChainedHashTable<>();
        // Constant time of inclusion, removal and search
        users = new ChainedHashTable<>();
    }

    @Override
    public void insertUser(String login, String name, int age, String address, String profession) throws UserExists {
        if (users.find(login) != null) {
            throw new UserExists();
        }
        this.users.put(login, new UserClass(login, name, age, address, profession));
    }

    @Override
    public User getUser(String login) throws UserNotExists {
        User temp = users.find(login);
        if(temp == null)
            throw new UserNotExists();
        else
            return temp;
    }

    @Override
    public void insertContact(String login1, String login2) throws UserNotExists, SameUsers, ExistingContact {
        User firstUser = getUser(login1);
        if (login1.equalsIgnoreCase(login2)) {
            throw new SameUsers();
        }
        User secondUser = getUser(login2);
        firstUser.addContact(secondUser);
        secondUser.addContact(firstUser);
    }

    @Override
    public void removeContact(String login1, String login2) throws UserNotExists, InexistingContact, ContactNotRemoved {
        User firstUser = getUser(login1);
        if (login1.equalsIgnoreCase(login2)) {
            throw new SameUsers();
        }
        User secondUser = getUser(login2);
        firstUser.removeContact(secondUser);
        secondUser.removeContact(firstUser);
    }

    @Override
    public Iterator<User> listContacts(String login) throws UserNotExists, NoContacts {
        User user = getUser(login);
        Iterator<User> iterator = user.listContacts();
        if (!iterator.hasNext()) {
            throw new NoContacts();
        }
        return user.listContacts();
    }

    @Override
    public Group getGroup(String name) throws NoSuchGroup {
        Group temp = groups.find(name);
        if(temp == null)
            throw new NoSuchGroup();
        else
            return temp;
    }

    @Override
    public void insertGroup(String name, String description) throws GroupExists {
        if (groups.find(name) != null) {
            throw new GroupExists();
        }
        groups.put(name, new GroupClass(name, description));
    }


    @Override
    public void removeGroup(String name) throws NoSuchGroup {
        if(groups.remove(name) == null)
            throw new NoSuchGroup();
    }

    @Override
    public void subscribeGroup(String login, String groupName) throws UserNotExists, NoSuchGroup, SubscriptionExists {
        User user = getUser(login);
        Group group = getGroup(groupName);
        group.addMember(user);
        user.addGroup(group);
    }

    @Override
    public void removeSubscription(String login, String groupName) throws UserNotExists, NoSuchGroup, SubscriptionNotExists {
        User user = getUser(login);
        Group group = getGroup(groupName);
        group.removeMember(user);
        user.removeGroup(group);
    }

    @Override
    public Iterator<User> listParticipants(String groupName) throws NoSuchGroup, NoParticipants {
        Iterator<User> userIterator = getGroup(groupName).listMembers();
        if (!userIterator.hasNext()) {
            throw new NoParticipants();
        }
        return userIterator;
    }

    @Override
    public void insertMessage(String login, String title, String text, String url) throws UserNotExists {
        User user = getUser(login);
        Message message = new MessageClass(title, text, url);
        user.receiveMessage(message);
        Iterator<User> userIterator = user.listContacts();
        while (userIterator.hasNext()) {
            User contact = userIterator.next();
            contact.receiveMessage(message);
        }
        Iterator<Group> groupIterator = user.getGroups();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            group.receiveMessage(message);
        }
    }

    @Override
    public Iterator<Message> listContactMessages(String login1, String login2) throws UserNotExists, InexistingContact, NoContactMessages {
        User firstUser = getUser(login1);
        User secondUser = getUser(login2);
        if (!firstUser.hasContact(secondUser)) {
            throw new InexistingContact();
        }
        final Iterator<Message> messageIterator = firstUser.listMessages();
        if (!messageIterator.hasNext()) {
            throw new NoContactMessages();
        }
        return messageIterator;
    }

    @Override
    public Iterator<Message> listGroupMessages(String groupName, String login) throws NoSuchGroup, UserNotExists, SubscriptionNotExists, NoGroupMessages {
        Group group = getGroup(groupName);
        User user = getUser(login);
        if (!group.hasMember(user)) {
            throw new SubscriptionNotExists();
        }
        Iterator<Message> messages = group.getMessages();
        if (!messages.hasNext()) {
            throw new NoGroupMessages();
        }
        return messages;
    }
}