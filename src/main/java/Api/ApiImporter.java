package Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.UserResponse;
import mapper.UserMapper;
import models.Kunde;
import retrofit2.Call;
import retrofit2.Response;

/**
 * ApiImporter handles customer import from the external API and converts
 * API DTO (Data Transfer Object) objects into the domain Kunde model.
 *
 * The mapping logic centralizes conversion from UserResponse, AddressResponse,
 * and CompanyResponse into the domain classes used by the application.
 */
public class ApiImporter {

    /**
     * Fetch users from external API and convert them to Kunde objects
     * 
     * @return List of Kunde objects fetched from the API
     */
    public static List<Kunde> importKundenFromApi() {
        // Prepare an empty list to collect mapped customer objects.
        List<Kunde> kunden = new ArrayList<>();

        try {
            // Use the shared Retrofit instance from RetrofitClient.
            // This avoids building Retrofit repeatedly and keeps the client reusable.
            JsonPlaceholderApi apiService = RetrofitClient.getInstance().create(JsonPlaceholderApi.class);

            // Execute the network request synchronously.
            Call<List<UserResponse>> call = apiService.getUsers();
            Response<List<UserResponse>> response = call.execute();

            // Ensure the HTTP response was successful and the body is present.
            if (!response.isSuccessful() || response.body() == null) {
                // The API may return a success code with an empty body, so check both.
                String errorMessage = response.isSuccessful()
                        ? "Response body is null"
                        : "HTTP " + response.code() + " - " + response.message();
                System.err.println("❌ API Error: " + errorMessage);
                return kunden;
            }

            List<UserResponse> users = response.body();

            System.out.println("✅ Successfully fetched " + users.size() + " users from API");

            // Map each API DTO to the domain Kunde model.
            // Using a dedicated mapper class keeps the conversion logic centralized.
            for (UserResponse user : users) {
                kunden.add(UserMapper.mapToKunde(user));
            }

            System.out.println("✅ Successfully imported " + kunden.size() + " customers from API");

        } catch (IOException e) {
            System.err.println("❌ Error importing customers: " + e.getMessage());
            e.printStackTrace();
        }

        return kunden;
    }

    /**
     * Parse JSON string and convert to list of Kunde objects
     * Useful for testing and offline processing
     * 
     * @param jsonString JSON string containing users array
     * @return List of Kunde objects
     */
    public static List<Kunde> parseKundenFromJson(String jsonString) {
        List<Kunde> kunden = new ArrayList<>();

        try {
            // Parse raw JSON into the API DTO list.
            ObjectMapper objectMapper = new ObjectMapper();
            List<UserResponse> users = objectMapper.readValue(jsonString,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, UserResponse.class));

            // Convert parsed DTOs to the domain Kunde model.
            for (UserResponse user : users) {
                kunden.add(UserMapper.mapToKunde(user));
            }

        } catch (IOException e) {
            System.err.println("❌ Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return kunden;
    }

}
