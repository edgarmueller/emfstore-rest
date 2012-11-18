/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
/*******************************************************************************
 * Copyright 2008, Shawn O. Pearce <spearce@spearce.org>
 * Copyright 2012, Alexander Aumann <alexander.f.aumann@gmail.com>
 * and other copyright owners as documented in the project's IP log.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 * available at http://www.eclipse.org/org/documents/edl-v10.php
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution.
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 * names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior
 * written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

/**
 * A line space within the graph.
 * <p>
 * Commits are strung onto a lane. For many UIs a lane represents a column. Originally taken from
 * org.eclipse.egit.ui.internal.history.
 */
public class PlotLane implements Serializable {
	private static final long serialVersionUID = 1L;

	private int position;

	// if colors get created, remember to dispose them in the dispose function below!
	private Color color = PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_BLACK);

	private Color lightColor = PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_GRAY);

	/**
	 * Logical location of this lane within the graphing plane.
	 * 
	 * @return location of this lane, 0 through the maximum number of lanes.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Sets the logical position of this lane. See {@link #getPosition()}.
	 * 
	 * @param position The new position of this lane.
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Disposes the lane and frees all reserved resources.
	 */
	public void dispose() {

	}

	/**
	 * Sets this lane's main color.
	 * 
	 * @param color The lane's color.
	 */
	public void setSaturatedColor(Color color) {
		this.color = color;
	}

	/**
	 * @return The lane's main color.
	 */
	public Color getSaturatedColor() {
		return color;
	}

	/**
	 * Sets the light color of this lane {@link #getLightColor()}.
	 * 
	 * @param lightColor The new light color f this commit.
	 */
	public void setLightColor(Color lightColor) {
		this.lightColor = lightColor;
	}

	/**
	 * @return The lane's lighter color, used e.g. for expanded tree lanes.
	 */
	public Color getLightColor() {
		return lightColor;
	}

	@Override
	public int hashCode() {
		return position;
	}

	@Override
	public boolean equals(final Object o) {
		return o == this;
	}
}