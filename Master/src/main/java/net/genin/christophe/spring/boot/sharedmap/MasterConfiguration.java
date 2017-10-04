package net.genin.christophe.spring.boot.sharedmap;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Collections.singletonList;

@Configuration
public class MasterConfiguration {

    @Bean
    public CallClient.Cli clientRest() {
        Decoder  decoder = new GsonDecoder();
        return Feign.builder()
                .decoder(decoder)
                .errorDecoder(new CallClient.CliDecoder(decoder))
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(CallClient.Cli.class, "http://localhost:8082");
    }

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();

        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true).setMembers(singletonList("127.0.0.1"));



        return config;
    }

}
