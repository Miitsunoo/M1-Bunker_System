package Features;

import java.util.ArrayList;
import java.util.Scanner;

public class ManageOccupantProfiles {

    // POLYMORPHISM: List of superclass
    static ArrayList<Person> persons = new ArrayList<>();
    static int nextId = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n======================================");
            System.out.println("   UNDERGROUND BUNKER MANAGEMENT");
            System.out.println("======================================");
            System.out.println("[1] Register Bunker Occupant");
            System.out.println("[2] View Occupant Profile");
            System.out.println("[3] Edit Occupant Profile");
            System.out.println("[4] View All Occupants");
            System.out.println("[5] Exit");
            System.out.print("Select Option: ");

            try {
                choice = sc.nextInt(); sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("❌ Invalid input.");
                continue;
            }

            switch (choice) {
                case 1: register(sc); break;
                case 2: viewProfile(sc); break;
                case 3: editProfile(sc); break;
                case 4: viewAll(); break;
                case 5: System.out.println("🔒 Exiting Bunker System..."); break;
                default: System.out.println("❌ Invalid choice.");
            }

        } while (choice != 5);
    }

    // ================================
    // FEATURE 1: REGISTER OCCUPANT
    // ================================
    static void register(Scanner sc) {

        System.out.println("\n--- BUNKER OCCUPANT REGISTRATION ---");

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Age: ");
        int age;
        try {
            age = sc.nextInt(); sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("❌ Invalid age.");
            return;
        }

        System.out.print("Role (Security/Medic/Engineer/Logistics/etc): ");
        String role = sc.nextLine();

        System.out.print("Contact Number: ");
        String contact = sc.nextLine();

        // INHERITANCE
        Occupant o = new Occupant(nextId++, name, age, role, contact);

        persons.add(o); // POLYMORPHISM

        System.out.println("✅ Occupant successfully registered into the bunker!");
    }

    // ================================
    // FEATURE 2: VIEW PROFILE
    // ================================
    static void viewProfile(Scanner sc) {

        System.out.print("\nEnter Occupant ID: ");
        int id = sc.nextInt();

        for (Person p : persons) {
            if (p.getId() == id) {
                display(p);
                return;
            }
        }

        System.out.println("❌ Occupant not found.");
    }

    // ================================
    // FEATURE 2: EDIT PROFILE
    // ================================
    static void editProfile(Scanner sc) {

        System.out.print("\nEnter Occupant ID: ");
        int id = sc.nextInt(); sc.nextLine();

        for (Person p : persons) {
            if (p.getId() == id) {

                System.out.print("New Contact Number: ");
                String contact = sc.nextLine();
                if (!contact.isEmpty()) {
                    p.setContact(contact);
                }

                System.out.println("✅ Profile updated successfully!");
                return;
            }
        }

        System.out.println("❌ Occupant not found.");
    }

    // ================================
    // VIEW ALL OCCUPANTS
    // ================================
    static void viewAll() {

        if (persons.isEmpty()) {
            System.out.println("⚠ No occupants in bunker.");
            return;
        }

        for (Person p : persons) {
            display(p);
        }
    }

    // ================================
    // DISPLAY (POLYMORPHISM)
    // ================================
    static void display(Person p) {
        System.out.println("\n-------------------------------");
        System.out.println("Occupant ID : " + p.getId());
        System.out.println("Name        : " + p.getName());
        System.out.println("Age         : " + p.getAge());
        System.out.println("Role        : " + p.getRole());
        System.out.println("Contact     : " + p.getContact());
        System.out.println("Status      : " + p.getDescription()); // OVERRIDING
        System.out.println("-------------------------------");
    }

    // ============================================
    // SUPERCLASS (ABSTRACTION)
    // ============================================
    static abstract class Person {

        protected int id;
        protected String name;
        protected int age;
        protected String role;
        protected String contact;

        public Person(int id, String name, int age, String role, String contact) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.role = role;
            this.contact = contact;
        }

        public abstract String getDescription();

        public int getId() { return id; }
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getRole() { return role; }
        public String getContact() { return contact; }

        public void setContact(String contact) { this.contact = contact; }
    }

    // ============================================
    // SUBCLASS (INHERITANCE + OVERRIDING)
    // ============================================
    static class Occupant extends Person {

        private String status;

        public Occupant(int id, String name, int age, String role, String contact) {
            super(id, name, age, role, contact);
            this.status = "Active in Bunker";
        }

        @Override
        public String getDescription() {
            return status;
        }
    }
}