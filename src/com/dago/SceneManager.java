/**
 * SceneManager class that manages the scenes in the game.
 * It provides methods to set, initialize, update and dispose the scenes.
 *
 * @author Dago
 * @version 1.0
 * @date 5-Feb-2023
 */
package com.dago;

import com.dago.games.Battleship.client.Main;

public class SceneManager {
    /**
     * The constructor for the SceneManager class.
     */
    SceneManager() {
    }

    /**
     * A static reference to the current scene.
     */
    public static Scene scene;

    /**
     * The set method for the SceneManager class.
     * It disposes the previous scene and sets the new scene as the current scene.
     * Also sets the main panel in the Main class to the new scene.
     *
     * @param scn The new scene to be set as the current scene.
     */
    public static void set(Scene scn) {
        if (scene != null) scn.dispose();
        scene = scn;
        Main.mainPanel = scene;
        init();
    }

    /**
     * The init method for the SceneManager class.
     * It initializes the current scene.
     */
    public static void init() {
        scene.init();
    }

    /**
     * The update method for the SceneManager class.
     * It updates the current scene.
     */
    public static void update() {
        scene.update();
    }

    /**
     * The dispose method for the SceneManager class.
     * It disposes the current scene.
     */
    public static void dispose() {
        scene.dispose();
    }

    /**
     * The get method for the SceneManager class.
     * It returns the current scene.
     *
     * @return The current scene.
     */
    public static Scene get() {
        return scene;
    }

}

    