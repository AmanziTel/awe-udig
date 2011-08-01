package org.amanzi.neo.services;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.amanzi.neo.services.NewStatisticsService.StatisticsNodeTypes;
import org.amanzi.neo.services.NewStatisticsService.StatisticsRelationships;
import org.amanzi.neo.services.exceptions.DatabaseException;
import org.amanzi.neo.services.exceptions.DuplicateStatisticsException;
import org.amanzi.neo.services.exceptions.InvalidStatisticsParameterException;
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
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

public class NewStatisticsServiceTest extends AbstractAWETest {

	private final static String PROPERTIES = "PROPERTIES";
	private final static String NEIGHBOURS = "Neighbours";
	private final static String NETWORK = "Network";
	private final static String ORIGINAL = "original";

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

	private void cleanReferenceNode() {
		Iterator<Relationship> iter = referenceNode.getRelationships()
				.iterator();
		while (iter.hasNext()) {
			iter.next().delete();
		}
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

		StatisticsVault propVault = new StatisticsVault(PROPERTIES);
		StatisticsVault neighboursSubVault = new StatisticsVault(NEIGHBOURS);
		StatisticsVault networkSubVault = new StatisticsVault(NETWORK);
		propVault.addSubVault(networkSubVault);
		propVault.addSubVault(neighboursSubVault);
		service.saveVault(referenceNode, propVault);

		boolean hasStatisticsRelationships = referenceNode.hasRelationship(
				StatisticsRelationships.STATISTICS, Direction.OUTGOING);
		Assert.assertTrue("not create StatisticsRelationships.STATISTICS",
				hasStatisticsRelationships);

		Node propVaultNode = referenceNode.getSingleRelationship(
				StatisticsRelationships.STATISTICS, Direction.OUTGOING)
				.getEndNode();
		String nodeType = (String) propVaultNode.getProperty(
				NewAbstractService.PROPERTY_TYPE_NAME, "");
		Assert.assertEquals("Vault node has not VAULT type",
				StatisticsNodeTypes.VAULT.getId(), nodeType);

		String nodeName = (String) propVaultNode.getProperty(
				NewStatisticsService.PROPERTY_NAME_NAME, "");
		Assert.assertEquals("", PROPERTIES, nodeName);

		String klass = (String) propVaultNode.getProperty(
				NewStatisticsService.CLASS, null);
		Assert.assertNotNull("Vault node has not property CLASS", klass);
		Assert.assertEquals("Vault node property CLASS is wrong value",
				StatisticsVault.class.toString(), klass);

		boolean hasChildRelationships = propVaultNode.hasRelationship(
				StatisticsRelationships.CHILD, Direction.OUTGOING);
		Assert.assertTrue("not create StatisticsRelationships.CHILD",
				hasChildRelationships);
		Iterator<Relationship> iter = propVaultNode.getRelationships(
				StatisticsRelationships.CHILD, Direction.OUTGOING).iterator();
		int countSubVault = 0;
		while (iter.hasNext()) {
			iter.next();
			countSubVault++;
		}
		Assert.assertEquals(
				"Vault node has wrong count of CHILD relationships", 2,
				countSubVault);

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
		StatisticsVault propVault = new StatisticsVault(PROPERTIES);
		service.saveVault(null, propVault);
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
		service.saveVault(referenceNode, null);
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
	}

	@Test
	public void loadVaultPositiveTest() throws DatabaseException,
			InvalidStatisticsParameterException, DuplicateStatisticsException {
		StatisticsVault propVault = new StatisticsVault(PROPERTIES);
		StatisticsVault neighboursSubVault = new StatisticsVault(NEIGHBOURS);
		StatisticsVault networkSubVault = new StatisticsVault(NETWORK);
		
		propVault.addSubVault(neighboursSubVault);
		neighboursSubVault.addSubVault(networkSubVault);
		service.saveVault(referenceNode, propVault);

		IVault vault = service.loadVault(referenceNode);
		Class<?> actualClass = vault.getClass();
		Assert.assertEquals("load wrong IVault object", StatisticsVault.class,
				actualClass);

		int actualCount = vault.getCount();
		Assert.assertEquals("vault has wrong count", 0, actualCount);
		
		String actualType = vault.getType();
		Assert.assertEquals("vault has wrong type", PROPERTIES, actualType);

		Iterator<IVault> iter = vault.getSubVaults().iterator();
		int count = 0;
		while(iter.hasNext()){
			iter.next();
			count++;
		}
		Assert.assertEquals("vault has wrong count of subVault", 1, count);
		IVault subVault =  vault.getSubVaults().iterator().next();
		
		

		
	}
}
