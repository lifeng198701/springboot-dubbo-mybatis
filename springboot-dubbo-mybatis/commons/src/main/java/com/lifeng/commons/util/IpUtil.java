package com.lifeng.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by lifeng on 2018/5/23.
 */
public class IpUtil {

    final static Logger log = LoggerFactory.getLogger(IpUtil.class);



    /**
     * IP地址的正则表达式.
     */
    public static final String IP_REGEX = "((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})";

    private static volatile String cachedIpAddress;

    /**
     * 获取本机IP地址.
     *
     * <p>
     * 有限获取外网IP地址.
     * 也有可能是链接着路由器的最终IP地址.
     * </p>
     *
     * @return 本机IP地址
     */
    public static String getIp() {
        if (null != cachedIpAddress) {
            return cachedIpAddress;
        }
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (final SocketException ex) {
            log.error(ex.toString());
        }
        String localIpAddress = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = netInterfaces.nextElement();
            Enumeration<InetAddress> ipAddresses = netInterface.getInetAddresses();
            while (ipAddresses.hasMoreElements()) {
                InetAddress ipAddress = ipAddresses.nextElement();
                if (isPublicIpAddress(ipAddress)) {
                    String publicIpAddress = ipAddress.getHostAddress();
                    cachedIpAddress = publicIpAddress;
                    return publicIpAddress;
                }
                if (isLocalIpAddress(ipAddress)) {
                    localIpAddress = ipAddress.getHostAddress();
                }
            }
        }
        cachedIpAddress = localIpAddress;
        return localIpAddress;
    }

    private static boolean isPublicIpAddress(final InetAddress ipAddress) {
        return !ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private static boolean isLocalIpAddress(final InetAddress ipAddress) {
        return ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private static boolean isV6IpAddress(final InetAddress ipAddress) {
        return ipAddress.getHostAddress().contains(":");
    }

    /**
     * 获取本机Host名称.
     *
     * @return 本机Host名称
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException ex) {
            log.error(ex.toString());
        }
        return "";
    }

    public static String getHostLanIP() {
        List<InetAddress> addresses = new ArrayList<InetAddress>();

        try {
            Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
            while (ifs.hasMoreElements()) {
                NetworkInterface nic = ifs.nextElement();
                if (nic.isLoopback() || !nic.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addrs = nic.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    if (addr.isAnyLocalAddress() || addr.isLinkLocalAddress()
                            || addr.isLoopbackAddress() || addr.isMulticastAddress()) {
                        continue;
                    }

                    addresses.add(addr);
                }
            }
        } catch (SocketException e) {
            log.error("Get network interface failed", e);
        }

        for (InetAddress addr : addresses) {
            if (isIPv4Addr(addr) && isLanAddr(addr)) {
                return addr.getHostAddress();
            }
        }
        return null;
    }

    /**
     * Whether an InetAddress is IPv4.
     *
     * @param addr
     * @return
     */
    public static boolean isIPv4Addr(InetAddress addr) {
        return addr != null && addr instanceof Inet4Address;
    }

    /**
     * Whether an InetAddress is LAN address.
     *
     * @param addr
     * @return
     */
    public static boolean isLanAddr(InetAddress addr) {
        if (addr == null) {
            return false;
        }

        String ip = addr.getHostAddress();
        if (ip == null) {
            return false;
        }

        int[] decs = new int[4];
        try {
            int i = 0;
            for (String decStr : ip.split("\\.")) {
                decs[i++] = Integer.parseInt(decStr);
            }
        } catch (Exception e) {
            return false;
        }

        if (decs[0] == 10) {
            return true;
        }

        if (decs[0] == 172) {
            return decs[1] >= 16 && decs[1] <= 31;
        }

        if (decs[0] == 192) {
            return decs[1] == 168;
        }

        return false;
    }


}
