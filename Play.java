import character.Character;
import enemy.Enemy;
import java.util.Scanner;

public class Play {
    public static void display(int potionCount) {
        System.err.println("Pilih aksi:");
        System.err.print(
                "1. Serang\n" + "2. Bertahan\n" + "3. Minum Ramuan (sisa: " + potionCount + ")\n" + "4. Keluar\n");
    }

    public static void newGame() {

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pil;
        String pil2 = "y";
        int potionCount = 3; // jumlah ramuan
        Character player1 = new Character();
        Enemy enemy = new Enemy();
        enemy.setHp(150);

        System.err.println("Selamat datang di Text RPG!");
        do {

            System.out.println("HP Character: " + player1.getHp() + " | HP Enemy: " + enemy.getHp());
            display(potionCount);

            System.out.print("> ");
            pil = scanner.nextInt();
            scanner.nextLine();

            switch (pil) {
                case 1: // Serang
                    int damageToEnemy = 20; // contoh damage ke musuh
                    int damageToPlayer = 15; // contoh damage balasan
                    enemy.setHp(enemy.getHp() - damageToEnemy);
                    if (enemy.getHp() < 0) {
                        enemy.setHp(0);
                        break;
                    }
                    System.out.println("Kamu menyerang musuh dan memberikan " + damageToEnemy + " damage!");

                    if (enemy.getHp() > 0) {
                        player1.setHp(player1.getHp() - damageToPlayer);
                        if (player1.getHp() < 0) {
                            player1.setHp(0);
                            break;
                        }
                        System.out.println("Musuh membalas serangan! Kamu terkena " + damageToPlayer + " damage.");
                    }
                    break;

                case 2: // Bertahan
                    int reducedDamage = 5; // damage berkurang karena bertahan
                    player1.setHp(player1.getHp() - reducedDamage);
                    if (player1.getHp() < 0) {
                        player1.setHp(0);
                        break;
                    }
                    System.out.println("Kamu bertahan. Damage musuh berkurang menjadi " + reducedDamage + ".");
                    break;

                case 3: // Minum Ramuan
                    if (potionCount > 0) {
                        // HP yang dipulihkan
                        player1.minumRamuan();
                        potionCount--;
                        System.out.println(
                                "Kamu minum ramuan dan memulihkan " + 50 + " HP. Sisa ramuan: " + potionCount);
                    } else {
                        System.out.println("Ramuan habis!");
                    }
                    break;

                case 4: // Keluar
                    System.out.println("Keluar dari permainan...");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
                    break;
            }

            // Cek kondisi menang atau kalah
            if (player1.getHp() <= 0) {
                System.out.println("\nKamu kalah! HP kamu habis.");
                System.out.print("Lanjut bermain? (y/n): ");
                pil2 = scanner.nextLine();
                if (pil2.equals("y")) {
                    player1.resetHP();
                    enemy.setHp(150);
                    potionCount = 3;
                }
            }
            if (enemy.getHp() <= 0) {
                System.out.println("\nSelamat! Kamu mengalahkan musuh!");
                System.out.print("Lanjut bermain? (y/n): ");
                pil2 = scanner.nextLine();
                if (pil2.equals("y")) {
                    player1.resetHP();
                    enemy.setHp(150);
                    potionCount = 3;
                }
            }

        } while (pil != 4 && !pil2.equalsIgnoreCase("n"));

        System.err.print("Terima kasih sudah bermain.");
    }
}
