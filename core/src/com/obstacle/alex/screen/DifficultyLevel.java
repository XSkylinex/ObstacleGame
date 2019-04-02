package com.obstacle.alex.screen;

import com.obstacle.alex.config.GameConfig;

public enum DifficultyLevel {
    EASY(GameConfig.EASY_ENEMY_SPEED),
    MEDIUM(GameConfig.MEDIUM_ENEMY_SPEED),
    HARD(GameConfig.HARD_ENEMY_SPEED);

    private final float enemySpeed;

    DifficultyLevel(float enemySpeed) {
        this.enemySpeed = enemySpeed;
    }

    public float getEnemySpeed() {
        return enemySpeed;
    }
}
