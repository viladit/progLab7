package Collection;

import java.io.Serializable;

public class Discipline implements Serializable {
    private String name; //Field can't be null, String can't be empty

    public Discipline (String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
