/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.felix.converter.impl.json;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;

import org.osgi.service.converter.Converter;
import org.osgi.service.converter.Decoding;

public class JsonDecodingImpl<T> implements Decoding<T> {
    private final Class<T> clazz;
    private final Converter converter;

    public JsonDecodingImpl(Converter c, Class<T> cls) {
        converter = c;
        clazz = cls;
    }

    @Override
    public T from(CharSequence in) {
        if (Map.class.isAssignableFrom(clazz)) {
            return createMapFromJSONString(in);
        }
        return deserializeSingleJSONValue(clazz, in);
    }

    @SuppressWarnings("unchecked")
    private T createMapFromJSONString(CharSequence in) {
        JsonParser jp = new JsonParser((String) in);
        return (T) jp.getParsed();
    }

    @SuppressWarnings("unchecked")
    private T deserializeSingleJSONValue(Class<T> cls, CharSequence cs) {
        try {
            Method m = cls.getDeclaredMethod("valueOf", String.class);
            if (m != null) {
                return (T) m.invoke(null, cs);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public T from(InputStream in) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T from(InputStream in, Charset charset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T from(Readable in) {
        // TODO Auto-generated method stub
        return null;
    }
}