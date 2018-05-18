package ru.ether.babichev.mnemonicexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ether.babichev.mnemonicexample.model.AccountRequest;
import ru.ether.babichev.mnemonicexample.model.AccountResponce;
import ru.ether.babichev.mnemonicexample.model.HDWallet;
import ru.ether.babichev.mnemonicexample.model.TransferRequest;
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
            HDWallet walletN = wallet.getChildWallet(i);
            responce.add(new AccountResponce(walletN.getAddress(), walletService.getBalance(walletN.getAddress())));
        }
        return responce;
    }

    @RequestMapping(value = "/transfer_ether")
    @ResponseBody
    public String transferEther(@RequestBody TransferRequest request){
        HDWallet wallet = walletService.getHDWallet(request.getMnemonics());
        HDWallet childWallet = wallet.getChildWallet(request.getNumberAccount());
        return walletService.transferETH(request, childWallet);
    }
}
