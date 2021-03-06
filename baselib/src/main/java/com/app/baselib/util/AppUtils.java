package com.app.baselib.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import com.app.baselib.key.EncryptUtils;
import com.app.baselib.log.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *
 *     time  : 2016/08/02
 *     desc  : App相关工具类
 * </pre>
 */
public final class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 判断App是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(String packageName) {
        return !StringUtils.isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 安装App(支持7.0)
     *
     * @param filePath 文件路径
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     * <br>参看https://developer.android
     * .com/reference/android/support/v4/content
     * /FileProvider.html
     */
    public static void installApp(String filePath,String authority) {
        installApp(FileUtils.getFileByPath(filePath),authority);
    }

    /**
     * 安装App（支持7.0）
     *
     * @param file 文件
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     * <br>参看https://developer.android
     * .com/reference/android/support/v4/content
     * /FileProvider.html
     */
    public static void installApp(File file,String authority) {
        if (!FileUtils.isFileExists(file)) {
            return;
        }
        Utils.getContext().startActivity(IntentUtils.getInstallAppIntent(file,authority));
    }

    /**
     * 安装App（支持6.0）
     *
     * @param activity activity
     * @param filePath 文件路径
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     * <br>参看https://developer.android
     * .com/reference/android/support/v4/content
     * /FileProvider.html
     * @param requestCode 请求值
     */
    public static void installApp(
            Activity activity,String filePath,String authority,int requestCode
    )
    {
        installApp(activity,FileUtils.getFileByPath(filePath),authority,requestCode);
    }

    /**
     * 安装App(支持6.0)
     *
     * @param activity activity
     * @param file 文件
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     * <br>参看https://developer.android
     * .com/reference/android/support/v4/content
     * /FileProvider.html
     * @param requestCode 请求值
     */
    public static void installApp(
            Activity activity,File file,String authority,int requestCode
    )
    {
        if (!FileUtils.isFileExists(file)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getInstallAppIntent(file,authority),
                                        requestCode
        );
    }

    /**
     * 静默安装App
     * <p>非root需添加权限 {@code <uses-permission android:name="android.permission
     * .INSTALL_PACKAGES" />}</p>
     *
     * @param filePath 文件路径
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    public static boolean installAppSilent(String filePath) {
        File file = FileUtils.getFileByPath(filePath);
        if (!FileUtils.isFileExists(file)) {
            return false;
        }
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " +
                         "" +
                         "" +
                         "" +
                         filePath;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command,!isSystemApp(),true);
        return commandResult.successMsg != null &&
               commandResult.successMsg.toLowerCase().contains("success");
    }

    /**
     * 卸载App
     *
     * @param packageName 包名
     */
    public static void uninstallApp(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        Utils.getContext().startActivity(IntentUtils.getUninstallAppIntent(packageName));
    }

    /**
     * 卸载App
     *
     * @param activity activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void uninstallApp(
            Activity activity,String packageName,int requestCode
    )
    {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName),requestCode);
    }

    /**
     * 静默卸载App
     * <p>非root需添加权限 {@code <uses-permission android:name="android.permission
     * .DELETE_PACKAGES" />}</p>
     *
     * @param packageName 包名
     * @param isKeepData 是否保留数据
     * @return {@code true}: 卸载成功<br>{@code false}: 卸载失败
     */
    public static boolean uninstallAppSilent(
            String packageName,boolean isKeepData
    )
    {
        if (StringUtils.isSpace(packageName)) {
            return false;
        }
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm " +
                         "uninstall " +
                         (isKeepData ? "-k " : "") +
                         packageName;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command,!isSystemApp(),true);
        return commandResult.successMsg != null &&
               commandResult.successMsg.toLowerCase().contains("success");
    }


    /**
     * 判断App是否有root权限
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppRoot() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("echo root",true);
        if (result.result == 0) {
            return true;
        }
        if (result.errorMsg != null) {
            LogUtils.d("isAppRoot",result.errorMsg);
        }
        return false;
    }

    /**
     * 打开App
     *
     * @param packageName 包名
     */
    public static void launchApp(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        Utils.getContext().startActivity(IntentUtils.getLaunchAppIntent(packageName));
    }

    /**
     * 打开App
     *
     * @param activity activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void launchApp(
            Activity activity,String packageName,int requestCode
    )
    {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName),requestCode);
    }

    /**
     * 获取App包名
     *
     * @return App包名
     */
    public static String getAppPackageName() {
        return Utils.getContext().getPackageName();
    }

    /**
     * 获取App具体设置
     */
    public static void getAppDetailsSettings() {
        getAppDetailsSettings(Utils.getContext().getPackageName());
    }

    /**
     * 获取App具体设置
     *
     * @param packageName 包名
     */
    public static void getAppDetailsSettings(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        Utils.getContext().startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName));
    }

    /**
     * 获取App名称
     *
     * @return App名称
     */
    public static String getAppName() {
        return getAppName(Utils.getContext().getPackageName());
    }

    /**
     * 获取App名称
     *
     * @param packageName 包名
     * @return App名称
     */
    public static String getAppName(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName,0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App图标
     *
     * @return App图标
     */
    public static Drawable getAppIcon() {
        return getAppIcon(Utils.getContext().getPackageName());
    }

    /**
     * 获取App图标
     *
     * @param packageName 包名
     * @return App图标
     */
    public static Drawable getAppIcon(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName,0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App路径
     *
     * @return App路径
     */
    public static String getAppPath() {
        return getAppPath(Utils.getContext().getPackageName());
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        try {
            PackageManager packageManager = Utils.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(Utils.getContext()
                                                                         .getPackageName(),0);
            return packageInfo.versionName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            PackageManager packageManager = Utils.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(Utils.getContext()
                                                                         .getPackageName(),0);
            return packageInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取App路径
     *
     * @param packageName 包名
     * @return App路径
     */
    public static String getAppPath(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName,0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static String getAppVersionName() {
        return getAppVersionName(Utils.getContext().getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName,0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本码
     *
     * @return App版本码
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(Utils.getContext().getPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param packageName 包名
     * @return App版本码
     */
    public static int getAppVersionCode(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return -1;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName,0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断App是否是系统应用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp() {
        return isSystemApp(Utils.getContext().getPackageName());
    }

    /**
     * 判断App是否是系统应用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName,0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取Application信息
     *
     * @return ApplicationInfo
     */
    public static ApplicationInfo getApplicationInfo(
            String packageName,int packageManagerType
    )
    {
        PackageManager pm = getPackageManager();
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo(packageName,packageManagerType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ai;
    }

    /**
     * 获取Application信息
     *
     * @param packageManagerType
     * @return ApplicationInfo
     */
    public static ApplicationInfo getApplicationInfo(int packageManagerType) {
        String name = getAppPackageName();
        return getApplicationInfo(name,packageManagerType);
    }

    /**
     * 获取Application信息
     *
     * @return ApplicationInfo
     */
    public static PackageManager getPackageManager() {
        return Utils.getContext().getPackageManager();
    }


    /**
     * 获取清单文件信息
     *
     * @param key
     * @param defultVel
     * @return
     */
    public static String getManifestMetaData(String key,String defultVel) {
        String keyString = defultVel;
        try {
            String appPackageName = AppUtils.getAppPackageName();
            ApplicationInfo appInfo = getApplicationInfo(appPackageName,
                                                         PackageManager.GET_META_DATA
            );
            keyString = appInfo.metaData.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyString;
    }

    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        boolean isDebug = (Utils.getApplication().getApplicationInfo().flags &
                           ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        return isDebug;
    }

    /**
     * 判断App是否是Debug版本
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return false;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName,0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取App签名
     *
     * @return App签名
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(Utils.getContext().getPackageName());
    }

    /**
     * 获取App签名
     *
     * @param packageName 包名
     * @return App签名
     */
    public static Signature[] getAppSignature(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures") PackageInfo pi = pm.getPackageInfo(packageName,
                                                                                            PackageManager.GET_SIGNATURES
            );
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用签名的的SHA1值
     * <p>可据此判断高德，百度地图key是否正确</p>
     *
     * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88
     * :1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(Utils.getContext().getPackageName());
    }

    /**
     * 获取应用签名的的SHA1值
     * <p>可据此判断高德，百度地图key是否正确</p>
     *
     * @param packageName 包名
     * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88
     * :1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1(String packageName) {
        Signature[] signature = getAppSignature(packageName);
        if (signature == null) {
            return null;
        }
        return EncryptUtils.encryptSHA1ToString(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}",":$0");
    }

    /**
     * 判断App是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        ActivityManager manager = (ActivityManager)Utils.getContext()
                                                        .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getContext().getPackageName());
            }
        }
        return false;
    }

    /**
     * 判断App是否处于前台
     * <p>当不是查看当前App，且SDK大于21时，
     * 需添加权限 {@code <uses-permission android:name="android.permission
     * .PACKAGE_USAGE_STATS"/>}</p>
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground(String packageName) {
        return !StringUtils.isSpace(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName());
    }

    /**
     * 获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @return 当前应用的AppInfo
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(Utils.getContext().getPackageName());
    }

    /**
     * 获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @param packageName 包名
     * @return 当前应用的AppInfo
     */
    public static AppInfo getAppInfo(String packageName) {
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName,0);
            return getBean(pm,pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到AppInfo的Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo类
     */
    private static AppInfo getBean(PackageManager pm,PackageInfo pi) {
        if (pm == null || pi == null) {
            return null;
        }
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName,name,icon,packagePath,versionName,versionCode,isSystem);
    }

    /**
     * 获取所有已安装App信息
     * <p>{@link #getBean(PackageManager,PackageInfo)}（名称，图标，包名，包路径，版本号，版本Code，是否系统应用）</p>
     * <p>依赖上面的getBean方法</p>
     *
     * @return 所有已安装的AppInfo列表
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = Utils.getContext().getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm,pi);
            if (ai == null) {
                continue;
            }
            list.add(ai);
        }
        return list;
    }

    /**
     * 清除App所有数据
     *
     * @param dirPaths 目录路径
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除App所有数据
     *
     * @param dirs 目录
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache();
        isSuccess &= CleanUtils.cleanInternalDbs();
        isSuccess &= CleanUtils.cleanInternalSP();
        isSuccess &= CleanUtils.cleanInternalFiles();
        isSuccess &= CleanUtils.cleanExternalCache();
        for (File dir : dirs) {
            isSuccess &= CleanUtils.cleanCustomCache(dir);
        }
        return isSuccess;
    }

    

    /**
     * 打开微信
     */
    public static void openWeiChat() {
        try {
            //打开微信
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm",
                                                  "com.tencent.mm.ui" + "" + ".LauncherUI"
            );
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            Utils.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭自身app
     */
    public static void exitApp() {
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /**
     * 获取进程名
     *
     * @param context
     * @param pid
     * @return
     */
    public static String getProcessName(Context context,int pid) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        return getProcessName(context,android.os.Process.myPid());
    }

    /**
     * 获取运行时最大内存
     * @return
     */
    public static long getRuntimeMaxMemory(){
        return Runtime.getRuntime().maxMemory();
    }
    /**
     * 获取运行时空闲内存
     * @return
     */
    public static long getRuntimeFreeMemory(){
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * 获取运行时最大内存
     * @return
     */
    public static long getRuntimeTotalMemory(){
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * 封装App信息的Bean类
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        /**
         * @param name 名称
         * @param icon 图标
         * @param packageName 包名
         * @param packagePath 包路径
         * @param versionName 版本号
         * @param versionCode 版本码
         * @param isSystem 是否系统应用
         */
        public AppInfo(
                String packageName,
                String name,
                Drawable icon,
                String packagePath,
                String versionName,
                int versionCode,
                boolean isSystem
        )
        {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        @Override
        public String toString() {
            return "pkg name: " +
                   getPackageName() +
                   "\napp name: " +
                   getName() +
                   "\napp path: " +
                   getPackagePath() +
                   "\napp v name: " +
                   getVersionName() +
                   "\napp v code: " +
                   getVersionCode() +
                   "\nis system: " +
                   isSystem();
        }
    }
}