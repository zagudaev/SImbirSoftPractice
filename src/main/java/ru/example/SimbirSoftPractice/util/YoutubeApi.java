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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class YoutubeApi {


    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_CHANNEL_RETURNED = 1;
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static final JsonFactory JSON_FACTORY = new JacksonFactory();


    public List<String> GetVideo(String channelName,String videoName) {

        String videoId = "";
        String viewCount = "";

        String likeCount = "";

        // чтение файла с ключем
        Properties properties = new Properties();
        try {
            InputStream in = YoutubeApi.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        try {

            YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-ApiExample-sample").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");

            // установка кльча, строки запроса  и других параметров
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(channelName);

            search.setType("channel");

            search.setMaxResults(NUMBER_OF_CHANNEL_RETURNED);

            //запрос
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            List<String> channels = new ArrayList<String>();
            if (searchResultList != null) {

                String channelId = searchResultList.get(0).getId().getChannelId();

                YouTube.Channels.List listChannelsRequest = youtube.channels().list("snippet,contentDetails, statistics ").setId(channelId).setKey(apiKey);
                listChannelsRequest.setFields("items/contentDetails,nextPageToken,pageInfo");
                ChannelListResponse listResponse = listChannelsRequest.execute();
                List<Channel> channelList = listResponse.getItems();

                if (channelList != null) {
                    String uploadPlaylistId =
                            channelList.get(0).getContentDetails().getRelatedPlaylists().getUploads();
                    System.out.println(uploadPlaylistId);
                    List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
                    YouTube.PlaylistItems.List playlistItemRequest =
                            youtube.playlistItems().list("id,contentDetails,snippet");
                    playlistItemRequest.setKey(apiKey);
                    playlistItemRequest.setPlaylistId(uploadPlaylistId);
                    playlistItemRequest.setFields(
                            "items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");

                    String nextToken = "";
                    do {
                        playlistItemRequest.setPageToken(nextToken);
                        PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                        playlistItemList.addAll(playlistItemResult.getItems());

                        nextToken = playlistItemResult.getNextPageToken();
                    } while (nextToken != null);
                    System.out.println("ok");
                    for (PlaylistItem playlistItem : playlistItemList) {
                        if (playlistItem.getSnippet().getTitle().equals(videoName)) {
                            videoId = playlistItem.getContentDetails().getVideoId();
                        }

                    }

                    if (videoId != null) {
                        YouTube.Videos.List ListVideo = youtube.videos().list("statistics").setKey(apiKey).setId(videoId);
                        VideoListResponse videoListResponse = ListVideo.execute();
                        List<Video> video = videoListResponse.getItems();
                        viewCount = String.valueOf(video.get(0).getStatistics().getViewCount());
                        likeCount = String.valueOf(video.get(0).getStatistics().getLikeCount());


                    }

                }


            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        List<String> list = new ArrayList();
        list.add(videoId);
        list.add(viewCount);
        list.add(likeCount);


        return list;

    }

    public List<String> GetChangellInfo (String channelName){

        List<String> listResult = new ArrayList();
        listResult.add(channelName);

        // чтение файла с ключем
        Properties properties = new Properties();
        try {
            InputStream in = YoutubeApi.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        try {

            YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-ApiExample-sample").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");

            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(channelName);

            search.setType("channel");

            search.setMaxResults(NUMBER_OF_CHANNEL_RETURNED);

            //запрос
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            List<String> channels = new ArrayList<String>();
            if (searchResultList != null) {

                String channelId = searchResultList.get(0).getId().getChannelId();

                YouTube.Channels.List listChannelsRequest = youtube.channels().list("snippet,contentDetails, statistics ").setId(channelId).setKey(apiKey);
                listChannelsRequest.setFields("items/contentDetails,nextPageToken,pageInfo");
                ChannelListResponse listResponse = listChannelsRequest.execute();
                List<Channel> channelList = listResponse.getItems();

                if (channelList != null) {
                    String uploadPlaylistId =
                            channelList.get(0).getContentDetails().getRelatedPlaylists().getUploads();
                    System.out.println(uploadPlaylistId);
                    List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
                    YouTube.PlaylistItems.List playlistItemRequest =
                            youtube.playlistItems().list("id,contentDetails,snippet");
                    playlistItemRequest.setKey(apiKey);
                    playlistItemRequest.setPlaylistId(uploadPlaylistId);
                    playlistItemRequest.setFields(
                            "items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");
                    playlistItemRequest.setMaxResults((long) 5);
                    PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();
                    playlistItemList = playlistItemResult.getItems();
                    for (PlaylistItem playlistItem : playlistItemList) {
                        listResult.add("https://www.youtube.com/watch?v="+playlistItem.getContentDetails().getVideoId());

                    }



                }


            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return listResult;
    }
    public String GetvidoCommentRanom (String channelName,String videoName){
        String videoId = "";
        String result = "";

        Properties properties = new Properties();
        try {
            InputStream in = YoutubeApi.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        try {

            YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-ApiExample-sample").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");

            // установка кльча, строки запроса  и других параметров
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(channelName);

            search.setType("channel");

            search.setMaxResults(NUMBER_OF_CHANNEL_RETURNED);

            //запрос
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            List<String> channels = new ArrayList<String>();
            if (searchResultList != null) {

                String channelId = searchResultList.get(0).getId().getChannelId();

                YouTube.Channels.List listChannelsRequest = youtube.channels().list("snippet,contentDetails, statistics ").setId(channelId).setKey(apiKey);
                listChannelsRequest.setFields("items/contentDetails,nextPageToken,pageInfo");

                ChannelListResponse listResponse = listChannelsRequest.execute();
                List<Channel> channelList = listResponse.getItems();

                if (channelList != null) {
                    String uploadPlaylistId =
                            channelList.get(0).getContentDetails().getRelatedPlaylists().getUploads();
                    System.out.println(uploadPlaylistId);
                    List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
                    YouTube.PlaylistItems.List playlistItemRequest =
                            youtube.playlistItems().list("id,contentDetails,snippet");
                    playlistItemRequest.setKey(apiKey);
                    playlistItemRequest.setPlaylistId(uploadPlaylistId);
                    playlistItemRequest.setFields(
                            "items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");

                    String nextToken = "";
                    do {
                        playlistItemRequest.setPageToken(nextToken);
                        PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                        playlistItemList.addAll(playlistItemResult.getItems());

                        nextToken = playlistItemResult.getNextPageToken();
                    } while (nextToken != null);
                    for (PlaylistItem playlistItem : playlistItemList) {
                        if (playlistItem.getSnippet().getTitle().equals(videoName)) {
                            videoId = playlistItem.getContentDetails().getVideoId();
                        }

                    }
                    CommentThreadListResponse videoCommentsListResponse = youtube.commentThreads()
                            .list("snippet").setVideoId(videoId).setTextFormat("plainText").execute();
                    List<CommentThread> videoComments = videoCommentsListResponse.getItems();

                    Random random = new Random();
                    result = videoComments.get(random.nextInt(videoComments.size())).getSnippet().getTopLevelComment().getSnippet().getTextDisplay();

                }


            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }




        return result;

    }
}
