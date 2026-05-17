package service;

import model.Book;
import model.Member;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class LibraryService {
    private final Map<String, Book> books = new LinkedHashMap<>();
    private final Map<Integer, Member> members = new LinkedHashMap<>();
    private final Map<String, Integer> loans = new LinkedHashMap<>();

    private final Path booksFile = Path.of("books.csv");
    private final Path membersFile = Path.of("members.csv");
    private final Path loansFile = Path.of("loans.csv");

    public LibraryService() {
        load();
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Book ISBN already exists.");
        }
        books.put(book.getIsbn(), book);
        save();
    }

    public void addMember(Member member) {
        if (members.containsKey(member.getId())) {
            throw new IllegalArgumentException("Member ID already exists.");
        }
        members.put(member.getId(), member);
        save();
    }

    public void borrowBook(String isbn, int memberId) {
        Book book = requireBook(isbn);
        requireMember(memberId);

        if (loans.containsKey(isbn)) {
            throw new IllegalStateException("Book already loaned.");
        }

        book.borrow();
        loans.put(isbn, memberId);
        save();
    }

    public void returnBook(String isbn) {
        Book book = requireBook(isbn);

        if (!loans.containsKey(isbn)) {
            throw new IllegalStateException("Book is not loaned.");
        }

        book.returnBook();
        loans.remove(isbn);
        save();
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        books.values().forEach(System.out::println);
    }

    public void listMembers() {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        members.values().forEach(System.out::println);
    }

    public void listLoans() {
        if (loans.isEmpty()) {
            System.out.println("No active loans.");
            return;
        }

        for (Map.Entry<String, Integer> entry : loans.entrySet()) {
            System.out.println("Book ISBN: " + entry.getKey() + " -> Member ID: " + entry.getValue());
        }
    }

    public void searchBooks(String keyword) {
        String normalized = keyword.toLowerCase();
        boolean found = false;

        for (Book book : books.values()) {
            if (book.toString().toLowerCase().contains(normalized)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching books found.");
        }
    }

    private Book requireBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }
        return book;
    }

    private Member requireMember(int id) {
        Member member = members.get(id);
        if (member == null) {
            throw new IllegalArgumentException("Member not found.");
        }
        return member;
    }

    private void load() {
        loadBooks();
        loadMembers();
        loadLoans();
    }

    private void loadBooks() {
        if (!Files.exists(booksFile)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(booksFile)) {
                if (!line.isBlank()) {
                    Book book = Book.fromCsv(line);
                    books.put(book.getIsbn(), book);
                }
            }
        } catch (IOException error) {
            System.out.println("Could not load books: " + error.getMessage());
        }
    }

    private void loadMembers() {
        if (!Files.exists(membersFile)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(membersFile)) {
                if (!line.isBlank()) {
                    Member member = Member.fromCsv(line);
                    members.put(member.getId(), member);
                }
            }
        } catch (IOException error) {
            System.out.println("Could not load members: " + error.getMessage());
        }
    }

    private void loadLoans() {
        if (!Files.exists(loansFile)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(loansFile)) {
                if (!line.isBlank()) {
                    String[] parts = line.split(",", -1);
                    loans.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException error) {
            System.out.println("Could not load loans: " + error.getMessage());
        }
    }

    private void save() {
        saveBooks();
        saveMembers();
        saveLoans();
    }

    private void saveBooks() {
        try {
            List<String> lines = new ArrayList<>();
            for (Book book : books.values()) {
                lines.add(book.toCsv());
            }
            Files.write(booksFile, lines);
        } catch (IOException error) {
            System.out.println("Could not save books: " + error.getMessage());
        }
    }

    private void saveMembers() {
        try {
            List<String> lines = new ArrayList<>();
            for (Member member : members.values()) {
                lines.add(member.toCsv());
            }
            Files.write(membersFile, lines);
        } catch (IOException error) {
            System.out.println("Could not save members: " + error.getMessage());
        }
    }

    private void saveLoans() {
        try {
            List<String> lines = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : loans.entrySet()) {
                lines.add(entry.getKey() + "," + entry.getValue());
            }
            Files.write(loansFile, lines);
        } catch (IOException error) {
            System.out.println("Could not save loans: " + error.getMessage());
        }
    }
}
