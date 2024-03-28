package gremlins;

import processing.core.PApplet;

import org.checkerframework.checker.units.qual.s;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class AttackTest{
    @Test
    public void AttackTick() { // test tick function in Attack
        Fireball f = new Fireball(100, 200, "right");
        f.tick();
        assertEquals(104, f.getX());
        assertEquals(200, f.getY());
        f.direction = "down";
        f.tick();
        assertEquals(104, f.getX());
        assertEquals(204, f.getY());
        f.direction = "left";
        f.tick();
        assertEquals(100, f.getX());
        assertEquals(204, f.getY());
        f.direction = "up";
        f.tick();
        assertEquals(100, f.getX());
        assertEquals(200, f.getY());
    }

    @Test
    public void AttackCollision() { //test the static method attack_collision_check in Attack
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1400);

        //fireball hit slime
        app.wizard.x = 60;
        app.wizard.y = 40;
        app.gremlins.get(0).x = 260;
        app.gremlins.get(0).y = 40;
        app.gremlins.get(0).setDirection("left");
        app.fireballs.clear(); //reset, avoid error
        app.wizard.attack(app);
        app.gremlins.get(0).attack(app);
        for (int i = 0; i < 10; i++){ //fireball and slime collide after 23 tick
            app.fireballs.get(0).tick();
            app.slimes.get(0).tick();
        }
        Attack.attack_collision_check(app);
        assertEquals(1, app.fireballs.size()); //fireball still exist
        for (int i = 0; i < 13; i++){ //they collide!
            app.fireballs.get(0).tick();
            app.slimes.get(0).tick();
        }
        Attack.attack_collision_check(app);
        assertEquals(0, app.fireballs.size()); //fireball is absorbed by slime
        app.delay(300);

        //fireball hit gremlin
        app.wizard.x = 160;
        app.wizard.y = 40;
        app.gremlins.get(0).x = 260;
        app.gremlins.get(0).y = 40;
        app.wizard.attack(app); //fireball hit gremlin after 21 tick
        for(int i = 0; i < 10; i++){
            app.fireballs.get(0).tick();
        }
        Attack.attack_collision_check(app);
        assertEquals(1, app.fireballs.size()); // the fireball still exist
        for(int i = 0; i < 11; i++){
            app.fireballs.get(0).tick();
        }
        Attack.attack_collision_check(app);
        assertEquals(0, app.fireballs.size()); // the fireball is absorbed

        app.delay(300);
        //slime hit wizard
        app.wizard.x = 160;
        app.wizard.y = 40;
        app.gremlins.get(0).x = 260;
        app.gremlins.get(0).y = 40;
        app.gremlins.get(0).setDirection("left");
        app.gremlins.get(0).attack(app);
        for(int i = 0; i < 10; i++){ //slime hit wizard after 21 tick
            app.slimes.get(0).tick();
        }
        Attack.attack_collision_check(app);
        assertEquals(3, app.current_lives);  // does not hit wizard yet
        for(int i = 0; i < 11; i++){
            app.slimes.get(0).tick();
        }
        app.current_lives = 3; //reset, avoid error
        Attack.attack_collision_check(app);
        app.delay(100);
        assertEquals(2, app.current_lives); //lives decrease to 2 => the slime hit wizard
        assertEquals(app.map.wizard_location[0], app.wizard.x); //wizard respawn at original location
        assertEquals(app.map.wizard_location[1], app.wizard.y);
    }

    @Test
    public void AttackGetXY() {
        App app = new App();
        Wizard w = new Wizard(200, 300);
        w.attack(app);
        assertEquals(200, app.fireballs.get(0).getX());
        assertEquals(300, app.fireballs.get(0).getY());
        app.fireballs.get(0).tick();
        assertEquals(204, app.fireballs.get(0).getX());
        assertEquals(300, app.fireballs.get(0).getY());
    }

}