#!/bin/bash

start_time=`date +%s -d '2020/05/10'`
back=0
date "+%s" -d "-${back} days"
echo ${start_time}
while [ `date +%s -d "-${back} days"` -gt ${start_time} ]; do
    datestring=`date "+%Y%m%d" -d "-${back} days"`
    echo ${datestring}
    adb shell "run-as com.example.behaviourmonitoring cp /data/user/0/com.example.behaviourmonitoring/files/${datestring}.csv /sdcard/"
    adb pull /sdcard/${datestring}.csv ~/Desktop/android_data/
    back=`expr ${back} + 1`
done
