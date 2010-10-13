package org.amanzi.neo.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.amanzi.neo.core.utils.NeoUtils;
import org.amanzi.neo.loader.internal.NeoLoaderPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.neo4j.graphdb.Node;

public class TemsRemoteUrlLoader extends TEMSLoader {

	/** The url. */
    private final URL url;
    
    public TemsRemoteUrlLoader(URL url, String datasetName, Display display, Node mNode, Node virtualMnode){
    	super(datasetName, display, datasetName, mNode, virtualMnode);
        this.url = url;
        dataset = datasetName;
    }
    
    
    /**
     * Run.
     * 
     * @param monitor the monitor
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void run(IProgressMonitor monitor) throws IOException {
        if (monitor != null) {
            monitor.setTaskName(basename);
        }
        String characterSet = NeoLoaderPlugin.getDefault().getCharacterSet();
        BufferedReader reader = null;

        mainTx = neo.beginTx();
        NeoUtils.addTransactionLog(mainTx, Thread.currentThread(), "temsUrlLoader");
        try {
            InputStream inputStream = url.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, characterSet);
            reader = new BufferedReader(inputStreamReader);
            initializeIndexes();
            int prevLineNumber = 0;
            String line;
            headerWasParced = !needParceHeaders();
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                System.out.println(line);
                if (!headerWasParced) {
                    parseHeader(line);
                } else {
                    parseLine(line);
                }

                if (lineNumber > prevLineNumber + commitSize) {
                    commit(true);
                    prevLineNumber = lineNumber;
                }
                if (isOverLimit())
                    break;

            }
            commit(true);
            saveProperties();
            finishUpIndexes();
            finishUp();
        } finally {
            commit(false);
            if (reader != null) {
                reader.close();
            }
        }
    }


}