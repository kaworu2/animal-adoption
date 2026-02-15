package model.Animals;

import javafx.beans.property.*;

public class Animal {
    protected StringProperty name;
    protected IntegerProperty age;
    protected BooleanProperty isAdopted;

    public Animal(String name, int age) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.isAdopted = new SimpleBooleanProperty(false);
    }

    public String getName() { return name.get(); }
    public boolean isAdopted() { return isAdopted.get(); }
    public void adopt() {
        isAdopted.set(true);
    }

    public StringProperty nameProperty() {
        return name;
    }
    public IntegerProperty ageProperty() {
        return age;
    }

    public StringProperty typeProperty() {
        switch (this.getClass().getSimpleName()) {
            case "Cat": return new SimpleStringProperty("Cat");
            case "Dog": return new SimpleStringProperty("Dog");
            case "Rabbit": return new SimpleStringProperty("Rabbit");
        }
        return new SimpleStringProperty("");
    }

    public StringProperty isAdoptedProperty() {
        return isAdopted() ? new SimpleStringProperty("Adopted") : new SimpleStringProperty("Available");
    }

    @Override
    public String toString() {
        return name.get() + " (Age: " + age.get() + ")";
    }
}
