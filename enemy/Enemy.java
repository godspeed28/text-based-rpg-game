package enemy;

import java.util.Random;

import character.Character;

public class Enemy extends Character {
    public Enemy(String nama, int hp) {
        super();
        this.setData(nama);
        this.setHp(hp);
    }

    @Override
    public void attack(int rand, String nama) {
        System.out.println(this.nama + " menyerang balik " + nama + "! Damage: " + rand);
    }

    @Override
    public void resetHP() {
        this.hp = 150;
    }

}
