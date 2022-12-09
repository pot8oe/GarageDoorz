package com.tkj.garagedoorz.config.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnRaspberryPiPlatformCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome( ConditionContext context, AnnotatedTypeMetadata metadata ) {

        return null;
    }

}
