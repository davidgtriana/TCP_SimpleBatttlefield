package com.dago;
import com.dago.games.Battleship.client.MainScene;

public class GameFrame implements AppAdapter {
    //public static SceneManager sceneManager = new SceneManager();
    public GameFrame(){
        this.init();
    }
    @Override
    public void init() {
        SceneManager.set(new MainScene());
    }

    @Override
    public void update() { SceneManager.update(); }

    @Override
    public void dispose() { SceneManager.dispose();}
}
