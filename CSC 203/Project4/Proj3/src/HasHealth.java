import processing.core.PImage;

import java.util.List;

public abstract class HasHealth extends Active{
    private int health;

    public HasHealth(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod,
                     int health) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
    }
    public int getHealth(){
        return health;
    }

    public void setHealth(int inc){
        this.health += inc;
    }
}
