package groups;

import datastructures.Iterator;
import exceptions.SubscriptionExists;
import exceptions.SubscriptionNotExists;
import messages.Message;
import users.User;

public interface Group {
	 /**
     * Returns the name of the group
     * @return name of the group
     */
    String getName();
    /**
     * Returns the description of the group
     * @return description of the group
     */
    String getDescription();
    /**
     * Adds a user to the group member list if the user is not yet in it otherwise throws an exception
     * @param user, user being added to the group
     * @throws SubscriptionExists, if the user is already in the group
     */
    void addMember(User user);
    /**
     * Removes a user from the group member list if the user is in it otherwise throws an exception
     * @param user, user being removed from the group
     * @throws SubscriptionNotExists, if the user is not in the group
     */
    void removeMember(User user);
    /**
     * @return members of the group to the iterator
     */
    Iterator<User> listMembers();
    /**
     * Receives a message from a user
     * @param message, message already created(with title, text and url) that is going to received
     */
    void receiveMessage(Message message);
    /**
     * @returns all the messages in the group to the iterator to be listed
     */
    Iterator<Message> getMessages();
    /**
     * Checks if the user is member of the group
     * @param user, user that is in the group
     * @returns true, if the user is in the group member list otherwise returns false
     */
    boolean hasMember(User user);
    /**
     * Compares if both groups have the same name
     * @param o, group being compared to by name
     * @returns true, if the groups have the same name, false otherwise
     */
    boolean equals(Object o);

   
}
