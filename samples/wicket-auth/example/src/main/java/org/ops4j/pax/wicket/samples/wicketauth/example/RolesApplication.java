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
 * $Id: RolesApplication.java 460432 2006-04-28 10:08:35Z ehillenius $ $Revision:
 * 1.2 $ $Date: 2006-04-28 19:08:35 +0900 (Fri, 28 Apr 2006) $
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

import java.util.Arrays;
import java.util.List;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.authorization.strategies.role.RoleAuthorizationStrategy;
import wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import wicket.protocol.http.WebApplication;

/**
 * Application object for this example.
 * 
 * @author Eelco Hillenius
 */
public class RolesApplication extends WebApplication implements ISessionFactory
{
	/**
	 * User DB.
	 */
	public static List<User> USERS = Arrays.asList(new User[] { new User("jon", "ADMIN"),
			new User("kay", "USER"), new User("pam", "") });

	/**
	 * Construct.
	 */
	public RolesApplication()
	{
		super();
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	@Override
	public Class getHomePage()
	{
		return Example.class;
	}

	/**
	 * @see wicket.ISessionFactory#newSession()
	 */
	public Session newSession()
	{
		return new RolesSession(this);
	}

	@Override
	protected void init()
	{
		setSessionFactory(this);
//		getSecuritySettings().setAuthorizationStrategy(
//				new RoleAuthorizationStrategy(new UserRolesAuthorizer()));
//		MetaDataRoleAuthorizationStrategy.authorize(AdminBookmarkablePage.class, "ADMIN");
//		MetaDataRoleAuthorizationStrategy.authorize(AdminInternalPage.class, "ADMIN");
	}

}
