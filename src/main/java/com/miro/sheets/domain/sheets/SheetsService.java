package com.miro.sheets.domain.sheets;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.miro.sheets.utils.sheets.SheetsQuickstart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SheetsService {

    private final SheetsQuickstart quickstart;

    public void get() throws GeneralSecurityException, IOException {

        // 조회 범위
        final String range = "시트!A1:E";

        // 조회
        ValueRange response = quickstart.connSheets().spreadsheets().values()
                .get(quickstart.SHEET_ID, range)
                .execute();

       // 결과
        List<List<Object>> values = response.getValues();
        System.out.println(values);

    }
}


