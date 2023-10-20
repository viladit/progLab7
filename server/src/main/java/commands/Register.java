package commands;

import java.sql.SQLException;

import exceptions.ElementAmountException;
import requests.Request;
import utils.DatabaseHelper;
import utils.Hasher;
import utils.IOHandler;

/**
 * Класс отвечающий за команду register
 */
public class Register extends AbstractCommand {

    private DatabaseHelper databaseHelper;

    public Register(DatabaseHelper databaseHelper){
        super("register", "");
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
                try{
                    databaseHelper.register(request.getUser().getLogin(), request.getUser().getPassword());
                } catch (SQLException sqle) {
                    return "Пользователь с таким логином уже существует";
                }
            } else {
                return "Пользователь с таким логином уже существует";
            }
        }
        return "Пользователь успешно зарегистрирован!";
    }
}