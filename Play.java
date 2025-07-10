import enemy.Enemy;
import player.Player;
import boss.Boss;
import map.BattleField;
import java.util.Random;
import java.util.Scanner;

public class Play {

    public static void displayActionMenu(int potionCount) {
        System.out.println("\nPilih aksi:");
        System.out.println("1. Serang");
        System.out.println("2. Bertahan");
        System.out.println("3. Minum Ramuan (sisa: " + potionCount + ")");
        System.out.println("4. Keluar");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Masukan nama anda : ");
        String name = scanner.nextLine();

        Player player = new Player(name, 100);
        Enemy[] enemies = new Enemy[5];
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy("Enemy" + (i + 1), 150);
        }

        Boss boss = new Boss("Boss");
        BattleField map = new BattleField(player, enemies, boss);

        boolean playAgain;

        do {
            int potionCount = 3;
            int stopMessage = 1;
            boolean gameOver = false;

            System.out.println("🎮 Selamat datang " + name + " di Text RPG Arena!");
            System.out.println("Bergeraklah sampai kamu bertemu musuh...");

            while (!gameOver) {

                if (map.allEnemiesDefeated() && map.isBossActive()) {
                    map.enableBossStage();
                    if (stopMessage == 1)
                        System.out.println("\n💀 Semua musuh dikalahkan! BOS MUNCUL! 💀");
                    stopMessage++;
                }
                map.updateZone();
                map.displayMap();

                // Gerakan terlebih dahulu
                System.out.print("\n➡️  Gerak ke mana? (w/a/s/d): ");
                String direction = scanner.nextLine().toLowerCase();

                if (!direction.equals("w") && !direction.equals("a") &&
                        !direction.equals("s") && !direction.equals("d")) {
                    System.out.println("❌ Input tidak valid! Gunakan w/a/s/d.");
                    continue;
                }

                map.moveEnemyRandom();
                map.moveBossRandom();
                map.movePlayer(direction);

                map.displayMap();

                System.out.println("\n== LOG ==");
                map.applyZoneEffects();

                // Status
                System.out.println("\n== STATUS ==");
                System.out.println(player.getNama() + " HP: " + player.getHp() + " | Level: " + player.getLevel()
                        + " | EXP: " + player.getXp());
                for (Enemy e : enemies) {
                    if (e.getHp() > 0) {
                        System.out.println(e.getNama() + " HP: " + e.getHp());
                    }
                }
                if (map.allEnemiesDefeated()) {
                    System.out.println(boss.getNama() + " HP: " + boss.getHp() + " | Level: " + boss.getLevel()
                            + " | EXP: " + boss.getXp());
                }

                // Jika battle terjadi, tampilkan menu aksi
                if (map.isBattleTriggered()) {
                    System.out.println("\n⚔️  Kamu bertemu musuh!");
                    boolean inBattle = true;

                    while (inBattle && !gameOver) {
                        if (!map.allEnemiesDefeated()) {
                            var enemy = map.getCollidingEnemy();
                            System.out
                                    .println(player.getNama() + " HP: " + player.getHp() + " | " + enemy.getNama()
                                            + " HP: "
                                            + enemy.getHp());
                        } else {
                            System.out
                                    .println(player.getNama() + " HP: " + player.getHp() + " | " + boss.getNama()
                                            + " HP: "
                                            + boss.getHp());
                        }

                        displayActionMenu(potionCount);
                        System.out.print("> ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        int damage = rand.nextInt(30) + 10;
                        int enemyDamage = rand.nextInt(20) + 5;
                        int reduced = rand.nextInt(15) + 5;

                        switch (choice) {
                            case 1: // Serang
                                if (!map.allEnemiesDefeated()) {
                                    if (map.getCurrentZone(player).equals("⚡")) {
                                        damage *= 2;
                                        enemyDamage *= 2;
                                        System.out.println("⚡ Efek Chaos! Seranganmu meningkat menjadi " + damage);
                                    }

                                    var enemy = map.getCollidingEnemy();
                                    if (enemy != null && enemy.getHp() > 0) {
                                        enemy.attacked(damage);
                                        player.attack(damage, enemy.getNama());
                                        if (enemy.getHp() > 0) {
                                            player.attacked(enemyDamage);
                                            enemy.attack(enemyDamage, player.getNama());
                                        } else {
                                            System.out.println("💀 " + enemy.getNama() + " dikalahkan!");
                                            player.addXp(100);
                                            player.calculateLevel();
                                            inBattle = false;
                                        }

                                        if (map.allEnemiesDefeated()) {
                                            map.enableBossStage();
                                            inBattle = false;
                                        }
                                    } else {
                                        System.out.println("Musuh sudah kalah.");
                                        inBattle = false;
                                    }
                                } else {
                                    if (map.getCurrentZone(player).equals("⚡")) {
                                        damage *= 2;
                                        enemyDamage *= 2;
                                        System.out.println(
                                                "⚡ Efek Chaos! Seranganmu meningkat menjadi " + damage);
                                    }
                                    map.battleAnimation();
                                    Boss bossTarget = map.getBoss();
                                    bossTarget.attacked(damage);
                                    player.attack(damage, bossTarget.getNama());

                                    if (bossTarget.getHp() > 0) {
                                        player.attacked(enemyDamage + 60);
                                        bossTarget.attack(enemyDamage, player.getNama());
                                    } else {
                                        System.out.println("🎉 Kamu mengalahkan BOSS dan menang!");
                                        gameOver = true;
                                        inBattle = false;
                                    }
                                }
                                break;

                            case 2: // Bertahan
                                player.defense(reduced);
                                System.out.println(player.getNama() + " bertahan. HP berkurang " + reduced);
                                break;

                            case 3: // Minum Ramuan
                                if (potionCount > 0) {
                                    potionCount--;
                                    player.minumRamuan();
                                } else {
                                    System.out.println("Ramuan habis!");
                                }
                                break;

                            case 4: // Keluar
                                System.out.println("Keluar dari permainan.");
                                gameOver = true;
                                inBattle = false;
                                break;

                            default:
                                System.out.println("Pilihan tidak valid.");
                        }

                        if (player.getHp() <= 0) {
                            System.out.println("\n💀 Kamu kalah. Game over.");
                            gameOver = true;
                            break;
                        }
                    }
                } else {
                    System.out.println("\n❗ Belum bertemu musuh, teruslah bergerak...");
                    if (player.getHp() <= 0) {
                        System.out.println("\n💀 Kamu kalah. Game over.");
                        gameOver = true;
                    }
                }
            }

            System.out.print("Main lagi? (y/n): ");
            String ulang = scanner.nextLine();
            playAgain = ulang.equalsIgnoreCase("y");
            if (playAgain) {
                player.resetHP();
                player.resetLevel();
                player.resetXp();
                boss.setHp(500);
                ;
                for (int i = 0; i < enemies.length; i++) {
                    enemies[i].resetHP();
                }
                map.reset();
            } else {
                System.out.println("Sampai jumpa!");
            }

        } while (playAgain);

        scanner.close();
    }
}
