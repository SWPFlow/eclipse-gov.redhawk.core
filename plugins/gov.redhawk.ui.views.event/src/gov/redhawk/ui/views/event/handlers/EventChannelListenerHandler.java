/*******************************************************************************
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package gov.redhawk.ui.views.event.handlers;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.omg.CosEventChannelAdmin.EventChannel;
import org.omg.CosEventChannelAdmin.EventChannelHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import gov.redhawk.ui.views.event.EventView;
import gov.redhawk.ui.views.event.EventViewPlugin;
import gov.redhawk.model.sca.ScaDomainManager;
import gov.redhawk.model.sca.ScaEventChannel;
import gov.redhawk.ui.views.namebrowser.view.BindingNode;
import mil.jpeojtrs.sca.util.CorbaUtils;
import mil.jpeojtrs.sca.util.ScaEcoreUtils;

public class EventChannelListenerHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get window, page, selection(s)
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null) {
			selection = HandlerUtil.getActiveMenuSelection(event);
		}
		if (!(selection instanceof IStructuredSelection)) {
			return null;
		}
		List< ? > selectedItems = ((IStructuredSelection) selection).toList();

		EventView view = null;
		boolean isMany = selectedItems.size() > 1;
		for (final Object obj : selectedItems) {
			if (obj instanceof BindingNode) {
				view = showBindingNode((BindingNode) obj, page, view, isMany);
			} else if (obj instanceof ScaEventChannel) {
				view = showScaEventChannel((ScaEventChannel) obj, page, view, isMany);
			}
		}

		return null;
	}

	private EventView showBindingNode(final BindingNode node, IWorkbenchPage page, EventView view, boolean isMany) throws ExecutionException {
		// Open the event view
		final String name = node.getPath();
		final EventView finalView;
		try {
			finalView = showView(page, view, isMany, name);
		} catch (PartInitException e) {
			throw new ExecutionException("Failed to open event view.", e);
		}

		// Connect the event channel to the view
		Job connectJob = new Job("Connecting to : " + name) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					CorbaUtils.invoke(new Callable<Object>() {

						@Override
						public Object call() throws Exception {
							try {
								org.omg.CORBA.Object ref = node.getNamingContext().resolve_str(name);
								EventChannel channel = EventChannelHelper.narrow(ref);
								finalView.connect(name, channel);
							} catch (NotFound e) {
								throw new CoreException(new Status(IStatus.ERROR, EventViewPlugin.PLUGIN_ID, "Failed to connect to event channel", e));
							} catch (CannotProceed e) {
								throw new CoreException(new Status(IStatus.ERROR, EventViewPlugin.PLUGIN_ID, "Failed to connect to event channel", e));
							} catch (InvalidName e) {
								throw new CoreException(new Status(IStatus.ERROR, EventViewPlugin.PLUGIN_ID, "Failed to connect to event channel", e));
							}
							return null;
						}
					}, monitor);
					return Status.CANCEL_STATUS;
				} catch (CoreException e1) {
					return new Status(e1.getStatus().getSeverity(), EventViewPlugin.PLUGIN_ID, "Failed to connect to : " + name, e1);
				} catch (InterruptedException e1) {
					return Status.CANCEL_STATUS;
				}

			}

		};
		connectJob.setUser(true);
		connectJob.setSystem(false);
		connectJob.schedule();

		return finalView;
	}

	private EventView showScaEventChannel(final ScaEventChannel channel, IWorkbenchPage page, EventView view, boolean isMany) throws ExecutionException {
		// Find the domain manager
		final ScaDomainManager domMgr = ScaEcoreUtils.getEContainerOfType(channel, ScaDomainManager.class);
		if (domMgr == null) {
			return view;
		}

		// Open the event view
		final String name = domMgr.getLabel() + "/" + channel.getName();
		final EventView finalView;
		try {
			finalView = showView(page, view, isMany, name);
		} catch (PartInitException e) {
			throw new ExecutionException("Failed to open event view.", e);
		}

		// Connect the event channel to the view
		Job connectJob = new Job("Connecting to : " + name) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					CorbaUtils.invoke(new Callable<Object>() {

						@Override
						public Object call() throws Exception {
							finalView.connect(domMgr, channel.getName());
							return null;
						}

					}, monitor);
					return Status.OK_STATUS;
				} catch (CoreException e1) {
					return new Status(e1.getStatus().getSeverity(), EventViewPlugin.PLUGIN_ID, "Failed to connect to : " + name, e1);
				} catch (InterruptedException e1) {
					return Status.CANCEL_STATUS;
				}
			}
		};
		connectJob.setUser(true);
		connectJob.setSystem(false);
		connectJob.schedule();

		return finalView;
	}

	private EventView showView(IWorkbenchPage page, EventView view, boolean isMany, final String name) throws PartInitException {
		if (view == null) {
			String shortName = name;
			if (name.contains("/")) {
				shortName = name.substring(name.lastIndexOf("/") + 1);
			}
			if (isMany) {
				view = (EventView) page.showView(EventView.ID, String.valueOf(System.currentTimeMillis()), IWorkbenchPage.VIEW_ACTIVATE);
			} else {
				view = (EventView) page.showView(EventView.ID, name, IWorkbenchPage.VIEW_ACTIVATE);
				view.setPartName(shortName);
				view.setTitleToolTip(shortName);
			}

			// Show the Properties view whenever the Event View is opened
			page.showView(IPageLayout.ID_PROP_SHEET);
		}

		return view;
	}
}
