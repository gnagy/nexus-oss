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

package org.sonatype.nexus.proxy.item;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The support for implementing {@link ContentLocator}s.
 * 
 * @since 2.7.0
 */
public abstract class AbstractContentLocator
    implements ContentLocator
{
  private final String mimeType;

  private final boolean reusable;

  public AbstractContentLocator(final String mimeType, final boolean reusable) {
    this.mimeType = checkNotNull(mimeType);
    this.reusable = reusable;
  }

  @Override
  public String getMimeType() {
    return mimeType;
  }

  @Override
  public boolean isReusable() {
    return reusable;
  }
}
