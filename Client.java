

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;


public class Client {
    public static void main(String[] args) throws Exception {
            //SendMongoDoc();   // Http PUT operation method
            //GetMongoDoc();    // Http GET operation method

        }


    static  public void SendMongoDoc()
            throws  IOException, AuthenticationException, JSONException {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPut httpPut = new HttpPut("http://localhost:8080/rh");

        httpPut.setEntity(new StringEntity("test post"));
                UsernamePasswordCredentials cred
                        = new UsernamePasswordCredentials("admin", "changeit");
        httpPut.addHeader(new BasicScheme().authenticate(cred, httpPut, null));
            JSONObject params = new JSONObject();
            params.put("name", "Test2");
            params.put("desc", "working2");
        httpPut.setEntity(new StringEntity(params.toString(), ContentType.APPLICATION_JSON));


                CloseableHttpResponse response = client.execute(httpPut);
                System.out.print(response.getStatusLine().getStatusCode());
                client.close();
        }

        static public void GetMongoDoc() throws Exception {
            CloseableHttpClient client = HttpClients.createDefault();

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("rh")
                    .addParameter("name","Test2")
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            UsernamePasswordCredentials cred
                    = new UsernamePasswordCredentials("admin", "changeit");
            httpGet.addHeader(new BasicScheme().authenticate(cred, httpGet, null));

            HttpResponse response = client.execute(httpGet);
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(
                            response.getEntity().getContent()));

            String line = "";

            line = rd.readLine();
            JSONObject params = new JSONObject(line);

            System.out.println(params.get("name"));
            System.out.println(params.get("desc"));
            client.close();
        }

}
