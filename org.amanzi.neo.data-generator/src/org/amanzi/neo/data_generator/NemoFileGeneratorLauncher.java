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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.amanzi.neo.loader.nemo_enum.ApplicationProtocol;
import org.amanzi.neo.loader.nemo_enum.FTPcause;
import org.amanzi.neo.loader.nemo_enum.TechnologySystems;

/**
 * <p>
 * NemoFileGeneratorLauncher
 * </p>
 * 
 * @author NiCK
 * @since 1.0.0
 */
public class NemoFileGeneratorLauncher {

    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Started.");

            URL url = new URL("http://gritinnovation.com/misc/geoptima/DownloadLogs_plain.php");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);

            in.close();

            // NemoDataGenerator dataGenerator = new NemoDataGenerator("1.86.00", 100500);
            // String result = dataGenerator.generate();
            // FileWriter ryt = new FileWriter("D:/nemo" + System.currentTimeMillis() + ".dt1");
            // BufferedWriter out = new BufferedWriter(ryt);
            // out.write(result);
            // out.close();

            System.out.println("Finished.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Aborted.");
        }
    }

    /**
     * The Class NemoDataGenerator.
     */
    private static class NemoDataGenerator {

        /** The version. */
        private final String version;

        /** The lines count. */
        private final int linesCount;

        /** The start lon. */
        private final double startLon;

        /** The start lat. */
        private final double startLat;

        /** The last lon. */
        private double lastLon;

        /** The last lat. */
        private double lastLat;

        /** The last geo h. */
        private int lastGeoH = 50;

        /** The max step. */
        private final double maxStep = 0.00006D;// maxStep/2

        /** The r. */
        private final Random r;

        /** The df. */
        private final DecimalFormat df;

        /** The Constant ver_1_84_00. */
        private static final String ver_1_84_00 = "1.84.00";

        /** The Constant ver_1_85_00. */
        private static final String ver_1_85_00 = "1.85.00";

        /** The Constant ver_1_86_00. */
        private static final String ver_1_86_00 = "1.86.00";

        /** The Constant ver_2_01_00. */
        private static final String ver_2_01_00 = "2.01.00";

        // **Really random string! I got it, striking twice my keyboard.*/
        final String RANDOM_STRING = "angie35WerRahz4P893y";

        /** The avaliable versions. */
        private static Set<String> avaliableVersions = new HashSet<String>();

        /**
         * Instantiates a new nemo data generator.
         * 
         * @param version the version
         * @param lineCount the line count
         */
        public NemoDataGenerator(String version, int lineCount) {
            this(version, lineCount, 0D, 0D);
        }

        /**
         * Instantiates a new nemo data generator.
         * 
         * @param version the version
         * @param linesCount the lines count
         * @param startLon the start lon
         * @param startLat the start lat
         */
        public NemoDataGenerator(String version, int linesCount, Double startLon, Double startLat) {
            super();
            this.version = version;
            this.linesCount = linesCount;
            this.startLon = startLon;
            this.startLat = startLat;

            this.r = new Random();
            this.df = new DecimalFormat("##0.000000");
            DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
            dfs.setDecimalSeparator('.');
            this.df.setDecimalFormatSymbols(dfs);
            init();
        }

        /**
         * Inits the data.
         */
        private void init() {
            this.avaliableVersions.add(ver_1_86_00);
            this.avaliableVersions.add(ver_2_01_00);
        }

        /**
         * Generate.
         * 
         * @return the string
         * @throws IllegalArgumentException the illegal argument exception
         */
        public String generate() throws IllegalArgumentException {
            if (!avaliableVersions.contains(version)) {
                throw new IllegalArgumentException("Required version " + version + " is not supported.");
            }
            StringBuilder result = new StringBuilder("");
            genHeader(result);
            for (int i = 0; i < linesCount; i++) {
                genRow(result);
            }
            genFooter(result);
            return result.toString();
        }

        /**
         * Gen row.
         * 
         * @param result the result
         */
        private void genRow(StringBuilder result) {
            String cId = "";
            String cParams = "";
            if (version.compareTo(ver_1_86_00) == 0) {
                int commandNum = r.nextInt(NemoCommand_1_86_00.values().length);
                NemoCommand_1_86_00 command = NemoCommand_1_86_00.values()[commandNum];
                cId = command.toString();
                cParams = command.genParams();
            } else if (version.compareTo(ver_2_01_00) == 0) {
                int commandNum = r.nextInt(NemoCommand_2_01_00.values().length);
                NemoCommand_2_01_00 command = NemoCommand_2_01_00.values()[commandNum];
                cId = command.toString();
                cParams = command.genParams();
            }
            result.append(cId).append(getValSep());
            genCommonParams(result);
            result.append(cParams).append("\n");
        }

        /**
         * Gen footer.
         * 
         * @param result the result
         */
        private void genFooter(StringBuilder result) {
            if (version.compareTo(ver_1_86_00) == 0)
                genHeader(result);
            else if (version.compareTo(ver_2_01_00) == 0) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS,,\"d.MM.yyyy\"");
                result.append("#STOP,").append(dateFormat.format(new Date()));
            }
        }

        /**
         * Gen header.
         * 
         * @param result the result
         */
        private void genHeader(StringBuilder result) {
            if (version.compareTo(ver_1_86_00) == 0) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy\tHH:mm:ss");
                result.append("***\tNemoDataGenerator\t1.0\tff ver 1.86.00\t");
                result.append(dateFormat.format(new Date()));
                result.append("\n");
            } else if (version.compareTo(ver_2_01_00) == 0) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS,,\"d.MM.yyyy\"");
                result.append("#PRODUCT,,,\"NemoDataGenerator\",\"1.0\"\n");
                result.append("#FF,,,\"2.01.00\"\n");
                result.append("#START,").append(dateFormat.format(new Date())).append("\n");
            }
        }

        /**
         * Gen common params.
         * 
         * @param result the result
         */
        private void genCommonParams(StringBuilder result) {
            if (version.compareTo(ver_1_86_00) == 0) {
                result.append(genCoordinates());
                result.append(genDistance());
                result.append(genState());
                result.append(genDate()).append(getValSep());
            } else if (version.compareTo(ver_1_86_00) == 0) {
                result.append(genDate()).append(getValSep());
                // TODO no any usages of context IDs ?
                result.append(getValSep());
            }
        }

        /**
         * Gen distance.
         * 
         * @return the string
         */
        private String genDistance() {
            double angularDifference = 2D * Math.asin(Math.sqrt(Math.pow(Math.sin((lastLat - startLat) / 2D), 2D) + Math.cos(startLat) * Math.cos(lastLat)
                    * Math.pow(Math.sin((lastLon - startLon) / 2D), 2D)));
            double distance = angularDifference * 6371302D;
            return df.format(distance) + getValSep();
            // return " >>> " + df.format(distance) + " <<< ";
        }

        /**
         * Gen date.
         * 
         * @return the string
         */
        private String genDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
            return dateFormat.format(new Date());
        }

        /**
         * Gen state.
         * 
         * @return the string
         */
        private String genState() {
            return genGpsStatus() + genNumberOfSatellites() + genVelocity();
        }

        /**
         * Gen velocity.
         * 
         * @return the string
         */
        private String genVelocity() {
            return 33 + getValSep();
        }

        /**
         * Gen number of satellites.
         * 
         * @return the string
         */
        private String genNumberOfSatellites() {
            return (1 + r.nextInt(10)) + getValSep();
        }

        /**
         * Gen gps status.
         * 
         * @return the string
         */
        private String genGpsStatus() {
            return 1 + getValSep();
        }

        /**
         * Gen coordinates.
         * 
         * @return the string
         */
        private String genCoordinates() {
            return genLongitude() + genLatitude() + genGeoHeight();
        }

        /**
         * Gen geo height.
         * 
         * @return the string
         */
        private String genGeoHeight() {
            lastGeoH += 10 - r.nextInt(20);
            return lastGeoH + getValSep();
        }

        /**
         * Gen longitude.
         * 
         * @return the string
         */
        private String genLongitude() {
            lastLon += (.5D - r.nextDouble()) * maxStep;
            return df.format(lastLon) + getValSep();
        }

        /**
         * Gen latitude.
         * 
         * @return the string
         */
        private String genLatitude() {
            lastLat += (.5D - r.nextDouble()) * maxStep;
            return df.format(lastLat) + getValSep();

        }

        /**
         * Gets the val sep.
         * 
         * @return the val sep
         */
        private String getValSep() {
            if (version.compareTo(ver_1_86_00) == 0) {
                return NemoCommand_1_86_00.getValSep();
            } else if (version.compareTo(ver_1_86_00) == 0) {
                return NemoCommand_2_01_00.getValSep();
            }
            return version;
        }

    }

    /**
     * The Enum NemoCommand_1_86_00.
     */
    private enum NemoCommand_1_86_00 {

        /** The PHDAS. */
        PHDAS("PHDAS") {

            @Override
            public String genParams() {
                String[] posibleValues = new String[] {"12", "13", "20", "21", "31", "33", "34"};
                Random r = new Random();
                return posibleValues[r.nextInt(posibleValues.length)] + getValSep() + r.nextInt(1000000);
            }
        };

        /** The id. */
        private final String id;

        /**
         * Instantiates a new nemo command_1_86_00.
         * 
         * @param id the id
         */
        private NemoCommand_1_86_00(String id) {
            this.id = id;
        }

        /**
         * Gen params.
         * 
         * @return the string
         */
        public abstract String genParams();

        /**
         * Gets the val sep.
         * 
         * @return the val sep
         */
        public static final String getValSep() {
            return " ";
        }

    }

    /**
     * The Enum NemoCommand_2_01_00.
     */
    private enum NemoCommand_2_01_00 {

        CAA("CAA") {

            @Override
            public String genParams() {
                Random r = new Random();
                StringBuilder result = new StringBuilder("Call_context_ID").append(getValSep());
                result.append(TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)].getId()).append(getValSep());
                result.append(r.nextInt(9) + 1).append(getValSep());
                result.append(r.nextInt(1) + 1).append(getValSep());
                result.append(r.nextInt(9999999));
                return result.toString();
            }

        },

        CAC("CAC") {

            @Override
            public String genParams() {
                Random r = new Random();
                StringBuilder result = new StringBuilder("Call_context_ID").append(getValSep());
                TechnologySystems ts = TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)];
                result.append(TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)].getId()).append(getValSep());
                result.append(r.nextInt(9) + 1).append(getValSep());
                result.append(r.nextInt(4) + 1).append(getValSep());
                int count = 0;
                switch (ts) {
                case GSM:
                    count = r.nextInt(4) + 3; // TODO need clarify possible count of parameters
                    result.append(count).append(getValSep());
                    for (int i = 0; i < count; i++) {
                        result.append(r.nextInt(8)).append(getValSep());
                    }
                    break;
                case TETRA:
                    count = r.nextInt(4) + 3; // TODO need clarify possible count of parameters
                    result.append(count).append(getValSep());
                    for (int i = 0; i < count; i++) {
                        result.append(r.nextInt(4) + 1).append(getValSep());
                    }
                    break;
                default:
                    result.append(count).append(getValSep());
                    break;
                }
                return result.toString();
            }

        },
        CAF("CAF") {
            // TODO ZNN commands CAF and CAD are identical?
            @Override
            public String genParams() {
                Random r = new Random();
                StringBuilder result = new StringBuilder("Call_context_ID").append(getValSep());
                TechnologySystems ts = TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)];
                result.append(TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)].getId()).append(getValSep());
                result.append(r.nextInt(9) + 1).append(getValSep());
                int param = r.nextInt(11);
                switch (param) {
                case 0:
                    result.append(20).append(getValSep());
                    break;
                case 10:
                    result.append(11).append(getValSep());
                    break;
                default:
                    result.append(param).append(getValSep());
                    break;
                }
                switch (ts) {
                case GSM:
                case UMTS_FDD:
                case UMTS_TD_SCDMA:
                case GAN_WLAN:
                    Integer[] possibleValues = new Integer[] {1, 3, 6, 8, 16, 17, 18, 19, 21, 22, 25, 26, 27, 28, 29, 30, 31, 34, 38, 41, 42, 43, 44, 47, 49, 50, 55, 57, 58, 63,
                            65, 68, 69, 70, 79, 81, 87, 88, 91, 95, 96, 97, 98, 99, 100, 101, 102, 111, 127};
                    param = r.nextInt(200 + possibleValues.length);
                    result.append(param < 200 ? param + 600 : possibleValues[param - 200]).append(getValSep());
                    break;
                case TETRA:
                    result.append(r.nextInt(16)).append(getValSep());
                case CDMA_ONE:
                case CDMA_ONE_X:
                case EVDO:
                    param = r.nextInt(29);
                    result.append(param > 0 ? param + 2 : 0).append(getValSep());
                default:
                    break;
                }

                return result.toString();
            }

        },

        CAD("CAD") {
            // TODO ZNN commands CAF and CAD are identical?
            @Override
            public String genParams() {
                Random r = new Random();
                StringBuilder result = new StringBuilder("Call_context_ID").append(getValSep());
                TechnologySystems ts = TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)];
                result.append(TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)].getId()).append(getValSep());
                result.append(r.nextInt(9) + 1).append(getValSep());
                int param = r.nextInt(11);
                switch (param) {
                case 0:
                    result.append(20).append(getValSep());
                    break;
                case 10:
                    result.append(11).append(getValSep());
                    break;
                default:
                    result.append(param).append(getValSep());
                    break;
                }
                switch (ts) {
                case GSM:
                case UMTS_FDD:
                case UMTS_TD_SCDMA:
                case GAN_WLAN:
                    Integer[] possibleValues = new Integer[] {1, 3, 6, 8, 16, 17, 18, 19, 21, 22, 25, 26, 27, 28, 29, 30, 31, 34, 38, 41, 42, 43, 44, 47, 49, 50, 55, 57, 58, 63,
                            65, 68, 69, 70, 79, 81, 87, 88, 91, 95, 96, 97, 98, 99, 100, 101, 102, 111, 127};
                    param = r.nextInt(200 + possibleValues.length);
                    result.append(param < 200 ? param + 600 : possibleValues[param - 200]).append(getValSep());
                    break;
                case TETRA:
                    result.append(r.nextInt(16)).append(getValSep());
                case CDMA_ONE:
                case CDMA_ONE_X:
                case EVDO:
                    param = r.nextInt(29);
                    result.append(param > 0 ? param + 2 : 0).append(getValSep());
                default:
                    break;
                }

                return result.toString();
            }

        },
        VCHI("VCHI") {
            @Override
            public String genParams() {
                Random r = new Random();
                StringBuilder result = new StringBuilder("Call_context_ID").append(getValSep());
                TechnologySystems ts = TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)];
                result.append(TechnologySystems.values()[r.nextInt(TechnologySystems.values().length)].getId()).append(getValSep());
                if (ts == TechnologySystems.TETRA) {
                    result.append(r.nextInt(4) + 1).append(getValSep());
                    result.append(r.nextInt(4)).append(getValSep());
                    result.append(Long.toHexString(r.nextLong())).append(getValSep());
                }
                return result.toString();
            }

        },

        DAA("DAA") {
            @Override
            public String genParams() {
                Random r = new Random();
                StringBuilder result = new StringBuilder("Data_connection_context_ID").append(getValSep());
                result.append("Packet_session_context_ID").append(getValSep());
                result.append("Call_context_ID").append(getValSep());
                result.append(ApplicationProtocol.getRandomProtocol().getId()).append(getValSep());
                result.append(r.nextInt(255) + 1).append(".").append(r.nextInt(255) + 1).append(".").append(r.nextInt(255) + 1).append(".").append(r.nextInt(255) + 1)
                        .append(getValSep());
                result.append(r.nextInt(65535)).append(getValSep());
                return result.toString();
            }

        },
        DAC("DAC") {
            @Override
            public String genParams() {
                Random r = new Random();
                StringBuilder result = new StringBuilder("Data_connection_context_ID").append(getValSep());
                result.append(ApplicationProtocol.getRandomProtocol().getId()).append(getValSep());

                return result.toString();
            }

        },
        DAF("DAF") {
            @Override
            public String genParams() {
                Random r = new Random();
                StringBuilder result = new StringBuilder("Data_connection_context_ID").append(getValSep());
                ApplicationProtocol protocol = ApplicationProtocol.getRandomProtocol();
                result.append(protocol.getId()).append(getValSep());
                int failStatus = r.nextInt(5) + 1;
                result.append(failStatus).append(getValSep());
                if (failStatus == 2) {
                    Integer[] possibleValues = new Integer[] {10004, 10009, 10013, 10014, 10022, 10024, 10035, 10036, 10037, 10038, 10039, 10040, 10041, 10042, 10043, 10044,
                            10045, 10046, 10047, 10048, 10049, 10050, 10051, 10052, 10053, 10054, 10055, 10056, 10057, 10058, 10059, 10060, 10061, 10062, 10063, 10064, 10065,
                            10066, 10067, 10068, 10069, 10070, 10071, 10091, 10092, 10093, 10101, 10102, 10103, 10104, 10105, 10106, 10107, 10108, 10109, 10110, 10111, 10112,
                            11001, 11002, 11003, 11004, 11005, 11006, 11007, 11008, 11009, 11010, 11011, 11012, 11013, 11014, 11015, 11016, 11017, 11018, 11019, 11020, 11021,
                            11022, 11023, 11024, 11025, 11026, 11027, 11028, 11029, 11030, 11031};
                    result.append(possibleValues[r.nextInt(possibleValues.length)]).append(getValSep());
                } else {
                    result.append("reservedField").append(getValSep());
                }
                switch (protocol) {
                case NEMO_MODEM:
                case NEMO_TCP:
                case NEMO_UPD:
                    result.append(1).append(getValSep());
                    break;
                case FTP:
                    result.append(FTPcause.getRandomCause().getId()).append(getValSep());
                    break;

                default:
                    break;
                }
                return result.toString();
            }

        },

        /** The PILOTSCAN. */
        PILOTSCAN("PILOTSCAN") {

            @Override
            public String genParams() {
                return "pilotscan command params";
            }

        },

        /** The GPS. */
        GPS("GPS") {

            @Override
            public String genParams() {
                return "gps command params";
            }
        },

        /** The STARTSCAN. */
        STARTSCAN("STARTSCAN") {
            @Override
            public String genParams() {
                return "startscan command params";
            }
        },

        /** The STOPSCAN. */
        STOPSCAN("STOPSCAN") {

            @Override
            public String genParams() {
                return "stopscan command params";
            }
        };

        /** The id. */
        private final String id;

        /**
         * Instantiates a new nemo command_2_01_00.
         * 
         * @param id the id
         */
        private NemoCommand_2_01_00(String id) {
            this.id = id;
        }

        /**
         * Gen params.
         * 
         * @return the string
         */
        public abstract String genParams();

        /**
         * Gets the val sep.
         * 
         * @return the val sep
         */
        public static final String getValSep() {
            return ",";
        }

    }
}