/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.controllers.interfaces;

import com.mygdx.game.constants.*;

/**
 *
 * @author Hugo
 */
public interface AttackType {
    
    public void act(float dt);
    public void attack(State state , Direction direction);
    public void setCooldown(float cooldown);
    public void setDelay(float delay);
    public boolean isAttacking();


    
}
