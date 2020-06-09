package com.dune.game.core.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.*;

public class Harvester extends AbstractUnit {

    private boolean isMoved;

    public Harvester(GameController gc) {
        super(gc);
        this.textures = Assets.getInstance().getAtlas().findRegion("tankcore").split(64, 64)[0];
        this.weaponTexture = Assets.getInstance().getAtlas().findRegion("harvester");
        this.containerCapacity = 10;
        this.minDstToActiveTarget = 5.0f;
        this.speed = 120.0f;
        this.weapon = new Weapon(4.0f, 1);
        this.hpMax = 500;
        this.unitType = UnitType.HARVESTER;
        this.isMoved = false;
    }

    @Override
    public void setup(Owner ownerType, float x, float y) {
        this.position.set(x, y);
        this.ownerType = ownerType;
        this.hp = this.hpMax;
        this.destination = new Vector2(position);
    }

    @Override
    public void commandMoveToBase() { // по наполнению контейнера перемещаем харвестер на базу
        if (container == containerCapacity) {
            destination.set(200, 200);
        }
        if (getPosition().dst(200,200) <= 3.0f) {
            isMoved = true;
        }
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void unloading() {
       if(isMoved()) {
            while (container != 0) {
                container -= 1;
                gc.getPlayerLogic().setMoney(gc.getPlayerLogic().getMoney() + 20);
            }
            isMoved = false;
       }
    }

    public void updateWeapon(float dt) {
        if (target == null) {
            weapon.setAngle(rotateTo(weapon.getAngle(), angle, 180.0f, dt));
        }
        if (gc.getMap().getResourceCount(position) > 0) {
            int result = weapon.use(dt);
            if (result > -1) {
                container += gc.getMap().harvestResource(position, result);
                if (container > containerCapacity) {
                    container = containerCapacity;
                }
            }
        } else {
            weapon.reset();
        }
    }

    @Override
    public void commandAttack(Targetable target) {
        commandMoveTo(target.getPosition());
    }

    @Override
    public void renderGui(SpriteBatch batch) {
        super.renderGui(batch);

        if (weapon.getUsageTimePercentage() > 0.0f) {
            batch.setColor(0.2f, 0.2f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 32, position.y + 22, 64, 8);
            batch.setColor(1.0f, 1.0f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 30, position.y + 24, 60 * weapon.getUsageTimePercentage(), 4);

            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (container== containerCapacity) {  // меняем цвет по заполнению контейнера
            batch.setColor(1f, 0.7f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 32, position.y + 22, 64, 8);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
