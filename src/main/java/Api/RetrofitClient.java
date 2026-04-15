package Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class RetrofitClient {
    // The Retrofit instance is shared across the application.
    // This avoids recreating Retrofit on every API call and keeps the client
    // reusable.
    private static volatile Retrofit instance;

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final int TIMEOUT_SECONDS = 15;

    private RetrofitClient() {
        // Prevent instantiation
    }

    /**
     * Returns the singleton Retrofit instance.
     *
     * We use Double-Checked Locking so that:
     * 1) The instance is created lazily on first use.
     * 2) The synchronization block is entered only once during initialization.
     * 3) Subsequent calls are fast because they skip the synchronized block.
     *
     * The volatile modifier ensures that once the instance is constructed,
     * all threads see the fully initialized Retrofit object without caching issues.
     */
    public static Retrofit getInstance() {
        // First check: avoid synchronization if instance is already initialized.
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                // Second check: ensure another thread did not initialize instance while
                // waiting.
                if (instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            // Attach a configured OkHttp client for timeouts and logging.
                            .client(createOkHttpClient())
                            .addConverterFactory(JacksonConverterFactory.create())
                            .build();
                }
            }
        }
        return instance;
    }

    private static OkHttpClient createOkHttpClient() {
        // HTTP logging for requests/responses. Useful for debugging API calls.
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                // Timeouts prevent hanging if the server is too slow or unreachable.
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
