package com.dune.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dune.game.core.Assets;

public class MenuScreen extends AbstractScreen {

    private Stage menuStage;
    private BitmapFont font72;
    public MenuScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        font72 = Assets.getInstance().getAssetManager().get("fonts/font72.ttf");
        createGuiMenu();
    }

    private void createGuiMenu() {
        menuStage = new Stage();
        Gdx.input.setInputProcessor(menuStage);
        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        BitmapFont font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        TextButton.TextButtonStyle menuBtnStyle = new TextButton.TextButtonStyle(
                skin.getDrawable("simpleButton"),null,null,font24);
        TextButton btnNewGame = new TextButton("New Game", menuBtnStyle);
        btnNewGame.setPosition(480,320);
        TextButton btnExitGame = new TextButton("Exit Game", menuBtnStyle);
        btnExitGame.setPosition(480,220);
        btnNewGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
            }
        });

        btnExitGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menuStage.addActor(btnNewGame);
        menuStage.addActor(btnExitGame);
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font72.draw(batch, "Dune Game",0 ,500,1280, Align.center,false);
        batch.end();
        menuStage.draw();
    }

    public void update(float dt) {
//        if (Gdx.input.justTouched()) {
//            ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
//        }
        menuStage.act(dt);
    }

    @Override
    public void dispose() {
    }
}