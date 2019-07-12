/*

    ApkPopper "Simple App that lets user to get info about installed app and extract them."
    Copyright (C) 2019  Sujan Thapa

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package com.ztcartxe.reppopkpa.apkpopper.engine;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ztcartxe.reppopkpa.apkpopper.model.PackageInfoItem;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLConnection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PackageFullDetails {
    private PackageInfoItem packageInfoItem;
    private PackageInfo packageInfo;
    private Locale locale = Locale.US; //Default
    private File file;
    private ApkFile apkFile;
    private ApkMeta apkMeta;

    public PackageFullDetails(PackageInfoItem packageInfoItem) {
        try {
            this.packageInfoItem = packageInfoItem;
            this.file = new File(packageInfoItem.applicationInfo.sourceDir);
            this.apkFile = new ApkFile(this.file);
            this.apkMeta = apkFile.getApkMeta();
            this.packageInfo = packageInfoItem.context.getPackageManager().getPackageInfo(packageInfoItem.PackageName, 0);
        }
        catch (PackageManager.NameNotFoundException ex){
            ex.printStackTrace();
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public PackageInfoItem getPackageInfoItem() {
        return packageInfoItem;
    }

    public ApkFile getApkFile() {
        return apkFile;
    }

    public ApkMeta getApkMeta() {
        return apkMeta;
    }

    public String getFirstInstalledTime(){
        if(packageInfo != null) {
            long time = packageInfo.firstInstallTime;
            Date date = new Date(time);
            Format dFormat = new SimpleDateFormat("yyyy, MMM dd 'at' hh:mm a", locale);
            return dFormat.format(date);
        }
        return null;
    }

    public String getLastModifyTime(){
        if(packageInfo != null) {
            long time = packageInfo.lastUpdateTime;
            Date date = new Date(time);
            Format dFormat = new SimpleDateFormat("yyyy, MMM dd 'at' hh:mm a", locale);
            return dFormat.format(date);
        }
        return null;
    }

    public String getAbsolutePath(){
        //just return file path
        return file.getAbsolutePath();
    }

    public float getFileSize(){
        long fileSize = file.length(); //bytes
        return (float)fileSize / (1024*1024);
    }

    public String getMIME(){
        return URLConnection.getFileNameMap().getContentTypeFor(file.getName());
    }

    public File getFile() {
        return file;
    }
}