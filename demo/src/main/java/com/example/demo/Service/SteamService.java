package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Client.SteamClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class SteamService {

    @Autowired
    private SteamClient steamClient;

    public JsonNode getListaJogos(String chaveApi, String nomeUsuario) throws JsonProcessingException {

        final String STEAM_ID = steamClient.getSteamID(chaveApi, nomeUsuario);

        return steamClient.getJogos(chaveApi, STEAM_ID);
    }

    public JsonNode getInfoJogo(int appId) throws JsonMappingException, JsonProcessingException {
        return steamClient.getInfoJogo(appId);
    }

    public JsonNode getListaDesejo(String chaveApi, String nomeUsuario) throws JsonMappingException, JsonProcessingException {
        final String STEAM_ID = steamClient.getSteamID(chaveApi, nomeUsuario);

        return steamClient.geListaDesejo(STEAM_ID);
    }

    public JsonNode getContasAdicionadas(String chaveApi, String nomeUsuario) throws JsonMappingException, JsonProcessingException {
        // final String STEAM_ID = steamClient.getSteamID(chaveApi, nomeUsuario);
        final String STEAM_ID = "76561197960435530";

        return steamClient.getContasAdicionadas(chaveApi, STEAM_ID);
    }

    public JsonNode getInfoConta(String chaveApi, String nomeUsuario) throws JsonMappingException, JsonProcessingException {

        final String STEAM_ID = steamClient.getSteamID(chaveApi, nomeUsuario);
        return steamClient.getInfoConta(chaveApi, STEAM_ID);
    }
}
