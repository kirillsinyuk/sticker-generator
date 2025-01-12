package com.kvsinyuk.stickergenerator.adapter.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

@Component
class ValidFileTypeValidator : ConstraintValidator<ValidFileType, String> {
    private val supportedTypes = setOf("jpg", "jpeg", "png", "webp")

    override fun isValid(
        fileName: String,
        context: ConstraintValidatorContext,
    ) = fileName.substringAfterLast('.', "") in supportedTypes
}
