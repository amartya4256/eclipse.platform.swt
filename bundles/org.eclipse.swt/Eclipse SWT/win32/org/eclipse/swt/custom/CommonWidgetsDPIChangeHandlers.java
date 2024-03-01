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
		DPIZoomChangeRegistry.registerHandler(CommonWidgetsDPIChangeHandlers::handleCComboDPIChange, CCombo.class);
		DPIZoomChangeRegistry.registerHandler(CommonWidgetsDPIChangeHandlers::handleCTabFolderDPIChange, CTabFolder.class);
		DPIZoomChangeRegistry.registerHandler(CommonWidgetsDPIChangeHandlers::handleCTabItemDPIChange, CTabItem.class);
		DPIZoomChangeRegistry.registerHandler(CommonWidgetsDPIChangeHandlers::handleItemDPIChange, Item.class);
		DPIZoomChangeRegistry.registerHandler(CommonWidgetsDPIChangeHandlers::handleStyledTextDPIChange, StyledText.class);
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


	private static void handleCComboDPIChange(DPIChangeEvent event, Widget widget) {
		if (!(widget instanceof CCombo)) {
			return;
		}
		CCombo combo = (CCombo) widget;

		DPIZoomChangeRegistry.applyChange(event, combo.text);
		DPIZoomChangeRegistry.applyChange(event, combo.list);
		DPIZoomChangeRegistry.applyChange(event, combo.arrow);
	}

	private static void handleCTabFolderDPIChange(DPIChangeEvent event, Widget widget) {
		if (!(widget instanceof CTabFolder)) {
			return;
		}
		CTabFolder cTabFolder = (CTabFolder) widget;

		for (CTabItem item : cTabFolder.getItems()) {
			DPIZoomChangeRegistry.applyChange(event, item);
		}
		cTabFolder.updateFolder(CTabFolder.UPDATE_TAB_HEIGHT | CTabFolder.REDRAW_TABS);
	}

	private static void handleCTabItemDPIChange(DPIChangeEvent event, Widget widget) {
		if (!(widget instanceof CTabItem)) {
			return;
		}
		CTabItem item = (CTabItem) widget;
		Font itemFont = item.font;
		if (itemFont != null) {
			item.setFont(itemFont);
		}
		Image itemImage = item.getImage();
		if (itemImage != null) {
			itemImage.handleDPIChange(event.newZoom());

		}
		Image itemDisabledImage = item.getDisabledImage();
		if (itemDisabledImage != null) {
			itemDisabledImage.handleDPIChange(event.newZoom());
		}
	}

	private static void handleStyledTextDPIChange(DPIChangeEvent event, Widget widget) {
		if (!(widget instanceof StyledText)) {
			return;
		}
		StyledText styledText = (StyledText) widget;

		DPIZoomChangeRegistry.applyChange(event, styledText.getCaret());
		DPIZoomChangeRegistry.applyChange(event, styledText.ime);

		DPIZoomChangeRegistry.applyChange(event, styledText.defaultCaret);
		for (Caret caret : styledText.carets) {
			DPIZoomChangeRegistry.applyChange(event, caret);
		}

		styledText.updateCaretVisibility();

		styledText.renderer.setFont(styledText.getFont(), styledText.tabLength);
		styledText.setCaretLocations();
	}
}
