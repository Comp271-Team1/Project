package Team1.Comp271.LUC.com;

import java.io.FileNotFoundException;


public interface Iphonebook {

    /**
     * Process command from console
     * @param line
     * @throws FileNotFoundException
     */
    void processCommand(String line) throws FileNotFoundException;

    /**
     * Saves phonebook entries to the file
     * @throws FileNotFoundException
     */
    public void saveToFile() throws FileNotFoundException;

    /**
     * Loads phonebook entries saved in file into memory
     * @throws FileNotFoundException
     */
    public void loadFromFile() throws FileNotFoundException;

    /**
     * Adds new entry to the phonebook
     * @param command
     */
    public void add(String command);

    /**
     * Displays help menu
     */
    public void help();

    /**
     * List all or group of phonebook entries
     */
    public void list(String command);

    /**
     * Removes phonebook entry
     * @param command
     */
    public void remove(String command);

    /**
     * Displays phonebook entry with provided phone number
     * @param command
     */
    public void show(String command);

    /**
     * Search and displays all phonebook entries found with name search
     * @param command
     */
    public void search(String command);

    /**
     * Sort and display phonebook by name
     */
    public void sortName();

}

