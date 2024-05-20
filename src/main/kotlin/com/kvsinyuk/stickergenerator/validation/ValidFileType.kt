package com.kvsinyuk.stickergenerator.validation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [ValidFileTypeValidator::class])
annotation class ValidFileType(
    val message: String = "{file.type.invalid}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = [],
)
