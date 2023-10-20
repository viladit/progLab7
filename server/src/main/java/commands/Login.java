package commands;

import exceptions.ElementAmountException;
import requests.Request;
import utils.DatabaseHelper;
import utils.Hasher;
import utils.IOHandler;

/**
 * Класс отвечающий за команду login
 */
public class Login extends AbstractCommand {

    private DatabaseHelper databaseHelper;

    public Login(DatabaseHelper databaseHelper){
        super("login", "");
        this.databaseHelper = databaseHelper;
    }

    @Override
    public boolean argCheck(String arg){
        try{
            if(!arg.equals("placeholderArg")) throw new ElementAmountException();
            return true;
        } catch (ElementAmountException e) {
            IOHandler.println("Некорректное кол-во аргументов");
        }
        return false;
    }
    /**
     * Создание объекта класса Person и добавление его в коллекцию
     * @see utils.CollectionHandler
     * @see utils.LabWorkCreator
     */
    @Override
    public String execute(Request request){
        if(argCheck(request.getArguments())){
            Hasher hasher = new Hasher("SHA-256");
            if(!databaseHelper.checkIfUserExists(request.getUser().getLogin(), hasher.encode(request.getUser().getPassword()))){
                return "Неправильный логин или пароль";
            } else {
                return "Теперь вам доступны комманды, используйте help для их просмотра";
            }
        }
        return "";
    }
}
