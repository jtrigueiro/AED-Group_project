package groups;


import datastructures.*;
import datastructures.exception.ElementDoesNotExists;
import exceptions.SubscriptionExists;
import exceptions.SubscriptionNotExists;
import messages.Message;
import users.User;

@SuppressWarnings("unchecked")
public class GroupClass implements Group {
	private String group, description;
	private SortedDictionary<String, User> members;
	private List<Message> messages;

	public GroupClass(String group, String description) {
		this.group = group;
		this.description = description;
		members = new TreeDictionary<>(); //fast removal of a member and sorted by login
		messages = new LinkedList<>(); //constant insertion (always) when having it order by the most recent to the oldest
	}
	
	public String getName() {
		return group;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void addMember(User user) {
		if (!members.hasKey(user.getName())) {
			members.put(user.getName(), user);
		} else {
			throw new SubscriptionExists();
		}
	}
	
	public void removeMember(User user) {
		try {
			members.remove(user.getName());
		} catch (ElementDoesNotExists ignore){
			throw new SubscriptionNotExists();
		}
	}

	public Iterator<User> listMembers() {
		return members.values();
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
		return members.hasKey(user.getName());
	}

	public boolean equals(Object o) {
		if (o instanceof Group) {
			return ((Group) o).getName().equalsIgnoreCase(getName());
		}
		return false;
	}




}
