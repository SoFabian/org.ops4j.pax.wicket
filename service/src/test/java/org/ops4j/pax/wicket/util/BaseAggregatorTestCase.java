/**
 * Copyright OPS4J
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.wicket.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Dictionary;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.wicket.api.ContentSource;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceRegistration;

@RunWith(JMock.class)
public final class BaseAggregatorTestCase {

    private Mockery mockery;

    @Before
    public void setup() {
        mockery = new Mockery();
    }

    @Test
    public final void testConstructorArguments() {
        BundleContext context = mockery.mock(BundleContext.class);
        Object[][] arguments = {
            { context, null, "aggPoint" },
            { context, "appName", null },
            { null, null, "aggPoint" },
            { context, null, null },
            { null, null, null }
        };

        String msg = "Constructor arguments must not be [null].";
        for (Object[] argument : arguments) {
            BundleContext ctx = (BundleContext) argument[0];
            String appName = (String) argument[1];
            String aggPoint = (String) argument[2];
            try {
                new TestBaseAggregator(ctx, appName, aggPoint);
                fail(msg);
            } catch (IllegalArgumentException e) {
                // expected
            } catch (Throwable e) {
                e.printStackTrace();
                fail(msg);
            }
        }

        try {
            new TestBaseAggregator(context, "appName", "aggPoint");
        } catch (Throwable e) {
            e.printStackTrace();
            fail("Construct object with valid argument must not throw exception.");
        }
    }

    @Test
    public final void testBaseAggregatorServiceLifeCycle()
        throws Throwable {
        BundleContext context = mockery.mock(BundleContext.class);

        TestBaseAggregator aggregator = new TestBaseAggregator(context, "appName", "aggPoint");

        Expectations exp1 = new Expectations();
        exp1.one(context).createFilter(exp1.with(exp1.any(String.class)));
        Filter filter = mockery.mock(Filter.class);
        exp1.returnValue(filter);

        exp1.one(context).addServiceListener(
            exp1.with(exp1.any(ServiceListener.class)),
            exp1.with(exp1.any(String.class))
            );

        exp1.one(context).getServiceReferences(
            exp1.with(exp1.any(String.class)),
            exp1.with(exp1.any(String.class))
            );

        exp1.allowing(context).getProperty("org.osgi.framework.version");
        exp1.will(exp1.returnValue("4.2"));

        exp1.one(context).registerService(
            (String[]) exp1.with(exp1.any(Object.class)),
            exp1.with(exp1.any(Object.class)),
            exp1.with(exp1.any(Dictionary.class))
            );
        ServiceRegistration expectedSerReg = mockery.mock(ServiceRegistration.class);
        exp1.will(exp1.returnValue(expectedSerReg));
        exp1.one(expectedSerReg).unregister();
        mockery.checking(exp1);

        try {
            aggregator.register();
        } catch (Throwable e) {
            e.printStackTrace();
            fail("Register first time must throw any exception.");
        }

        String msg = "Must thrown an [IllegalStateException] when the service registered twice.";
        try {
            aggregator.register();
            fail(msg);
        } catch (IllegalStateException e) {
            // Expected
        } catch (Throwable e) {
            fail(msg);
        }

        Expectations exp2 = new Expectations();
        exp2.one(context).removeServiceListener(exp2.with(exp2.any(ServiceListener.class)));

        mockery.checking(exp2);

        aggregator.dispose();

        Expectations exp3 = new Expectations();
        exp3.one(context).createFilter(exp3.with(exp3.any(String.class)));
        exp3.returnValue(filter);

        exp3.one(context).addServiceListener(
            exp3.with(exp3.any(ServiceListener.class)),
            exp3.with(exp3.any(String.class))
            );

        exp3.one(context).getServiceReferences(
            exp3.with(exp3.any(String.class)),
            exp3.with(exp3.any(String.class))
            );

        exp3.one(context).registerService(
            (String[]) exp3.with(exp3.any(Object.class)),
            exp3.with(exp3.any(Object.class)),
            exp3.with(exp3.any(Dictionary.class))
            );
        exp3.will(exp3.returnValue(expectedSerReg));
        mockery.checking(exp3);

        try {
            aggregator.register();
        } catch (Throwable e) {
            e.printStackTrace();
            fail("Register first time must throw any exception.");
        }
    }

    @Test
    public final void testAddRemoveGetContent() {
        TestBaseAggregator aggregator = new TestBaseAggregator(
            mockery.mock(BundleContext.class), "appName", "aggPoint"
            );

        String msg = "AddContent with [null] must throw [IllegalArgumentException]";
        ContentSource cs = mockery.mock(ContentSource.class);
        Object[][] arguments = {
            { null, null },
            { "wicketId", null },
            { null, cs }
        };

        for (Object[] argument : arguments) {
            String wicketId = (String) argument[0];
            ContentSource cs1 = (ContentSource) argument[1];
            try {
                aggregator.addContent(wicketId, cs1);
                fail(msg);
            } catch (IllegalArgumentException e) {
                // Expected
            } catch (Throwable e) {
                fail(msg);
            }
        }

        Expectations exp = new Expectations();
        exp.allowing(cs).getSourceId();

        String sourceId = "sourceId";
        exp.will(exp.returnValue(sourceId));

        mockery.checking(exp);

        String wicketId = "wicketId";
        aggregator.addContent(wicketId, cs);

        String getContentsMsg = "getContents with [null] argument must throw [IllegalArgumentException].";
        try {
            aggregator.getContentByGroupId(null);
            fail(getContentsMsg);
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Throwable e) {
            e.printStackTrace();
            fail(getContentsMsg);
        }

        List<ContentSource> cnt1 = aggregator.getContentByGroupId("wicketId2");
        assertEquals(cnt1.size(), 0);

        List<ContentSource> cnt2 = aggregator.getContentByGroupId(wicketId);
        assertEquals(cs, cnt2.get(0));

        String getContentByIdMsg = "getContentById with [null] argument must throw [IllegalArgumentException].";
        try {
            aggregator.getContentBySourceId("srcId");
            fail(getContentByIdMsg);
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Throwable e) {
            e.printStackTrace();
            fail(getContentByIdMsg);
        }

        ContentSource retContentSource = aggregator.getContentBySourceId(sourceId);
        assertEquals(cs, retContentSource);

        boolean isRemoveSuccesfull = aggregator.removeContent(wicketId, cs);
        assertEquals(true, isRemoveSuccesfull);
    }

    private static final class TestBaseAggregator extends BaseAggregator {

        private TestBaseAggregator(BundleContext ctx, String appName, String aggregatePoint)
            throws IllegalArgumentException {
            super(ctx, appName, aggregatePoint);
        }

        @Override
        protected String[] getServiceNames() {
            return new String[0];
        }
    }
}
