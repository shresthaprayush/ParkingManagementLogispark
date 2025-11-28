
package com.logispark.parkingmanagementlogispark.utilites;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BackupManager {

    private static final String BACKUP_FOLDER_NAME = "ParkingManagementBackups";
    private static final int MAX_BACKUP_FILES = 10;
    private Context context;
    private static final String TAG = "BackupManager";


    public BackupManager(Context context) {
        this.context = context;
    }

    public void createBackup(List<ModelParkingData> parkingDataList) {
        File backupDir = new File(context.getExternalFilesDir(null), BACKUP_FOLDER_NAME);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "backup_" + timeStamp + ".json";
        File backupFile = new File(backupDir, fileName);

        try (FileWriter writer = new FileWriter(backupFile)) {
            Gson gson = new Gson();
            gson.toJson(parkingDataList, writer);
            Log.d(TAG, "Backup created successfully at: " + backupFile.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error creating backup", e);
        }

        manageBackupFiles(backupDir);
    }

    private void manageBackupFiles(File backupDir) {
        File[] backupFiles = backupDir.listFiles();
        if (backupFiles != null && backupFiles.length > MAX_BACKUP_FILES) {
            Arrays.sort(backupFiles, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
            for (int i = 0; i < backupFiles.length - MAX_BACKUP_FILES; i++) {
                if (backupFiles[i].delete()) {
                    Log.d(TAG, "Deleted old backup file: " + backupFiles[i].getName());
                } else {
                    Log.e(TAG, "Failed to delete old backup file: " + backupFiles[i].getName());
                }
            }
        }
    }
}
