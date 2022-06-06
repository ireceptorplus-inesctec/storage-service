package com.ireceptorplus.ireceptorchainclient.MetadataServiceAPI;

public class FileUrlBuilder
{
    private static final String defaultListeningPort = "8080";

    public static String buildFromUuid(String peerIpAddr, String uuid)
    {
        return buildFromUuid(peerIpAddr, defaultListeningPort, uuid);
    }

    public static String buildFromUuid(String peerIpAddr, String listeningPort, String uuid)
    {
        return "https://" + peerIpAddr + ":" + listeningPort + "/file/" + uuid;
    }
}
