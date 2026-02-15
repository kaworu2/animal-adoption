package model.Animals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Animals {
    private final ObservableList<Animal> animals;

    public Animals() {
        this.animals = FXCollections.<Animal>observableArrayList();
    }

    public void add(Animal animal) {
        this.animals.add(animal);
    }

    public void remove(Animal animal) {
        this.animals.remove(animal);
    }

    public ObservableList<Animal> getAnimals() {
        return this.animals;
    }

    //Lookup Pattern
    public Animal animal(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }

    public Animals insertSeedData() {
        this.add(new Cat("Jiu Jiu", 5));
        this.add(new Cat("Abby", 8));
        this.add(new Cat("Nimo", 6));
        this.add(new Cat("Whiskers", 3));
        this.add(new Cat("Luna", 7));
        this.add(new Cat("Oliver", 2));
        this.add(new Cat("Mochi", 1));
        this.add(new Cat("Simba", 6));

        this.add(new Dog("Charlie", 2));
        this.add(new Dog("Buddy", 4));
        this.add(new Dog("Bella", 1));
        this.add(new Dog("Max", 7));
        this.add(new Dog("Rocky", 8));
        this.add(new Dog("Milo", 5));

        this.add(new Rabbit("Carrots", 1));
        this.add(new Rabbit("Coco", 6));
        this.add(new Rabbit("BunBun", 2));
        this.add(new Rabbit("Hazel", 2));
        this.add(new Rabbit("Peanut", 3));

        return this;
    }
}
