package topclass;

import datastructures.Iterator;
import exceptions.*;
import groups.Group;
import messages.Message;
import users.User;

public interface ContactNet {
	 /**
   	 * Inserts a new user to the users list if there isnt some one with the same login already
   	 * @param login, users unique id
   	 * @param name, name of the user
   	 * @param age, age of the user
   	 * @param address, address of the user
   	 * @param profession, profession of the user
   	 * @throws UserExists, if there is already a user with the same login
   	 */
	void insertUser(String login, String name, int age, String address, String profession) 
			throws UserExists;
	 /**
   	 * Returns a user with a certain login if he/she exists
   	 * @param login, user id
   	 * @throws UserNotExists, if there isnt any user with that login
   	 * @return the user with that login
   	 */
	User getUser(String login)
			throws UserNotExists;
	/**
   	 * Creates a contact between two users if they havent one already
   	 * @param login1, user 1 login id
   	 * @param login2, user 2 login id
   	 * @throws UserNotExists, if there isnt one/any user with that login
   	 * @throws ExistingContact, when the login ids are the same or the users already have a current contact
   	 */
	void insertContact(String login1, String login2) 
			throws UserNotExists, ExistingContact;
	 /**
   	 * Removes a contact between two users if they have one
   	 * @param login1, user 1 login id
   	 * @param login2, user 2 login id
   	 * @throws UserNotExists, if there isnt one/any user with that login
   	 * @throws InexistingContact, when the login ids are the same
   	 * @throws ContactNotRemoved when there isnt a current contact between the users
   	 */
	void removeContact(String login1, String login2) 
			throws UserNotExists, InexistingContact, ContactNotRemoved;
	/**
   	 * Returns the contacts from a user to the iterator to be listed
   	 * @param login, user login id
   	 * @throws UserNotExists, if there isnt any user with that login
   	 * @throws NoContacts, when the user doesnt have contacts
   	 * @return all the user contacts to the iterator
   	 */
	Iterator<User> listContacts(String login)
			throws UserNotExists, NoContacts;
	/**
   	 * Inserts a new group to the groups list if there isnt one with the same name already
   	 * @param group, group name
   	 * @param description, group description
   	 * @throws GroupExists, if there is already a group with the same name
   	 */
	void insertGroup(String group, String description)
			throws GroupExists;
	 /**
   	 * Returns a group with a certain name if it exists
   	 * @param group, group name
   	 * @throws NoSuchGroup, if there isnt any user with that login
   	 * @return the group with that name
   	 */
	Group getGroup(String group)
			throws NoSuchGroup;
	/**
   	 * Removes a group from the groups list if there is one with a certain name
   	 * @param group, group name
   	 * @throws NoSuchGroup, if there isnt any group with that name
   	 */
	void removeGroup(String group)
			throws NoSuchGroup;
	/**
   	 * Adds a user to a group if they arent already in it
   	 * @param login, user login id
   	 * @param group, group name
   	 * @throws UserNotExists, if there isnt any user with that login
   	 * @throws NoSuchGroup, if there isnt any group with that name
   	 * @throws SubscriptionExists, if the user is already in the group
   	 */
	void subscribeGroup(String login, String group) 
			throws UserNotExists, NoSuchGroup, SubscriptionExists;
	/**
   	 * Removes a user from a group if they are already in it
   	 * @param login, user login id
   	 * @param group, group name
   	 * @throws UserNotExists, if there isnt any user with that login
   	 * @throws NoSuchGroup, if there isnt any group with that name
   	 * @throws SubscriptionExists, if the user isnt in the group
   	 */
	void removeSubscription(String login, String group)
			throws UserNotExists, NoSuchGroup, SubscriptionNotExists;
	/**
   	 * Returns the members from a group to the iterator to be listed
   	 * @param group, group name
   	 * @throws NoSuchGroup, if there isnt any group with that name
   	 * @throws NoParticipants, if the group doesnt have any members
   	 * @return the group members
   	 */
	Iterator<User> listParticipants(String group)
			throws NoSuchGroup, NoParticipants;
	/**
   	 * Creates and sends a new message to the user, contacts and groups
   	 * @param login, user login id
   	 * @param title, title of the message
   	 * @param text, body of the message
   	 * @param url, message url
   	 * @throws UserNotExists, if there isnt any user with that login
   	 */
	void insertMessage(String login, String title, String text, String url)
			throws UserNotExists;
	/**
   	 * Returns the messages that the user1 received if the user2 is a current contact to the iterator to be listed
   	 * @param login1, user 1 login id
   	 * @param login2, user 2 login id
   	 * @throws UserNotExists, if there isnt one/any users with that login id
   	 * @throws InexistingContact, if there isnt any current contact between the users
   	 * @throws NoContactMessages, if the user1 hasnt received any messages
   	 * @return all the user1 messages received
   	 */
	Iterator<Message> listContactMessages(String login1, String login2)
			throws UserNotExists, InexistingContact, NoContactMessages;
	/**
   	 * Returns the group messages received from the its members to the iterator to be listed
   	 * @param group, group name
   	 * @param login, user login id that is asking to see the messages
   	 * @throws NoSuchGroup, if there isnt any group with that name
   	 * @throws UserNotExists,  if there isnt any user with that login id
   	 * @throws SubscriptionNotExists, if the user isnt a member of the group
   	 * @throws NoGroupMessages, if the group hasnt received any messages
   	 * @return all the messages received from its users
   	 */
	Iterator<Message> listGroupMessages(String group, String login)
			throws NoSuchGroup, UserNotExists, SubscriptionNotExists, NoGroupMessages;
}
