package enemy;

import character.Character;

public class Enemy extends Character {

    @Override
    public void attack(int rand, String nama) {
        System.out.println(this.nama + " menyerang balik " + nama + "! Damage: " + rand);
    }

    @Override
    public void resetHP() {
        this.hp = 150;
    }    
}
