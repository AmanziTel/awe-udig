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

package org.amanzi.awe.views.tree.drive.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.amanzi.awe.views.network.proxy.NeoNode;
import org.amanzi.neo.services.INeoConstants;
import org.amanzi.neo.services.enums.CallProperties;
import org.amanzi.neo.services.enums.GeoNeoRelationshipTypes;
import org.amanzi.neo.services.enums.NodeTypes;
import org.amanzi.neo.services.enums.ProbeCallRelationshipType;
import org.amanzi.neo.services.enums.CallProperties.CallType;
import org.amanzi.neo.services.ui.NeoUtils;
import org.amanzi.neo.services.ui.enums.ColoredFlags;
import org.amanzi.neo.services.ui.utils.StatisticSelectionNode;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.TraversalPosition;
import org.neo4j.graphdb.Traverser.Order;

/**
 * TODO Purpose of 
 * <p>
 *
 * </p>
 * @author Lagutko_N
 * @since 1.0.0
 */
public class CallAnalyzisNeoNode extends DriveNeoNode {
    
    private final String type;
    
    private Node statisticsNode;
    
    private String timeStr;

    /**
     * @param node
     */
    public CallAnalyzisNeoNode(Node node, int number) {
        super(node, number);
        
        type = NeoUtils.getNodeType(node);
        
        if (type.equals(NodeTypes.CALL_ANALYSIS_ROOT.getId())) {
            boolean inconclusive = (Boolean)node.getProperty(INeoConstants.PROPERTY_IS_INCONCLUSIVE, false);
            name = "Call Analysis"+(inconclusive?"*":"")+": " + node.getProperty(INeoConstants.PROPERTY_VALUE_NAME) + " (" + node.getProperty(CallProperties.CALL_TYPE.getId()) + ")";
        }        
        else if (type.equals(NodeTypes.S_CELL.getId())) {
            timeStr = getSCellTime(node);
            name = node.getProperty(INeoConstants.PROPERTY_NAME_NAME) + ": " + node.getProperty(INeoConstants.PROPERTY_VALUE_NAME,"0")+" ("+timeStr+")";            
        }        
        hasChildren();
    }
    
    private String getSCellTime(Node cell){
        Node row = NeoUtils.getParent(null, cell);
        return NeoUtils.getNodeName(row);
    }
    
    protected CallAnalyzisNeoNode(Node probeNode, Node statisticsNode, int number) {
        this(probeNode, number);
        
        this.statisticsNode = statisticsNode; 
        hasChildren();
    }
    
    @Override
    public NeoNode[] getChildren() {
        ArrayList<NeoNode> children = new ArrayList<NeoNode>();
        
        Iterator<Node> iterator;
        if (type.equals(NodeTypes.CALL_ANALYSIS_ROOT.getId())) {
            iterator = node.traverse(Order.BREADTH_FIRST, StopEvaluator.DEPTH_ONE, ReturnableEvaluator.ALL_BUT_START_NODE, 
                                     GeoNeoRelationshipTypes.CHILD, Direction.OUTGOING).iterator();
        }
        else if (type.equals(NodeTypes.CALL_ANALYSIS.getId())) {
            StatisticsCallType callType = StatisticsCallType.valueOf((String)getParent().getNode().getProperty(CallProperties.CALL_TYPE.getId()));          
            if (callType.getLevel().equals(StatisticsCallType.FIRST_LEVEL)) {
                Node root = node.getSingleRelationship(GeoNeoRelationshipTypes.CHILD, Direction.INCOMING).getStartNode();
                Node dataset = root.getSingleRelationship(ProbeCallRelationshipType.CALL_ANALYSIS, Direction.INCOMING).getStartNode();
                iterator = NeoUtils.getAllProbesOfDataset(dataset, callType.getId()).iterator();
            }else{
                iterator = NeoUtils.getChildTraverser(node).iterator();
            }
        }
        else if (type.equals(NodeTypes.PROBE.getId())) {
            iterator = NeoUtils.getChildTraverser(statisticsNode, new ReturnableEvaluator() {
                
                @Override
                public boolean isReturnableNode(TraversalPosition currentPos) {
                    Node node2 = currentPos.currentNode();
                    boolean inconclusive = (Boolean)NeoUtils.getParent(null,statisticsNode).getProperty(INeoConstants.PROPERTY_IS_INCONCLUSIVE, false);
                    Node probe;
                    if (inconclusive) {
                        probe = node2.traverse(Order.DEPTH_FIRST, StopEvaluator.END_OF_GRAPH, new ReturnableEvaluator() {

                            @Override
                            public boolean isReturnableNode(TraversalPosition currentPos) {
                                return NeoUtils.isProbeNode(currentPos.currentNode());
                            }
                        }, GeoNeoRelationshipTypes.SOURCE, Direction.OUTGOING).iterator().next();
                    }else{
                        probe = node2.traverse(Order.DEPTH_FIRST, StopEvaluator.DEPTH_ONE, new ReturnableEvaluator() {

                            @Override
                            public boolean isReturnableNode(TraversalPosition currentPos) {
                                return NeoUtils.isProbeNode(currentPos.currentNode());
                            }
                        }, GeoNeoRelationshipTypes.SOURCE, Direction.OUTGOING).iterator().next();
                    }
                    
                    return probe.equals(node);
                }
            }).iterator();
        }
        else if (type.equals(NodeTypes.S_CELL.getId())) {
            iterator = node.traverse(Order.DEPTH_FIRST, StopEvaluator.DEPTH_ONE, new ReturnableEvaluator() {
                
                @Override
                public boolean isReturnableNode(TraversalPosition currentPos) {
                    Node currentNode = currentPos.currentNode();                    
                    return (!currentPos.isStartNode()) && (NeoUtils.isCallNode(currentNode)||NeoUtils.isScellNode(currentNode));
                }
            }, GeoNeoRelationshipTypes.SOURCE, Direction.OUTGOING).iterator();
        }
        else if (type.equals(NodeTypes.CALL.getId())) {
            iterator = node.traverse(Order.DEPTH_FIRST, StopEvaluator.DEPTH_ONE,ReturnableEvaluator.ALL_BUT_START_NODE,ProbeCallRelationshipType.CALL_M, Direction.OUTGOING).iterator();
        } else {
            iterator = NeoUtils.getChildTraverser(node).iterator();
        }
        int nextNum = number+1;
        while (iterator.hasNext()) {
            Node child = iterator.next();
            // TODO refactoring
            children.add(new CallAnalyzisNeoNode(child, node, nextNum++));
            // if (++i <= TRUNCATE_NODE) {
            // } else {
            // children.add(new AggregatesNode(child));
            // break;
            // }
        }      
        Collections.sort(children, new Comparator<NeoNode>() {

            @Override
            public int compare(NeoNode o1, NeoNode o2) {
                if(!((o1 instanceof CallAnalyzisNeoNode)&&(o2 instanceof CallAnalyzisNeoNode)) ){
                    return 0;
                }
                CallAnalyzisNeoNode analyzisNode1 = (CallAnalyzisNeoNode)o1;
                CallAnalyzisNeoNode analyzisNode2 = (CallAnalyzisNeoNode)o2;
                if(!(analyzisNode1.type.equals(NodeTypes.S_CELL.getId())&&analyzisNode2.type.equals(NodeTypes.S_CELL.getId()))){
                    return 0;
                }
                return analyzisNode1.timeStr.compareTo(analyzisNode2.timeStr);
            }
        });
        return children.toArray(NO_NODES);
    }
    
    @Override
    public boolean hasChildren() {
        if (type.equals(NodeTypes.PROBE.getId())) {
            return node.hasRelationship(GeoNeoRelationshipTypes.SOURCE, Direction.INCOMING);
        }
        if (type.equals(NodeTypes.S_CELL.getId())) {
            return node.hasRelationship(GeoNeoRelationshipTypes.SOURCE, Direction.OUTGOING);
        }
        if (type.equals(NodeTypes.CALL.getId())) {
            return node.hasRelationship(ProbeCallRelationshipType.CALL_M, Direction.OUTGOING);
        }
        return node.hasRelationship(GeoNeoRelationshipTypes.CHILD, Direction.OUTGOING);
    }

    /**
     * @return
     */
    public NeoNode getParent() {
        int nextNum = number+1;
        if (type.equals(NodeTypes.PROBE.getId())) {
            return new CallAnalyzisNeoNode(statisticsNode, statisticsNode, nextNum);
        } else if (type.equals(NodeTypes.S_ROW.getId())) {
            boolean inconclusive = (Boolean)NeoUtils.getParent(null,statisticsNode).getProperty(INeoConstants.PROPERTY_IS_INCONCLUSIVE, false);
            Iterator<Node> probeIterator;
            if (inconclusive) {
                probeIterator = node.traverse(Order.DEPTH_FIRST, StopEvaluator.END_OF_GRAPH, new ReturnableEvaluator() {

                    @Override
                    public boolean isReturnableNode(TraversalPosition currentPos) {
                        return NeoUtils.isProbeNode(currentPos.currentNode());
                    }
                }, GeoNeoRelationshipTypes.SOURCE, Direction.OUTGOING).iterator();
            }else{
                probeIterator = node.traverse(Order.DEPTH_FIRST, StopEvaluator.DEPTH_ONE, new ReturnableEvaluator() {

                    @Override
                    public boolean isReturnableNode(TraversalPosition currentPos) {
                        return NeoUtils.isProbeNode(currentPos.currentNode());
                    }
                }, GeoNeoRelationshipTypes.SOURCE, Direction.OUTGOING).iterator();
            }
            Node parentNode;
            if (probeIterator.hasNext()) {
                parentNode = probeIterator.next();
            }else{
                parentNode = NeoUtils.getParent(null, node);
            }
            return new CallAnalyzisNeoNode(parentNode, statisticsNode, nextNum);
        } else {
            return new CallAnalyzisNeoNode(NeoUtils.getParent(null, node), statisticsNode, nextNum);
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = super.equals(obj);
        if (result) {
            if (obj instanceof CallAnalyzisNeoNode && NeoUtils.getNodeType(node, "").equals(NodeTypes.PROBE.getId())) {
                return statisticsNode.equals(((CallAnalyzisNeoNode)obj).statisticsNode);
            }
            if (obj instanceof StatisticSelectionNode && statisticsNode != null && NeoUtils.getNodeType(node, "").equals(NodeTypes.PROBE.getId())) {
                StatisticSelectionNode statNode = (StatisticSelectionNode)obj;
                Node clNode = statNode.getClarifyingNode();
                if (statisticsNode.equals(clNode)) {
                    return true;
                }
                Relationship rel = clNode.getSingleRelationship(GeoNeoRelationshipTypes.CHILD, Direction.INCOMING);
                if (rel != null) {
                    return statisticsNode.equals(rel.getOtherNode(clNode));
                }
            }
        }
        return result;
    }
    
    @Override
    public String getImageKey() {
        if (type.equals(NodeTypes.S_CELL.getId())) {
            ColoredFlags flag = ColoredFlags.getFlagById((String)node.getProperty(INeoConstants.PROPERTY_FLAGGED_NAME, ColoredFlags.NONE.getId()));
            if(!flag.equals(ColoredFlags.NONE)){
                return getType()+"_"+flag.getId();
            }
        }
        return super.getImageKey();
    }
    
    @Override
    public Set<Node> getNodesForMap() {
        if (type.equals(NodeTypes.PROBE.getId())) {
            return NeoUtils.getCallsForProbeNode(node, null);
        }
        if (type.equals(NodeTypes.S_ROW.getId())) {
            return NeoUtils.getCallsForSRowNode(node, null);
        }
        if (type.equals(NodeTypes.S_CELL.getId())) {
            return NeoUtils.getCallsForSCellNode(node, null);
        }
        return super.getNodesForMap();
    }
    /**
     * 
     * <p>
     *Enum of StatisticsCallTypes
     * </p>
     * @author TsAr
     * @since 1.0.0
     */
    public enum StatisticsCallType {
        
        /**
         * Second level statistics.
         */
        AGGREGATION_STATISTICS("Second level",null,55,2),
        /**
         * Individual calls.
         */
        INDIVIDUAL("SC (single call)",CallType.INDIVIDUAL,0, 1),
        
        /**
         * Group calls.
         */
        GROUP("GC (group call)",CallType.GROUP,5,1 ),
        /**
         * SDS messages.
         */
        SDS("SDS",CallType.SDS,10,1),
        /**
         * TSM messages.
         */
        TSM("TSM",CallType.TSM,15,1),
        /**
         * Alarm messages.
         */
        ALARM("Alarm",CallType.ALARM,40,1),
        /**
         * Emergency call type 1.
         */
        EMERGENCY("EC1",CallType.EMERGENCY,20,1),
        /**
         * Emergency call type 2.
         */
        HELP("EC2",CallType.HELP,25,1),
        /**
         * ITSI attach call type.
         */
        ITSI_ATTACH("ITSI-attach",CallType.ITSI_ATTACH,35,1),
        /**
         * ITSI CC (cell change) call type.
         */
        ITSI_CC("Cell change",CallType.ITSI_CC,30,1),
        /**
         * ITSI HO (Handover) call type.
         */
        ITSI_HO("Handover",CallType.ITSI_HO,30,1),
        /**
         * Circuit-switched data call type.                       
         */
        CS_DATA("Circuit-switched",CallType.CS_DATA,45,1),
        /**
         * Packet switched data call type.
         */
        PS_DATA("Packet switched",CallType.PS_DATA,50,1);
        
        
        public static final Integer FIRST_LEVEL = 1;
        public static final Integer SECOND_LEVEL = 2;
        
        private CallType id;
        private String viewName;
        private Integer level;
        private Integer order;
        
        /**
         * Constructor.
         * @param anId CallType
         * @param statHeaders headers
         */
        private StatisticsCallType(String name, CallType anId,Integer orderValue, Integer aLevel ) {
            id = anId;
            level = aLevel;
            viewName = name;
            order = orderValue;
        }
        
        /**
         * @return Returns the id.
         */
        public CallType getId() {
            return id;
        }
        
        
        /**
         * @return Returns the level.
         */
        public Integer getLevel() {
            return level;
        }

        
        /**
         * Returns call type.
         *
         * @param id String
         * @return StatisticsCallTypes
         */
        public static StatisticsCallType getTypeById(String id){
            for(StatisticsCallType type : values()){
                if(type.toString().equals(id)){
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown call type <"+id+">");
        }
        
        /**
         * Returns call type.
         *
         * @param id String
         * @return StatisticsCallTypes
         */
        public static StatisticsCallType getTypeByViewName(String name){
            for(StatisticsCallType type : values()){
                if(type.viewName.equals(name)){
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown call type <"+name+">");
        }
        
        /**
         * Returns call type.
         *
         * @param id CallType
         * @return StatisticsCallTypes
         */
        public static StatisticsCallType getTypeById(CallType id){
            for(StatisticsCallType type : getTypesByLevel(FIRST_LEVEL)){
                if(type.id!=null&&type.id.equals(id)){
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown call type <"+id+">");
        }
        
        public static List<StatisticsCallType> getTypesByLevel(Integer level){
            List<StatisticsCallType> result = new ArrayList<StatisticsCallType>();
            for(StatisticsCallType type : values()){
                if(type.level.equals(level)){
                    result.add(type);
                }
            }
            return result;
        }
        
        /**
         * @return Returns the viewName.
         */
        public String getViewName() {
            return viewName;
        }
        
        public static List<StatisticsCallType> getSortedTypesList(Set<StatisticsCallType> typesSet){
            List<StatisticsCallType> result = new ArrayList<StatisticsCallType>(typesSet);
            Collections.sort(result, new Comparator<StatisticsCallType>() {
                @Override
                public int compare(StatisticsCallType o1, StatisticsCallType o2) {
                    return o1.order.compareTo(o2.order);
                }
            });
            return result;
        }
        
        
    }
}
