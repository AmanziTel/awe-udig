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

package org.amanzi.neo.data_generator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.amanzi.neo.data_generator.data.calls.GeneratedCallsData;
import org.amanzi.neo.data_generator.generate.IDataGenerator;
import org.amanzi.neo.data_generator.generate.calls.GroupCallsGenerator;
import org.amanzi.neo.data_generator.generate.calls.IndividualCallsGenerator;
import org.amanzi.neo.data_generator.generate.nemo.NemoDataGenerator;
import org.amanzi.neo.data_generator.generate.nokia.NokiaTopologyGenerator;
import org.amanzi.neo.data_generator.utils.NeoDataUtils;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * Manager for getting generators for different data.
 * </p>
 * 
 * @author Shcharbatsevich_A
 * @since 1.0.0
 */
public class DataGenerateManager {

    /**
     * Returns AMS data generator for individual calls.
     * 
     * @param aDirectory String (path to save data)
     * @param aHours Integer (count of hours)
     * @param aHourDrift Integer (drift of start time)
     * @param aCallsPerHour Integer (call count in hour)
     * @param aCallPerHourVariance Integer (call variance in hour)
     * @param aProbes Integer (probes count)
     * @return AmsDataGenerator.
     */
    public static IDataGenerator getIndividualAmsGenerator(String aDirectory, Integer aHours, Integer aHourDrift, Integer aCallsPerHour, Integer aCallPerHourVariance,
            Integer aProbes) {
        return new IndividualCallsGenerator(aDirectory, aHours, aHourDrift, aCallsPerHour, aCallPerHourVariance, aProbes);
    }

    /**
     * Returns AMS data generator for group calls.
     * 
     * @param aDirectory String (path to save data)
     * @param aHours Integer (count of hours)
     * @param aHourDrift Integer (drift of start time)
     * @param aCallsPerHour Integer (call count in hour)
     * @param aCallPerHourVariance Integer (call variance in hour)
     * @param aProbes Integer (probes count)
     * @param aMaxGroupSize the a max group size
     * @return AmsDataGenerator.
     */
    public static IDataGenerator getGroupAmsGenerator(String aDirectory, Integer aHours, Integer aHourDrift, Integer aCallsPerHour, Integer aCallPerHourVariance,
            Integer aProbes, Integer aMaxGroupSize) {
        return new GroupCallsGenerator(aDirectory, aHours, aHourDrift, aCallsPerHour, aCallPerHourVariance, aProbes, aMaxGroupSize);
    }

    /**
     * Returns Nokia Topology data generator.
     * 
     * @param aPath String (path to save file)
     * @param aFileName String (file name)
     * @param bscs Integer (BSCs count)
     * @param sites Integer (maximum sites count for one BSC)
     * @param sectors Integer (maximum sectors count for one site)
     * @param extUmtsCount Integer (external UMTS sectors count)
     * @param latBorders Float[] (must be like {min_latitude,max_latitude})
     * @param lonBorders Float[] (must be like {min_longitude,max_longitude})
     * @return NokiaTopologyGenerator
     */
    public static IDataGenerator getNokiaTopologyGenerator(String aPath, String aFileName, Integer bscs, Integer sites, Integer sectors, Integer extUmtsCount,
            Float[] latBorders, Float[] lonBorders) {
        return new NokiaTopologyGenerator(aPath, aFileName, bscs, sites, sectors, extUmtsCount, latBorders, lonBorders);
    }

    /**
     * Returns generator for ams data.
     * 
     * @param data IDataGenerator generated ams data
     * @param aDirectory the a directory
     * @param aFileName the a file name
     * @return the nemo data generator
     */
    public static IDataGenerator getNemoDataGenerator(GeneratedCallsData data, String aDirectory, String aFileName) {
        return new NemoDataGenerator(data.getData(), aDirectory, aFileName);
    }

    /**
     * Creates the etalon network.
     * 
     * @param networkName the network name
     * @param neo the neo
     * @return the node
     */
    public static Node createEtalonNetwork(String networkName, String fileName, EmbeddedGraphDatabase neo) {
        Transaction tx = neo.beginTx();
        try {
            Map<String, Object> propertyMap = new HashMap<String, Object>();
            propertyMap.put("name", networkName);
            propertyMap.put("type", "gis");
            propertyMap.put("bbox", new double[] {3290118.0, 3290118.0, 5621767.0, 5637016.0});
            propertyMap.put("crs", "EPSG:31467");
            propertyMap.put("crs_type", "geographic");
            propertyMap.put("gis_type", "network");
            propertyMap.put("network_type", "radio");
            Node result = NeoDataUtils.createNode(propertyMap, neo);
            NeoDataUtils.createRelationship(neo.getReferenceNode(), result, "CHILD");
            propertyMap.clear();
            propertyMap.put("name", networkName);
            propertyMap.put("filename", fileName);
            Node network = NeoDataUtils.createNode(propertyMap, neo);
            NeoDataUtils.createRelationship(result, network, "NEXT");
            propertyMap.clear();
            propertyMap.put("name", "IL_R");
            propertyMap.put("id", "IL_R");
            propertyMap.put("userLabel", "IL_R");
            Node bscRoot = NeoDataUtils.createNode(propertyMap, neo);
            NeoDataUtils.createRelationship(network, bscRoot, "CHILD");
            propertyMap.clear();
            propertyMap.put("name", "ERNBC1");
            propertyMap.put("id", "ERNBC1");
            propertyMap.put("userLabel", "ERNBC1");
            propertyMap.put("userDefinedNetworkType", "UTRAN");
            Node bsc = NeoDataUtils.createNode(propertyMap, neo);
            NeoDataUtils.createRelationship(bscRoot, bsc, "CHILD");
            Node site = NeoDataUtils.createSite(bsc, "3043", 3290118.0, 5621767.0, neo);
            propertyMap.clear();
            propertyMap.put("name", "30431");
            propertyMap.put("azimuth", 335);
            propertyMap.put("userLabel", "ERNBC1");
            propertyMap.put("userDefinedNetworkType", "UTRAN");
            Node sector = NeoDataUtils.createNode(propertyMap, neo);
            NeoDataUtils.createRelationship(site, sector, "CHILD");
            propertyMap.clear();

            return result;
        } finally {
            tx.finish();
        }
    }
    public static enum NeoRelationTypes implements RelationshipType{
        CHILD,NEXT;
    }
    /**
     * @param fileName
     * @param etalonGis
     * @throws IOException
     * @throws TransformerConfigurationException
     * @throws SAXException
     */
    public static void generateEriccsonTopology(String fileName, Node etalonGis) throws IOException, TransformerConfigurationException, SAXException {
        File file = new File(fileName);
        PrintWriter out = new PrintWriter(file);
        StreamResult streamResult = new StreamResult(out);
        SAXTransformerFactory tf = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
        TransformerHandler hd = tf.newTransformerHandler();
        Transformer serializer = hd.getTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        hd.setResult(streamResult);
        hd.startDocument();
        AttributesImpl atts = new AttributesImpl();
        hd.startElement("", "", "bulkCmConfigDataFile", atts);
        hd.startElement("", "", "fileHeader", atts);
        hd.endElement("", "", "fileHeader");
        hd.startElement("", "", "configData", atts);
        Relationship rel = etalonGis.getSingleRelationship(NeoRelationTypes.NEXT,Direction.OUTGOING);
        if (rel!=null){
            Node network=rel.getOtherNode(etalonGis);
            Traverser travers = network.traverse(Order.BREADTH_FIRST, StopEvaluator.DEPTH_ONE, ReturnableEvaluator.ALL_BUT_START_NODE, NeoRelationTypes.CHILD,Direction.OUTGOING);
            for (Node node:travers){
                
            }
        }
        // USER tags.
        // String[] id = {"PWD122","MX787","A4Q45"};
        // for (int i=0;i<id.length;i++) {
        // atts.clear();
        // atts.addAttribute("","","ID","CDATA",id[i]);
        // atts.addAttribute("","","TYPE","CDATA",type[i]);
        // hd.startElement("","","USER",atts);
        // hd.characters(desc[i].toCharArray(),0,desc[i].length());
        // hd.endElement("","","USER");
        // }
        hd.endElement("", "", "configData");
        hd.endElement("", "", "bulkCmConfigDataFile");
        hd.endDocument();
        out.close();
    }
}
