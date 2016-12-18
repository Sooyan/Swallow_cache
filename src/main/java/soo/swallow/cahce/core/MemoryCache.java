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

package soo.swallow.cahce.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import soo.swallow.base.ArgsUtils;
import soo.swallow.cahce.Algorithm;
import soo.swallow.cahce.Platform;
import soo.swallow.cahce.core.entity.CacheEntity;
import soo.swallow.cahce.core.entity.CacheEntityFactory;
import soo.swallow.cahce.exception.OverflowException;

/**
 * @author Soo.
 */
public class MemoryCache<K, V> extends CleanCache<K, V> {
    private static final String TAG = "MemoryCache--->";

    private Map<K, CacheEntity<V>> map;

    public MemoryCache(Platform platform, String name, long sizeCapacity, long countCapacity, Algorithm algorithm) {
        super(platform, name, sizeCapacity, countCapacity, algorithm);
        init();
    }

    public MemoryCache(Platform platform, String name, long sizeCapacity, long countCapacity, Algorithm algorithm,
                       CacheEntityFactory cacheEntityFactory) {
        super(platform, name, sizeCapacity, countCapacity, algorithm, cacheEntityFactory);
        init();
    }

    private void init() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void put(K key, V value) {
        put(key, value, false);
    }

    @Override
    public void put(K key, V value, boolean isFinal) {
        ArgsUtils.notNull(key, "The key of value is null");
        CacheEntity<V> cacheEntity = map.get(key);
        try {
            cacheEntity = bindOrCreateIfNecessary(cacheEntity, value, isFinal);
        } catch (OverflowException e) {
            e.printStackTrace();
        }
        if (cacheEntity != null) {
            try {
                map.put(key, cacheEntity);
            } finally {
                recycle(cacheEntity);
            }
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        CacheEntity<V> cacheEntity = map.get(key);
        if (cacheEntity == null) {
            return null;
        }
        return cacheEntity.getValue();
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        CacheEntity<V> cacheEntity = map.remove(key);
        if (cacheEntity == null) {
            return null;
        }
        V value = cacheEntity.getValue();
        recycle(cacheEntity);
        return value;
    }

    @Override
    public void clear() {
        for (K key : map.keySet()) {
            remove(key);
        }
    }

    @Override
    public void flush() {
        for (Map.Entry<K, CacheEntity<V>> entry : map.entrySet()) {
            K key = entry.getKey();
            CacheEntity<V> cacheEntity = entry.getValue();
            boolean isValid = isValidCacheEntity(cacheEntity);
            if (!isValid) {
                remove(key);
            }
        }
    }

    @Override
    public boolean isValid(K key) {
        return isValidCacheEntity(map.get(key));
    }

}
