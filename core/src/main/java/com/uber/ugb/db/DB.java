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

package com.uber.ugb.db;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.uber.ugb.measurement.Metrics;
import com.uber.ugb.queries.QueriesSpec;
import com.uber.ugb.schema.QualifiedName;
import com.uber.ugb.schema.Vocabulary;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DB implements Serializable {

    protected Vocabulary vocabulary;
    /**
     * Properties for configuring this DB.
     */
    private Properties properties = new Properties();

    private Metrics metrics = new Metrics();

    protected static Properties extractProperties(Properties properties, String select, String filterField) {
        if (Strings.isNullOrEmpty(select)) {
            return properties;
        }
        Properties answer = new Properties();
        AtomicBoolean hasFilterField = new AtomicBoolean();
        Splitter.on(',').omitEmptyStrings().trimResults().split(select).forEach(s -> {
            if (s.equals(filterField)) {
                hasFilterField.set(true);
            }
            if (properties.containsKey(s)) {
                answer.put(s, properties.getProperty(s));
            }
        });
        if (!hasFilterField.get() && filterField != null) {
            if (properties.containsKey(filterField)) {
                answer.put(filterField, properties.getProperty(filterField));
            }
        }
        return answer;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    /**
     * Initialize any state for this DB.
     * Called once per DB instance; there is one DB instance per client thread.
     */
    public void init() throws DBException {
    }

    /**
     * Cleanup any state for this DB.
     * Called once per DB instance; there is one DB instance per client thread.
     */
    public void cleanup() throws DBException {
    }

    public abstract Status writeVertex(QualifiedName label, Object id, Object... keyValues);

    public abstract Status writeEdge(QualifiedName edgeLabel,
                                     QualifiedName outVertexLabel, Object outVertexId, QualifiedName inVertexLabel, Object inVertexId,
                                     Object... keyValues);

    public abstract Status subgraph(QueriesSpec.Query query, Subgraph subgraph);

    public Status commitBatch() {
        return Status.OK;
    }

    /**
     * Get the set of properties for this DB.
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Set the properties for this DB.
     */
    public void setProperties(Properties p) {
        properties = p;
    }

    /**
     * genVertexId customizable way to generate a vertex id
     *
     * @param label
     * @param id
     * @return
     */
    public Object genVertexId(QualifiedName label, long id) {
        long key = UUID.nameUUIDFromBytes((label.toString() + id).getBytes()).getLeastSignificantBits();
        if (key < 0) {
            key = -key;
        }
        return key;
    }

    /**
     * Implement this and executeQuery() if native query executions can be supported.
     */
    public String supportedQueryType() {
        return "";
    }

    /**
     * Implement this and supportedQueryType() if native query executions can be supported.
     */
    public QueryResult executeQuery(String query, Object startVertexId) {
        return null;
    }

}
