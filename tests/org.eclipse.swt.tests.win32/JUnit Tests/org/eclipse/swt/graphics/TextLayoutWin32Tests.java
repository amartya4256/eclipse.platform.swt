package org.eclipse.swt.graphics;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

public class TextLayoutWin32Tests {
	private Display display;
	final static String text = "This is a text for testing.";

	@Before
	public void setUp() {
		display = Display.getDefault();
	}

	@Test
	public void getBoundPublicAPIshouldReturnTheSameValueRegardlessOfZoomLevel() {
		final TextLayout layout = new TextLayout(display);
		GCData unscaledData = new GCData();
		unscaledData.nativeDeviceZoom = DPIUtil.getNativeDeviceZoom();
		GC gc = GC.win32_new(display, unscaledData);
		layout.draw(gc, 10, 10);
		Rectangle unscaledBounds = layout.getBounds();

		int scalingFactor = 2;
		int newZoom = DPIUtil.getNativeDeviceZoom() * scalingFactor;
		GCData scaledData = new GCData();
		scaledData.nativeDeviceZoom = newZoom;
		GC scaledGc = GC.win32_new(display, unscaledData);
		layout.draw(scaledGc, 10, 10);
		Rectangle scaledBounds = layout.getBounds();

		assertEquals("The public API for getBounds should give the same result for any zoom level", scaledBounds, unscaledBounds);
	}

}
