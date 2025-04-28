import character.Character;
import enemy.Enemy;
import java.util.Scanner;

public class Play {
    public static void display() {
        System.err.println("Pilih aksi:");
        System.err.print("1. Serang\n" + "2. Bertahan\n" + "3. Minum Ramuan (sisa: 3)\n" + "4. Keluar\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Character player1 = new Character();
        Enemy enemy = new Enemy();

        System.err.println("Selamat datang di Text RPG!");
        System.out.println("HP Character: " + player1.getHp() + " | " + enemy.getHp());
        display();

        System.out.print("> ");
        int pil = scanner.nextInt();

        // player1.setData("Cloud");
        // System.out.println("Character Baru: ");
        // System.out.println("Nama : " + player1.nama);
        // System.out.println("Level " + player1.getLevel());

        // if (player1.getHp() == 0) {
        // System.out.println(player1.nama + " mati");
        // } else {
        // player1.attack();
        // if (player1.getExp() == 10) {
        // player1.levelUp();
        // }
        // }
    }
}
