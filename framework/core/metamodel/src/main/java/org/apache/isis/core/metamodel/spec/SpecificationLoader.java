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

package org.apache.isis.core.metamodel.spec;

import java.util.List;

import org.apache.isis.core.commons.components.ApplicationScopedComponent;
import org.apache.isis.core.commons.debug.DebuggableWithTitle;

public interface SpecificationLoader extends ApplicationScopedComponent, DebuggableWithTitle, SpecificationLookup {

    /**
     * @see #loadSpecification(String)
     */
    @Override
    ObjectSpecification loadSpecification(Class<?> cls);

    /**
     * Loads the specifications of the specified types except the one specified
     * (to prevent an infinite loop).
     */
    public boolean loadSpecifications(List<Class<?>> typesToLoad, final Class<?> typeToIgnore);

    /**
     * Return the specification for the specified class of object.
     * 
     * <p>
     * It is possible for this method to return <tt>null</tt>, for example if
     * the configured {@link #getClassSubstitutor()} has filtered out the class.
     */
    ObjectSpecification loadSpecification(String fullyQualifiedClassName);

    /**
     * Whether this class has been loaded.
     */
    boolean loaded(Class<?> cls);

    /**
     * @see #loaded(Class).
     */
    boolean loaded(String fullyQualifiedClassName);

    /**
     * Specify the classes of the services to pro-actively prime the cache.
     */
    void setServiceClasses(List<Class<?>> serviceClasses);

    /**
     * Loads the specifications of the specified types.
     */
    boolean loadSpecifications(List<Class<?>> typesToLoad);

}