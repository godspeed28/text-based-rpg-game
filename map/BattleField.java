package map;

import player.Player;
import enemy.Enemy;
import boss.Boss;
import character.Character;

import java.util.Random;

public class BattleField implements BattleFieldInterface {
    private static final int SIZE = 5;
    private static final int ENEMY_COUNT = 1;
    private static final String[] ZONE_SYMBOLS = { "🔥", "💧", "⚡", "🌀", "🪵 " };
    private static final String PLAYER_SYMBOL = "[  P ]";
    private static final String[] ENEMY_SYMBOLS = { "[ E1 ]", "[ E2 ]", "[ E3 ]", "[ E4 ]", "[ E5 ]" };
    private static final String BOSS_SYMBOL = "[  B ]";
    private static final String COLLISION_SYMBOL = "[ ⚔️  ]";

    private final String[][] zoneMap = new String[SIZE][SIZE];
    private final Player player;
    private final Enemy[] enemies;
    private final Boss boss;
    private final Random rand = new Random();

    private int playerX = 2, playerY = 2;
    private final int[] enemyX = new int[ENEMY_COUNT];
    private final int[] enemyY = new int[ENEMY_COUNT];
    private int bossX = 4, bossY = 4;
    private int round = 0;
    private boolean isBossStage = false;
    private boolean bossActive = false;

    public BattleField(Player player, Enemy[] enemies, Boss boss) {
        this.player = player;
        this.enemies = enemies;
        this.boss = boss;
        generateZones();
        initEnemyPositions();
    }

    private void initEnemyPositions() {
        for (int i = 0; i < ENEMY_COUNT; i++) {
            enemyX[i] = rand.nextInt(SIZE);
            enemyY[i] = rand.nextInt(SIZE);
        }
    }

    private void generateZones() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                zoneMap[y][x] = ZONE_SYMBOLS[rand.nextInt(ZONE_SYMBOLS.length)];
            }
        }
    }

    @Override
    public void updateZone() {
        if (++round % 3 == 0) {
            System.out.println("\n⚡ Zona berubah karena badai sihir! ⚡");
            generateZones();
        }
    }

    @Override
    public void movePlayer(String direction) {
        int newX = playerX, newY = playerY;
        switch (direction.toLowerCase()) {
            case "w":
                newY--;
                break;
            case "s":
                newY++;
                break;
            case "a":
                newX--;
                break;
            case "d":
                newX++;
                break;
        }

        if (isValidPosition(newX, newY)) {
            playerX = newX;
            playerY = newY;
        }
    }

    @Override
    public void moveEnemyRandom() {
        for (int i = 0; i < ENEMY_COUNT; i++) {
            if (enemies[i].getHp() <= 0)
                continue;

            int newX = enemyX[i] + rand.nextInt(3) - 1;
            int newY = enemyY[i] + rand.nextInt(3) - 1;
            if (isValidPosition(newX, newY)) {
                enemyX[i] = newX;
                enemyY[i] = newY;
            }
        }
    }

    @Override
    public void moveBossRandom() {
        if (!bossActive)
            return;
        int newX = bossX + rand.nextInt(3) - 1;
        int newY = bossY + rand.nextInt(3) - 1;
        if (isValidPosition(newX, newY)) {
            bossX = newX;
            bossY = newY;
        }
    }

    @Override
    public void applyZoneEffects() {
        applyZoneEffect(playerX, playerY, player, true);
        for (int i = 0; i < ENEMY_COUNT; i++) {
            if (enemies[i].getHp() > 0) {
                applyZoneEffect(enemyX[i], enemyY[i], enemies[i], false);
            }
        }
        if (isBossStage) {
            applyZoneEffect(bossX, bossY, boss, false);
        }
    }

    private void applyZoneEffect(int x, int y, Character character, boolean isPlayer) {
        String zone = zoneMap[y][x];
        String name = character.getNama();

        switch (zone) {
            case "🔥":
                character.setHp(character.getHp() - 20);
                System.out.printf("%s terbakar lava! HP -20\n", name);
                break;
            case "💧":
                character.setHp(character.getHp() + 15);
                System.out.printf("%s mandi di mata air! HP +15\n", name);
                break;
            case "⚡":
                System.out.printf("%s masuk zona Chaos! %s\n", name,
                        isPlayer ? "Seranganmu akan 2x lipat!" : "Serangan musuh bisa meningkat!");
                break;
            case "🌀":
                teleportCharacter(character);
                System.out.printf("%s terkena portal! Terteleportasi.\n", name);
                break;
        }
    }

    private void teleportCharacter(Character character) {
        int x = rand.nextInt(SIZE);
        int y = rand.nextInt(SIZE);
        if (character instanceof Player) {
            playerX = x;
            playerY = y;
        } else if (character instanceof Enemy) {
            for (int i = 0; i < ENEMY_COUNT; i++) {
                if (enemies[i] == character) {
                    enemyX[i] = x;
                    enemyY[i] = y;
                    break;
                }
            }
        } else if (character instanceof Boss) {
            bossX = x;
            bossY = y;
        }
    }

    @Override
    public void displayMap() {
        System.out.println("\n============ ZONA ARENA =============");

        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                boolean isPlayer = (playerX == x && playerY == y);
                boolean isBoss = (bossX == x && bossY == y && isBossStage);
                int enemyIndex = -1;
                for (int i = 0; i < ENEMY_COUNT; i++) {
                    if (enemies[i].getHp() > 0 && enemyX[i] == x && enemyY[i] == y) {
                        enemyIndex = i;
                        break;
                    }
                }
                boolean isEnemy = (enemyIndex != -1);

                if (isPlayer && isEnemy && isBoss) {
                    System.out.print("[!!!]");
                } else if ((isPlayer && isEnemy) || (isPlayer && isBoss) || (isEnemy && isBoss)) {
                    System.out.print(COLLISION_SYMBOL);
                } else if (isPlayer) {
                    System.out.print(PLAYER_SYMBOL);
                } else if (isEnemy) {
                    System.out.print(ENEMY_SYMBOLS[enemyIndex]);
                } else if (isBoss) {
                    System.out.print(BOSS_SYMBOL);
                } else {
                    System.out.printf("[ %s ]", zoneMap[y][x]);
                }
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    public String getCurrentZone(Character character) {
        if (character instanceof Player) {
            return zoneMap[playerY][playerX];
        } else if (character instanceof Enemy) {
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] == character) {
                    return zoneMap[enemyY[i]][enemyX[i]];
                }
            }
        } else if (character instanceof Boss) {
            return zoneMap[bossY][bossX];
        }
        return "";
    }

    @Override
    public boolean isBattleTriggered() {
        for (int i = 0; i < ENEMY_COUNT; i++) {
            if (enemies[i].getHp() > 0 && playerX == enemyX[i] && playerY == enemyY[i])
                return true;
        }
        return isBossStage && playerX == bossX && playerY == bossY;
    }

    public Enemy getCollidingEnemy() {
        for (int i = 0; i < ENEMY_COUNT; i++) {
            if (enemies[i].getHp() > 0 && playerX == enemyX[i] && playerY == enemyY[i])
                return enemies[i];
        }
        return null;
    }

    public Boss getBoss() {
        return boss;
    }

    public boolean allEnemiesDefeated() {
        for (Enemy e : enemies) {
            if (e.getHp() > 0)
                return false;
        }
        return true;
    }

    public boolean isBossActive() {
        return bossActive;
    }

    public void enableBossStage() {
        isBossStage = true;
        bossActive = true;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < SIZE && y < SIZE;
    }

    public void battleAnimation() {
        String[] anim = {
                "⚔️  ", "💥", "🔥", "⚡", "🧨",
                "💨", "✨", "🗡️", "🔪", "🩸",
                "🛡️", "💫", "🌪️", "💣", "💢"
        };
        for (String a : anim) {
            System.out.print("\r" + a + " Bertarung...");
            try {
                Thread.sleep(200); // delay antar simbol
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    @Override
    public void reset() {
        playerX = 2;
        playerY = 2;
        initEnemyPositions();
        bossX = 4;
        bossY = 4;
        round = 0;
        isBossStage = false;
        generateZones();
    }
}