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
 
public class ReadWorkflowGraph {
 
	//private iVar
	private String tName;
	FileWriter fstream ;
	BufferedWriter out ;
	String fileName;
	// Method process reading xml file
	public void setTagValue(){
 
		try {
 
			File xmlFile = new File("graphs//saveLivingstone-complex.xml");
			fstream = new FileWriter("graphs//saveLivingstone-complex.grs");
			out = new BufferedWriter(fstream);
			fileName="saveLivingstone-complex";
			// obtain a parser that produces DOM object trees from XML documents.
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();// obtain a Parser
			//Defines the API to obtain DOM Document instances from an XML document
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//The Document interface represents the entire XML document
			// provides the primary access to the document's data
			org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
			//direct access to the child node that is the document element of the document.
			doc.getDocumentElement().normalize();
 
			System.out.println("Root Element: "
					+ doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("*");
			System.out.println("=============================");
			out.write("new graph "+fileName+"\n\n");
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
			}
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
	
	public void writeNode(String tag, String val){
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
			}
			
		}catch(Exception e){
			System.out.println(" throws IOException");
		}
		
	}
	
	public void writeAttribute(){
		
	}
 
	public static void main(String... args) {
		ReadWorkflowGraph file = new ReadWorkflowGraph();
		file.setTagValue();
		System.out.println(file.getTagName());
 
	}
}