package com.example.demo.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SteamClient {

    @Autowired
    private ObjectMapper objectMapper;

    // private final WebClient webClient = WebClient.create();
    private final RestTemplate restTemplate = new RestTemplate();

    public SteamClient() {

    }

    public String getSteamID(String chaveApi, String nomeUsuario) throws JsonMappingException, JsonProcessingException {

        final String urlModelo = String.format(
                "http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key=%s&vanityurl=%s", chaveApi,
                nomeUsuario);

        String json = restTemplate.getForObject(urlModelo, String.class);
        JsonNode root = objectMapper.readTree(json);

        JsonNode responseNode = root.path("response");

        String steamID = responseNode.path("steamid").asText();

        return steamID;
    }

    public JsonNode getJogos(String chaveApi, String steamId) throws JsonMappingException, JsonProcessingException {

        // final String urlRequest = String.format(
        //         "https://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=%s&steamid=%s&format=json",
        //         chaveApi, steamId);
        final String urlRequest = String.format("https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/?key=%s&steamid=%s&include_appinfo=true", chaveApi, steamId);

        String json = restTemplate.getForObject(urlRequest, String.class);
        JsonNode root = objectMapper.readTree(json);

        JsonNode responseNode = root.path("response");
        JsonNode listaJogos = responseNode.path("games");

        return listaJogos;
    }

    public JsonNode getInfoJogo(int appId) throws JsonMappingException, JsonProcessingException {
        final String urlRequest = String.format("https://store.steampowered.com/api/appdetails?appids=%d", appId);

        String json = restTemplate.getForObject(urlRequest, String.class);
        JsonNode root = objectMapper.readTree(json);

        JsonNode appNode = root.path(String.valueOf(appId));
        JsonNode dataNode = appNode.path("data");

        return dataNode;
    }

    public JsonNode getContasAdicionadas(String chaveApi, String steamId) throws JsonMappingException, JsonProcessingException {
        final String urlRequest = String.format("https://api.steampowered.com/ISteamUser/GetFriendList/v0001/?key=%s&steamid=%s&relationship=all", chaveApi, steamId);

        String json = restTemplate.getForObject(urlRequest, String.class);

        JsonNode root = objectMapper.readTree(json);

        JsonNode friendslistNode = root.path("friendslist");
        JsonNode friendsNode = friendslistNode.path("friends");

        return friendsNode;
    }

    public JsonNode geListaDesejo(String steamId) throws JsonMappingException, JsonProcessingException {
        final String urlRequest = String.format("https://api.steampowered.com/IWishlistService/GetWishlist/v1/?steamid=%s", steamId);

        String json = restTemplate.getForObject(urlRequest, String.class);

        JsonNode root = objectMapper.readTree(json);

        JsonNode response = root.path("response");
        JsonNode listaDesejos = response.path("items");

        return listaDesejos;
    }

    public JsonNode getInfoConta(String chaveApi, String steamID) throws JsonMappingException, JsonProcessingException {

        final String urlRequest = String.format("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=%s&steamids=%s", chaveApi, steamID);

        String json = restTemplate.getForObject(urlRequest, String.class);

        JsonNode root = objectMapper.readTree(json);

        JsonNode infoConta = root.path("response").path("players");

        return infoConta;
    }

}
