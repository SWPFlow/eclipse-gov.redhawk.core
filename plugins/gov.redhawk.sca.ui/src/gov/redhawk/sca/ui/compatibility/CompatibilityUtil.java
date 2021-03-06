/**
 * This file is protected by Copyright.
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package gov.redhawk.sca.ui.compatibility;

import java.security.Principal;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

/**
 * @since 9.0
 */
public final class CompatibilityUtil {
	private static enum SafeCompatibilityUtil implements ICompatibilityUtil {
		INSTANCE;
		private ICompatibilityUtil provider = new CompatibilityUtilImpl();

		@Override
		public void setFontDataStyle(FontData fontData, int style) {
			provider.setFontDataStyle(fontData, style);
		}

		@Override
		public void disableComboWheelScrollSelect(ComboViewer viewer) {
			provider.disableComboWheelScrollSelect(viewer);
		}

		@Override
		public Principal getUserPrincipal(Display display) {
			return provider.getUserPrincipal(display);
		}

		@Override
		public void runInFakeUIContext(Display display, Runnable runnable) {
			provider.runInFakeUIContext(display, runnable);
		}

		@Override
		public void activateUIConnection(String id) {
			provider.activateUIConnection(id);
		}

		@Override
		public void deactivateUIConnection(String id) {
			provider.deactivateUIConnection(id);
		}

		@Override
		public void executeOnRequestThread(Runnable runnable) {
			provider.executeOnRequestThread(runnable);
		}

	}

	private CompatibilityUtil() {
		//Prevent instantiation
	}

	public static void setFontDataStyle(FontData fontData, int style) {
		SafeCompatibilityUtil.INSTANCE.setFontDataStyle(fontData, style);
	}

	/**
	 * @since 9.1
	 */
	public static void disableComboWheelScrollSelect(ComboViewer viewer) {
		SafeCompatibilityUtil.INSTANCE.disableComboWheelScrollSelect(viewer);
	}

	/**
	 * @since 9.1
	 */
	public static Principal getUserPrincipal(Display display) {
		return SafeCompatibilityUtil.INSTANCE.getUserPrincipal(display);
	}

	/**
	 * @since 9.1
	 */
	public static void runInFakeUIContext(Display display, Runnable runnable) {
		SafeCompatibilityUtil.INSTANCE.runInFakeUIContext(display, runnable);
	}

	/**
	 * @since 9.3
	 */
	public static void executeOnRequestThread(Runnable runnable) {
		SafeCompatibilityUtil.INSTANCE.executeOnRequestThread(runnable);
	}

	/**
	 * @since 9.1
	 */
	public static void activateUIConnection(String id) {
		SafeCompatibilityUtil.INSTANCE.activateUIConnection(id);
	}

	/**
	 * @since 9.1
	 */
	public static void deactivateUIConnection(String id) {
		SafeCompatibilityUtil.INSTANCE.deactivateUIConnection(id);
	}
}
