package me.cozycosa.api.users.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlackList implements ITokenBlackList {
    private Set<String> blackList = new HashSet<>();

    @Override
    public void addToBlackList(String token) {
        blackList.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blackList.contains(token);
    }
}
