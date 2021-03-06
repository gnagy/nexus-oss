/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2013 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */

package org.sonatype.nexus.plugins.rest;

import java.util.List;

/**
 * A Resource bundle meant for extending/contributing/spoofing existing resources (JS, CSS, Images, etc) of the Nexus
 * Web Application. This component is able only to contribute static resources, if you want to extends REST API, please
 * see PlexusResource.
 *
 * @author cstamas
 */
@Deprecated
public interface NexusResourceBundle
{
  /**
   * Returns the list of static resources.
   */
  List<StaticResource> getContributedResouces();  // FIXME: Spelling!
}
