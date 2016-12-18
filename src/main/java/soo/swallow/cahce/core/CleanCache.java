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

import soo.swallow.cahce.Algorithm;
import soo.swallow.cahce.core.entity.DefaultCacheEntityFactory;
import soo.swallow.cahce.Platform;
import soo.swallow.cahce.Utils;
import soo.swallow.cahce.core.entity.CacheEntity;
import soo.swallow.cahce.core.entity.CacheEntityFactory;
import soo.swallow.cahce.exception.OverflowException;
import soo.swallow.cahce.tool.CacheCleaner;
import soo.swallow.cahce.tool.CapacityRecorder;
import soo.swallow.cahce.tool.SizeSpec;

/**
 * @author Soo.
 */
public abstract class CleanCache<K, V> implements Cache<K, V>, CacheCleaner.Cleanable {
    private static final String TAG = "CleanCache--->";

    private Platform platform;
    private String name;
    private Algorithm algorithm;

    private CapacityRecorder sizeCapacityRecorder;
    private CapacityRecorder countCapacityRecorder;

    private CacheEntityFactory cacheEntityFactory;

    public CleanCache(Platform platform, String name, long sizeCapacity,
                      long countCapacity, Algorithm algorithm) {
        this(platform, name, sizeCapacity, countCapacity, algorithm, new DefaultCacheEntityFactory(platform));
    }

    public CleanCache(Platform platform, String name, long sizeCapacity,
                      long countCapacity, Algorithm algorithm, CacheEntityFactory cacheEntityFactory) {
        Utils.notNull(name, "No name be found");
        Utils.notNull(platform, "No platform be found");
        Utils.notNull(algorithm, "No Algorithm be found");
        Utils.notNull(cacheEntityFactory, "No CacheEntityFactory be found");
        Utils.greaterThan(sizeCapacity, 0, "Size is must greater than zero");
        Utils.greaterThan(countCapacity, 0, "Count is must greater than zero");

        this.platform = platform;
        this.name = name;
        this.algorithm = algorithm;
        this.cacheEntityFactory = cacheEntityFactory;
        this.sizeCapacityRecorder = new CapacityRecorder(sizeCapacity);
        this.countCapacityRecorder = new CapacityRecorder(countCapacity);
    }

    protected CacheEntity<V> bindOrCreateIfNecessary(CacheEntity<V> cacheEntity, V value,
                                                     boolean isFinal) throws OverflowException {
        long size = SizeSpec.sizeOf(value);
        sizeCapacityRecorder.allocate(size);
        try {
            countCapacityRecorder.allocate(1);
        } catch (OverflowException e) {
            throw new OverflowException();
        } finally {
            sizeCapacityRecorder.release(size);
        }
        if (cacheEntity == null) {
            cacheEntity = cacheEntityFactory.obtain(size);
        } else {
            if (isFinal) {
                return cacheEntity;
            }
        }
        if (cacheEntity != null) {
            cacheEntity.bind(value);
            cacheEntity.setSize(size);
        }
        return cacheEntity;
    }

    protected boolean isValidCacheEntity(CacheEntity<V> cacheEntity) {
        if (cacheEntity == null) {
            return false;
        }

        return false;
    }

    protected void recycle(CacheEntity<V> cacheEntity) {
        cacheEntityFactory.recycle(cacheEntity);
    }

    @Override
    public int count() {
        return (int) countCapacityRecorder.currentCapacity();
    }

    @Override
    public long size() {
        return sizeCapacityRecorder.currentCapacity();
    }

    @Override
    public void clean() {
        
    }

    public CacheEntityFactory getCacheEntityFactory() {
        return cacheEntityFactory;
    }

    public String getName() {
        return name;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

//    static class InnerValid<V> implements Valid<CacheEntity<V>> {
//
//        final long perMaxSize;
//        final long maxPeriodTime;
//
//        InnerValid(long perMaxSize, long maxPeriodTime) {
//            this.perMaxSize = perMaxSize;
//            this.maxPeriodTime = maxPeriodTime;
//        }
//
//        @Override
//        public boolean valid(CacheEntity<V> value) {
//            return validSize(value) && validPeriod(value);
//        }
//
//        boolean validSize(CacheEntity<?> value) {
//            if (value == null) {
//                return false;
//            }
//            long size = value.getSize();
//            return size <= perMaxSize;
//        }
//
//        boolean validPeriod(CacheEntity<?> value) {
//            if (value == null) {
//                return false;
//            }
//            long createTime = value.getCreateTime();
//
//            return createTime <= System.currentTimeMillis() - maxPeriodTime;
//        }
//    }
}
