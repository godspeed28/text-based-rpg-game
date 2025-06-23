package character;

import java.util.Random;

public abstract class Character {
    protected String nama;
    protected int hp;
    protected int level;
    protected int xp;

    public Character() {
        this.level = 1;
        this.xp = 0;
        this.hp = 100;
    }

    public void addXp(int amount) {
        this.xp += amount;
    }

    public void calculateLevel() {
        int[] thresholds = { 0, 100, 300, 600, 1000 };

        for (int i = 1; i < thresholds.length; i++) {
            if (this.xp >= thresholds[i]) {
                this.level = i + 1;
                this.hp += 100;
            } else {
                break;
            }
        }
    }

    public void setData(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return this.nama;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLevel() {
        return this.level;
    }

    public int getXp() {
        return this.xp;
    }

    public void resetHP() {
        this.hp = 100;
    }

    public void resetXp() {
        this.xp = 0;
    }

    public void resetLevel() {
        this.level = 1;
    }

    // OVERLOADING
    public void attacked(int rand) {
        this.hp -= rand;
    }

    public void attacked(int rand, String attackerName) {
        this.hp -= rand;
        System.out.println("Enemy diserang oleh " + attackerName + " dan kehilangan " + rand + " HP!");
    }

    public void attack(int rand, String targetNama) {
        System.out.println(this.nama + " menyerang " + targetNama + "! Damage: " + rand);
    }
}
