package ru.ether.babichev.mnemonicexample.model;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

public class HDWallet {

    private DeterministicKey dkKey;
    private ECKeyPair keyPair;
    private String address;

    public HDWallet(DeterministicKey dkKey) {
        this.dkKey = dkKey;
    }

    public HDWallet getChildWallet(int n){
        DeterministicKey key = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(n, false));
        ECKeyPair keyPair = ECKeyPair.create(key.getPrivKeyBytes());
        String address = Keys.getAddress(keyPair);
        HDWallet wallet = new HDWallet(key, keyPair, address);
        return wallet;
    }

    public HDWallet(DeterministicKey dkKey, ECKeyPair keyPair, String address) {
        this.dkKey = dkKey;
        this.keyPair = keyPair;
        this.address = address;
    }

    public DeterministicKey getDkKey() {
        return dkKey;
    }

    public void setDkKey(DeterministicKey dkKey) {
        this.dkKey = dkKey;
    }

    public ECKeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(ECKeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
