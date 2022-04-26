package io.github.patlego.cm.ping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.patlego.cm.ping.models.CMInstance;

public class CMPing implements Callable<CMInstance> {

    private CMInstance instance;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CMPing(CMInstance instance) {
        this.instance = instance;
    }

    @Override
    public CMInstance call() throws Exception {
        logger.info(String.format("Checking to see if %s needs to be invoked", this.instance.getUrl()));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastInvocation = this.getLastInvocation();

        if (now.isAfter(lastInvocation.plusSeconds(this.instance.getInterval()))) {
            logger.info(String.format("About to invoke %s since last invocation has elapsed", this.instance.getUrl()));

            if (this.instance.hasAuth()) {
                this.authenticatedRequest();
            } else {
                this.unAuthenticatedRequest();
            }
            lastInvocation = now;

        } else {
            logger.info(String.format("No need to invoke since lastInvocation has not elapsed necessary amount of time",
                    this.instance.getUrl()));
        }

        // Need to set this incase it is null and so it is set to now that way going
        // forward it will check against the previous now and not right now.
        this.instance.setLastInvocation(this.localDateTimeToString(lastInvocation));
        logger.info(String.format("Completed Cloud Manager ping for %s", this.instance.getUrl()));
        return this.instance;
    }

    public LocalDateTime getLastInvocation() {
        if (null == this.instance.getLastInvocation()) {
            return LocalDateTime.now();
        } else {
            return LocalDateTime.parse(this.instance.getLastInvocation(), formatter);
        }
    }

    public String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(this.formatter);
    }

    public void authenticatedRequest() throws URISyntaxException, IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.instance.getAuth().getUsername(),
                this.instance.getAuth().getPassword()));

        try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider)
                .build()) {

            String url = buildUrl();
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(get);

            if (response.getStatusLine().getStatusCode() / 100 == 2) {
                logger.info(String.format("Received a %d when invoking the %s",
                        response.getStatusLine().getStatusCode(), url));
            } else {
                logger.warn(String.format("Received a %d when invoking the %s",
                        response.getStatusLine().getStatusCode(), url));
            }
        }
    }

    public void unAuthenticatedRequest() throws URISyntaxException, IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            String url = buildUrl();

            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(get);

            if (response.getStatusLine().getStatusCode() / 100 == 2) {
                logger.info(String.format("Received a %d when invoking the %s",
                        response.getStatusLine().getStatusCode(), url));
            } else {
                logger.warn(String.format("Received a %d when invoking the %s",
                        response.getStatusLine().getStatusCode(), url));
            }
        }
    }

    public String buildUrl() throws URISyntaxException {
        if (this.instance.getUrl().endsWith("/")) {
            this.instance.setUrl(this.instance.getUrl().substring(0, this.instance.getUrl().length() - 1));
        }

        if (!this.instance.getPath().startsWith("/")) {
            this.instance.setPath("/" + this.instance.getPath());
        }

        String url = String.format("%s%s", this.instance.getUrl(), this.instance.getPath());
        // Add a cache buster
        URIBuilder builder = new URIBuilder(url);
        builder.addParameter("uuid", UUID.randomUUID().toString());

        return builder.build().toString();
    }

}
