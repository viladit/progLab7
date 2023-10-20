package utils;

import Collection.LabWork;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Класс отвечающий за управление коллекцией
 */
public class CollectionHandler implements Serializable {
    private TreeSet<LabWork> collection;
    private Date initDate;
    public static final Set<Long> IDs = new LinkedHashSet<>();

    public CollectionHandler() {
        collection = new TreeSet<>();
        initDate = new Date();
    }
    /**
     * Добавляет объект класса LabWork в коллекцию
     * @param labWork : Объект класса LabWork
     * @see LabWork
     * @return
     */
    public Boolean addLabWork(LabWork labWork){
        collection.add(labWork);
        return true;
    }
    /**
     * Удаляет объект класса LabWork из коллекции
     * @param labWork : Объект класса LabWork
     * @see LabWork
     * @return
     */
    public Boolean removeLabWork(LabWork labWork){
        collection.remove(labWork);
        return true;
    }

    public LabWork getLabWork(LabWork labWork){
        return labWork;
    }

    public int getSize(){
        return collection.size();
    }

    public Date getInitDate() {
        return initDate;
    }

    public TreeSet<LabWork> getCollection(){
        return collection;
    }

    public void setCollection(TreeSet<LabWork> loadedCollection){
        this.collection = loadedCollection;
    }

    public void clear(){
        collection.clear();
    }

    public int generateNextId(){
        Long randomID = 0l;
        while (true) {
            randomID = generateID();
            if (checkIfIDUnique(randomID)) {
                saveId(randomID);
                return Math.toIntExact(randomID);
            }
        }
    }
    public static boolean checkIfIDUnique(long id) {
        return !IDs.contains(id);
    }

    public static long generateID() {
        Random rand = new Random();
        int upperbound = 999999;
        int int_random = rand.nextInt(upperbound);
        return int_random;
    }
    public static void saveId(long id) {
        IDs.add(id);
    }

    /**
     * Загружает коллецию из csv
     * @see FileManager#readFromFile()
     */
    public void loadCollection(){
        FileManager fileManager = new FileManager();
        LabWork[] labWorks;
        try{
            labWorks = fileManager.readFromFile();
            for (LabWork labWork : labWorks) {
                addLabWork(labWork);
            }
        } catch (IOException e) {
            IOHandler.println("Ошибка при чтении файла: " + e.getMessage());
        }

    }
    /**
     *Выводит все объекты в коллекции в строковом представлении
     */
    public void printLabWorkList(){
        for (LabWork labWork : collection){
            IOHandler.println("id: " + labWork.getId());
            IOHandler.println("name: " + labWork.getName());
            IOHandler.println("coordinates: X:" + labWork.getCoordinates().getX() +" Y:"+ labWork.getCoordinates().getY());
            IOHandler.println("creation_date: " + labWork.getCreationDate());
            IOHandler.println("minimal point: " + labWork.getMinimalPoint());
            IOHandler.println("difficulty: " + labWork.getDifficulty());
            IOHandler.println("discipline: " + labWork.getDiscipline());
            IOHandler.println("------------------------------------------");
        }
    }
    /**
     *Выводит объект из коллекции в строковом представлении
     *@param labWork: Объект класса LabWork
     *@see LabWork
     */
    public void printLabWork(LabWork labWork){
        IOHandler.println("id: " + labWork.getId());
        IOHandler.println("name: " + labWork.getName());
        IOHandler.println("coordinates: X:" + labWork.getCoordinates().getX() +" Y:"+ labWork.getCoordinates().getY());
        IOHandler.println("creation_date: " + labWork.getCreationDate());
        IOHandler.println("minimal point: " + labWork.getMinimalPoint());
        IOHandler.println("difficulty: " + labWork.getDifficulty());
        IOHandler.println("discipline: " + labWork.getDiscipline());
        IOHandler.println("------------------------------------------");
    }
}
