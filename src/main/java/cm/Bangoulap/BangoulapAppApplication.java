package cm.Bangoulap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableJpaAuditing
public class BangoulapAppApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BangoulapAppApplication.class, args);

		// System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL");
		String urlParameters = "grant_type=client_credentials";

		byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int postDataLength = postData.length;
		String request = "https://api.orange.com/oauth/v3/token";
		URL url = new URL( request );

		HttpURLConnection conn= (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Basic SlY2QXZoNXdlUllMZkYxZUkzUld1d0ZlTWphNUdHQkI6N04yckdzcmE0a3EyY3lkNA==");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Accept", "application/json");
		// conn.setRequestProperty("charset", "utf-8");
		// conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
		conn.setUseCaches(false);
		try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write( postData );
			System.out.println(postData.toString());
		}

	}

}
