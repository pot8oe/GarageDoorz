package com.tkj.garagedoorz.adapter.out;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.plugin.mock.platform.MockPlatform;
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogInputProvider;
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogOutputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInput;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalOutputProvider;
import com.pi4j.plugin.mock.provider.i2c.MockI2CProvider;
import com.pi4j.plugin.mock.provider.pwm.MockPwmProvider;
import com.pi4j.plugin.mock.provider.serial.MockSerialProvider;
import com.pi4j.plugin.mock.provider.spi.MockSpiProvider;
import com.tkj.garagedoorz.config.ContextConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest( classes = RpiAdapterTests.TestConfiguration.class )
public class RpiAdapterTests {

    @Autowired
    RpiAdapter subject;

    @Autowired
    Context context;

    @Autowired
    DigitalInput doorOnePosition;

    @Autowired
    DigitalInput doorTwoPosition;

    @Autowired
    DigitalOutput doorOneActuator;

    @Autowired
    DigitalOutput doorTwoActuator;

    @Test
    void whenDefaultState_verifyDoorIsOpen() {

        var actual = this.subject.isDoorOpen( this.doorOnePosition.id() );
        assertThat( actual ).isTrue();

    }

    @Test
    void whenStateLow_verifyDoorIsOpen() {

        var position = this.context.io( this.doorOnePosition.id(), MockDigitalInput.class );
        position.mockState( DigitalState.LOW );

        var actual = this.subject.isDoorOpen( this.doorOnePosition.id() );
        assertThat( actual ).isTrue();

    }

    @Test
    void whenStateHigh_verifyDoorIsClosed() {

        var position = this.context.io( this.doorOnePosition.id(), MockDigitalInput.class );
        position.mockState( DigitalState.HIGH );

        var actual = this.subject.isDoorOpen( this.doorOnePosition.id() );
        assertThat( actual ).isFalse();

    }

    @Test
    void whenDefaultState_verifyStatusIsOpen() {

        var actual = this.subject.status();

        var expected =
                Map.of(
                        this.doorOnePosition.name(), true,
                        this.doorTwoPosition.name(), true
                );

        assertThat( actual ).isEqualTo( expected );

    }

    @Test
    void whenDoorOneIsHigh_verifyStatusIsClosedAndOpen() {

        var position = this.context.io( this.doorOnePosition.id(), MockDigitalInput.class );
        position.mockState( DigitalState.HIGH );

        var actual = this.subject.status();

        var expected =
                Map.of(
                        this.doorOnePosition.name(), false,
                        this.doorTwoPosition.name(), true
                );

        assertThat( actual ).isEqualTo( expected );

    }

    @SpringBootConfiguration
    @Import({ContextConfig.class, RpiAdapter.class})
    static class TestConfiguration {

        @Bean
        String inputProvider() {

            return MockDigitalInputProvider.ID;
        }

        @Bean
        String outputProvider() {

            return MockDigitalOutputProvider.ID;
        }

        @Bean
        Context context() {

            return Pi4J.newContextBuilder()
                    .add( new MockPlatform() )
                    .add(
                            MockAnalogInputProvider.newInstance(),
                            MockAnalogOutputProvider.newInstance(),
                            MockSpiProvider.newInstance(),
                            MockPwmProvider.newInstance(),
                            MockSerialProvider.newInstance(),
                            MockI2CProvider.newInstance(),
                            MockDigitalInputProvider.newInstance(),
                            MockDigitalOutputProvider.newInstance()
                    )
                    .build();
        }

    }

}
