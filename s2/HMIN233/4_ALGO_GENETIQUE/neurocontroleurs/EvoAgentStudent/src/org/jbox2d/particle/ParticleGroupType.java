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

public class ParticleGroupType {
  /** resists penetration */
  public static final int b2_solidParticleGroup = 1 << 0;
  /** keeps its shape */
  public static final int b2_rigidParticleGroup = 1 << 1;
}
