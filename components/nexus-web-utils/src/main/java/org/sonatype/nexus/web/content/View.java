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

package org.sonatype.nexus.web.content;

import java.io.IOException;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.nexus.proxy.ItemNotFoundException;
import org.sonatype.nexus.proxy.ResourceStoreRequest;
import org.sonatype.nexus.proxy.item.StorageItem;

/**
 * Component providing a "view" for a request, usually providing some metadata about the request, request result, or
 * such. There are several views, differentiated by their {@link Named} annotation.
 * 
 * @since 2.7.0
 */
public interface View
{
  /**
   * Renders the "view".
   * 
   * @param request the servlet request, never {@code null}.
   * @param response the servlet response, never {@code null}.
   * @param resourceStoreRequest the resource store request, never {@code null}.
   * @param item item, if found, or {@code null} (in case of {@link ItemNotFoundException} for example).
   * @param exception exception, if any.
   */
  void renderView(final HttpServletRequest request, final HttpServletResponse response,
      final ResourceStoreRequest resourceStoreRequest, final StorageItem item, final Exception exception)
      throws IOException;

}
