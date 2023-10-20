package utils;

import Collection.LabWork;
import java.util.TreeSet;

/**
 * Класс для проверки загружаемых элементов класса LabWork
 * @see LabWork
 */
public class LabWorkValidator {

    CollectionHandler collectionHandler;
    TreeSet<LabWork> labWorksToCheck;
    public LabWorkValidator(CollectionHandler collectionHandler) {
        this.collectionHandler = collectionHandler;
        labWorksToCheck = collectionHandler.getCollection();
    }

    Boolean checkPersonValidity(LabWork labWork){
        if(labWork.getId()<=0) return false;
        if(labWork.getName().trim().isEmpty()) return false;
        if(labWork.getCoordinates().getX() < -999 || labWork.getCoordinates().getY() < -511) return false;
        if(labWork.getCreationDate() == null) return false;
        if(labWork.getMinimalPoint() < 6 || labWork.getMinimalPoint() > 20) return false;
        if(labWork.getDifficulty().toString().toUpperCase() != "HARD" ||
                labWork.getDifficulty().toString().toUpperCase() != "VERY_HARD" ||
                labWork.getDifficulty().toString().toUpperCase() != "IMPOSSIBLE" ||
                labWork.getDifficulty().toString().toUpperCase() != "INSANE" ||
                labWork.getDifficulty().toString().toUpperCase() != "HOPELESS") return false;
        return true;
    }

    public void checkCollectionValidity(){
        TreeSet<LabWork> labWorksToDelete = new TreeSet<LabWork>();
        for (LabWork labWork : labWorksToCheck){
            if(!checkPersonValidity(labWork)){
                labWorksToDelete.add(labWork);
            }
        }
        if(!labWorksToDelete.isEmpty()){
            IOHandler.println("В файле обнаружены некорректные данные, некорректные элементы коллекции будут удалены.");
            for (LabWork labWork: labWorksToDelete){
                collectionHandler.removeLabWork(labWork);
            }
        }
    }
}
