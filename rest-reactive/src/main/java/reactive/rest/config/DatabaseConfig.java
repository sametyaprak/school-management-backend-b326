package reactive.rest.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;


/**
 * Configuration class responsible for setting up the database connection and populating the database during initialization.
 */
@Configuration
public class DatabaseConfig {

    /**
     * Creates a ConnectionFactoryInitializer object with the provided ConnectionFactory and initializes the database by populating it with data from "init.sql".
     *
     * @param connectionFactory the ConnectionFactory to be set on the ConnectionFactoryInitializer
     * @return the initialized ConnectionFactoryInitializer object
     */
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("init.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

}
