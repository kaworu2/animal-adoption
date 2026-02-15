package model.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Animals.*;
import model.Users.*;

public class AdoptionCentre {
    private final Animals animals;
    private final Users users;
    public static User loggedInUser;

    public AdoptionCentre(Animals seedAnimals, Users seedUsers) {
        this.animals = seedAnimals;
        this.users = seedUsers;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public Animals getAnimals() {
        return this.animals;
    }

    public Users getUsers() {
        return this.users;
    }
}
