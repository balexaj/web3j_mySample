package ru.ether.babichev.mnemonicexample.model;

import java.util.List;

public class AccountRequest {
    private List<String> mnemonics;
    private int countAccounts;

    public List<String> getMnemonics() {
        return mnemonics;
    }

    public void setMnemonics(List<String> mnemonics) {
        this.mnemonics = mnemonics;
    }

    public int getCountAccounts() {
        return countAccounts;
    }

    public void setCountAccounts(int countAccounts) {
        this.countAccounts = countAccounts;
    }
}
