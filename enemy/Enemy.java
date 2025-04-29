package enemy;

import character.Character;

public class Enemy extends Character {

    @Override
    public void attack(int rand) {
        System.out.println(this.nama + " menyerang balik! Damage: " + rand);
    }
}
