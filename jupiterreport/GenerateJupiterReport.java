package net.sf.tools.jupiter.actions;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.WorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.ObjectPluginAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GenerateJupiterReport implements IObjectActionDelegate {

	private Shell shell;
	private int row;

	/**
	 * Constructor for Action1.
	 */
	public GenerateJupiterReport() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		ObjectPluginAction action1=(ObjectPluginAction)action;
		ISelection selection = action1.getSelection();
		TreeSelection treeSel=(TreeSelection)selection;
		TreePath[] treePaths = treeSel.getPaths();
		List<String> reviewFilePaths=new ArrayList<String>();
		// get all jupiter files
		String lastProjectName=null;
		String oneProjectName=null;
		for(int i=0;i<treePaths.length;i++){
			Object lastSeg=treePaths[i].getLastSegment();
			File file=(File)lastSeg;
			if(file.getLocation().toString().endsWith(".review")){
				reviewFilePaths.add(file.getLocation().toString());
				String currentProject=file.getFullPath().segment(0);
				if(lastProjectName==null){
					lastProjectName=currentProject;
					oneProjectName=lastProjectName;
				}else if(!lastProjectName.equals(currentProject)){ // cross at least 2 projects 
					lastProjectName=currentProject;
					oneProjectName=null;
				}
			}
		}
		
		if(reviewFilePaths.size()<=0){
			MessageDialog.openInformation(shell,"Generate Jupiter Report","No code review file");
			return;
		}
		
		String excelFilePath=null;
		try{
			// write to Excel
			WorkspaceRoot root = (WorkspaceRoot)ResourcesPlugin.getWorkspace().getRoot(); 
			String workspaceRootPath=root.getLocation().toString();
			excelFilePath=getExcelFileName(workspaceRootPath,oneProjectName);
			java.io.File excelFile=new java.io.File(excelFilePath);
			BufferedWriter excelWriter = new BufferedWriter(new FileWriter(excelFile));

			row=0;
			for (String reviewFilePath : reviewFilePaths) {
				readXML2Excel(reviewFilePath,excelWriter);
			}
			writeStatisticsItems(excelWriter);
			excelWriter.close();
		}catch(Exception ex){
			MessageDialog.openInformation(shell,"Generate Jupiter Report Failed",ex.getClass().getSimpleName()+":"+ex.getMessage());
			return;
		}

		System.out.println("Write Excel completed, reviewed count is "+row+", file located "+excelFilePath);
		MessageDialog.openInformation(shell,"Generate Jupiter Report","Write Excel completed, reviewed count is "+row+", file located "+excelFilePath);
	}
	
	private String getExcelFileName(String workspaceRootPath,String projectName){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String time=sdf.format(new Date());
		String excelFilePath=null;
		if(projectName==null)
			excelFilePath=workspaceRootPath+"\\jupiter_report"+time+".csv";
		else
			excelFilePath=workspaceRootPath+"\\"+projectName+"\\jupiter_report"+time+".csv";
		return excelFilePath;
	}

	private void readXML2Excel(String xmlFile,BufferedWriter excelWriter) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factory.newDocumentBuilder();
		Document doc = db.parse(new java.io.File(xmlFile));
		Element element = doc.getDocumentElement();
		String reviewId=element.getAttribute("id");
		NodeList nodes = element.getChildNodes();
		Map<String, String> lastRow = new HashMap<String, String>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node result = nodes.item(i);
			if (result.getNodeName().equals("ReviewIssue")) {
				NodeList items = result.getChildNodes();
				for (int k = 0; k <= items.getLength(); k++) {
					Node item = items.item(k);
					if (item == null)
						continue;

					String nodeName = item.getNodeName();
					if (nodeName.equals("ReviewIssueMeta")) {
						writeLine2CSV(excelWriter, lastRow, reviewId);

						NodeList metas = item.getChildNodes();
						for (int x = 0; x < metas.getLength(); x++) {
							Node metaItem = metas.item(x);
							if (metaItem.getNodeName().equals("CreationDate")) {
								lastRow.put(metaItem.getNodeName(), metaItem.getTextContent());
							}
						}
					} else if (nodeName.equals("Status")
							|| nodeName.equals("Resolution")
							|| nodeName.equals("Severity")
							|| nodeName.equals("Type")) {
						lastRow.put(nodeName, getLastDotField(item.getTextContent()));
					} else {
						lastRow.put(nodeName, item.getTextContent());
					}
				}
			}
		}

		// write last row
		writeLine2CSV(excelWriter, lastRow, reviewId);
	}
	
	private void writeLine2CSV(BufferedWriter excelWriter,Map<String,String> lastRow,String reviewId) throws Exception{
		if(lastRow.size()>0){
			if(lastRow.get("CreationDate")!=null){
				if(!haveHeader){
					writeHeader(excelWriter);
				}
				
				excelWriter.newLine();
				excelWriter.write(replaceCommaAndLine(lastRow.get("CreationDate"))+",");
				
				String reviewerId=lastRow.get("ReviewerId");
				excelWriter.write(replaceCommaAndLine(reviewerId)+",");
				increaseCountForStats(reviewerId,reviewerIdStats);
				
				String assignedTo=lastRow.get("AssignedTo");
				excelWriter.write(replaceCommaAndLine(assignedTo)+",");
				increaseCountForStats(assignedTo,assignedToStats);
				
				excelWriter.write(replaceCommaAndLine(reviewId)+",");
				increaseCountForStats(reviewId,reviewIdStats);
				
				excelWriter.write(replaceCommaAndLine(lastRow.get("File"))+",");
				
				String type=lastRow.get("Type");
				excelWriter.write(replaceCommaAndLine(type)+",");
				increaseCountForStats(type,typeStats);
				
				String severity=lastRow.get("Severity");
				excelWriter.write(replaceCommaAndLine(severity)+",");
				increaseCountForStats(severity,severityStats);
				
				excelWriter.write(replaceCommaAndLine(lastRow.get("Summary"))+",");
				excelWriter.write(replaceCommaAndLine(lastRow.get("Description"))+",");
				
				String resolution=lastRow.get("Resolution");
				excelWriter.write(replaceCommaAndLine(resolution)+",");
				increaseCountForStats(resolution,resolutionStats);

				excelWriter.write(replaceCommaAndLine(lastRow.get("Annotation"))+",");
				
				String status=lastRow.get("Status");
				excelWriter.write(replaceCommaAndLine(status));
				increaseCountForStats(status,statusStats);

				row++;
			}
			lastRow.clear();
		}
	}
	
	private void increaseCountForStats(String itemName,Map<String,Integer> statsData){
		if(itemName!=null){
			Integer cnt=statsData.get(itemName);
			if(cnt==null){
				statsData.put(itemName, 1);
			}else{
				statsData.put(itemName, cnt+1);
			}
		}
	}
	
	boolean haveHeader=false;
	private void writeHeader(BufferedWriter excelWriter) throws Exception{
		excelWriter.write("CreationDate,ReviewerId,AssignedTo,reviewId,File,Type,Severity,Summary,Description,Resolution,Annotation,Status");
		haveHeader=true;
	}
	
	Map<String,Integer> reviewerIdStats=new HashMap<String,Integer>();
	Map<String,Integer> reviewIdStats=new HashMap<String,Integer>();
	Map<String,Integer> assignedToStats=new HashMap<String,Integer>();
	Map<String,Integer> typeStats=new HashMap<String,Integer>();
	Map<String,Integer> severityStats=new HashMap<String,Integer>();
	Map<String,Integer> resolutionStats=new HashMap<String,Integer>();
	Map<String,Integer> statusStats=new HashMap<String,Integer>();
	
	private void writeStatisticsItems(BufferedWriter excelWriter) throws Exception{
		excelWriter.newLine();
		
		writeStatisticsItem(excelWriter,reviewerIdStats,"ReviewerId statistics");
		writeStatisticsItem(excelWriter,reviewIdStats,"ReviewId statistics");
		writeStatisticsItem(excelWriter,assignedToStats,"AssignedTo statistics");
		
		writeStatisticsItem(excelWriter,typeStats,"Type statistics");
		writeStatisticsItem(excelWriter,severityStats,"Severity statistics");
		writeStatisticsItem(excelWriter,resolutionStats,"Resolution statistics");
		writeStatisticsItem(excelWriter,statusStats,"Status statistics");
	}
	
	private void writeStatisticsItem(BufferedWriter excelWriter,Map<String,Integer> data,String itemName) throws Exception{
		if(data.size()>0){
			excelWriter.newLine();
			excelWriter.write(itemName);
			excelWriter.newLine();
			for(Map.Entry<String,Integer> entry:data.entrySet()){
				excelWriter.write(entry.getKey()+","+entry.getValue());
				excelWriter.newLine();
			}
		}
	}

	private String getLastDotField(String msg){
		if(msg==null){
			return null;
		}
		
		int pos=msg.lastIndexOf(".");
		if(pos==-1){
			return null;
		}
		
		return msg.substring(pos+1);
	}
	
	private String replaceCommaAndLine(String originalString){
		if(originalString==null){
			return null;
		}
		
		String newLine=originalString.replace(",", ";");
		String newLine2=newLine.replace("\r\n", "##");
		String newLine3=newLine2.replace("\r", "##");
		String newLine4=newLine3.replace("\n", "##");
		return newLine4;
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}
	
}
