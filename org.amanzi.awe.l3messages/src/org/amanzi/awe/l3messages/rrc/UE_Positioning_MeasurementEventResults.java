
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
    @ASN1Choice ( name = "UE_Positioning_MeasurementEventResults" )
    public class UE_Positioning_MeasurementEventResults implements IASN1PreparedElement {
            
        @ASN1Element ( name = "event7a", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private UE_Positioning_PositionEstimateInfo event7a = null;
                
  
        @ASN1Element ( name = "event7b", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private UE_Positioning_OTDOA_Measurement event7b = null;
                
  
        @ASN1Element ( name = "event7c", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private UE_Positioning_GPS_MeasurementResults event7c = null;
                
  
        @ASN1Null ( name = "spare" ) 
    
        @ASN1Element ( name = "spare", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private org.bn.types.NullObject spare = null;
                
  
        
        public UE_Positioning_PositionEstimateInfo getEvent7a () {
            return this.event7a;
        }

        public boolean isEvent7aSelected () {
            return this.event7a != null;
        }

        private void setEvent7a (UE_Positioning_PositionEstimateInfo value) {
            this.event7a = value;
        }

        
        public void selectEvent7a (UE_Positioning_PositionEstimateInfo value) {
            this.event7a = value;
            
                    setEvent7b(null);
                
                    setEvent7c(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public UE_Positioning_OTDOA_Measurement getEvent7b () {
            return this.event7b;
        }

        public boolean isEvent7bSelected () {
            return this.event7b != null;
        }

        private void setEvent7b (UE_Positioning_OTDOA_Measurement value) {
            this.event7b = value;
        }

        
        public void selectEvent7b (UE_Positioning_OTDOA_Measurement value) {
            this.event7b = value;
            
                    setEvent7a(null);
                
                    setEvent7c(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public UE_Positioning_GPS_MeasurementResults getEvent7c () {
            return this.event7c;
        }

        public boolean isEvent7cSelected () {
            return this.event7c != null;
        }

        private void setEvent7c (UE_Positioning_GPS_MeasurementResults value) {
            this.event7c = value;
        }

        
        public void selectEvent7c (UE_Positioning_GPS_MeasurementResults value) {
            this.event7c = value;
            
                    setEvent7a(null);
                
                    setEvent7b(null);
                
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
            
                    setEvent7a(null);
                
                    setEvent7b(null);
                
                    setEvent7c(null);
                            
        }

        
  

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(UE_Positioning_MeasurementEventResults.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            