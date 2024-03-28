package gremlins;

import processing.core.PApplet;

import org.checkerframework.checker.units.qual.s;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class MapTest{
    @Test
    public void MapConstructor () { //test GameMap constructor
        GameMap map = new GameMap("level1.txt");
        assertEquals("level1.txt", map.getMapDoc());
    }
 
    @Test
    public void MapReadValidMap() { //test map read level 1
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        GameMap map = new GameMap("level1.txt");
        map.read(app);
        assertNotNull(app.wizard);
        int[] wl = {40, 20};
        assertArrayEquals(wl, map.wizard_location);

        assertNotNull(app.gremlins);
        int[] gl0 = {460, 200};
        assertArrayEquals(map.gremlin_location.get(0), gl0);
        int[] gl1 = {40, 360};
        assertArrayEquals(map.gremlin_location.get(1), gl1);
        int[] gl2 = {440, 400};
        assertArrayEquals(map.gremlin_location.get(2), gl2);
        int[] gl3 = {420, 580};
        assertArrayEquals(map.gremlin_location.get(3), gl3);
        assertEquals(app.gremlins.size(), 4);

        int[] pl = {620, 620};
        assertArrayEquals(pl, map.portal_location);
        int[] sl = {320, 320};
        assertArrayEquals(sl, map.shield_location);

        char[][] mo =  {{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                    {'X', ' ', 'W', ' ', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', ' ', ' ', 'X'},
                    {'X', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'B', 'B', 'B', ' ', 'X', ' ', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'B', ' ', 'B', ' ', 'X'},
                    {'X', ' ', 'B', ' ', 'B', 'B', 'X', 'B', 'B', 'X', 'B', ' ', 'B', 'X', 'B', 'B', 'B', ' ', ' ', ' ', ' ', 'B', ' ', 'X', 'X', 'X', 'X', 'X', 'X', ' ', ' ', 'B', ' ', 'B', ' ', 'X'},
                    {'X', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'B', 'B', 'B', 'B', 'B', 'B', ' ', 'B', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'B', 'B', ' ', ' ', ' ', 'X'},
                    {'X', ' ', 'B', ' ', 'B', 'B', 'X', 'B', 'X', 'B', 'B', 'X', 'B', 'B', 'B', 'B', ' ', ' ', ' ', 'B', 'B', 'B', ' ', ' ', 'B', ' ', 'X', ' ', 'X', ' ', ' ', 'B', ' ', 'B', ' ', 'X'},
                    {'X', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' ', ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ', 'X', 'X', ' ', ' ', ' ', 'B', 'B', 'B', ' ', 'X', ' ', 'X', ' ', 'B', 'B', ' ', 'B', ' ', 'X'},
                    {'X', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' ', ' ', ' ', ' ', 'B', ' ', 'B', ' ', ' ', ' ', 'B', 'B', 'B', 'B', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'B', 'B', 'B', 'B', 'X', 'X'},
                    {'X', ' ', 'X', ' ', ' ', ' ', 'B', ' ', 'B', ' ', 'B', ' ', ' ', ' ', ' ', 'B', 'B', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X', ' ', 'B', ' ', 'B', 'B', ' ', ' ', 'X', 'X'},
                    {'X', ' ', 'X', 'B', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', 'B', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'B', ' ', ' ', ' ', ' ', ' ', 'X', 'X'},
                    {'X', ' ', 'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', 'X', 'X', 'X', ' ', 'B', 'B', 'X', ' ', ' ', ' ', ' ', ' ', 'G', ' ', ' ', 'B', ' ', 'B', ' ', 'B', 'B', 'B', ' ', 'X', 'X'},
                    {'X', ' ', 'X', ' ', 'B', 'B', 'X', 'X', 'X', ' ', 'X', 'X', ' ', ' ', ' ', 'B', 'B', 'X', ' ', ' ', 'B', 'B', 'B', 'B', 'B', ' ', ' ', ' ', 'B', ' ', 'B', 'B', ' ', ' ', 'X', 'X'},
                    {'X', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'B', ' ', ' ', 'B', ' ', 'B', 'B', 'B', 'B', 'B', ' ', ' ', ' ', ' ', ' ', ' ', 'B', ' ', 'B', ' ', 'B', 'B', 'B', 'X', 'X', 'X'},
                    {'X', ' ', 'B', 'B', 'B', ' ', 'X', ' ', ' ', 'B', 'B', ' ', 'B', 'B', 'B', ' ', 'B', 'B', 'X', 'B', ' ', 'B', 'B', 'B', 'B', 'B', 'B', ' ', ' ', ' ', ' ', 'B', 'B', 'X', 'X', 'X'},
                    {'X', ' ', 'B', ' ', 'B', ' ', 'X', ' ', 'B', 'B', 'B', ' ', ' ', 'B', ' ', ' ', 'B', 'B', 'B', ' ', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'B', 'B', 'X', 'X', 'X'},
                    {'X', ' ', ' ', ' ', 'B', ' ', 'X', ' ', 'B', ' ', ' ', ' ', ' ', 'B', ' ', 'B', 'B', 'B', ' ', ' ', ' ', 'B', ' ', 'B', 'B', 'B', 'B', ' ', 'B', ' ', ' ', 'B', 'B', 'X', 'X', 'X'},
                    {'X', ' ', 'B', ' ', ' ', ' ', 'X', 'B', 'B', ' ', ' ', 'B', 'B', ' ', ' ', ' ', 'S', ' ', ' ', 'B', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'B', 'B', 'B', 'X', 'X', 'X'},
                    {'X', 'B', ' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ', 'B', ' ', 'B', ' ', 'B', 'B', 'B', 'B', ' ', ' ', ' ', 'B', 'B', 'B', ' ', ' ', 'B', ' ', 'B', ' ', 'B', 'X', 'X', 'X'},
                    {'X', 'B', 'G', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' ', 'B', ' ', ' ', 'B', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', 'B', ' ', 'B', ' ', ' ', ' ', 'X', 'X'},
                    {'X', 'B', 'B', 'B', 'B', ' ', 'X', ' ', 'B', ' ', 'B', ' ', 'B', ' ', 'B', ' ', 'B', ' ', ' ', 'B', ' ', 'B', ' ', 'B', 'B', 'B', ' ', ' ', 'X', ' ', 'B', ' ', ' ', ' ', 'X', 'X'},
                    {'X', 'X', 'X', 'X', 'B', ' ', 'B', 'B', 'B', ' ', 'B', ' ', 'B', ' ', 'B', ' ', 'B', ' ', 'B', 'B', ' ', 'B', 'G', 'B', ' ', ' ', ' ', ' ', 'X', ' ', 'B', ' ', 'X', ' ', 'X', 'X'},
                    {'X', 'B', 'X', 'X', 'B', 'B', 'X', ' ', ' ', ' ', 'B', ' ', 'B', ' ', 'B', ' ', ' ', ' ', ' ', 'B', ' ', 'X', ' ', 'B', ' ', ' ', ' ', ' ', 'X', ' ', 'B', ' ', 'X', ' ', ' ', 'X'},
                    {'X', 'B', 'B', 'B', 'B', 'B', 'X', ' ', ' ', ' ', 'B', ' ', 'B', ' ', 'B', ' ', 'X', ' ', ' ', ' ', ' ', 'X', ' ', 'B', 'X', 'X', 'X', ' ', 'X', ' ', ' ', ' ', 'X', ' ', ' ', 'X'},
                    {'X', ' ', 'B', ' ', 'B', 'B', 'B', ' ', 'B', 'B', ' ', ' ', 'B', ' ', 'X', ' ', ' ', ' ', ' ', 'B', ' ', 'X', ' ', 'B', 'X', 'X', 'B', ' ', 'X', ' ', 'B', ' ', 'X', 'B', ' ', 'X'},
                    {'X', ' ', 'B', ' ', ' ', 'B', 'B', ' ', ' ', 'B', 'B', ' ', 'B', 'X', ' ', ' ', ' ', ' ', ' ', 'B', ' ', 'X', ' ', 'B', 'B', ' ', ' ', ' ', 'X', ' ', 'B', ' ', ' ', 'B', ' ', 'X'},
                    {'X', 'B', 'B', ' ', ' ', 'B', 'B', 'B', ' ', ' ', ' ', 'X', 'B', ' ', ' ', ' ', ' ', ' ', ' ', 'B', ' ', ' ', 'R', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'B', ' ', ' ', 'B', ' ', 'X'},
                    {'X', ' ', ' ', 'X', 'X', 'X', 'B', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'B', 'B', ' ', ' ', 'B', ' ', ' ', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'X'},
                    {'X', 'B', ' ', 'X', 'B', ' ', 'B', 'B', ' ', ' ', 'B', ' ', 'B', ' ', ' ', 'X', ' ', ' ', 'B', ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X', ' ', 'X'},
                    {'X', ' ', 'B', ' ', 'B', ' ', 'B', 'B', ' ', 'B', 'B', 'B', 'B', 'B', ' ', 'X', ' ', ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X', ' ', 'X'},
                    {'X', ' ', 'B', ' ', 'B', ' ', 'B', 'B', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'B', ' ', ' ', 'B', ' ', 'B', 'G', ' ', 'X', 'B', 'X', 'B', 'X', 'B', ' ', 'X', 'X', ' ', 'X', ' ', 'X'},
                    {'X', ' ', 'B', ' ', 'X', 'X', 'B', 'X', 'B', 'X', 'X', 'B', 'X', 'X', ' ', 'X', ' ', ' ', 'B', ' ', 'B', ' ', ' ', ' ', ' ', 'B', ' ', 'B', ' ', ' ', ' ', 'X', 'X', 'X', ' ', 'X'},
                    {'X', ' ', 'B', ' ', ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'B', ' ', 'B', ' ', ' ', ' ', 'E', ' ', ' ', ' ', 'X'},
                    {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};

        assertArrayEquals(mo, map.map_objects);
    }

    @Test
    public void MapGenerate() { //test map generate function
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(400);

        app.map.read(app);
        app.map.generate(app);
        assertFalse(app.map.powerUp_available); //powerup should not be available at the beginning of game
        for(int i = 0 ; i < 700; i++){
            app.map.generate(app);
        }
        app.delay(500);
        assertEquals(600, app.map.powerUp_count);
        assertTrue(app.map.powerUp_available);
    }

    @Test
    public void MapValidMapCheck() { //test map ValidMapCheck function can check valid map document and map
        List<String> layout = new ArrayList<String>();
        GameMap map = new GameMap("level1.txt");
        layout.add("level1.txt");
        layout.add("level2.txt");
        map.ValidMapCheck(layout);
        assertFalse(map.error);
    }

    @Test
    public void MapInvalidMapDocCheck() { //test map ValidMapCheck function can check invalid map document
        List<String> layout = new ArrayList<String>();
        GameMap map = new GameMap("NoSuchFile.txt");
        layout.add("NoSuchFile.txt"); // add file that does not exist to cause an error
        map.ValidMapCheck(layout);
        assertTrue(map.error);
    }

    @Test
    public void MapInvalidMapCheck() { //test map ValidMapCheck function can check invalid map
        List<String> layout = new ArrayList<String>();
        GameMap map = new GameMap("InvalidMap1.txt");
        layout.add("InvalidMap1.txt"); //map size is 33 * 35
        map.ValidMapCheck(layout);
        assertTrue(map.error);

        layout.clear();  //reset
        map.error = false; //reset
        layout.add("InvalidMap2.txt"); // map size is 32 * 36
        map.ValidMapCheck(layout);
        assertTrue(map.error);

        layout.clear();  //reset
        map.error = false; //reset
        layout.add("InvalidMap3.txt"); // not every bounding border is stonewall
        map.ValidMapCheck(layout);
        assertTrue(map.error);

        layout.clear();  //reset
        map.error = false; //reset
        layout.add("InvalidMap4.txt"); // no wizard
        map.ValidMapCheck(layout);
        assertTrue(map.error);

        layout.clear();  //reset
        map.error = false; //reset
        layout.add("InvalidMap5.txt"); // no exit
        map.ValidMapCheck(layout);
        assertTrue(map.error);

        layout.clear();  //reset
        map.error = false; //reset
        layout.add("InvalidMap6.txt"); // more than one shield
        map.ValidMapCheck(layout);
        assertTrue(map.error);
    }

    @Test
    public void BrickDestory() { // test map brick_destory function in GameMap
        GameMap map = new GameMap("level1.txt");
        map.brick_destory(25, 14);
        map.brick_destory(18, 6);
        int[] brick_information1 = {25, 14, 0};
        assertArrayEquals(brick_information1, map.destory_list.get(0));
        int[] brick_information2 = {18, 6, 0};
        assertArrayEquals(brick_information2, map.destory_list.get(1));
    }

    @Test
    public void Destory_Animation() { //test destory_animation function in GameMap
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1200);

        GameMap map = new GameMap("level1.txt");
        map.brick_destory(25, 14);
        for (int i = 0; i < 8; i++){
            map.destory_animation(app);
        }
        int[] brick_information1 = {25, 14, 8};
        app.delay(300);
        assertArrayEquals(brick_information1, map.destory_list.get(0));
        map.brick_destory(18, 6);
        
        for (int i = 0; i < 8; i++){
            map.destory_animation(app);
        }
        int[] brick_information2 = {18, 6, 8};
        app.delay(300);
        assertArrayEquals(brick_information2, map.destory_list.get(0));
    }

    @Test
    public void NextLevel() { //test nextlevel function in GameMap
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(800);

        assertEquals("level1.txt", app.map.getMapDoc());
        assertEquals(1, app.current_level);
        assertEquals(3, app.total_level);
        assertEquals(0.3333, app.wizard_cooldown);
        assertEquals(3.0, app.enemy_cooldown);
        app.map.powerUp_available = true;
        app.map.powerUp_count = 300;

        app.current_level = 1; //reset, avoid error
        app.map.nextlevel(app);
        assertEquals("level2.txt", app.map.getMapDoc());
        assertEquals(2, app.current_level);
        assertEquals(2.0, app.wizard_cooldown);
        assertEquals(1.0, app.enemy_cooldown);
        assertFalse(app.win);
        assertFalse(app.map.powerUp_available);
        assertEquals(0, app.map.powerUp_count);
        app.map.nextlevel(app);
        app.delay(200);
        assertEquals("level3.txt", app.map.getMapDoc());
        app.map.nextlevel(app);
        assertTrue(app.win);
    }
    @Test
    public void Restart() { //test restart function in GameMap
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.map.nextlevel(app); //level 2
        app.current_lives = 1;
        app.delay(200);
        assertEquals(2, app.current_level);
        app.map.powerUp_available = true;
        app.map.powerUp_count = 300;
        app.map.restart(app);
        app.delay(400);
        assertEquals(3, app.current_lives);
        assertEquals(1, app.current_level);
        assertEquals("level1.txt", app.map.getMapDoc());
        assertEquals(0.3333, app.wizard_cooldown);
        assertEquals(3.0, app.enemy_cooldown);
        assertFalse(app.map.powerUp_available);
        assertEquals(0, app.map.powerUp_count);
    }
}