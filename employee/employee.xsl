<?xml version="1.0" encoding="UTF-8"?>
 <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0" >
     <xsl:template match="employees">
         <employees>
           <xsl:for-each select="employees">
               <employee>
                    <experience>    <xsl:value-of select="experience"/>         </experience>
                    <id>            <xsl:value-of select="id"/>                 </id>
                    <name>          <xsl:value-of select="name"/>               </name>
                    <salary>        <xsl:value-of select="salary*experience"/>  </salary>
               </employee>
           </xsl:for-each>
         </employees>

     </xsl:template>
 </xsl:stylesheet>
