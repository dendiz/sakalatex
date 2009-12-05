package com.swe573.sakalatex;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.swe573.sakalatex";

	// The shared instance
	private static Activator plugin;
	public static String projectName = "sakalatexdefault";
	private Object n;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	public static IProject getCurrentProject() {
		return null;
	}
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

	
}
