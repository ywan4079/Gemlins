package gremlins;

import gremlins.App;
import gremlins.Gremlin;
import gremlins.Slime;
import gremlins.Wizard;

import java.util.List;
import java.util.ArrayList;

public class Attack{
    
    /**
     * Moving direction of attack
     */
    protected String direction;

    /**
     * x,y coordinate
     */
    protected int x, y;

    /**
     * Speed of the attack
     */
    protected int speed;

    /**
     * Constructor of Attack, required x,y coordinate and direction of attack
     * @param x, x coordinate
     * @param y, y coordinate
     * @param direction, direction of attack
     */
    public Attack(int x, int y, String direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * Detect collision between wizard, fireball, gremlin, red gremlin, and slime, and red slime
     * If the distance between fireball and slime (or red slime) is less than 20 pixel, they both disappear
     * If the distance between fireball and gremlin (or red gremlin) is less than 20 pixel
     * Then, fireball is absorbed, gremlin respawns, and if red gremlin's lives is less or equal to 0, it respwans.
     * If the distance between slime(or red slime) and wizard is less than 20 pixel, slime vapourised, and wizard respawns.
     * @param app, update data to app
     */
    public static void attack_collision_check(App app){
        List<Fireball> fireball_delete = new ArrayList<Fireball>();
        List<Slime> slime_delete = new ArrayList<Slime>();
        List<RedSlime> redslime_delete = new ArrayList<RedSlime>();
        for (Fireball f : app.fireballs){  //fireball hit gremlin or slime
            int f_center_X = f.getX() + 10;
            int f_center_Y = f.getY() + 10;
            for (Slime s : app.slimes){
                int s_center_X = s.getX() + 10;
                int s_center_Y = s.getY() + 10;
                int distance_square = (s_center_X - f_center_X)*(s_center_X - f_center_X) + (s_center_Y - f_center_Y)*(s_center_Y - f_center_Y);
                if (distance_square < 400){
                    fireball_delete.add(f);
                    slime_delete.add(s);
                }
            }
            for (Gremlin g : app.gremlins){
                int g_center_X = g.getX() + 10;
                int g_center_Y = g.getY() + 10;
                int distance_square = (g_center_X - f_center_X)*(g_center_X - f_center_X) + (g_center_Y - f_center_Y)*(g_center_Y - f_center_Y);
                if(distance_square < 400){
                    fireball_delete.add(f);
                    g.respawn(app);
                }
            }

            for (RedSlime r : app.redslimes){
                int r_center_X = r.getX() + 10;
                int r_center_Y = r.getY() + 10;
                int distance_square = (r_center_X - f_center_X)*(r_center_X - f_center_X) + (r_center_Y - f_center_Y)*(r_center_Y - f_center_Y);
                if (distance_square < 400){
                    fireball_delete.add(f);
                    redslime_delete.add(r);
                }
            }
            for (RedGremlin rg : app.redgremlins){
                int rg_center_X = rg.getX() + 10;
                int rg_center_Y = rg.getY() + 10;
                int distance_square = (rg_center_X - f_center_X)*(rg_center_X - f_center_X) + (rg_center_Y - f_center_Y)*(rg_center_Y - f_center_Y);
                if(distance_square < 400){
                    fireball_delete.add(f);
                    rg.lives -= 1;
                    if(rg.lives == 0){
                        rg.total_lives += 1;
                        rg.lives = rg.total_lives;
                        rg.respawn(app);
                    }
                }
            }
        }
        for (Slime s : app.slimes){ //slime hit wizard
            int s_center_X = s.getX() + 10;
            int s_center_Y = s.getY() + 10;
            int w_center_X = app.wizard.getX() + 10;
            int w_center_Y = app.wizard.getY() + 10;
            int distance_square = (s_center_X - w_center_X)*(s_center_X - w_center_X) + (s_center_Y - w_center_Y)*(s_center_Y - w_center_Y);
            if (distance_square < 400){
                slime_delete.add(s);
                if(!app.wizard.getInvincible()){
                    app.current_lives -= 1;
                    app.wizard.respawn(app);
                    if (app.current_lives <= 0){app.lose = true;}
                }
            }
        }

        for (RedSlime r : app.redslimes){ //redslime hit wizard
            int r_center_X = r.getX() + 10;
            int r_center_Y = r.getY() + 10;
            int w_center_X = app.wizard.getX() + 10;
            int w_center_Y = app.wizard.getY() + 10;
            int distance_square = (r_center_X - w_center_X)*(r_center_X - w_center_X) + (r_center_Y - w_center_Y)*(r_center_Y - w_center_Y);
            if (distance_square < 400){
                redslime_delete.add(r);
                if(!app.wizard.getInvincible()){
                    app.current_lives -= 1;
                    app.wizard.respawn(app);
                    if (app.current_lives <= 0){app.lose = true;}
                }
            }
        }


        for (Fireball f : fireball_delete){app.fireballs.remove(f);}
        for (Slime s : slime_delete){app.slimes.remove(s);}
        for (RedSlime s : redslime_delete){app.redslimes.remove(s);}
    }

    /**
     * @return x coordinate
     */
    public int getX(){return x;}

    /**
     * @return y coordinate
     */
    public int getY(){return y;}
}