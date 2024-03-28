package gremlins;

public class EnemyAttack extends Attack{
    /**
     * Constructor of EnemyAttack, required x and y coordinate, and direction
     * @param x, x coordinate
     * @param y, y coordinate
     * @param direction, the moving direction of fireball
     */
    public EnemyAttack(int x ,int y, String direction){
        super(x, y, direction);
    }
    
    /**
     * Update the location of EnmeyAttack based on its direction
     */
    public void tick(){
        if (direction == "right") {x += speed;}
        else if (direction == "left") {x -= speed;}
        else if (direction == "up") {y -= speed;}
        else if (direction == "down") {y += speed;}
    }
}