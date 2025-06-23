package player;

import character.Character;

public class Player extends Character {

    public Player(String nama, int hp) {
        super();
        this.setData(nama);
        this.setHp(hp);
    }

    public void minumRamuan() {
        this.hp += 50;
        System.out.println("Kamu minum ramuan dan memulihkan 50 HP.");
    }

    public void defense(int reduce) {
        this.hp -= reduce;
    }

}
