package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private Vector2 position;
    private Vector2 velocity;
    private TextureRegion texture;
    private boolean setVisible;

    public Vector2 getPosition() {
        return position;
    }

    public Projectile(TextureAtlas atlas) {
        this.texture = atlas.findRegion("bullet");
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.setVisible = false;

    }

    public void setup(Vector2 startPosition, float angle) {
        position.set(startPosition);
        velocity.set(200.0f * MathUtils.cosDeg(angle), 200.0f * MathUtils.sinDeg(angle));
        setVisible = true;
    }

    public void update(float dt) {
        if (!setVisible) return;
        position.mulAdd(velocity, dt);
        if (position.x > 1280 | position.x < 0 | position.y > 720 | position.y < 0)
            setVisible = false;
    }

    public boolean isSetVisible() {
        return setVisible;
    }

    public void render(SpriteBatch batch) {
        if (!setVisible) return;
        batch.draw(texture, position.x, position.y);
    }
}
