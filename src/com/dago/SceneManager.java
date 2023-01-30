package com.dago;

import com.dago.games.Battleship.client.Main;

public class SceneManager {

    SceneManager(){}
    public static Scene scene;
    public static void set(Scene scn) {
        if(scene != null) scn.dispose();
        scene = scn;
        Main.mainPanel = scene;
        init();
    }
    public static void init() { scene.init();}
    public static void update() { scene.update();}
    public static void dispose() { scene.dispose();}
    public static Scene get() { return scene;}


}
