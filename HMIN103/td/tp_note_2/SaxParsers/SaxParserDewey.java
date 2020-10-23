/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2000-2002 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Crimson" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, Sun Microsystems, Inc., 
 * http://www.sun.com.  For more information on the Apache Software 
 * Foundation, please see <http://www.apache.org/>.
 */

// JAXP packages
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

/**
 * Program to be modified to implement the begin/end schema encoding. 
 * 
 * The present code is drawn from SAXLocalNameCount.java (2002-04-18 by Edwin Goei)
 */
public class SaxParserDewey extends DefaultHandler {
    /** Constants used for JAXP 1.2 */
    static final String JAXP_SCHEMA_LANGUAGE =
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA =
        "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE =
        "http://java.sun.com/xml/jaxp/properties/schemaSource";

    /** A Hashtable with tag names as keys and Integers as values */
    private Hashtable<String, Integer> tags;
    private Stack<Integer> parent;
    private Stack<Integer> frere;

    // Parser calls this once at the beginning of a document
    public void startDocument() throws SAXException {
        tags = new Hashtable<String, Integer>();
        parent=new Stack<Integer>();
        frere =new Stack<Integer>();
    }

    // Parser calls this for each opening of an element in a document
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        // si y'a plus de frère on récupère le parent
    	if (frere.empty()) {
    	    frere.push(1);
    	    frere.push(1);
    	}

    	// sinon on continue de descendre
    	else {
    		int f = frere.pop();
    		int p = f;
    		if (!frere.empty()) {
    		    p = frere.pop();
            }
    		parent.push(p);
    		frere.push(p);
    		frere.push(f);
    		frere.push(1);
    	}
    }
    
    // Parser calls this for each end of an element in a document
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

        // on crée une pile temporaire qui permet de "renverser" notre pile parent
        Stack<Integer> temp = new Stack<Integer>();
        for(Integer e : parent) {
            temp.push(e);
        }

        frere.pop();

        // on commence à crée l'index en dépilant notre pile renversé
        String indice="";
    	for(Integer e : temp) {
    		indice=indice+String.valueOf(e)+".";
    	}

    	// on ajoute le dernier élément
        int f = frere.pop();
    	indice=indice+String.valueOf(f);

    	// on incrémente de 1 la pile des frères
    	frere.push(f+1);

    	System.out.println("INSERT INTO node (indice, tag, nodetype) VALUES("+indice+", "+"\""+localName+"\""+", elt);");

    	if(!parent.empty()) {
    	    parent.pop();
        }
    }
    
    // Parser calls this once after parsing a document
    public void endDocument() throws SAXException {
        System.out.println("End ---");
    }

    // Parser calls after parsing a text node
    public void characters(char[] ch, int start, int length) throws SAXException {
    	
        String str = new String(ch, start, length);
        if (!str.trim().isEmpty()) {

            // on réutilise le même code que start/endElement
        	if (frere.empty()) {
                frere.push(1);
                frere.push(1);
        	}

        	else {
        		int f = frere.pop();
        		int p = f;
        		if (!frere.empty()) {
        		    p= frere.pop();
                }
        		parent.push(p);
        		frere.push(p);
        		frere.push(f);
        		frere.push(1);
        	}

            Stack<Integer> temp = new Stack<Integer>();
            for (Integer e : parent) {
                temp.push(e);
            }

            frere.pop();

        	String indice="";
        	for(Integer e : temp) {
        		indice=indice+String.valueOf(e)+".";
        	}

        	int f = frere.pop();
        	indice=indice+String.valueOf(f);
        	frere.push(f+1);

            System.out.println("INSERT INTO node (indice, tag, nodetype) VALUES ("+indice+", NULL, txt);");

        	if (!parent.empty()) {
        	    parent.pop();
            }
        }
    }


    /**
     * Convert from a filename to a file URL.
     */
    private static String convertToFileURL(String filename) {
        // On JDK 1.2 and later, simplify this to:
        // "path = file.toURL().toString()".
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    private static void usage() {
        System.err.println("Usage: SAXLocalNameCount [-options] <file.xml>");
        System.exit(1);
    }

    static public void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();

        String filename = null;

        if (args.length < 1) {
            usage();
        }
        else
            filename = args[0];

        // Create a JAXP SAXParserFactory and configure it
        SAXParserFactory spf = SAXParserFactory.newInstance();

        // Set namespaceAware to true to get a parser that corresponds to
        // the default SAX2 namespace feature setting.  This is necessary
        // because the default value from JAXP 1.0 was defined to be false.
        spf.setNamespaceAware(true);

        // Create a JAXP SAXParser
        SAXParser saxParser = spf.newSAXParser();


        // Get the encapsulated SAX XMLReader
        XMLReader xmlReader = saxParser.getXMLReader();

        // Set the ContentHandler of the XMLReader
        xmlReader.setContentHandler(new SaxParserDewey());

        // Set an ErrorHandler before parsing
        xmlReader.setErrorHandler(new MyErrorHandler(System.err));

        // Tell the XMLReader to parse the XML document
        xmlReader.parse(convertToFileURL(filename));

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Temps d'exécution : " + time + "ms");
    }

    // Error handler to report errors and warnings
    private static class MyErrorHandler implements ErrorHandler {
        /** Error handler output goes here */
        private PrintStream out;

        MyErrorHandler(PrintStream out) {
            this.out = out;
        }

        /**
         * Returns a string describing parse exception details
         */
        private String getParseExceptionInfo(SAXParseException spe) {
            String systemId = spe.getSystemId();
            if (systemId == null) {
                systemId = "null";
            }
            String info = "URI=" + systemId +
                " Line=" + spe.getLineNumber() +
                ": " + spe.getMessage();
            return info;
        }

        // The following methods are standard SAX ErrorHandler methods.
        // See SAX documentation for more info.

        public void warning(SAXParseException spe) throws SAXException {
            out.println("Warning: " + getParseExceptionInfo(spe));
        }
        
        public void error(SAXParseException spe) throws SAXException {
            String message = "Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }

        public void fatalError(SAXParseException spe) throws SAXException {
            String message = "Fatal Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
    }
}