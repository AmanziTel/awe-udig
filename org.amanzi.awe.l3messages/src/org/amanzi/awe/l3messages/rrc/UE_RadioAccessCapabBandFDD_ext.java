
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
    @ASN1Sequence ( name = "UE_RadioAccessCapabBandFDD_ext", isSet = false )
    public class UE_RadioAccessCapabBandFDD_ext implements IASN1PreparedElement {
            
        @ASN1Element ( name = "radioFrequencyBandFDD", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private RadioFrequencyBandFDD radioFrequencyBandFDD = null;
                
  
        @ASN1Element ( name = "compressedModeMeasCapabFDDList-ext", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private CompressedModeMeasCapabFDDList_ext compressedModeMeasCapabFDDList_ext = null;
                
  
        
        public RadioFrequencyBandFDD getRadioFrequencyBandFDD () {
            return this.radioFrequencyBandFDD;
        }

        

        public void setRadioFrequencyBandFDD (RadioFrequencyBandFDD value) {
            this.radioFrequencyBandFDD = value;
        }
        
  
        
        public CompressedModeMeasCapabFDDList_ext getCompressedModeMeasCapabFDDList_ext () {
            return this.compressedModeMeasCapabFDDList_ext;
        }

        

        public void setCompressedModeMeasCapabFDDList_ext (CompressedModeMeasCapabFDDList_ext value) {
            this.compressedModeMeasCapabFDDList_ext = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(UE_RadioAccessCapabBandFDD_ext.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            