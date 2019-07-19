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

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.ztcartxe.reppopkpa.apkpopper.model.PackageInfoItem;

import java.util.ArrayList;
import java.util.List;

public class PackageInfoManager {
    private static final String TAG = "PackageInfoManager";

    private Context context;
    private PackageTask packageTask;

    public PackageInfoManager(Context context) {
        this.context = context;
    }

    public void getPackages(PackageListener packageListener){
        packageTask = new PackageTask(this.context, packageListener);
        packageTask.execute();
    }

    public interface PackageListener{
        void onStart();
        void onFinished(ArrayList<PackageInfoItem> systemPackageInfoItemArrayList, ArrayList<PackageInfoItem> installedPackageInfoItemArrayList);
        void onProgress(int progress);
    }

    private class PackageTask extends AsyncTask<Void, Integer, Void>{
        PackageListener packageListener;
        ArrayList<PackageInfoItem> systemPackageInfoItemArrayList = new ArrayList<>();
        ArrayList<PackageInfoItem> installedPackageInfoItemArrayList = new ArrayList<>();
        Context context;

        PackageTask(Context context, PackageListener packageListener) {
            super();
            this.context = context;
            this.packageListener = packageListener;
            this.packageListener.onStart();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            packageListener.onProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);

                int count = 0;
                int max = resolveInfoList.size();
                for (ResolveInfo resolveInfo : resolveInfoList) {
                    int progressVal = (int) ((double)count / (double)max * 100);

                    PackageInfoItem packageInfoItem = new PackageInfoItem(context, resolveInfo);
                    if(packageInfoItem.isSystemPackages())
                        systemPackageInfoItemArrayList.add(packageInfoItem);
                    else
                        installedPackageInfoItemArrayList.add(packageInfoItem);

                    publishProgress(progressVal);
                    if (isCancelled()) break;
                    count++;
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
                Log.e(TAG, ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            packageListener.onFinished(systemPackageInfoItemArrayList, installedPackageInfoItemArrayList);
        }
    }
}