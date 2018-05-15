package ru.ether.babichev.mnemonicexample.model;

import java.math.BigInteger;

public class AccountResponce {
    private String account;
    private BigInteger balance;

    public AccountResponce(String account, BigInteger balance) {
        this.account = account;
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }
}
