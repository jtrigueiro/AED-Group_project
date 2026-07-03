package groups;


import datastructures.*;
import exceptions.SubscriptionExists;
import exceptions.SubscriptionNotExists;
import messages.Message;
import users.User;

@SuppressWarnings("unchecked")
public class GroupClass implements Group {
	private String group, description;
	private Sequence<User> members;
	private List<Message> messages;

	public GroupClass(String group, String description) {
		this.group = group;
		this.description = description;
		members = new SortedSequence<>();
		messages = new LinkedList<>();
	}
	
	public String getName() {
		return group;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void addMember(User user) {
		if (members.find(user) != -1) {
			throw new SubscriptionExists();
		}
		members.add(user);
	}
	
	public void removeMember(User user) {

		if(!members.remove(user)) {
			throw new SubscriptionNotExists();
		}
	}

	public Iterator<User> listMembers() { // falta o iterador da linkedlist
		return members.iterator();
	}
	
	public void receiveMessage(Message message) {
		messages.addFirst(message);
	}

	@Override
	public Iterator<Message> getMessages() {
		return messages.iterator();
	}

	@Override
	public boolean hasMember(User user) {
		return members.find(user) != -1;
	}


	public boolean equals(Object o) {
		if (o instanceof Group) {
			return ((Group) o).getName().equalsIgnoreCase(getName());
		}
		return false;
	}




}
