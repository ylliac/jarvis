package edwin.plugins.weather.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bbelovic.weather.WeatherModel;

import edwin.app.Edwin;
import edwin.core.action.Action;
import edwin.core.variable.InternVariables;
import edwin.core.variable.VariableRepository;
import edwin.plugins.weather.service.WeatherService;

public class GetWeatherAction implements Action {

	public GetWeatherAction() {
		this(0);
	}

	public GetWeatherAction(int days) {
		dayCount = days;

		// Init condition translations
		// http://developer.yahoo.com/weather/#codes

		// 0 tornado
		conditionTranslation.put("tornado", "Tornade");
		// 1 tropical storm
		conditionTranslation.put("tropical storm", "Orage tropical");
		// 2 hurricane
		conditionTranslation.put("hurricane", "Ouragan");
		// 3 severe thunderstorms
		conditionTranslation.put("severe thunderstorms", "Violents orages");
		// 4 thunderstorms
		conditionTranslation.put("thunderstorms", "orages");
		// 5 mixed rain and snow
		conditionTranslation.put("mixed rain and snow", "Pluie et neige");
		// 6 mixed rain and sleet
		conditionTranslation.put("mixed rain and sleet", "Pluie et grêle");
		// 7 mixed snow and sleet
		conditionTranslation.put("mixed snow and sleet", "Neige et grêle");
		// 8 freezing drizzle
		conditionTranslation.put("freezing drizzle", "Brouillard verglaçant");
		// 9 drizzle
		conditionTranslation.put("drizzle", "Brumeux");
		// 10 freezing rain
		conditionTranslation.put("freezing rain", "Pluie verglaçante");
		// 11 showers
		// 12 showers
		conditionTranslation.put("showers", "Averses");
		// 13 snow flurries
		conditionTranslation.put("snow flurries", "Neige");
		// 14 light snow showers
		conditionTranslation.put("light snow showers", "Neige");
		// 15 blowing snow
		conditionTranslation.put("blowing snow", "Neige");
		// 16 snow
		conditionTranslation.put("snow", "Neige");
		// 17 hail
		conditionTranslation.put("hail", "Grêle");
		// 18 sleet
		conditionTranslation.put("sleet", "Grêle");
		// 19 dust
		conditionTranslation.put("dust", "Goussière");
		// 20 foggy
		conditionTranslation.put("foggy", "Brouillard");
		// 21 haze
		conditionTranslation.put("haze", "Brumeux");
		// 22 smoky
		conditionTranslation.put("smoky", "Brumeux");
		// 23 blustery
		conditionTranslation.put("blustery", "Venteux");
		// 24 windy
		conditionTranslation.put("windy", "Venteux");
		// 25 cold
		conditionTranslation.put("cold", "Froid");
		// 26 cloudy
		conditionTranslation.put("cloudy", "Nuageux");
		// 27 mostly cloudy (night)
		// 28 mostly cloudy (day)
		conditionTranslation.put("mostly cloudy", "Nuageux");
		// 29 partly cloudy (night)
		// 30 partly cloudy (day)
		// 44 partly cloudy
		conditionTranslation.put("partly cloudy", "Nuageux");
		// 31 clear (night)
		conditionTranslation.put("clear", "Dégagé");
		// 32 sunny
		conditionTranslation.put("sunny", "Ensoleillé");
		// 33 fair (night)
		// 34 fair (day)
		conditionTranslation.put("fair", "Beau temps");
		// 35 mixed rain and hail
		conditionTranslation.put("mixed rain and hail", "Pluie et grêle");
		// 36 hot
		conditionTranslation.put("hot", "Chaud");
		// 37 isolated thunderstorms
		conditionTranslation.put("isolated thunderstorms", "Orages isolés");
		// 38 scattered thunderstorms
		// 39 scattered thunderstorms
		conditionTranslation.put("scattered thunderstorms", "Orages épars");
		// 40 scattered showers
		conditionTranslation.put("scattered showers", "Averses éparses");
		// 41 heavy snow
		// 43 heavy snow
		conditionTranslation.put("heavy snow", "Grosses chutes de neige");
		// 42 scattered snow showers
		conditionTranslation.put("scattered snow showers", "Neige éparse");
		// 45 thundershowers
		conditionTranslation.put("thundershowers", "Orages");
		// 46 snow showers
		conditionTranslation.put("snow showers", "Neige");
		// 47 isolated thundershowers
		conditionTranslation.put("isolated thundershowers", "Orages isolés");
		// + Light Rain Early
		conditionTranslation.put("light rain early", "Pluie dans la matinée");
		// + PM Showers
		conditionTranslation.put("pm showers", "Nuages épars");
	}

	public void execute(VariableRepository localVars, Edwin edwin) {

		String output = "";

		WeatherService service = edwin.getService(WeatherService.class);

		// NOW
		WeatherModel weather = service.getWeather();
		output += "Météo pour aujourd'hui.\n";
		output += parseWeatherModel(weather);

		List<WeatherModel> forecast = weather.getForecast();
		int forecastSize = Math.min(dayCount, forecast.size());

		// TODAY
		if (forecastSize > 0) {
			weather = forecast.get(0);
			output += "Prévisions pour aujourd'hui.\n";
			output += parseWeatherModel(weather);
		}

		// TOMORROW
		if (forecastSize > 1) {
			weather = forecast.get(1);
			output += "Prévisions pour demain.\n";
			output += parseWeatherModel(weather);
		}

		for (int i = 2; i < forecastSize; i++) {
			weather = forecast.get(i);
			output += "Prévisions pour dans " + i + " jours.\n";
			output += parseWeatherModel(weather);
		}

		InternVariables.setOutput(localVars, output);
	}

	private String parseWeatherModel(WeatherModel model) {
		String result = "";

		result += conditionTranslation.get(model.getCondition().toLowerCase());
		result += ".\n";

		result += "Température : ";
		result += model.getTemperature();
		result += "degrés.\n";

		return result;
	}

	private int dayCount = 0;

	private Map<String, String> conditionTranslation = new HashMap<String, String>();

}
