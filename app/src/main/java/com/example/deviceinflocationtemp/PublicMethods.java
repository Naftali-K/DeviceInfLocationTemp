package com.example.deviceinflocationtemp;

import android.os.Build;

import com.example.deviceinflocationtemp.enums.DeviceInf;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Author: naftalikomarovski
 * @Date: 2023/09/12
 */
public class PublicMethods {

    public static String getDeviceInf(DeviceInf deviceInfEnum) {
        String deviceInf = "";

        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        String product = Build.PRODUCT;
        String model = Build.MODEL;

        deviceInf = manufacturer + " " + brand + " " + product + " " + model;

        if (deviceInfEnum == DeviceInf.MANUFACTURER) {
            return manufacturer;
        }

        if (deviceInfEnum == DeviceInf.BRAND) {
            return brand;
        }

        if (deviceInfEnum == DeviceInf.PRODUCT) {
            return product;
        }

        if (deviceInfEnum == DeviceInf.MODEL) {
            return product;
        }

        return deviceInf;
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipAddress = inetAddress.getHostAddress();
                        // Проверка, это IPv4 или IPv6-адрес
                        boolean isIPv4 = ipAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) {
                                return ipAddress;
                            }
                        } else {
                            if (!isIPv4) {
                                int delim = ipAddress.indexOf('%'); // Удаление идентификатора области IPv6
                                return delim < 0 ? ipAddress.toUpperCase() : ipAddress.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
