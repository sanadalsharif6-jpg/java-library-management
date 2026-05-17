import model.Book;
import model.Member;
import service.LibraryService;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryService library = new LibraryService();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addBook();
                    case "2" -> addMember();
                    case "3" -> borrowBook();
                    case "4" -> returnBook();
                    case "5" -> library.listBooks();
                    case "6" -> library.listMembers();
                    case "7" -> library.listLoans();
                    case "8" -> searchBooks();
                    case "9" -> {
                        System.out.println("Goodbye.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (RuntimeException error) {
                System.out.println("Error: " + error.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Java Library Management Pro ===");
        System.out.println("1. Add book");
        System.out.println("2. Add member");
        System.out.println("3. Borrow book");
        System.out.println("4. Return book");
        System.out.println("5. List books");
        System.out.println("6. List members");
        System.out.println("7. List active loans");
        System.out.println("8. Search books");
        System.out.println("9. Exit");
        System.out.print("Choose: ");
    }

    private static void addBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Published year: ");
        int year = Integer.parseInt(scanner.nextLine());

        library.addBook(new Book(isbn, title, author, year, false));
        System.out.println("Book added.");
    }

    private static void addMember() {
        System.out.print("Member ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        library.addMember(new Member(id, name, email));
        System.out.println("Member added.");
    }

    private static void borrowBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Member ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        library.borrowBook(isbn, memberId);
        System.out.println("Book borrowed.");
    }

    private static void returnBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        library.returnBook(isbn);
        System.out.println("Book returned.");
    }

    private static void searchBooks() {
        System.out.print("Search keyword: ");
        library.searchBooks(scanner.nextLine());
    }
}
