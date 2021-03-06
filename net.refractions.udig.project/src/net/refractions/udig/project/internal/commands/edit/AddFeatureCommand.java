/* uDig - User Friendly Desktop Internet GIS client
 * http://udig.refractions.net
 * (C) 2004, Refractions Research Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */
package net.refractions.udig.project.internal.commands.edit;

import java.io.IOException;
import java.util.Iterator;

import net.refractions.udig.project.ILayer;
import net.refractions.udig.project.command.AbstractCommand;
import net.refractions.udig.project.command.UndoableMapCommand;
import net.refractions.udig.project.internal.Messages;

import org.eclipse.core.runtime.IProgressMonitor;
import org.geotools.data.FeatureStore;
import org.geotools.feature.Feature;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.collection.AbstractFeatureCollection;
import org.geotools.filter.FilterFactoryFinder;

/**
 * Adds the new feature to the 
 * @author jones
 * @since 1.1.0
 */
public class AddFeatureCommand extends AbstractCommand implements UndoableMapCommand {

    private Feature feature;
    private ILayer layer;
    private FeatureStore resource;
    private String fid;

    public AddFeatureCommand( Feature feature, ILayer layer ) {
        this.feature=feature;
        this.layer=layer;
    }

    public void run( IProgressMonitor monitor ) throws Exception {
        resource = layer.getResource(FeatureStore.class, monitor);
        if( resource == null )
            return;
        FeatureCollection c=new AbstractFeatureCollection(resource.getSchema()){

            @Override
            public int size() {
                return 1;
            }

            @Override
            protected Iterator openIterator() {
                return new Iterator(){
                    Feature next=feature;

                    public Object next() {
                        Feature tmp=next;
                        next=null;
                        return tmp;
                    }

                    public boolean hasNext(){
                        return next!=null;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                    
                };
            }

            @Override
            protected void closeIterator( Iterator close ) {
            }
            
        };
        fid=(String) resource.addFeatures(c).iterator().next();
    }

    public Feature getNewFeature() throws IOException{
        if( resource!=null ){
            FeatureCollection features = resource.getFeatures(FilterFactoryFinder.createFilterFactory().createFidFilter(fid));
            FeatureIterator iter=features.features();
            try{
                return iter.next();
            }finally{
                features.close(iter);
            }
        }
        return null;
    }
    
    public String getName() {
        return Messages.AddFeatureCommand_name; 
    }

    public void rollback( IProgressMonitor monitor ) throws Exception {
        resource.removeFeatures(FilterFactoryFinder.createFilterFactory().createFidFilter(fid));
    }

    /**
     * @return Returns the fid.
     */
    public String getFid() {
        return fid;
    }

}
