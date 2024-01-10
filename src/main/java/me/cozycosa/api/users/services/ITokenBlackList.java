package me.cozycosa.api.users.services;

public interface ITokenBlackList {
    void addToBlackList(String token);

    boolean isBlacklisted(String token);
}
