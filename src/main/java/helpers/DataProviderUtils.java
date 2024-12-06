package helpers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
public class DataProviderUtils {

	private static final String UserData = "testData/userData";
	private static final String urlData = "testData/urlData";
	private static final String technologiesData = "testData/technologies";
	private static final String locationData = "testData/location";

	/**
	 * Provides a single value from a simple key-value JSON file.
	 *
	 * @param fileName The name of the JSON file (without the .json extension).
	 * @param key      The key for which the value needs to be retrieved (e.g.,
	 *                 "linkedin").
	 * @return The value associated with the provided key.
	 */
	public static String provideSimpleData(String fileName, String key) {
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader(fileName + ".json")) {
			Object obj = parser.parse(reader);
			JSONObject jsonObject = (JSONObject) obj;

			return (String) jsonObject.get(key);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Provides user data by the username from the JSON file.
	 *
	 * @param fileName The name of the JSON file (without the .json extension).
	 * @param username The name of the user (e.g., "Arun", "John", "Alice").
	 * @return A Map containing the user's data (email, password).
	 */
	public static Map<String, String> provideUserData(String fileName, String websiteName, String username) {
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader(fileName + ".json")) {
			Object obj = parser.parse(reader);
			JSONObject jsonObject = (JSONObject) obj;

			JSONObject users = (JSONObject) jsonObject.get(websiteName);
			JSONObject userData = (JSONObject) users.get(username);

			return (Map<String, String>) userData;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieves the list of data from the specified category in the JSON file.
	 *
	 * @param category The category name.
	 * @return A list of technologies under the specified category.
	 */
	public static List<String> provideListData(String fileName, String category) {
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader(fileName + ".json")) {
			Object obj = parser.parse(reader);
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray technologiesArray = (JSONArray) jsonObject.get(category);
			return technologiesArray != null ? new ArrayList<>(technologiesArray) : null;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getUrlData(String key) {
		String url = provideSimpleData(urlData, key);
		return url;
	}

	public static String getLinkedInUserData(String data, String username) {
		Map<String, String> testData = provideUserData(UserData,"linkedin", username);
		return (String) testData.get(data);
	}
	
	public static String getHubstaffTalentUserData(String data, String username) {
		Map<String, String> testData = provideUserData(UserData,"hubstafftalent", username);
		return (String) testData.get(data);
	}
	
	public static String getTejofiUserData(String data, String username) {
		Map<String, String> testData = provideUserData(UserData,"tejofi", username);
		return (String) testData.get(data);
	}

	/**
	 * Retrieves the keys (categories) from the JSON file in the order they appear.
	 *
	 * This method uses the Jackson library to parse the JSON file, ensuring that
	 * the order of keys is preserved as they are defined in the file. The keys
	 * represent categories that are stored in a list and returned.
	 *
	 * @param fileName The name of the JSON file (without the .json extension).
	 * @return A list of keys (categories) from the JSON file in the order they
	 *         appear.
	 */
	public static List<String> getObject(String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		List<String> keys = new ArrayList<>();
		try {
			// Read the JSON file into a JsonNode
			JsonNode rootNode = mapper.readTree(new FileReader(fileName + ".json"));

			// Iterate over the field names (keys) in the root node
			Iterator<String> fieldNames = rootNode.fieldNames();
			while (fieldNames.hasNext()) {
				keys.add(fieldNames.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keys;
	}

	/**
	 *
	 * @param category availabe : "Frontend", "Backend", "AI_ML", "Mobile_App",
	 *                 "Design_Tools".
	 * @return A list of technologies under the specified category.
	 */
	public static List<String> getTechnologiesData(String category) {
		List<String> provideListData = provideListData(technologiesData, category);
		return provideListData;
	}

	public static List<String> getCategories() {
		List<String> provideListData = getObject(technologiesData);
		return provideListData;
	}

	public static List<String> getLocationData(String location) {
		List<String> provideListData = provideListData(locationData, location);
		return provideListData;
	}

	public static List<String> getTechnologyData(String technology) {
		List<String> provideListData = provideListData(technologiesData, technology);
		return provideListData;
	}
	
	public static List<String> getTechnologyData(String technology, String category) {
	    String fileName = "testData/" + technology + "Technologies";
	    List<String> provideListData = provideListData(fileName, category);
	    return provideListData;
	}
}
