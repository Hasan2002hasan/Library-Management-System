package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class LibraryManagement {

    // Data models
    static class Book {
        String title;
        String author;

        Book(String title, String author) {
            this.title = title;
            this.author = author;
        }
    }

    static class Student {
        String id;
        String name;
        String borrowedBook;

        Student(String id, String name, String borrowedBook) {
            this.id = id;
            this.name = name;
            this.borrowedBook = borrowedBook;
        }
    }

    static class Staff {
        String id;
        String name;

        Staff(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static void main(String[] args) {

        // Book, Staff, and Student lists
        List<Book> books = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        List<Staff> staffMembers = new ArrayList<>();

        // Predefined books
        books.add(new Book("Astronomy", "Carl Sagan"));
        books.add(new Book("Science", "Stephen Hawking"));
        books.add(new Book("History", "Yuval Noah Harari"));
        books.add(new Book("Philosophy", "Plato"));
        books.add(new Book("Mathematics", "Isaac Newton"));

        // Main Application Window
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.setContentPane(mainPanel);

        // Header
        JLabel headerLabel = new JLabel("Library Management System", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 26));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(70, 130, 180));
        headerLabel.setForeground(Color.WHITE);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Dashboard - Main Navigation
        JTabbedPane tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Dashboard Tab
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        JButton booksButton = new JButton("Books Management");
        JButton studentButton = new JButton("Student Management");
        JButton staffButton = new JButton("Library Staff");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(booksButton);
        buttonPanel.add(studentButton);
        buttonPanel.add(staffButton);

        dashboardPanel.add(buttonPanel, BorderLayout.NORTH);
        tabbedPane.addTab("Dashboard", dashboardPanel);

        // Books Section
        JPanel booksPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> bookListModel = new DefaultListModel<>();
        JList<String> bookListView = new JList<>(bookListModel);
        JScrollPane bookScrollPane = new JScrollPane(bookListView);

        for (Book book : books) {
            bookListModel.addElement(book.title + " by " + book.author);
        }

        // Add/Delete Book Options
        bookListView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton addBookButton = new JButton("Add Book");
        JButton deleteBookButton = new JButton("Delete Book");

        addBookButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(frame, "Enter Book Title:");
            String author = JOptionPane.showInputDialog(frame, "Enter Author:");
            if (title != null && author != null) {
                books.add(new Book(title, author));
                bookListModel.addElement(title + " by " + author);
            }
        });

        deleteBookButton.addActionListener(e -> {
            int selectedIndex = bookListView.getSelectedIndex();
            if (selectedIndex != -1) {
                books.remove(selectedIndex);
                bookListModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a book to delete.");
            }
        });

        JPanel bookButtonPanel = new JPanel();
        bookButtonPanel.add(addBookButton);
        bookButtonPanel.add(deleteBookButton);

        booksPanel.add(bookScrollPane, BorderLayout.CENTER);
        booksPanel.add(bookButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Books Management", booksPanel);

        // Student Section
        JPanel studentPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> studentListModel = new DefaultListModel<>();
        JList<String> studentListView = new JList<>(studentListModel);
        JScrollPane studentScrollPane = new JScrollPane(studentListView);

        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");

        borrowBookButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Student ID:");
            String name = JOptionPane.showInputDialog(frame, "Enter Student Name:");
            String bookTitle = JOptionPane.showInputDialog(frame, "Enter Book Title to Borrow:");

            boolean bookAvailable = books.stream().anyMatch(b -> b.title.equalsIgnoreCase(bookTitle));
            if (bookAvailable) {
                students.add(new Student(id, name, bookTitle));
                studentListModel.addElement("ID: " + id + " | Name: " + name + " | Borrowed: " + bookTitle);
                JOptionPane.showMessageDialog(frame, "Book Borrowed Successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Book not available.");
            }
        });

        returnBookButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Student ID to return:");
            students.removeIf(student -> {
                if (student.id.equals(id)) {
                    studentListModel.removeElement("ID: " + student.id + " | Name: " + student.name + " | Borrowed: " + student.borrowedBook);
                    JOptionPane.showMessageDialog(frame, "Book returned successfully.");
                    return true;
                }
                return false;
            });
        });

        JPanel studentButtonPanel = new JPanel();
        studentButtonPanel.add(borrowBookButton);
        studentButtonPanel.add(returnBookButton);

        studentPanel.add(studentScrollPane, BorderLayout.CENTER);
        studentPanel.add(studentButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Student Management", studentPanel);

        // Staff Section
        JPanel staffPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> staffListModel = new DefaultListModel<>();
        JList<String> staffListView = new JList<>(staffListModel);
        JScrollPane staffScrollPane = new JScrollPane(staffListView);

        JButton addStaffButton = new JButton("Add Staff");
        JButton removeStaffButton = new JButton("Remove Staff");

        addStaffButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Staff ID:");
            String name = JOptionPane.showInputDialog(frame, "Enter Staff Name:");
            staffListModel.addElement("ID: " + id + " | Name: " + name);
        });

        removeStaffButton.addActionListener(e -> {
            int index = staffListView.getSelectedIndex();
            if (index != -1) {
                staffListModel.remove(index);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a staff member to remove.");
            }
        });

        JPanel staffButtonPanel = new JPanel();
        staffButtonPanel.add(addStaffButton);
        staffButtonPanel.add(removeStaffButton);

        staffPanel.add(staffScrollPane, BorderLayout.CENTER);
        staffPanel.add(staffButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Library Staff", staffPanel);

        frame.revalidate();
        frame.setVisible(true);
    }
}
