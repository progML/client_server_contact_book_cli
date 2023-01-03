package se.ifmo.refactoring.contacts.client;

import se.ifmo.refactoring.contacts.api.Contact;
import se.ifmo.refactoring.contacts.api.ContactFilter;
import se.ifmo.refactoring.contacts.api.ContactService;

import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final PrintWriter writer;
    private final ContactService contactService;

    public MainMenu(
            final Scanner scanner, final PrintWriter writer, final ContactService contactService) {
        this.scanner = scanner;
        this.writer = writer;
        this.contactService = contactService;
    }

    public void init() {
        printMenu(true);
        while (true) {
            final MenuCommand menuCommand = readMenuCommand();
            switch (menuCommand) {
                case VIEW_ALL:
                    final List<Contact> allContacts = contactService.getAllContacts(new ContactFilter());
                    if (allContacts.isEmpty()) {
                        writer.println("There is no any contact in your book. Try to create one!");
                    } else {
                        writer.printf("All contacts (%d):%n", allContacts.size());
                        printContactsInfo(allContacts);
                    }
                    break;

                case SEARCH:
                    final ContactFilter contactFilter = readContactFilter();
                    writer.println("Searching...");
                    final List<Contact> foundContacts = contactService.getAllContacts(contactFilter);
                    if (foundContacts.isEmpty()) {
                        writer.println("There are no contact with such request parameters!");
                    } else {
                        writer.printf("Results (%d):%n", foundContacts.size());
                        printContactsInfo(foundContacts);
                    }
                    break;

                case NEW:
                    writer.println("New contact");
                    final Contact contact = readNewContact();
                    contactService.saveContact(contact);
                    writer.println("New contact successfully created!");
                    break;

                case EXIT:
                    writer.println("Goodbye!");
                    return;
                default:
                    writer.println("Unknown state for command " + menuCommand);
            }
            printMenu(false);
        }
    }

    private void printMenu(final boolean isNew) {
        if (isNew) {
            writer.println("Enter the number of action and press [Enter]. Then follow instructions.\n");
        }
        writer.println("Menu:");
        writer.println("1. View all contacts");
        writer.println("2. Search");
        writer.println("3. New contact");
        writer.println("4. Exit");
    }

    private void printSearchMenu() {
        writer.println("Search by:");
        writer.println("1. Name");
        writer.println("2. Surname");
        writer.println("3. Name and Surname");
        writer.println("4. Phone");
        writer.println("5. Email");
    }

    private MenuCommand readMenuCommand() {
        try {
            final int input = scanner.nextInt();
            scanner.nextLine();
            return MenuCommand.fromInt(input);
        } catch (final InputMismatchException | IllegalArgumentException e) {
            printWrongInputNote();
        }
        return readMenuCommand();
    }

    private SearchCommand readSearchCommand() {
        try {
            final int input = scanner.nextInt();
            scanner.nextLine();
            return SearchCommand.fromInt(input);
        } catch (final InputMismatchException | IllegalArgumentException e) {
            printWrongInputNote();
        }
        return readSearchCommand();
    }

    private Contact readNewContact() {
        final Contact contact = new Contact();
        writer.println("Name: ");
        contact.setName(readLine());
        writer.println("Surname: ");
        contact.setSurname(readLine());
        writer.println("Phone: ");
        contact.setPhoneNumber(readLine());
        writer.println("Email: ");
        contact.setEmail(readLine());

        return contact;
    }

    private ContactFilter readContactFilter() {
        printSearchMenu();
        final SearchCommand searchCommand = readSearchCommand();
        final ContactFilter contactFilter = new ContactFilter();
        writer.println("Request: ");
        final String input = readLine();
        switch (searchCommand) {
            case NAME:
                contactFilter.setName(input);
                return contactFilter;
            case SURNAME:
                contactFilter.setSurname(input);
                return contactFilter;
            case NAME_AND_SURNAME:
                contactFilter.setName(input);
                contactFilter.setSurname(input);
                return contactFilter;
            case PHONE:
                contactFilter.setPhoneNumber(input);
                return contactFilter;
            case EMAIL:
                contactFilter.setEmail(input);
                return contactFilter;
            case ALL_FIELDS:
                contactFilter.setName(input);
                contactFilter.setSurname(input);
                contactFilter.setPhoneNumber(input);
                contactFilter.setEmail(input);
                return contactFilter;
            default:
                throw new IllegalArgumentException("Unknown state for command: " + searchCommand);
        }
    }

    private String readLine() {
        try {
            return scanner.next();
        } catch (final NoSuchElementException e) {
            printWrongInputNote();
        }
        return readLine();
    }

    private void printWrongInputNote() {
        writer.println("You typed something wrong. Try one more time!");
    }

    private void printContactsInfo(final List<Contact> contacts) {
        for (int i = 0; i < contacts.size(); i++) {
            writer.printf("#%d ", (i + 1));
            printContactInfo(contacts.get(i));
        }
    }

    private void printContactInfo(final Contact contact) {
        writer.println("Name: " + contact.getName());
        writer.println("Surname: " + contact.getSurname());
        writer.println("Phone: " + contact.getPhoneNumber());
        writer.println("E-mail: " + contact.getEmail());
    }

    private enum MenuCommand {
        VIEW_ALL,
        SEARCH,
        NEW,
        EXIT;

        public static MenuCommand fromInt(final int number) {
            switch (number) {
                case 1:
                    return VIEW_ALL;
                case 2:
                    return SEARCH;
                case 3:
                    return NEW;
                case 4:
                    return EXIT;
                default:
                    throw new IllegalArgumentException(
                            "There is no any MenuCommand mapped to number " + number);
            }
        }
    }

    private enum SearchCommand {
        NAME,
        SURNAME,
        NAME_AND_SURNAME,
        PHONE,
        EMAIL,
        ALL_FIELDS;

        public static SearchCommand fromInt(final int number) {
            switch (number) {
                case 1:
                    return NAME;
                case 2:
                    return SURNAME;
                case 3:
                    return NAME_AND_SURNAME;
                case 4:
                    return PHONE;
                case 5:
                    return EMAIL;
                default:
                    throw new IllegalArgumentException(
                            "There is no any MenuCommand mapped to number " + number);
            }
        }
    }
}
