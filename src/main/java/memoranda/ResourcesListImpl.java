/**
 * ResourcesListImpl.java
 * Created on 24.03.2003, 18:30:31 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.Vector;
import main.java.memoranda.interfaces.IProject;
import main.java.memoranda.interfaces.IResourcesList;
import main.java.memoranda.util.Util;

import java.io.File;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

/**
 *
 */
/*$Id: ResourcesListImpl.java,v 1.5 2007/03/20 06:21:46 alexeya Exp $*/
public class ResourcesListImpl implements IResourcesList {
    
	private IProject _project = null;
    private Document _doc = null;
    private Element _root = null;

    /**
     * Constructor for TaskListImpl.
     */
    public ResourcesListImpl(Document doc, IProject prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
    }

    public ResourcesListImpl(IProject prj) {
            _root = new Element("resources-list");
            _doc = new Document(_root);
            _project = prj;
    }

    public Vector getAllResources() {
        Vector v = new Vector();
        Elements rs = _root.getChildElements("resource");
        for (int i = 0; i < rs.size(); i++)
            v.add(new Resource(rs.get(i).getAttribute("path").getValue(), rs.get(i).getAttribute("isInetShortcut") != null, rs.get(i).getAttribute("isProjectFile") != null));
        return v;
    }

    /**
     * @see main.java.memoranda.interfaces.IResourcesList#getResource(java.lang.String)
     */
    public Resource getResource(String path) {
        Elements rs = _root.getChildElements("resource");
        for (int i = 0; i < rs.size(); i++)
            if (rs.get(i).getAttribute("path").getValue().equals(path))
                return new Resource(rs.get(i).getAttribute("path").getValue(), rs.get(i).getAttribute("isInetShortcut") != null, rs.get(i).getAttribute("isProjectFile") != null);
        return null;
    }

    /**
     * @see net.sf.memoranda.ResourcesList#addResource(java.lang.String, java.lang.String)
     */
    /*public void addResource(String path, String taskId) {
        Element el = new Element("resource");
        el.addAttribute(new Attribute("id", Util.generateId()));
        el.addAttribute(new Attribute("path", path));
        if (taskId != null) el.addAttribute(new Attribute("taskId", taskId));
        _root.appendChild(el);
    }*/
    
    /**
     * @see main.java.memoranda.interfaces.IResourcesList#addResource(java.lang.String, boolean)
     */
    public void addResource(String path, boolean isInternetShortcut, boolean isProjectFile) {
        Element el = new Element("resource");
        el.addAttribute(new Attribute("id", Util.generateId()));
        el.addAttribute(new Attribute("path", path));  
        if (isInternetShortcut)
            el.addAttribute(new Attribute("isInetShortcut", "true"));
        if (isProjectFile)
            el.addAttribute(new Attribute("isProjectFile", "true"));
        _root.appendChild(el);
    }

    public void addResource(String path) {
        addResource(path, false, false);
    }

    /**
     * @see main.java.memoranda.interfaces.IResourcesList#removeResource(java.lang.String)
     */
    
    // Task 1 reducing complexity
    public void removeResource(String path) {
        Elements rs = _root.getChildElements("resource");
        for (int i = 0; i < rs.size(); i++)
            if (rs.get(i).getAttribute("path").getValue().equals(path) && getResource(path).isProjectFile()) {
            	//if(getResource(path).isProjectFile()) {
            		File f = new File(path);
            		System.out.println("[DEBUG] Removing file "+path);
                	f.delete();
            	}
            else {
            	_root.removeChild(rs.get(i));
            }
    }
        

    /**
     * @see main.java.memoranda.interfaces.IResourcesList#getAllResourcesCount()
     */
    public int getAllResourcesCount() {
        return _root.getChildElements("resource").size();
    }
    /**
     * @see main.java.memoranda.interfaces.IResourcesList#getXMLContent()
     */
    public Document getXMLContent() {
        return _doc;
    }
    
    /**
     * @see net.sf.memoranda.ResourcesList#getResourcesForTask(java.lang.String)
     */
    /*public Vector getResourcesForTask(String taskId) {
        Vector v = new Vector();
        Elements rs = _root.getChildElements("resource");
        for (int i = 0; i < rs.size(); i++)
            if (rs.get(i).getAttribute("taskId").getValue().equals(taskId))
                v.add(rs.get(i).getAttribute("path").getValue());
        return v;
    }*/
   

}
