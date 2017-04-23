package com.flappygame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flappygame.game.FlappyDemo;
import com.flappygame.game.sprites.Cactus;
import com.flappygame.game.sprites.Cat;

import java.awt.Graphics;


/**
 * Created by Miruna on 4/8/2017.
 */

public class PlayState extends State {
    private static final int CACTUS_SPACING=100;
    private static final int CACTUS_COUNT=7;
    private  Cat cat;
    private Texture bg;
    private static int score=0;

    private Array<Cactus> cactuses;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        cat=new Cat(50,100);
        cam.setToOrtho(false, FlappyDemo.WIDTH,FlappyDemo.HEIGHT);
        bg= new Texture("background4.jpg");
        cactuses = new Array<Cactus>();

        for(int i=1;i<CACTUS_COUNT;i++){
            cactuses.add(new Cactus(i*(CACTUS_SPACING + Cactus.CACTUS_WIDTH)));
        }

    }


    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            cat.jump(); }

    @Override
    public void update(float dt)   {
        handleInput();
        cat.update(dt);
        cam.position.x = cat.getPosition().x+120;

        for (Cactus cactus : cactuses) {
            if (cam.position.x - cam.viewportWidth / 2 > cactus.getPosTopCactus().x + cactus.getTopCactus().getWidth())
                cactus.reposition(cactus.getPosTopCactus().x + (Cactus.CACTUS_WIDTH + CACTUS_SPACING) * CACTUS_COUNT);

            if (cactus.collides(cat.getBounds()))
                {
                    gsm.set(new GameOverState(gsm));
            score=0;}
            if (cat.getPosition().x >= cactus.getPosTopCactus().x+56 && cat.getPosition().x < cactus.getPosBotCactus().x+57)
                score++;



        cam.update();}
    }

    @Override
    public void render(SpriteBatch sb) {
        BitmapFont font=new BitmapFont(Gdx.files.internal("data/text.fnt"),Gdx.files.internal("data/text.png"),false);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg,cam.position.x - cam.viewportWidth/2 ,0);
        sb.draw(cat.getTexture(), cat.getPosition().x,cat.getPosition().y);
        for(Cactus cactus:cactuses){
        sb.draw(cactus.getTopCactus(),cactus.getPosTopCactus().x, cactus.getPosTopCactus().y);
        sb.draw(cactus.getBottomCactus(),cactus.getPosBotCactus().x, cactus.getPosBotCactus().y);}
        font.setColor(1.0f,1.0f,1.0f,1.0f);
        font.draw(sb," "+score,cam.position.x-cam.viewportWidth/2,780);
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        cat.dispose();
        for(Cactus cactus:cactuses)
            cactus.dispose();

    }
    public int getScore()
    {
        return score;
    }

}