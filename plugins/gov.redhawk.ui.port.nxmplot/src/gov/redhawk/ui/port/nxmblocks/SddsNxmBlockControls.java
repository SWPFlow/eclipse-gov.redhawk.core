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
package gov.redhawk.ui.port.nxmblocks;

import java.nio.ByteOrder;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Adjust/override SDDS NXM block settings user entry dialog.
 * @noreference This class is provisional/beta and is subject to API changes
 * @since 4.4
 */
public class SddsNxmBlockControls {

	private final SddsNxmBlockSettings settings;
	private final DataBindingContext dataBindingCtx;

	// widgets
	private Text connectionIDField;
	private ComboViewer dataByteOrderField;

	public SddsNxmBlockControls(SddsNxmBlockSettings settings, DataBindingContext dataBindingCtx) {
		this.settings = settings;
		this.dataBindingCtx = dataBindingCtx;
	}

	public void createControls(final Composite container) {
		container.setLayout(new GridLayout(2, false));
		Label label;

		// === connection ID ==
		label = new Label(container, SWT.None);
		label.setText("Connection ID:");
		connectionIDField = new Text(container, SWT.BORDER);
		connectionIDField.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		connectionIDField.setToolTipText("Custom Port connection ID to use vs a generated one.");
		if (this.settings.getConnectionID() != null && !this.settings.getConnectionID().isEmpty()) {
			// Can't change the ID after it has been set
			connectionIDField.setEditable(false);
			connectionIDField.setEnabled(false);
		}

		// === data byte order ===
		label = new Label(container, SWT.NONE);
		label.setText("Data Byte Order:");
		dataByteOrderField = new ComboViewer(container, SWT.READ_ONLY);
		dataByteOrderField.getCombo().setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		dataByteOrderField.getCombo().setToolTipText("Custom data byte order to override value in SDDS packets.");
		dataByteOrderField.setContentProvider(ArrayContentProvider.getInstance());
		dataByteOrderField.setLabelProvider(new LabelProvider());
		dataByteOrderField.setInput(new ByteOrder[] { ByteOrder.BIG_ENDIAN, ByteOrder.LITTLE_ENDIAN });

		addDataBindings();
	}

	@SuppressWarnings("unchecked")
	protected void addDataBindings() {
		IObservableValue< ? > target = WidgetProperties.text(SWT.Modify).observe(connectionIDField);
		IObservableValue< ? > model = PojoProperties.value(SddsNxmBlockSettings.PROP_CONNECTION_ID).observe(settings);
		dataBindingCtx.bindValue(target, model);

		target = ViewerProperties.singleSelection().observe(this.dataByteOrderField);
		model = PojoProperties.value(SddsNxmBlockSettings.PROP_DATA_BYTE_ORDER).observe(this.settings);
		dataBindingCtx.bindValue(target, model);
	}

}
