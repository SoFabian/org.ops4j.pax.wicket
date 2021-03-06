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
package org.ops4j.pax.wicket.samples.departmentstore.view.floor.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    private FloorDepartmentStoreModelTracker regularStoreTracker;
    private FloorDepartmentStoreModelTracker springStoreTracker;
    private FloorDepartmentStoreModelTracker blueprintStoreTracker;

    public void start(BundleContext context) throws Exception {
        regularStoreTracker = new FloorDepartmentStoreModelTracker(context, "departmentstore");
        regularStoreTracker.open();
        springStoreTracker = new FloorDepartmentStoreModelTracker(context, "springDeptStore");
        springStoreTracker.open();
        blueprintStoreTracker = new FloorDepartmentStoreModelTracker(context, "blueprintDeptStore");
        blueprintStoreTracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        regularStoreTracker.close();
        springStoreTracker.close();
        blueprintStoreTracker.close();
    }
}
