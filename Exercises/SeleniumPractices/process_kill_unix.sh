# Company          : Go Digit General Insurance Limited
# Author           : Deepjyoti Barman
# Designation      : QA Analyst
# License          : GNU GPL
# Date             : September 04 (Friday), 2020
# Description      : Script to kill all the processes/instances running in local system for UNIX (Linux/Mac) platform


#!/bin/bash

echo "Executing script to kill all unwanted processes"
ps aux | grep 'chromedriver*'
kill -9  $(ps aux | grep 'chromedriver*' | awk '{print $2}') && echo "Successfully terminated all chromedriver instances" || echo "Process \"chromedriver\" is not running" && echo "System could not find any chromedriver instances"