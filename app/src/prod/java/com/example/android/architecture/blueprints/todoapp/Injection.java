/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.wxine_mvp.data.source.InfosDataSource;
import com.example.wxine_mvp.data.source.InfosRepository;
import com.example.wxine_mvp.data.source.local.InfosLocalDataSource;
import com.example.wxine_mvp.data.FakeInfosRemoteDataSource;
import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Enables injection of production implementations for
 * {@link InfosDataSource} at compile time.
 */
public class Injection {

    public static InfosRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return InfosRepository.getInstance(InfosRemoteDataSource.getInstance(),
                InfosLocalDataSource.getInstance(context));
    }
}
