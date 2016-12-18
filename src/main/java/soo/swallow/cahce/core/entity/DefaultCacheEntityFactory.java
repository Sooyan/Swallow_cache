/*
 * Copyright 2015 Soo [154014022@qq.com | sootracker@gmail.com]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package soo.swallow.cahce.core.entity;

import soo.swallow.cahce.Platform;

/**
 * @author Soo.
 */
public class DefaultCacheEntityFactory extends CacheEntityFactory {
    private static final String TAG = "DefaultCacheEntityFactory--->";

    private static final long DEFAULT_MAX_SIZE = 1;
    private static final long DEFAULT_MAX_PERIOD = 1;

    public DefaultCacheEntityFactory(Platform platform) {
        super(platform, DEFAULT_MAX_SIZE, DEFAULT_MAX_PERIOD);
    }

    @Override
    protected <V> CacheEntity<V> onCreate() {
        return new CacheEntity<>(getMaxPeriodTime());
    }
}
