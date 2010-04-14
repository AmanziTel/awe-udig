
package org.amanzi.awe.l3messages.rrc;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;
import org.bn.types.*;




    @ASN1PreparedElement
    @ASN1Choice ( name = "MeasuredResults" )
    public class MeasuredResults implements IASN1PreparedElement {
            
        @ASN1Element ( name = "intraFreqMeasuredResultsList", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private IntraFreqMeasuredResultsList intraFreqMeasuredResultsList = null;
                
  
        @ASN1Element ( name = "interFreqMeasuredResultsList", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private InterFreqMeasuredResultsList interFreqMeasuredResultsList = null;
                
  
        @ASN1Element ( name = "interRATMeasuredResultsList", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private InterRATMeasuredResultsList interRATMeasuredResultsList = null;
                
  
        @ASN1Element ( name = "trafficVolumeMeasuredResultsList", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private TrafficVolumeMeasuredResultsList trafficVolumeMeasuredResultsList = null;
                
  
        @ASN1Element ( name = "qualityMeasuredResults", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private QualityMeasuredResults qualityMeasuredResults = null;
                
  
        @ASN1Element ( name = "ue-InternalMeasuredResults", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private UE_InternalMeasuredResults ue_InternalMeasuredResults = null;
                
  
        @ASN1Element ( name = "ue-positioning-MeasuredResults", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private UE_Positioning_MeasuredResults ue_positioning_MeasuredResults = null;
                
  
        @ASN1Null ( name = "spare" ) 
    
        @ASN1Element ( name = "spare", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private org.bn.types.NullObject spare = null;
                
  
        
        public IntraFreqMeasuredResultsList getIntraFreqMeasuredResultsList () {
            return this.intraFreqMeasuredResultsList;
        }

        public boolean isIntraFreqMeasuredResultsListSelected () {
            return this.intraFreqMeasuredResultsList != null;
        }

        private void setIntraFreqMeasuredResultsList (IntraFreqMeasuredResultsList value) {
            this.intraFreqMeasuredResultsList = value;
        }

        
        public void selectIntraFreqMeasuredResultsList (IntraFreqMeasuredResultsList value) {
            this.intraFreqMeasuredResultsList = value;
            
                    setInterFreqMeasuredResultsList(null);
                
                    setInterRATMeasuredResultsList(null);
                
                    setTrafficVolumeMeasuredResultsList(null);
                
                    setQualityMeasuredResults(null);
                
                    setUe_InternalMeasuredResults(null);
                
                    setUe_positioning_MeasuredResults(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public InterFreqMeasuredResultsList getInterFreqMeasuredResultsList () {
            return this.interFreqMeasuredResultsList;
        }

        public boolean isInterFreqMeasuredResultsListSelected () {
            return this.interFreqMeasuredResultsList != null;
        }

        private void setInterFreqMeasuredResultsList (InterFreqMeasuredResultsList value) {
            this.interFreqMeasuredResultsList = value;
        }

        
        public void selectInterFreqMeasuredResultsList (InterFreqMeasuredResultsList value) {
            this.interFreqMeasuredResultsList = value;
            
                    setIntraFreqMeasuredResultsList(null);
                
                    setInterRATMeasuredResultsList(null);
                
                    setTrafficVolumeMeasuredResultsList(null);
                
                    setQualityMeasuredResults(null);
                
                    setUe_InternalMeasuredResults(null);
                
                    setUe_positioning_MeasuredResults(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public InterRATMeasuredResultsList getInterRATMeasuredResultsList () {
            return this.interRATMeasuredResultsList;
        }

        public boolean isInterRATMeasuredResultsListSelected () {
            return this.interRATMeasuredResultsList != null;
        }

        private void setInterRATMeasuredResultsList (InterRATMeasuredResultsList value) {
            this.interRATMeasuredResultsList = value;
        }

        
        public void selectInterRATMeasuredResultsList (InterRATMeasuredResultsList value) {
            this.interRATMeasuredResultsList = value;
            
                    setIntraFreqMeasuredResultsList(null);
                
                    setInterFreqMeasuredResultsList(null);
                
                    setTrafficVolumeMeasuredResultsList(null);
                
                    setQualityMeasuredResults(null);
                
                    setUe_InternalMeasuredResults(null);
                
                    setUe_positioning_MeasuredResults(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public TrafficVolumeMeasuredResultsList getTrafficVolumeMeasuredResultsList () {
            return this.trafficVolumeMeasuredResultsList;
        }

        public boolean isTrafficVolumeMeasuredResultsListSelected () {
            return this.trafficVolumeMeasuredResultsList != null;
        }

        private void setTrafficVolumeMeasuredResultsList (TrafficVolumeMeasuredResultsList value) {
            this.trafficVolumeMeasuredResultsList = value;
        }

        
        public void selectTrafficVolumeMeasuredResultsList (TrafficVolumeMeasuredResultsList value) {
            this.trafficVolumeMeasuredResultsList = value;
            
                    setIntraFreqMeasuredResultsList(null);
                
                    setInterFreqMeasuredResultsList(null);
                
                    setInterRATMeasuredResultsList(null);
                
                    setQualityMeasuredResults(null);
                
                    setUe_InternalMeasuredResults(null);
                
                    setUe_positioning_MeasuredResults(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public QualityMeasuredResults getQualityMeasuredResults () {
            return this.qualityMeasuredResults;
        }

        public boolean isQualityMeasuredResultsSelected () {
            return this.qualityMeasuredResults != null;
        }

        private void setQualityMeasuredResults (QualityMeasuredResults value) {
            this.qualityMeasuredResults = value;
        }

        
        public void selectQualityMeasuredResults (QualityMeasuredResults value) {
            this.qualityMeasuredResults = value;
            
                    setIntraFreqMeasuredResultsList(null);
                
                    setInterFreqMeasuredResultsList(null);
                
                    setInterRATMeasuredResultsList(null);
                
                    setTrafficVolumeMeasuredResultsList(null);
                
                    setUe_InternalMeasuredResults(null);
                
                    setUe_positioning_MeasuredResults(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public UE_InternalMeasuredResults getUe_InternalMeasuredResults () {
            return this.ue_InternalMeasuredResults;
        }

        public boolean isUe_InternalMeasuredResultsSelected () {
            return this.ue_InternalMeasuredResults != null;
        }

        private void setUe_InternalMeasuredResults (UE_InternalMeasuredResults value) {
            this.ue_InternalMeasuredResults = value;
        }

        
        public void selectUe_InternalMeasuredResults (UE_InternalMeasuredResults value) {
            this.ue_InternalMeasuredResults = value;
            
                    setIntraFreqMeasuredResultsList(null);
                
                    setInterFreqMeasuredResultsList(null);
                
                    setInterRATMeasuredResultsList(null);
                
                    setTrafficVolumeMeasuredResultsList(null);
                
                    setQualityMeasuredResults(null);
                
                    setUe_InternalMeasuredResults(null);
                
                    setUe_positioning_MeasuredResults(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public UE_Positioning_MeasuredResults getUe_positioning_MeasuredResults () {
            return this.ue_positioning_MeasuredResults;
        }

        public boolean isUe_positioning_MeasuredResultsSelected () {
            return this.ue_positioning_MeasuredResults != null;
        }

        private void setUe_positioning_MeasuredResults (UE_Positioning_MeasuredResults value) {
            this.ue_positioning_MeasuredResults = value;
        }

        
        public void selectUe_positioning_MeasuredResults (UE_Positioning_MeasuredResults value) {
            this.ue_positioning_MeasuredResults = value;
            
                    setIntraFreqMeasuredResultsList(null);
                
                    setInterFreqMeasuredResultsList(null);
                
                    setInterRATMeasuredResultsList(null);
                
                    setTrafficVolumeMeasuredResultsList(null);
                
                    setQualityMeasuredResults(null);
                
                    setUe_InternalMeasuredResults(null);
                
                    setUe_positioning_MeasuredResults(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public org.bn.types.NullObject getSpare () {
            return this.spare;
        }

        public boolean isSpareSelected () {
            return this.spare != null;
        }

        private void setSpare (org.bn.types.NullObject value) {
            this.spare = value;
        }

        
        public void selectSpare () {
            selectSpare (new org.bn.types.NullObject());
	}
	
        public void selectSpare (org.bn.types.NullObject value) {
            this.spare = value;
            
                    setIntraFreqMeasuredResultsList(null);
                
                    setInterFreqMeasuredResultsList(null);
                
                    setInterRATMeasuredResultsList(null);
                
                    setTrafficVolumeMeasuredResultsList(null);
                
                    setQualityMeasuredResults(null);
                
                    setUe_InternalMeasuredResults(null);
                
                    setUe_positioning_MeasuredResults(null);
                            
        }

        
  

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(MeasuredResults.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            