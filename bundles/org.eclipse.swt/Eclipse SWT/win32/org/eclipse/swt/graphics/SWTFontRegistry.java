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
package org.eclipse.swt.graphics;

public interface SWTFontRegistry {

	/**
	 * Returns a system font optimally suited for the specified zoom level.
	 *
     * @param deviceZoom zoom level to determine the appropriate system font
     * @return the system font best suited for the specified zoom level
	 */
	Font getSystemFont(int deviceZoom);

	/**
     * Provides a font optimally suited for the specified zoom level. Fonts created in this manner
     * are managed by the font registry and should not be disposed of externally.
     *
     * @param fontData the data used to create the font
     * @param sdeviceZoom zoom level to determine the appropriate font
     * @return the font best suited for the specified zoom level
     */
	Font getFont(FontData fontData, int deviceZoom);

	/**
     * Disposes of all fonts managed by the font registry.
	 */
	void dispose();
}
