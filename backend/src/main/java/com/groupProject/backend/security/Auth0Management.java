package com.groupProject.backend.security;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Setter;
import lombok.var;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Setter
class TokenResponseType
{
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("expires_in")
    public String expiresIn;
    @SerializedName("token_type")
    public String tokenType;
}

@Service
public class Auth0Management
{
    private final String domain;
    private final String clientId;
    private final String audience;
    private final String clientSecret;
    private OkHttpClient client;

    public Auth0Management()
    {
        Properties prop = new Properties();
        try
        {
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        domain = prop.getProperty("auth0.domain");
        clientId = prop.getProperty("auth0.clientId");
        audience = prop.getProperty("auth0.audience");
        clientSecret = prop.getProperty("auth0.clientSecret");
        client = new OkHttpClient();
    }

    public boolean deleteUser(String authId)
    {
        try
        {
            String token = requestToken();

            MediaType type = MediaType.parse("text/plain");

            RequestBody body = RequestBody.create("", type);
            Request request = new Request.Builder()
                    .url("https://"+ domain + "/api/v2/users/" + authId)
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .method("DELETE", body)
                    .build();

            try(Response response = client.newCall(request).execute())
            {
                if(response.code() != 204)
                    return false;
            }

            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean updateUserEmail(String authId, String email)
    {
        try
        {
            String token = requestToken();

            MediaType type = MediaType.parse("application/json");

            RequestBody body = RequestBody.create("{\"email\":\"" + email + "\"}", type);
            Request request = new Request.Builder()
                    .url("https://"+ domain + "/api/v2/users/" + authId)
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("Content-Type", "application/json")
                    .method("PATCH", body)
                    .build();

            try(Response response = client.newCall(request).execute())
            {
                if(response.code() != 200)
                    return false;
            }

            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    private String requestToken() throws IOException
    {
        try
        {
            String bodyStr = "grant_type=" +
                    "client_credentials" +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret + "" +
                    "&audience=" + audience;

            MediaType type = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(bodyStr, type);

            Request request = new Request.Builder()
                    .url("https://"+ domain + "/oauth/token")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            Gson gson = new Gson();
            var responseBody = response.body().string();
            TokenResponseType responseType = gson.fromJson(responseBody, TokenResponseType.class);

            return responseType.accessToken;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return "";
    }
}
