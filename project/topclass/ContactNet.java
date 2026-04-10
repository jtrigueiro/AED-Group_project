package topclass;

import datastructures.Iterator;
import exceptions.*;
import groups.Group;
import messages.Message;
import users.User;

public interface ContactNet {
	
	
	void insertUser(String login, String name, int age, String address, String profession) 
			throws UserExists;
	
	User getUser(String login) throws UserNotExists;
	
	void insertContact(String login1, String login2) 
			throws UserNotExists, ExistingContact;
	
	void removeContact(String login1, String login2) 
			throws UserNotExists, InexistingContact, ContactNotRemoved;

	Iterator<User> listContacts(String login)
			throws UserNotExists, NoContacts;
	
	void insertGroup(String group, String description) throws GroupExists;
	
	Group getGroup(String group) throws NoSuchGroup;
	
	void removeGroup(String group) throws NoSuchGroup;
	
	void subscribeGroup(String login, String group) 
			throws UserNotExists, NoSuchGroup, SubscriptionExists;
	
	void removeSubscription(String login, String group)
		throws UserNotExists, NoSuchGroup, SubscriptionNotExists;

	Iterator<User> listParticipants(String group)
			throws NoSuchGroup, NoParticipants;

	void insertMessage(String login, String title, String text, String url)
		throws UserNotExists;
	
	Iterator<Message> listContactMessages(String login1, String login2)
		throws UserNotExists, InexistingContact, NoContactMessages;
	
	Iterator<Message> listGroupMessages(String group, String login)
			throws NoSuchGroup, UserNotExists, SubscriptionNotExists, NoGroupMessages;
}
