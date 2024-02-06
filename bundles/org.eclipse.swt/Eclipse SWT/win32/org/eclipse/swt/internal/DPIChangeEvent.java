/*******************************************************************************
 * Copyright (c) 2000, 2024 Yatta Solutions and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Yatta Solutions - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal;

public class DPIChangeEvent {
	private int oldZoom;
	private int newZoom;

	public DPIChangeEvent(int oldZoom, int newZoom) {
		this.oldZoom = oldZoom;
		this.newZoom = newZoom;
	}

	public int newZoom() {
		return newZoom;
	}

	public int oldZoom() {
		return oldZoom;
	}

	public float getScalingFactor() {
		return 1f * newZoom / oldZoom;
	}

	public boolean isDPIChange() {
		return oldZoom != newZoom;
	}

	@Override
	public String toString() {
		return "[oldZoom=" + oldZoom + ", newZoom=" + newZoom + "]";
	}
}
