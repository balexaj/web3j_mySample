package ru.ether.babichev.mnemonicexample.service;

import org.bitcoinj.crypto.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import ru.ether.babichev.mnemonicexample.model.HDWallet;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WalletService {

    public HDWallet getHDWallet(List<String> mnemonics){
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed ds = new DeterministicSeed(mnemonics, null, "", creationTimeSeconds);
        byte[] seedBytes = ds.getSeedBytes();
        try {
            if (seedBytes == null)
                return null;

            DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(44, true));
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(60, true));
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(0, true));
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(0, false));

            return new HDWallet(dkKey);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger getBalance(String address){
        Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/6rfE9FJUqXyHUPjXooDn"));
        EthGetBalance ethGetBalance = null;
        try {
            ethGetBalance = web3
                    .ethGetBalance("0x"+address, DefaultBlockParameterName.LATEST)
                    .sendAsync()
                    .get();
            return ethGetBalance.getBalance();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
