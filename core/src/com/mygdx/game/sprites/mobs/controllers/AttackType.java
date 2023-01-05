/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.mobs.controllers;

import com.mygdx.game.screens.EntityHandler;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.mobs.Mob;

/**
 *
 * @author Hugo
 */
public interface AttackType {
    
    public void act(float dt);
    public void setCooldown(float cooldown);
    public void setDelay(float delay);
    public boolean isAttacking();


    
}
