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

package org.apache.isis.core.metamodel.facets.propparam.labelat;

import java.lang.reflect.Method;
import org.apache.isis.applib.annotation.LabelAt;
import org.apache.isis.core.metamodel.facetapi.Facet;
import org.apache.isis.core.metamodel.facets.AbstractFacetFactoryTest;
import org.apache.isis.core.metamodel.facets.FacetFactory.ProcessMethodContext;
import org.apache.isis.core.metamodel.facets.FacetFactory.ProcessParameterContext;
import org.apache.isis.core.metamodel.facets.param.labelat.annotation.LabelAtFacetOnParameterAnnotation;
import org.apache.isis.core.metamodel.facets.param.labelat.annotation.LabelAtFacetOnParameterAnnotationFactory;
import org.apache.isis.core.metamodel.facets.properties.labelat.annotation.LabelAtFacetOnPropertyAnnotation;
import org.apache.isis.core.metamodel.facets.properties.labelat.annotation.LabelAtFacetOnPropertyFactory;

public class LabelAtAnnotationFacetFactoryTest extends AbstractFacetFactoryTest {

    public void testLabelAtAnnotationPickedUpOnProperty() {
        final LabelAtFacetOnPropertyFactory facetFactory = new LabelAtFacetOnPropertyFactory();

        class Customer {
            @SuppressWarnings("unused")
            @LabelAt(LabelAt.Position.LEFT)
            public String getFirstName() {
                return null;
            }
        }
        final Method method = findMethod(Customer.class, "getFirstName");

        facetFactory.process(new ProcessMethodContext(Customer.class, null, null, method, methodRemover, facetedMethod));

        final Facet facet = facetedMethod.getFacet(LabelAtFacet.class);
        assertNotNull(facet);
        assertTrue(facet instanceof LabelAtFacetOnPropertyAnnotation);
        final LabelAtFacetOnPropertyAnnotation labelAtFacetAnnotation = (LabelAtFacetOnPropertyAnnotation) facet;
        assertEquals(LabelAt.Position.LEFT, labelAtFacetAnnotation.value());
    }

    public void testLabelAtAnnotationPickedUpOnActionParameter() {
        final LabelAtFacetOnParameterAnnotationFactory facetFactory = new LabelAtFacetOnParameterAnnotationFactory();

        class Customer {
            @SuppressWarnings("unused")
            public void someAction(@LabelAt(LabelAt.Position.LEFT) final String foo) {
            }
        }
        final Method method = findMethod(Customer.class, "someAction", new Class[] { String.class });

        facetFactory.processParams(new ProcessParameterContext(method, 0, facetedMethodParameter));

        final Facet facet = facetedMethodParameter.getFacet(LabelAtFacet.class);
        assertNotNull(facet);
        assertTrue(facet instanceof LabelAtFacetOnParameterAnnotation);
        final LabelAtFacetOnParameterAnnotation labelAtFacetAnnotation = (LabelAtFacetOnParameterAnnotation) facet;
        assertEquals(LabelAt.Position.LEFT, labelAtFacetAnnotation.value());
    }
}