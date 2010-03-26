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

package org.amanzi.neo.loader.sax_parsers;

import org.amanzi.neo.core.utils.NeoUtils;
import org.neo4j.graphdb.Node;
import org.xml.sax.Attributes;

/**
 * <p>
 * Abstract tag with support neo4j and CHILD/NEXT structure New transaction do not created
 * </p>
 * 
 * @author Tsinkel_A
 * @since 1.0.0
 */
public abstract class AbstractNeoTag extends AbstractTag {
    protected Node node;
    protected Node lastChild;

    /**
     * Constructor
     * 
     * @param tagName - tag name
     * @param parent - parent AbstractNeoTag
     * @param attributes - attributes of tag
     */
    protected AbstractNeoTag(String tagName, AbstractNeoTag parent, Attributes attributes) {
        super(tagName, parent);
        lastChild = null;
        node = createNode(attributes);
        parent.addChild(this);
    }

    /**
     * Constructor
     * 
     * @param tagName - tag name
     * @param parent - parent node
     * @param lastChild -last child of parent node, if null, then child will be found
     * @param attributes - attributes of tag
     */
    protected AbstractNeoTag(String tagName, Node parent, Node lastChild, Attributes attributes) {
        super(tagName, null);
        lastChild = null;
        node = createNode(attributes);
        addChild(parent,lastChild);
    }
    /**
     * add current node like child of parent node
     * 
     * @param childNode - child tag
     */
    protected void addChild(Node parent,Node lastChild) {
        NeoUtils.addChild(parent, node, lastChild, null);
    }
    /**
     * add child
     * 
     * @param childNode - child tag
     */
    protected void addChild(AbstractNeoTag childNode) {
        NeoUtils.addChild(node, childNode.node, lastChild, null);
        lastChild = childNode.node;
    }

    /**
     * utility method for storing attributes like propertys in node
     * 
     * @param node - node to store
     * @param attributes - attributes
     */
    public void storeAttributes(Node node, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            node.setProperty(attributes.getLocalName(i), attributes.getValue(i));
        }
    }

    /**
     * Create node of current tag
     * 
     * @param attributes - attributes
     * @return created node
     */
    protected abstract Node createNode(Attributes attributes);

    /**
     * Get node of current tag
     * 
     * @return Returns the node.
     */
    public Node getNode() {
        return node;
    }

}