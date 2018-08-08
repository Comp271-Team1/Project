package Team1.Comp271.LUC.com;

import java.util.Comparator;

public class SortContactsByPhoneNumber implements Comparator<Contact>{

    @Override
    public int compare(Contact o1, Contact o2) {
        if(Long.parseLong(o1.getPhoneNumber()) > Long.parseLong(o2.getPhoneNumber())) {
            return 1;
        }
        return -1;
    }
}
