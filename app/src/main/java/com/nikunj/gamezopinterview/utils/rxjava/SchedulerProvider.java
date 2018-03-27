package com.nikunj.gamezopinterview.utils.rxjava;

import io.reactivex.Scheduler;

/**
 * Created by nikunj on 3/27/18.
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
