package users;

import datastructures.*;
import datastructures.exception.ElementDoesNotExists;
import exceptions.*;
import groups.Group;
import messages.Message;

@SuppressWarnings("unchecked")
public class UserClass implements User {
	private String login, name, address, profession;
	private int age;
	private SortedDictionary<String, User> activeUserContacts;
	// como eh 10 no maximo, nao compensa alterar.
	private List<Group> groupList;
	// OK
	private List<Message> userMessages;


	public UserClass(String login, String name, int age, String address, String profession) {
		this.login = login;
		this.name = name;
		this.age = age;
		this.address = address;
		this.profession = profession;
		// Log time of inclusion, exclusion and find, constant time to iterate each element
		activeUserContacts = new TreeDictionary<>();
		// the user can only be in 10 groups at a time (constant time of inclusion)
		// It has the drawback of O(n) removal, but with n in the maximum of 10, the removal is not much worser than
		// what we get using linked list in the worst case, with the advantage of smaller size and faster iteration.
		groupList = new ArrayList<Group>(10);
		userMessages = new LinkedList<>(); //consistent insertion (always) with having it order by the most recent to the oldest
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
		if (!activeUserContacts.hasKey(user.getName())) {
			activeUserContacts.put(user.getName(), user);
		} else {
			throw new ExistingContact();
		}
	}
	
	public void removeContact(User user) {
		try {
			activeUserContacts.remove(user.getName());
		} catch(ElementDoesNotExists ignore) {
			throw new InexistingContact();
		}
	}
	
	public Iterator<User> listContacts() {
		return activeUserContacts.values();
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
		return user == this || activeUserContacts.hasKey(user.getName());
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
