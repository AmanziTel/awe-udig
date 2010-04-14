
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
    @ASN1BoxedType ( name = "GSM_MeasuredResultsList" )
    public class GSM_MeasuredResultsList implements IASN1PreparedElement {
                
            @ASN1ValueRangeConstraint ( 
		
		min = 1L, 
		
		max = 8L 
		
	   )
	   
            @ASN1SequenceOf( name = "GSM-MeasuredResultsList" , isSetOf = false)
	    private java.util.Collection<GSM_MeasuredResults> value = null; 
    
            public GSM_MeasuredResultsList () {
            }
        
            public GSM_MeasuredResultsList ( java.util.Collection<GSM_MeasuredResults> value ) {
                setValue(value);
            }
                        
            public void setValue(java.util.Collection<GSM_MeasuredResults> value) {
                this.value = value;
            }
            
            public java.util.Collection<GSM_MeasuredResults> getValue() {
                return this.value;
            }            
            
            public void initValue() {
                setValue(new java.util.LinkedList<GSM_MeasuredResults>()); 
            }
            
            public void add(GSM_MeasuredResults item) {
                value.add(item);
            }

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(GSM_MeasuredResultsList.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            