package com.miro.sheets.utils.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

@Component
public class SheetsQuickstart {

    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    public static final String SHEET_ID = "1Fjz49_MNcKjDVRHppsbFp6g-wG8DqIoGwYpWq-V50hs";

    public Sheets service;

    private NetHttpTransport HTTP_TRANSPORT;

    {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = connSheets();
        } catch (Throwable t) {
            System.out.println("## googleHttpTransportInit Throwable: " + t);
            System.exit(1);
        }
    }

    public Credential getCredentials() throws IOException {
        // Load client secrets.
        String CREDENTIALS_FILE_PATH = "/credentials.json";
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT
                ,JSON_FACTORY
                ,GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in))
                ,Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();


        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public Sheets connSheets() throws IOException {

        String APPLICATION_NAME = "   ";
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
