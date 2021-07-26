:: Company          : Go Digit General Insurance Limited
:: Author           : Deepjyoti Barman
:: Designation      : QA Analyst
:: License          : GNU GPL
:: Date             : September 04 (Friday), 2020
:: Description      : Script to kill all the processes/instances running in local system for UNIX (Linux/Mac) platform


@ECHO off

ECHO Executing script to kill unwanted processes
TASKLIST | FIND /I "chromedriver"
TASKKILL /F /IM chromedriver* && ECHO Successfully terminated all chromedriver instances || ECHO Process "chromedriver.exe" is not running && ECHO System could not find any chromedriver instances