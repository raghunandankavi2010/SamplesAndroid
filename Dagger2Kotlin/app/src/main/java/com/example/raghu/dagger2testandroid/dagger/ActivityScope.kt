package com.example.raghu.dagger2testandroid.dagger

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by raghu.
 */
@Scope
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
annotation class ActivityScope
