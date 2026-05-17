package model;

public class Member {
    private final int id;
    private final String name;
    private final String email;

    public Member(int id, String name, String email) {
        if (id <= 0) {
            throw new IllegalArgumentException("Member ID must be positive.");
        }

        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String toCsv() {
        return id + "," + name.replace(",", " ") + "," + email.replace(",", " ");
    }

    public static Member fromCsv(String line) {
        String[] parts = line.split(",", -1);
        return new Member(Integer.parseInt(parts[0]), parts[1], parts[2]);
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + email;
    }
}
