package ru.ether.babichev.mnemonicexample.model;

import java.util.List;

public class MnemonicModel {

    private List<String> mnemonics;
    private String password;

    public List<String> getMnemonics() {
        return mnemonics;
    }

    public void setMnemonics(List<String> mnemonics) {
        this.mnemonics = mnemonics;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
