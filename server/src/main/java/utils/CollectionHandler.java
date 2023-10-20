package utils;

import Collection.LabWork;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Класс отвечающий за управление коллекцией
 */
public class CollectionHandler implements Serializable {
    private TreeSet<LabWork> collection;
    private Date initDate;
    private ReadWriteLock lock;
    public static final Set<Long> IDs = new LinkedHashSet<>();

    public CollectionHandler() {
        collection = new TreeSet<>();
        initDate = new Date();
        lock = new ReentrantReadWriteLock();
    }
    /**
     * Добавляет объект класса LabWork в коллекцию
     * @param labWork : Объект класса LabWork
     * @see LabWork
     * @return
     */
    public Boolean addLabWork(LabWork labWork){
        lock.writeLock().lock();
        try {
            collection.add(labWork);
            return true;
        } finally {
            lock.writeLock().lock();
        }
    }
    /**
     * Удаляет объект класса LabWork из коллекции
     * @param labWork : Объект класса LabWork
     * @see LabWork
     * @return
     */
    public Boolean removeLabWork(LabWork labWork){
        lock.writeLock().lock();
        try {
            collection.remove(labWork);
            return true;
        }finally {
            lock.writeLock().unlock();
        }
    }

    public LabWork getLabWork(int id){
        lock.readLock().lock();
        try{
            for(LabWork labWork1 :  collection){
                if(labWork1.getId()==id){
                    return labWork1;
                }
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getSize(){
        lock.readLock().lock();
        try {
            return collection.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public Date getInitDate() {
        lock.readLock().lock();
        try {
            return initDate;
        } finally {
            lock.readLock().unlock();
        }
    }

    public TreeSet<LabWork> getCollection(){
        lock.readLock().lock();
        try {
            return collection;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setCollection(TreeSet<LabWork> loadedCollection){
        lock.writeLock().lock();
        try{
            this.collection = loadedCollection;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void clear(){
        lock.readLock().lock();
        try{
            collection = collection.stream()
                    .limit(0)
                    .collect(Collectors.toCollection(TreeSet::new));
        }finally{
            lock.readLock().unlock();
        }
    }

    public long generateNextId(){
        lock.readLock().lock();
        try{
            long nextId = 0;
            for(LabWork labWork :  collection){
                if(labWork.getId()>=nextId){
                    nextId = labWork.getId();
                }
            }
            return nextId+1;
        }finally{
            lock.readLock().unlock();
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
    public void printLabWorkList(PrintWriter output){
        lock.writeLock().lock();
        try {
            for (LabWork labWork : collection) {
                output.println("id: " + labWork.getId());
                output.println("name: " + labWork.getName());
                output.println("coordinates: X:" + labWork.getCoordinates().getX() + " Y:" + labWork.getCoordinates().getY());
                output.println("creation_date: " + labWork.getCreationDate());
                output.println("minimal point: " + labWork.getMinimalPoint());
                output.println("difficulty: " + labWork.getDifficulty());
                output.println("discipline: " + labWork.getDiscipline().getName());
                output.println("------------------------------------------");
            }
        }finally {
            lock.writeLock().unlock();
        }
    }
    /**
     *Выводит объект из коллекции в строковом представлении
     *@param labWork: Объект класса LabWork
     *@see LabWork
     */
    public void printLabWork(LabWork labWork, PrintWriter output){
        lock.writeLock().lock();
        try {
            output.println("id: " + labWork.getId());
            output.println("name: " + labWork.getName());
            output.println("coordinates: X:" + labWork.getCoordinates().getX() + " Y:" + labWork.getCoordinates().getY());
            output.println("creation_date: " + labWork.getCreationDate());
            output.println("minimal point: " + labWork.getMinimalPoint());
            output.println("difficulty: " + labWork.getDifficulty());
            output.println("discipline: " + labWork.getDiscipline().getName());
            output.println("------------------------------------------");
        } finally {
            lock.writeLock().unlock();
        }
    }
    public String toString(LabWork labWork) {
        lock.writeLock().lock();
        try {
            String toStringResult;
            toStringResult = "id: " + labWork.getId() + "\n" +
                    "name: " + labWork.getName() + "\n" +
                    "coordinates: X:" + labWork.getCoordinates().getX() + " Y:" + labWork.getCoordinates().getY() + "\n" +
                    "creation_date: " + labWork.getCreationDate() + "\n" +
                    "minimal point: " + labWork.getMinimalPoint() + "\n" +
                    "difficulty: " + labWork.getDifficulty() + "\n" +
                    "discipline: " + labWork.getDiscipline().getName() + "\n" +
                    "------------------------------------------";
            return toStringResult;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
