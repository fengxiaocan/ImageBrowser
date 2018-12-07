package com.app.baselib.pmsion;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ADD_VOICEMAIL;
import static android.Manifest.permission.BODY_SENSORS;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.PROCESS_OUTGOING_CALLS;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_MMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECEIVE_WAP_PUSH;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.USE_SIP;
import static android.Manifest.permission.WRITE_CALENDAR;
import static android.Manifest.permission.WRITE_CALL_LOG;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @name： BaseApp
 * @package： com.dgtle.baselib.base.pmsion
 * @author: Noah.冯 QQ:1066537317
 * @time: 15:49
 * @version: 1.1
 * @desc： TODO
 */

public interface IPermission {
    //Normal Permissions
    //ACCESS_LOCATION_EXTRA_COMMANDS
    //ACCESS_NETWORK_STATE
    //ACCESS_NOTIFICATION_POLICY
    //ACCESS_WIFI_STATE
    //BLUETOOTH
    //BLUETOOTH_ADMIN
    //BROADCAST_STICKY
    //CHANGE_NETWORK_STATE
    //CHANGE_WIFI_MULTICAST_STATE
    //CHANGE_WIFI_STATE
    //DISABLE_KEYGUARD
    //EXPAND_STATUS_BAR
    //GET_PACKAGE_SIZE
    //INSTALL_SHORTCUT
    //INTERNET
    //KILL_BACKGROUND_PROCESSES
    //MODIFY_AUDIO_SETTINGS
    //NFC
    //READ_SYNC_SETTINGS
    //READ_SYNC_STATS
    //RECEIVE_BOOT_COMPLETED
    //REORDER_TASKS
    //REQUEST_INSTALL_PACKAGES
    //SET_ALARM
    //SET_TIME_ZONE
    //SET_WALLPAPER
    //SET_WALLPAPER_HINTS
    //TRANSMIT_IR
    //UNINSTALL_SHORTCUT
    //USE_FINGERPRINT
    //VIBRATE
    //WAKE_LOCK
    //WRITE_SYNC_SETTINGS

    //Dangerous Permissions:

    //    group:android.permission-group.CONTACTS
    String CONTACTS1 = WRITE_CONTACTS;
    String CONTACTS2 = GET_ACCOUNTS;
    String CONTACTS3 = READ_CONTACTS;

    //    group:android.permission-group.PHONE
    String PHONE1 = READ_CALL_LOG;
    String PHONE2 = READ_PHONE_STATE;
    String PHONE3 = CALL_PHONE;
    String PHONE4 = WRITE_CALL_LOG;
    String PHONE5 = USE_SIP;
    String PHONE6 = PROCESS_OUTGOING_CALLS;
    String PHONE7 = ADD_VOICEMAIL;

    //    group:android.permission-group.CALENDAR
    String CALENDAR1 = READ_CALENDAR;

    //    group:android.permission-group.CAMERA
    String CAMERA0 = CAMERA;
    String CALENDAR2 = WRITE_CALENDAR;
    //
    //    group:android.permission-group.SENSORS
    String SENSORS = BODY_SENSORS;
    //
    //    group:android.permission-group.LOCATION
    String LOCATION1 = ACCESS_FINE_LOCATION;
    String LOCATION2 = ACCESS_COARSE_LOCATION;
    //
    //    group:android.permission-group.STORAGE
    String STORAGE1 = READ_EXTERNAL_STORAGE;
    String STORAGE2 = WRITE_EXTERNAL_STORAGE;
    //
    //    group:android.permission-group.MICROPHONE
    String MICROPHONE = RECORD_AUDIO;
    //
    //    group:android.permission-group.SMS
    String SMS1 = READ_SMS;
    String SMS2 = RECEIVE_WAP_PUSH;
    String SMS3 = RECEIVE_MMS;
    String SMS4 = RECEIVE_SMS;
    String SMS5 = SEND_SMS;
}
