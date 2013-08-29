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

import org.sonatype.nexus.proxy.ResourceStoreRequest;
import org.sonatype.nexus.proxy.item.RepositoryItemUid;
import org.sonatype.nexus.proxy.item.StorageFileItem;
import org.sonatype.nexus.proxy.item.StorageItem;
import org.sonatype.nexus.proxy.maven.MavenRepository;
import org.sonatype.nexus.proxy.maven.gav.Gav;
import org.sonatype.nexus.web.content.Renderer;

/**
 * View about item as "seen" by Maven2. Accepts only Maven2 artifacts.
 * <p>
 * Note: this implementation is actually a "stub", doing plain HTTP redirection to "old" resource, as the payload it
 * emits is a model from legacy restlet1x plugin.
 * 
 * @since 2.7.0
 */
@Singleton
@Named(Maven2ArtifactView.ID)
public class Maven2ArtifactView
    extends ViewSupport
{
  public static final String ID = "maven2";

  @Inject
  public Maven2ArtifactView(final Renderer renderer) {
    super(renderer);
  }

  @Override
  public void renderView(final HttpServletRequest request, final HttpServletResponse response,
      final ResourceStoreRequest resourceStoreRequest, final StorageItem item, final Exception exception)
      throws IOException
  {
    // this view must have access to file item that lies on Maven2 layout path, 404 if not
    if (item instanceof StorageFileItem) {
      final RepositoryItemUid uid = item.getRepositoryItemUid();
      if (uid != null) {
        final MavenRepository mavenRepository = uid.getRepository().adaptToFacet(MavenRepository.class);
        final Gav gav = mavenRepository.getGavCalculator().pathToGav(item.getPath());
        if (gav != null) {
          // gav is not null, hence, the request corresponds to a file that lies on Maven2 layout path
          // TODO: re-implement this, current one uses DTOs from restlet1x (needs UI changes)
          // For now, to make UI work, redirect this
          // http://localhost:8081/nexus/content/repositories/central/log4j/log4j/1.2.13/log4j-1.2.13.pom?describe=maven2
          // to
          // http://localhost:8081/nexus/service/local/repositories/central/content/log4j/log4j/1.2.13/log4j-1.2.13.pom?describe=maven2
          response.setStatus(HttpServletResponse.SC_FOUND);
          response.setHeader("Location", resourceStoreRequest.getRequestAppRootUrl() + "service/local/repositories/"
              + item.getRepositoryItemUid().getRepository().getId() + "/content"
              + item.getRepositoryItemUid().getPath() + "?describe=maven2");
        }
      }
    }
    else {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      getRenderer().renderErrorPage(request, response, resourceStoreRequest, exception);
      return;
    }
  }
}
