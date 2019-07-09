package com.ztcartxe.reppopkpa.apkpopper.engine;

import android.content.Context;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class PopManager{
    private File fileSource;
    private Context context;
    private String path, fileName;
    private ProgressCallBack progressCallBack;

    public interface ProgressCallBack{
        void onStart();
        void onProgress(CallbackByteChannel cbc, double progress);
        void onStop();
    }

    public PopManager(Context context, File fileSource, String path, String fileName, ProgressCallBack progressCallBack) {
        this.context = context;
        this.fileSource = fileSource;
        this.path = path;
        this.fileName = fileName;
        this.progressCallBack = progressCallBack;
    }

    public void ExtractApk(){
        try{
            File fileDestination;
            File dir = new File(this.path);
            if(dir.exists() && dir.isDirectory()){
                fileDestination = new File(path  + fileName);
            }
            else{
                //Create Directory
                dir.mkdir();
                fileDestination = new File(path  + fileName);
            }

            pop(fileDestination);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void pop(File fileDestination){
        try {
            progressCallBack.onStart();
            FileInputStream fileInputStream = new FileInputStream(fileSource);
            FileOutputStream fileOutputStream = new FileOutputStream(fileDestination);

            FileChannel fileChannel_IN = fileInputStream.getChannel();
            FileChannel fileChannel_OUT = fileOutputStream.getChannel();

            WritableByteChannel writableByteChannel = new CallbackByteChannel(fileChannel_OUT, fileChannel_IN.size(), progressCallBack);

            fileChannel_IN.transferTo(0, fileChannel_IN.size(), writableByteChannel);

            fileChannel_IN.close();
            fileChannel_OUT.close();
            fileInputStream.close();
            fileOutputStream.close();
            progressCallBack.onStop();

            Toast.makeText(context, "APK Extracted : " + fileDestination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public class CallbackByteChannel implements WritableByteChannel{
        ProgressCallBack delegate;
        long size;
        WritableByteChannel wbc;
        long sizeWrite;

        CallbackByteChannel(WritableByteChannel wbc, long size, ProgressCallBack delegate) {
            this.wbc = wbc;
            this.size = size;
            this.delegate = delegate;
        }

        @Override
        public boolean isOpen() {
            return wbc.isOpen();
        }

        @Override
        public void close() throws IOException {
            wbc.close();
        }

        @Override
        public int write(ByteBuffer byteBuffer) throws IOException {
            int n;
            double progress;
            if((n = wbc.write(byteBuffer)) > 0){
                sizeWrite += n;
                progress = size > 0 ? (double) sizeWrite / (double) size * 100.0 : -1.0;
                delegate.onProgress(this, progress);
            }
            return n;
        }
    }
}