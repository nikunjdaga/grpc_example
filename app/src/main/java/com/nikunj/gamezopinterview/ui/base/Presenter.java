package com.nikunj.gamezopinterview.ui.base;

import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by nikunj on 3/27/18.
 */

public abstract class Presenter<T extends Presenter.View> {

    private WeakReference<T> view;

    protected AtomicBoolean isViewAlive = new AtomicBoolean();

    public T getView() {
        return view.get();
    }

    public void setView(T view) {
        this.view = new WeakReference<>(view);
    }

    public void initialize(Bundle extras) {
    }

    public void start() {
        isViewAlive.set(true);
    }

    public void finalizeView() {
        isViewAlive.set(false);
    }

    public interface View {
    }
}
