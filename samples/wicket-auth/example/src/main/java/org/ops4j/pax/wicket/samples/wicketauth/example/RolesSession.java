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
/*
 * $Id: RolesSession.java 459082 2006-02-08 21:11:29Z ehillenius $ $Revision:
 * 1.1 $ $Date: 2006-02-09 06:11:29 +0900 (Thu, 09 Feb 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.ops4j.pax.wicket.samples.wicketauth.example;

import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebSession;

/**
 * Web Session for this example.
 * 
 * @author Eelco Hillenius
 */
public class RolesSession extends WebSession
{
	/** the current user. */
	private User user = RolesApplication.USERS.get(0);

	/**
	 * Construct.
	 * 
	 * @param application
	 */
	public RolesSession(WebApplication application)
	{
		super(application);
	}

	/**
	 * Gets user.
	 * 
	 * @return user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * Sets user.
	 * 
	 * @param user
	 *            user
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

}
