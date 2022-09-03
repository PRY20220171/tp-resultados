package com.example.backresultados.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.internal.core.ssl.DefaultSslEngineFactory;
import com.datastax.oss.protocol.internal.ProtocolV4ClientCodecs;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//@Configuration
//@EnableCassandraRepositories(basePackages = {"com.example.backresultados.repository"})
//public class CassandraConfig extends AbstractReactiveCassandraConfiguration {
//    @Value("${spring.data.cassandra.username}")
//    private String username;
//    @Value("${spring.data.cassandra.password}")
//    private String passPhrase;
//    @Value("${spring.data.cassandra.keyspace-name}")
//    private String keyspace;
//    @Value("${spring.data.cassandra.local-datacenter}")
//    private String datacenter;
//    @Value("${spring.data.cassandra.contact-points}")
//    private String contactPoints;
//    @Value("${spring.data.cassandra.port}")
//    private int    port;
//    @Override
//    protected String getKeyspaceName() {
//        return "pacientes";
//    }
//
//    @Bean
//    @NonNull
//    @Override
//    public CqlSession session() {
//        try {
//            SSLContext context = SSLContext.getDefault();
//            CqlSession cassandraSession = CqlSession.builder()
//                    .withConfigLoader(DriverConfigLoader.fromClasspath("application.conf"))
//                    .withAuthCredentials(username, passPhrase)
//                    .withSslContext(context)
//                    .withKeyspace(keyspace)
//                    .build();
//            return cassandraSession;
//            //CqlSessionFactoryBean session = new CqlSessionFactoryBean();
//            //cassandraSession.setContactPoints(contactPoints);
//            //cassandraSession.setKeyspaceName(keyspace);
//            //cassandraSession.(sslContext);
//            //cassandraSession.setLocalDatacenter(datacenter);
//            //cassandraSession.setPort(port);
//            //cassandraSession.setUsername(username);
//            //cassandraSession.setPassword(passPhrase);
//            //return cassandraSession;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }
//}
@Configuration
@EnableCassandraRepositories(basePackages = {"com.example.backresultados.repository"})
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

            //DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application-prod.properties");
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
                    //.withString(DefaultDriverOption.SSL_ENGINE_FACTORY_CLASS, "DefaultSslEngineFactory")
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

/*
    //DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application-prod.properties");
            return CqlSession.builder()
                    .withConfigLoader(loader)
                    .withKeyspace("pacientes")
                    .build();

 */


/*
            loader =
                    DriverConfigLoader.programmaticBuilder()
                            .withStringList(DefaultDriverOption.CONTACT_POINTS, contactPoints)
                            .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, reqtimeout)
                            .withDuration(DefaultDriverOption.CONNECTION_CONNECT_TIMEOUT, conntimeout)
                            .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, initqrytimeout)
                            //.withString(DefaultDriverOption.AUTH_PROVIDER_CLASS, "PlainTextAuthProvider")
                            //.withString(DefaultDriverOption.AUTH_PROVIDER_USER_NAME, username)
                            //.withString(DefaultDriverOption.AUTH_PROVIDER_PASSWORD, passPhrase)
                            //.withString(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER, datacenter)
                            .withString(DefaultDriverOption.SSL_ENGINE_FACTORY_CLASS, "DefaultSslEngineFactory")
                            .withString(DefaultDriverOption.SSL_TRUSTSTORE_PATH, "./src/main/resources/cassandra_truststore.jks")
                            .withString(DefaultDriverOption.SSL_TRUSTSTORE_PASSWORD, "cassandra")
                            .withBoolean(DefaultDriverOption.SSL_HOSTNAME_VALIDATION, false)
                            .endProfile()
                            .build();
 */