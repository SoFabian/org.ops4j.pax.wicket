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
package org.ops4j.pax.wicket.samples.departmentstore.view.internal;

import java.util.Arrays;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.ops4j.pax.wicket.api.PageFactory;
import org.ops4j.pax.wicket.util.AbstractPageFactory;
import org.ops4j.pax.wicket.util.PageFinder;
import org.ops4j.pax.wicket.util.RootContentAggregator;
import org.osgi.framework.BundleContext;

final class OverviewPageFactory extends AbstractPageFactory<OverviewPage>
        implements PageFactory<OverviewPage> {

    private BundleContext bundleContext;
    private RootContentAggregator aggregator;

    public OverviewPageFactory(BundleContext context, RootContentAggregator aggregator, String applicationName,
                                String pageName) {
        super(context, "overview", applicationName, pageName);
        bundleContext = context;
        this.aggregator = aggregator;
    }

    public Class<OverviewPage> getPageClass() {
        return OverviewPage.class;
    }

    public OverviewPage createPage(PageParameters params) {
        PageFactory<Page>[] pageSources = PageFinder.findPages(bundleContext, "departmentstore", "about");
        return new OverviewPage(aggregator, new StoreDescription("Sungei Wang Plaza (Activator-Powered)"),
            Arrays.asList(pageSources));
    }
}
