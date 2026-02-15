package model.Users;

public class Manager extends User {
    private final int managerID;

    public Manager(String name, int managerID) {
        super(name);
        this.managerID = managerID;
    }

    public int getManagerID() {
        return managerID;
    }

    @Override
    public String toString() {
        return name + " (Manager)";
    }
}