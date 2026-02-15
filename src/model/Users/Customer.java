package model.Users;

import model.Animals.*;


public class Customer extends User {
    private final String email;
    private static final int ADOPTION_LIMIT = 2;
    private final Animals adoptedAnimals;

    public Customer(String name, String email) {
        super(name);
        this.email = email;
        this.adoptedAnimals = new Animals();
    }

    public String getEmail() {
        return email;
    }

    public Animals adoptedAnimals() {
        return adoptedAnimals;
    }

    public boolean canAdopt(Animal animal) {
        int count = 0;
        for (Animal a : adoptedAnimals.getAnimals()) {
            if (a.getClass().equals(animal.getClass())) {
                count++;
            }
        }
        return count < ADOPTION_LIMIT;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.email + ")";
    }
}