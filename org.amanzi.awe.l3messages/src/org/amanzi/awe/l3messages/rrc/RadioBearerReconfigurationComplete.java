
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
    @ASN1Sequence ( name = "RadioBearerReconfigurationComplete", isSet = false )
    public class RadioBearerReconfigurationComplete implements IASN1PreparedElement {
            
        @ASN1Element ( name = "rrc-TransactionIdentifier", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private RRC_TransactionIdentifier rrc_TransactionIdentifier = null;
                
  
        @ASN1Element ( name = "ul-IntegProtActivationInfo", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private IntegrityProtActivationInfo ul_IntegProtActivationInfo = null;
                
  
        @ASN1Element ( name = "ul-TimingAdvance", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private UL_TimingAdvance ul_TimingAdvance = null;
                
  
        @ASN1Element ( name = "count-C-ActivationTime", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private ActivationTime count_C_ActivationTime = null;
                
  
        @ASN1Element ( name = "dummy", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private RB_ActivationTimeInfoList dummy = null;
                
  
        @ASN1Element ( name = "ul-CounterSynchronisationInfo", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private UL_CounterSynchronisationInfo ul_CounterSynchronisationInfo = null;
                
  

       @ASN1PreparedElement
       @ASN1Sequence ( name = "laterNonCriticalExtensions" , isSet = false )
       public static class LaterNonCriticalExtensionsSequenceType implements IASN1PreparedElement {
                @ASN1BitString( name = "" )
    
        @ASN1Element ( name = "radioBearerReconfigurationComplete-r3-add-ext", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private BitString radioBearerReconfigurationComplete_r3_add_ext = null;
                
  

       @ASN1PreparedElement
       @ASN1Sequence ( name = "nonCriticalExtensions" , isSet = false )
       public static class NonCriticalExtensionsSequenceType implements IASN1PreparedElement {
                
                
                
        public void initWithDefaults() {
            
        }

        public IASN1PreparedElementData getPreparedData() {
            return preparedData_NonCriticalExtensionsSequenceType;
        }

       private static IASN1PreparedElementData preparedData_NonCriticalExtensionsSequenceType = CoderFactory.getInstance().newPreparedElementData(NonCriticalExtensionsSequenceType.class);
                
       }

       
                
        @ASN1Element ( name = "nonCriticalExtensions", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private NonCriticalExtensionsSequenceType nonCriticalExtensions = null;
                
  
        
        public BitString getRadioBearerReconfigurationComplete_r3_add_ext () {
            return this.radioBearerReconfigurationComplete_r3_add_ext;
        }

        
        public boolean isRadioBearerReconfigurationComplete_r3_add_extPresent () {
            return this.radioBearerReconfigurationComplete_r3_add_ext != null;
        }
        

        public void setRadioBearerReconfigurationComplete_r3_add_ext (BitString value) {
            this.radioBearerReconfigurationComplete_r3_add_ext = value;
        }
        
  
        
        public NonCriticalExtensionsSequenceType getNonCriticalExtensions () {
            return this.nonCriticalExtensions;
        }

        
        public boolean isNonCriticalExtensionsPresent () {
            return this.nonCriticalExtensions != null;
        }
        

        public void setNonCriticalExtensions (NonCriticalExtensionsSequenceType value) {
            this.nonCriticalExtensions = value;
        }
        
  
                
                
        public void initWithDefaults() {
            
        }

        public IASN1PreparedElementData getPreparedData() {
            return preparedData_LaterNonCriticalExtensionsSequenceType;
        }

       private static IASN1PreparedElementData preparedData_LaterNonCriticalExtensionsSequenceType = CoderFactory.getInstance().newPreparedElementData(LaterNonCriticalExtensionsSequenceType.class);
                
       }

       
                
        @ASN1Element ( name = "laterNonCriticalExtensions", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private LaterNonCriticalExtensionsSequenceType laterNonCriticalExtensions = null;
                
  
        
        public RRC_TransactionIdentifier getRrc_TransactionIdentifier () {
            return this.rrc_TransactionIdentifier;
        }

        

        public void setRrc_TransactionIdentifier (RRC_TransactionIdentifier value) {
            this.rrc_TransactionIdentifier = value;
        }
        
  
        
        public IntegrityProtActivationInfo getUl_IntegProtActivationInfo () {
            return this.ul_IntegProtActivationInfo;
        }

        
        public boolean isUl_IntegProtActivationInfoPresent () {
            return this.ul_IntegProtActivationInfo != null;
        }
        

        public void setUl_IntegProtActivationInfo (IntegrityProtActivationInfo value) {
            this.ul_IntegProtActivationInfo = value;
        }
        
  
        
        public UL_TimingAdvance getUl_TimingAdvance () {
            return this.ul_TimingAdvance;
        }

        
        public boolean isUl_TimingAdvancePresent () {
            return this.ul_TimingAdvance != null;
        }
        

        public void setUl_TimingAdvance (UL_TimingAdvance value) {
            this.ul_TimingAdvance = value;
        }
        
  
        
        public ActivationTime getCount_C_ActivationTime () {
            return this.count_C_ActivationTime;
        }

        
        public boolean isCount_C_ActivationTimePresent () {
            return this.count_C_ActivationTime != null;
        }
        

        public void setCount_C_ActivationTime (ActivationTime value) {
            this.count_C_ActivationTime = value;
        }
        
  
        
        public RB_ActivationTimeInfoList getDummy () {
            return this.dummy;
        }

        
        public boolean isDummyPresent () {
            return this.dummy != null;
        }
        

        public void setDummy (RB_ActivationTimeInfoList value) {
            this.dummy = value;
        }
        
  
        
        public UL_CounterSynchronisationInfo getUl_CounterSynchronisationInfo () {
            return this.ul_CounterSynchronisationInfo;
        }

        
        public boolean isUl_CounterSynchronisationInfoPresent () {
            return this.ul_CounterSynchronisationInfo != null;
        }
        

        public void setUl_CounterSynchronisationInfo (UL_CounterSynchronisationInfo value) {
            this.ul_CounterSynchronisationInfo = value;
        }
        
  
        
        public LaterNonCriticalExtensionsSequenceType getLaterNonCriticalExtensions () {
            return this.laterNonCriticalExtensions;
        }

        
        public boolean isLaterNonCriticalExtensionsPresent () {
            return this.laterNonCriticalExtensions != null;
        }
        

        public void setLaterNonCriticalExtensions (LaterNonCriticalExtensionsSequenceType value) {
            this.laterNonCriticalExtensions = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(RadioBearerReconfigurationComplete.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            