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
import soo.swallow.cahce.core.entity.CacheEntity;

/**
 * @author Soo.
 */
public abstract class CacheEntityFactory {

    private static final String TAG = "CacheEntityFactory--->";

    private Platform platform;
    private long maxSize;
    private long maxPeriodTime;

    public CacheEntityFactory(Platform platform, long maxSize, long maxPeriodTime) {
        this.platform = platform;
        this.maxSize = maxSize;
        this.maxPeriodTime = maxPeriodTime;
    }

    public final <V> CacheEntity<V> obtain(long destSize) {
        if (destSize > maxSize) {
            return null;
        }
        return onCreate();
    }

    protected abstract <V> CacheEntity<V> onCreate();

    public void recycle(CacheEntity<?> cacheEntity) {
//        TODO
    }

    public Platform getPlatform() {
        return platform;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public long getMaxPeriodTime() {
        return maxPeriodTime;
    }
}
