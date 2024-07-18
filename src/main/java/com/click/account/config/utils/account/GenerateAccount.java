package com.click.account.config.utils.account;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class GenerateAccount {

    public String generateAccount() {
        String account = RandomStringUtils.random(9, 48, 57, false, true);
        return "416" + account;
    }
}
