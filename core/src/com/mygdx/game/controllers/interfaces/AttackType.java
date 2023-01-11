
package com.mygdx.game.controllers.interfaces;

import com.mygdx.game.constants.*;

public interface AttackType {
    
    public void act(float dt);
    public void attack(State state , Direction direction);
    public void setCooldown(float cooldown);
    public void setDelay(float delay);
    public boolean isAttacking();


    
}
