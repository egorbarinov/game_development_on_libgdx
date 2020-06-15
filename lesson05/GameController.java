package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameController {
    private BattleMap map;
    private ProjectilesController projectilesController;
    private TanksController tanksController;
    private Vector2 tmp;
    private Vector2 selectionStart;  // объявляем вектор для хранения координат при выделении мышкой

    private List<Tank> selectedUnits;

    public TanksController getTanksController() {
        return tanksController;
    }

    public List<Tank> getSelectedUnits() {
        return selectedUnits;
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
        this.tmp = new Vector2();
        this.selectionStart = new Vector2();
        this.selectedUnits = new ArrayList<>();
        this.map = new BattleMap();
        this.projectilesController = new ProjectilesController(this);
        this.tanksController = new TanksController(this);
//        this.tanksController.setup(200, 200, Tank.Owner.PLAYER);
//        this.tanksController.setup(400, 400, Tank.Owner.PLAYER);
        for (int i = 0; i < 5; i++) {
            this.tanksController.setup(MathUtils.random(80, 1200), MathUtils.random(80, 640), Tank.Owner.PLAYER);
<<<<<<< HEAD
        }
<<<<<<< HEAD
        for (int i = 0; i < 2; i++) {
=======
        for (int i = 0; i < 5; i++) {
>>>>>>> lesson05
=======

        for (int i = 0; i < 5; i++) {

>>>>>>> 227fa5d7c8b49fc8b4f651cdb46717085b06553d
            this.tanksController.setup(MathUtils.random(80, 1200), MathUtils.random(80, 640), Tank.Owner.AI);
        }
        prepareInput();
    }

    public void update(float dt) {
        tanksController.update(dt);
        projectilesController.update(dt);
        map.update(dt);
        checkCollisions(dt);
        // checkSelection();
<<<<<<< HEAD
<<<<<<< HEAD
=======
        checkCollisionTarget(); // проверка на попадание
>>>>>>> lesson05
=======

>>>>>>> 227fa5d7c8b49fc8b4f651cdb46717085b06553d
    }

    public void checkCollisions(float dt) {  // метод проверки танков на столкновение
        for (int i = 0; i < tanksController.activeSize() - 1; i++) {
            Tank t1 = tanksController.getActiveList().get(i);
            for (int j = i + 1; j < tanksController.activeSize(); j++) {
                Tank t2 = tanksController.getActiveList().get(j);
                float dst = t1.getPosition().dst(t2.getPosition()); // объявляем расстояние между позициями танков
                if (dst < 30 + 30) {   // если расстояние меньше суммы радиусов двух танков, то случилось столкновение
                    float colLengthD2 = (60 - dst) / 2; // здесь вычисляем расстояние, на которое танк должен откатиться назад
                    // после столкновения. расстояние равно половине "отрицательного" расстояния между позициями танка
                    tmp.set(t2.getPosition()).sub(t1.getPosition()).nor().scl(colLengthD2);  // получаем вектор отката танка 2 от танка 1
                    t2.moveBy(tmp);  //смещаем танк после столкновения обратно (отскок)
                    tmp.scl(-1);  // разворачиваем вектор для перового для формирования отскока первого танка
                    t1.moveBy(tmp);
                }
            }
        }
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======

>>>>>>> 227fa5d7c8b49fc8b4f651cdb46717085b06553d
    public void checkCollisionTarget() { 
        for (int i = 0; i < getProjectilesController().getActiveList().size(); i++) {
            Projectile p =  getProjectilesController().getActiveList().get(i);
            for (int j = 0; j < tanksController.activeSize(); j++) {
                Tank t = tanksController.getActiveList().get(j);
                if (p.getPosition().dst(t.getPosition()) < 32 && t.getOwnerType() == Tank.Owner.AI) {
                    p.deactivate(); // снаряд деактивируется при попадании в танк
                    t.healthLevel(-50); // за 1 попадание показатель здоровья уменьшается на 50%
                }
            }
        }
    }

<<<<<<< HEAD
>>>>>>> lesson05
=======
>>>>>>> 227fa5d7c8b49fc8b4f651cdb46717085b06553d
    public boolean isTankSelected(Tank tank) {  // создаем служебный метод проверки
        return selectedUnits.contains(tank);   // возвращяет булеан, если selectedUnits содержит танк
    }

    public void prepareInput() {    // ОПИСАНИЕ ЛОГИКИ УПРАВЛЕНИЯ ВЫДЕЛЕНИЕМ МЫШКОЙ КАК ГРУППЫ ТАНКОВ, ТАК И ОДИНОЧНЫХ
        InputProcessor ip = new InputAdapter() {  // рисуем рамку для выделения группы танков, InputProcessor - обработчик ввода
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {  // обработчки ввода умеет ожидать нажатие кнопки
                if (button == Input.Buttons.LEFT) {  // если кнопка зажата ...
                    selectionStart.set(screenX, 720 - screenY);  // то фиксируем координаты начала выделения
                }
                return true;  // return true возвращает, что нажатие отработано
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {  // и отжатия кнопки
                if (button == Input.Buttons.LEFT) {
                    tmp.set(Gdx.input.getX(), 720 - Gdx.input.getY());

                    if (tmp.x < selectionStart.x) {   // Если позиция temp.x оказалась меньше позиции стартового выделения (выделяем справа налево)
                        float t = tmp.x;
                        tmp.x = selectionStart.x;
                        selectionStart.x = t;  // то меняем местами эти значения
                    }
                    if (tmp.y > selectionStart.y) {   // тоже делаем и с координатами по y
                        float t = tmp.y;
                        tmp.y = selectionStart.y;
                        selectionStart.y = t;
                    }

                    selectedUnits.clear();  // освобождаем список если выделили пустое место, где нет танков
                    if (Math.abs(tmp.x - selectionStart.x) > 20 & Math.abs(tmp.y - selectionStart.y) > 20) {   // описываем массовое выделение танков
                        for (int i = 0; i < tanksController.getActiveList().size(); i++) {
                            Tank t = tanksController.getActiveList().get(i);
                            if (t.getOwnerType() == Tank.Owner.PLAYER && t.getPosition().x > selectionStart.x && t.getPosition().x < tmp.x
                                    && t.getPosition().y > tmp.y && t.getPosition().y < selectionStart.y
                            ) {
                                selectedUnits.add(t);   // позиции выделяются
                            }
                        }
                    } else {                                                              // иначе срабатывает одиночное выделение танка по клику мыши
                        for (int i = 0; i < tanksController.getActiveList().size(); i++) {  // перебираем цикл по нашему активному листу танков и смотрим
                            Tank t = tanksController.getActiveList().get(i);   // если кординаты танка из листа совпадают с координатами танка клика кнопки мыши
                            if (t.getPosition().dst(tmp) < 30.0f) {  // то добаваляем этот танк в HashSet танков (можно и Аррай лист использовать)
                                selectedUnits.add(t);    // выбран танк или нет определяется условием- лежит ли наш танк в selectedUnits или не лежит
                            }
                        }
                    }
                }
                return true;
            }
        };
        Gdx.input.setInputProcessor(ip);  // Задействуем наше выделение объектов
    }
}
