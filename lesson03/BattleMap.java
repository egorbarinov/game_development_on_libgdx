package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class BattleMap {
    private TextureRegion grassTexture;
    private TextureRegion flowerTexture;
    private int[][] data;

    public BattleMap() {
        this.grassTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.flowerTexture = Assets.getInstance().getAtlas().findRegion("flower");
        this.data = new int[16][9];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                data[MathUtils.random(15)][MathUtils.random(8)] = 1;
            }
        }
    }

    public void cleanData(GameController gc) {
        int cellX = (int)(gc.getTank().position.x / 80);
        int cellY = (int)(gc.getTank().position.y / 80);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                    data[cellX][cellY]= 0;
            }
        }
    }

     public void render(SpriteBatch batch) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(grassTexture, i * 80, j * 80);
                if (data[i][j] == 1) {
                    batch.draw(flowerTexture, i * 80, j * 80);
                }
            }
        }
    }
}
