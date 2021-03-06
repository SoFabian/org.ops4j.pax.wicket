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
package org.ops4j.pax.wicket.internal;

import static org.ops4j.lang.NullArgumentException.validateNotEmpty;
import static org.ops4j.lang.NullArgumentException.validateNotNull;
import static org.ops4j.pax.wicket.api.ContentSource.APPLICATION_NAME;
import static org.osgi.framework.Constants.OBJECTCLASS;

import org.ops4j.pax.wicket.api.ContentSource;
import org.ops4j.pax.wicket.api.PageFactory;
import org.ops4j.pax.wicket.api.SessionDestroyedListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;

final class TrackingUtil {

    private TrackingUtil() {
    }

    static Filter createApplicationFilter(BundleContext bundleContext, String applicationName) {
        validateNotNull(bundleContext, "bundleContext");
        validateNotEmpty(applicationName, "applicationName");

        Filter filter;
        try {
            String filterString = "(&(" + APPLICATION_NAME + "=" + applicationName + ")(" +
                    OBJECTCLASS + "=" + SessionDestroyedListener.class.getName() + "))";
            filter = bundleContext.createFilter(filterString);
        } catch (InvalidSyntaxException e) {
            throw new IllegalArgumentException("applicationName can not contain '*', '(' or ')' : " + applicationName);
        }

        return filter;
    }

    static Filter createContentFilter(BundleContext bundleContext, String applicationName)
        throws IllegalArgumentException {
        validateNotNull(bundleContext, "bundleContext");
        validateNotEmpty(applicationName, "applicationName");

        Filter filter;
        try {
            String filterString = "(&(" + APPLICATION_NAME + "=" + applicationName + ")(" +
                                  OBJECTCLASS + "=" + ContentSource.class.getName() + "))";
            filter = bundleContext.createFilter(filterString);
        } catch (InvalidSyntaxException e) {
            throw new IllegalArgumentException("applicationName can not contain '*', '(' or ')' : " + applicationName);
        }

        return filter;
    }

    static Filter createAllPageFactoryFilter(BundleContext bundleContext, String applicationName)
        throws IllegalArgumentException {
        validateNotNull(bundleContext, "bundleContext");
        validateNotEmpty(applicationName, "applicationName");

        Filter filter;
        try {
            String filterString = "(&(" + APPLICATION_NAME + "=" + applicationName + ")"
                                  + "(" + OBJECTCLASS + "=" + PageFactory.class.getName() + "))";
            filter = bundleContext.createFilter(filterString);
        } catch (InvalidSyntaxException e) {
            throw new IllegalArgumentException("applicationName can not contain '*', '(' or ')' : " + applicationName);
        }
        return filter;
    }
}
