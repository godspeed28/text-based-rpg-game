package boss;

import character.Character;

public class Boss extends Character {
    public Boss(String nama) {
        super();
        this.setData(nama);
        this.addXp(1000);
        this.calculateLevel();
    }

    @Override
    public void attack(int rand, String targetNama) {
        int bonusDamage = rand + 60; // Boss damage lebih besar
        System.out.println(
                this.nama + " menyerang " + targetNama + " dengan kekuatan luar biasa! Damage: " + bonusDamage);
    }

}
