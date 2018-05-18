package ru.ether.babichev.mnemonicexample.service;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import ru.ether.babichev.mnemonicexample.model.HDWallet;
import ru.ether.babichev.mnemonicexample.model.TransferRequest;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WalletService {

    private Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/6rfE9FJUqXyHUPjXooDn"));

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

    public String transferETH(TransferRequest request, HDWallet wallet){

        Credentials credentials = Credentials.create(wallet.getKeyPair());
        try {
            TransactionReceipt transactionReceipt = Transfer.sendFunds(web3, credentials, request.getPayeeAddress(),
                    request.getCountETH(), Convert.Unit.ETHER).send();
            return transactionReceipt.getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
