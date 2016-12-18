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

package soo.swallow.cahce.tool;

import soo.swallow.cahce.exception.OverflowException;

/**
 * @author Soo.
 */
public class CapacityRecorder {
    private static final String TAG = "CapacityRecorder--->";

    private long maxCapacity;
    private long current;

    public CapacityRecorder(long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void allocate(long capacity) throws OverflowException {
        long temp = current + capacity;
        if (temp > maxCapacity) {
            throw new OverflowException();
        }
        current = temp;
    }

    public synchronized void release(long capacity) {
        current = Math.max(current - capacity, 0);
    }

    public synchronized long currentCapacity() {
        return current;
    }

    public long getMaxCapacity() {
        return maxCapacity;
    }

}
