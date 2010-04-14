
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
    @ASN1Sequence ( name = "Neighbour", isSet = false )
    public class Neighbour implements IASN1PreparedElement {
            
        
    @ASN1PreparedElement
    @ASN1Choice ( name = "modeSpecificInfo" )
    public static class ModeSpecificInfoChoiceType implements IASN1PreparedElement {
            

       @ASN1PreparedElement
       @ASN1Sequence ( name = "fdd" , isSet = false )
       public static class FddSequenceType implements IASN1PreparedElement {
                
        @ASN1Element ( name = "neighbourIdentity", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private PrimaryCPICH_Info neighbourIdentity = null;
                
  
        @ASN1Element ( name = "uE-RX-TX-TimeDifferenceType2Info", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private UE_RX_TX_TimeDifferenceType2Info uE_RX_TX_TimeDifferenceType2Info = null;
                
  
        
        public PrimaryCPICH_Info getNeighbourIdentity () {
            return this.neighbourIdentity;
        }

        
        public boolean isNeighbourIdentityPresent () {
            return this.neighbourIdentity != null;
        }
        

        public void setNeighbourIdentity (PrimaryCPICH_Info value) {
            this.neighbourIdentity = value;
        }
        
  
        
        public UE_RX_TX_TimeDifferenceType2Info getUE_RX_TX_TimeDifferenceType2Info () {
            return this.uE_RX_TX_TimeDifferenceType2Info;
        }

        
        public boolean isUE_RX_TX_TimeDifferenceType2InfoPresent () {
            return this.uE_RX_TX_TimeDifferenceType2Info != null;
        }
        

        public void setUE_RX_TX_TimeDifferenceType2Info (UE_RX_TX_TimeDifferenceType2Info value) {
            this.uE_RX_TX_TimeDifferenceType2Info = value;
        }
        
  
                
                
        public void initWithDefaults() {
            
        }

        public IASN1PreparedElementData getPreparedData() {
            return preparedData_FddSequenceType;
        }

       private static IASN1PreparedElementData preparedData_FddSequenceType = CoderFactory.getInstance().newPreparedElementData(FddSequenceType.class);
                
       }

       
                
        @ASN1Element ( name = "fdd", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private FddSequenceType fdd = null;
                
  

       @ASN1PreparedElement
       @ASN1Sequence ( name = "tdd" , isSet = false )
       public static class TddSequenceType implements IASN1PreparedElement {
                
        @ASN1Element ( name = "neighbourAndChannelIdentity", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private CellAndChannelIdentity neighbourAndChannelIdentity = null;
                
  
        
        public CellAndChannelIdentity getNeighbourAndChannelIdentity () {
            return this.neighbourAndChannelIdentity;
        }

        
        public boolean isNeighbourAndChannelIdentityPresent () {
            return this.neighbourAndChannelIdentity != null;
        }
        

        public void setNeighbourAndChannelIdentity (CellAndChannelIdentity value) {
            this.neighbourAndChannelIdentity = value;
        }
        
  
                
                
        public void initWithDefaults() {
            
        }

        public IASN1PreparedElementData getPreparedData() {
            return preparedData_TddSequenceType;
        }

       private static IASN1PreparedElementData preparedData_TddSequenceType = CoderFactory.getInstance().newPreparedElementData(TddSequenceType.class);
                
       }

       
                
        @ASN1Element ( name = "tdd", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private TddSequenceType tdd = null;
                
  
        
        public FddSequenceType getFdd () {
            return this.fdd;
        }

        public boolean isFddSelected () {
            return this.fdd != null;
        }

        private void setFdd (FddSequenceType value) {
            this.fdd = value;
        }

        
        public void selectFdd (FddSequenceType value) {
            this.fdd = value;
            
                    setTdd(null);
                            
        }

        
  
        
        public TddSequenceType getTdd () {
            return this.tdd;
        }

        public boolean isTddSelected () {
            return this.tdd != null;
        }

        private void setTdd (TddSequenceType value) {
            this.tdd = value;
        }

        
        public void selectTdd (TddSequenceType value) {
            this.tdd = value;
            
                    setFdd(null);
                            
        }

        
  

	    public void initWithDefaults() {
	    }

        public IASN1PreparedElementData getPreparedData() {
            return preparedData_ModeSpecificInfoChoiceType;
        }

        private static IASN1PreparedElementData preparedData_ModeSpecificInfoChoiceType = CoderFactory.getInstance().newPreparedElementData(ModeSpecificInfoChoiceType.class);

    }

                
        @ASN1Element ( name = "modeSpecificInfo", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private ModeSpecificInfoChoiceType modeSpecificInfo = null;
                
  
        @ASN1Element ( name = "neighbourQuality", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private NeighbourQuality neighbourQuality = null;
                
  
        @ASN1Element ( name = "sfn-SFN-ObsTimeDifference2", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private SFN_SFN_ObsTimeDifference2 sfn_SFN_ObsTimeDifference2 = null;
                
  
        
        public ModeSpecificInfoChoiceType getModeSpecificInfo () {
            return this.modeSpecificInfo;
        }

        

        public void setModeSpecificInfo (ModeSpecificInfoChoiceType value) {
            this.modeSpecificInfo = value;
        }
        
  
        
        public NeighbourQuality getNeighbourQuality () {
            return this.neighbourQuality;
        }

        

        public void setNeighbourQuality (NeighbourQuality value) {
            this.neighbourQuality = value;
        }
        
  
        
        public SFN_SFN_ObsTimeDifference2 getSfn_SFN_ObsTimeDifference2 () {
            return this.sfn_SFN_ObsTimeDifference2;
        }

        

        public void setSfn_SFN_ObsTimeDifference2 (SFN_SFN_ObsTimeDifference2 value) {
            this.sfn_SFN_ObsTimeDifference2 = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(Neighbour.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            