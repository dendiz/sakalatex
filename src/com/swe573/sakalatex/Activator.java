package com.swe573.sakalatex;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	static IWorkbenchWindow currentWindow = null;
	// The plug-in ID
	public static final String PLUGIN_ID = "com.swe573.sakalatex";

	// The shared instance
	private static Activator plugin;
	public static String projectName = "sakalatexdefault";
	public static String templateName = "";
	private Object n;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	public static void openEditor(String filename) {
	    IWorkbenchPage page = Activator.getDefault().getWorkbench().getWorkbenchWindows()[0].getActivePage();//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	    IFile ifile =  Activator.getCurrentProject().getFile(filename);
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(filename);
		try {
			page.openEditor(new FileEditorInput(ifile), desc.getId());
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static IProject getCurrentProject() {
        IResource res = SelectedResourceManager.getDefault().getSelectedResource();
        return res == null ? null : res.getProject();	}
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
    public static String readStream(InputStream in) throws IOException {
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        
        int len = 0;
        while ((len = in.read(buff)) == buff.length) {
            out.write(buff);
        }
        
        if (len > 0) {
            out.write(buff, 0, len);
        }
        
        in.close();
        
        return out.toString();
    }

    public static void writeFile(String fName, String str) throws Exception {
    	FileWriter fw = null;
    	try{
    	fw = new FileWriter(fName);
		fw.write(str);
		}
    	finally
    	{
    	 if(fw != null)
    		 fw.close();
    	}
    }

    public static IWorkbenchWindow getCurrentView() {
    
    IWorkbench workbench = Activator.getDefault().getWorkbench();
    
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    if (window == null) {
        Display display = workbench.getDisplay();
        display.syncExec(new Runnable() {
            public void run() {
                currentWindow = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow();
            }});
        window = currentWindow;
    }
    return window;
    }

    

	
}
