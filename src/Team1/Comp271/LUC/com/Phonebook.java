package Team1.Comp271.LUC.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Phonebook implements Iphonebook {

    public static void main(String[] args) {

    }

    private Map<String, Contact> contacts;
    private List<Contact> family;
    private List<Contact> friends;
    private List<Contact> colleagues;

    public Phonebook() {
        contacts = new HashMap<>();
        family = new ArrayList<>();
        friends = new ArrayList<>();
        colleagues = new ArrayList<>();
    }

    @Override
    public void processCommand(String line) throws FileNotFoundException {
        String command = line.split("\\s+")[0];
        switch (command) {
            case "save":
                saveToFile();
                break;
            case "load":
                loadFromFile();
                break;
            case "add":
                add(line);
                break;
            case "help":
                help();
                break;
            case "list":
                list(line);
                break;
            case "remove":
                remove(line);
                break;
            case "show":
                show(line);
                break;
            case "search":
                search(line);
                break;
            case "sortName":
                sortName();
                break;
            case "sortEmail":
                //sortEmail();
                break;
            default:
                System.out.println("Unknown command. Type help for help.");
                break;
        }
    }

    @Override
    public void saveToFile() throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter("phonebook.rtf")) {
            Iterator it = contacts.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Contact> pair = (Map.Entry) it.next();
                out.println(pair.getValue().toStringForFile());
            }
        }
        System.out.println("Phonebook saved to file");
    }

    @Override
    public void loadFromFile() throws FileNotFoundException {
        File file = new File("phonebook.rtf");
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            String[] c = input.nextLine().split(",");
            String phoneNumber = c[0];
            String name = c[1];
            String email = c[2];
            String ringtone = c[3];
            String contactType = c[4];
            createAndAddContact(phoneNumber, name, email, contactType, ringtone);
        }
        System.out.println("Phonebook loaded from file");
    }

    @Override
    public void add(String command) {
        String[] s = splitCommand(command);
        //expect properly formed command of 6 strings
        if (s.length != 6) {
            System.out.println("Incorrect command format. Command should be in format 'add PHONE_NUMBER NAME EMAIL RINGTONE CONTACT_TYPE(FAMILY, FRIENDS, COLLEAGUES)'");
            return;
        }
        String phoneNumber = s[1];
        String name = s[2];
        String email = s[3];
        String ringtone = s[4];
        try {
            String contactType = s[5];
            createAndAddContact(phoneNumber, name, email, contactType, ringtone);
            System.out.println("Contact added to phonebook");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid contact type. Expected contactTypes: friends, family or colleagues");
        }
    }

    private void createAndAddContact(String phoneNumber, String name, String email, String contactType, String ringtone) {
        Contact contact = new Contact(phoneNumber, name, email, ringtone, contactType);
        contacts.put(phoneNumber, contact);
        switch (contactType) {
            case "friends":
                friends.add(contact);
                break;
            case "family":
                family.add(contact);
                break;
            case "colleagues":
                colleagues.add(contact);
                break;
            default:
                break;
        }
    }

    @Override
    public void help() {
        System.out.println("================Help for phonebook========");
        System.out.println("Available methods are:");
        System.out.println("save - Saves phonebook entries to file rtf");
        System.out.println("load - Loads phonebook entries from file phonebook rtf into phonebook memory");
        System.out.println("add phone name email contactType(family, friends, colleagues) ringtone - Adds phone number to phonebook");
        System.out.println("help - Displays application help menu");
        System.out.println("list group - List all phonebook entries. Group is optional and can be family, friends, colleagues. If group is not provided entire phonebook is listed");
        System.out.println("remove phone - Removes entrie from phonebook");
        System.out.println("show phone - Prints provided phone entry");
        System.out.println("search name - Search for name");
        System.out.println("sortName - Sort by name");
        System.out.println("sortEmail - Sort by email");

    }

    @Override
    public void list(String command) {
        String[] s = splitCommand(command);
        if (s.length == 1) {
            System.out.println("Listing all phonebook contacts");
            Iterator it = contacts.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Contact> pair = (Map.Entry) it.next();
                System.out.println(pair.getValue());
            }
        } else {
            String group = s[1];
            switch (group) {
                case "friends":
                    printList(friends);
                    break;
                case "family":
                    printList(family);
                    break;
                case "colleagues":
                    printList(colleagues);
                    break;
                default:
                    System.out.println("Invalid group name. Expected groups: friends, family or colleagues");
            }
        }
    }

    private void printList(List<Contact> list) {
        for (Contact contact: list) {
            System.out.println(contact);
        }
    }

    @Override
    public void remove(String command) {
        String[] s = splitCommand(command);
        if (s.length != 2) {
            System.out.println("Incorrect command format. Command should be in format 'remove PHONE_NUMBER'");
            return;
        }
        String phoneNumber = s[1];
        Contact contact = contacts.get(phoneNumber);
        if (contact == null) {
            System.out.println("Tried to delete contact which is not in list");
        } else {
            //remove from phonebook
            contacts.remove(phoneNumber);
            //remove from contact type list
            switch (contact.getContactType().toString()) {
                case "friends":
                    friends.remove(contact);
                    break;
                case "family":
                    family.remove(contact);
                    break;
                case "colleagues":
                    colleagues.remove(contact);
                    break;
                default:
                    System.out.println("Unexpected error. Unkonwn contact group");
            }
        }
    }

    @Override
    public void show(String command) {
        String[] s = splitCommand(command);
        if (s.length != 2) {
            System.out.println("Incorrect command format. Command should be in format 'show PHONE_NUMBER'");
            return;
        }
        String phoneNumber = s[1];
        Contact contact = contacts.get(phoneNumber);
        if (contact == null) {
            System.out.println("Failed to find contact with phoneNumber:" + phoneNumber);
        } else {
            System.out.println(contact);
        }
    }

    @Override
    public void search(String command) {
        String[] s = splitCommand(command);
        if (s.length != 2) {
            System.out.println("Incorrect command format. Command should be in format 'search NAME'");
            return;
        }
        String name = s[1];
        List<Contact> results = new ArrayList();
        Iterator it = contacts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Contact> pair = (Map.Entry) it.next();
            if (pair.getValue().getName().contains(name)) {
                results.add(pair.getValue());
            }
        }
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            Contact contact = (Contact) iterator.next();
            System.out.println(contact);
        }
    }


    public void sortByPhoneNum() {
        //TODO
        // Create new list to sort dynamically.
        List<Contact> dynFriendList = new ArrayList<>();
        List<Contact> dynFamilyList = new ArrayList<>();
        List<Contact> dynColleaguesList = new ArrayList<>();

        // Get values from hashmap.
        Collection<Contact> values = contacts.values();
        for( Contact c : values){
            if(c.getContactType().equals("friends")) {
                dynFriendList.add(c);
            }
            else if (c.getContactType().equals("family")) {
                dynFamilyList.add(c);
            }
            else
                dynColleaguesList.add(c);
        }
        // Sort new dynamic list by phone number.
        SortContactsByPhoneNumber sc = new SortContactsByPhoneNumber();
        System.out.println("Sorting by phone number....");
        Collections.sort(dynFriendList, sc);


        // Print sorted phone numbers.
        for (int i = 0; i < dynFriendList.size(); i += 1) {
            //System.out.println(dynList.get(i).getPhoneNumber());
            System.out.println(dynFriendList.get(i));
        }
    }

    @Override
    public void sortName() {
        //TODO

    }

        private String[] splitCommand(String line) {
            return line.split("\\s+");
        }

    }