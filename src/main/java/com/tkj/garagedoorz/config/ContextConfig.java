package com.tkj.garagedoorz.config;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties( DoorzProperties.class )
public class ContextConfig {

    @Bean
    DigitalOutput doorOneActuator( Context context, DoorzProperties properties, String outputProvider ) {

        var doorConfig = DigitalOutput.newConfigBuilder( context )
                .id( properties.doorOne().id() )
                .name( properties.doorOne().name() )
                .address( properties.doorOne().actuatorGpio() )
                .initial( properties.doorOne().initialState() )
                .shutdown( properties.doorOne().shutdownState() )
                .provider( outputProvider );

        return context.create( doorConfig );
    }

    @Bean
    DigitalInput doorOnePosition( Context context, DoorzProperties properties, String inputProvider ) {

        var doorConfig = DigitalInput.newConfigBuilder( context )
                .id( properties.positionOne().id() )
                .name( properties.positionOne().name() )
                .address( properties.positionOne().positionGpio() )
                .pull( properties.positionOne().pullResistance() )
                .debounce( properties.positionOne().debounce(), TimeUnit.MICROSECONDS )
                .provider( inputProvider );

        return context.create( doorConfig );
    }

    @Bean
    DigitalOutput doorTwoActuator( Context context, DoorzProperties properties, String outputProvider ) {

        var doorConfig = DigitalOutput.newConfigBuilder( context )
                .id( properties.doorTwo().id() )
                .name( properties.doorTwo().name() )
                .address( properties.doorTwo().actuatorGpio() )
                .initial( properties.doorTwo().initialState() )
                .shutdown( properties.doorTwo().shutdownState() )
                .provider( outputProvider );

        return context.create( doorConfig );
    }

    @Bean
    DigitalInput doorTwoPosition( Context context, DoorzProperties properties, String inputProvider ) {

        var doorConfig = DigitalInput.newConfigBuilder( context )
                .id( properties.positionTwo().id() )
                .name( properties.positionTwo().name() )
                .address( properties.positionTwo().positionGpio() )
                .pull( properties.positionTwo().pullResistance() )
                .debounce( properties.positionTwo().debounce(), TimeUnit.MICROSECONDS )
                .provider( inputProvider );

        return context.create( doorConfig );
    }

    @Configuration
    @ConditionalOnProperty( prefix = "rpi", name = "enabled", havingValue = "true" )
    static class RpiContextConfiguration {

        @Bean
        String inputProvider() {

            return PiGpioDigitalInputProvider.ID;
        }

        @Bean
        String outputProvider() {

            return PiGpioDigitalOutputProvider.ID;
        }


        @Bean
        Context context() {

            return Pi4J.newAutoContext();
        }

    }

    @Configuration
    static class ShutdownHook {

        @Autowired
        Context rpiContext;

        @PreDestroy
        void shutdown() {

            rpiContext.shutdown();

        }

    }

}
