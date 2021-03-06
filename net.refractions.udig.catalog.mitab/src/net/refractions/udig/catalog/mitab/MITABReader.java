/* uDig - User Friendly Desktop Internet GIS client
 * http://udig.refractions.net
 * (C) 2008, AmanziTel
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

package net.refractions.udig.catalog.mitab;

import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;
import java.io.InputStreamReader;

import net.refractions.udig.catalog.mitab.internal.Activator;
import net.refractions.udig.catalog.mitab.internal.ui.OgrPreferencePage;

/**
 * Does the actual MITAB to Shapefile conversion
 *
 * @author Lucas Reed, (Refractions Research Inc)
 * @since 1.1.0
 */
public class MITABReader {
    private File    source;
    private File    tempDir;
    private Process process;
    private File    shapeFile;

    @SuppressWarnings("nls")
    public MITABReader(File file) throws IOException {
        this.source = file;

        // Create temporary directory for conversion output
        this.createTempDir();

        this.execute();

        int a = file.getName().lastIndexOf(".");

        String bareName = file.getName().substring(0, a);

        String tempPath = this.tempDir.toString();

        if (false == tempPath.endsWith(File.separator)) {
            tempPath += File.separator;
        }

        tempPath += bareName + ".shp";

        this.shapeFile = new File(tempPath);

        if (false == this.shapeFile.exists()) {
            throw new RuntimeException("Could not find generated shapefile.");
        }

        if (false == this.shapeFile.setReadOnly()) {
            throw new IOException("Could not set generated shapefile as read-only.");
        }

        return;
    }

    public File getShapeFile() {
        return this.shapeFile;
    }

    @SuppressWarnings("nls")
    private void execute() throws IOException {
        String executablePath = Activator.getInstance().getPreferenceStore().getString(
                OgrPreferencePage.executablePathKey);

        if (null == executablePath || "".equals(executablePath.trim())) {
            throw new IOException("No ogr2ogr executable selected");
        }

        String[] args = new String[]{executablePath, "-skipfailures", this.tempDir.toString(),
                this.source.toString()};

        int exitCode;

        try {
            this.process = Runtime.getRuntime().exec(args);
        } catch(Exception e) {
            throw new IOException("Error creating ogr2ogr process.");
        }

        BufferedReader stdout = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
        BufferedReader stderr = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));

        while(true) {
            try {
                String stdout_str;
                String stderr_str;

                while(null != (stdout_str = stdout.readLine())) {
                    System.out.println(stdout_str);
                }

                while(null != (stderr_str = stderr.readLine())) {
                    System.err.println(stderr_str);
                }

                exitCode = this.process.exitValue();

                if (0 != exitCode) {
                    throw new IOException("ogr2ogr exited with non-zero exit code of '" + exitCode + "'.");
                }

                break;
            } catch(IllegalThreadStateException e) {
                continue;
            }
        }

        stdout.close();
        stderr.close();

        return;
    }

    @SuppressWarnings("nls")
    private void createTempDir() throws IOException {
        this.tempDir = File.createTempFile("tab2shp_", "", null);

        if (false == this.tempDir.delete()) {
            throw new IOException("Could not delete temp file in order to create temp directory.");
        }

        if (false == this.tempDir.mkdir()) {
            throw new IOException("Could not create/change to a directory.");
        }
    }
}