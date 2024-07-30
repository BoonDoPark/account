package com.click.account.domain.dto.request.account;

public record WithdrawRequest(
    String bhName,
    Long bhAmount,
    String myAccount,
    String yourAccount,
    String bhStatus,
    Long bhBalance,
    Integer bhOutType,
    Long cardId,
    Long categoryId
) {
    public static WithdrawRequest toTransfer(AccountMoneyRequest req, String bhName, Long bhBalance) {
        return new WithdrawRequest(
            bhName,
            req.moneyAmount(),
            req.account(),
            req.transferAccount(),
            "출금",
            bhBalance,
            1,
            null,
            req.category()
        );
    }
}
