package com.flappygame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappygame.game.FlappyDemo;

/**
 * Created by Miruna on 4/18/2017.
 */

public class GameOverState extends State {
    private Texture background;

    public void dispose() {
        background.dispose();

    }

    public GameOverState (GameStateManager gsm) {
        super(gsm);
        background = new Texture("background4.jpg");
        cam.setToOrtho(false, FlappyDemo.WIDTH,FlappyDemo.HEIGHT);
    }
    @Override
    public void handleInput() {
        if(Gdx.input.isTouched())
        {
            gsm.set(new PlayState(gsm));

        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        BitmapFont font=new BitmapFont(Gdx.files.internal("data/text.fnt"),Gdx.files.internal("data/text.png"),false);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,0,0);
        font.setColor(1.0f,1.0f,1.0f,1.0f);
        font.draw(sb,"Game Over",50,700);
        font.draw(sb,"Score: ",50,600);
        font.draw(sb,"HighScore: ",50,500);
        sb.end();

    }
}
