package ru.ether.babichev.mnemonicexample.model;

import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ababichev on 18.05.2018.
 */
public class TransferRequest {
    @NotBlank
    private List<String> mnemonics;
    @NonNull
    private int numberAccount;
    @NotBlank
    private String payeeAddress;
    @NotBlank
    private BigDecimal countETH;

    private BigDecimal priceGas;

    public List<String> getMnemonics() {
        return mnemonics;
    }

    public void setMnemonics(List<String> mnemonics) {
        this.mnemonics = mnemonics;
    }

    public int getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(int numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public BigDecimal getCountETH() {
        return countETH;
    }

    public void setCountETH(BigDecimal countETH) {
        this.countETH = countETH;
    }

    public BigDecimal getPriceGas() {
        return priceGas;
    }

    public void setPriceGas(BigDecimal priceGas) {
        this.priceGas = priceGas;
    }
}
