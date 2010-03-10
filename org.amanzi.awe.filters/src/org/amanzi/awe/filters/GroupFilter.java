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

package org.amanzi.awe.filters;

import java.util.LinkedList;

import org.amanzi.neo.core.enums.NodeTypes;
import org.amanzi.neo.core.utils.NeoUtils;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Transaction;
import org.neo4j.api.core.Traverser;

/**
 * TODO Purpose of
 * <p>
 * </p>
 * 
 * @author Tsinkel_A
 * @since 1.0.0
 */
public class GroupFilter extends AbstractFilter {
    protected final LinkedList<AbstractFilter> subfilters;

    protected GroupFilter(Node node, NeoService service) {
        super(node, service);
        type = NodeTypes.FILTER_GROUP;
        subfilters = new LinkedList<AbstractFilter>();
        for (Node child : NeoUtils.getChildTraverser(node)) {
            subfilters.add(AbstractFilter.getInstance(child, service));
        }
    }

    @Override
    public FilterResult filterNode(Node node) {
        Transaction tx = NeoUtils.beginTx(service);
        try{
            for (int i=0;i<subfilters.size();i++){
                final FilterResult result = subfilters.get(i).filterNode(node);
                if (result.isValid()){
                    return new FilterResult(true, true, i, subfilters.size(), node);
                }
            }
            return new FilterResult(false, true, -1, subfilters.size(), node);
        }finally{
            NeoUtils.finishTx(tx);
        }
    }

    @Override
    public FilterResult filterNodesByTraverser(Traverser traverser) {
        Transaction tx = NeoUtils.beginTx(service);
        try{
            for (Node node:traverser){
                FilterResult result = filterNode(node);
                if (result.isValid()){
                    return result;
                }
            }
            return new FilterResult(false, true, -1, subfilters.size(), null);
         }finally{
            NeoUtils.finishTx(tx);
        }
    }

    @Override
    public FilterResult filterNode(Object value) {
            for (int i=0;i<subfilters.size();i++){
                final FilterResult result = subfilters.get(i).filterNode(value);
                if (result.isValid()){
                    return new FilterResult(true, true, i, subfilters.size(), null);
                }
            }
            return new FilterResult(false, true, -1, subfilters.size(), null);
    }

}