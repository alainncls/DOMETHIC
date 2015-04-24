package fr.epf.medfile.daos.user;

import java.util.List;

import fr.epf.medfile.models.User;

public interface UserService {
    long addUser(int iD, String firstName, String lastName, String cps, String birthDate, String sex, String service, String address, String city, String zipCode, String email, String phoneNumber, String healthInfos, String birthAddress, String password, String login);

    List<User> getUsers();
    User getUser(int id);
    boolean removeUser(int id);
    boolean clearUsers();
    User getConnectedUser();
    boolean connectUser(String login, String password);
    boolean isEmpty();
}
