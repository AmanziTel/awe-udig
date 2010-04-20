
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
    @ASN1Sequence ( name = "FrequencyInfoFDD", isSet = false )
    public class FrequencyInfoFDD implements IASN1PreparedElement {
            
        @ASN1Element ( name = "uarfcn-UL", isOptional =  true , hasTag =  false  , hasDefaultValue =  false  )
    
	private UARFCN uarfcn_UL = null;
                
  
        @ASN1Element ( name = "uarfcn-DL", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private UARFCN uarfcn_DL = null;
                
  
        
        public UARFCN getUarfcn_UL () {
            return this.uarfcn_UL;
        }

        
        public boolean isUarfcn_ULPresent () {
            return this.uarfcn_UL != null;
        }
        

        public void setUarfcn_UL (UARFCN value) {
            this.uarfcn_UL = value;
        }
        
  
        
        public UARFCN getUarfcn_DL () {
            return this.uarfcn_DL;
        }

        

        public void setUarfcn_DL (UARFCN value) {
            this.uarfcn_DL = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(FrequencyInfoFDD.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            