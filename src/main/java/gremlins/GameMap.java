package gremlins;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Define a exception called InvalidMapException with with message: Invalid Map!
 * It is thrown when map is invalid
 */
class InvalidMapException extends Exception {
    /**
     * Constructor of InvalidMapException
     */
    public InvalidMapException() {
        super("Invalid Map!");
    }
}

public class GameMap{

    /**
     * Map objects in map. e.g. stonewall, brickwall, shield, and exit
     */
    public char[][] map_objects = new char[33][36];
    /**
     * The name of map document
     */
    private String map_doc;
    /**
     * A list of brickwall that is going to be destoryed
     */
    public List<int[]> destory_list = new ArrayList<int[]>();
    /**
     * A list of brickwall that is going to be remove from destory_list
     */
    public List<int[]> brick_remove = new ArrayList<int[]>();
    /**
     *  The original locations of gremlins
     */
    public List<int[]> gremlin_location = new ArrayList<int[]>();

    /**
     * The original locations of red gremlins
     */
    public List<int[]> redgremlin_location = new ArrayList<int[]>();
    /**
     *  The location of shield
     */
    public int[] shield_location = new int[2];
    /**
     *  The location of exit
     */
    public int[] portal_location = new int[2];
    /**
     * The original location of wizard
     */
    public int[] wizard_location = new int[2];
    /**
     * The status of whether the powerup is avilable
     * It is false by default
     */
    public boolean powerUp_available = false;
    /**
     * How much time in game is the powerup not available
     */
    public int powerUp_count = 0;
    /**
     * Error from reading maps
     * By default, it is false
     */
    public boolean error = false;

    /**
     * Constructor of GameMap, required a file name
     * @param map_doc, the name of the map document
     */
    public GameMap(String map_doc){
        this.map_doc = map_doc;
    }

    /**
     * Read data in specified file name and store it
     * If the file does not exist, exception is thrown
     * @param app, read document and update data to variables in app 
     */
    public void read(App app){
        File f = new File(map_doc);
        try{
            Scanner scan = new Scanner(f);
            int i = 0;
            while(scan.hasNext()){
                String line  = scan.nextLine();
                map_objects[i] = line.toCharArray();
                i += 1;
            }
            scan.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        for (int i = 0 ; i < 33 ; i++){ //something do only once
            for (int j = 0 ; j < 36 ; j++){
                if (map_objects[i][j] == 'W') {
                    app.wizard = new Wizard(j*20, i*20);
                    app.wizard.setSprite(app.wizard_right);
                    wizard_location[0] = j*20;
                    wizard_location[1] = i*20;
                }
                else if (map_objects[i][j] == 'E') {
                    portal_location[0] = j*20;
                    portal_location[1] = i*20;
                }
                else if (map_objects[i][j] == 'G') {
                    app.gremlins.add(new Gremlin(j*20, i*20));
                    int[] location = {j*20, i*20};
                    gremlin_location.add(location);
                }
                else if (map_objects[i][j] == 'S'){
                    shield_location[0] = j*20;
                    shield_location[1] = i*20;
                }
                else if (map_objects[i][j] == 'R'){
                    app.redgremlins.add(new RedGremlin(j*20, i*20));
                    int[] location = {j*20, i*20};
                    redgremlin_location.add(location);
                }
            }
        }
    }

    /**
     * Draw images based on data at specified location in window
     * @param app, update data to app
     */
    public void generate(App app){
        for (int i = 0 ; i < 33 ; i++){
            for (int j = 0 ; j < 36 ; j++){
                if (map_objects[i][j] == 'X'){app.image(app.stonewall, j*20, i*20);}
                else if (map_objects[i][j] == 'B') {
                    app.image(app.brickwall, j*20, i*20);
                }
                else if (map_objects[i][j] == 'E') {
                    app.image(app.portal, j*20, i*20);
                }
                else if (map_objects[i][j] == 'S'){
                    if (powerUp_available){
                        app.image(app.shield, j*20, i*20);
                    }
                    if (powerUp_count < 10 * 60){
                        powerUp_count += 1;
                    }
                    if (powerUp_count == 10 * 60){
                        powerUp_available = true;
                    }
                }
            }
        }
    }

    /**
     * Open files with the names in layout
     * If the file does not exist, FileNotFoundException will be thrown
     * If map is invalid, InvalidMapException will be thrown
     * @param layout, the name of map file of different level
     */
    public void ValidMapCheck(List<String> layout){
        int wizard_count = 0;
        int shield_count = 0;
        int exit_count = 0;
        for (String l : layout){
            char[][] map_test = new char[33][36];
            File f = new File(l);
            try{
                Scanner scan = new Scanner(f);
                int i = 0;
                while(scan.hasNext()){
                    String line  = scan.nextLine();
                    if(line.length() != 36){throw new InvalidMapException();}
                    map_test[i] = line.toCharArray();
                    i += 1;
                }
                scan.close();
                if (i != 33){throw new InvalidMapException();}   
            }catch (FileNotFoundException e){
                e.printStackTrace();
                error = true;
                return;
            }catch (InvalidMapException e){
                e.printStackTrace();
                error = true;
            }catch(Exception e){
                e.printStackTrace();
                error = true;
            }
            try{
                for (int i = 0 ; i < 33 ; i++){
                    for (int j = 0 ; j < 36 ; j++){
                        if (i == 0 || i == 32){
                            if(map_test[i][j] != 'X'){throw new InvalidMapException();}
                        }
                        if(j == 0 || j == 35){
                            if (map_test[i][j] != 'X') {throw new InvalidMapException();}
                        }
        
                        if (map_test[i][j] == 'W') {wizard_count += 1;}
                        else if (map_test[i][j] == 'E') {exit_count += 1;}
                        else if (map_test[i][j] == 'S'){shield_count += 1;}
                    }
                }
                if (wizard_count != 1 || shield_count > 1 || exit_count != 1){throw new InvalidMapException();}  
                wizard_count = 0;
                shield_count = 0;
                exit_count = 0;
            }catch (InvalidMapException e){
                e.printStackTrace();
                error = true;
            }catch (Exception e){
                e.printStackTrace();
                error = true;
            }
        }
    }


    /**
     * Store location and count of brickwall that is going to be destoryed in list
     * @param x, x coordinate
     * @param y, y coordinate
     */
    public void brick_destory(int x, int y){
        int[] brick_detail = {x, y, 0};  // 0 is count
        destory_list.add(brick_detail);
    }

    /**
     * Load different images at the same location for brickwalls at different breaking status
     * @param app, update data to app
     */
    public void destory_animation(App app){

        for (int[] l : destory_list){
            if (l[2]%4 == 0){
                if((int)l[2]/4 == 0){
                    app.image(app.brickwall_destroy_0, l[1]*20, l[0]*20);
                }
                else if((int)l[2]/4 == 1){
                    app.image(app.brickwall_destroy_1, l[1]*20, l[0]*20);
                }
                else if((int)l[2]/4 == 2){
                    app.image(app.brickwall_destroy_2, l[1]*20, l[0]*20);
                }
                else{
                    app.image(app.brickwall_destroy_3, l[1]*20, l[0]*20);
                }
            }
            l[2] += 1;
            if(l[2] == 15){
                brick_remove.add(l);
            }
        }
        for(int[] l : brick_remove){
            destory_list.remove(l);
        }
        brick_remove.clear();
    }

    /**
     * Go to next level when wizard step on exit
     * Win when wizard reachs exit of last level
     * @param app, update data to app
     */
    public void nextlevel(App app){
        app.current_level += 1;
        if (app.current_level > app.total_level){
            app.win = true;
        }
        else{
            map_doc = app.layout.get(app.current_level - 1);
            app.wizard_cooldown = app.wizard_cooldowns.get(app.current_level - 1);
            app.enemy_cooldown = app.enemy_cooldowns.get(app.current_level - 1);
            app.fireballs.clear();
            app.slimes.clear();
            app.redslimes.clear();
            app.gremlins.clear();
            app.redgremlins.clear();
            gremlin_location.clear();
            redgremlin_location.clear();
            powerUp_available = false;
            powerUp_count = 0;
            read(app);
        }
    }
    /**
     Restart the game when player press any key after win or lose
     * @param app, update data to app
     */
    public void restart(App app){
        app.current_lives = app.total_lives;
        app.current_level = 1;
        map_doc = app.layout.get(app.current_level - 1);
        app.wizard_cooldown = app.wizard_cooldowns.get(app.current_level - 1);
        app.enemy_cooldown = app.enemy_cooldowns.get(app.current_level - 1);
        app.fireballs.clear();
        app.gremlins.clear();
        app.redgremlins.clear();
        app.slimes.clear();
        app.redslimes.clear();
        gremlin_location.clear();
        redgremlin_location.clear();
        powerUp_available = false;
        powerUp_count = 0;
        read(app);
    }

    /**
     * @return the name of current map document
     */
    public String getMapDoc(){return map_doc;}
}