package Collection;

import java.util.TreeSet;

public class LabWorkHelper {
    private static TreeSet<LabWork> labWorkTreeSet = new TreeSet<LabWork>();

    public static TreeSet<LabWork> getCollection(){
        return labWorkTreeSet;
    }
}
