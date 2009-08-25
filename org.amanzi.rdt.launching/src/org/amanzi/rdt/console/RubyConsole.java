package org.amanzi.rdt.console;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import org.amanzi.rdt.internal.launching.AweLaunchingPlugin;
import org.amanzi.rdt.internal.launching.AweLaunchingPluginMessages;
import org.amanzi.scripting.jruby.EclipseLoadService;
import org.amanzi.scripting.jruby.ScriptUtils;
import org.amanzi.splash.neo4j.utilities.NeoSplashUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.debug.ui.console.IConsoleHyperlink;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.runtime.load.LoadService;
import org.rubypeople.rdt.internal.launching.StandardVMRunner;
import org.rubypeople.rdt.launching.IRubyLaunchConfigurationConstants;
import org.rubypeople.rdt.launching.IVMInstall;


/**
 * Console for input/output of Ruby Script Launch
 * 
 * @author Lagutko_N
 *
 */

public class RubyConsole extends IOConsole implements IConsole {
	
    /*
     * Path to Start Script
     */
	private static final String[] START_SCRIPTS = {"ruby/gisGlobals.rb", "ruby/startScript.rb"};	

    /*
	 * Name of Process
	 */
	private static final String PROCESS_NAME = "AWE";
	
	/*
	 * Monitor
	 */
	IProgressMonitor monitor;
	
	/*
	 * Launced configuration
	 */
	ILaunchConfiguration configuration;
	
	/*
	 * Launch preferences
	 */
	ILaunch launch;
	
	/*
	 * Launched project
	 */
	IProject project;
	
	/*
	 * Console output stream
	 */
	PrintStream outputStream = null;
	
	/*
	 * Ruby runtime
	 */
	Ruby runtime;
	
	/**
	 * Public constructor that creates console 
	 * 
	 * @param configuration launch configuration
	 * @param launch launch preferences
	 * @param monitor monitor
	 * @throws CoreException 
	 */

	public RubyConsole(ILaunchConfiguration configuration, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		this(configuration.getName(), configuration.getType().getName());
		
		this.monitor = monitor;
		this.configuration = configuration;
		this.launch = launch;
		
		if (ConsoleRuntime.getConsoleLineTrackers(getType()).length > 0) {
			addPatternMatchListener(new AweConsoleLineNotfier());
		}
		
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new RubyConsole[] {this});
	}
	
	/**
	 * Internal constructor that creates Label of Console by launchName and configrurationType
	 * 
	 * @param launchName name of launch
	 * @param configurationType name of launch configuration type
	 */
	
	protected RubyConsole(String launchName, String configurationType) {
		this(launchName + " [" + configurationType + "] " + StandardVMRunner.renderProcessLabel(new String[] {PROCESS_NAME}));
	}
	
	/**
	 * Internal constructor that creates Console with label
	 * 
	 * @param label label of Console
	 */
	
	protected RubyConsole(String label) {
		
		super(label, IRubyLaunchConfigurationConstants.ID_RUBY_PROCESS_TYPE, null, true);
	}
	
	/**
	 * Initializes RubyConsole
	 * 
	 * @param jRubyInstall Ruby VM
	 * @throws CoreException
	 */

	public void init(IVMInstall jRubyInstall) throws CoreException {		
		activate();
		
		initializeJRubyInterpreter(createRubyConfig(jRubyInstall));
		
		if (!runInitScript()) {
			//TODO: throw exception
		}
	}
	
	/**
	 * Initialized Ruby Runtime
	 * 
	 * @param rubyConfiguration configuration of RubyRuntime
	 */
	
	protected void initializeJRubyInterpreter(RubyInstanceConfig rubyConfiguration) {
		runtime = Ruby.newInstance(rubyConfiguration);		
		runtime.getLoadService().init(ScriptUtils.makeLoadPath(new String[] {}));
	}
	
	/**
	 * Creates Configuration of Ruby Environment
	 * 
	 * @param configuration launch configuration
	 * @param mode mode of launch
	 * @return created RubyInstanceConfig class
	 * @throws CoreException 
	 */
	
	private RubyInstanceConfig createRubyConfig(final IVMInstall jRubyInstall) throws CoreException {
		outputStream = new PrintStream(newOutputStream());
		
		return new RubyInstanceConfig() {{
			setJRubyHome(jRubyInstall.getInstallLocation().getAbsolutePath());
			setOutput(outputStream);
			setError(outputStream);			
			setInput(getInputStream());
            setLoadServiceCreator(new LoadServiceCreator() {
                public LoadService create(Ruby runtime) {
                    return new EclipseLoadService(runtime);
                }
            });
            //Lagutko, 29.08.2009, put a classloader of this class
            setLoader(this.getClass().getClassLoader());
		}};
	}
	
	private boolean runInitScript() {
	    for (String startScript : START_SCRIPTS) {
	        try {
	            URL scriptUrl = FileLocator.toFileURL(AweLaunchingPlugin.getDefault().getBundle().getEntry(startScript));
		
	            String script = NeoSplashUtil.getScriptContent(scriptUrl.getPath());
		
	            runtime.evalScriptlet(script);
	        }
	        catch (IOException e) {
	            AweLaunchingPlugin.log(null, e);
	            return false;
	        }
	    }
		return true;
	}
	
	/**
	 * Runs script by path
	 * 
	 * @param filePath path to script
	 */
	
	public void run(String filePath) {
		try {
			runtime.runFromMain(new FileInputStream(filePath), filePath);
		}
		catch (Exception e) {			
			//pring stack trace of any exception to output stream
			e.printStackTrace(outputStream);
		}
		finally {
			runtime.tearDown();
		}
		
		setConsoleLabelTerminated();
	}
	
	/**
	 * Add prefix '<terminated>' to Console's label
	 * 
	 */
	
	private void setConsoleLabelTerminated() {
	    final String newName = AweLaunchingPluginMessages.getFormattedString(AweLaunchingPluginMessages.Console_Terminated, getName());
		
		
		Runnable r = new Runnable() {
			public void run() {
				setName(newName);
				ConsolePlugin.getDefault().getConsoleManager().warnOfContentChange(RubyConsole.this);
			}
		};
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(r);
	}
	
	public void addLink(IConsoleHyperlink arg0, int arg1, int arg2) {
		try {
			addHyperlink(arg0, arg1, arg2);
		}
		catch (BadLocationException e) {
			AweLaunchingPlugin.log(null, e);
		}
	}

	public void addLink(IHyperlink arg0, int arg1, int arg2) {
		try {
			addHyperlink(arg0, arg1, arg2);
		}
		catch (BadLocationException e) {
			//TODO: handle this exception
			e.printStackTrace();
		}
		
	}

	public void connect(IStreamsProxy arg0) {
		//do nothing
		
	}

	public void connect(IStreamMonitor arg0, String arg1) {
		// do nothing
		
	}

	public IProcess getProcess() {		
		return null;
	}
	
	public IProject getProject() {
		return project;
	}
	
	public void setProject(IProject project) {
		this.project = project;
	}
	
	public ILaunch getLaunch() {
		return launch;
	}

	public IRegion getRegion(IConsoleHyperlink arg0) {
		return super.getRegion(arg0);
	}

	public IOConsoleOutputStream getStream(String arg0) { 
		return null;
	}
}
