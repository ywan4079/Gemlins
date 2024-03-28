package gremlins;

import processing.core.PApplet;

import org.checkerframework.checker.units.qual.s;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class CharacterTest{
    @Test
    public void CharacterCoolDownCheck() { // test cooldown_check function in Character
        Wizard w = new Wizard(100, 200);
        assertFalse(w.cooldown);
        assertEquals(0, w.cooldown_count);
        w.cooldown = true;
        for (int i = 0; i < 10; i++){
            w.cooldown_check(0.3333); //choose level 1 for test
        }
        assertTrue(w.cooldown);
        assertEquals(10, w.cooldown_count);
        for (int i = 0; i < 10; i++){
            w.cooldown_check(0.3333);
        }
        assertFalse(w.cooldown);
        assertEquals(0, w.cooldown_count);
    }

    @Test
    public void CharacterCollideGremlin() { //test character_collision_check function in Character
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1800);

        app.wizard.x = app.map.gremlin_location.get(0)[0] - 30;  //not collide, distance is 30
        app.wizard.y = app.map.gremlin_location.get(0)[1] ;
        app.gremlins.get(0).x = app.map.gremlin_location.get(0)[0];
        app.gremlins.get(0).y = app.map.gremlin_location.get(0)[1];
        app.wizard.setInvincible(false); //avoid wizard being invincible
        Character.character_collision_check(app);
        assertEquals(3, app.current_lives);

        app.wizard.x += 20; //collide, distance is 10
        app.gremlins.get(0).x = app.map.gremlin_location.get(0)[0];
        app.gremlins.get(0).y = app.map.gremlin_location.get(0)[1];
        app.wizard.setInvincible(false); //avoid wizard being invincible
        app.current_lives = 3; //full lives
        Character.character_collision_check(app);
        assertEquals(2, app.current_lives); //lose one live
        assertEquals(app.map.wizard_location[0], app.wizard.x);
        assertEquals(app.map.wizard_location[1], app.wizard.y);
        app.delay(100);
        app.current_lives = 1;

        app.delay(200);
        app.wizard.x = app.gremlins.get(0).x; //not collide, distance is 30
        app.wizard.y = app.gremlins.get(0).y - 30;
        app.wizard.y += 20;
        Character.character_collision_check(app);
        assertEquals(0, app.current_lives);
        assertTrue(app.lose);
    }
    @Test
    public void CharacterCollideWall() { //test collision function in Character
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1200);
        
        //wall collision
        boolean[] result = new boolean[8];
        app.wizard.x = 280;
        app.wizard.y = 160;
        // B
        // WB
        //  B
        result = app.wizard.collision(app);
        app.delay(200);
        assertFalse(result[2]);//up
        assertFalse(result[0]);//right
        assertFalse(result[5]);//right-down

        app.wizard.x = 200;
        app.wizard.y = 460;
        // B
        //BW
        //BB
        result = app.wizard.collision(app);
        app.delay(200);
        assertFalse(result[2]);//up
        assertFalse(result[1]);//left
        assertFalse(result[3]);//down
        assertFalse(result[7]);//left-down

        app.wizard.x = 180;
        app.wizard.y = 180;
        //B B
        // W
        result = app.wizard.collision(app);
        app.delay(200);
        assertFalse(result[4]);//right-up
        assertFalse(result[6]);//left-up
        //all 8 directions are included
    }
    @Test
    public void CharacterSetAndGet() { //test get_cooldown_status, getX, getY, setX, and setY function in Character
        Wizard w = new Wizard(100, 200);
        w.cooldown = true;
        w.setX(200);
        w.setY(400);
        assertTrue(w.get_cooldown_status());
        assertEquals(200, w.getX());
        assertEquals(400, w.getY());
        w.cooldown = false;
        w.setX(60);
        w.setY(150);
        assertFalse(w.get_cooldown_status());
        assertEquals(60, w.getX());
        assertEquals(150, w.getY());
    }
}