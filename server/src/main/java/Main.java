import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import commands.Command;
import commands.*;
import network.TCPServer;
import requests.Request;
import utils.*;

public class Main {
    public static void main(String[] args) {
        LabWorkCreator labWorkCreator = new LabWorkCreator();
        CollectionHandler collectionHandler = new CollectionHandler();
        FileManager fileManager = new FileManager();
        LabWorkValidator labWorkValidator = new LabWorkValidator(collectionHandler);
        DatabaseHelper databaseHelper = new DatabaseHelper();
        Scanner scanner = new Scanner(System.in);

        try {
            collectionHandler.setCollection(databaseHelper.getAllLabWorks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        labWorkValidator.checkCollectionValidity();

        TCPServer server = new TCPServer();

        Command info = new Info(collectionHandler, server);
        Command show = new Show(collectionHandler, server);
        Command add = new Add(collectionHandler, databaseHelper);
        Command addIfMax = new AddIfMax(collectionHandler, databaseHelper);
        Command exit = new Exit();
        Command removeById = new RemoveById(collectionHandler, databaseHelper);
        Command updateById = new UpdateById(labWorkCreator, collectionHandler);
        Command clear = new Clear(collectionHandler);
        Command save = new Save(collectionHandler, fileManager);
        Command printDescending = new PrintDescending(collectionHandler, server);
        Command removeGreater = new RemoveGreater(collectionHandler);
        Command removeLower = new RemoveLower(collectionHandler);
        Command filterStartsWithName = new FilterStartsWithName(collectionHandler, server);
        Command averageOfMinimalPoint = new AverageOfMinimalPoint(collectionHandler, server);
        Command register = new Register(databaseHelper);
        Command login = new Login(databaseHelper);
        Command load = new Load(collectionHandler);

        HashMap<String, Command> map= new HashMap<String, Command>();
        map.put(info.getName(), info);
        map.put(show.getName(), show);
        map.put(add.getName(), add);
        map.put(removeById.getName(), removeById);
        map.put(clear.getName(), clear);
        map.put(removeGreater.getName(), removeGreater);
        map.put(addIfMax.getName(), addIfMax);
        map.put(updateById.getName(), updateById);
        map.put(printDescending.getName(), printDescending);
        map.put(removeLower.getName(), removeLower);
        map.put(filterStartsWithName.getName(), filterStartsWithName);
        map.put(averageOfMinimalPoint.getName(), averageOfMinimalPoint);
        map.put(register.getName(), register);
        map.put(login.getName(), login);
        map.put(load.getName(), load);

        Command executeScript = new ExecuteScript(map);
        map.put(executeScript.getName(), executeScript);

        new Thread(() ->{
            while(true){
                String command = scanner.nextLine();
                if(command.trim().equals(exit.getName())){
                    scanner.close();
                    save.execute(new Request("save", "placeholderArg", null, null));
                    exit.execute(new Request("exit", "placeholderArg", null, null));
                }
                if(command.trim().equals(save.getName())){
                    save.execute(new Request("save", "placeholderArg", null, null));
                } else {
                    IOHandler.serverMsg("Такой команды не существует, на сервере доступны только команды save и exit");
                }
            }
        }).start();

        new Thread(() ->{
            server.start(map,collectionHandler);
        }).start();
    }
}