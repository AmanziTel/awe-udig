package org.amanzi.neo.services;

import java.util.Iterator;

import junit.framework.Assert;
import org.amanzi.neo.services.NewStatisticsService.StatisticsNodeTypes;
import org.amanzi.neo.services.NewStatisticsService.StatisticsRelationships;
import org.amanzi.neo.services.exceptions.DatabaseException;
import org.amanzi.neo.services.exceptions.DuplicateStatisticsException;
import org.amanzi.neo.services.exceptions.InvalidStatisticsParameterException;
import org.amanzi.neo.services.exceptions.LoadVaultException;
import org.amanzi.neo.services.statistic.IVault;
import org.amanzi.neo.services.statistic.StatisticsVault;
import org.amanzi.testing.AbstractAWETest;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class NewStatisticsServiceTest extends AbstractAWETest {

	private final static String PROPERTIES = "PROPERTIES";
	private final static String NEIGHBOURS = "Neighbours";
	private final static String NETWORK = "Network";

	private static Logger LOGGER = Logger
			.getLogger(NewDatasetServiceTest.class);

	@BeforeClass
	public static final void beforeClass() {
		clearDb();
		initializeDb();
	}

	@AfterClass
	public static final void afterClass() {
		stopDb();
		clearDb();
	}

	private Transaction tx;
	private NewStatisticsService service = new NewStatisticsService();
	private Node referenceNode = graphDatabaseService.getReferenceNode();

	@Before
	public final void before() {

	}

	@After
	public final void after() {

		cleanReferenceNode();
	}

	/**
	 * delete all referenceNode relationships
	 */
	private void cleanReferenceNode() {
		Iterator<Relationship> iter = referenceNode.getRelationships()
				.iterator();
		while (iter.hasNext()) {
			iter.next().delete();
		}
	}

	/**
	 * this method checks vault for compliance with the expected values
	 * 
	 * @param vault
	 * @param expectedClass
	 * @param expectedCount
	 * @param expectedType
	 * @param expectedCountSubVault
	 */
	private void checkVault(IVault vault, Class<?> expectedClass,
			int expectedCount, String expectedType, int expectedCountSubVault) {
		Class<?> actualClass = vault.getClass();
		Assert.assertEquals("load wrong IVault object", expectedClass,
				actualClass);

		int actualCount = vault.getCount();
		Assert.assertEquals("vault has wrong count", expectedCount, actualCount);

		String actualType = vault.getType();
		Assert.assertEquals("vault has wrong type", expectedType, actualType);

		Iterator<IVault> iter = vault.getSubVaults().iterator();
		int count = 0;
		while (iter.hasNext()) {
			iter.next();
			count++;
		}
		Assert.assertEquals("vault has wrong count of subVault",
				expectedCountSubVault, count);
	}

	/**
	 * this method checks vault node for compliance with the expected values
	 * 
	 * @param vaultNode
	 * @param expectedName
	 * @param expectedClass
	 * @param expectedCount
	 * @param expectedCountSubVault
	 */
	private void checkVaultNode(Node vaultNode, String expectedName,
			Class<?> expectedClass, int expectedCount, int expectedCountSubVault) {
		String nodeType = (String) vaultNode.getProperty(
				NewAbstractService.PROPERTY_TYPE_NAME, "");
		Assert.assertEquals("Vault node has not VAULT type",
				StatisticsNodeTypes.VAULT.getId(), nodeType);

		String nodeName = (String) vaultNode.getProperty(
				NewStatisticsService.PROPERTY_NAME_NAME, "");
		Assert.assertEquals("", expectedName, nodeName);

		String klass = (String) vaultNode.getProperty(
				NewStatisticsService.CLASS, null);
		Assert.assertNotNull("Vault node has not property CLASS", klass);
		Assert.assertEquals("Vault node property CLASS is wrong value",
				expectedClass.getCanonicalName(), klass);

		int count = (Integer) vaultNode.getProperty(NewStatisticsService.COUNT,
				null);
		Assert.assertEquals("Vault node has wrong count", expectedCount, count);

		Iterator<Relationship> iter = vaultNode.getRelationships(
				StatisticsRelationships.CHILD, Direction.OUTGOING).iterator();
		int countSubVault = 0;
		while (iter.hasNext()) {
			iter.next();
			countSubVault++;
		}
		Assert.assertEquals(
				"Vault node has wrong count of CHILD relationships",
				expectedCountSubVault, countSubVault);
	}

	/**
	 * testing method saveVault(Node rootNode, IVault vault)
	 * 
	 * @throws DatabaseException
	 * @throws InvalidStatisticsParameterException
	 * @throws DuplicateStatisticsException
	 */
	@Test
	public void saveVaultPositiveTest() throws DatabaseException,
			InvalidStatisticsParameterException, DuplicateStatisticsException {
		LOGGER.debug("start saveVaultPositiveTest()");
		StatisticsVault propVault = new StatisticsVault(PROPERTIES);
		StatisticsVault neighboursSubVault = new StatisticsVault(NEIGHBOURS);
		StatisticsVault networkSubVault = new StatisticsVault(NETWORK);
		propVault.addSubVault(neighboursSubVault);
		neighboursSubVault.addSubVault(networkSubVault);
		service.saveVault(referenceNode, propVault);

		boolean hasStatisticsRelationships = referenceNode.hasRelationship(
				StatisticsRelationships.STATISTICS, Direction.OUTGOING);
		Assert.assertTrue("not create StatisticsRelationships.STATISTICS",
				hasStatisticsRelationships);

		Node propVaultNode = referenceNode.getSingleRelationship(
				StatisticsRelationships.STATISTICS, Direction.OUTGOING)
				.getEndNode();
		checkVaultNode(propVaultNode, PROPERTIES, StatisticsVault.class, 0, 1);

		Node neighbourtsVaultNode = propVaultNode
				.getRelationships(StatisticsRelationships.CHILD,
						Direction.OUTGOING).iterator().next().getEndNode();
		checkVaultNode(neighbourtsVaultNode, NEIGHBOURS, StatisticsVault.class,
				0, 1);
		Node networkVaultNode = neighbourtsVaultNode
				.getRelationships(StatisticsRelationships.CHILD,
						Direction.OUTGOING).iterator().next().getEndNode();
		checkVaultNode(networkVaultNode, NETWORK, StatisticsVault.class, 0, 0);
		LOGGER.debug("finish saveVaultPositiveTest()");

	}

	/**
	 * testing method saveVault(Node rootNode, IVault vault) when parameter
	 * rootNode == null
	 * 
	 * @throws DatabaseException
	 * @throws InvalidStatisticsParameterException
	 * @throws DuplicateStatisticsException
	 */
	@Test(expected = InvalidStatisticsParameterException.class)
	public void saveVaultNullParameterRootNodeNegativeTest()
			throws DatabaseException, InvalidStatisticsParameterException,
			DuplicateStatisticsException {
		LOGGER.debug("start saveVaultNullParameterRootNodeNegativeTest()");
		StatisticsVault propVault = new StatisticsVault(PROPERTIES);
		service.saveVault(null, propVault);
		LOGGER.debug("finish saveVaultNullParameterRootNodeNegativeTest()");

	}

	/**
	 * testing method saveVault(Node rootNode, IVault vault) when parameter
	 * vault == null
	 * 
	 * @throws DatabaseException
	 * @throws InvalidStatisticsParameterException
	 * @throws DuplicateStatisticsException
	 */
	@Test(expected = InvalidStatisticsParameterException.class)
	public void saveVaultNullParameterVaultNegativeTest()
			throws DatabaseException, InvalidStatisticsParameterException,
			DuplicateStatisticsException {
		LOGGER.debug("start saveVaultNullParameterVaultNegativeTest()");
		service.saveVault(referenceNode, null);
		LOGGER.debug("finish saveVaultNullParameterVaultNegativeTest()");

	}

	/**
	 * testing method saveVault(Node rootNode, IVault vault) when rootNode
	 * already exists statistics
	 * 
	 * @throws DatabaseException
	 * @throws InvalidStatisticsParameterException
	 * @throws DuplicateStatisticsException
	 */
	@Test(expected = DuplicateStatisticsException.class)
	public void saveVaultDuplicateStatisticsNegativeTest()
			throws DatabaseException, InvalidStatisticsParameterException,
			DuplicateStatisticsException {
		LOGGER.debug("start saveVaultDuplicateStatisticsNegativeTest()");
		StatisticsVault propVault = new StatisticsVault(PROPERTIES);
		tx = graphDatabaseService.beginTx();
		try {
			Node statNode = graphDatabaseService.createNode();
			referenceNode.createRelationshipTo(statNode,
					StatisticsRelationships.STATISTICS);
			tx.success();
		} finally {
			tx.finish();
		}
		service.saveVault(referenceNode, propVault);
		LOGGER.debug("finish saveVaultDuplicateStatisticsNegativeTest()");

	}

	/**
	 * testing method loadVault(Node rootNode)
	 * 
	 * @throws DatabaseException
	 * @throws InvalidStatisticsParameterException
	 * @throws DuplicateStatisticsException
	 * @throws LoadVaultException
	 */
	@Test
	public void loadVaultPositiveTest() throws DatabaseException,
			InvalidStatisticsParameterException, DuplicateStatisticsException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, LoadVaultException {
		LOGGER.debug("start loadVaultPositiveTest()");
		StatisticsVault propVault = new StatisticsVault(PROPERTIES);
		StatisticsVault neighboursSubVault = new StatisticsVault(NEIGHBOURS);
		StatisticsVault networkSubVault = new StatisticsVault(NETWORK);

		propVault.addSubVault(neighboursSubVault);
		neighboursSubVault.addSubVault(networkSubVault);
		service.saveVault(referenceNode, propVault);

		IVault vault = service.loadVault(referenceNode);
		checkVault(vault, StatisticsVault.class, 0, PROPERTIES, 1);
		IVault subVault = vault.getSubVaults().iterator().next();
		checkVault(subVault, StatisticsVault.class, 0, NEIGHBOURS, 1);
		subVault = subVault.getSubVaults().iterator().next();
		checkVault(subVault, StatisticsVault.class, 0, NETWORK, 0);
		LOGGER.debug("finish loadVaultPositiveTest()");

	}

	/**
	 * testing method loadVault(Node rootNode) when parameter rootNode = null
	 * 
	 * @throws InvalidStatisticsParameterException
	 * @throws LoadVaultException
	 */
	@Test(expected = InvalidStatisticsParameterException.class)
	public void loadVaultNullParameterNegativeTest()
			throws InvalidStatisticsParameterException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, LoadVaultException {
		LOGGER.debug("start loadVaultNullParameterNegativeTest()");
		service.loadVault(null);
		LOGGER.debug("finish loadVaultNullParameterNegativeTest()");

	}

	/**
	 * testing method loadVault(Node rootNode) when rootNode has not statistics
	 * vault
	 * 
	 * @throws InvalidStatisticsParameterException
	 * @throws LoadVaultException
	 */
	@Test
	public void loadVaultEmptyPositiveTest()
			throws InvalidStatisticsParameterException, LoadVaultException {
		LOGGER.debug("start loadVaultEmptyPositiveTest()");
		IVault vault = service.loadVault(referenceNode);
		checkVault(vault, StatisticsVault.class, 0, "", 0);
		LOGGER.debug("finish loadVaultEmptyPositiveTest()");
	}

	/**
	 * testing method loadVault(Node rootNode) when vaultNode has wrong CLASS
	 * property
	 * 
	 * @throws DatabaseException
	 * @throws InvalidStatisticsParameterException
	 * @throws DuplicateStatisticsException
	 * @throws LoadVaultException
	 */
	@Test(expected = LoadVaultException.class)
	public void loadVaultExceptionNegativeTest() throws DatabaseException,
			InvalidStatisticsParameterException, DuplicateStatisticsException,
			LoadVaultException {
		LOGGER.debug("start loadVaultExceptionNegativeTest()");
		StatisticsVault propVault = new StatisticsVault(PROPERTIES);
		service.saveVault(referenceNode, propVault);
		Node vaultNode = referenceNode.getSingleRelationship(
				StatisticsRelationships.STATISTICS, Direction.OUTGOING)
				.getEndNode();
		tx = graphDatabaseService.beginTx();
		try {
			vaultNode.setProperty(NewStatisticsService.CLASS, "wrong_class");
			tx.success();
		} finally {
			tx.finish();
		}
		service.loadVault(referenceNode);
		LOGGER.debug("finish loadVaultExceptionNegativeTest()");
	}

}