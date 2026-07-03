package users;

import datastructures.Iterator;
import exceptions.NoSuchGroup;
import groups.Group;
import messages.Message;

public interface User extends Comparable {
    /**
   	 * Returns the login of the user
   	 * @return login of the user
   	 */
    String getLogin();
    /**
   	 * Returns the name of the user
   	 * @return name of the user
   	 */
    String getName();
    /**
   	 * Returns the age of the user
   	 * @return age of the user
   	 */
    int getAge();
    /**
   	 * Returns the address of the user
   	 * @return address of the user
   	 */
    String getAddress();
    /**
   	 * Returns the profession of the user
   	 * @return profession of the user
   	 */
    String getProfession();
    /**
   	 * Adds a contact with a user if he/she hasnt one already
   	 * @param user, user getting in contact with
   	 * @throws ExistingContact, if there is already a contact between the users
   	 */
    void addContact(User user);
    /**
   	 * Removes the contact between the users if there is one
   	 * @param user, user being removed from being in contact with
   	 * @throws InexistingContact, if there isnt a contact between the users
   	 */
    void removeContact(User user);
    /**
   	 * @return all the current contacts to the iterator to be listed
   	 */
    Iterator<User> listContacts();
    /**
   	 * @return all the messages received to the iterator to be listed
   	 */
    Iterator<Message> listMessages();
    /**
   	 * Receives a message from himself or a certain user if they have a current contact
   	 * @param message, the message being received
   	 * @throws InexistingContact, if he/she doesnt have a current contact with this user
   	 */
    void receiveMessage(Message message);
    /**
   	 * Adds the user to the group if he/she hasnt reach the 10 group mark
   	 * @throws TooManyGroupsForUser, if the user has reached the 10 group mark
   	 */
    void addGroup(Group group);
    /**
   	 * Removes the user from a goup if he is in it
   	 * @param group, group that the user is leaving
   	 * @throws NoSuchGroup, if the user is not in the group
   	 */
    void removeGroup(Group group);
    /**
   	 * @return all the user groups to the iterator to be listed
   	 */
    Iterator<Group> getGroups();
    /**
   	 * Checks if the he/she has a current contact with that user
   	 * @param secondUser, user being checked if has a current contact with he/she
   	 * @return true if they have a current contact, false otherwise
   	 */
    boolean hasContact(User secondUser);
    boolean equals(Object o);
    /**
   	 * Compares if is the users have the same name (if they are the same)
   	 * @param o, user being the compared with
   	 * @return 0 if users name are the same
   	 */
    int compareTo(Object o);
	
   
}
