/*******************************************************************************
 * Copyright (c) 2024 Yatta Solutions
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
package org.eclipse.swt.widgets;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.SWTFontProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Automated Tests for class org.eclipse.swt.widgets.Control
 * for Windows specific behavior
 *
 * @see org.eclipse.swt.widgets.Control
 */
public class ControlWin32Tests {
	private Display display;

	@Before
	public void setUp() {
		display = Display.getDefault();
		// dispose a probably existing font registry for the default display
		SWTFontProvider.disposeFontRegistry(display);
	}

	@After
	public void tearDown() {
		// dispose all created fonts for the default display
		SWTFontProvider.disposeFontRegistry(display);
	}

	@Test
	public void scaleFontCorrectlyInAutoScaleSzenario() {
		changeAutoScaleOnRuntime(true);
		assertTrue("Autoscale property is not set to true", DPIUtil.isAutoScaleOnRuntimeActive());

		int scalingFactor = 2;
		Shell shell = new Shell(display);
		Control control = new Composite(shell, SWT.NONE);
		int zoom = shell.getNativeZoom();
		try {
			Font oldFont = control.getFont();
			shell.setNativeZoom(zoom * scalingFactor);
			control.setFont(oldFont);
			Font newFont = control.getFont();
			FontData fontData = oldFont.getFontData()[0];
			FontData currentFontData = newFont.getFontData()[0];
			int heightInPixels = fontData.data.lfHeight;
			int currentHeightInPixels = currentFontData.data.lfHeight;
			assertEquals("Font height in points is different on different zoom levels", fontData.getHeight(), currentFontData.getHeight());
			assertEquals("Font height in pixels is not adjusted according to the scale factor", heightInPixels * scalingFactor, currentHeightInPixels);
		} finally {
			control.dispose();
			shell.dispose();
			changeAutoScaleOnRuntime(false);
		}
	}

	@Test
	public void doNotScaleFontCorrectlyInNoAutoScaleSzenario() {
		assertFalse("Autoscale property is not set to false", DPIUtil.isAutoScaleOnRuntimeActive());

		Shell shell = new Shell(display);
		Control control = new Composite(shell, SWT.NONE);
		try {
			Font oldFont = control.getFont();
			control.setFont(oldFont);
			Font newFont = control.getFont();
			FontData fontData = oldFont.getFontData()[0];
			FontData currentFontData = newFont.getFontData()[0];
			int heightInPixels = fontData.data.lfHeight;
			int currentHeightInPixels = currentFontData.data.lfHeight;
			assertEquals("Font height in points is different on different zoom levels", fontData.getHeight(), currentFontData.getHeight());
			assertEquals("Font height in pixels is different when setting the same font again", heightInPixels, currentHeightInPixels);
		} finally {
			control.dispose();
			shell.dispose();
		}
	}

	private void changeAutoScaleOnRuntime(boolean value) {
		try {
			Field autoScaleOnRuntimeField = DPIUtil.class.getDeclaredField("autoScaleOnRuntime");
			autoScaleOnRuntimeField.setAccessible(true);
			autoScaleOnRuntimeField.setBoolean(null, value);
			autoScaleOnRuntimeField.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail("Value for DPI::autoScaleOnRuntime could not be changed");
		}
	}
}
