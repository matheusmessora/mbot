package br.matheusmessora.mbot.discord;

import br.matheusmessora.mbot.games.arena1v1.Arena1v1Service;
import br.matheusmessora.mbot.games.greetings.GreetingsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

import javax.annotation.PostConstruct;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@Configuration
public class DiscordBoot {

    @Autowired
    private Arena1v1Service arena1v1Service;

    @Autowired
    private DiscordServer discordServer;

    @Autowired
    private GreetingsListener greetingsListener;

    @Autowired
    private DiscordMessageEventListener discordListener;

    @PostConstruct
    public void init() throws Exception {
        ignoreCerts();

        final EventDispatcher dispatcher = discordServer.client().getDispatcher();
        dispatcher.registerListener(discordListener);
        dispatcher.registerListener(greetingsListener);
    }

    @Bean
    public IDiscordClient discordClient(){
        final IDiscordClient client = createClient("MzE5MjE0OTY1MDI0MjI3MzI5.DA9rvg.egKa8Mi5Skb7-Q5_5GHSyvpu7qg", true);
        return client;
    }

    private void ignoreCerts() throws Exception {
        /* Start of Fix */
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }

        } };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) { return true; }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /* End of the fix*/
    }

    public IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the br.matheusmessora.mbot.discord.Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        clientBuilder.set5xxRetryCount(10);
//        clientBuilder.withShards(5);
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}