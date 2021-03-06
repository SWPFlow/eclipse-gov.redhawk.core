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
// BEGIN GENERATED CODE
package gov.redhawk.model.sca.tests;

import org.eclipse.emf.transaction.util.TransactionUtil;
import org.jacorb.JacorbUtil;
import org.junit.Assert;

import CF.PropertySetPackage.InvalidConfiguration;
import CF.PropertySetPackage.PartialConfiguration;
import gov.redhawk.model.sca.RefreshDepth;
import gov.redhawk.model.sca.ScaAbstractProperty;
import gov.redhawk.model.sca.ScaComponent;
import gov.redhawk.model.sca.ScaFactory;
import gov.redhawk.model.sca.ScaSimpleProperty;
import gov.redhawk.model.sca.ScaWaveform;
import gov.redhawk.model.sca.commands.ScaModelCommand;
import gov.redhawk.model.sca.tests.stubs.ScaTestConstaints;
import junit.textui.TestRunner;
import mil.jpeojtrs.sca.prf.PrfFactory;
import mil.jpeojtrs.sca.prf.PropertyValueType;
import mil.jpeojtrs.sca.prf.Simple;
import mil.jpeojtrs.sca.prf.SimpleRef;
import mil.jpeojtrs.sca.prf.StructRef;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Simple Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link gov.redhawk.model.sca.ScaSimpleProperty#createPropertyRef() <em>Create Property Ref</em>}</li>
 * <li>{@link gov.redhawk.model.sca.ScaSimpleProperty#setValueFromRef(mil.jpeojtrs.sca.prf.SimpleRef) <em>Set Value From
 * Ref</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class ScaSimplePropertyTest extends ScaAbstractPropertyTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ScaSimplePropertyTest.class);
	}

	/**
	 * Constructs a new Simple Property test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScaSimplePropertyTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Simple Property test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ScaSimpleProperty getFixture() {
		return (ScaSimpleProperty) fixture;
	}

	private TestEnvirornment env;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		this.env = TestEnvirornment.getInstance();
		final ScaWaveform waveform = this.env.getDomMgr().getWaveformFactories().get(0).createWaveform(null, "name", null, null);
		Assert.assertNotNull(waveform);
		waveform.refresh(null, RefreshDepth.FULL);
		this.env.validateStartState();
		Assert.assertNotNull(waveform);
		ScaModelCommand.execute(this.env.getDomMgr(), new ScaModelCommand() {

			@Override
			public void execute() {
				final ScaComponent kitchenSink = waveform.findComponent(ScaTestConstaints.DCE_KITCHEN_SINK_COMPONENT);
				if (kitchenSink == null && ScaTestsUtil.DEBUG.enabled) {
					ScaTestsUtil.DEBUG.message("Invalid state: {0}", waveform);
				}
				if (kitchenSink == null) {
					return;
				}
				final ScaAbstractProperty< ? > prop = kitchenSink.getProperty(ScaTestConstaints.DCE_SIMPLE_STRING_PROP);
				if (prop == null && ScaTestsUtil.DEBUG.enabled) {
					ScaTestsUtil.DEBUG.message("Invalid state: {0}", kitchenSink);
				}
				setFixture(prop);
			}

		});
		Assert.assertNotNull(getFixture());
		Assert.assertNotNull(TransactionUtil.getEditingDomain(getFixture()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated NOT
	 */
	@Override
	protected void tearDown() throws Exception {
		this.env = null;

		setFixture(null);
	}

	/**
	 * Tests the '{@link gov.redhawk.model.sca.ScaSimpleProperty#createPropertyRef() <em>Create Property Ref</em>}'
	 * operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see gov.redhawk.model.sca.ScaSimpleProperty#createPropertyRef()
	 * @generated NOT
	 */
	public void testCreatePropertyRef() {
		ScaSimpleProperty prop = getFixture();
		SimpleRef ref = prop.createPropertyRef();
		Assert.assertEquals(prop.getId(), ref.getRefID());
		Assert.assertNotNull(ref.getValue());
	}

	// END GENERATED CODE

	private ScaSimpleProperty pre_testSetValueFromRef() {
		final String ID = "abc";

		ScaSimpleProperty prop = ScaFactory.eINSTANCE.createScaSimpleProperty();
		Simple simple = PrfFactory.eINSTANCE.createSimple();
		simple.setId(ID);
		simple.setType(PropertyValueType.LONG);
		simple.setValue("123");
		prop.setDefinition(simple);
		Assert.assertEquals(123, prop.getValue());
		Assert.assertTrue(prop.getStatus().isOK());

		return prop;
	}

	public void testSetValueFromRef__AbstractPropertyRef() {
		final String ID = "abc";

		ScaSimpleProperty prop = pre_testSetValueFromRef();

		StructRef structRef = PrfFactory.eINSTANCE.createStructRef();
		structRef.setRefID(ID);
		SimpleRef member = PrfFactory.eINSTANCE.createSimpleRef("somememberid", "123");
		structRef.getSimpleRef().add(member);
		prop.setValueFromRef(structRef);
		Assert.assertEquals(123, prop.getValue());
		Assert.assertFalse(prop.getStatus().isOK());
	}

	// BEGIN GENERATED CODE

	/**
	 * Tests the '{@link gov.redhawk.model.sca.ScaSimpleProperty#setValueFromRef(mil.jpeojtrs.sca.prf.SimpleRef) <em>Set
	 * Value From Ref</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see gov.redhawk.model.sca.ScaSimpleProperty#setValueFromRef(mil.jpeojtrs.sca.prf.SimpleRef)
	 * @generated NOT
	 */
	public void testSetValueFromRef__SimpleRef() {
		// END GENERATED CODE
		final String ID = "abc";

		ScaSimpleProperty prop = pre_testSetValueFromRef();

		SimpleRef simpleRef = PrfFactory.eINSTANCE.createSimpleRef(ID, "456");
		prop.setValueFromRef(simpleRef);
		Assert.assertEquals(456, prop.getValue());
		Assert.assertTrue(prop.getStatus().isOK());

		simpleRef = PrfFactory.eINSTANCE.createSimpleRef(ID, "bad");
		prop.setValueFromRef(simpleRef);
		Assert.assertEquals(456, prop.getValue());
		Assert.assertFalse(prop.getStatus().isOK());
		// BEGIN GENERATED CODE
	}

	/**
	 * Tests the '{@link gov.redhawk.model.sca.ScaSimpleProperty#getValue() <em>Value</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see gov.redhawk.model.sca.ScaSimpleProperty#getValue()
	 * @generated NOT
	 */
	public void testGetValue() {
		// END GENERATED CODE
		ScaModelCommand.execute(getFixture(), new ScaModelCommand() {

			@Override
			public void execute() {
				Assert.assertNotNull(getFixture().getValue());
			}

		});

		// BEGIN GENERATED CODE
	}

	/**
	 * Tests the '{@link gov.redhawk.model.sca.ScaSimpleProperty#setValue(java.lang.Object) <em>Value</em>}' feature
	 * setter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see gov.redhawk.model.sca.ScaSimpleProperty#setValue(java.lang.Object)
	 * @generated NOT
	 */
	public void testSetValue() {
		Assert.assertNotNull(getFixture().getValue());
		final String value = "test";

		ScaModelCommand.execute(getFixture(), new ScaModelCommand() {

			@Override
			public void execute() {
				getFixture().setValue(value);
				Assert.assertEquals(value, getFixture().getValue());
				getFixture().setValue(null);
				Assert.assertNull(getFixture().getValue());
			}
		});
	}

	/**
	 * Tests the '{@link gov.redhawk.model.sca.ScaSimpleProperty#setRemoteValue(java.lang.Object) <em>Set Remote
	 * Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws InvalidConfiguration
	 * @throws PartialConfiguration
	 * @see gov.redhawk.model.sca.ScaSimpleProperty#setRemoteValue(java.lang.Object)
	 * @generated NOT
	 */
	public void testSetRemoteValue__Object() throws PartialConfiguration, InvalidConfiguration {
		// END GENERATED CODE
		getFixture().setRemoteValue(JacorbUtil.init().create_any());
		// BEGIN GENERATED CODE
	}

	protected void setNewValue() {
		ScaModelCommand.execute(getFixture(), new ScaModelCommand() {
			@Override
			public void execute() {
				getFixture().setValue("temp value");
			}
		});
	}

	protected void clearAndResetDefintion() {
		ScaModelCommand.execute(getFixture(), new ScaModelCommand() {
			@Override
			public void execute() {
				Simple simple = getFixture().getDefinition();
				getFixture().setDefinition(null);
				getFixture().setDefinition(simple);
			}
		});
	}

} // ScaSimplePropertyTest
