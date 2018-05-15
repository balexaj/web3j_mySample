package ru.ether.babichev.mnemonicexample.model;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

public class HDWallet {

    DeterministicKey dkKey;

    public HDWallet(DeterministicKey dkKey) {
        this.dkKey = dkKey;
    }

    public String getAddress(int n){
        DeterministicKey key = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(n, false));
        ECKeyPair keyPair = ECKeyPair.create(key.getPrivKeyBytes());
        return Keys.getAddress(keyPair);
    }
}
