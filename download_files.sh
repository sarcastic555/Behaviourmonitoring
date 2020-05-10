#!/bin/bash

adb pull /sdcard/test.txt ~/Desktop/
adb shell "run-as com.example.behaviourmonitoring cp /data/user/0/com.example.behaviourmonitoring/files/test.txt /sdcard/test.txt"
