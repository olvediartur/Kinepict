package com.weather.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.weather.model.Location;

public class LocationService {

	private static Logger logger = LoggerFactory.getLogger(LocationService.class);
	private Location location = new Location();

	public LocationService() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.debug("Start getting location data.");
			location = mapper.readValue(getResponse(), Location.class);
		} catch (JsonParseException e) {
			logger.error("Unable to parse json!" + e);
		} catch (JsonMappingException e) {
			logger.error("Unexpected error in json mapping!" + e);
		} catch (IOException e) {
			logger.error("Unable to find file!" + e);
		} finally {
			logger.info("Finished getting location data.");
		}
	}

	public Location getLocation() {
		return location;
	}

	private String getLink() {
		String link = "";
		logger.debug("Start getting link from properties file.");
		try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			logger.info("Loading properties file.");
			link = prop.getProperty("link") + prop.getProperty("city") + "&appid=" + prop.getProperty("key")
					+ "&units=metric";
		} catch (IOException ex) {
			logger.error("Unable to find file!" + ex);
		} finally {
			logger.info("Finished getting link from properties file.");
		}
		return link;
	}

	public String getResponse() {
		String responseBody = "";
		logger.debug("Start getting response.");
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			HttpGet httpget = new HttpGet(getLink());
			logger.info("Executing request " + httpget.getRequestLine());

			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					logger.error("Unexpected response status: " + status);
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (IOException e) {
			logger.error("Unable to find file!" + e);
		} finally {
			logger.info("Finished getting response.");
		}
		return responseBody;
	}

}
