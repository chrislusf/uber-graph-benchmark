/*
 *
 *  * Copyright 2018 Uber Technologies Inc.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.uber.ugb.measurement;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonMetricsOutput implements MetricsOutput {

    JsonObject json;

    public JsonMetricsOutput() {
        json = new JsonObject();
    }

    @Override
    public void write(String category, String metric, long i) {
        JsonObject categoryObject = json.getAsJsonObject(category);
        if (categoryObject == null) {
            categoryObject = new JsonObject();
            json.add(category, categoryObject);
        }
        categoryObject.add(metric, new JsonPrimitive(i));
    }

    @Override
    public void write(String category, String metric, double d) {
        JsonObject categoryObject = json.getAsJsonObject(category);
        if (categoryObject == null) {
            categoryObject = new JsonObject();
            json.add(category, categoryObject);
        }
        categoryObject.add(metric, new JsonPrimitive(d));
    }

    public JsonObject getJson() {
        return json;
    }
}
