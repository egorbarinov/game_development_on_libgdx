package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameController {
    private BattleMap map;
    private ProjectilesController projectilesController;
    private TanksController tanksController;

    public TanksController getTanksController() {
        return tanksController;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public BattleMap getMap() {
        return map;
    }


    // Инициализация игровой логики
    public GameController() {
        Assets.getInstance().loadAssets();
        this.map = new BattleMap();
        this.projectilesController = new ProjectilesController(this);
        this.tanksController = new TanksController(this);
        this.tanksController.setup(200, 200, Tank.Owner.PLAYER);
        this.tanksController.setup(400, 400, Tank.Owner.PLAYER);

    }

    public Tank getSelectedTank() {
        for (int i = 0; i < tanksController.getActiveList().size(); i++) {
            Tank t = tanksController.getActiveList().get(i);
            if (t.getPosition().dst(Gdx.input.getX(), 720 - Gdx.input.getY()) < 32) {
                return t;
            }
        }
        return null;
    }

    public void update(float dt) {

        tanksController.update(dt);
        projectilesController.update(dt);
        map.update(dt);
        checkCollisions(dt);

    }

    public void checkCollisions(float dt) {
    }
}


