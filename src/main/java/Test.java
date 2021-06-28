import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;


//tutorial: https://www.visualcrossing.com/resources/documentation/weather-api/how-to-fetch-weather-forecast-data-from-a-restful-web-service-in-java/
public class Test {
	public static void main(String[] args) throws URISyntaxException, IOException {
		//CloseableHttpResponse response = getCloseableHttpResponseVisualCrossing();
		CloseableHttpResponse response = getCloseableHttpResponseOpenWeather();


		try {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.printf("Bad response status code:%d%n", response.getStatusLine().getStatusCode());
				return;
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String rawResult= EntityUtils.toString(entity, Charset.forName("utf-8"));

				JSONObject jsonWeatherForecast = new JSONObject(rawResult);
				System.out.println(jsonWeatherForecast.toString(4) );
				//System.out.println(rawResult);
			}

		} finally {
			response.close();
		}


	}

	private static CloseableHttpResponse getCloseableHttpResponseVisualCrossing() throws URISyntaxException, IOException {
		URIBuilder builder = new URIBuilder("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/forecast");

		builder.setParameter("aggregateHours", "24")
				.setParameter("contentType", "csv")
				.setParameter("unitGroup", "metric")
				.setParameter("key", "RNHEMXJBSKWV8LTLZNUUKGTRV")
				.setParameter("locations", "Belém,PT");
		HttpGet get = new HttpGet(builder.build());
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(get);
		return response;
	}

	private static CloseableHttpResponse getCloseableHttpResponseOpenWeather() throws URISyntaxException, IOException {
		//URIBuilder builder = new URIBuilder("https://api.openweathermap.org");
		URIBuilder builder = new URIBuilder("https://api.openweathermap.org/data/2.5/onecall");

		builder.setParameter("appid", "3bd4bc1d0953ac6f516800c167b02ecf")
				.setParameter("lat", "38.704620")
				.setParameter("lon", "-9.202358")
				.setParameter("units", "metric")
				.setParameter("lang", "pt");
		HttpGet get = new HttpGet(builder.build());
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(get);
		return response;
	}

}
