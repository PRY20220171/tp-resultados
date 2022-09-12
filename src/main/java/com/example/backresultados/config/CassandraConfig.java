package com.example.backresultados.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.internal.core.ssl.DefaultSslEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableCassandraRepositories(basePackages = {"com.example.backresultados.repository"})
@Profile("!test")
public class CassandraConfig {
    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnProperty(value="${spring.data.cassandra.ssl}", havingValue="true")
    public CqlSession session() {
        //variables obligatorias:
        Duration reqtimeout = DurationStyle.detectAndParse(Objects.requireNonNull(env.getProperty("spring.data.cassandra.request.timeout")));
        Duration conntimeout = DurationStyle.detectAndParse(Objects.requireNonNull(env.getProperty("spring.data.cassandra.connection.connect-timeout")));
        Duration initqrytimeout = DurationStyle.detectAndParse(Objects.requireNonNull(env.getProperty("spring.data.cassandra.connection.init-query-timeout")));
        String username = Objects.requireNonNull(env.getProperty("spring.data.cassandra.username"));
        String passPhrase = Objects.requireNonNull(env.getProperty("spring.data.cassandra.password"));
        String keyspace = Objects.requireNonNull(env.getProperty("spring.data.cassandra.keyspace-name"));
        String datacenter = Objects.requireNonNull(env.getProperty("spring.data.cassandra.local-datacenter"));
        //Esta variable se podría cambiar para redundancia activa, para poder conectarse a través de otros clusters de cassandra a la red:
        List<String> contactPoints = Collections.singletonList(Objects.requireNonNull(env.getProperty("spring.data.cassandra.contact-points")));
        System.out.println(contactPoints);
        DriverConfigLoader loader;
        boolean ssl = Objects.equals(env.getProperty("cassandra.ssl"), "true") ;
        if(!ssl) {
            loader = DriverConfigLoader.programmaticBuilder()
                    .withStringList(DefaultDriverOption.CONTACT_POINTS, contactPoints)
                    .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, reqtimeout)
                    .withDuration(DefaultDriverOption.CONNECTION_CONNECT_TIMEOUT, conntimeout)
                    .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, initqrytimeout)
                    .withClass(DefaultDriverOption.PROTOCOL_VERSION, ProtocolVersion.V4.getClass())
                    .build();
        } else {
            loader = DriverConfigLoader.programmaticBuilder()
                    .withStringList(DefaultDriverOption.CONTACT_POINTS, contactPoints)
                    .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, reqtimeout)
                    .withDuration(DefaultDriverOption.CONNECTION_CONNECT_TIMEOUT, conntimeout)
                    .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, initqrytimeout)
                    .withClass(DefaultDriverOption.SSL_ENGINE_FACTORY_CLASS, DefaultSslEngineFactory.class)
                    .withString(DefaultDriverOption.SSL_TRUSTSTORE_PATH, "./src/main/resources/cassandra_truststore.jks")
                    .withString(DefaultDriverOption.SSL_TRUSTSTORE_PASSWORD, "cassandra")
                    .withBoolean(DefaultDriverOption.SSL_HOSTNAME_VALIDATION, false)
                    .endProfile()
                    .build();
        }
        return CqlSession.builder()
                .withAuthCredentials(username,passPhrase)
                .withLocalDatacenter(datacenter)
                .withConfigLoader(loader)
                .withKeyspace(keyspace)
                .build();
    }
}