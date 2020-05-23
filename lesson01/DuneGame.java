package com.dune.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DuneGame extends ApplicationAdapter {

	private static class Circle {

		private Texture textureCircle;
		private Vector2 positionCircle; // здесь зашиты x и y и еще математека


		public Circle(float x, float y) {
			this.positionCircle = new Vector2(x, y);
			this.textureCircle = new Texture("circle.png");
		}

		public void update () {
			positionCircle.x = (float)Math.random() * 1100;
			positionCircle.y = (float)Math.random() * 650;
		}

		public void render (SpriteBatch batch) {
			batch.draw(textureCircle, positionCircle.x, positionCircle.y);
		}

		public void dispose () {
			textureCircle.dispose();
		}
	}

	private static class Tank {

		private Vector2 position; // здесь зашиты x и y и еще математека
		private Texture texture;
		private float scale; // масштаб
		private float angle; // угол
		private float speed;

		public Tank(float x, float y) {

			this.position = new Vector2(x, y);
			this.texture = new Texture("tank.png");

			this.scale = 1.0f;
			this.angle = 0.0f;
			this.speed = 200.0f;
		}
		public void setPosition(float dt) {
			position.x += speed * MathUtils.cosDeg(angle) * dt;
			position.y += speed * MathUtils.sinDeg(angle) * dt;
		}

		public void update(float dt) {
//			x += 300 * dt; // скорость
//			if (x > 1280) {
//				x = -100;
//			}
//			angle += 30.0f * dt; // вращение
//			scale+= dt * 0.3f; // увеличение
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { // проверяем, нажата ли кнопка влево
				angle += 180.0f * dt; // за секунду бот повернется на 180 градусов
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { // проверяем, нажата ли кнопка вправо
				angle -= 180.0f * dt; // за секунду бот повернется на 180 градусов
			}
			if (position.x < 1280) {
				if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
					setPosition(dt);
				}
			} else {
				position.x = 1200;

			}

			if (position.x > 0) {
				if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
					setPosition(dt);

				}
			} else {
				position.x = 80;
			}

			if (position.y < 720) {
				if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
					setPosition(dt);
				}
			} else position.y = 640;

			if (position.y > 0) {
				if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
					setPosition(dt);
				}
			} else position.y = 80;
		}

		public void render(SpriteBatch batch) {
			batch.draw(texture, position.x - 40, position.y - 40, 40, 40, 80, 80, scale, scale, angle, 0, 0, 80, 80, false, false);
		}

		public void dispose () {
			texture.dispose();
		}
	}

	private SpriteBatch batch;
	private Tank tank;
	private Circle circle;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tank = new Tank(200,200);
		circle = new Circle((float)Math.random()*1100,(float)Math.random()*650);

	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0, 0.4f,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		tank.render(batch);
		circle.render(batch);
		batch.end();
	}
	public void update (float dt) {
		tank.update(dt);
		if (Math.abs(tank.position.x-circle.positionCircle.x) < 5) {
			circle.update();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		tank.dispose();
		circle.dispose();
	}

}
