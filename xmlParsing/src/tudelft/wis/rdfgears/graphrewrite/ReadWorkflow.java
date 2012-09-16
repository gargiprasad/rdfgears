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


public class ReadWorkflow {

	//private iVar
	private String tName;
	FileWriter fstream ;
	BufferedWriter out ;
	String fileName;
	String toWrite="";
	int gotANameAttr= 0;
	// Method process reading xml file
	public void setTagValue(){

		try {

			File xmlFile = new File("graphs//testConstruct.xml");
			fstream = new FileWriter("graphs//testConstruct.grs");
			out = new BufferedWriter(fstream);
			fileName="testConstruct";

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

			NodeList nList1 = doc.getElementsByTagName("processor");
			NodeList nList2 = doc.getElementsByTagName("workflowInputPort");
			NodeList nList3 = doc.getElementsByTagName("network");
			NodeList children,children2;
			String networkOutput=null;
			//for tagname:network
			networkOutput=this.readAttributeValue(nList3, "network","output");
			
			
			//for tagname:processor
			toWrite+=this.readNode(nList1, "processor","id",networkOutput);
			out.write(toWrite+"\n");
			toWrite="";

			//for tagname:workflowInputPort
			toWrite+=this.readNode(nList2, "workflowInputPort","name");
			out.write(toWrite+"\n");
			toWrite="";


			


			// for edges between processors
			String target="";
			String source="";
			String inputPortSource="";
			String inputPortTarget="";
			int xyflag=1;
			int abflag = 1;	gotANameAttr= 0; 
			Element eElle,eElle2;
			for (int i = 0; i < nList1.getLength(); i++) {
				org.w3c.dom.Node nNode = nList1.item(i);
				xyflag=1;
				gotANameAttr=0;
				//finding the target node = current processor node
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					if (eElement.hasAttributes()){
						NamedNodeMap attributes = (NamedNodeMap)eElement.getAttributes();
						for (int g = 0; g < attributes.getLength(); g++) {
							Attr attribute = (Attr)attributes.item(g);
							if(attribute.getName().equals("id")){
								target=attribute.getValue();
							}
						}
					}
					/*
					if(eElement.hasChildNodes()){
						children=eElement.getChildNodes();
						for (int x = 0; x < children.getLength(); x++) {
							org.w3c.dom.Node nChild = children.item(x);
							if (nChild.getNodeType() == Node.ELEMENT_NODE) {
								eElle =  (Element) nChild;
								if (eElement.getNodeName().equals("inputPort")){

									toWrite+="new "+":"+"inputPort";

									if (eElement.hasAttributes()){
										// System.out.println("hello");


										NamedNodeMap a = (NamedNodeMap)eElement.getAttributes();
										//System.out.println("hello");
										for (int d = 0; d < a.getLength(); d++) {
											if (xyflag==1)
												toWrite+="(" ;//first attribute
											else
												toWrite+=",";// every subsequent attribute
											xyflag=a.getLength();
											Attr at = (Attr)a.item(d);
											if(!at.getName().equals("name")){
												toWrite+= at.getName() +
														"=" +at.getValue()+"";}
											else{
												toWrite+="$="+at.getValue();
												inputPortTarget=at.getValue();
												gotANameAttr=1;
											}
										}
										if(gotANameAttr==0){
											toWrite+=",$=inputPort"+(1)+")\n";
										}
										else 
											toWrite+=")\n";

									}else // no attributes case
										toWrite+="($=inputPort"+(1)+")\n";
								}
							}
						}}*/
					if(eElement.hasChildNodes()){
						children=eElement.getChildNodes();
						for (int x = 0; x < children.getLength(); x++) {
							org.w3c.dom.Node nChild = children.item(x);
							if (nChild.getNodeType() == Node.ELEMENT_NODE) {
								eElle =  (Element) nChild;
								/*if (eElle.getNodeName().equals("inputPort")){

									toWrite+="new "+":"+"inputPort";

									if (eElle.hasAttributes()){
										//System.out.println("hello");


										NamedNodeMap a = (NamedNodeMap)eElle.getAttributes();
										//System.out.println("hello");
										for (int d = 0; d < a.getLength(); d++) {
											if (abflag==1)
												toWrite+="(" ;//first attribute
											else
												toWrite+=",";// every subsequent attribute
											abflag=a.getLength();
											Attr at = (Attr)a.item(d);
											if(!at.getName().equals("name")){
												toWrite+= at.getName() +
														"=" +at.getValue()+"";}
											else{
												toWrite+="$="+at.getValue();
												inputPortSource=at.getValue();
												gotANameAttr=1;
											}
										}
										if(gotANameAttr==0){
											toWrite+=",$=inputPort"+(1)+")\n";
										}
										else 
											toWrite+=")\n";

									}else // no attributes case
										toWrite+="($=inputPort"+(1)+")\n";*/
									//finding the source node = source tag inside 
									//inputport tag of current processor node
									if(eElle.hasChildNodes()){
										children2=eElle.getChildNodes();
										for (int y = 0; y < children2.getLength(); y++) {
											org.w3c.dom.Node nChild2 = children2.item(y);
											if (nChild2.getNodeType() == Node.ELEMENT_NODE) {
												eElle2 =  (Element) nChild2;
												if(eElle2.getNodeName().equals("source"))	{
													if (eElle2.hasAttributes()){
														NamedNodeMap attr = (NamedNodeMap)eElle2.getAttributes();
														for (int g = 0; g < attr.getLength(); g++) {
															Attr att = (Attr)attr.item(g);
															if(att.getName().equals("processor"))
																source=att.getValue();
														}
													}
												}}}
									}}
						}
						}
					//}
				}
				if(!source.equals(target)){
					//toWrite+="new @("+inputPortSource+") -:hasPorts($=hasPort"+(i+1)+")-> @("+source+")\n";
					//toWrite+="new @("+inputPortTarget+") -:hasPorts($=hasPort"+(i+1)+")-> @("+target+")\n";
					//toWrite+="new @("+source+") -:connects($=connect"+(i+1)+")-> @("+inputPortTarget+")\n";
					toWrite+="new @("+source+") -:Connects($=connect"+(i+1)+")-> @("+target+")\n";

				}
				System.out.println("\nSource : "+source+"    Target: "+target+"\n");
			}
			out.write(toWrite);
			toWrite="";			
			out.write("\ndebug exec modRule *\nsave graph result.grs\n");

			// for edges between workflowinput and processors	
			// do later







			out.close();
		} catch (ParserConfigurationException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	//read any node
	public String readNode(NodeList nList1,String nodeName,String nodeTag){

		for (int i = 0; i < nList1.getLength(); i++) {
			org.w3c.dom.Node nNode = nList1.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				toWrite+="new "+":"+nodeName;
				int x=1;	gotANameAttr= 0;
				if (eElement.hasAttributes()){

					NamedNodeMap attributes = (NamedNodeMap)eElement.getAttributes();

					for (int g = 0; g < attributes.getLength(); g++) {
						if (x==1)
							toWrite+="(" ;//first attribute
							else
								toWrite+=",";// every subsequent attribute
						x=attributes.getLength();
						Attr attribute = (Attr)attributes.item(g);
						if(!attribute.getName().equals(nodeTag)){
							toWrite+= attribute.getName() +
									"=" +attribute.getValue()+"";}
						else{
							toWrite+="$="+attribute.getValue();
							gotANameAttr=1;
						}
					}
					if(gotANameAttr==0){
						toWrite+=",$="+nodeName+(i+1)+")\n";
					}
					else 
						toWrite+=")\n";

				}else // no attributes case
					toWrite+="($="+nodeName+(i+1)+")\n";
			}
		}

		return toWrite;

	}
	
	//read any node with optional parameter to handle network output
		public String readNode(NodeList nList1,String nodeName,String nodeTag,String output){

			for (int i = 0; i < nList1.getLength(); i++) {
				org.w3c.dom.Node nNode = nList1.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					toWrite+="new "+":"+nodeName;
					int x=1;	gotANameAttr= 0;
					if (eElement.hasAttributes()){

						NamedNodeMap attributes = (NamedNodeMap)eElement.getAttributes();

						for (int g = 0; g < attributes.getLength(); g++) {
							if (x==1)
								toWrite+="(" ;//first attribute
								else
									toWrite+=",";// every subsequent attribute
							x=attributes.getLength();
							Attr attribute = (Attr)attributes.item(g);
							if(!attribute.getName().equals(nodeTag)){
								toWrite+= attribute.getName() +
										"=" +attribute.getValue()+"";}
							else{
								if(!attribute.getValue().equals(output)){//to handle network output case
									toWrite+="$="+attribute.getValue()+" , isOutput=false ";
									gotANameAttr=1;
								}else {
									toWrite+="$="+attribute.getValue()+" , isOutput=true ";
									gotANameAttr=1;
								}
								
							}
						}
						if(gotANameAttr==0){
							toWrite+=",$="+nodeName+(i+1)+")\n";
						}
						else 
							toWrite+=")\n";

					}else // no attributes case
						toWrite+="($="+nodeName+(i+1)+")\n";
				}
			}

			return toWrite;

		}
	
	//read any attribute's value by tagname and attribute
	public String readAttributeValue(NodeList nList1,String nodeName,String nodeTag){
		String value = null;
		for (int i = 0; i < nList1.getLength(); i++) {
			org.w3c.dom.Node nNode = nList1.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				
				
				if (eElement.hasAttributes()){

					NamedNodeMap attributes = (NamedNodeMap)eElement.getAttributes();

					for (int g = 0; g < attributes.getLength(); g++) {
						
					
						Attr attribute = (Attr)attributes.item(g);
						if(attribute.getName().equals(nodeTag)){
							return attribute.getValue();
						}
					}
				}else // no attributes case
					return null;
			}
		}

		return value;

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




	public static void main(String... args) {
		ReadWorkflow file = new ReadWorkflow();
		file.setTagValue();
		System.out.println(file.getTagName());

	}
}