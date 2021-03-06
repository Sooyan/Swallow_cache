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

import java.lang.ref.WeakReference;

/**
 * @author Soo.
 */
public class WeakReferenceCacheEntity<V> extends CacheEntity<V> {
    private static final String TAG = "WeakReferenceCacheEntity--->";

    private WeakReference<V> weakReference;

    public WeakReferenceCacheEntity(long period) {
        super(period);
    }

    @Override
    public void bind(V value) {
        if (weakReference != null) {
            weakReference = null;
        }
        this.weakReference = new WeakReference<>(value);
    }

    @Override
    public V getValue() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
}
