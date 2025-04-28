package enemy;

import character.Character;

public class Enemy extends Character {

    @Override
    public void attack() {
        System.out.println(this.nama + " menyerang musuh dengan ganas!");
        expUp();
    }

    @Override
    public void attacked() {
        this.hp -= 2; // Enemy menerima damage lebih besar saat diserang
        System.out.println(this.nama + " diserang dan kehilangan 2 HP!");
    }

    // Bisa juga tambah behavior khusus untuk Enemy
    public void taunt() {
        System.out.println(this.nama + " mengejek lawan dengan provokasi!");
    }
}
