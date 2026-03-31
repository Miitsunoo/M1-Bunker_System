package Features;

import Models.Occupant;
import Models.Keycard;
import Repositories.OccupantRepo;
import Repositories.KeycardRepository;
import java.util.Scanner;
import java.util.List;

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
        List<Keycard> keycards = KeycardRepository.getKeycardsByOccupantId(occupantId);
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
        accessFeatures(user, userCard, scanner);
        
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
        System.out.println("  Occupant ID   : " + user.getId());
        System.out.println("  Position      : " + user.getPosition());
        System.out.println("  Room Number   : " + user.getRoomNumber());
        System.out.println("\n✓ Keycard Details:");
        System.out.println("  Card Number   : " + card.getCardNumber());
        System.out.println("  Access Level  : " + card.getAccessLevelName() + " (Level " + card.getAccessLevel() + ")");
        System.out.println("  Status        : ACTIVE");
        System.out.println("  Issued Date   : " + card.getIssueDate());
        System.out.println("\n✓ Access Level Details:");
        displayAccessSummary(card);
    }

    /**
     * Display access permissions based on keycard level
     */
    public static void displayAccessSummary(Keycard card) {
        System.out.println("\n  Access Permissions for " + card.getAccessLevelName() + ":");
        System.out.println("    ✓ Common Areas (Cafeteria, Library, Recreation)");
        
        if (card.getAccessLevel() >= 2) {
            System.out.println("    ✓ Residential Areas (Sleeping Quarters, Personal Storage)");
        }
        if (card.getAccessLevel() >= 3) {
            System.out.println("    ✓ Restricted Areas (Medical Bay, Armory, Lab)");
        }
        if (card.getAccessLevel() >= 4) {
            System.out.println("    ✓ Server Room / Command Center (Full Control)");
        }
    }

    /**
     * Display available features based on access level and handle navigation
     */
    public static void accessFeatures(Occupant user, Keycard card, Scanner input) {
        scanner = input;
        
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                    SYSTEM FEATURES MENU                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            
            System.out.println("\nAvailable Features Based on Your Access Level:\n");
            
            // Level 1 - Common Areas (All users)
            System.out.println("[1] View Keycard Access Information");
            System.out.println("    └─ View detailed keycard and access permissions");
            
            // Level 2 - Residential Areas
            if (card.getAccessLevel() >= 2) {
                System.out.println("\n[2] Manage Room Reservations");
                System.out.println("    └─ View and book residential areas");
            }
            
            // Level 3 - Restricted Areas
            if (card.getAccessLevel() >= 3) {
                System.out.println("\n[3] Assign Work Duties");
                System.out.println("    └─ Manage staff work assignments (Staff access)");
                System.out.println("\n[4] Manage Occupant Profiles");
                System.out.println("    └─ View and update occupant information (Staff access)");
            }
            
            // Level 4 - Server Room / Command Center
            if (card.getAccessLevel() >= 4) {
                System.out.println("\n[5] System Administration");
                System.out.println("    └─ Full system control and monitoring (Admin only)");
            }
            
            System.out.println("\n[0] Logout");
            System.out.print("\nSelect feature (0-" + getMaxFeatureOption(card) + "): ");
            
            String choice = scanner.nextLine().trim();
            
            if (handleFeatureChoice(choice, user, card)) {
                break; // Return to login
            }
        }
    }

    /**
     * Get maximum feature option based on access level
     */
    private static int getMaxFeatureOption(Keycard card) {
        if (card.getAccessLevel() >= 4) return 5;
        if (card.getAccessLevel() >= 3) return 4;
        if (card.getAccessLevel() >= 2) return 2;
        return 1;
    }

    /**
     * Handle feature selection based on access level
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
                if (card.getAccessLevel() >= 2) {
                    displayFeature("MANAGE ROOM RESERVATIONS", 
                        "This feature allows you to view available rooms and make reservations.\n" +
                        "Access Level Required: Level 2 (Residential)\n" +
                        "Feature coming soon...");
                } else {
                    accessDenied(card);
                }
                return false;
                
            case "3":
                if (card.getAccessLevel() >= 3) {
                    displayFeature("ASSIGN WORK DUTIES",
                        "This feature allows you to manage and assign work duties to staff members.\n" +
                        "Access Level Required: Level 3 (Staff)\n" +
                        "Feature coming soon...");
                } else {
                    accessDenied(card);
                }
                return false;
                
            case "4":
                if (card.getAccessLevel() >= 3) {
                    displayFeature("MANAGE OCCUPANT PROFILES",
                        "This feature allows you to view and manage occupant information.\n" +
                        "Access Level Required: Level 3 (Staff)\n" +
                        "Feature coming soon...");
                } else {
                    accessDenied(card);
                }
                return false;
                
            case "5":
                if (card.getAccessLevel() >= 4) {
                    displayFeature("SYSTEM ADMINISTRATION",
                        "This feature provides full system control and monitoring.\n" +
                        "Access Level Required: Level 4 (Admin)\n" +
                        "Feature coming soon...");
                } else {
                    accessDenied(card);
                }
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
        System.out.println("  Card Number   : " + card.getCardNumber());
        System.out.println("  Holder        : " + user.getFullName());
        System.out.println("  Occupant ID   : " + user.getId());
        System.out.println("  Access Level  : " + card.getAccessLevelName() + " (Level " + card.getAccessLevel() + ")");
        System.out.println("  Status        : " + (card.isActive() ? "ACTIVE" : "INACTIVE"));
        System.out.println("  Issued Date   : " + card.getIssueDate());
        System.out.println("\nYour Access Permissions:");
        System.out.println("  ✓ Common Areas (Cafeteria, Library, Recreation)");
        if (card.getAccessLevel() >= 2) {
            System.out.println("  ✓ Residential Areas (Sleeping Quarters, Personal Storage)");
        }
        if (card.getAccessLevel() >= 3) {
            System.out.println("  ✓ Restricted Areas (Medical Bay, Armory, Lab)");
        }
        if (card.getAccessLevel() >= 4) {
            System.out.println("  ✓ Server Room / Command Center (Full Control)");
        }
        System.out.println();
        
        System.out.print("Press Enter to return to menu...");
        scanner.nextLine();
    }

    /**
     * Display a feature placeholder
     */
    private static void displayFeature(String title, String description) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  " + padCenter(title, 56) + "  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n" + description + "\n");
        
        System.out.print("Press Enter to return to menu...");
        scanner.nextLine();
    }

    /**
     * Display access denied message
     */
    private static void accessDenied(Keycard card) {
        System.out.println("\n❌ ACCESS DENIED");
        System.out.println("Your access level (" + card.getAccessLevelName() + 
                          ") does not permit this action.");
        System.out.println("Please contact system administrator for access upgrade.\n");
        System.out.print("Press Enter to return to menu...");
        scanner.nextLine();
    }

    /**
     * Helper method to center text .
     */
    private static String padCenter(String text, int width) {
        if (text.length() >= width) return text;
        int totalPad = width - text.length();
        int leftPad = totalPad / 2;
        int rightPad = totalPad - leftPad;
        return " ".repeat(leftPad) + text + " ".repeat(rightPad);
    }
}
