package com.miro.sheets.domain.sheets;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequestMapping("/sheets")
@RestController
@RequiredArgsConstructor
public class SheetsController {

    private final SheetsService sheetsService;

    @GetMapping("/get")
    public void get() throws GeneralSecurityException, IOException {

        sheetsService.get();

    }

}
