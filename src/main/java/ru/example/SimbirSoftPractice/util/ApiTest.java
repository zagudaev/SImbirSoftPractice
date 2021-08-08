package ru.example.SimbirSoftPractice.util;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

    //TODO тестовый класс для работы с youtobe api лог ошибок в низу

public class ApiTest {


    private static final String PROPERTIES_FILENAME = "application.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static final JsonFactory JSON_FACTORY = new JacksonFactory();


    private static YouTube youtube;


    public static void main(String[] args) {
        // чтение файла с ключем
        Properties properties = new Properties();
        try {
            InputStream in = ApiTest.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        try {

            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-ApiExample-sample").build();

            // строка запроса
            String queryTerm = getInputQuery();


            YouTube.Search.List search = youtube.search().list("id,snippet");   //указывается какие данные нужни получить  || на youtube.search().list выдает ошибку

            // установка кльча, строки запроса  и других параметров
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(queryTerm);

            search.setType("video");

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            //запрос
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                prettyPrint(searchResultList.iterator(), queryTerm);
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String getInputQuery() throws IOException {

        String inputQuery = "";

        System.out.print("Please enter a search term: ");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        inputQuery = bReader.readLine();

        return inputQuery;
    }

    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

                System.out.println(" Video Id" + rId.getVideoId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");
            }
        }
    }
}
// TODO
//C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2020.2.1\lib\idea_rt.jar=57996:C:\Program Files\JetBrains\IntelliJ IDEA 2020.2.1\bin" -Dfile.encoding=UTF-8 -classpath C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\charsets.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\access-bridge-64.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\cldrdata.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\dnsns.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\jaccess.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\jfxrt.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\localedata.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\nashorn.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\sunec.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\sunjce_provider.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\sunmscapi.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\sunpkcs11.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\ext\zipfs.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\jce.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\jfr.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\jfxswt.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\jsse.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\management-agent.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\resources.jar;C:\Users\kjolenskap\.jdks\corretto-1.8.0_272\jre\lib\rt.jar;C:\Users\kjolenskap\IdeaProjects\SImbirSoftPractice\target\classes;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-data-jpa\2.2.2.RELEASE\spring-boot-starter-data-jpa-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-aop\2.2.2.RELEASE\spring-boot-starter-aop-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\aspectj\aspectjweaver\1.9.5\aspectjweaver-1.9.5.jar;C:\Users\kjolenskap\.m2\repository\jakarta\activation\jakarta.activation-api\1.2.1\jakarta.activation-api-1.2.1.jar;C:\Users\kjolenskap\.m2\repository\jakarta\persistence\jakarta.persistence-api\2.2.3\jakarta.persistence-api-2.2.3.jar;C:\Users\kjolenskap\.m2\repository\jakarta\transaction\jakarta.transaction-api\1.3.3\jakarta.transaction-api-1.3.3.jar;C:\Users\kjolenskap\.m2\repository\org\hibernate\hibernate-core\5.4.9.Final\hibernate-core-5.4.9.Final.jar;C:\Users\kjolenskap\.m2\repository\org\jboss\logging\jboss-logging\3.4.1.Final\jboss-logging-3.4.1.Final.jar;C:\Users\kjolenskap\.m2\repository\org\javassist\javassist\3.24.0-GA\javassist-3.24.0-GA.jar;C:\Users\kjolenskap\.m2\repository\net\bytebuddy\byte-buddy\1.10.4\byte-buddy-1.10.4.jar;C:\Users\kjolenskap\.m2\repository\antlr\antlr\2.7.7\antlr-2.7.7.jar;C:\Users\kjolenskap\.m2\repository\org\jboss\jandex\2.1.1.Final\jandex-2.1.1.Final.jar;C:\Users\kjolenskap\.m2\repository\com\fasterxml\classmate\1.5.1\classmate-1.5.1.jar;C:\Users\kjolenskap\.m2\repository\org\dom4j\dom4j\2.1.1\dom4j-2.1.1.jar;C:\Users\kjolenskap\.m2\repository\org\hibernate\common\hibernate-commons-annotations\5.1.0.Final\hibernate-commons-annotations-5.1.0.Final.jar;C:\Users\kjolenskap\.m2\repository\org\glassfish\jaxb\jaxb-runtime\2.3.2\jaxb-runtime-2.3.2.jar;C:\Users\kjolenskap\.m2\repository\org\glassfish\jaxb\txw2\2.3.2\txw2-2.3.2.jar;C:\Users\kjolenskap\.m2\repository\com\sun\istack\istack-commons-runtime\3.0.8\istack-commons-runtime-3.0.8.jar;C:\Users\kjolenskap\.m2\repository\org\jvnet\staxex\stax-ex\1.8.1\stax-ex-1.8.1.jar;C:\Users\kjolenskap\.m2\repository\com\sun\xml\fastinfoset\FastInfoset\1.2.16\FastInfoset-1.2.16.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\data\spring-data-jpa\2.2.3.RELEASE\spring-data-jpa-2.2.3.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\data\spring-data-commons\2.2.3.RELEASE\spring-data-commons-2.2.3.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-orm\5.2.2.RELEASE\spring-orm-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-context\5.2.2.RELEASE\spring-context-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-tx\5.2.2.RELEASE\spring-tx-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-beans\5.2.2.RELEASE\spring-beans-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\slf4j\slf4j-api\1.7.29\slf4j-api-1.7.29.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-aspects\5.2.2.RELEASE\spring-aspects-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-data-rest\2.2.2.RELEASE\spring-boot-starter-data-rest-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\data\spring-data-rest-webmvc\3.2.3.RELEASE\spring-data-rest-webmvc-3.2.3.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\data\spring-data-rest-core\3.2.3.RELEASE\spring-data-rest-core-3.2.3.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\hateoas\spring-hateoas\1.0.2.RELEASE\spring-hateoas-1.0.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\plugin\spring-plugin-core\2.0.0.RELEASE\spring-plugin-core-2.0.0.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\atteo\evo-inflector\1.2.2\evo-inflector-1.2.2.jar;C:\Users\kjolenskap\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.10.1\jackson-annotations-2.10.1.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-jdbc\2.2.2.RELEASE\spring-boot-starter-jdbc-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter\2.2.2.RELEASE\spring-boot-starter-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-logging\2.2.2.RELEASE\spring-boot-starter-logging-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;C:\Users\kjolenskap\.m2\repository\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;C:\Users\kjolenskap\.m2\repository\org\apache\logging\log4j\log4j-to-slf4j\2.12.1\log4j-to-slf4j-2.12.1.jar;C:\Users\kjolenskap\.m2\repository\org\apache\logging\log4j\log4j-api\2.12.1\log4j-api-2.12.1.jar;C:\Users\kjolenskap\.m2\repository\org\slf4j\jul-to-slf4j\1.7.29\jul-to-slf4j-1.7.29.jar;C:\Users\kjolenskap\.m2\repository\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar;C:\Users\kjolenskap\.m2\repository\org\yaml\snakeyaml\1.25\snakeyaml-1.25.jar;C:\Users\kjolenskap\.m2\repository\com\zaxxer\HikariCP\3.4.1\HikariCP-3.4.1.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-jdbc\5.2.2.RELEASE\spring-jdbc-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-security\2.2.2.RELEASE\spring-boot-starter-security-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-aop\5.2.2.RELEASE\spring-aop-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\security\spring-security-config\5.2.1.RELEASE\spring-security-config-5.2.1.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\security\spring-security-web\5.2.1.RELEASE\spring-security-web-5.2.1.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-expression\5.2.2.RELEASE\spring-expression-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-web\2.2.2.RELEASE\spring-boot-starter-web-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-json\2.2.2.RELEASE\spring-boot-starter-json-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.10.1\jackson-datatype-jdk8-2.10.1.jar;C:\Users\kjolenskap\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.10.1\jackson-datatype-jsr310-2.10.1.jar;C:\Users\kjolenskap\.m2\repository\com\fasterxml\jackson\module\jackson-module-parameter-names\2.10.1\jackson-module-parameter-names-2.10.1.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-tomcat\2.2.2.RELEASE\spring-boot-starter-tomcat-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\apache\tomcat\embed\tomcat-embed-core\9.0.29\tomcat-embed-core-9.0.29.jar;C:\Users\kjolenskap\.m2\repository\org\apache\tomcat\embed\tomcat-embed-el\9.0.29\tomcat-embed-el-9.0.29.jar;C:\Users\kjolenskap\.m2\repository\org\apache\tomcat\embed\tomcat-embed-websocket\9.0.29\tomcat-embed-websocket-9.0.29.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-validation\2.2.2.RELEASE\spring-boot-starter-validation-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\jakarta\validation\jakarta.validation-api\2.0.1\jakarta.validation-api-2.0.1.jar;C:\Users\kjolenskap\.m2\repository\org\hibernate\validator\hibernate-validator\6.0.18.Final\hibernate-validator-6.0.18.Final.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-web\5.2.2.RELEASE\spring-web-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-webmvc\5.2.2.RELEASE\spring-webmvc-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\postgresql\postgresql\42.2.12\postgresql-42.2.12.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-devtools\2.2.2.RELEASE\spring-boot-devtools-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot\2.2.2.RELEASE\spring-boot-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-autoconfigure\2.2.2.RELEASE\spring-boot-autoconfigure-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-configuration-processor\2.2.2.RELEASE\spring-boot-configuration-processor-2.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\io\jsonwebtoken\jjwt\0.9.1\jjwt-0.9.1.jar;C:\Users\kjolenskap\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.10.1\jackson-databind-2.10.1.jar;C:\Users\kjolenskap\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.10.1\jackson-core-2.10.1.jar;C:\Users\kjolenskap\.m2\repository\com\sun\mail\smtp\1.4.4\smtp-1.4.4.jar;C:\Users\kjolenskap\.m2\repository\javax\activation\activation\1.1\activation-1.1.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\boot\spring-boot-starter-mail\2.2.5.RELEASE\spring-boot-starter-mail-2.2.5.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-context-support\5.2.2.RELEASE\spring-context-support-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\com\sun\mail\jakarta.mail\1.6.4\jakarta.mail-1.6.4.jar;C:\Users\kjolenskap\.m2\repository\com\sun\activation\jakarta.activation\1.2.1\jakarta.activation-1.2.1.jar;C:\Users\kjolenskap\.m2\repository\org\springdoc\springdoc-openapi-ui\1.2.32\springdoc-openapi-ui-1.2.32.jar;C:\Users\kjolenskap\.m2\repository\org\springdoc\springdoc-openapi-webmvc-core\1.2.32\springdoc-openapi-webmvc-core-1.2.32.jar;C:\Users\kjolenskap\.m2\repository\org\springdoc\springdoc-openapi-common\1.2.32\springdoc-openapi-common-1.2.32.jar;C:\Users\kjolenskap\.m2\repository\io\swagger\core\v3\swagger-models\2.1.1\swagger-models-2.1.1.jar;C:\Users\kjolenskap\.m2\repository\io\swagger\core\v3\swagger-annotations\2.1.1\swagger-annotations-2.1.1.jar;C:\Users\kjolenskap\.m2\repository\io\swagger\core\v3\swagger-integration\2.1.1\swagger-integration-2.1.1.jar;C:\Users\kjolenskap\.m2\repository\io\swagger\core\v3\swagger-core\2.1.1\swagger-core-2.1.1.jar;C:\Users\kjolenskap\.m2\repository\javax\xml\bind\jaxb-api\2.3.1\jaxb-api-2.3.1.jar;C:\Users\kjolenskap\.m2\repository\javax\activation\javax.activation-api\1.2.0\javax.activation-api-1.2.0.jar;C:\Users\kjolenskap\.m2\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-yaml\2.10.1\jackson-dataformat-yaml-2.10.1.jar;C:\Users\kjolenskap\.m2\repository\javax\validation\validation-api\2.0.1.Final\validation-api-2.0.1.Final.jar;C:\Users\kjolenskap\.m2\repository\io\github\classgraph\classgraph\4.8.44\classgraph-4.8.44.jar;C:\Users\kjolenskap\.m2\repository\org\webjars\swagger-ui\3.25.0\swagger-ui-3.25.0.jar;C:\Users\kjolenskap\.m2\repository\org\webjars\webjars-locator\0.38\webjars-locator-0.38.jar;C:\Users\kjolenskap\.m2\repository\org\webjars\webjars-locator-core\0.41\webjars-locator-core-0.41.jar;C:\Users\kjolenskap\.m2\repository\org\webjars\npm\angular__http\2.4.10\angular__http-2.4.10.jar;C:\Users\kjolenskap\.m2\repository\org\apache\commons\commons-lang3\3.9\commons-lang3-3.9.jar;C:\Users\kjolenskap\.m2\repository\com\google\gdata\core\1.47.1\core-1.47.1.jar;C:\Users\kjolenskap\.m2\repository\com\google\oauth-client\google-oauth-client-jetty\1.11.0-beta\google-oauth-client-jetty-1.11.0-beta.jar;C:\Users\kjolenskap\.m2\repository\com\google\oauth-client\google-oauth-client-java6\1.11.0-beta\google-oauth-client-java6-1.11.0-beta.jar;C:\Users\kjolenskap\.m2\repository\org\mortbay\jetty\jetty\6.1.26\jetty-6.1.26.jar;C:\Users\kjolenskap\.m2\repository\org\mortbay\jetty\jetty-util\6.1.26\jetty-util-6.1.26.jar;C:\Users\kjolenskap\.m2\repository\org\mortbay\jetty\servlet-api\2.5-20081211\servlet-api-2.5-20081211.jar;C:\Users\kjolenskap\.m2\repository\com\google\code\findbugs\jsr305\1.3.7\jsr305-1.3.7.jar;C:\Users\kjolenskap\.m2\repository\javax\mail\mail\1.4\mail-1.4.jar;C:\Users\kjolenskap\.m2\repository\com\google\api-client\google-api-client\1.32.1\google-api-client-1.32.1.jar;C:\Users\kjolenskap\.m2\repository\com\google\oauth-client\google-oauth-client\1.31.5\google-oauth-client-1.31.5.jar;C:\Users\kjolenskap\.m2\repository\com\google\http-client\google-http-client-gson\1.39.2\google-http-client-gson-1.39.2.jar;C:\Users\kjolenskap\.m2\repository\com\google\code\gson\gson\2.8.6\gson-2.8.6.jar;C:\Users\kjolenskap\.m2\repository\com\google\http-client\google-http-client-apache-v2\1.39.2\google-http-client-apache-v2-1.39.2.jar;C:\Users\kjolenskap\.m2\repository\org\apache\httpcomponents\httpcore\4.4.12\httpcore-4.4.12.jar;C:\Users\kjolenskap\.m2\repository\org\apache\httpcomponents\httpclient\4.5.10\httpclient-4.5.10.jar;C:\Users\kjolenskap\.m2\repository\commons-codec\commons-codec\1.13\commons-codec-1.13.jar;C:\Users\kjolenskap\.m2\repository\com\google\http-client\google-http-client\1.39.2\google-http-client-1.39.2.jar;C:\Users\kjolenskap\.m2\repository\com\google\j2objc\j2objc-annotations\1.3\j2objc-annotations-1.3.jar;C:\Users\kjolenskap\.m2\repository\io\opencensus\opencensus-api\0.28.0\opencensus-api-0.28.0.jar;C:\Users\kjolenskap\.m2\repository\io\grpc\grpc-context\1.27.2\grpc-context-1.27.2.jar;C:\Users\kjolenskap\.m2\repository\io\opencensus\opencensus-contrib-http-util\0.28.0\opencensus-contrib-http-util-0.28.0.jar;C:\Users\kjolenskap\.m2\repository\com\google\apis\google-api-services-youtube\v3-rev222-1.25.0\google-api-services-youtube-v3-rev222-1.25.0.jar;C:\Users\kjolenskap\.m2\repository\com\google\api-client\google-api-client-jackson2\1.20.0\google-api-client-jackson2-1.20.0.jar;C:\Users\kjolenskap\.m2\repository\com\google\http-client\google-http-client-jackson2\1.20.0\google-http-client-jackson2-1.20.0.jar;C:\Users\kjolenskap\.m2\repository\com\google\guava\guava\13.0-rc1\guava-13.0-rc1.jar;C:\Users\kjolenskap\.m2\repository\org\projectlombok\lombok\1.18.10\lombok-1.18.10.jar;C:\Users\kjolenskap\.m2\repository\com\jayway\jsonpath\json-path\2.4.0\json-path-2.4.0.jar;C:\Users\kjolenskap\.m2\repository\net\minidev\json-smart\2.3\json-smart-2.3.jar;C:\Users\kjolenskap\.m2\repository\net\minidev\accessors-smart\1.2\accessors-smart-1.2.jar;C:\Users\kjolenskap\.m2\repository\org\ow2\asm\asm\5.0.4\asm-5.0.4.jar;C:\Users\kjolenskap\.m2\repository\jakarta\xml\bind\jakarta.xml.bind-api\2.3.2\jakarta.xml.bind-api-2.3.2.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-core\5.2.2.RELEASE\spring-core-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\spring-jcl\5.2.2.RELEASE\spring-jcl-5.2.2.RELEASE.jar;C:\Users\kjolenskap\.m2\repository\org\springframework\security\spring-security-core\5.2.1.RELEASE\spring-security-core-5.2.1.RELEASE.jar ru.example.SimbirSoftPractice.util.ApiExample
//        Please enter a search term: car
//        java.lang.NoClassDefFoundError: com/google/common/base/StandardSystemProperty
//        at com.google.api.client.googleapis.services.AbstractGoogleClientRequest$ApiClientVersion.<init>(AbstractGoogleClientRequest.java:149)
//        at com.google.api.client.googleapis.services.AbstractGoogleClientRequest$ApiClientVersion.<clinit>(AbstractGoogleClientRequest.java:145)
//        at com.google.api.client.googleapis.services.AbstractGoogleClientRequest.<init>(AbstractGoogleClientRequest.java:135)
//        at com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest.<init>(AbstractGoogleJsonClientRequest.java:58)
//        at com.google.api.services.youtube.YouTubeRequest.<init>(YouTubeRequest.java:43)
//        at com.google.api.services.youtube.YouTube$Search$List.<init>(YouTube.java:11966)
//        at com.google.api.services.youtube.YouTube$Search.list(YouTube.java:11940)
//        at ru.example.SimbirSoftPractice.util.ApiExample.main(ApiExample.java:95)
//        Caused by: java.lang.ClassNotFoundException: com.google.common.base.StandardSystemProperty
//        at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
//        at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
//        at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:352)
//        at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
//        ... 8 more
//