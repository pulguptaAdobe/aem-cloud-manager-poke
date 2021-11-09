package io.github.patlego.cm.ping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.Callable;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.patlego.cm.ping.models.CMInstance;

public class CMPing implements Callable<CMInstance> {

    private CMInstance instance;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpGet get = new HttpGet(this.instance.getUrl());
                CloseableHttpResponse response = httpclient.execute(get);

                if (response.getStatusLine().getStatusCode() / 100 == 2) {
                    logger.info(String.format("Received a %d when invoking the %s",
                            response.getStatusLine().getStatusCode(), this.instance.getUrl()));
                } else {
                    logger.warn(String.format("Received a %d when invoking the %s",
                            response.getStatusLine().getStatusCode(), this.instance.getUrl()));
                }
            }
            lastInvocation = now;
            
        } else {
            logger.info(String.format("No need to invoke since lastInvocation has not elapsed necessary amount of time",
                    this.instance.getUrl()));
        }
        
        // Need to set this incase it is null and so it is set to now that way going forward it will check against the previous now and not right now.
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

}
