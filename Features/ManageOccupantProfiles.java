package Features;

import java.util.Scanner;
import java.util.List;
import Models.Occupant;
import Repositories.OccupantRepo;

public class ManageOccupantProfiles {

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

        System.out.print("First Name: ");
        String firstName = sc.nextLine().trim();

        System.out.print("Last Name: ");
        String lastName = sc.nextLine().trim();

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();

        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            System.out.println("❌ ERROR: First name, last name, and email are required.");
            return;
        }

        // Create occupant in database
        if (OccupantRepo.createOccupant(firstName, lastName, email, phone)) {
            System.out.println("✅ Occupant successfully registered into the bunker!");
        } else {
            System.out.println("❌ ERROR: Failed to register occupant.");
        }
    }

    // ================================
    // FEATURE 2: VIEW PROFILE
    // ================================
    static void viewProfile(Scanner sc) {

        System.out.print("\nEnter Occupant ID: ");
        int id = sc.nextInt(); sc.nextLine();

        Occupant occupant = OccupantRepo.getOccupantById(id);
        if (occupant != null) {
            display(occupant);
        } else {
            System.out.println("❌ Occupant not found.");
        }
    }

    // ================================
    // FEATURE 3: EDIT PROFILE
    // ================================
    static void editProfile(Scanner sc) {

        System.out.print("\nEnter Occupant ID: ");
        int id = sc.nextInt(); sc.nextLine();

        Occupant occupant = OccupantRepo.getOccupantById(id);
        if (occupant == null) {
            System.out.println("❌ Occupant not found.");
            return;
        }

        System.out.println("Current details:");
        display(occupant);

        System.out.print("New First Name (leave empty to keep current): ");
        String firstName = sc.nextLine().trim();
        if (firstName.isEmpty()) firstName = occupant.getFirstName();

        System.out.print("New Last Name (leave empty to keep current): ");
        String lastName = sc.nextLine().trim();
        if (lastName.isEmpty()) lastName = occupant.getLastName();

        System.out.print("New Email (leave empty to keep current): ");
        String email = sc.nextLine().trim();
        if (email.isEmpty()) email = occupant.getEmail();

        System.out.print("New Phone (leave empty to keep current): ");
        String phone = sc.nextLine().trim();
        if (phone.isEmpty()) phone = occupant.getPhone();

        if (OccupantRepo.updateOccupant(id, firstName, lastName, email, phone)) {
            System.out.println("✅ Profile updated successfully!");
        } else {
            System.out.println("❌ ERROR: Failed to update profile.");
        }
    }

    // ================================
    // FEATURE 4: VIEW ALL OCCUPANTS
    // ================================
    static void viewAll() {

        List<Occupant> occupants = OccupantRepo.getAllOccupants();
        if (occupants.isEmpty()) {
            System.out.println("⚠ No occupants in bunker.");
            return;
        }

        for (Occupant occupant : occupants) {
            display(occupant);
        }
    }

    // ================================
    // DISPLAY
    // ================================
    static void display(Occupant occupant) {
        System.out.println("\n-------------------------------");
        System.out.println("Occupant ID : " + occupant.getOccupantId());
        System.out.println("Name        : " + occupant.getFirstName() + " " + occupant.getLastName());
        System.out.println("Email       : " + occupant.getEmail());
        System.out.println("Phone       : " + occupant.getPhone());
        System.out.println("Registered  : " + occupant.getRegisteredAt());
        System.out.println("-------------------------------");
    }
}