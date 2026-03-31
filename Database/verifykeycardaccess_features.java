package Database;

import Models.Keycard;
import Models.Occupant;
import Features.VerifyKeyCardAccess;
import Repositories.KeycardRepository;
import Repositories.OccupantRepo;
import java.util.List;
import java.util.Scanner;

/**
 * VerifyKeycardAccess_Features - Database layer for keycard access verification
 * Demonstrates the keycard access system with repository data
 */
public class verifykeycardaccess_features {

    /**
     * Display all keycards in the system
     */
    public static void displayAllKeycards() {
        System.out.println("\n===== ALL KEYCARDS IN SYSTEM =====");
        System.out.println(String.format("%-3s | %-15s | %-10s | %-12s | %-8s | %-12s",
                "ID", "Card Number", "Level", "Occupant ID", "Active", "Issued"));
        System.out.println("--------|---|--------|----------|---------|----------");
        
        List<Keycard> allCards = KeycardRepository.getAllKeycards();
        for (Keycard card : allCards) {
            System.out.println(String.format("%-3d | %-15s | %-10s | %-12d | %-8s | %-12s",
                    card.getId(),
                    card.getCardNumber(),
                    card.getAccessLevelName(),
                    card.getOccupantId(),
                    card.isActive() ? "Yes" : "No",
                    card.getIssueDate()));
        }
        System.out.println("===================================\n");
    }

    /**
     * Count keycards by access level
     */
    public static void displayAccessLevelSummary() {
        System.out.println("\n===== ACCESS LEVEL SUMMARY =====");
        
        int guestCount = KeycardRepository.getKeycardsByAccessLevel(1).size();
        int standardCount = KeycardRepository.getKeycardsByAccessLevel(2).size();
        int staffCount = KeycardRepository.getKeycardsByAccessLevel(3).size();
        int adminCount = KeycardRepository.getKeycardsByAccessLevel(4).size();
        
        System.out.println("Guest (Level 1)     : " + guestCount);
        System.out.println("Standard (Level 2)  : " + standardCount);
        System.out.println("Staff (Level 3)     : " + staffCount);
        System.out.println("Admin (Level 4)     : " + adminCount);
        System.out.println("Active Keycards     : " + KeycardRepository.getAllActiveKeycards().size() + " / " + KeycardRepository.getAllKeycards().size());
        System.out.println("================================\n");
    }

    /**
     * Test keycard access to a specific room
     */
    public static void testAccessToRoom(String cardNumber, int requiredLevel, String roomName) {
        System.out.println("\n--- Testing Access to: " + roomName + " ---");
        Keycard card = KeycardRepository.getKeycardByNumber(cardNumber);
        
        if (card == null) {
            System.out.println("❌ Keycard not found: " + cardNumber);
            return;
        }
        
        System.out.println("Cardholder ID: " + card.getOccupantId() + " | Card: " + cardNumber);
        VerifyKeyCardAccess.verifyAccess(card, requiredLevel);
    }

    /**
     * Display detailed summary for a specific keycard
     */
    public static void displayKeycardDetails(int keycardId) {
        Keycard card = KeycardRepository.getKeycardById(keycardId);
        
        if (card != null) {
            VerifyKeyCardAccess.displayAccessSummary(card);
        } else {
            System.out.println("❌ Keycard ID not found: " + keycardId);
        }
    }

    /**
     * Main login system - prompts user for credentials
     */
    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║   BUNKER SYSTEM - KEYCARD ACCESS VERIFICATION          ║");
        System.out.println("║   LOGIN SYSTEM                                          ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n===== LOGIN =====");
            
            // Get user input
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine().trim();
            
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine().trim();
            
            System.out.print("Enter Occupant ID: ");
            int occupantId;
            try {
                occupantId = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid ID format. Please enter a number.");
                continue;
            }
            
            // Verify occupant
            if (!OccupantRepo.verifyOccupant(firstName, lastName, occupantId)) {
                System.out.println("❌ Login failed! Credentials do not match our records.");
                System.out.println("Please try again or check the occupant list.\n");
                continue;
            }
            
            // Login successful
            Occupant occupant = OccupantRepo.getOccupantById(occupantId);
            System.out.println("\n✅ Login successful! Welcome, " + occupant.getFullName() + "!");
            
            // Display occupant info
            displayOccupantInfo(occupant);
            
            // Display keycard info
            displayOccupantKeycards(occupant.getId());
            
            // Access test menu
            accessTestMenu(scanner, occupant.getId());
            
            // Logout option
            System.out.print("\nLogout? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                System.out.println("You have been logged out. Goodbye!\n");
                break;
            }
        }
        
        scanner.close();
    }

    /**
     * Display occupant information
     */
    public static void displayOccupantInfo(Occupant occupant) {
        System.out.println("\n===== YOUR INFORMATION =====");
        System.out.println("Name    : " + occupant.getFullName());
        System.out.println("ID      : " + occupant.getId());
        System.out.println("Position: " + occupant.getPosition());
        System.out.println("Room    : " + occupant.getRoomNumber());
        System.out.println("=============================\n");
    }

    /**
     * Display keycards assigned to occupant
     */
    public static void displayOccupantKeycards(int occupantId) {
        System.out.println("===== YOUR KEYCARDS =====");
        List<Keycard> occupantCards = KeycardRepository.getKeycardsByOccupantId(occupantId);
        
        if (occupantCards.isEmpty()) {
            System.out.println("❌ No keycards assigned to this account.");
            return;
        }
        
        System.out.println(String.format("%-3s | %-15s | %-10s | %-8s",
                "ID", "Card Number", "Level", "Active"));
        System.out.println("---------|---|---------|---------|");
        
        for (Keycard card : occupantCards) {
            System.out.println(String.format("%-3d | %-15s | %-10s | %-8s",
                    card.getId(),
                    card.getCardNumber(),
                    card.getAccessLevelName(),
                    card.isActive() ? "Yes" : "No"));
        }
        System.out.println("========================\n");
    }

    /**
     * Access test menu for logged-in occupant
     */
    public static void accessTestMenu(Scanner scanner, int occupantId) {
        List<Keycard> occupantCards = KeycardRepository.getKeycardsByOccupantId(occupantId);
        
        if (occupantCards.isEmpty()) {
            return;
        }
        
        // Get first active keycard
        Keycard userCard = null;
        for (Keycard card : occupantCards) {
            if (card.isActive()) {
                userCard = card;
                break;
            }
        }
        
        if (userCard == null) {
            System.out.println("❌ No active keycards available for access testing.");
            return;
        }
        
        System.out.println("\n===== ACCESS TEST MENU =====");
        System.out.println("1. Common Areas (Level 1)");
        System.out.println("2. Residential Areas (Level 2)");
        System.out.println("3. Restricted Lab (Level 3)");
        System.out.println("4. Server Room / Command Center (Level 4)");
        System.out.println("5. Skip access testing");
        
        System.out.print("Select area to test (1-5): ");
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                testAccessToRoom(userCard.getCardNumber(), 1, "Common Areas");
                break;
            case "2":
                testAccessToRoom(userCard.getCardNumber(), 2, "Residential Areas");
                break;
            case "3":
                testAccessToRoom(userCard.getCardNumber(), 3, "Restricted Lab");
                break;
            case "4":
                testAccessToRoom(userCard.getCardNumber(), 4, "Server Room / Command Center");
                break;
            case "5":
                System.out.println("Skipping access testing...");
                break;
            default:
                System.out.println("❌ Invalid choice.");
        }
    }
}
