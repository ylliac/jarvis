package edwin.app;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import edwin.core.rule.Rule;
import edwin.core.rule.RuleRepository;
import edwin.core.service.Service;
import edwin.core.service.ServiceRepository;
import edwin.core.variable.VariableRepository;
import edwin.plugins.rivescript.service.RiveScriptService;
import edwin.plugins.speech.service.SpeechRecognitionService;
import edwin.plugins.speech.service.SpeechService;

public class Edwin {

	private static final Logger LOGGER = Logger.getLogger(Edwin.class);

	/**
	 * Version.
	 */
	private static final String VERSION = "0.1";

	public Edwin() {
		// Add default services
		addDefaultServices();
	}

	public void addListener(EdwinListener listener) {
		listeners.add(listener);
	}

	public void removeListener(EdwinListener listener) {
		listeners.add(listener);
	}

	protected void fireEdwinReady() {
		for (EdwinListener listener : listeners) {
			listener.edwinReady(this);
		}
	}

	public void addRule(Rule rule) {
		rulesRepo.add(rule);
	}

	public void start() {

		LOGGER.info("Edwin " + VERSION);

		LOGGER.info("");

		// Enable services
		LOGGER.info("Enable services...");
		enableServices();
		LOGGER.info("Enable services...done");

		LOGGER.info("");

		// Enable rules
		LOGGER.info("Enable rules...");
		enableRules();
		LOGGER.info("Enable rules...done");

		LOGGER.info("");

		// Get ready services
		LOGGER.info("Get services ready...");
		readyServices();
		LOGGER.info("Get services ready...done");

		LOGGER.info("");

		LOGGER.info("Ready");
		fireEdwinReady();

		// Wait
		synchronized (waitObject) {
			try {
				waitObject.wait();
			} catch (InterruptedException e) {
				LOGGER.error(e, e);
			}
		}
	}

	public void stop() {
		synchronized (waitObject) {
			waitObject.notifyAll();
		}

		System.exit(0);
	}

	public VariableRepository getGlobalVariables() {
		return variableRepo;
	}

	public <TYPE extends Service> TYPE getService(Class<TYPE> clazz) {
		return (TYPE) serviceRepo.findFromKey(clazz);
	}

	public void addService(Service service) {
		serviceRepo.add(service);
	}

	private void addDefaultServices() {

		// Speech service
		addService(new SpeechService());

		// Speech recognition service
		addService(new SpeechRecognitionService());

		// Rivescript
		addService(new RiveScriptService());

	}

	private void enableServices() {
		Collection<Service> services = serviceRepo.findAll();
		for (Service service : services) {
			LOGGER.info(" - service " + service.getClass().getSimpleName());
			service.enable();
		}
	}

	private void readyServices() {
		Collection<Service> services = serviceRepo.findAll();
		for (Service service : services) {
			LOGGER.info(" - service " + service.getClass().getSimpleName());
			service.ready();
		}
	}

	private void enableRules() {
		Collection<Rule> rules = rulesRepo.findAll();
		for (Rule rule : rules) {
			LOGGER.info(" - rule " + rule.getName());
			rule.enable(this);
		}
	}

	/**
	 * Services.
	 */
	private ServiceRepository serviceRepo = new ServiceRepository();

	/**
	 * Global variables.
	 */
	private VariableRepository variableRepo = new VariableRepository();

	/**
	 * Rules.
	 */
	private RuleRepository rulesRepo = new RuleRepository();

	/**
	 * Listeners.
	 */
	private Set<EdwinListener> listeners = new HashSet<EdwinListener>();

	private Object waitObject = new Object();

}
