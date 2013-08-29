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

package org.sonatype.nexus.web.content.view;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.nexus.proxy.ItemNotFoundException;
import org.sonatype.nexus.proxy.ItemNotFoundException.ItemNotFoundInRepositoryReason;
import org.sonatype.nexus.proxy.ResourceStoreRequest;
import org.sonatype.nexus.proxy.item.StorageItem;
import org.sonatype.nexus.web.content.Renderer;

/**
 * Component providing a "describe" of a request.
 * <p>
 * Note: this implementation is actually a "stub", doing plain HTTP redirection to "old" resource, as the payload it
 * emits is a model from legacy restlet1x plugin.
 * 
 * @since 2.7.0
 */
@Singleton
@Named(RequestDescribeView.ID)
public class RequestDescribeView
    extends ViewSupport
{
  public static final String ID = "describe";

  @Inject
  public RequestDescribeView(final Renderer renderer) {
    super(renderer);
  }

  @Override
  public void renderView(final HttpServletRequest request, final HttpServletResponse response,
      final ResourceStoreRequest resourceStoreRequest, final StorageItem item, final Exception exception)
      throws IOException
  {
    // TODO: implement this, but...
    // For now, to make UI work, redirect this
    // http://localhost:8081/nexus/content/repositories/central/log4j/log4j/1.2.13/log4j-1.2.13.pom?describe
    // to
    // http://localhost:8081/nexus/service/local/repositories/central/content/log4j/log4j/1.2.13/log4j-1.2.13.pom?describe
    if (item != null) {
      // easy peasy
      response.setStatus(HttpServletResponse.SC_FOUND);
      final String locationUrl = resourceStoreRequest.getRequestAppRootUrl() + "service/local/repositories/"
          + item.getRepositoryItemUid().getRepository().getId() + "/content" + item.getRepositoryItemUid().getPath()
          + "?describe";
      response.setHeader("Location", locationUrl);
    }
    else if (exception instanceof ItemNotFoundException
        && ((ItemNotFoundException) exception).getReason() instanceof ItemNotFoundInRepositoryReason) {
      final ItemNotFoundException infe = (ItemNotFoundException) exception;
      final ItemNotFoundInRepositoryReason reason = (ItemNotFoundInRepositoryReason) infe.getReason();
      // chum it
      response.setStatus(HttpServletResponse.SC_FOUND);
      final String locationUrl = resourceStoreRequest.getRequestAppRootUrl() + "service/local/repositories/"
          + reason.getRepository().getId() + "/content" + reason.getResourceStoreRequest().getRequestPath()
          + "?describe";
      response.setHeader("Location", locationUrl);
    }
    else {
      // in this case (no item nor INFex) we should not be called at all, but tie loose ends for now
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      getRenderer().renderErrorPage(request, response, resourceStoreRequest,
          new UnsupportedOperationException("Not implemented yet"));
    }
  }
}
