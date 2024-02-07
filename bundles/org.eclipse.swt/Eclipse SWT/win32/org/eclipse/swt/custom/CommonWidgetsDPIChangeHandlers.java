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
package org.eclipse.swt.custom;

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.internal.*;
import org.eclipse.swt.widgets.*;

public class CommonWidgetsDPIChangeHandlers {


	public static void registerCommonHandlers() {
		DPIZoomChangeRegistry.registerHandler(CommonWidgetsDPIChangeHandlers::handleItemDPIChange, Item.class);
	}

	private static void handleItemDPIChange (DPIChangeEvent event, Widget widget) {
		if (!(widget instanceof Item)) {
			return;
		}
		Item item = (Item) widget;

			// Refresh the image
		Image image = item.getImage();
		if (image != null) {
			image.handleDPIChange(event.newZoom());
			item.setImage(null);
			item.setImage(image);
		}
	}
}
