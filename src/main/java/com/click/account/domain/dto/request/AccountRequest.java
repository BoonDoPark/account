package com.click.account.domain.dto.request;

import com.click.account.domain.entity.Account;

import java.util.UUID;

public record AccountRequest(
        String account,
        String accountPassword,
        String accountName
) {
    public Account toEntity(
            UUID userId,
            Long accountDailyLimit,
            Long accountOneTimeLimit

    ) {
        return Account.builder()
                .account(account)
                .userId(userId)
                .accountPassword(accountPassword)
                .accountName(accountName)
                .accountDailyLimit(accountDailyLimit)
                .accountOneTimeLimit(accountOneTimeLimit)
                .accountDisable(true)
                .build();
    }
}
