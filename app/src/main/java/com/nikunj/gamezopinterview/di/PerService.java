package com.nikunj.gamezopinterview.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by nikunj on 3/27/18.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}