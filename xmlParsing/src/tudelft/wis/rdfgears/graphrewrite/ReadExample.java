package tudelft.wis.rdfgears.graphrewrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
 
public class ReadExample {
 
	//private iVar
	private String tName;
	FileWriter fstream ;
	BufferedWriter out ;
	String fileName;
	// Method process reading xml file
	public void setTagValue(){
 
		try {
 
			File xmlFile = new File("graphs//Sample//example.xml");
			fstream = new FileWriter("graphs//Sample//example.grs");
			out = new BufferedWriter(fstream);
			fileName="example";
			
			// obtain a parser that produces DOM object trees from XML documents.
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();// obtain a Parser
			
			//Defines the API to obtain DOM Document instances from an XML document
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			//The Document interface represents the entire XML document
			// provides the primary access to the document's data
			org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
			
			//direct access to the child node that is the document element of the document.
			doc.getDocumentElement().normalize();
 
			System.out.println("Root Element: " + doc.getDocumentElement().getNodeName());
			
			//NodeList nList = doc.getElementsByTagName("*");
			System.out.println("=============================");
			
			out.write("new graph "+fileName+"\n\n");
			
			NodeList nList1 = doc.getElementsByTagName("balls");
			NodeList nList2 = doc.getElementsByTagName("bats");
			String toWrite="";
			
			//for tagname:node
			for (int i = 0; i < nList1.getLength(); i++) {
				org.w3c.dom.Node nNode = nList1.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					 Element eElement = (Element) nNode;
					 toWrite+="new "+"ball"+(i+1)+":"+eElement.getNodeName();
					int x=1;	
					 if (eElement.hasAttributes()){
						
					      NamedNodeMap attributes = (NamedNodeMap)eElement.getAttributes();
					      
			                for (int g = 0; g < attributes.getLength(); g++) {
			                	if (x==1)
									toWrite+="(" ;//first attribute
								else
									toWrite+=",";// every subsequent attribute
			                	x=attributes.getLength();
			                    Attr attribute = (Attr)attributes.item(g);
			                    toWrite+= attribute.getName() +
			                    "=\"" +attribute.getValue()+"\"";
			                }
			                toWrite+=")\n";
						}
				}
			}
			out.write(toWrite);
			toWrite="";
			//for tagname:edge
			for (int i = 0; i < nList2.getLength(); i++) {
				org.w3c.dom.Node nNode = nList2.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					 Element eElement = (Element) nNode;
					 toWrite+="new ball"+getStringname(eElement.getAttribute("source"))+" -:bats-> "+"ball"+getStringname(eElement.getAttribute("target"));
					/*int x=1;	
					 if (eElement.hasAttributes()){
					      NamedNodeMap attributes = (NamedNodeMap)eElement.getAttributes();
					      
			                for (int g = 0; g < attributes.getLength(); g++) {
			                	if (x==1)
									toWrite+="(" ;//first attribute
								else
									toWrite+=",";// every subsequent attribute
			                	x=attributes.getLength();
			                    Attr attribute = (Attr)attributes.item(g);
			                    toWrite+=  attribute.getName() +
			                    "=\"" +attribute.getValue()+"\"";
			                }
			                toWrite+=")\n";
						}*/
					 toWrite+="\n";
				}
			}
			out.write(toWrite);
			toWrite="";
			toWrite="\ndebug exec sampleRule\ndump graph result.grs";
			out.write(toWrite);
			/*
			// Keep reading elements till you reach the end of subNode i.e - end of </operations> tag
			for (int i = 0; i < nList.getLength(); i++) {
				org.w3c.dom.Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
					Element eElement = (Element) nNode;
					// Pass the tagName to method so that it can be populated
					
					if(eElement.hasChildNodes()){
						System.out.print("Tagname :'"+eElement.getNodeName()+"'\n Value:'"+eElement.getFirstChild().getNodeValue()+"'");
						writeNode(eElement.getNodeName(),eElement.getFirstChild().getNodeValue());
							if (eElement.hasAttributes()){
							      NamedNodeMap attributes = (NamedNodeMap)eElement.getAttributes();
					                for (int g = 0; g < attributes.getLength(); g++) {
					                    Attr attribute = (Attr)attributes.item(g);
					                    System.out.print(" Attribute: '" + attribute.getName() +
					                    "' with value '" +attribute.getValue()+"'\n\n");
					                }
								System.out.println("_______________________________________________");
							}
						}
					else{
						System.out.println("Tagname :'"+eElement.getNodeName()+"'\n Value:'"+eElement.getNextSibling().getNodeValue()+"'");
						writeNode(eElement.getNodeName(),eElement.getNextSibling().getNodeValue());
						if (eElement.hasAttributes()){
					      NamedNodeMap attributes = (NamedNodeMap)eElement.getAttributes();
			                for (int g = 0; g < attributes.getLength(); g++) {
			                    Attr attribute = (Attr)attributes.item(g);
			                    System.out.print("   Attribute: '" + attribute.getName() +
			                    "' with value '" +attribute.getValue()+"'\n\n");
			                }
			                System.out.println("_______________________________________________");
					} }
				}
			}*/
			out.close();
		} catch (ParserConfigurationException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
 
	// Method return tagValue fetched from file to its caller 
	public String populateTagValue(String tName, Element element) {
		NodeList nList = element.getElementsByTagName(tName).item(0)
				.getChildNodes();
		Node nValue = nList.item(0);
		return nValue.getNodeValue();
	}
 
	// Method Sets the tagName received through its param 
	public void processXML(String tagName){
 
		this.tName = tagName;
	}
 
	// Method return tagName to its caller/invoker 
	public String getTagName(){
 
		return tName;
	}
	
	public int getStringname(String str){
		if (str.equals("first"))
			return 1;
		else if(str.equals("second"))
			return 2;
		else if(str.equalsIgnoreCase("third"))
				return 3;
		return 0;
	}
	
	/*public void writeNode(String tag, String val){
		try{
			if (tag == "metadata"){
				out.write("new WorkFlowGraph1:WorkFlowGraph");
			}else
			if (tag == "id"){
				out.write("(id=\""+val+"\",");
			}else
			if (tag == "description"){
				if(val.matches("^\\s*$"))
					out.write(" desc=\""+""+"\",");
				else
					out.write(" desc=\""+val+"\",");
			}else
			if (tag == "password"){
				if(val.matches("^\\s*$"))
					out.write(" pass=\""+""+"\")\n");
				else
					out.write(" pass=\""+val+"\")\n");
			}if (tag == "graph"){//forexample.xml from here
				out.write("new Graph1:Graph");
			}else
				if(tag == "node"){
					if(val.matches("^\\s*$"))
						out.write(" name=\""+""+"\")\n");
					else
						out.write(" name=\""+val+"\")\n");
				}
			
		}catch(Exception e){
			System.out.println(" throws IOException");
		}
		
	}*/
	
	/*public void writeAttribute(){
		
	}*/
 
	public static void main(String... args) {
		ReadExample file = new ReadExample();
		file.setTagValue();
		System.out.println(file.getTagName());
 
	}
}