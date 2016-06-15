/* 
 * The MIT License
 *
 * Copyright 2016 https://github.com/shoon.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.example.rapidoid.morphia.dev_issue.services;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.rapidoid.setup.AppRestartListener;

/**
 *
 * @author shoon
 */
public enum MorphiaService implements AppRestartListener {
    INSTANCE;

    private org.mongodb.morphia.Morphia morphia = null;
    private MongoClient client = null;
    private Datastore datastore = null;

    MorphiaService() {
        createDatastoreConnection();
    }

    private void createDatastoreConnection() {
        //Create Mongo Connection
        morphia = new org.mongodb.morphia.Morphia();
        morphia.mapPackage("com.example.rapidoid.morphia.dev_issue.services.models");
        client = new MongoClient();
        datastore = morphia.createDatastore(client, "rapidoid_morphia_example");
        datastore.ensureIndexes();
    }

    public static MorphiaService getInstance() {
        return INSTANCE;
    }

    public Datastore getDatastore() {
        //Get datastore
        return this.datastore;
    }

    @Override
    public void beforeAppRestart() {
        // Close client
        if (this.client != null) {
            this.client.close();
        }

    }

    @Override
    public void afterAppRestart() {
        // Close and restart connection if not null
//        if (this.client != null) {
//            client.close();
//            createDatastoreConnection();
//        }
    }

}
