/**
 * WSDeleteTransformerV2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.talend.mdm.webservice;

public class WSDeleteTransformerV2  implements java.io.Serializable {
    private org.talend.mdm.webservice.WSTransformerV2PK wsTransformerV2PK;

    public WSDeleteTransformerV2() {
    }

    public WSDeleteTransformerV2(
           org.talend.mdm.webservice.WSTransformerV2PK wsTransformerV2PK) {
           this.wsTransformerV2PK = wsTransformerV2PK;
    }


    /**
     * Gets the wsTransformerV2PK value for this WSDeleteTransformerV2.
     * 
     * @return wsTransformerV2PK
     */
    public org.talend.mdm.webservice.WSTransformerV2PK getWsTransformerV2PK() {
        return wsTransformerV2PK;
    }


    /**
     * Sets the wsTransformerV2PK value for this WSDeleteTransformerV2.
     * 
     * @param wsTransformerV2PK
     */
    public void setWsTransformerV2PK(org.talend.mdm.webservice.WSTransformerV2PK wsTransformerV2PK) {
        this.wsTransformerV2PK = wsTransformerV2PK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSDeleteTransformerV2)) return false;
        WSDeleteTransformerV2 other = (WSDeleteTransformerV2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsTransformerV2PK==null && other.getWsTransformerV2PK()==null) || 
             (this.wsTransformerV2PK!=null &&
              this.wsTransformerV2PK.equals(other.getWsTransformerV2PK())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getWsTransformerV2PK() != null) {
            _hashCode += getWsTransformerV2PK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSDeleteTransformerV2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSDeleteTransformerV2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsTransformerV2PK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsTransformerV2PK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.talend.com/mdm", "WSTransformerV2PK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
