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

package org.amanzi.neo.data_generator.data.calls.csv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Enumeration of all headers for csv file.
 * </p>
 * @author Shcharbatsevich_A
 * @since 1.0.0
 */
public enum CsvHeaders {

    STARTTIME("STARTTIME",0,0,ValueType.TIME),
    ENDTIME("ENDTIME",0,5,ValueType.TIME),
    HOST("HOST",0,10,ValueType.STRING),
    LA("LA",0,15,ValueType.INTEGER),
    FREQUENCY("FREQUENCY",0,20,ValueType.STRING),
    VERSION_NUMBER("Version Number",0,1510,ValueType.STRING),
    CONFIG_VERSION("Config Version",0,1515,ValueType.INTEGER),
    
    SC1_CALL_ATTEMPT_COUNT("SL-SRV-SC-1_CALL_ATTEMPT_COUNT",1,25,ValueType.INTEGER),
    SC1_SUCC_SETUP_COUNT("SL-SRV-SC-1_SUCC_SETUP_COUNT",1,30,ValueType.INTEGER),
    SC2_SETUP_TM_Z1_P1("SL-SRV-SC-2_SETUP_TM_Z1_P1",1,35,ValueType.INTEGER),
    SC2_SETUP_TM_Z1_P2("SL-SRV-SC-2_SETUP_TM_Z1_P2",1,40,ValueType.INTEGER),
    SC2_SETUP_TM_Z1_P3("SL-SRV-SC-2_SETUP_TM_Z1_P3",1,45,ValueType.INTEGER),
    SC2_SETUP_TM_Z1_P4("SL-SRV-SC-2_SETUP_TM_Z1_P4",1,50,ValueType.INTEGER),
    SC2_SETUP_TM_Z1_L1("SL-SRV-SC-2_SETUP_TM_Z1_L1",1,55,ValueType.INTEGER),
    SC2_SETUP_TM_Z1_L2("SL-SRV-SC-2_SETUP_TM_Z1_L2",1,60,ValueType.INTEGER),
    SC2_SETUP_TM_Z1_L3("SL-SRV-SC-2_SETUP_TM_Z1_L3",1,65,ValueType.INTEGER),
    SC2_SETUP_TM_Z1_L4("SL-SRV-SC-2_SETUP_TM_Z1_L4",1,70,ValueType.INTEGER),
    SC2_SETUP_TIME_MIN("SL-SRV-SC-2_SETUP_TIME_MIN",1,75,ValueType.FLOAT),
    SC2_SETUP_TIME_MAX("SL-SRV-SC-2_SETUP_TIME_MAX",1,80,ValueType.FLOAT),
    SC2_SETUP_TOTAL_DUR("SL-SRV-SC-2_SETUP_TOTAL_DUR",1,85,ValueType.FLOAT),
    SC2_SETUP_DUR_Z1_P1("SL-SRV-SC-2_SETUP_DUR_Z1_P1",1,90,ValueType.FLOAT),
    SC2_SETUP_DUR_Z1_P2("SL-SRV-SC-2_SETUP_DUR_Z1_P2",1,95,ValueType.FLOAT),
    SC2_SETUP_DUR_Z1_P3("SL-SRV-SC-2_SETUP_DUR_Z1_P3",1,100,ValueType.FLOAT),
    SC2_SETUP_DUR_Z1_P4("SL-SRV-SC-2_SETUP_DUR_Z1_P4",1,105,ValueType.FLOAT),
    SC2_SETUP_DUR_Z1_L1("SL-SRV-SC-2_SETUP_DUR_Z1_L1",1,110,ValueType.FLOAT),
    SC2_SETUP_DUR_Z1_L2("SL-SRV-SC-2_SETUP_DUR_Z1_L2",1,115,ValueType.FLOAT),
    SC2_SETUP_DUR_Z1_L3("SL-SRV-SC-2_SETUP_DUR_Z1_L3",1,120,ValueType.FLOAT),
    SC2_SETUP_DUR_Z1_L4("SL-SRV-SC-2_SETUP_DUR_Z1_L4",1,125,ValueType.FLOAT),
    SC3_CALL_DISC_TIME("SL-SRV-SC-3_CALL_DISC_TIME",1,126,ValueType.INTEGER),
    SC4_AUDIO_QUAL_SUCC("SL-SRV-SC-4_AUDIO_QUAL_SUCC",1,130,ValueType.INTEGER),
    SC4_AUDIO_QUAL_P1("SL-SRV-SC-4_AUDIO_QUAL_P1",1,135,ValueType.INTEGER),
    SC4_AUDIO_QUAL_P2("SL-SRV-SC-4_AUDIO_QUAL_P2",1,140,ValueType.INTEGER),
    SC4_AUDIO_QUAL_P3("SL-SRV-SC-4_AUDIO_QUAL_P3",1,145,ValueType.INTEGER),
    SC4_AUDIO_QUAL_P4("SL-SRV-SC-4_AUDIO_QUAL_P4",1,150,ValueType.INTEGER),
    SC4_AUDIO_QUAL_L1("SL-SRV-SC-4_AUDIO_QUAL_L1",1,155,ValueType.INTEGER),
    SC4_AUDIO_QUAL_L2("SL-SRV-SC-4_AUDIO_QUAL_L2",1,160,ValueType.INTEGER),
    SC4_AUDIO_QUAL_L3("SL-SRV-SC-4_AUDIO_QUAL_L3",1,165,ValueType.INTEGER),
    SC4_AUDIO_QUAL_L4("SL-SRV-SC-4_AUDIO_QUAL_L4",1,175,ValueType.INTEGER),
    SC4_AUDIO_QUAL_MIN("SL-SRV-SC-4_AUDIO_QUAL_MIN",1,180,ValueType.FLOAT),
    SC4_AUDIO_QUAL_MAX("SL-SRV-SC-4_AUDIO_QUAL_MAX",1,185,ValueType.FLOAT),
    SC4_AUDIO_QUAL_TOTAL("SL-SRV-SC-4_AUDIO_QUAL_TOTAL",1,190,ValueType.FLOAT),
    SC4_AUDIO_QUAL_Z1_P1("SL-SRV-SC-4_AUDIO_QUAL_Z1_P1",1,195,ValueType.FLOAT),
    SC4_AUDIO_QUAL_Z1_P2("SL-SRV-SC-4_AUDIO_QUAL_Z1_P2",1,200,ValueType.FLOAT),
    SC4_AUDIO_QUAL_Z1_P3("SL-SRV-SC-4_AUDIO_QUAL_Z1_P3",1,205,ValueType.FLOAT),
    SC4_AUDIO_QUAL_Z1_P4("SL-SRV-SC-4_AUDIO_QUAL_Z1_P4",1,210,ValueType.FLOAT),
    SC4_AUDIO_QUAL_Z1_L1("SL-SRV-SC-4_AUDIO_QUAL_Z1_L1",1,215,ValueType.FLOAT),
    SC4_AUDIO_QUAL_Z1_L2("SL-SRV-SC-4_AUDIO_QUAL_Z1_L2",1,220,ValueType.FLOAT),
    SC4_AUDIO_QUAL_Z1_L3("SL-SRV-SC-4_AUDIO_QUAL_Z1_L3",1,225,ValueType.FLOAT),
    SC4_AUDIO_QUAL_Z1_L4("SL-SRV-SC-4_AUDIO_QUAL_Z1_L4",1,230,ValueType.FLOAT),
    SC5_DELAY_COUNT_P1("SL-SRV-SC-5_DELAY_COUNT_P1",1,235,ValueType.INTEGER),
    SC5_DELAY_COUNT_P2("SL-SRV-SC-5_DELAY_COUNT_P2",1,240,ValueType.INTEGER),
    SC5_DELAY_COUNT_P3("SL-SRV-SC-5_DELAY_COUNT_P3",1,245,ValueType.INTEGER),
    SC5_DELAY_COUNT_P4("SL-SRV-SC-5_DELAY_COUNT_P4",1,250,ValueType.INTEGER),
    SC5_DELAY_COUNT_L1("SL-SRV-SC-5_DELAY_COUNT_L1",1,255,ValueType.INTEGER),
    SC5_DELAY_COUNT_L2("SL-SRV-SC-5_DELAY_COUNT_L2",1,260,ValueType.INTEGER),
    SC5_DELAY_COUNT_L3("SL-SRV-SC-5_DELAY_COUNT_L3",1,265,ValueType.INTEGER),
    SC5_DELAY_COUNT_L4("SL-SRV-SC-5_DELAY_COUNT_L4",1,270,ValueType.INTEGER),
    SC5_DELAY_MIN("SL-SRV-SC-5_DELAY_MIN",1,275,ValueType.FLOAT),
    SC5_DELAY_MAX("SL-SRV-SC-5_DELAY_MAX",1,280,ValueType.FLOAT),
    SC5_DELAY_TOTAL("SL-SRV-SC-5_DELAY_TOTAL",1,285,ValueType.FLOAT),
    SC5_DELAY_Z1_P1("SL-SRV-SC-5_DELAY_Z1_P1",1,290,ValueType.FLOAT),
    SC5_DELAY_Z1_P2("SL-SRV-SC-5_DELAY_Z1_P2",1,295,ValueType.FLOAT),
    SC5_DELAY_Z1_P3("SL-SRV-SC-5_DELAY_Z1_P3",1,300,ValueType.FLOAT),
    SC5_DELAY_Z1_P4("SL-SRV-SC-5_DELAY_Z1_P4",1,305,ValueType.FLOAT),
    SC5_DELAY_Z1_L1("SL-SRV-SC-5_DELAY_Z1_L1",1,310,ValueType.FLOAT),
    SC5_DELAY_Z1_L2("SL-SRV-SC-5_DELAY_Z1_L2",1,315,ValueType.FLOAT),
    SC5_DELAY_Z1_L3("SL-SRV-SC-5_DELAY_Z1_L3",1,320,ValueType.FLOAT),
    SC5_DELAY_Z1_L4("SL-SRV-SC-5_DELAY_Z1_L4",1,325,ValueType.FLOAT),
    ATT_ATTEMPTS("SL-INH-ATT_ATTEMPTS",1,330,ValueType.INTEGER),
    ATT_SUCCESS("SL-INH-ATT_SUCCESS",1,335,ValueType.INTEGER),
    ATT_DELAY_P1("SL-INH-ATT_DELAY_P1",1,340,ValueType.INTEGER),
    ATT_DELAY_P2("SL-INH-ATT_DELAY_P2",1,345,ValueType.INTEGER),
    ATT_DELAY_P3("SL-INH-ATT_DELAY_P3",1,350,ValueType.INTEGER),
    ATT_DELAY_P4("SL-INH-ATT_DELAY_P4",1,355,ValueType.INTEGER),
    ATT_DELAY_L1("SL-INH-ATT_DELAY_L1",1,360,ValueType.INTEGER),
    ATT_DELAY_L2("SL-INH-ATT_DELAY_L2",1,365,ValueType.INTEGER),
    ATT_DELAY_L3("SL-INH-ATT_DELAY_L3",1,370,ValueType.INTEGER),
    ATT_DELAY_L4("SL-INH-ATT_DELAY_L4",1,375,ValueType.INTEGER),
    CC_HO_ATTEMPTS("SL-INH-CC_HO_ATTEMPTS",1,380,ValueType.INTEGER),
    CC_HO_SUCCESS("SL-INH-CC_HO_SUCCESS",1,385,ValueType.INTEGER),
    CC_HO_TIME_P1("SL-INH-CC_HO_TIME_P1",1,390,ValueType.INTEGER),
    CC_HO_TIME_P2("SL-INH-CC_HO_TIME_P2",1,395,ValueType.INTEGER),
    CC_HO_TIME_P3("SL-INH-CC_HO_TIME_P3",1,400,ValueType.INTEGER),
    CC_HO_TIME_P4("SL-INH-CC_HO_TIME_P4",1,405,ValueType.INTEGER),
    CC_HO_TIME_L1("SL-INH-CC_HO_TIME_L1",1,410,ValueType.INTEGER),
    CC_HO_TIME_L2("SL-INH-CC_HO_TIME_L2",1,415,ValueType.INTEGER),
    CC_HO_TIME_L3("SL-INH-CC_HO_TIME_L3",1,420,ValueType.INTEGER),
    CC_HO_TIME_L4("SL-INH-CC_HO_TIME_L4",1,425,ValueType.INTEGER),
    CC_RES_ATTEMPTS("SL-INH-CC_RES_ATTEMPTS",1,430,ValueType.INTEGER), 
    CC_RES_SUCCESS("SL-INH-CC_RES_SUCCESS",1,435,ValueType.INTEGER),
    CC_RES_TIME_P1("SL-INH-CC_RES_TIME_P1",1,440,ValueType.INTEGER),
    CC_RES_TIME_P2("SL-INH-CC_RES_TIME_P2",1,445,ValueType.INTEGER),
    CC_RES_TIME_P3("SL-INH-CC_RES_TIME_P3",1,447,ValueType.INTEGER),
    CC_RES_TIME_P4("SL-INH-CC_RES_TIME_P4",1,450,ValueType.INTEGER),
    CC_RES_TIME_L1("SL-INH-CC_RES_TIME_L1",1,455,ValueType.INTEGER),
    CC_RES_TIME_L2("SL-INH-CC_RES_TIME_L2",1,460,ValueType.INTEGER),
    CC_RES_TIME_L3("SL-INH-CC_RES_TIME_L3",1,465,ValueType.INTEGER),
    CC_RES_TIME_L4("SL-INH-CC_RES_TIME_L4",1,470,ValueType.INTEGER),
    TSM_MESSAGE_ATTEMPT("SL-SRV-TSM_MESSAGE_ATTEMPT",1,475,ValueType.INTEGER),
    SDS_MESSAGE_ATTEMPT("SL-SRV-SDS_MESSAGE_ATTEMPT",1,480,ValueType.INTEGER),
    TSM_MESSAGE_SUCC("SL-SRV-TSM_MESSAGE_SUCC",1,485,ValueType.INTEGER),
    SDS_MESSAGE_SUCC("SL-SRV-SDS_MESSAGE_SUCC",1,490,ValueType.INTEGER),
    EC1_ATTEMPT("SL-SRV-EC-1_ATTEMPT",1,495,ValueType.INTEGER),
    EC2_ATTEMPT("SL-SRV-EC-2_ATTEMPT",1,500,ValueType.INTEGER),
    EC1_SUCCESS("SL-SRV-EC-1_SUCCESS",1,505,ValueType.INTEGER),
    EC2_SUCCESS("SL-SRV-EC-2_SUCCESS",1,510,ValueType.INTEGER),
    ALM_ATTEMPT("SL-SRV-ALM_ATTEMPT",1,515,ValueType.INTEGER),
    ALM_SUCCESS("SL-SRV-ALM_SUCCESS",1,520,ValueType.INTEGER),
    ALM_DELAY_TOTAL_MIN("SL-SRV-ALM_DELAY_TOTAL_MIN",1,525,ValueType.FLOAT),
    ALM_DELAY_TOTAL_MAX("SL-SRV-ALM_DELAY_TOTAL_MAX",1,530,ValueType.FLOAT),
    ALM_DELAY_TOTAL_SUM("SL-SRV-ALM_DELAY_TOTAL_SUM",1,535,ValueType.FLOAT),
    ALM_DELAY_FIRST_MIN("SL-SRV-ALM_DELAY_FIRST_MIN",1,540,ValueType.FLOAT),
    ALM_DELAY_FIRST_MAX("SL-SRV-ALM_DELAY_FIRST_MAX",1,545,ValueType.FLOAT),
    ALM_DELAY_FIRST_SUM("SL-SRV-ALM_DELAY_FIRST_SUM",1,550,ValueType.FLOAT),
    ALM_DELAY_Z1_P1("SL-SRV-ALM_DELAY_Z1_P1",1,555,ValueType.INTEGER),
    ALM_DELAY_Z1_P2("SL-SRV-ALM_DELAY_Z1_P2",1,560,ValueType.INTEGER),
    ALM_DELAY_Z1_P3("SL-SRV-ALM_DELAY_Z1_P3",1,565,ValueType.INTEGER),
    ALM_DELAY_Z1_P4("SL-SRV-ALM_DELAY_Z1_P4",1,570,ValueType.INTEGER),
    ALM_DELAY_Z1_L1("SL-SRV-ALM_DELAY_Z1_L1",1,575,ValueType.INTEGER),
    ALM_DELAY_Z1_L2("SL-SRV-ALM_DELAY_Z1_L2",1,580,ValueType.INTEGER),
    ALM_DELAY_Z1_L3("SL-SRV-ALM_DELAY_Z1_L3",1,585,ValueType.INTEGER),
    ALM_DELAY_Z1_L4("SL-SRV-ALM_DELAY_Z1_L4",1,590,ValueType.INTEGER),
    ALM_DELAY_Z2_P1("SL-SRV-ALM_DELAY_Z2_P1",1,595,ValueType.INTEGER),
    ALM_DELAY_Z2_P2("SL-SRV-ALM_DELAY_Z2_P2",1,600,ValueType.INTEGER),
    ALM_DELAY_Z2_P3("SL-SRV-ALM_DELAY_Z2_P3",1,605,ValueType.INTEGER),
    ALM_DELAY_Z2_P4("SL-SRV-ALM_DELAY_Z2_P4",1,610,ValueType.INTEGER),
    ALM_DELAY_Z2_L1("SL-SRV-ALM_DELAY_Z2_L1",1,615,ValueType.INTEGER),
    ALM_DELAY_Z2_L2("SL-SRV-ALM_DELAY_Z2_L2",1,620,ValueType.INTEGER),
    ALM_DELAY_Z2_L3("SL-SRV-ALM_DELAY_Z2_L3",1,625,ValueType.INTEGER),
    ALM_DELAY_Z2_L4("SL-SRV-ALM_DELAY_Z2_L4",1,630,ValueType.INTEGER),
    ALM_DELAY_Z3_P1("SL-SRV-ALM_DELAY_Z3_P1",1,635,ValueType.INTEGER),
    ALM_DELAY_Z3_P2("SL-SRV-ALM_DELAY_Z3_P2",1,640,ValueType.INTEGER),
    ALM_DELAY_Z3_P3("SL-SRV-ALM_DELAY_Z3_P3",1,645,ValueType.INTEGER),
    ALM_DELAY_Z3_P4("SL-SRV-ALM_DELAY_Z3_P4",1,650,ValueType.INTEGER),
    ALM_DELAY_Z3_L1("SL-SRV-ALM_DELAY_Z3_L1",1,655,ValueType.INTEGER),
    ALM_DELAY_Z3_L2("SL-SRV-ALM_DELAY_Z3_L2",1,660,ValueType.INTEGER),
    ALM_DELAY_Z3_L3("SL-SRV-ALM_DELAY_Z3_L3",1,665,ValueType.INTEGER),
    ALM_DELAY_Z3_L4("SL-SRV-ALM_DELAY_Z3_L4",1,670,ValueType.INTEGER),
    ALM_DELAY_Z4_P1("SL-SRV-ALM_DELAY_Z4_P1",1,675,ValueType.INTEGER),
    ALM_DELAY_Z4_P2("SL-SRV-ALM_DELAY_Z4_P2",1,680,ValueType.INTEGER),
    ALM_DELAY_Z4_P3("SL-SRV-ALM_DELAY_Z4_P3",1,685,ValueType.INTEGER),
    ALM_DELAY_Z4_P4("SL-SRV-ALM_DELAY_Z4_P4",1,690,ValueType.INTEGER),
    ALM_DELAY_Z4_L1("SL-SRV-ALM_DELAY_Z4_L1",1,695,ValueType.INTEGER),
    ALM_DELAY_Z4_L2("SL-SRV-ALM_DELAY_Z4_L2",1,700,ValueType.INTEGER),
    ALM_DELAY_Z4_L3("SL-SRV-ALM_DELAY_Z4_L3",1,705,ValueType.INTEGER),
    ALM_DELAY_Z4_L4("SL-SRV-ALM_DELAY_Z4_L4",1,710,ValueType.INTEGER),
    ALM_DELAY_TOTAL_SUM_P1("SL-SRV-ALM_DELAY_TOTAL_SUM_P1",1,715,ValueType.FLOAT),
    ALM_DELAY_TOTAL_SUM_P2("SL-SRV-ALM_DELAY_TOTAL_SUM_P2",1,720,ValueType.FLOAT),
    ALM_DELAY_TOTAL_SUM_P3("SL-SRV-ALM_DELAY_TOTAL_SUM_P3",1,725,ValueType.FLOAT),
    ALM_DELAY_TOTAL_SUM_P4("SL-SRV-ALM_DELAY_TOTAL_SUM_P4",1,730,ValueType.FLOAT),
    ALM_DELAY_TOTAL_SUM_L1("SL-SRV-ALM_DELAY_TOTAL_SUM_L1",1,735,ValueType.FLOAT),
    ALM_DELAY_TOTAL_SUM_L2("SL-SRV-ALM_DELAY_TOTAL_SUM_L2",1,740,ValueType.FLOAT),
    ALM_DELAY_TOTAL_SUM_L3("SL-SRV-ALM_DELAY_TOTAL_SUM_L3",1,745,ValueType.FLOAT),
    ALM_DELAY_TOTAL_SUM_L4("SL-SRV-ALM_DELAY_TOTAL_SUM_L4",1,750,ValueType.FLOAT),
    
    GC1_ATTEMPT("SL-SRV-GC-1_ATTEMPT",2,755,ValueType.INTEGER),
    GC1_SUCC_SETUP_COUNT("SL-SRV-GC-1_SUCC_SETUP_COUNT",2,760,ValueType.INTEGER),
    GC2_SETUP_TM_Z1_P1("SL-SRV-GC-2_SETUP_TM_Z1_P1",2,765,ValueType.INTEGER),
    GC2_SETUP_TM_Z1_P2("SL-SRV-GC-2_SETUP_TM_Z1_P2",2,770,ValueType.INTEGER),
    GC2_SETUP_TM_Z1_P3("SL-SRV-GC-2_SETUP_TM_Z1_P3",2,775,ValueType.INTEGER),
    GC2_SETUP_TM_Z1_P4("SL-SRV-GC-2_SETUP_TM_Z1_P4",2,780,ValueType.INTEGER),
    GC2_SETUP_TM_Z1_L1("SL-SRV-GC-2_SETUP_TM_Z1_L1",2,785,ValueType.INTEGER),
    GC2_SETUP_TM_Z1_L2("SL-SRV-GC-2_SETUP_TM_Z1_L2",2,790,ValueType.INTEGER),
    GC2_SETUP_TM_Z1_L3("SL-SRV-GC-2_SETUP_TM_Z1_L3",2,795,ValueType.INTEGER),
    GC2_SETUP_TM_Z1_L4("SL-SRV-GC-2_SETUP_TM_Z1_L4",2,800,ValueType.INTEGER),
    GC2_SETUP_TIME_MIN("SL-SRV-GC-2_SETUP_TIME_MIN",2,805,ValueType.FLOAT),
    GC2_SETUP_TIME_MAX("SL-SRV-GC-2_SETUP_TIME_MAX",2,810,ValueType.FLOAT),
    GC2_SETUP_TOTAL_DUR("SL-SRV-GC-2_SETUP_TOTAL_DUR",2,815,ValueType.FLOAT),
    GC2_SETUP_DUR_Z1_P1("SL-SRV-GC-2_SETUP_DUR_Z1_P1",2,820,ValueType.FLOAT),
    GC2_SETUP_DUR_Z1_P2("SL-SRV-GC-2_SETUP_DUR_Z1_P2",2,825,ValueType.FLOAT),
    GC2_SETUP_DUR_Z1_P3("SL-SRV-GC-2_SETUP_DUR_Z1_P3",2,830,ValueType.FLOAT),
    GC2_SETUP_DUR_Z1_P4("SL-SRV-GC-2_SETUP_DUR_Z1_P4",2,835,ValueType.FLOAT),
    GC2_SETUP_DUR_Z1_L1("SL-SRV-GC-2_SETUP_DUR_Z1_L1",2,840,ValueType.FLOAT),
    GC2_SETUP_DUR_Z1_L2("SL-SRV-GC-2_SETUP_DUR_Z1_L2",2,845,ValueType.FLOAT),
    GC2_SETUP_DUR_Z1_L3("SL-SRV-GC-2_SETUP_DUR_Z1_L3",2,850,ValueType.FLOAT),
    GC2_SETUP_DUR_Z1_L4("SL-SRV-GC-2_SETUP_DUR_Z1_L4",2,855,ValueType.FLOAT),
    GC3_CALL_DISC_TIME("SL-SRV-GC-3_CALL_DISC_TIME",2,860,ValueType.INTEGER),
    GC4_AUDIO_QUAL_SUCC("SL-SRV-GC-4_AUDIO_QUAL_SUCC",2,865,ValueType.INTEGER),
    GC4_AUDIO_QUAL_P1("SL-SRV-GC-4_AUDIO_QUAL_P1",2,870,ValueType.INTEGER),
    GC4_AUDIO_QUAL_P2("SL-SRV-GC-4_AUDIO_QUAL_P2",2,875,ValueType.INTEGER),
    GC4_AUDIO_QUAL_P3("SL-SRV-GC-4_AUDIO_QUAL_P3",2,880,ValueType.INTEGER),
    GC4_AUDIO_QUAL_P4("SL-SRV-GC-4_AUDIO_QUAL_P4",2,885,ValueType.INTEGER),
    GC4_AUDIO_QUAL_L1("SL-SRV-GC-4_AUDIO_QUAL_L1",2,890,ValueType.INTEGER),
    GC4_AUDIO_QUAL_L2("SL-SRV-GC-4_AUDIO_QUAL_L2",2,895,ValueType.INTEGER),
    GC4_AUDIO_QUAL_L3("SL-SRV-GC-4_AUDIO_QUAL_L3",2,900,ValueType.INTEGER),
    GC4_AUDIO_QUAL_L4("SL-SRV-GC-4_AUDIO_QUAL_L4",2,905,ValueType.INTEGER),
    GC4_AUDIO_QUAL_MIN("SL-SRV-GC-4_AUDIO_QUAL_MIN",2,910,ValueType.FLOAT),
    GC4_AUDIO_QUAL_MAX("SL-SRV-GC-4_AUDIO_QUAL_MAX",2,915,ValueType.FLOAT),
    GC4_AUDIO_QUAL_TOTAL("SL-SRV-GC-4_AUDIO_QUAL_TOTAL",2,920,ValueType.FLOAT),
    GC4_AUDIO_QUAL_Z1_P1("SL-SRV-GC-4_AUDIO_QUAL_Z1_P1",2,925,ValueType.FLOAT),
    GC4_AUDIO_QUAL_Z1_P2("SL-SRV-GC-4_AUDIO_QUAL_Z1_P2",2,930,ValueType.FLOAT),
    GC4_AUDIO_QUAL_Z1_P3("SL-SRV-GC-4_AUDIO_QUAL_Z1_P3",2,945,ValueType.FLOAT),
    GC4_AUDIO_QUAL_Z1_P4("SL-SRV-GC-4_AUDIO_QUAL_Z1_P4",2,950,ValueType.FLOAT),
    GC4_AUDIO_QUAL_Z1_L1("SL-SRV-GC-4_AUDIO_QUAL_Z1_L1",2,955,ValueType.FLOAT),
    GC4_AUDIO_QUAL_Z1_L2("SL-SRV-GC-4_AUDIO_QUAL_Z1_L2",2,960,ValueType.FLOAT),
    GC4_AUDIO_QUAL_Z1_L3("SL-SRV-GC-4_AUDIO_QUAL_Z1_L3",2,965,ValueType.FLOAT),
    GC4_AUDIO_QUAL_Z1_L4("SL-SRV-GC-4_AUDIO_QUAL_Z1_L4",2,970,ValueType.FLOAT),
    GC5_DELAY_COUNT_P1("SL-SRV-GC-5_DELAY_COUNT_P1",2,975,ValueType.INTEGER),
    GC5_DELAY_COUNT_P2("SL-SRV-GC-5_DELAY_COUNT_P2",2,980,ValueType.INTEGER),
    GC5_DELAY_COUNT_P3("SL-SRV-GC-5_DELAY_COUNT_P3",2,985,ValueType.INTEGER),
    GC5_DELAY_COUNT_P4("SL-SRV-GC-5_DELAY_COUNT_P4",2,990,ValueType.INTEGER),
    GC5_DELAY_COUNT_L1("SL-SRV-GC-5_DELAY_COUNT_L1",2,995,ValueType.INTEGER),
    GC5_DELAY_COUNT_L2("SL-SRV-GC-5_DELAY_COUNT_L2",2,1000,ValueType.INTEGER),
    GC5_DELAY_COUNT_L3("SL-SRV-GC-5_DELAY_COUNT_L3",2,1005,ValueType.INTEGER),
    GC5_DELAY_COUNT_L4("SL-SRV-GC-5_DELAY_COUNT_L4",2,1010,ValueType.INTEGER),
    GC5_DELAY_MIN("SL-SRV-GC-5_DELAY_MIN",2,1015,ValueType.FLOAT),
    GC5_DELAY_MAX("SL-SRV-GC-5_DELAY_MAX",2,1020,ValueType.FLOAT),
    GC5_DELAY_TOTAL("SL-SRV-GC-5_DELAY_TOTAL",2,1025,ValueType.FLOAT),
    GC5_DELAY_Z1_P1("SL-SRV-GC-5_DELAY_Z1_P1",2,1030,ValueType.FLOAT),
    GC5_DELAY_Z1_P2("SL-SRV-GC-5_DELAY_Z1_P2",2,1035,ValueType.FLOAT),
    GC5_DELAY_Z1_P3("SL-SRV-GC-5_DELAY_Z1_P3",2,1040,ValueType.FLOAT),
    GC5_DELAY_Z1_P4("SL-SRV-GC-5_DELAY_Z1_P4",2,1045,ValueType.FLOAT),
    GC5_DELAY_Z1_L1("SL-SRV-GC-5_DELAY_Z1_L1",2,1055,ValueType.FLOAT),
    GC5_DELAY_Z1_L2("SL-SRV-GC-5_DELAY_Z1_L2",2,1065,ValueType.FLOAT),
    GC5_DELAY_Z1_L3("SL-SRV-GC-5_DELAY_Z1_L3",2,1070,ValueType.FLOAT),
    GC5_DELAY_Z1_L4("SL-SRV-GC-5_DELAY_Z1_L4",2,1075,ValueType.FLOAT),
    CSD1_ATTEMPTS("SL-SRV-CSD-1_ATTEMPTS",2,1080,ValueType.INTEGER),
    CSD1_SUCCESS("SL-SRV-CSD-1_SUCCESS",2,1085,ValueType.INTEGER),
    CSD2_STABILITY("SL-SRV-CSD-2_STABILITY",2,1090,ValueType.INTEGER),
    CSD3_THROUGHPUT_MIN("SL-SRV-CSD-3_THROUGHPUT_MIN",2,1095,ValueType.FLOAT),
    CSD3_THROUGHPUT_MAX("SL-SRV-CSD-3_THROUGHPUT_MAX",2,1100,ValueType.FLOAT),
    CSD3_CONNECT_TOTAL_DUR("SL-SRV-CSD-3_CONNECT_TOTAL_DUR",2,1105,ValueType.FLOAT),
    CSD3_DATA_EXCH_SUCC("SL-SRV-CSD-3_DATA_EXCH_SUCC",2,1110,ValueType.FLOAT),
    CSD3_THROUGHPUT_Z1_P1("SL-SRV-CSD-3_THROUGHPUT_Z1_P1",2,1115,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z1_P2("SL-SRV-CSD-3_THROUGHPUT_Z1_P2",2,1120,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z1_P3("SL-SRV-CSD-3_THROUGHPUT_Z1_P3",2,1125,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z1_P4("SL-SRV-CSD-3_THROUGHPUT_Z1_P4",2,1130,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z1_L1("SL-SRV-CSD-3_THROUGHPUT_Z1_L1",2,1135,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z1_L2("SL-SRV-CSD-3_THROUGHPUT_Z1_L2",2,1140,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z1_L3("SL-SRV-CSD-3_THROUGHPUT_Z1_L3",2,1145,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z1_L4("SL-SRV-CSD-3_THROUGHPUT_Z1_L4",2,1150,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z2_P1("SL-SRV-CSD-3_THROUGHPUT_Z2_P1",2,1155,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z2_P2("SL-SRV-CSD-3_THROUGHPUT_Z2_P2",2,1160,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z2_P3("SL-SRV-CSD-3_THROUGHPUT_Z2_P3",2,1175,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z2_P4("SL-SRV-CSD-3_THROUGHPUT_Z2_P4",2,1180,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z2_L1("SL-SRV-CSD-3_THROUGHPUT_Z2_L1",2,1185,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z2_L2("SL-SRV-CSD-3_THROUGHPUT_Z2_L2",2,1190,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z2_L3("SL-SRV-CSD-3_THROUGHPUT_Z2_L3",2,1195,ValueType.INTEGER),
    CSD3_THROUGHPUT_Z2_L4("SL-SRV-CSD-3_THROUGHPUT_Z2_L4",2,1200,ValueType.INTEGER),
    CSD3_DATA_SUM_Z1_P1("SL-SRV-CSD-3_DATA_SUM_Z1_P1",2,1205,ValueType.FLOAT),
    CSD3_DATA_SUM_Z1_P2("SL-SRV-CSD-3_DATA_SUM_Z1_P2",2,1210,ValueType.FLOAT),
    CSD3_DATA_SUM_Z1_P3("SL-SRV-CSD-3_DATA_SUM_Z1_P3",2,1215,ValueType.FLOAT),
    CSD3_DATA_SUM_Z1_P4("SL-SRV-CSD-3_DATA_SUM_Z1_P4",2,1220,ValueType.FLOAT),
    CSD3_DATA_SUM_Z1_L1("SL-SRV-CSD-3_DATA_SUM_Z1_L1",2,1225,ValueType.FLOAT),
    CSD3_DATA_SUM_Z1_L2("SL-SRV-CSD-3_DATA_SUM_Z1_L2",2,1230,ValueType.FLOAT),
    CSD3_DATA_SUM_Z1_L3("SL-SRV-CSD-3_DATA_SUM_Z1_L3",2,1235,ValueType.FLOAT),
    CSD3_DATA_SUM_Z1_L4("SL-SRV-CSD-3_DATA_SUM_Z1_L4",2,1240,ValueType.FLOAT),
    CSD3_TIME_SUM_Z1_P1("SL-SRV-CSD-3_TIME_SUM_Z1_P1",2,1245,ValueType.FLOAT),
    CSD3_TIME_SUM_Z1_P2("SL-SRV-CSD-3_TIME_SUM_Z1_P2",2,1250,ValueType.FLOAT),
    CSD3_TIME_SUM_Z1_P3("SL-SRV-CSD-3_TIME_SUM_Z1_P3",2,1255,ValueType.FLOAT),
    CSD3_TIME_SUM_Z1_P4("SL-SRV-CSD-3_TIME_SUM_Z1_P4",2,1260,ValueType.FLOAT),
    CSD3_TIME_SUM_Z1_L1("SL-SRV-CSD-3_TIME_SUM_Z1_L1",2,1275,ValueType.FLOAT),
    CSD3_TIME_SUM_Z1_L2("SL-SRV-CSD-3_TIME_SUM_Z1_L2",2,1270,ValueType.FLOAT),
    CSD3_TIME_SUM_Z1_L3("SL-SRV-CSD-3_TIME_SUM_Z1_L3",2,1285,ValueType.FLOAT),
    CSD3_TIME_SUM_Z1_L4("SL-SRV-CSD-3_TIME_SUM_Z1_L4",2,1290,ValueType.FLOAT),
    IP1_ATTEMPTS("SL-SRV-IP-1_ATTEMPTS",2,1295,ValueType.INTEGER),
    IP1_SUCCESS("SL-SRV-IP-1_SUCCESS",2,1300,ValueType.INTEGER),
    IP2_STABILITY("SL-SRV-IP-2_STABILITY",2,1305,ValueType.INTEGER),
    IP3_THROUGHPUT_MIN("SL-SRV-IP-3_THROUGHPUT_MIN",2,1310,ValueType.FLOAT),
    IP3_THROUGHPUT_MAX("SL-SRV-IP-3_THROUGHPUT_MAX",2,1315,ValueType.FLOAT),
    IP3_CONNECT_TOTAL_DUR("SL-SRV-IP-3_CONNECT_TOTAL_DUR",2,1320,ValueType.FLOAT),
    IP3_DATA_EXCH_SUCC("SL-SRV-IP-3_DATA_EXCH_SUCC",2,1335,ValueType.FLOAT),
    IP3_THROUGHPUT_Z1_P1("SL-SRV-IP-3_THROUGHPUT_Z1_P1",2,1340,ValueType.INTEGER),
    IP3_THROUGHPUT_Z1_P2("SL-SRV-IP-3_THROUGHPUT_Z1_P2",2,1345,ValueType.INTEGER),
    IP3_THROUGHPUT_Z1_P3("SL-SRV-IP-3_THROUGHPUT_Z1_P3",2,1350,ValueType.INTEGER),
    IP3_THROUGHPUT_Z1_P4("SL-SRV-IP-3_THROUGHPUT_Z1_P4",2,1355,ValueType.INTEGER),
    IP3_THROUGHPUT_Z1_L1("SL-SRV-IP-3_THROUGHPUT_Z1_L1",2,1360,ValueType.INTEGER),
    IP3_THROUGHPUT_Z1_L2("SL-SRV-IP-3_THROUGHPUT_Z1_L2",2,1375,ValueType.INTEGER),
    IP3_THROUGHPUT_Z1_L3("SL-SRV-IP-3_THROUGHPUT_Z1_L3",2,1380,ValueType.INTEGER),
    IP3_THROUGHPUT_Z1_L4("SL-SRV-IP-3_THROUGHPUT_Z1_L4",2,1385,ValueType.INTEGER),
    IP3_THROUGHPUT_Z2_P1("SL-SRV-IP-3_THROUGHPUT_Z2_P1",2,1390,ValueType.INTEGER),
    IP3_THROUGHPUT_Z2_P2("SL-SRV-IP-3_THROUGHPUT_Z2_P2",2,1395,ValueType.INTEGER),
    IP3_THROUGHPUT_Z2_P3("SL-SRV-IP-3_THROUGHPUT_Z2_P3",2,1400,ValueType.INTEGER),
    IP3_THROUGHPUT_Z2_P4("SL-SRV-IP-3_THROUGHPUT_Z2_P4",2,1405,ValueType.INTEGER),
    IP3_THROUGHPUT_Z2_L1("SL-SRV-IP-3_THROUGHPUT_Z2_L1",2,1410,ValueType.INTEGER),
    IP3_THROUGHPUT_Z2_L2("SL-SRV-IP-3_THROUGHPUT_Z2_L2",2,1415,ValueType.INTEGER),
    IP3_THROUGHPUT_Z2_L3("SL-SRV-IP-3_THROUGHPUT_Z2_L3",2,1420,ValueType.INTEGER),
    IP3_THROUGHPUT_Z2_L4("SL-SRV-IP-3_THROUGHPUT_Z2_L4",2,1425,ValueType.INTEGER),
    IP3_DATA_SUM_Z1_P1("SL-SRV-IP-3_DATA_SUM_Z1_P1",2,1430,ValueType.FLOAT),
    IP3_DATA_SUM_Z1_P2("SL-SRV-IP-3_DATA_SUM_Z1_P2",2,1435,ValueType.FLOAT),
    IP3_DATA_SUM_Z1_P3("SL-SRV-IP-3_DATA_SUM_Z1_P3",2,1440,ValueType.FLOAT),
    IP3_DATA_SUM_Z1_P4("SL-SRV-IP-3_DATA_SUM_Z1_P4",2,1445,ValueType.FLOAT),
    IP3_DATA_SUM_Z1_L1("SL-SRV-IP-3_DATA_SUM_Z1_L1",2,1450,ValueType.FLOAT),
    IP3_DATA_SUM_Z1_L2("SL-SRV-IP-3_DATA_SUM_Z1_L2",2,1455,ValueType.FLOAT),
    IP3_DATA_SUM_Z1_L3("SL-SRV-IP-3_DATA_SUM_Z1_L3",2,1460,ValueType.FLOAT),
    IP3_DATA_SUM_Z1_L4("SL-SRV-IP-3_DATA_SUM_Z1_L4",2,1465,ValueType.FLOAT),
    IP3_TIME_SUM_Z1_P1("SL-SRV-IP-3_TIME_SUM_Z1_P1",2,1470,ValueType.FLOAT),
    IP3_TIME_SUM_Z1_P2("SL-SRV-IP-3_TIME_SUM_Z1_P2",2,1475,ValueType.FLOAT),
    IP3_TIME_SUM_Z1_P3("SL-SRV-IP-3_TIME_SUM_Z1_P3",2,1480,ValueType.FLOAT),
    IP3_TIME_SUM_Z1_P4("SL-SRV-IP-3_TIME_SUM_Z1_P4",2,1485,ValueType.FLOAT),
    IP3_TIME_SUM_Z1_L1("SL-SRV-IP-3_TIME_SUM_Z1_L1",2,1490,ValueType.FLOAT),
    IP3_TIME_SUM_Z1_L2("SL-SRV-IP-3_TIME_SUM_Z1_L2",2,1495,ValueType.FLOAT),
    IP3_TIME_SUM_Z1_L3("SL-SRV-IP-3_TIME_SUM_Z1_L3",2,1500,ValueType.FLOAT),
    IP3_TIME_SUM_Z1_L4("SL-SRV-IP-3_TIME_SUM_Z1_L4",2,1505,ValueType.FLOAT);
    
    public static final int COMMON_PART = 0;
    public static final int FIRST_PART = 1;
    public static final int SECOND_PART = 2;
    
    private static final String TIME_POSTFIX = "_UTS";
    private static final String TIME_PATTERN = "yyyyMMdd_HHmm";
    
    private String fullName;
    private Integer part;
    private Integer order;
    private ValueType type;
    
    private CsvHeaders(String name, int partNum, int orderValue, ValueType valueType) {
        fullName = name;
        part = partNum;
        type = valueType;
        order = orderValue;
    }
    
    /**
     * @return Returns the fullName.
     */
    public String getFullName() {
        return fullName;
    }
    
    /**
     * @return Returns the part.
     */
    public int getPart() {
        return part;
    }
    
    /**
     * @return Returns the type.
     */
    public ValueType getType() {
        return type;
    }
    
    /**
     * Convert generated value to string.
     *
     * @param value Object
     * @return String.
     */
    public String convertValueToString(Object value){
        switch (type){
        case TIME:
            return getTimeString(value);
        case STRING:
            return value==null?"":value.toString();
        case INTEGER:
            return value==null?"0":value.toString();
        case FLOAT:
            return getFloatString(value);
        default:
            return "ERROR: no type "+type;
        }
    }
    
    /**
     * Float value to string (mask '0.000')
     *
     * @param value Object
     * @return String
     */
    private String getFloatString(Object value){
        if(value==null||value.equals(0f)){
            return "0";
        }
        BigDecimal decValue = new BigDecimal(value.toString());
        decValue = decValue.setScale(3, RoundingMode.HALF_EVEN);
        return value.toString();
    }
    
    /**
     * Time value to string (mask 'yyyyMMdd_HHmm_UTS')
     *
     * @param value Object(Long)
     * @return String
     */
    public static String getTimeString(Object value){
        if((value==null)||!(value instanceof Long)) {
           return "ERROR value: "+value; 
        }
        SimpleDateFormat sf = new SimpleDateFormat(TIME_PATTERN);
        String dateStr = sf.format(new Date((Long)value));
        return dateStr+TIME_POSTFIX;
    }
    
    public static List<CsvHeaders> getHeadersForPart(int partValue){
        if(partValue>SECOND_PART){
            return null;
        }
        List<CsvHeaders> result = new ArrayList<CsvHeaders>();
        for(CsvHeaders header : values()){
            if(header.part.equals(partValue)||header.part.equals(COMMON_PART)){
                result.add(header);
            }
        }
        Collections.sort(result, new Comparator<CsvHeaders>() {
            @Override
            public int compare(CsvHeaders o1, CsvHeaders o2) {
                return o1.order.compareTo(o2.order);
            }
        });
        return result;
    }
    
    /**
     * <p>
     * Value types.
     * </p>
     * @author Shcharbatsevich_A
     * @since 1.0.0
     */
    public enum ValueType{
        TIME,
        INTEGER,
        FLOAT,
        STRING;
    }
}