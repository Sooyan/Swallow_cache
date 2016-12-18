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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Soo.
 */
public class CacheCleaner extends Thread {
    private static final String TAG = "CacheCleaner--->";

    public interface Cleanable {
        void clean();

        int getPriority();
    }

    private List<Cleanable> cleanableList;

    public CacheCleaner() {
        cleanableList = Collections.synchronizedList(new ArrayList<Cleanable>());
    }

    public synchronized void performClean() {
        for (Cleanable cleanable : cleanableList) {

        }
    }

    public void registerCleanable(Cleanable cleanable) {
        if (cleanable != null) {
            cleanableList.add(cleanable);
        }
    }

    public void unRegisterCleanable(Cleanable cleanable) {
        cleanableList.remove(cleanable);
    }

    @Override
    public void run() {
        
    }
}
