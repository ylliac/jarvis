package edwin.plugins.weather.service;

import org.bbelovic.weather.WeatherModel;
import org.bbelovic.weather.YahooWeatherReader;

import edwin.app.config.Configuration;
import edwin.core.service.Service;

public class WeatherService implements Service {

	// Configuration
	private static final String INI_SECTION = "Weather";
	private static final String INI_PARAM_LOCATION = "location";

	public void enable() {
	}

	public void disable() {
	}

	public void ready() {
	}

	public WeatherModel getWeather() {
		String location = Configuration.getInstance().getParam(INI_SECTION, INI_PARAM_LOCATION);
		YahooWeatherReader yahoo = new YahooWeatherReader(location, "C");
		yahoo.process();
		return yahoo.getWeatherModel();
	}

}
