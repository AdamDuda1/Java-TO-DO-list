@echo off

echo:

echo To run this program, you need to have Java (c) Universal Runtime Environment installed.

echo:

echo Checking, if Java is installed on your machine . . .                     [1m[%time%][0m

timeout 3 > NUL

echo:
echo:

where java >nul 2>nul

if %errorlevel%==1 (
	echo [41mERROR[0m: Java not found in system path.                                   [1m[%time%][0m
	echo [43mWARNING[0m: To run this program, you have to install JRE.
	echo [43mWARNING[0m: Install java and JRE manually, or press enter, and we will install it for you.
	timeout 2 > NUL
	pause
	
	echo:

	REM Insert full path to executable here as a literal string or environment
	Call :s_Install_Short "C:\Program Files\test\test1\Java\jre1.7.0.55"
	Goto :EOF

	:s_Install_Short
	REM Block attempts to pass no parameter
	If "%~1" EQU "" Goto :EOF

	REM %~s1 contains the path in the first parameter as a shortened string
	"%~dp0jre-7u55-windows-i586.exe" /s /v"AgreeToLicense=YES INSTALLDIR=%~s1 IEXPLORER=1 MOZILLA=1 REBOOT=SUPRESS JAVAUPDATE=0 SYSTRAY=0"

	EOF:

	echo [42mSUCCESS[0m: Java has been succesfuly installed on your system.              [1m[%time%][0m
	echo [42mSUCCESS[0m: JRE path variable has been attached to system varsiables.       [1m[%time%][0m
	echo:
	echo Now, you can close this window, and run the program.
	timeout 5 > NUL
	pause
) else (
	echo [42mSUCCESS[0m: Java found in system path.                                      [1m[%time%][0m
	echo:
	java -version
	timeout 1 > NUL
)