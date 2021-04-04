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
import org.jbox2d.dynamics.Body;

/**
 * Motor joint definition.
 * 
 * @author dmurph
 */
public class MotorJointDef extends JointDef {
  /**
   * Position of bodyB minus the position of bodyA, in bodyA's frame, in meters.
   */
  public final Vec2 linearOffset = new Vec2();

  /**
   * The bodyB angle minus bodyA angle in radians.
   */
  public float angularOffset;

  /**
   * The maximum motor force in N.
   */
  public float maxForce;

  /**
   * The maximum motor torque in N-m.
   */
  public float maxTorque;

  /**
   * Position correction factor in the range [0,1].
   */
  public float correctionFactor;

  public MotorJointDef() {
    super(JointType.MOTOR);
    angularOffset = 0;
    maxForce = 1;
    maxTorque = 1;
    correctionFactor = 0.3f;
  }

  public void initialize(Body bA, Body bB) {
    bodyA = bA;
    bodyB = bB;
    Vec2 xB = bodyB.getPosition();
    bodyA.getLocalPointToOut(xB, linearOffset);

    float angleA = bodyA.getAngle();
    float angleB = bodyB.getAngle();
    angularOffset = angleB - angleA;
  }
}
