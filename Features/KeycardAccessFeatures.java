package Features;

import java.util.Scanner;
import java.util.List;
import Models.Occupant;
import Models.Keycard;
import Repositories.OccupantRepo;
import Repositories.KeycardRepo;

/**
 * KeycardAccessFeatures - Main login system and feature access for the bunker system
 * Handles user authentication and routes to appropriate features based on access level
 */
public class KeycardAccessFeatures {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main entry point - starts the login system
     */
    public static void main(String[] args) {
        displayWelcome();
        
        while (true) {
            if (login()) {
                // Login successful, logout handled from feature menu
            } else {
                System.out.print("\nTry again? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if (!response.equals("yes") && !response.equals("y")) {
                    break;
                }
            }
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║         Thank you for using the Bunker System             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        scanner.close();
    }

    /**
     * Display welcome banner
     */
    private static void displayWelcome() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          BUNKER SYSTEM - KEYCARD ACCESS CONTROL            ║");
        System.out.println("║                     LOGIN SYSTEM                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }

    /**
     * Login system - prompts for credentials and verifies against database
     * @return true if login successful, false otherwise
     */
    private static boolean login() {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Enter Occupant ID: ");
        String idInput = scanner.nextLine().trim();
        
        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || idInput.isEmpty()) {
            System.out.println("\n❌ ERROR: All fields are required.");
            return false;
        }
        
        int occupantId;
        try {
            occupantId = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("\n❌ ERROR: Invalid Occupant ID. Must be a number.");
            return false;
        }
        
        // Verify occupant in database
        if (!OccupantRepo.verifyOccupant(firstName, lastName, occupantId)) {
            System.out.println("\n❌ LOGIN FAILED: Credentials do not match any registered occupant.");
            return false;
        }
        
        // Get occupant details
        Occupant user = OccupantRepo.getOccupantById(occupantId);
        if (user == null) {
            System.out.println("\n❌ LOGIN FAILED: Occupant not found.");
            return false;
        }
        
        // Get user's keycard
        List<Keycard> keycards = KeycardRepo.getKeycardsByOccupantId(occupantId);
        if (keycards.isEmpty()) {
            System.out.println("\n❌ LOGIN FAILED: No keycard assigned to this occupant.");
            return false;
        }
        
        // Find first active keycard
        Keycard userCard = null;
        for (Keycard card : keycards) {
            if (card.isActive()) {
                userCard = card;
                break;
            }
        }
        
        if (userCard == null) {
            System.out.println("\n❌ LOGIN FAILED: No active keycard found.");
            return false;
        }
        
        // Login successful
        System.out.println("\n✅ Login successful! Welcome, " + user.getFullName() + "!");
        
        // Display verification
        displayVerification(user, userCard);
        
        // Access feature menu
        accessFeatures(user, userCard);
        
        return true;
    }

    /**
     * Display verified user information
     */
    public static void displayVerification(Occupant user, Keycard card) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   USER VERIFICATION                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n✓ Verification Details:");
        System.out.println("  First Name    : " + user.getFirstName());
        System.out.println("  Last Name     : " + user.getLastName());
        System.out.println("  Occupant ID   : " + user.getOccupantId());
        System.out.println("\n✓ Keycard Details:");
        System.out.println("  Keycard ID    : " + card.getKeycardId());
        System.out.println("  Keycard Code  : " + card.getKeycardCode());
        System.out.println("  Status        : " + (card.isActive() ? "ACTIVE" : "INACTIVE"));
        System.out.println("  Issued At     : " + card.getIssuedAt());
    }



    /**
     * Display available features and handle navigation
     */
    public static void accessFeatures(Occupant user, Keycard card) {
        
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                    SYSTEM FEATURES MENU                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            
            System.out.println("\nAvailable Features:\n");
            
            System.out.println("[1] View Keycard Access Information");
            System.out.println("    └─ View detailed keycard information");
            
            System.out.println("[2] Manage Occupant Profiles");
            System.out.println("    └─ View and manage occupant information");
            
            System.out.println("\n[0] Logout");
            System.out.print("\nSelect feature (0-2): ");
            
            String choice = scanner.nextLine().trim();
            
            if (handleFeatureChoice(choice, user, card)) {
                break; // Return to login
            }
        }
    }



    /**
     * Handle feature selection
     * @param choice User's menu choice
     * @param user The logged-in occupant
     * @param card The occupant's keycard
     * @return true if user wants to logout and return to login, false otherwise
     */
    private static boolean handleFeatureChoice(String choice, Occupant user, Keycard card) {
        switch (choice) {
            case "1":
                displayKeycardAccessInfo(user, card);
                return false;
                
            case "2":
                ManageOccupantProfiles.main(new String[]{});
                return false;
                
            case "0":
                return true; // Logout
                
            default:
                System.out.println("❌ Invalid choice. Please try again.\n");
                return false;
        }
    }

    /**
     * Display keycard access information
     */
    private static void displayKeycardAccessInfo(Occupant user, Keycard card) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           KEYCARD ACCESS INFORMATION                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        System.out.println("\nYour Keycard Details:");
        System.out.println("  Keycard ID    : " + card.getKeycardId());
        System.out.println("  Keycard Code  : " + card.getKeycardCode());
        System.out.println("  Holder        : " + user.getFullName());
        System.out.println("  Occupant ID   : " + user.getOccupantId());
        System.out.println("  Status        : " + (card.isActive() ? "ACTIVE" : "INACTIVE"));
        System.out.println("  Issued At     : " + card.getIssuedAt());
        System.out.println();
        
        System.out.print("Press Enter to return to menu...");
        scanner.nextLine();
    }






}
