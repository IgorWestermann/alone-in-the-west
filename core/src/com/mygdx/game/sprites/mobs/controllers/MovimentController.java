/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.mobs.controllers;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.sprites.*;
import com.mygdx.game.sprites.mobs.*;

/**
 *
 * @author Hugo
 */
public interface MovimentController {
    public void move(Mob mob , float dt);
}
