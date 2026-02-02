package me.andrestube.calculatorchat.config.cache;

/**
 * Interface to represent the config caches
 */
public interface Cache {
    void load();

    default void reload() {
        load();
    }
}
