package Team1.Comp271.LUC.com;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Phonebook ph = new Phonebook();
        Scanner kb = new Scanner(System.in);
        System.out.println("Welcome to Team 1's Phonebook.");
        System.out.println("Press A to add a contact, H to see help, x to exit");
        boolean bool = true;
        String choice = "";
        while (kb.hasNextLine()) {
            choice = kb.next();
            if (choice.equals("h")){
                ph.help();
            }
            else if (choice.equals("a")) {
                ph.add("add 7085994545 joe joe@yahoo.com ringtone1 friends");
                ph.add("add 3125553221 bob bob@yahoo.com ringtone5 friends");
                ph.add("add 7024438777 alice alice@yahoo.com ringtone6 friends");
                ph.add("add 3125558775 faris faris@gmail.com ringtone2 family");
                ph.add("add 7735456677 stephanie steph@aol.com ringtone3 colleagues");
                System.out.println("You've added the following contacts.");
                //ph.show("show 7085994545");
                //ph.show("show 3125558775");
                //ph.show("show 7735456677");
                ph.list("list");
                System.out.println();
                ph.sortByPhoneNum();
            }
            else if(choice.equals("r")) {
                ph.remove("remove 7085994545");
                ph.list("list");
            }
        }

    }

}
