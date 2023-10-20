package Collection;

import java.io.Serializable;

/**
 * Класс который является одним из полей LabWork
 * @see LabWork
 */
public class Coordinates  implements Serializable {
    private long x; //Значение поля должно быть больше -1000
    private float y; //Значение поля должно быть больше -500
    public Coordinates(long x, float y){
        this.x = x;
        this.y = y;
    }
    public Coordinates(){}

    public long getX() {
        return x;
    }
    public void setX(long x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
}
