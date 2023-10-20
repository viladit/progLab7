package requests;

import java.io.Serializable;
import Collection.LabWork;
import Collection.User;

public class Request implements Serializable {
    private static final long serialVersionUID = -6961630760202667918L;
    private String name;
    private String arguments;
    private LabWork labWork;
    private User user;
    public Request(String name, String arguments, LabWork labWork, User user){
        this.name = name;
        this.arguments = arguments;
        this.labWork = labWork;
        this.user = user;
    }

    public String getCommandName() {
        return name;
    }

    public String getArguments() {
        return arguments;
    }

    public LabWork getLabWork() {
        return labWork;
    }

    public User getUser() {
        return user;
    }
}
