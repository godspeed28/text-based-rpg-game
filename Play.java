import character.Character;
import enemy.Enemy;

import java.util.Random;
import java.util.Scanner;

public class Play {
    public static void display(int potionCount) {
        System.err.println("Pilih aksi:");
        System.err.print(
                "1. Serang\n" + "2. Bertahan\n" + "3. Minum Ramuan (sisa: " + potionCount + ")\n" + "4. Keluar\n");
    }

    public static void main(String[] args) {
        Character player1 = new Character();
        Enemy enemy = new Enemy();
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        int pil;
        String pil2 = "y";
        int potionCount = 3;

        player1.setHp(100);
        enemy.setHp(150);
        player1.setData("Gandalf");
        enemy.setData("Enemy");

        System.err.println("Selamat datang di Text RPG!");
        do {
            int angka = rand.nextInt(100);
            int angkaEnemy = rand.nextInt(100);
            int reduce = rand.nextInt(30);
            int reduceAttack = rand.nextInt(20);

            System.out.println("HP " + player1.getNama() + ": " + player1.getHp() + " | HP " + enemy.getNama() + ": "
                    + enemy.getHp());
            display(potionCount);

            System.out.print("> ");
            pil = scanner.nextInt();
            scanner.nextLine();

            switch (pil) {

                case 1: // Serang
                    enemy.attacked(angka);
                    player1.attack(angka, enemy.getNama());
                    if (enemy.getHp() < 0) {
                        enemy.setHp(0);
                        break;
                    }

                    if (enemy.getHp() > 0) {
                        player1.attacked(angkaEnemy);
                        enemy.attack(angkaEnemy, player1.getNama());
                        if (player1.getHp() < 0) {
                            player1.setHp(0);
                            break;
                        }
                    }
                    break;

                case 2: // Bertahan
                    player1.defense(reduce);
                    if (player1.getHp() < 0) {
                        player1.setHp(0);
                        break;
                    }
                    enemy.attacked(reduceAttack, player1.nama);
                    if (enemy.getHp() < 0) {
                        enemy.setHp(0);
                        break;
                    }

                    break;

                case 3: // Minum Ramuan
                    if (potionCount > 0) {
                        --potionCount;
                        player1.minumRamuan();
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
                System.out.println("\nKamu kalah! HP kamu habis.\n" + "HP Character: " + player1.getHp()
                        + " | HP Enemy: " + enemy.getHp());
                System.out.print("Lanjut bermain? (y/n): ");
                pil2 = scanner.nextLine();
                if (pil2.equals("y")) {
                    player1.resetHP();
                    enemy.resetHP();
                    potionCount = 3;
                }
            }
            if (enemy.getHp() <= 0) {
                System.out.println("\nSelamat! Kamu mengalahkan musuh!\n" + "HP Character: " + player1.getHp()
                        + " | HP Enemy: " + enemy.getHp());
                System.out.print("Lanjut bermain? (y/n): ");
                pil2 = scanner.nextLine();
                if (pil2.equals("y")) {
                    player1.resetHP();
                    enemy.resetHP();
                    potionCount = 3;
                }
            }

        } while (pil != 4 && !pil2.equalsIgnoreCase("n"));

        scanner.close();

        System.err.print("Terima kasih sudah bermain.");
    }
}
