/**
 * GameFrame class that implements the AppAdapter interface.
 * It initializes the main scene and updates and disposes the scene as necessary.
 *
 * @author Dago
 * @version 1.0
 * @date 5-Feb-2023
 */
package com.dago;
import com.dago.games.Battleship.client.MainScene;

public class GameFrame implements AppAdapter {
    /**
     * The constructor for the GameFrame class.
     * It calls the init method.
     */
    public GameFrame(){
        this.init();
    }

    /**
     * The implementation of the init method from the AppAdapter interface.
     * It sets the main scene as the current scene in the SceneManager.
     */
    @Override
    public void init() {
        SceneManager.set(new MainScene());
    }

    /**
     * The implementation of the update method from the AppAdapter interface.
     * It updates the current scene in the SceneManager.
     */
    @Override
    public void update() { SceneManager.update(); }

    /**
     * The implementation of the dispose method from the AppAdapter interface.
     * It disposes the current scene in the SceneManager.
     */
    @Override
    public void dispose() { SceneManager.dispose();}
}