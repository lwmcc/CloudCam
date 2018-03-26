package com.mccarty.cloudcam.utils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Module
public class ErrorMessageString {
    @Provides
    public String message() {
        return "SHOWING THIS MESSAGE";
    }
}
