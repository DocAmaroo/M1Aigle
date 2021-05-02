/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.jbox2d.particle;

import org.jbox2d.common.Vec2;

public class ParticleContact {
  /** Indices of the respective particles making contact. */
  public int indexA, indexB;
  /** The logical sum of the particle behaviors that have been set. */
  public int flags;
  /** Weight of the contact. A value between 0.0f and 1.0f. */
  public float weight;
  /** The normalized direction from A to B. */
  public final Vec2 normal = new Vec2();
}
