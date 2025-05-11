package character;

public class Character {
    public String nama;
    protected int hp;

    public void setData(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return this.nama;
    }

    public void attack(int rand, String nama) {
        System.out.println(this.nama + " menyerang " + nama + "! Damage: " + rand);
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void resetHP() {
        this.hp = 100;
    }

    public void attacked(int rand) {
        this.hp -= rand;
    }

    public void attacked(int rand, String attackerName) {
        this.hp -= rand;
        System.out.println("Enemy diserang oleh " + attackerName + " dan kehilangan " + rand + " HP!");
    }

    public void minumRamuan() {
        this.hp += 50;
        System.out.println("Kamu minum ramuan dan memulihkan " + 50 + " HP.");
    }

    public void defense(int reduce) {
        this.hp -= reduce;
        System.out.println("Kamu bertahan. Damage Enemy berkurang menjadi " + reduce + ".");
    }
}
