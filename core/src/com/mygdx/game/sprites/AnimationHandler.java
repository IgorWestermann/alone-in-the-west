/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Map;
import com.mygdx.game.constants.*;

/**
 *
 * @author Hugo
 */
//essa classo coleciona as animações de um mesmo atlas e as separa por um map de sheets
//cada map dessas sheets é separado então por ação dentro da sheet
//tambem é passado um dicionario que pega uma direção e traduz na sheet que ela esta presente
//por fim dentro dessa sheet, um novo dicionario faz a tradução de uma acção pra animação correspodente
public class AnimationHandler {

    private TextureAtlas textures;
    private Map<String, Map<Integer, Animation>> sheets;
    private final float animDuration = 0.1f;
    private float timer;

    private Map<Direction, Pair<String, Boolean>> directionDictionary;
    private Direction lockedDirecion;
    private State lockedState;
    private boolean animationLock = false;

    public void setDirectionDictionary(Map<Direction, Pair<String, Boolean>> directionDictionary) {
        this.directionDictionary = directionDictionary;
    }

    public void setStateDictionary(Map<State, Integer> stateDictionary) {
        this.stateDictionary = stateDictionary;
    }
    private Map<State, Integer> stateDictionary;

    private State lastState;
    private Direction lastDirection;

    private boolean currentAnimationFinished;

    public boolean isCurrentAnimationFinished() {
        return currentAnimationFinished;
    }

    public AnimationHandler() {
        timer = 0;
        sheets = new HashMap<>();
        currentAnimationFinished = false;
    }

    public void buildAnimationsBySpriteList(String rootPath, String[] spriteNames, int frameW, int frameH, int[] framesPerAction, Animation.PlayMode[] modes) {

        Array<TextureRegion> frames = new Array<>();
        for (String spriteName : spriteNames) {

            Texture texture = new Texture(rootPath + spriteName + ".png");
            TextureRegion tr = new TextureRegion(texture);

            //varre cada ação
            for (int i = 0; i < framesPerAction.length; i++) {
                //varre o numero de frames por ação
                for (int j = 0; j < framesPerAction[i]; j++) {
                    frames.add(new TextureRegion(tr, j * frameW, i * frameH, frameW, frameH));
                }

                if (!sheets.containsKey(spriteName)) {
                    sheets.put(spriteName, new HashMap<Integer, Animation>());
                }

                sheets.get(spriteName).put(i, new Animation(animDuration, frames, modes[i]));
                frames.clear();
            }
        }

    }

    public void buildAnimationsBySheet(String path, String sheetName, int frameW, int frameH, int[] framesPerAction, Animation.PlayMode[] modes) {

        Texture texture = new Texture(path);
        TextureRegion textureRegion = new TextureRegion(texture);
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < framesPerAction.length; i++) {
            for (int j = 0; j < framesPerAction[i]; j++) {
                frames.add(new TextureRegion(textureRegion, j * frameW, i * frameH, frameW, frameH));
            }
            if (!sheets.containsKey(sheetName)) {
                sheets.put(sheetName, new HashMap<Integer, Animation>());
            }
            sheets.get(sheetName).put(i, new Animation(animDuration, frames, modes[i]));
            frames.clear();
        }
    }

    public TextureRegion getKeyFrame(State state, Direction direction, float dt) {

        if (animationLock) {
            state = lockedState;
            direction = lockedDirecion;
            timer += dt;

        } else {
            if (state != lastState || direction != lastDirection) {
                timer = 0;
                lastState = state;
                lastDirection = direction;
            } else {
                timer += dt;
            }
        }

        String sheet = directionDictionary.get(direction).first;
        boolean isFlip = directionDictionary.get(direction).second;

        int stateIndex = stateDictionary.get(state);

        TextureRegion frame = (TextureRegion) sheets.get(sheet).get(stateIndex).getKeyFrame(timer);
        currentAnimationFinished = sheets.get(sheet).get(stateIndex).isAnimationFinished(timer);

        if (isFlip) {
            if (!frame.isFlipX()) {
                frame.flip(true, false);
            }
        } else {
            if (frame.isFlipX()) {
                frame.flip(true, false);
            }
        }

        waitAnimationUnlock();

        return frame;
    }
    public void waitAnimationUnlock(){
        if (animationLock && currentAnimationFinished) {
            //System.out.println("Animation Unlocked");
            lockedDirecion = null;
            lockedState = null;
            animationLock = false;
            this.timer = 0;
        }
    }

    public void setAnimationLock(State s, Direction d) {
        if (!animationLock && lockedState == null && lockedDirecion == null) {
            //System.out.println("Animation Locked");
            this.timer = 0;
            this.lockedState = s;
            this.lockedDirecion = d;
            this.animationLock = true;
            this.currentAnimationFinished = false;
        }
    }

}
