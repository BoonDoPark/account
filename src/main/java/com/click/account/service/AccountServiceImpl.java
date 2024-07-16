package com.click.account.service;

import com.click.account.config.constants.TransferLimit;
import com.click.account.config.utils.GenerateAccount;
import com.click.account.config.utils.GroupCode;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;


    @Override
    public void saveAccount(UUID userId, AccountRequest req) {
        String account = GenerateAccount.generateAccount();

        // 중복된 계좌가 있는지 확인 필요
        accountRepository.findByAccount(account)
                .filter(byAccount -> byAccount.getAccount().equals(account))
                .ifPresent(byAccount -> {
                    throw new IllegalArgumentException("이미 있는 계좌입니다.");
                });

        if (req.status().equals("account"))
            accountRepository.save(req.toEntity(GenerateAccount.generateAccount(), userId, TransferLimit.getDailyLimit(), TransferLimit.getOnetimeLimit(), true));
        if (req.status().equals("group"))
            accountRepository.save(
                    req.toGroupEntity(
                        account,
                        userId,
                        TransferLimit.getDailyLimit(),
                        TransferLimit.getOnetimeLimit(),
                        GroupCode.getGroupCode(),
                        true
                    )
            );
    }
    @Override
    public List<Account> findByUserId(UUID userId) {
        return accountRepository.findByUserId(userId);
    }
    @Override
    public List<Account> findDisabledAccountByUserId(UUID userId) {
        return accountRepository.findByUserIdAndAccountDisable(userId, true);
    }

    @Override
    public List<String> findGroupAccountCodeByUserIdAndAccount(UUID userId, String account) {
        return accountRepository.findByUserIdAndAccountAndAccountDisable(userId, account,true)
                .stream()
                .map(Account::getGroupAccountCode)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId, String account) {
        Account delete = accountRepository.findOptionalByUserIdAndAccount(userId, account).orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(false);
    }



}

