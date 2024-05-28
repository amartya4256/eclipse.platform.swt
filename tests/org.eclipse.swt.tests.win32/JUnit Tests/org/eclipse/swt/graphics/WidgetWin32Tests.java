package org.eclipse.swt.graphics;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.DPITestUtil;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.WidgetTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WidgetWin32Tests {
	private Display display;
	private boolean autoScaleOnRuntime;

	@Before
	public void setUp() {
		autoScaleOnRuntime = DPIUtil.isAutoScaleOnRuntimeActive();
		DPITestUtil.setAutoScaleOnRunTime(true);
		display = Display.getDefault();
	}

	@After
	public void tearDown() {
		DPITestUtil.setAutoScaleOnRunTime(autoScaleOnRuntime);
	}

	@Test
	public void widgetZoomShouldChangeOnZoomLevelChange() {
		int zoom = DPIUtil.getDeviceZoom();
		int scaledZoom = zoom * 2;
		Shell shell = new Shell(display);

		Button button = new Button(shell, SWT.PUSH);
		button.setBounds(0, 0, 200, 50);
		button.setText("Widget Test");
		button.setBackground(display.getSystemColor(SWT.COLOR_CYAN));
		shell.open();
		changeZoomLevel(scaledZoom, shell);
		assertEquals("The Zoom Level should be updated for button on zoom change event on its shell", scaledZoom, WidgetTestUtil.getZoomOfWidget(button));
	}

	public void changeZoomLevel(int zoomLevel, Control widget) {
		Event event = new Event();
		event.detail = zoomLevel;
		event.type = SWT.ZoomChanged;
		event.doit = true;
		event.widget = widget;
		DPIUtil.setDeviceZoom(zoomLevel);
		widget.notifyListeners(SWT.ZoomChanged, event);
	}

}
