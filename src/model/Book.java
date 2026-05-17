package model;

public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final int publishedYear;
    private boolean borrowed;

    public Book(String isbn, String title, String author, int publishedYear, boolean borrowed) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN is required.");
        }

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.borrowed = borrowed;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void borrow() {
        if (borrowed) {
            throw new IllegalStateException("Book is already borrowed.");
        }
        borrowed = true;
    }

    public void returnBook() {
        if (!borrowed) {
            throw new IllegalStateException("Book is not currently borrowed.");
        }
        borrowed = false;
    }

    public String toCsv() {
        return String.join(",",
                escape(isbn),
                escape(title),
                escape(author),
                String.valueOf(publishedYear),
                String.valueOf(borrowed)
        );
    }

    public static Book fromCsv(String line) {
        String[] parts = line.split(",", -1);
        return new Book(
                unescape(parts[0]),
                unescape(parts[1]),
                unescape(parts[2]),
                Integer.parseInt(parts[3]),
                Boolean.parseBoolean(parts[4])
        );
    }

    private static String escape(String value) {
        return value.replace(",", " ");
    }

    private static String unescape(String value) {
        return value;
    }

    @Override
    public String toString() {
        return isbn + " | " + title + " | " + author + " | " + publishedYear + " | " +
                (borrowed ? "Borrowed" : "Available");
    }
}
