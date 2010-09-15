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

package org.amanzi.neo.loader.core.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.amanzi.neo.loader.core.CommonConfigData;
import org.amanzi.neo.loader.core.CountingFileInputStream;
import org.amanzi.neo.loader.core.ProgressEventImpl;

import au.com.bytecode.opencsv.CSVReader;

/**
 * <p>
 * Csv parser
 * </p>
 * 
 * @author TsAr
 * @since 1.0.0
 */
public class CSVParser extends CommonFilesParser<HeaderTransferData, CommonConfigData> {
    private static final int PERCENTAGE_FIRE = 5;
    protected String[] possibleFieldSepRegexes = new String[] {"\t", ",", ";"};
    Character delimeters;

    @Override
    protected HeaderTransferData getFinishData() {
        return null;
    }

    @Override
    protected boolean parseElement(FileElement element) {
        CSVReader reader = null;
        char delim = getDelimiters(element.getFile());
        try {
            CountingFileInputStream is = new CountingFileInputStream(element.getFile());
            reader = new CSVReader(new InputStreamReader(is), delim);
            String[] nextLine;
            String[] header = null;
            int persentageOld = 0;
            long line = 0;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    line++;
                    if (header == null) {
                        header = nextLine;
                        continue;
                    }

                    if (header.length != nextLine.length) {
                        error(String.format("File %s, line %s:incorrect data: Header length=%s,data length=%s. Data was skipped", element.getFile().getName(), line, header.length, nextLine.length));
                        continue;
                    }
                    HeaderTransferData data = new HeaderTransferData();
                    data.setLine(line);
                    data.setFileName(element.getFile().getName());
                    for (int i = 0; i < header.length; i++) {
                        data.put(header[i], nextLine[i]);
                    }
                    getSaver().save(data);
                } finally {
                    int persentage = is.percentage();
                    if (persentage - persentageOld > PERCENTAGE_FIRE) {
                        persentageOld = persentage;
                        if (fireSubProgressEvent(element, new ProgressEventImpl(String.format(getDescriptionFormat(), element.getFile().getName()), persentage))) {
                            return true;
                        }
                    }
                }
            }

        } catch (IOException e) {
            exception(e);
        } finally {
            closeStream(reader);
        }
        return false;
    }


    /**
     * Gets the delimiters.
     *
     * @param file the file
     * @return the delimiters
     */
    private char getDelimiters(File file) {
        if (delimeters == null) {
            String fieldSepRegex = "\t";
            BufferedReader read;
            String line;
            try {
                read = new BufferedReader(new FileReader(file));
                line = read.readLine();
                read.close();
                int maxMatch = 0;
                if (line != null) {
                    for (String regex : possibleFieldSepRegexes) {
                        String[] fields = line.split(regex);
                        if (fields.length > maxMatch) {
                            maxMatch = fields.length;
                            fieldSepRegex = regex;
                        }
                    }
                }
            } catch (IOException e) {
                exception(e);
            }
            delimeters = fieldSepRegex.charAt(0);
        }
        return delimeters;
    }

    @Override
    protected HeaderTransferData getInitData(CommonConfigData properties) {
        HeaderTransferData initdata = new HeaderTransferData();
        initdata.setProjectName(properties.getProjectName());
        initdata.setRootName(properties.getDbRootName());
        return initdata;
    }

}