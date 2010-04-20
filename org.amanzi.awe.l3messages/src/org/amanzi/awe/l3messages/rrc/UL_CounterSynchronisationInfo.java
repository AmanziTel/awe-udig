
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
    @ASN1Sequence ( name = "UL_CounterSynchronisationInfo", isSet = false )
    public class UL_CounterSynchronisationInfo implements IASN1PreparedElement {
            
        @ASN1Element ( name = "rB-WithPDCP-InfoList", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private RB_WithPDCP_InfoList rB_WithPDCP_InfoList = null;
                
  
        @ASN1Element ( name = "startList", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private STARTList startList = null;
                
  
        
        public RB_WithPDCP_InfoList getRB_WithPDCP_InfoList () {
            return this.rB_WithPDCP_InfoList;
        }

        
        public boolean isRB_WithPDCP_InfoListPresent () {
            return this.rB_WithPDCP_InfoList != null;
        }
        

        public void setRB_WithPDCP_InfoList (RB_WithPDCP_InfoList value) {
            this.rB_WithPDCP_InfoList = value;
        }
        
  
        
        public STARTList getStartList () {
            return this.startList;
        }

        

        public void setStartList (STARTList value) {
            this.startList = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(UL_CounterSynchronisationInfo.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            