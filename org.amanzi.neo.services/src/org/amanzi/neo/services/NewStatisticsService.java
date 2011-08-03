/* AWE - Amanzi Wireless Explorer
 * http://awe.amanzi.org
 * (C) 2008-2009, AmanziTel AB
 *
 * This library is provided under the terms of the Eclipse Public License
 * as described at http://www.eclipse.org/legal/epl-v10.html. Any use,
 * reproduction or distribution of the library constitutes recipient's
 * acceptance of this agreement.
 *
 * This library is distributed WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.amanzi.neo.services;

import org.amanzi.neo.services.enums.INodeType;
import org.amanzi.neo.services.exceptions.DatabaseException;
import org.amanzi.neo.services.exceptions.DuplicateStatisticsException;
import org.amanzi.neo.services.exceptions.InvalidStatisticsParameterException;
import org.amanzi.neo.services.exceptions.LoadVaultException;
import org.amanzi.neo.services.statistic.IVault;
import org.amanzi.neo.services.statistic.StatisticsVault;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

/**
 * TODO Purpose of
 * <p>
 * </p>
 * 
 * @author Kruglik_A
 * @since 1.0.0
 */
public class NewStatisticsService extends NewAbstractService {

    public static final String CLASS = "class";
    public static final String COUNT = "count";

    private static Logger LOGGER = Logger.getLogger(NewAbstractService.class);
    private Transaction tx;

    public enum StatisticsRelationships implements RelationshipType {
        STATISTICS, CHILD;
    }

    public enum StatisticsNodeTypes implements INodeType {
        VAULT;

        @Override
        public String getId() {
            return name();
        }
    }

    /**
     * This method recursively loads subVaults
     * 
     * @param vaultNode - node to which the attached statistics vault
     * @return IVault vault for vault node
     * @throws LoadVaultException - this method may generate exception if vault node has wrong
     *         className
     */
    private IVault loadSubVault(Node vaultNode) throws LoadVaultException {
        IVault result;
        String className = (String)vaultNode.getProperty(CLASS, null);
        try {
            @SuppressWarnings("unchecked")
            Class<IVault> klass = (Class<IVault>)Class.forName(className);
            result = klass.newInstance();

            result.setCount((Integer)vaultNode.getProperty(COUNT, null));
            result.setType((String)vaultNode.getProperty(PROPERTY_NAME_NAME, ""));
            for (Relationship childRelation : vaultNode.getRelationships(StatisticsRelationships.CHILD, Direction.OUTGOING)) {
                result.addSubVault(loadSubVault(childRelation.getEndNode()));
            }
        } catch (Exception e) {
            throw new LoadVaultException(e);
        }
        return result;

    }

    /**
     * this method save vault to database
     * 
     * @param rootNode - node to which the attached statistics vault
     * @param vault - vault of statistics
     * @throws DatabaseException - this method may generate exception if exception occurred while
     *         working with a database
     * @throws InvalidStatisticsParameterException - this method may generate exception if some
     *         parameter is null
     * @throws DuplicateStatisticsException - this method may generate exception if rootNode already
     *         has a statistics
     */
    public void saveVault(Node rootNode, IVault vault) throws DatabaseException, InvalidStatisticsParameterException,
            DuplicateStatisticsException {
        LOGGER.debug("start method saveVault(Node rootNode, IVault vault)");
        if (rootNode == null) {
            LOGGER.error("InvalidStatisticsParameterException: parameter rootNode = null");
            throw new InvalidStatisticsParameterException("rootNode", rootNode);
        }
        if (vault == null) {
            LOGGER.error("InvalidStatisticsParameterException: parameter vault = null");
            throw new InvalidStatisticsParameterException("vault", vault);
        }
        if (rootNode.getRelationships(StatisticsRelationships.STATISTICS, Direction.OUTGOING).iterator().hasNext()) {
            LOGGER.error("DuplicateStatisticsException: for this rootNode already exists statistics");
            throw new DuplicateStatisticsException("for this rootNode already exists statistics");
        }
        Node vaultNode = createNode(StatisticsNodeTypes.VAULT);
        tx = graphDb.beginTx();

        try {
            if (StatisticsNodeTypes.VAULT.getId().equals(getNodeType(rootNode))) {
                rootNode.createRelationshipTo(vaultNode, StatisticsRelationships.CHILD);
            } else {
                rootNode.createRelationshipTo(vaultNode, StatisticsRelationships.STATISTICS);
            }
            vaultNode.setProperty(PROPERTY_NAME_NAME, vault.getType());
            vaultNode.setProperty(COUNT, vault.getCount());
            vaultNode.setProperty(CLASS, vault.getClass().getCanonicalName());
            for (IVault subVault : vault.getSubVaults()) {
                saveVault(vaultNode, subVault);
            }
            tx.success();
        } catch (Exception e) {
            LOGGER.error("Could not create vault node in database", e);
            tx.failure();
            throw new DatabaseException(e);

        } finally {
            tx.finish();
            LOGGER.debug("finishmethod saveVault(Node rootNode, IVault vault)");
        }

    }

    /**
     * this method load vault from database
     * 
     * @param rootNode - node to which the attached statistics
     * @return
     * @throws InvalidStatisticsParameterException - this method may generate exception if rootNode
     *         parameter is null
     * @throws LoadVaultException - this method may generate exception if vault node has wrong
     *         className
     */
    public IVault loadVault(Node rootNode) throws InvalidStatisticsParameterException, LoadVaultException {
        LOGGER.debug("start method loadVault(Node rootNode)");
        if (rootNode == null) {
            throw new InvalidStatisticsParameterException("rootNode", rootNode);
        }

        IVault result;
        if (!rootNode.hasRelationship(StatisticsRelationships.STATISTICS, Direction.OUTGOING)) {
            return new StatisticsVault();
        }
        Node vaultNode = rootNode.getSingleRelationship(StatisticsRelationships.STATISTICS, Direction.OUTGOING).getEndNode();
        try {
            result = loadSubVault(vaultNode);
        } catch (LoadVaultException e) {
            LOGGER.error("LoadVaultException: problems to create IVault");
            throw e;
        }
        LOGGER.debug("finish method loadVault(Node rootNode)");
        return result;
    }

}