package character;

public class Character {
    public String nama;
    int level = 1;
    protected int hp = 100;
    int exp = 0;

    public void setData(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return this.nama;
    }

    public void attack() {
        System.out.println(this.nama + " menyerang lawan");
        expUp();
    }

    public int getHp() {
        return this.hp;
    }

    public int setHp(hp) {
        return this.hp;
    }

    public void attacked() {
        this.hp -= 1;
    }

    public void minumRamuan() {
        this.hp += 1;
    }

    public void levelUp() {
        this.level += 1;
    }

    public void expUp() {
        this.exp += 1;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExp() {
        return this.exp;
    }
}
