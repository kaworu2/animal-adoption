package model.Users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Application.AdoptionCentre;
import model.Exceptions.InvalidOperationException;
import model.Exceptions.UnauthorizedAccessException;
import model.Animals.*;

public class Users {
    private final ObservableList<User> users;

    public Users() {
        users = FXCollections.<User>observableArrayList();
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void add(User user) {
        users.add(user);
    }

    public Users insertSeedData(Animals seedAnimals) {
        this.add(new Customer("1", "1"));
        this.add(new Manager("David Dyer", 12345));
        this.add(new Manager("Rishik Sood", 48954));

        this.add(new Customer("Dahyun Kim", "dahyun@twice.com"));
        this.add(new Customer("Zyzz", "Aziz@gains.net"));

        Customer daisy = new Customer("Daisy Doodles", "daisy25@gmail.com");
        Animal nimo = seedAnimals.getAnimals().stream().filter(animal -> animal.getName().equals("Nimo")).findFirst().get();
        daisy.adoptedAnimals().add(nimo);
        nimo.adopt();
        this.add(daisy);

        Customer jenny = new Customer("Jenny Jenson", "jenny123@gmail.com");
        Animal oliver = seedAnimals.getAnimals().stream().filter(animal -> animal.getName().equals("Oliver")).findFirst().get();
        jenny.adoptedAnimals().add(oliver);
        oliver.adopt();
        this.add(jenny);

        return this;
    }

    public Customer validateCustomer(String name, String email) throws UnauthorizedAccessException{
        for (User u : this.users) {
            if (u instanceof Customer) {
                if (u.getName().equals(name) && ((Customer) u).getEmail().equals(email)) {
                    AdoptionCentre.setLoggedInUser(u);
                    return (Customer) u;
                }
            }
        }
        throw new UnauthorizedAccessException("Invalid customer credentials");
    }

    public Manager validateManager(String managerId) throws UnauthorizedAccessException, InvalidOperationException {
        int id;
        try {
            id = Integer.parseInt(managerId);
        }
        catch (NumberFormatException e) {
            throw new InvalidOperationException("Id must be an integer");
        }
        for (User u : this.users) {
            if (u instanceof Manager) {
                if (((Manager)u).getManagerID() == id) {
                    AdoptionCentre.setLoggedInUser(u);
                    return (Manager) u;
                }
            }
        }
        throw new UnauthorizedAccessException("Invalid manager credentials");
    }
}
