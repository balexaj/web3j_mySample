package ru.ether.babichev.mnemonicexample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bitcoinj.crypto.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {

    public List<String> getFiveAddresses(List<String> mnemonics, String password){
        if (mnemonics.size() != 12 || password.length() < 8){
            return null;
        }
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed ds = new DeterministicSeed(mnemonics, null, "", creationTimeSeconds);

        byte[] seedBytes = ds.getSeedBytes();
        try {
//            byte[] mnemonicSeedBytes = MnemonicCode.INSTANCE.toEntropy(mnemonics);
//            ECKeyPair mnemonicKeyPair = ECKeyPair.create(mnemonicSeedBytes);
//            WalletFile walletFileRoot = Wallet.createLight(password, mnemonicKeyPair);
//            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
//            String jsonStr = objectMapper.writeValueAsString(walletFileRoot);
//            WalletFile checkWalletFile = objectMapper.readValue(jsonStr, WalletFile.class);
//            ECKeyPair ecKeyPair = Wallet.decrypt(password, checkWalletFile);
//            byte[] checkMnemonicSeedBytes = Numeric.hexStringToByteArray(ecKeyPair.getPrivateKey().toString(16));
//            List<String> checkMnemonic = MnemonicCode.INSTANCE.toMnemonic(checkMnemonicSeedBytes);


            if (seedBytes == null)
                return null;
            DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(44, true));
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(60, true));
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(0, true));
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(0, false));

            ArrayList<String> addresses = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                DeterministicKey key = HDKeyDerivation.deriveChildKey(dkKey, new ChildNumber(i, false));
                ECKeyPair keyPair = ECKeyPair.create(key.getPrivKeyBytes());
                WalletFile walletFile = Wallet.createLight(password, keyPair);
                addresses.add(walletFile.getAddress());
            }
            return addresses;

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
