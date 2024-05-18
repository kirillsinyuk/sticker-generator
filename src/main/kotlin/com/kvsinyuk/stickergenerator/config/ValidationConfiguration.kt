package com.kvsinyuk.stickergenerator.config

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ValidationConfiguration {
    @Bean
    fun validationService(validator: Validator) = ValidationService(validator)
}

class ValidationService(private val validator: Validator) {
    fun <T> validate(
        entity: T,
        vararg validationGroups: Class<*>,
    ) {
        val violations = validator.validate(entity, *validationGroups)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(getValidationMessage(violations), violations)
        }
    }

    private fun getValidationMessage(constraintViolations: Set<ConstraintViolation<*>>) =
        constraintViolations
            .map { violation -> violation.propertyPath + ": " + violation.message }
            .joinToString()
}
