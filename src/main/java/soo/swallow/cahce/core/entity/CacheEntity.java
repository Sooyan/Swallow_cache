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

/**
 * @author Soo.
 */
public class CacheEntity<V> {

    private long size;
    private long period;

    private V value;
    private long createTime;
    private long modifiedTime;

    public CacheEntity(long period) {
        this.period = period;
        long currentTime = System.currentTimeMillis();
        this.createTime = currentTime;
        this.modifiedTime = currentTime;
    }

    public synchronized V getValue() {
        return value;
    }

    public synchronized long getSize() {
        return size;
    }

    public synchronized long getPeriod() {
        return period;
    }

    public synchronized void bind(V value) {
        this.value = value;
    }

    public synchronized void setSize(long size) {
        this.size = size;
    }

    public synchronized void reset() {
        this.createTime = -1;
        this.modifiedTime = -1;
        this.value = null;
    }

}
