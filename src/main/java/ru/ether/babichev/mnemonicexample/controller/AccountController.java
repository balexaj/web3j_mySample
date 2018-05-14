package ru.ether.babichev.mnemonicexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ether.babichev.mnemonicexample.model.MnemonicModel;
import ru.ether.babichev.mnemonicexample.service.WalletService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "/get_accounts",
        method = RequestMethod.POST,
//        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getFiveAccount(@RequestBody MnemonicModel payload){
        return walletService.getFiveAddresses(payload.getMnemonics(), payload.getPassword());
    }
}
