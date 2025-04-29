package enemy;

import character.Character;

public class Enemy extends Character {

    @Override
    public void attack(int rand) {
        System.out.println(this.nama + " menyerang balik! Damage: " + rand);
    }

    @Override
    public void resetHP() {
        this.hp = 150;
    }

    // Overloading
    public void attacked(int rand, String attackerName) {
        this.hp -= rand;
        System.out.println("Enemy diserang oleh " + attackerName + " dan kehilangan " + rand + " HP!");
    }

}
