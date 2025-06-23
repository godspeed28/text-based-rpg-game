package map;

public interface BattleFieldInterface {
    void movePlayer(String direction);

    void moveEnemyRandom();

    void moveBossRandom();

    void displayMap();

    void updateZone();

    void applyZoneEffects();

    boolean isBattleTriggered();

    void reset();
}
