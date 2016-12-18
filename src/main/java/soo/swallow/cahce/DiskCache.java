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

package soo.swallow.cahce;

import soo.swallow.cahce.core.CleanCache;
import soo.swallow.cahce.core.entity.CacheEntityFactory;

/**
 * @author Soo.
 */
public abstract class DiskCache<K, V> extends CleanCache<K, V> {
    private static final String TAG = "DiskCache--->";

    public DiskCache(CacheEntityFactory cacheEntityFactory) {
        super(cacheEntityFactory);
    }
}
