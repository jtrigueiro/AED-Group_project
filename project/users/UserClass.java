package users;

import datastructures.*;
import exceptions.*;
import groups.Group;
import messages.Message;

@SuppressWarnings("unchecked")
public class UserClass implements User {
	private String login, name, address, profession;
	private int age;
	private Sequence<User> activeUserContacts;
	private List<Group> groupList;
	private List<Message> userMessages;


	public UserClass(String login, String name, int age, String address, String profession) {
		this.login = login;
		this.name = name;
		this.age = age;
		this.address = address;
		this.profession = profession;
		activeUserContacts = new SortedSequence<>();
		groupList = new ArrayList<Group>(10);
		userMessages = new LinkedList<>();
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getProfession() {
		return profession;
	}

	public void addContact(User user) {
		if (activeUserContacts.find(user) != -1) {
			throw new ExistingContact();
		}
		activeUserContacts.add(user);
	}
	
	public void removeContact(User user) {
		if (!activeUserContacts.remove(user)) {
			throw new InexistingContact();
		}
	}
	
	public Iterator<User> listContacts() {
		return activeUserContacts.iterator();
	}

	public Iterator<Message> listMessages() {
		return userMessages.iterator();
	}
	
	public void receiveMessage(Message message) {
		this.userMessages.addFirst(message);
	}

	public void addGroup(Group group) {
		if (groupList.size() >= 10) {
			throw new TooManyGroupsForUser();
		}
		groupList.addLast(group);
	}

	public void removeGroup(Group group) {
		if (!groupList.remove(group)) {
			throw new NoSuchGroup();
		}
	}

	public Iterator<Group> getGroups() {
		return groupList.iterator();
	}

	public boolean hasContact(User user) {
		return user == this || activeUserContacts.find(user) != -1;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			return ((User) o).getLogin().equalsIgnoreCase(getLogin());
		}
		return false;
	}

	public int compareTo(Object o) {
		if (o instanceof User) {
			return this.getName().compareTo(((User) o).getName());
		}
		return 0;
	}
}
