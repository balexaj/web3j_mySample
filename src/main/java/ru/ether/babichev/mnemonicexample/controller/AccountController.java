package ru.ether.babichev.mnemonicexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ether.babichev.mnemonicexample.model.AccountRequest;
import ru.ether.babichev.mnemonicexample.model.AccountResponce;
import ru.ether.babichev.mnemonicexample.model.HDWallet;
import ru.ether.babichev.mnemonicexample.service.WalletService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "/get_accounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AccountResponce> getAccounts(@RequestBody AccountRequest request){
        HDWallet wallet = walletService.getHDWallet(request.getMnemonics());
        List<AccountResponce> responce = new ArrayList<>();
        for (int i = 0; i < request.getCountAccounts(); i++) {
            String address = wallet.getAddress(i);
            responce.add(new AccountResponce(address, walletService.getBalance(address)));
        }
        return responce;
    }
}
