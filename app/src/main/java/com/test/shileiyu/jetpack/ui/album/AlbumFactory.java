package com.test.shileiyu.jetpack.ui.album;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * @author shilei.yu
 * @date 2018/8/9
 */

public class AlbumFactory extends ViewModelProvider.AndroidViewModelFactory {
    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link AndroidViewModel}
     */
    public AlbumFactory(@NonNull Application application) {
        super(application);
    }
}
