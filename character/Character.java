package character;

public class Character {
    public String nama;
    protected int hp = 100;

    public void setData(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return this.nama;
    }

    public void attack(int rand) {
        System.out.println(this.nama + " menyerang Enemy! Damage: " + rand);

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

    public void minumRamuan(int potionCount) {
        this.hp += 50;
        System.out.println("Kamu minum ramuan dan memulihkan " + 50 + " HP. Sisa ramuan: " + potionCount);
    }

    public void defense() {
        this.hp -= 15;
        System.out.println("Kamu bertahan. Damage musuh berkurang menjadi " + 15 + ".");
    }
}
