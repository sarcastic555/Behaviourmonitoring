#!/bin/bash

adb shell "run-as com.example.behaviourmonitoring cp /data/user/0/com.example.behaviourmonitoring/files/20200411.csv /sdcard/"
adb pull /sdcard/20200411.csv ~/Desktop/android_data
