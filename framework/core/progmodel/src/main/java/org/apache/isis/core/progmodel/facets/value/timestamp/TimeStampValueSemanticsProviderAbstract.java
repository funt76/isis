/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.core.progmodel.facets.value.timestamp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.isis.applib.value.TimeStamp;
import org.apache.isis.core.commons.config.ConfigurationConstants;
import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.progmodel.facets.object.value.ValueSemanticsProviderContext;
import org.apache.isis.core.progmodel.facets.value.ValueSemanticsProviderAbstractTemporal;

public abstract class TimeStampValueSemanticsProviderAbstract<T> extends ValueSemanticsProviderAbstractTemporal<T> {

    private static final Object DEFAULT_VALUE = null; // no default
    private static final int TYPICAL_LENGTH = 12;

    private static final boolean IMMUTABLE = false;
    private static final boolean EQUAL_BY_CONTENT = false;

    protected static void initFormats(final Map<String, DateFormat> formats) {
        formats.put("iso", createDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        formats.put(ISO_ENCODING_FORMAT, createDateFormat("yyyyMMdd'T'HHmmssSSS"));
        formats.put("medium", DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG));
        formats.put("short", DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG));
    }

    @SuppressWarnings("unchecked")
    public TimeStampValueSemanticsProviderAbstract(final FacetHolder holder, final Class<T> adaptedClass, final IsisConfiguration configuration, final ValueSemanticsProviderContext context) {
        super("timestamp", holder, adaptedClass, TYPICAL_LENGTH, IMMUTABLE, EQUAL_BY_CONTENT, (T) DEFAULT_VALUE, configuration, context);
        final String formatRequired = configuration.getString(ConfigurationConstants.ROOT + "value.format.timestamp");
        if (formatRequired == null) {
            format = formats().get(defaultFormat());
        } else {
            setMask(formatRequired);
        }
    }

    @Override
    public int getLevel() {
        return TIMESTAMP;
    }

    @Override
    protected T add(final T original, final int years, final int months, final int days, final int hours, final int minutes) {
        return original;
    }

    @Override
    protected Date dateValue(final Object value) {
        return new Date(((TimeStamp) value).longValue());
    }

    @Override
    protected String defaultFormat() {
        return "short";
    }

    @Override
    public String toString() {
        return "TimeStampValueSemanticsProvider: " + format;
    }

}