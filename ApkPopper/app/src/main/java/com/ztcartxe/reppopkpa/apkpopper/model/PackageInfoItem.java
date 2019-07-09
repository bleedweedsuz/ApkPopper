package com.ztcartxe.reppopkpa.apkpopper.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class PackageInfoItem implements Comparable<PackageInfoItem>{
    private ResolveInfo resolveInfo;

    public String PackageName;
    public String AppName;
    public Drawable AppIcon;
    public ApplicationInfo applicationInfo;
    public Context context;

    public PackageInfoItem(Context context, ResolveInfo resolveInfo){
        this.context = context;
        this.resolveInfo = resolveInfo;
        this.PackageName = resolveInfo.activityInfo.packageName;
        try {
            this.applicationInfo = context.getPackageManager().getApplicationInfo(this.PackageName, 0);
            this.AppName = (String)context.getPackageManager().getApplicationLabel(this.applicationInfo);
            this.AppIcon = context.getPackageManager().getApplicationIcon(this.PackageName);
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean isSystemPackages(){ return (this.resolveInfo.activityInfo != null && (this.resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);}

    @Override
    public int compareTo(PackageInfoItem packageInfoItem) {
        return this.AppName.compareTo(packageInfoItem.AppName);
    }
}