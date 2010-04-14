
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
    @ASN1Choice ( name = "UE_InternalEventResults" )
    public class UE_InternalEventResults implements IASN1PreparedElement {
            
        @ASN1Null ( name = "event6a" ) 
    
        @ASN1Element ( name = "event6a", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private org.bn.types.NullObject event6a = null;
                
  
        @ASN1Null ( name = "event6b" ) 
    
        @ASN1Element ( name = "event6b", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private org.bn.types.NullObject event6b = null;
                
  
        @ASN1Null ( name = "event6c" ) 
    
        @ASN1Element ( name = "event6c", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private org.bn.types.NullObject event6c = null;
                
  
        @ASN1Null ( name = "event6d" ) 
    
        @ASN1Element ( name = "event6d", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private org.bn.types.NullObject event6d = null;
                
  
        @ASN1Null ( name = "event6e" ) 
    
        @ASN1Element ( name = "event6e", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private org.bn.types.NullObject event6e = null;
                
  
        @ASN1Element ( name = "event6f", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private PrimaryCPICH_Info event6f = null;
                
  
        @ASN1Element ( name = "event6g", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private PrimaryCPICH_Info event6g = null;
                
  
        @ASN1Null ( name = "spare" ) 
    
        @ASN1Element ( name = "spare", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private org.bn.types.NullObject spare = null;
                
  
        
        public org.bn.types.NullObject getEvent6a () {
            return this.event6a;
        }

        public boolean isEvent6aSelected () {
            return this.event6a != null;
        }

        private void setEvent6a (org.bn.types.NullObject value) {
            this.event6a = value;
        }

        
        public void selectEvent6a () {
            selectEvent6a (new org.bn.types.NullObject());
	}
	
        public void selectEvent6a (org.bn.types.NullObject value) {
            this.event6a = value;
            
                    setEvent6b(null);
                
                    setEvent6c(null);
                
                    setEvent6d(null);
                
                    setEvent6e(null);
                
                    setEvent6f(null);
                
                    setEvent6g(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public org.bn.types.NullObject getEvent6b () {
            return this.event6b;
        }

        public boolean isEvent6bSelected () {
            return this.event6b != null;
        }

        private void setEvent6b (org.bn.types.NullObject value) {
            this.event6b = value;
        }

        
        public void selectEvent6b () {
            selectEvent6b (new org.bn.types.NullObject());
	}
	
        public void selectEvent6b (org.bn.types.NullObject value) {
            this.event6b = value;
            
                    setEvent6a(null);
                
                    setEvent6c(null);
                
                    setEvent6d(null);
                
                    setEvent6e(null);
                
                    setEvent6f(null);
                
                    setEvent6g(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public org.bn.types.NullObject getEvent6c () {
            return this.event6c;
        }

        public boolean isEvent6cSelected () {
            return this.event6c != null;
        }

        private void setEvent6c (org.bn.types.NullObject value) {
            this.event6c = value;
        }

        
        public void selectEvent6c () {
            selectEvent6c (new org.bn.types.NullObject());
	}
	
        public void selectEvent6c (org.bn.types.NullObject value) {
            this.event6c = value;
            
                    setEvent6a(null);
                
                    setEvent6b(null);
                
                    setEvent6d(null);
                
                    setEvent6e(null);
                
                    setEvent6f(null);
                
                    setEvent6g(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public org.bn.types.NullObject getEvent6d () {
            return this.event6d;
        }

        public boolean isEvent6dSelected () {
            return this.event6d != null;
        }

        private void setEvent6d (org.bn.types.NullObject value) {
            this.event6d = value;
        }

        
        public void selectEvent6d () {
            selectEvent6d (new org.bn.types.NullObject());
	}
	
        public void selectEvent6d (org.bn.types.NullObject value) {
            this.event6d = value;
            
                    setEvent6a(null);
                
                    setEvent6b(null);
                
                    setEvent6c(null);
                
                    setEvent6e(null);
                
                    setEvent6f(null);
                
                    setEvent6g(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public org.bn.types.NullObject getEvent6e () {
            return this.event6e;
        }

        public boolean isEvent6eSelected () {
            return this.event6e != null;
        }

        private void setEvent6e (org.bn.types.NullObject value) {
            this.event6e = value;
        }

        
        public void selectEvent6e () {
            selectEvent6e (new org.bn.types.NullObject());
	}
	
        public void selectEvent6e (org.bn.types.NullObject value) {
            this.event6e = value;
            
                    setEvent6a(null);
                
                    setEvent6b(null);
                
                    setEvent6c(null);
                
                    setEvent6d(null);
                
                    setEvent6f(null);
                
                    setEvent6g(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public PrimaryCPICH_Info getEvent6f () {
            return this.event6f;
        }

        public boolean isEvent6fSelected () {
            return this.event6f != null;
        }

        private void setEvent6f (PrimaryCPICH_Info value) {
            this.event6f = value;
        }

        
        public void selectEvent6f (PrimaryCPICH_Info value) {
            this.event6f = value;
            
                    setEvent6a(null);
                
                    setEvent6b(null);
                
                    setEvent6c(null);
                
                    setEvent6d(null);
                
                    setEvent6e(null);
                
                    setEvent6g(null);
                
                    setSpare(null);
                            
        }

        
  
        
        public PrimaryCPICH_Info getEvent6g () {
            return this.event6g;
        }

        public boolean isEvent6gSelected () {
            return this.event6g != null;
        }

        private void setEvent6g (PrimaryCPICH_Info value) {
            this.event6g = value;
        }

        
        public void selectEvent6g (PrimaryCPICH_Info value) {
            this.event6g = value;
            
                    setEvent6a(null);
                
                    setEvent6b(null);
                
                    setEvent6c(null);
                
                    setEvent6d(null);
                
                    setEvent6e(null);
                
                    setEvent6f(null);
                
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
            
                    setEvent6a(null);
                
                    setEvent6b(null);
                
                    setEvent6c(null);
                
                    setEvent6d(null);
                
                    setEvent6e(null);
                
                    setEvent6f(null);
                
                    setEvent6g(null);
                            
        }

        
  

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(UE_InternalEventResults.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            