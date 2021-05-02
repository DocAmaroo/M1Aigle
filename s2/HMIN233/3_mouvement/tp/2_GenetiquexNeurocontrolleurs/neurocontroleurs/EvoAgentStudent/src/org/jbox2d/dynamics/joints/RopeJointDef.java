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
package org.jbox2d.dynamics.joints;

import org.jbox2d.common.Vec2;

/**
 * Rope joint definition. This requires two body anchor points and a maximum lengths. Note: by
 * default the connected objects will not collide. see collideConnected in b2JointDef.
 * 
 * @author Daniel Murphy
 */
public class RopeJointDef extends JointDef {

  /**
   * The local anchor point relative to bodyA's origin.
   */
  public final Vec2 localAnchorA = new Vec2();

  /**
   * The local anchor point relative to bodyB's origin.
   */
  public final Vec2 localAnchorB = new Vec2();

  /**
   * The maximum length of the rope. Warning: this must be larger than b2_linearSlop or the joint
   * will have no effect.
   */
  public float maxLength;

  public RopeJointDef() {
    super(JointType.ROPE);
    localAnchorA.set(-1.0f, 0.0f);
    localAnchorB.set(1.0f, 0.0f);
  }
}
