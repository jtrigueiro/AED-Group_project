package topclass;

import datastructures.Iterator;
import datastructures.LinkedList;
import datastructures.List;
import exceptions.*;
import groups.Group;
import groups.GroupClass;
import messages.Message;
import messages.MessageClass;
import users.User;
import users.UserClass;

public class ContactNetClass implements ContactNet {
    private final List<User> users;
    private final List<Group> groups;

    public ContactNetClass() {
        groups = new LinkedList<>();
        users = new LinkedList<>();
    }

    @Override
    public void insertUser(String login, String name, int age, String address, String profession) throws UserExists {
        User user = new UserClass(login, name, age, address, profession);
        if (users.find(user) != -1) {
            throw new UserExists();
        }
        this.users.addLast(user);
    }

    @Override
    public User getUser(String login) throws UserNotExists {
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User user = it.next();
            if (user.getLogin().equalsIgnoreCase(login)) {
                return user;
            }
        }
        throw new UserNotExists();
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
        if (name.equals("GRUPO1")) {
            int a = 1;
        }
        Iterator<Group> it = groups.iterator();
        while (it.hasNext()) {
            Group group = it.next();
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        throw new NoSuchGroup();
    }

    @Override
    public void insertGroup(String name, String description) throws GroupExists {
        // TODO: repensar isto.
        Group group = new GroupClass(name, description);
        if (groups.find(group) != -1) {
            throw new GroupExists();
        }
        groups.addLast(group);
    }


    @Override
    public void removeGroup(String name) throws NoSuchGroup {
        Group group = getGroup(name);
        Iterator<User> it = group.listMembers();
        while(it.hasNext()) {
            User member = it.next();
            member.removeGroup(group);
        }
        // TODO: aprimorar isto
        groups.remove(group);
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
