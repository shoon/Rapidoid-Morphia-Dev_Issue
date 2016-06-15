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
package com.example.rapidoid.morphia.dev_issue.controllers;

import com.example.rapidoid.morphia.dev_issue.models.Message;
import com.example.rapidoid.morphia.dev_issue.services.MorphiaService;
import java.io.UnsupportedEncodingException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.http.Req;
import org.rapidoid.u.U;

/**
 *
 * @author shoon
 */
@Controller
public class MessageApi {

    private static final String BODY_ERROR_RESPONSE = "error";

    /**
     * Returns a message
     *
     * @param id
     * @return
     */
    @GET("/m/{id}")
    public String getMessageById(Req req, String id) {
        ObjectId messageid;
        try {
            messageid = new ObjectId(id);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }

        Datastore ds = MorphiaService.INSTANCE.getDatastore();
        Message message = ds.get(Message.class, messageid);

        return U.frmt("Hello : %s, you asked for message id %s, what I found: %s", req.clientIpAddress(), id, message.getMsg());

    }

    /**
     * Save a message
     *
     * @param req
     * @return
     */
    @POST("/m")
    public String saveMessage(Req req) {

        String body;
        String response;
        try {
            body = new String(req.body(), "UTF-8");
            Datastore ds = MorphiaService.INSTANCE.getDatastore();
            Message message = new Message();
            message.setMsg(body);
            response = ds.save(message).getId().toString();

        } catch (UnsupportedEncodingException ex) {
            response = BODY_ERROR_RESPONSE;
        }

        return response;
    }

}
